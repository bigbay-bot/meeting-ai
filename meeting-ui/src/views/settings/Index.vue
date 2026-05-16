<script setup lang="ts">
import { ref, reactive } from 'vue'

const activeTab = ref('general')

const generalForm = reactive({
  companyName: 'XX科技有限公司',
  systemName: '会议纪要AI',
  logo: '',
  language: 'zh-CN'
})

const aiForm = reactive({
  apiKey: '',
  apiBase: 'https://fastgpt.run/api',
  model: 'gpt-3.5-turbo',
  maxTokens: 4000,
  temperature: 0.7
})

const notifyForm = reactive({
  emailNotify: true,
  wechatNotify: false,
  summaryBefore: 30,
  deadlineRemind: true
})

const saveLoading = ref(false)

const handleSave = async () => {
  saveLoading.value = true
  await new Promise(r => setTimeout(r, 800))
  saveLoading.value = false
}
</script>

<template>
  <div class="settings">
    <h2 class="page-title">系统设置</h2>

    <el-tabs v-model="activeTab" type="border-card">
      <el-tab-pane label="基础设置" name="general">
        <el-form :model="generalForm" label-width="120px" style="max-width: 600px">
          <el-form-item label="企业名称">
            <el-input v-model="generalForm.companyName" />
          </el-form-item>
          <el-form-item label="系统名称">
            <el-input v-model="generalForm.systemName" />
          </el-form-item>
          <el-form-item label="系统语言">
            <el-select v-model="generalForm.language" style="width: 100%">
              <el-option label="简体中文" value="zh-CN" />
              <el-option label="English" value="en-US" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" :loading="saveLoading" @click="handleSave">保存</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>

      <el-tab-pane label="AI 配置" name="ai">
        <el-form :model="aiForm" label-width="140px" style="max-width: 600px">
          <el-form-item label="FastGPT API Key">
            <el-input v-model="aiForm.apiKey" type="password" show-password placeholder="请输入 API Key" />
          </el-form-item>
          <el-form-item label="API 地址">
            <el-input v-model="aiForm.apiBase" />
          </el-form-item>
          <el-form-item label="模型">
            <el-select v-model="aiForm.model" style="width: 100%">
              <el-option label="GPT-3.5 Turbo" value="gpt-3.5-turbo" />
              <el-option label="GPT-4" value="gpt-4" />
              <el-option label="GPT-4 Turbo" value="gpt-4-turbo" />
            </el-select>
          </el-form-item>
          <el-form-item label="最大 Token">
            <el-slider v-model="aiForm.maxTokens" :min="1000" :max="8000" :step="500" show-stops />
          </el-form-item>
          <el-form-item label="Temperature">
            <el-slider v-model="aiForm.temperature" :min="0" :max="1" :step="0.1" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" :loading="saveLoading" @click="handleSave">保存</el-button>
            <el-button @click="handleSave">测试连接</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>

      <el-tab-pane label="通知设置" name="notify">
        <el-form :model="notifyForm" label-width="140px" style="max-width: 600px">
          <el-form-item label="邮件通知">
            <el-switch v-model="notifyForm.emailNotify" />
          </el-form-item>
          <el-form-item label="微信通知">
            <el-switch v-model="notifyForm.wechatNotify" />
          </el-form-item>
          <el-form-item label="会前提醒">
            <el-input-number v-model="notifyForm.summaryBefore" :min="5" :max="60" />
            <span class="form-tip">分钟</span>
          </el-form-item>
          <el-form-item label="截止提醒">
            <el-switch v-model="notifyForm.deadlineRemind" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" :loading="saveLoading" @click="handleSave">保存</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<style scoped lang="scss">
.settings {
  .page-title {
    margin: 0 0 20px;
    font-size: 20px;
    font-weight: 600;
  }

  .form-tip {
    margin-left: 12px;
    color: #999;
    font-size: 13px;
  }
}
</style>
