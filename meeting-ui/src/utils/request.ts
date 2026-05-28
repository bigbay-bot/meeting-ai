import axios, { type AxiosInstance, type AxiosRequestConfig, type AxiosResponse } from 'axios'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'

const instance: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json'
  }
})

instance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    config.headers['Cache-Control'] = 'no-cache'
    config.headers['Pragma'] = 'no-cache'
    return config
  },
  (error) => Promise.reject(error)
)

instance.interceptors.response.use(
  (response: AxiosResponse) => {
    const res = response.data
    if (res.code !== 200) {
      ElMessage.error(res.message || '请求失败')
      if (res.code === 401) {
        const userStore = useUserStore()
        userStore.clearToken()
        window.location.href = '/login'
      }
      return Promise.reject(new Error(res.message))
    }
    return res.data
  },
  (error) => {
    if (error.response?.status === 401 || error.response?.status === 403) {
      const userStore = useUserStore()
      userStore.clearToken()
      window.location.href = '/login'
    }
    ElMessage.error(error.response?.data?.message || error.message || '网络错误')
    return Promise.reject(error)
  }
)

export function get<T = unknown>(url: string, config?: AxiosRequestConfig) {
  return instance.get<any, T>(url, config)
}

export function post<T = unknown>(url: string, data?: unknown, config?: AxiosRequestConfig) {
  return instance.post<any, T>(url, data, config)
}

export function put<T = unknown>(url: string, data?: unknown, config?: AxiosRequestConfig) {
  return instance.put<any, T>(url, data, config)
}

export function del<T = unknown>(url: string, config?: AxiosRequestConfig) {
  return instance.delete<any, T>(url, config)
}

export default {
  get,
  post,
  put,
  delete: del
}
