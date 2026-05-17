import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { Meeting } from '@/api/meeting'
import { getMeetingsByType } from '@/api/ai-analysis'

export const useAIAnalysisStore = defineStore('ai-analysis', () => {
  // 当前选中的会议类型
  const activeType = ref<'all' | 'project' | 'review' | 'summary'>('all')

  // 已选会议列表
  const selectedMeetings = ref<Meeting[]>([])

  // 当前类型的会议列表（从服务器获取）
  const meetingsByType = ref<Meeting[]>([])

  // 加载状态
  const loading = ref(false)

  // 根据类型筛选会议
  const fetchMeetingsByType = async (type: string) => {
    loading.value = true
    try {
      const response = await getMeetingsByType(type)
      meetingsByType.value = response.records || []
    } catch (error) {
      console.error('Failed to fetch meetings by type:', error)
      meetingsByType.value = []
    } finally {
      loading.value = false
    }
  }

  // 检查会议是否已选中
  const isMeetingSelected = (meeting: Meeting) => {
    return selectedMeetings.value.some(m => m.id === meeting.id)
  }

  // 选择/取消选择会议
  const toggleMeetingSelection = (meeting: Meeting) => {
    const index = selectedMeetings.value.findIndex(m => m.id === meeting.id)
    if (index > -1) {
      selectedMeetings.value.splice(index, 1)
    } else {
      selectedMeetings.value.push(meeting)
    }
  }

  // 移除已选会议
  const removeSelectedMeeting = (meetingId: string) => {
    const index = selectedMeetings.value.findIndex(m => m.id === meetingId)
    if (index > -1) {
      selectedMeetings.value.splice(index, 1)
    }
  }

  // 清空已选会议
  const clearSelectedMeetings = () => {
    selectedMeetings.value = []
  }

  // 获取已选会议ID列表
  const getSelectedMeetingIds = () => {
    return selectedMeetings.value.map(m => m.id)
  }

  return {
    activeType,
    selectedMeetings,
    meetingsByType,
    loading,
    fetchMeetingsByType,
    isMeetingSelected,
    toggleMeetingSelection,
    removeSelectedMeeting,
    clearSelectedMeetings,
    getSelectedMeetingIds
  }
})