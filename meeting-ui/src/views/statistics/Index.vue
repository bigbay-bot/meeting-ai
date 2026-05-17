<script setup lang="ts">
import { ref } from 'vue'
import { Calendar, Timer, User, Document } from '@element-plus/icons-vue'

const statsOverview = ref([
  { label: '会议总数', value: '24', unit: '场', change: '+20%', icon: Calendar, color: '#4D7CFE', bg: '#E8EFFF' },
  { label: '会议总时长', value: '48.5', unit: '小时', change: '+12%', icon: Timer, color: '#F59E0B', bg: '#FEF3C7' },
  { label: '参会人次', value: '156', unit: '人次', change: '+8%', icon: User, color: '#22C55E', bg: '#DCFCE7' },
  { label: 'AI 生成纪要', value: '24', unit: '份', change: '+20%', icon: Document, color: '#8B5CF6', bg: '#EDE9FE' }
])

const meetingTypeData = ref([
  { name: '需求评审', value: 35, color: '#4D7CFE' },
  { name: '技术讨论', value: 28, color: '#F59E0B' },
  { name: '总结汇报', value: 20, color: '#22C55E' },
  { name: '敏捷开发', value: 17, color: '#8B5CF6' }
])

const deptData = ref([
  { name: '产品部', value: 85 },
  { name: '技术部', value: 72 },
  { name: '设计部', value: 58 },
  { name: '运营部', value: 45 },
  { name: '市场部', value: 32 }
])

const durationData = ref([
  { name: '0-30分钟', value: 30, color: '#4D7CFE' },
  { name: '30-60分钟', value: 40, color: '#22C55E' },
  { name: '60-90分钟', value: 20, color: '#F59E0B' },
  { name: '90分钟以上', value: 10, color: '#EF4444' }
])

const trendMonths = ['1月', '2月', '3月', '4月', '5月', '6月']
const trendMeetings = [12, 15, 18, 20, 22, 24]
const trendParticipants = [45, 52, 68, 80, 95, 110]
</script>

