import request from '@/utils/request'

export interface AiChatRequest {
  sessionId?: string
  message: string
  meetingId?: string
}

export interface AiChatResponse {
  sessionId: string
  content: string
  done: boolean
}

export function chatWithAI(data: AiChatRequest) {
  return request.post<AiChatResponse>('/v1/ai/chat', data)
}

export function testConnection() {
  return request.get('/v1/ai/health')
}
