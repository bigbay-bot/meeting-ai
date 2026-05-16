export interface PageResult<T> {
  records: T[]
  total: number
  page: number
  size: number
}

export interface Result<T> {
  code: number
  data: T
  message: string
  timestamp: number
}

export type MeetingStatus = 'pending' | 'ongoing' | 'transcribed' | 'completed'

export type MinutesStatus = 'GENERATED' | 'CONFIRMED' | 'ARCHIVED'

export type MeetingType = 'review' | 'tech' | 'summary' | 'agile' | 'other'
