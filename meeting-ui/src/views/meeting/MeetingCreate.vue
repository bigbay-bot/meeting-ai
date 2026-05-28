<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { Plus, ArrowLeft } from '@element-plus/icons-vue'

const router = useRouter()

const formRef = ref()

const form = reactive({
  title: '',
  type: '',
  time: '',
  duration: 60,
  location: '',
  participants: [] as string[],
  description: '',
  attachments: [] as string[]
})

const rules = {
  title: [{ required: true, message: '请输入会议标题', trigger: 'blur' }],
  type: [{ required: true, message: '请选择会议类型', trigger: 'change' }],
  time: [{ required: true, message: '请选择会议时间', trigger: 'change' }]
}

const meetingTypes = [
  { label: '需求评审', value: 'review' },
  { label: '技术讨论', value: 'tech' },
  { label: '总结汇报', value: 'summary' },
  { label: '敏捷开发', value: 'agile' },
  { label: '其他', value: 'other' }
]

const participantOptions = ['张三', '李四', '王五', '赵六', '钱七', '孙八']

const handleSubmit = async () => {
  await formRef.value.validate()
  console.log('创建会议', form)
  router.push('/meetings')
}

const handleCancel = () => {
  router.push('/meetings')
}
</script>

<template>
  <div class="meeting-create page-container">
    <div class="page-header">
      <el-button text class="back-btn" @click="router.push('/meetings')">
        <el-icon :size="18"><ArrowLeft /></el-icon>
        返回
      </el-button>
      <h1 class="page-title">新建会议</h1>
    </div>

    <div class="form-card ds-card">
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="100px"
        class="create-form"
      >
        <el-form-item label="会议标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入会议标题" size="large" />
        </el-form-item>

        <el-form-item label="会议类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择会议类型" size="large">
            <el-option
              v-for="t in meetingTypes"
              :key="t.value"
              :label="t.label"
              :value="t.value"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="会议时间" prop="time">
          <el-date-picker
            v-model="form.time"
            type="datetime"
            placeholder="选择日期时间"
            size="large"
          />
        </el-form-item>

        <el-form-item label="预计时长">
          <div class="duration-row">
            <el-slider v-model="form.duration" :min="15" :max="240" :step="15" show-stops />
            <span class="duration-text">{{ form.duration }} 分钟</span>
          </div>
        </el-form-item>

        <el-form-item label="会议地点">
          <el-input
            v-model="form.location"
            placeholder="请输入会议地点或线上会议链接"
            size="large"
          />
        </el-form-item>

        <el-form-item label="参会人员">
          <el-select
            v-model="form.participants"
            multiple
            placeholder="请选择参会人员"
            size="large"
          >
            <el-option
              v-for="p in participantOptions"
              :key="p"
              :label="p"
              :value="p"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="会议描述">
          <el-input
            v-model="form.description"
            type="textarea"
            :rows="4"
            placeholder="请输入会议议程或背景描述"
          />
        </el-form-item>

        <el-form-item label="附件">
          <el-upload
            action="#"
            :auto-upload="false"
            drag
            class="upload-area"
          >
            <el-icon :size="40"><Plus /></el-icon>
            <div class="upload-text">
              拖拽文件到此处或 <em>点击上传</em>
            </div>
            <template #tip>
              <div class="upload-tip">支持上传会议相关的文档、PPT等</div>
            </template>
          </el-upload>
        </el-form-item>

        <el-form-item>
          <div class="form-actions">
            <el-button type="primary" size="large" @click="handleSubmit">
              创建会议
            </el-button>
            <el-button size="large" @click="handleCancel">取消</el-button>
          </div>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<style scoped lang="scss">
.page-header {
  display: flex;
  align-items: center;
  gap: $space-3;
  margin-bottom: $space-5;

  .back-btn {
    padding: 0;
    height: auto;
    font-size: $font-size-base;
    color: $text-secondary;

    &:hover {
      color: $primary-color;
    }
  }

  .page-title {
    font-size: $font-size-xl;
    font-weight: $font-weight-bold;
    color: $text-primary;
    margin: 0;
  }
}

.form-card {
  max-width: 720px;
  padding: $space-5;

  @include respond-to(sm) {
    padding: $space-4;
  }
}

.create-form {
  :deep(.el-form-item__label) {
    font-weight: $font-weight-medium;
    color: $text-primary;
  }

  :deep(.el-input__wrapper),
  :deep(.el-select__wrapper),
  :deep(.el-textarea__inner) {
    border-radius: $radius-md;
  }
}

.duration-row {
  display: flex;
  align-items: center;
  gap: $space-4;

  .el-slider {
    flex: 1;
  }

  .duration-text {
    font-size: $font-size-base;
    color: $text-secondary;
    font-weight: $font-weight-medium;
    white-space: nowrap;
    min-width: 64px;
  }
}

.upload-area {
  :deep(.el-upload-dragger) {
    border-radius: $radius-lg;
    border-color: $border-color;
    background: $bg-hover;
    padding: $space-5;
    transition: $transition-base;

    &:hover {
      border-color: $primary-color;
    }
  }

  .upload-text {
    margin-top: $space-2;
    font-size: $font-size-base;
    color: $text-secondary;

    em {
      color: $primary-color;
      font-style: normal;
      font-weight: $font-weight-medium;
    }
  }

  .upload-tip {
    margin-top: $space-2;
    font-size: $font-size-xs;
    color: $text-disabled;
  }
}

.form-actions {
  display: flex;
  gap: $space-3;
  padding-top: $space-2;
}

.meeting-create.page-container {
  max-width: 90% !important;
  margin: 0 auto;
  width: 100%;

  @media (max-width: #{$bp-md - 1px}) {
    max-width: 100% !important;
  }
}
</style>
