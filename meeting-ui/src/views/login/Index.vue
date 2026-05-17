<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const form = reactive({
  username: '',
  password: '',
  remember: false
})

const loading = ref(false)

const handleLogin = async () => {
  if (!form.username.trim() || !form.password.trim()) {
    ElMessage.warning('请输入用户名和密码')
    return
  }
  loading.value = true
  try {
    await userStore.doLogin({
      username: form.username,
      password: form.password
    })
    ElMessage.success('登录成功')
    router.push('/dashboard')
  } catch (error: any) {
    ElMessage.error(error.message || '登录失败')
  } finally {
    loading.value = false
  }
}

const goToRegister = () => {
  router.push('/register')
}
</script>

<template>
  <div class="login-page">
    <div class="login-left">
      <div class="brand-area">
        <div class="brand-logo">
          <svg viewBox="0 0 32 32" fill="none">
            <path d="M16 2L28 9V23L16 30L4 23V9L16 2Z" fill="white" />
            <path d="M16 8L22 11.5V18.5L16 22L10 18.5V11.5L16 8Z" fill="#4D7CFE" fill-opacity="0.9" />
          </svg>
        </div>
        <h1>智会纪要</h1>
        <p>AI 驱动的智能会议纪要平台</p>
        <ul class="features">
          <li>一键生成结构化会议纪要</li>
          <li>智能提取决策项与行动项</li>
          <li>AI 复盘助手深度分析</li>
        </ul>
      </div>
    </div>

    <div class="login-right">
      <div class="login-box">
        <h2>欢迎回来</h2>
        <p class="login-sub">登录您的账户以继续</p>

        <el-form :model="form" class="login-form">
          <el-form-item>
            <el-input v-model="form.username" placeholder="用户名" :prefix-icon="User" size="large" @keyup.enter="handleLogin" />
          </el-form-item>
          <el-form-item>
            <el-input
              v-model="form.password"
              type="password"
              placeholder="密码"
              :prefix-icon="Lock"
              size="large"
              show-password
              @keyup.enter="handleLogin"
            />
          </el-form-item>
          <el-form-item class="remember-row">
            <el-checkbox v-model="form.remember">记住我</el-checkbox>
            <el-link type="primary" :underline="false">忘记密码？</el-link>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="large" style="width: 100%" :loading="loading" @click="handleLogin">
              登录
            </el-button>
          </el-form-item>
          <el-form-item>
            <el-button size="large" style="width: 100%" @click="goToRegister">
              注册账号
            </el-button>
          </el-form-item>
        </el-form>

        <div class="demo-accounts">
          <p class="demo-title">测试账号</p>
          <div class="demo-row" @click="form.username = 'admin'; form.password = 'admin123'">
            <span class="demo-label">管理员</span>
            <span class="demo-value">admin / admin123</span>
          </div>
          <div class="demo-row" @click="form.username = 'zhangxiaoming'; form.password = 'admin123'">
            <span class="demo-label">普通用户</span>
            <span class="demo-value">zhangxiaoming / admin123</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.login-page {
  height: 100vh;
  display: flex;
}

.login-left {
  flex: 1;
  background: $primary-color;
  display: none;
  align-items: center;
  justify-content: center;
  padding: $space-7;

  @include respond-to(min-lg) {
    display: flex;
  }

  .brand-area {
    color: #fff;
    max-width: 400px;

    .brand-logo {
      width: 52px;
      height: 52px;
      margin-bottom: $space-5;

      svg {
        width: 100%;
        height: 100%;
      }
    }

    h1 {
      font-size: $font-size-2xl;
      font-weight: $font-weight-semibold;
      margin: 0 0 $space-3;
      line-height: $line-height-tight;
      letter-spacing: -0.02em;
    }

    p {
      font-size: $font-size-lg;
      opacity: 0.92;
      margin: 0 0 $space-6;
      line-height: $line-height-base;
    }

    .features {
      list-style: none;
      padding: 0;
      margin: 0;

      li {
        font-size: $font-size-base;
        opacity: 0.88;
        padding: $space-2 0;
        padding-left: $space-5;
        position: relative;
        line-height: $line-height-base;

        &::before {
          content: '✓';
          position: absolute;
          left: 0;
        }
      }
    }
  }
}

.login-right {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  background: $bg-white;
  padding: $space-5;

  .login-box {
    width: 100%;
    max-width: 360px;

    h2 {
      font-size: $font-size-2xl;
      font-weight: $font-weight-semibold;
      color: $text-title;
      margin: 0 0 $space-2;
      line-height: $line-height-tight;
    }

    .login-sub {
      font-size: $font-size-base;
      color: $text-secondary;
      margin: 0 0 $space-6;
      line-height: $line-height-base;
    }

    .remember-row {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .demo-accounts {
      margin-top: $space-6;
      padding-top: $space-5;
      border-top: 1px solid $border-light;

      .demo-title {
        font-size: $font-size-sm;
        color: $text-secondary;
        margin: 0 0 $space-3;
        text-align: center;
      }

      .demo-row {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: $space-3 $space-4;
        margin-bottom: $space-2;
        background: $bg-hover;
        border-radius: $radius-md;
        cursor: pointer;
        transition: $transition-base;

        &:hover {
          background: $primary-light;
        }

        .demo-label {
          font-size: $font-size-sm;
          color: $text-secondary;
        }

        .demo-value {
          font-size: $font-size-sm;
          font-weight: $font-weight-medium;
          color: $primary-color;
          font-family: $font-family-num;
        }
      }
    }
  }
}
</style>
