<script setup lang="ts">
interface Props {
  visible: boolean
  title?: string
  width?: string
  confirmLoading?: boolean
}

withDefaults(defineProps<Props>(), {
  title: '提示',
  width: '500px',
  confirmLoading: false
})

const emit = defineEmits<{
  'update:visible': [value: boolean]
  confirm: []
  cancel: []
}>()

const handleClose = () => {
  emit('update:visible', false)
  emit('cancel')
}

const handleConfirm = () => {
  emit('confirm')
}
</script>

<template>
  <el-dialog
    :model-value="visible"
    :title="title"
    :width="width"
    :close-on-click-modal="false"
    @update:model-value="emit('update:visible', $event)"
    @close="handleClose"
  >
    <slot />
    <template #footer>
      <slot name="footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" :loading="confirmLoading" @click="handleConfirm">确认</el-button>
      </slot>
    </template>
  </el-dialog>
</template>
