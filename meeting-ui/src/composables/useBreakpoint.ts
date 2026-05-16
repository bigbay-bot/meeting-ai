import { ref, onMounted, onUnmounted } from 'vue'

export function useBreakpoint(query = '(max-width: 1023px)') {
  const matches = ref(false)
  let mql: MediaQueryList | null = null

  const update = () => {
    matches.value = mql?.matches ?? false
  }

  onMounted(() => {
    mql = window.matchMedia(query)
    update()
    mql.addEventListener('change', update)
  })

  onUnmounted(() => {
    mql?.removeEventListener('change', update)
  })

  return matches
}
