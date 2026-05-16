export function formatDate(date: string | Date, format = 'YYYY-MM-DD HH:mm') {
  const d = new Date(date)
  const pad = (n: number) => String(n).padStart(2, '0')

  const map: Record<string, string> = {
    YYYY: String(d.getFullYear()),
    MM: pad(d.getMonth() + 1),
    DD: pad(d.getDate()),
    HH: pad(d.getHours()),
    mm: pad(d.getMinutes()),
    ss: pad(d.getSeconds())
  }

  return format.replace(/YYYY|MM|DD|HH|mm|ss/g, (match) => map[match])
}

export function formatDuration(minutes: number) {
  const h = Math.floor(minutes / 60)
  const m = minutes % 60
  if (h > 0) {
    return `${h}小时${m > 0 ? `${m}分钟` : ''}`
  }
  return `${m}分钟`
}

export function truncateText(text: string, maxLength: number) {
  if (text.length <= maxLength) return text
  return text.slice(0, maxLength) + '...'
}
