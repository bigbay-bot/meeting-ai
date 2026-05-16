import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import MainLayout from '@/layouts/MainLayout.vue'

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: MainLayout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/Workplace.vue'),
        meta: { title: '首页', icon: 'HomeFilled' }
      },
      {
        path: 'meetings',
        name: 'MeetingList',
        component: () => import('@/views/meeting/MeetingList.vue'),
        meta: { title: '会议列表', icon: 'DocumentCopy' }
      },
      {
        path: 'meetings/:id',
        name: 'MeetingDetail',
        component: () => import('@/views/meeting/MeetingDetail.vue'),
        meta: { title: '会议详情', hidden: true }
      },
      {
        path: 'meetings/create',
        name: 'MeetingCreate',
        component: () => import('@/views/meeting/MeetingCreate.vue'),
        meta: { title: '新建会议', icon: 'CirclePlusFilled' }
      },
      {
        path: 'statistics',
        name: 'Statistics',
        component: () => import('@/views/statistics/Index.vue'),
        meta: { title: '数据统计', icon: 'TrendCharts' }
      },
      {
        path: 'settings',
        name: 'Settings',
        component: () => import('@/views/settings/Index.vue'),
        meta: { title: '系统设置', icon: 'Setting' }
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/Index.vue'),
    meta: { title: '登录', hidden: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to) => {
  document.title = to.meta.title ? `${to.meta.title} - 会议纪要AI` : '会议纪要AI'
})

export default router
