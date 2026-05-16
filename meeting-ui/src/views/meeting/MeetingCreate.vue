<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { Plus } from '@element-plus/icons-vue'

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
  <div class="meeting-create">
    <el-page-header @back="router.push('/meetings')" title="新建会议" />

    <el-card class="form-card" shadow="never">
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="100px"
        style="max-width: 800px"
      >
        <el-form-item label="会议标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入会议标题" />
        </el-form-item>

        <el-form-item label="会议类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择会议类型" style="width: 100%">
            <el-option
              v-for="type in meetingTypes"
              :key="type.value"
              :label="type.label"
              :value="type.value"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="会议时间" prop="time">
          <el-date-picker
            v-model="form.time"
            type="datetime"
            placeholder="选择日期时间"
            style="width: 100%"
          />
        </el-form-item>

        <el-form-item label="预计时长">
          <el-slider v-model="form.duration" :min="15" :max="240" :step="15" show-stops />
          <span class="duration-text">{{ form.duration }} 分钟</span>
        </el-form-item>

        <el-form-item label="会议地点">
          <el-input v-model="form.location" placeholder="请输入会议地点或线上会议链接" />
        </el-form-item>

        <el-form-item label="参会人员">
          <el-select
            v-model="form.participants"
            multiple
            placeholder="请选择参会人员"
            style="width: 100%"
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
            style="width: 100%"
          >
            <el-icon :size="40"><Plus /></el-icon>
            <div class="el-upload__text">
              拖拽文件到此处或 <em>点击上传</em>
            </div>
            <template #tip>
              <div class="el-upload__tip">支持上传会议相关的文档、PPT等</div>
            </template>
          </el-upload>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSubmit">创建会议</el-button>
          <el-button @click="handleCancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<style scoped lang="scss">
.meeting-create {
  .form-card {
    margin-top: 20px;

    .duration-text {
      margin-left: 12px;
      color: #666;
    }
  }
}
</style>
