import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { Minutes } from '@/api/minutes'

export const useMinutesStore = defineStore('minutes', () => {
  const minutesList = ref<Minutes[]>([])
  const currentMinutes = ref<Minutes | null>(null)

  const pendingCount = computed(() =>
    minutesList.value.filter(m => m.status === 'GENERATED').length
  )

  const setMinutesList = (list: Minutes[]) => {
    minutesList.value = list
  }

  const setCurrentMinutes = (minutes: Minutes | null) => {
    currentMinutes.value = minutes
  }

  return {
    minutesList: computed(() => minutesList.value),
    currentMinutes: computed(() => currentMinutes.value),
    pendingCount,
    setMinutesList,
    setCurrentMinutes
  }
})
