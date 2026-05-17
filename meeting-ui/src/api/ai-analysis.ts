import type { PageResult } from '@/types'
import type { Meeting } from '@/api/meeting'

// 模拟会议数据
const mockMeetings: Meeting[] = [
  {
    id: '1',
    title: '校园网安全专项工作会',
    organizer: '张晓明',
    time: '2026-05-15 14:00 - 16:00',
    duration: '2小时',
    location: '线上会议',
    participants: ['张三', '李四', '王五', '赵六'],
    status: 'completed',
    type: 'project'
  },
  {
    id: '2',
    title: '技术架构讨论会',
    organizer: '李华',
    time: '2026-05-14 10:00 - 11:30',
    duration: '1.5小时',
    location: '会议室A',
    participants: ['李华', '王五', '赵六'],
    status: 'completed',
    type: 'review'
  },
  {
    id: '3',
    title: '月度总结会议',
    organizer: '王经理',
    time: '2026-05-10 09:30 - 12:00',
    duration: '2.5小时',
    location: '会议室B',
    participants: ['王经理', '赵六', '钱七', '孙八'],
    status: 'completed',
    type: 'summary'
  },
  {
    id: '4',
    title: 'Sprint规划会议',
    organizer: '赵敏捷',
    time: '2026-05-08 14:00 - 15:00',
    duration: '1小时',
    location: '线上会议',
    participants: ['赵敏捷', '钱七', '孙八'],
    status: 'completed',
    type: 'project'
  },
  {
    id: '5',
    title: '需求评审会议',
    organizer: '产品部',
    time: '2026-05-05 16:00 - 17:30',
    duration: '1.5小时',
    location: '会议室C',
    participants: ['产品经理', '技术负责人'],
    status: 'completed',
    type: 'review'
  }
]

// 按类型获取会议列表（模拟实现）
export function getMeetingsByType(type: string) {
  return new Promise<PageResult<Meeting>>((resolve) => {
    setTimeout(() => {
      let filteredMeetings = mockMeetings

      if (type !== 'all') {
        // 映射前端标签到后端类型
        const typeMapping: Record<string, string> = {
          'project': 'project',
          'review': 'review',
          'summary': 'summary'
        }
        const backendType = typeMapping[type] || type
        filteredMeetings = mockMeetings.filter(meeting => meeting.type === backendType)
      }

      resolve({
        records: filteredMeetings,
        total: filteredMeetings.length,
        page: 1,
        size: filteredMeetings.length
      })
    }, 300) // 模拟网络延迟
  })
}

// 提交AI跨会议分析请求（模拟实现）
export function submitCrossAnalysis(params: {
  meetingIds: string[]
  question: string
}) {
  return new Promise<{ data: string }>((resolve) => {
    setTimeout(() => {
      const selectedMeetingTitles = mockMeetings
        .filter(m => params.meetingIds.includes(m.id))
        .map(m => m.title)
        .join('、')

      resolve({
        data: `基于您选择的会议（${selectedMeetingTitles}）和问题"${params.question}"，AI分析结果：\n\n1. 这些会议都围绕技术架构和安全主题展开\n2. 共识别出15个关键决策点\n3. 发现3个跨会议的共性议题\n4. 建议后续重点关注网络安全防护体系`
      })
    }, 800) // 模拟AI处理延迟
  })
}

// AI分析相关的类型定义
export interface AICrossAnalysisRequest {
  meetingIds: string[]
  question: string
  analysisType?: 'comparison' | 'summary' | 'trend'
}

export interface AICrossAnalysisResponse {
  sessionId: string
  content: string
  insights: Array<{
    meetingId: string
    meetingTitle: string
    keyPoints: string[]
  }>
}