<template>
  <div class="statistics-page page-container">
    <div class="page-header">
      <h1 class="page-title">数据看板</h1>
    </div>

    <!-- 指标卡片 -->
    <div class="stats-grid">
      <div v-for="item in statsOverview" :key="item.label" class="stat-card ds-card">
        <div class="stat-icon" :style="{ background: item.bg, color: item.color }">
          <el-icon :size="22"><component :is="item.icon" /></el-icon>
        </div>
        <div class="stat-body">
          <p class="stat-label">{{ item.label }}</p>
          <p class="stat-value">
            {{ item.value }}<span class="unit">{{ item.unit }}</span>
          </p>
          <p class="stat-change up">较上月 {{ item.change }}</p>
        </div>
      </div>
    </div>

    <!-- 图表行 1 -->
    <div class="charts-row">
      <div class="chart-card ds-card">
        <h3 class="chart-title">会议类型分布</h3>
        <div class="donut-chart">
          <svg viewBox="0 0 120 120" class="donut-svg">
            <circle cx="60" cy="60" r="45" fill="none" stroke="#E8EFFF" stroke-width="16" />
            <circle
              cx="60" cy="60" r="45" fill="none"
              stroke="#4D7CFE" stroke-width="16"
              stroke-dasharray="99 283"
              stroke-dashoffset="0"
              transform="rotate(-90 60 60)"
            />
            <circle
              cx="60" cy="60" r="45" fill="none"
              stroke="#F59E0B" stroke-width="16"
              stroke-dasharray="79 283"
              stroke-dashoffset="-99"
              transform="rotate(-90 60 60)"
            />
            <circle
              cx="60" cy="60" r="45" fill="none"
              stroke="#22C55E" stroke-width="16"
              stroke-dasharray="57 283"
              stroke-dashoffset="-178"
              transform="rotate(-90 60 60)"
            />
            <circle
              cx="60" cy="60" r="45" fill="none"
              stroke="#8B5CF6" stroke-width="16"
              stroke-dasharray="48 283"
              stroke-dashoffset="-235"
              transform="rotate(-90 60 60)"
            />
          </svg>
          <div class="donut-center">
            <span class="donut-total">24</span>
            <span class="donut-label">会议总数</span>
          </div>
        </div>
        <div class="chart-legend">
          <div v-for="item in meetingTypeData" :key="item.name" class="legend-item">
            <span class="dot" :style="{ background: item.color }" />
            <span>{{ item.name }}</span>
            <span class="legend-val">{{ item.value }}%</span>
          </div>
        </div>
      </div>

      <div class="chart-card ds-card">
        <h3 class="chart-title">会议趋势</h3>
        <div class="line-chart-area">
          <svg viewBox="0 0 400 160" class="line-svg" preserveAspectRatio="none">
            <polyline
              :points="trendMeetings.map((v, i) => `${i * 80 + 40},${160 - v * 5}`).join(' ')"
              fill="none" stroke="#4D7CFE" stroke-width="2.5"
            />
            <polyline
              :points="trendParticipants.map((v, i) => `${i * 80 + 40},${160 - v * 1.2}`).join(' ')"
              fill="none" stroke="#22C55E" stroke-width="2.5" stroke-dasharray="6 4"
            />
          </svg>
          <div class="x-labels">
            <span v-for="m in trendMonths" :key="m">{{ m }}</span>
          </div>
        </div>
        <div class="chart-legend horizontal">
          <span><span class="dot" style="background:#4D7CFE" /> 会议数</span>
          <span><span class="dot" style="background:#22C55E" /> 参会人次</span>
        </div>
      </div>
    </div>

    <!-- 图表行 2 -->
    <div class="charts-row three-col">
      <div class="chart-card ds-card">
        <h3 class="chart-title">部门会议 TOP5</h3>
        <div class="bar-chart">
          <div v-for="item in deptData" :key="item.name" class="bar-row">
            <span class="bar-label">{{ item.name }}</span>
            <div class="bar-track">
              <div class="bar-fill" :style="{ width: item.value + '%' }" />
            </div>
            <span class="bar-val">{{ item.value }}</span>
          </div>
        </div>
      </div>

      <div class="chart-card ds-card">
        <h3 class="chart-title">会议时长分布</h3>
        <div class="duration-legend">
          <div v-for="item in durationData" :key="item.name" class="legend-item">
            <span class="dot" :style="{ background: item.color }" />
            <span>{{ item.name }}</span>
            <span class="legend-val">{{ item.value }}%</span>
          </div>
        </div>
        <div class="duration-bars">
          <div
            v-for="item in durationData"
            :key="item.name"
            class="duration-segment"
            :style="{ width: item.value + '%', background: item.color }"
          />
        </div>
      </div>

      <div class="chart-card ds-card ai-stats">
        <h3 class="chart-title">AI 使用情况</h3>
        <div class="ai-stat-item">
          <p class="ai-stat-label">算法使用率</p>
          <div class="ai-gauge">
            <el-progress type="dashboard" :percentage="92" :width="100" color="#4D7CFE" />
          </div>
        </div>
        <div class="ai-stat-item">
          <p class="ai-stat-label">AI 提问次数</p>
          <p class="ai-stat-num">166<span>次</span></p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.stats-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: $spacing-md;
  margin-bottom: $spacing-md;

  @include respond-to(min-md) {
    grid-template-columns: repeat(2, 1fr);
  }

  @include respond-to(min-lg) {
    grid-template-columns: repeat(4, 1fr);
  }
}

.stat-card {
  display: flex;
  align-items: flex-start;
  gap: $space-4;
  padding: $space-5;

  @include respond-to(min-md) {
    padding: $space-5;
  }

  .stat-icon {
    width: 44px;
    height: 44px;
    border-radius: $radius-md;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;
  }

  .stat-label {
    font-size: $font-size-sm;
    color: $text-secondary;
    margin: 0 0 $space-2;
    line-height: $line-height-base;
  }

  .stat-value {
    font-size: $font-size-2xl;
    font-weight: $font-weight-semibold;
    font-family: $font-family-num;
    color: $text-title;
    margin: 0 0 4px;
    line-height: $line-height-tight;

    .unit {
      font-size: $font-size-sm;
      font-weight: $font-weight-regular;
      color: $text-secondary;
      margin-left: 2px;
    }
  }

  .stat-change {
    font-size: $font-size-xs;
    margin: 0;
    line-height: $line-height-base;

    &.up {
      color: $success-color;
    }
  }
}

