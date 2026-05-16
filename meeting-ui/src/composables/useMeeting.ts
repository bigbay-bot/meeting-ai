import { ref, computed } from 'vue'
import { getMeetingList, getMeetingById, createMeeting, updateMeeting, deleteMeeting } from '@/api/meeting'
import type { Meeting, MeetingListParams } from '@/api/meeting'

export function useMeeting() {
  const loading = ref(false)
  const meetings = ref<Meeting[]>([])
  const total = ref(0)
  const currentMeeting = ref<Meeting | null>(null)

  const fetchMeetings = async (params: MeetingListParams = {}) => {
    loading.value = true
    try {
      const res = await getMeetingList(params)
      meetings.value = res.data.records
      total.value = res.data.total
    } finally {
      loading.value = false
    }
  }

  const fetchMeetingById = async (id: string) => {
    loading.value = true
    try {
      const res = await getMeetingById(id)
      currentMeeting.value = res.data
    } finally {
      loading.value = false
    }
  }

  return {
    loading: computed(() => loading.value),
    meetings: computed(() => meetings.value),
    total: computed(() => total.value),
    currentMeeting: computed(() => currentMeeting.value),
    fetchMeetings,
    fetchMeetingById,
    createMeeting,
    updateMeeting,
    deleteMeeting
  }
}
