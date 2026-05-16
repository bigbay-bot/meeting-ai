import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { Meeting } from '@/api/meeting'

export const useMeetingStore = defineStore('meeting', () => {
  const meetings = ref<Meeting[]>([])
  const currentMeeting = ref<Meeting | null>(null)
  const loading = ref(false)

  const meetingCount = computed(() => meetings.value.length)

  const setMeetings = (list: Meeting[]) => {
    meetings.value = list
  }

  const setCurrentMeeting = (meeting: Meeting | null) => {
    currentMeeting.value = meeting
  }

  return {
    meetings: computed(() => meetings.value),
    currentMeeting: computed(() => currentMeeting.value),
    loading: computed(() => loading.value),
    meetingCount,
    setMeetings,
    setCurrentMeeting
  }
})