.charts-row {
  display: grid;
  grid-template-columns: 1fr;
  gap: $spacing-md;
  margin-bottom: $spacing-md;

  @include respond-to(min-lg) {
    grid-template-columns: minmax(0, 1fr) minmax(0, 1.6fr);
  }

  &.three-col {
    @include respond-to(min-md) {
      grid-template-columns: repeat(2, 1fr);
    }

    @include respond-to(min-lg) {
      grid-template-columns: minmax(0, 1.2fr) minmax(0, 1fr) minmax(0, 0.85fr);
    }
  }
}

.chart-card {
  padding: 18px 20px;
  min-width: 0;

  @include respond-to(min-md) {
    padding: 20px 22px;
  }

  .chart-title {
    font-size: $font-size-md;
    font-weight: $font-weight-semibold;
    color: $text-title;
    margin: 0 0 18px;
    line-height: $line-height-tight;
  }
}

.donut-chart {
  position: relative;
  width: 140px;
  height: 140px;
  margin: 0 auto 20px;

  .donut-svg {
    width: 100%;
    height: 100%;
  }

  .donut-center {
    position: absolute;
    inset: 0;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;

    .donut-total {
      font-size: $font-size-2xl;
      font-weight: $font-weight-semibold;
      font-family: $font-family-num;
      color: $text-title;
    }

    .donut-label {
      font-size: $font-size-xs;
      color: $text-secondary;
    }
  }
}

.chart-legend {
  display: flex;
  flex-direction: column;
  gap: 10px;

  &.horizontal {
    flex-direction: row;
    justify-content: center;
    gap: 24px;
    margin-top: 12px;
    font-size: 13px;
    color: $text-regular;
  }

  .legend-item {
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 13px;
    color: $text-regular;

    .dot {
      width: 10px;
      height: 10px;
      border-radius: 50%;
      flex-shrink: 0;
    }

    .legend-val {
      margin-left: auto;
      font-weight: 600;
      color: $text-primary;
    }
  }
}

.line-chart-area {
  height: 180px;
  position: relative;

  .line-svg {
    width: 100%;
    height: calc(100% - 24px);
  }

  .x-labels {
    display: flex;
    justify-content: space-between;
    padding: 0 20px;
    font-size: 12px;
    color: $text-secondary;
  }
}

.bar-chart {
  .bar-row {
    display: flex;
    align-items: center;
    gap: 10px;
    margin-bottom: 14px;

    .bar-label {
      width: 56px;
      font-size: 13px;
      color: $text-regular;
      flex-shrink: 0;
    }

    .bar-track {
      flex: 1;
      height: 8px;
      background: $border-lighter;
      border-radius: 4px;
      overflow: hidden;

      .bar-fill {
        height: 100%;
        background: linear-gradient(90deg, #4d7cfe, #7a9dfe);
        border-radius: 4px;
        transition: width 0.5s;
      }
    }

    .bar-val {
      width: 28px;
      font-size: 13px;
      font-weight: 600;
      color: $text-primary;
      text-align: right;
    }
  }
}

.duration-bars {
  display: flex;
  height: 12px;
  border-radius: 6px;
  overflow: hidden;
  margin-top: 16px;
}

.duration-legend {
  margin-top: 8px;
}

.ai-stats {
  .ai-stat-item {
    text-align: center;
    padding: 12px 0;

    .ai-stat-label {
      font-size: 13px;
      color: $text-secondary;
      margin: 0 0 8px;
    }

    .ai-stat-num {
      font-size: 36px;
      font-weight: 700;
      color: $primary-color;
      margin: 0;

      span {
        font-size: 14px;
        font-weight: 400;
        color: $text-secondary;
        margin-left: 4px;
      }
    }
  }
}

.statistics-page.page-container {
  max-width: 80% !important;
  margin: 0 auto;
  width: 100%;

  @media (max-width: #{$bp-md - 1px}) {
    max-width: 100% !important;
  }
}
</style>
