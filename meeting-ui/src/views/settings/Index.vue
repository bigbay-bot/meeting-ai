<script setup lang="ts">
import { ref } from 'vue'
import { Plus, Rank } from '@element-plus/icons-vue'

const activeTab = ref('template')

const templates = ref([
  {
    id: '1',
    name: '标准会议纪要',
    active: true,
    sections: ['1. 会议基本信息', '2. 参会人员', '3. 会议议程', '4. 讨论要点', '5. 决策事项', '6. 行动项']
  },
  {
    id: '2',
    name: '需求评审模板',
    active: false,
    sections: ['1. 需求背景', '2. 评审范围', '3. 需求详情', '4. 评审意见', '5. 修改建议', '6. 结论']
  },
  {
    id: '3',
    name: '技术研讨模板',
    active: false,
    sections: ['1. 技术背景', '2. 方案对比', '3. 技术细节', '4. 风险评估', '5. 实施计划', '6. 待决事项']
  },
  {
    id: '4',
    name: '周会汇报模板',
    active: false,
    sections: ['1. 本周进展', '2. 关键成果', '3. 遇到问题', '4. 下周计划', '5. 需要支持']
  }
])

const modules = ref([
  { name: '最近会议', enabled: true },
  { name: '待办事项', enabled: true },
  { name: 'AI 复盘助手', enabled: true },
  { name: '进行中会议', enabled: true },
  { name: '快捷入口', enabled: false },
  { name: '数据概览', enabled: false }
])

const saveLoading = ref(false)

const handleSave = async () => {
  saveLoading.value = true
  await new Promise((r) => setTimeout(r, 800))
  saveLoading.value = false
}

const handleReset = () => {
  modules.value.forEach((m) => (m.enabled = true))
}
</script>

<template>
  <div class="settings-page page-container">
    <div class="page-header">
      <h1 class="page-title">设置中心</h1>
    </div>

    <el-tabs v-model="activeTab" class="settings-tabs">
      <el-tab-pane label="模板设置" name="template">
        <div class="template-section">
          <div class="section-toolbar">
            <h3>纪要模板设置</h3>
            <el-button type="primary" :icon="Plus" size="small">新建模板</el-button>
          </div>

          <div class="template-grid">
            <div
              v-for="tpl in templates"
              :key="tpl.id"
              class="template-card ds-card"
              :class="{ active: tpl.active }"
            >
              <div class="tpl-header">
                <span class="tpl-name">{{ tpl.name }}</span>
                <el-tag v-if="tpl.active" type="primary" size="small">使用中</el-tag>
              </div>
              <ul class="tpl-sections">
                <li v-for="sec in tpl.sections" :key="sec">{{ sec }}</li>
              </ul>
              <div class="tpl-actions">
                <el-button link type="primary" size="small">编辑</el-button>
                <el-button v-if="!tpl.active" link size="small">设为默认</el-button>
              </div>
            </div>
          </div>
        </div>

        <div class="module-section">
          <h3>首页功能模块设置</h3>
          <div class="module-list ds-card">
            <div v-for="mod in modules" :key="mod.name" class="module-item">
              <el-icon class="drag-handle"><Rank /></el-icon>
              <span class="module-name">{{ mod.name }}</span>
              <el-switch v-model="mod.enabled" />
            </div>
          </div>
        </div>

        <div class="settings-footer">
          <el-button @click="handleReset">恢复默认</el-button>
          <el-button type="primary" :loading="saveLoading" @click="handleSave">保存设置</el-button>
        </div>
      </el-tab-pane>

      <el-tab-pane label="功能模块" name="modules">
        <div class="empty-tab ds-card">
          <p>功能模块配置开发中...</p>
        </div>
      </el-tab-pane>

      <el-tab-pane label="成员管理" name="members">
        <div class="empty-tab ds-card">
          <p>成员管理功能开发中...</p>
        </div>
      </el-tab-pane>

      <el-tab-pane label="集成设置" name="integration">
        <div class="empty-tab ds-card">
          <p>第三方集成配置开发中...</p>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<style scoped lang="scss">
.settings-page {
  max-width: 1200px;
}

.settings-tabs {
  :deep(.el-tabs__header) {
    margin-bottom: $spacing-lg;
  }

  :deep(.el-tabs__item) {
    font-size: 15px;
    padding: 0 20px;
  }
}

.template-section {
  margin-bottom: $spacing-xl;

  .section-toolbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: $spacing-md;

    h3 {
      font-size: $font-size-md;
      font-weight: $font-weight-semibold;
      margin: 0;
      color: $text-title;
      line-height: $line-height-tight;
    }
  }
}

.template-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: $spacing-md;

  @include respond-to(min-md) {
    grid-template-columns: repeat(2, 1fr);
  }

  @include respond-to(min-xl) {
    grid-template-columns: repeat(4, 1fr);
  }
}

.template-card {
  padding: 16px;
  cursor: pointer;
  transition: border-color 0.2s, box-shadow 0.2s;

  &.active {
    border-color: $primary-color !important;
    box-shadow: $shadow-hover !important;
  }

  .tpl-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 12px;

    .tpl-name {
      font-size: $font-size-base;
      font-weight: $font-weight-semibold;
      color: $text-title;
    }
  }

  .tpl-sections {
    list-style: none;
    padding: 0;
    margin: 0 0 12px;

    li {
      font-size: $font-size-xs;
      color: $text-secondary;
      line-height: $line-height-relaxed;
    }
  }

  .tpl-actions {
    display: flex;
    gap: 8px;
    border-top: 1px solid $border-lighter;
    padding-top: 10px;
  }
}

.module-section {
  h3 {
    font-size: 16px;
    font-weight: 600;
    margin: 0 0 $spacing-md;
    color: $text-primary;
  }
}

.module-list {
  padding: 8px 0;

  .module-item {
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 14px 20px;
    border-bottom: 1px solid $border-lighter;

    &:last-child {
      border-bottom: none;
    }

    .drag-handle {
      color: $text-placeholder;
      cursor: grab;
    }

    .module-name {
      flex: 1;
      font-size: 14px;
      color: $text-primary;
    }
  }
}

.settings-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: $spacing-xl;
  padding-top: $spacing-lg;
  border-top: 1px solid $border-light;
  flex-wrap: wrap;

  @include respond-to(sm) {
    .el-button {
      flex: 1;
    }
  }
}

.empty-tab {
  padding: 60px;
  text-align: center;
  color: $text-secondary;
  font-size: 14px;
}

.settings-page.page-container {
  max-width: 80% !important;
  margin: 0 auto;
  width: 100%;

  @media (max-width: #{$bp-md - 1px}) {
    max-width: 100% !important;
  }
}
</style>
