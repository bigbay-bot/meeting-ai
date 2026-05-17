import request from '@/utils/request'

export interface LoginRequest {
  username: string
  password: string
}

export interface RegisterRequest {
  username: string
  password: string
  email: string
  realName?: string
}

export interface LoginResponse {
  token: string
  userId: string
  username: string
  avatar: string
}

export interface UserInfo {
  id: string
  username: string
  email: string
  realName: string
  avatar: string
  role: string
  department: string
  position: string
}

export function login(data: LoginRequest) {
  return request.post<LoginResponse>('/api/v1/auth/login', data)
}

export function register(data: RegisterRequest) {
  return request.post<void>('/api/v1/auth/register', data)
}

export function getUserInfo() {
  return request.get<UserInfo>('/api/v1/user/info')
}
