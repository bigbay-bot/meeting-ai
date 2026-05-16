<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import {
  HomeFilled,
  DocumentCopy,
  CirclePlusFilled,
  TrendCharts,
  Setting,
  Bell,
  ArrowDown
} from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()

const isCollapse = ref(false)

const menuItems = [
  { path: '/dashboard', title: '首页', icon: HomeFilled },
  { path: '/meetings', title: '会议列表', icon: DocumentCopy },
  { path: '/meetings/create', title: '新建会议', icon: CirclePlusFilled },
  { path: '/statistics', title: '数据统计', icon: TrendCharts },
  { path: '/settings', title: '系统设置', icon: Setting }
]

const activeMenu = computed(() => route.path)

const handleSelect = (path: string) => {
  router.push(path)
}
</script>

<template>
  <el-container class="main-layout">
    <el-aside :width="isCollapse ? '64px' : '220px'" class="sidebar">
      <div class="logo">
        <span v-if="!isCollapse">会议纪要AI</span>
        <el-icon v-else><DocumentCopy /></el-icon>
      </div>
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :collapse-transition="false"
        router
        background-color="#f8f9fb"
        text-color="#5a5a5a"
        active-text-color="#4f46e5"
        @select="handleSelect"
      >
        <el-menu-item v-for="item in menuItems" :key="item.path" :index="item.path">
          <el-icon><component :is="item.icon" /></el-icon>
          <template #title>{{ item.title }}</template>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="header">
        <div class="header-left">
          <el-icon class="collapse-btn" @click="isCollapse = !isCollapse">
            <DocumentCopy />
          </el-icon>
        </div>
        <div class="header-right">
          <el-badge :value="3" class="notice-badge">
            <el-icon :size="20"><Bell /></el-icon>
          </el-badge>
          <el-dropdown>
            <span class="user-info">
              <el-avatar :size="32" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
              <span class="username">管理员</span>
              <el-icon><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>个人中心</el-dropdown-item>
                <el-dropdown-item divided>退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped lang="scss">
.main-layout {
  height: 100vh;

  .sidebar {
    background: #f8f9fb;
    transition: width 0.3s;

    .logo {
      height: 60px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 18px;
      font-weight: 600;
      color: #4f46e5;
      border-bottom: 1px solid #e4e7ed;
    }

    :deep(.el-menu) {
      border-right: none;
    }
  }

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    background: #fff;
    box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);

    .collapse-btn {
      font-size: 18px;
      cursor: pointer;
      color: #5a5a5a;
    }

    .header-right {
      display: flex;
      align-items: center;
      gap: 24px;

      .notice-badge {
        cursor: pointer;
      }

      .user-info {
        display: flex;
        align-items: center;
        gap: 8px;
        cursor: pointer;

        .username {
          font-size: 14px;
          color: #333;
        }
      }
    }
  }

  .main-content {
    background: #f5f6fa;
    padding: 20px;
    overflow-y: auto;
  }
}
</style>
