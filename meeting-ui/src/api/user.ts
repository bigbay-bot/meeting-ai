import request from '@/utils/request'

export interface LoginRequest {
  username: string
  password: string
}

export interface LoginResponse {
  token: string
  userId: string
  username: string
  avatar: string
}

export function login(data: LoginRequest) {
  return request.post<LoginResponse>('/api/v1/auth/login', data)
}

export function getUserInfo() {
  return request.get('/api/v1/user/info')
}
