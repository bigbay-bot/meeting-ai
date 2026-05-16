import { ref, computed } from 'vue'
import { generateMinutes, getMinutesById, updateMinutes } from '@/api/minutes'
import type { Minutes } from '@/api/minutes'

export function useMinutes() {
  const loading = ref(false)
  const minutes = ref<Minutes | null>(null)

  const generate = async (meetingId: string) => {
    loading.value = true
    try {
      const res = await generateMinutes(meetingId)
      minutes.value = res.data
      return res.data
    } finally {
      loading.value = false
    }
  }

  const fetchById = async (id: string) => {
    loading.value = true
    try {
      const res = await getMinutesById(id)
      minutes.value = res.data
    } finally {
      loading.value = false
    }
  }

  const update = async (id: string, data: Partial<Minutes>) => {
    return updateMinutes(id, data)
  }

  return {
    loading: computed(() => loading.value),
    minutes: computed(() => minutes.value),
    generate,
    fetchById,
    update
  }
}
