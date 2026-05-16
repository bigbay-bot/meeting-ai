<script setup lang="ts">
import { ref, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import {
  HomeFilled,
  DocumentCopy,
  TrendCharts,
  Setting,
  ArrowDown,
  Expand
} from '@element-plus/icons-vue'
import { useBreakpoint } from '@/composables/useBreakpoint'

const route = useRoute()
const router = useRouter()
const isMobile = useBreakpoint('(max-width: 1023px)')
const sidebarOpen = ref(false)

const menuItems = [
  { path: '/dashboard', title: '首页', icon: HomeFilled },
  { path: '/meetings', title: '历史会议', icon: DocumentCopy },
  { path: '/statistics', title: '数据看板', icon: TrendCharts },
  { path: '/settings', title: '设置中心', icon: Setting }
]

const activeMenu = computed(() => {
  const path = route.path
  if (path.startsWith('/meetings') && path !== '/meetings/create') return '/meetings'
  return path
})

const handleSelect = (path: string) => {
  router.push(path)
  if (isMobile.value) sidebarOpen.value = false
}

watch(isMobile, (mobile) => {
  if (!mobile) sidebarOpen.value = false
})

watch(
  () => route.path,
  () => {
    if (isMobile.value) sidebarOpen.value = false
  }
)
</script>

<template>
  <div class="main-layout">
    <transition name="fade">
      <div
        v-if="isMobile && sidebarOpen"
        class="sidebar-mask"
        @click="sidebarOpen = false"
      />
    </transition>

    <aside class="sidebar" :class="{ open: sidebarOpen, mobile: isMobile }">
      <div class="brand">
        <div class="brand-icon">
          <svg viewBox="0 0 32 32" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M16 2L28 9V23L16 30L4 23V9L16 2Z" fill="#3B82F6" />
            <path
              d="M16 8L22 11.5V18.5L16 22L10 18.5V11.5L16 8Z"
              fill="white"
              fill-opacity="0.9"
            />
          </svg>
        </div>
        <div class="brand-text">
          <span class="brand-name">智会纪要</span>
          <span class="brand-sub">AI Meeting Notes</span>
        </div>
      </div>

      <nav class="nav-menu">
        <div
          v-for="item in menuItems"
          :key="item.path"
          class="nav-item"
          :class="{ active: activeMenu === item.path }"
          @click="handleSelect(item.path)"
        >
          <el-icon :size="18"><component :is="item.icon" /></el-icon>
          <span>{{ item.title }}</span>
        </div>
      </nav>

      <div class="sidebar-promo">
        <div class="promo-card">
          <div class="promo-icon">✨</div>
          <p class="promo-title">AI 智能纪要</p>
          <p class="promo-desc">一键生成高质量会议纪要</p>
        </div>
      </div>

      <div class="user-section">
        <el-avatar
          :size="36"
          src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
        />
        <div class="user-info">
          <span class="user-name">张晓明</span>
          <span class="user-role">产品设计师</span>
        </div>
        <el-icon class="user-arrow"><ArrowDown /></el-icon>
      </div>
    </aside>

    <div class="layout-body">
      <header v-if="isMobile" class="mobile-header">
        <el-button class="menu-btn" text @click="sidebarOpen = true">
          <el-icon :size="20"><Expand /></el-icon>
        </el-button>
        <span class="mobile-title">智会纪要</span>
        <div class="mobile-header-placeholder" />
      </header>

      <main class="main-content">
        <router-view />
      </main>
    </div>
  </div>
</template>

<style scoped lang="scss">
.main-layout {
  display: flex;
  height: 100vh;
  height: 100dvh;
  background: $bg-base;
  overflow: hidden;
}

.sidebar-mask {
  position: fixed;
  inset: 0;
  background: rgba(29, 33, 41, 0.45);
  z-index: 998;
}

.sidebar {
  width: $sidebar-width;
  flex-shrink: 0;
  background: $bg-sidebar;
  border-right: 1px solid $border-light;
  display: flex;
  flex-direction: column;
  padding: 20px 14px;
  z-index: 999;
  transition: transform 0.28s cubic-bezier(0.34, 0.69, 0.1, 1);

  &.mobile {
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    transform: translateX(-100%);
    box-shadow: 4px 0 24px rgba(29, 33, 41, 0.12);

    &.open {
      transform: translateX(0);
    }
  }
}

.brand {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 2px 6px 20px;

  .brand-icon {
    width: 34px;
    height: 34px;
    flex-shrink: 0;

    svg {
      width: 100%;
      height: 100%;
    }
  }

  .brand-text {
    display: flex;
    flex-direction: column;
    gap: 1px;
    min-width: 0;
  }

  .brand-name {
    font-size: $font-size-lg;
    font-weight: $font-weight-semibold;
    color: $text-title;
    letter-spacing: 0.02em;
    line-height: $line-height-tight;
  }

  .brand-sub {
    font-size: $font-size-xs;
    color: $text-secondary;
    line-height: 1.4;
  }
}

.nav-menu {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 2px;
  overflow-y: auto;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px 12px;
  border-radius: $radius-md;
  cursor: pointer;
  font-size: $font-size-base;
  font-weight: $font-weight-regular;
  color: $text-regular;
  transition: $transition-base;
  line-height: $line-height-base;

  &:hover {
    background: $primary-light;
    color: $primary-color;
  }

  &.active {
    background: $primary-light;
    color: $primary-color;
    font-weight: $font-weight-semibold;
  }
}

.sidebar-promo {
  margin: 12px 0;

  .promo-card {
    background: linear-gradient(135deg, #edf2ff 0%, #f5f8ff 100%);
    border-radius: $radius-lg;
    padding: 14px;
    text-align: center;
    border: 1px solid rgba(59, 130, 246, 0.12);

    .promo-icon {
      font-size: 22px;
      margin-bottom: 6px;
      line-height: 1;
    }

    .promo-title {
      font-size: $font-size-sm;
      font-weight: $font-weight-semibold;
      color: $primary-color;
      margin: 0 0 4px;
      line-height: $line-height-tight;
    }

    .promo-desc {
      font-size: $font-size-xs;
      color: $text-secondary;
      margin: 0;
      line-height: 1.5;
    }
  }
}

.user-section {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px;
  border-radius: $radius-md;
  cursor: pointer;
  transition: background 0.2s;

  &:hover {
    background: $bg-hover;
  }

  .user-info {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 1px;
    min-width: 0;
  }

  .user-name {
    font-size: $font-size-base;
    font-weight: $font-weight-semibold;
    color: $text-title;
    line-height: $line-height-tight;
    @include text-ellipsis;
  }

  .user-role {
    font-size: $font-size-xs;
    color: $text-secondary;
    line-height: 1.4;
    @include text-ellipsis;
  }

  .user-arrow {
    color: $text-secondary;
    font-size: 12px;
    flex-shrink: 0;
  }
}

.layout-body {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-width: 0;
  overflow: hidden;
}

.mobile-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 52px;
  padding: 0 12px 0 4px;
  background: $bg-white;
  border-bottom: 1px solid $border-light;
  flex-shrink: 0;

  .menu-btn {
    width: 40px;
    height: 40px;
    color: $text-regular;
  }

  .mobile-title {
    font-size: $font-size-lg;
    font-weight: $font-weight-semibold;
    color: $text-title;
  }

  .mobile-header-placeholder {
    width: 40px;
  }
}

.main-content {
  flex: 1;
  overflow-y: auto;
  overflow-x: hidden;
  padding: 20px;

  @include respond-to(min-lg) {
    padding: 24px 28px;
  }

  @include respond-to(md) {
    padding: 16px;
  }

  @include respond-to(sm) {
    padding: 12px;
  }
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.25s;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
