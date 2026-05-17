<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { User, Lock } from '@element-plus/icons-vue'

const router = useRouter()

const form = reactive({
  username: '',
  password: '',
  remember: false
})

const loading = ref(false)

const handleLogin = async () => {
  loading.value = true
  await new Promise((r) => setTimeout(r, 1000))
  loading.value = false
  router.push('/dashboard')
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
            <el-input v-model="form.username" placeholder="用户名" :prefix-icon="User" size="large" />
          </el-form-item>
          <el-form-item>
            <el-input
              v-model="form.password"
              type="password"
              placeholder="密码"
              :prefix-icon="Lock"
              size="large"
              show-password
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
        </el-form>
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
  }
}
</style>
