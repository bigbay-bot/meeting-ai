import request from '@/utils/request'
import type { PageResult } from '@/types'

export interface Meeting {
  id: string
  title: string
  organizer: string
  time: string
  duration: string
  location: string
  participants: string[]
  status: string
  type: string
}

export interface MeetingListParams {
  page?: number
  size?: number
  keyword?: string
  status?: string
  startTime?: string
  endTime?: string
}

export function getMeetingList(params: MeetingListParams) {
  return request.get<PageResult<Meeting>>('/api/v1/meetings', { params })
}

export function getMeetingById(id: string) {
  return request.get<Meeting>(`/api/v1/meetings/${id}`)
}

export function createMeeting(data: Partial<Meeting>) {
  return request.post<string>('/api/v1/meetings', data)
}

export function updateMeeting(id: string, data: Partial<Meeting>) {
  return request.put(`/api/v1/meetings/${id}`, data)
}

export function deleteMeeting(id: string) {
  return request.delete(`/api/v1/meetings/${id}`)
}
