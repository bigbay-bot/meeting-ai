import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { login, getUserInfo } from '@/api/user'
import type { LoginRequest, LoginResponse } from '@/api/user'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref<Partial<LoginResponse>>({})

  const isLoggedIn = computed(() => !!token.value)

  const setToken = (val: string) => {
    token.value = val
    localStorage.setItem('token', val)
  }

  const clearToken = () => {
    token.value = ''
    userInfo.value = {}
    localStorage.removeItem('token')
  }

  const doLogin = async (data: LoginRequest) => {
    const res = await login(data)
    setToken(res.token)
    userInfo.value = res
    return res
  }

  const fetchUserInfo = async () => {
    const res = await getUserInfo()
    userInfo.value = res
  }

  return {
    token,
    userInfo: computed(() => userInfo.value),
    isLoggedIn,
    doLogin,
    fetchUserInfo,
    clearToken
  }
})
