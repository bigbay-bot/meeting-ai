import request from '@/utils/request'

export interface Minutes {
  id: string
  meetingId: string
  title: string
  summary: string
  participants: string[]
  decisions: string[]
  actionItems: ActionItem[]
  keyPoints: string[]
  status: string
  createdAt: string
}

export interface ActionItem {
  content: string
  owner: string
  deadline: string
  status: 'pending' | 'completed'
}

export function generateMinutes(meetingId: string) {
  return request.post<Minutes>(`/v1/meetings/${meetingId}/minutes/generate`)
}

export function getMinutesById(id: string) {
  return request.get<Minutes>(`/v1/minutes/${id}`)
}

export function updateMinutes(id: string, data: Partial<Minutes>) {
  return request.put(`/v1/minutes/${id}`, data)
}
