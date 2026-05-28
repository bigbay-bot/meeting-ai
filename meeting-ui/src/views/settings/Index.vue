<script setup lang="ts">
import { ref } from 'vue'
import { Plus, Rank } from '@element-plus/icons-vue'

const activeTab = ref('template')

const settingsTabs = [
  { label: '模板设置', name: 'template' },
  { label: '功能模块', name: 'modules' },
  { label: '成员管理', name: 'members' },
  { label: '集成设置', name: 'integration' }
]

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

    <div class="section-head">
      <span />
      <nav class="tab-bar">
        <button
          v-for="tab in settingsTabs"
          :key="tab.name"
          type="button"
          class="tab-bar__btn"
          :class="{ 'is-active': activeTab === tab.name }"
          @click="activeTab = tab.name"
        >
          {{ tab.label }}
        </button>
      </nav>
    </div>

    <!-- 模板设置 -->
    <div v-if="activeTab === 'template'">
      <div class="template-section">
        <div class="section-toolbar">
          <h2 class="h3">纪要模板设置</h2>
          <button type="button" class="ds-btn ds-btn--primary ds-btn--sm">
            <el-icon :size="14"><Plus /></el-icon>
            新建模板
          </button>
        </div>

        <div class="template-grid">
          <div
            v-for="tpl in templates"
            :key="tpl.id"
            class="template-card ds-card ds-card--flat"
            :class="{ active: tpl.active }"
          >
            <div class="tpl-header">
              <span class="tpl-name">{{ tpl.name }}</span>
              <span v-if="tpl.active" class="ds-tag ds-tag--success">使用中</span>
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
        <h2 class="h3">首页功能模块设置</h2>
        <div class="module-list ds-card ds-card--pad-lg ds-card--flat">
          <div v-for="mod in modules" :key="mod.name" class="module-item">
            <el-icon class="drag-handle"><Rank /></el-icon>
            <span class="module-name">{{ mod.name }}</span>
            <el-switch v-model="mod.enabled" />
          </div>
        </div>
      </div>

      <div class="settings-footer">
        <button type="button" class="ds-btn ds-btn--ghost" @click="handleReset">恢复默认</button>
        <button type="button" class="ds-btn ds-btn--primary" :disabled="saveLoading" @click="handleSave">
          {{ saveLoading ? '保存中...' : '保存设置' }}
        </button>
      </div>
    </div>

    <!-- 功能模块 -->
    <div v-if="activeTab === 'modules'">
      <div class="ds-card ds-card--pad-lg ds-card--flat empty-tab">
        <p>功能模块配置开发中...</p>
      </div>
    </div>

    <!-- 成员管理 -->
    <div v-if="activeTab === 'members'">
      <div class="ds-card ds-card--pad-lg ds-card--flat empty-tab">
        <p>成员管理功能开发中...</p>
      </div>
    </div>

    <!-- 集成设置 -->
    <div v-if="activeTab === 'integration'">
      <div class="ds-card ds-card--pad-lg ds-card--flat empty-tab">
        <p>第三方集成配置开发中...</p>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.template-section {
  margin-bottom: $space-6;

  .section-toolbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: $space-5;

    h2 {
      margin: 0;
    }
  }
}

.template-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: $space-4;

  @include respond-to(min-md) {
    grid-template-columns: repeat(2, 1fr);
  }

  @include respond-to(min-xl) {
    grid-template-columns: repeat(4, 1fr);
  }
}

.template-card {
  padding: $space-4;
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
    margin-bottom: $space-3;

    .tpl-name {
      font-size: $font-size-base;
      font-weight: $font-weight-semibold;
      color: $text-title;
    }
  }

  .tpl-sections {
    list-style: none;
    padding: 0;
    margin: 0 0 $space-3;

    li {
      font-size: $font-size-xs;
      color: $text-secondary;
      line-height: $line-height-relaxed;
    }
  }

  .tpl-actions {
    display: flex;
    gap: $space-2;
    border-top: 1px solid $border-light;
    padding-top: $space-3;
  }
}

.module-section {
  margin-bottom: $space-6;

  h2 {
    margin: 0 0 $space-5;
  }
}

.module-list {
  .module-item {
    display: flex;
    align-items: center;
    gap: $space-3;
    padding: $space-4 $space-5;
    border-bottom: 1px solid $border-light;

    &:last-child {
      border-bottom: none;
    }

    .drag-handle {
      color: $text-placeholder;
      cursor: grab;
    }

    .module-name {
      flex: 1;
      font-size: $font-size-base;
      color: $text-primary;
    }
  }
}

.settings-footer {
  display: flex;
  justify-content: flex-end;
  gap: $space-3;
  padding-top: $space-5;
  border-top: 1px solid $border-light;

  @include respond-to(sm) {
    .ds-btn {
      flex: 1;
    }
  }
}

.empty-tab {
  text-align: center;
  color: $text-secondary;
  font-size: $font-size-base;
}

.settings-page.page-container {
  max-width: 90% !important;
  margin: 0 auto;
  width: 100%;

  @media (max-width: #{$bp-md - 1px}) {
    max-width: 100% !important;
  }
}
</style>
