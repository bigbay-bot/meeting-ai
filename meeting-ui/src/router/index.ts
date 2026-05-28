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
        meta: { title: '历史会议', icon: 'DocumentCopy' }
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
        meta: { title: '数据看板', icon: 'TrendCharts' }
      },
      {
        path: 'settings',
        name: 'Settings',
        component: () => import('@/views/settings/Index.vue'),
        meta: { title: '设置中心', icon: 'Setting' }
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/Index.vue'),
    meta: { title: '登录', hidden: true }
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/register/Index.vue'),
    meta: { title: '注册', hidden: true }
  }
]

const publicPaths = ['/login', '/register']

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, _from, next) => {
  document.title = to.meta.title ? `${to.meta.title} - 智会纪要` : '智会纪要'

  const token = localStorage.getItem('token')
  const isPublic = publicPaths.includes(to.path)

  if (!token && !isPublic) {
    next('/login')
    return
  }

  if (token && isPublic) {
    next('/dashboard')
    return
  }

  next()
})

export default router
