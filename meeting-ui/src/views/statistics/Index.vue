<script setup lang="ts">
import { ref } from 'vue'
import { Calendar, Timer, User, Document } from '@element-plus/icons-vue'

const statsOverview = ref([
  { label: '会议总数', value: '24', unit: '场', change: '+20%', icon: Calendar, theme: 'blue' },
  { label: '会议总时长', value: '48.5', unit: '小时', change: '+12%', icon: Timer, theme: 'amber' },
  { label: '参会人次', value: '156', unit: '人次', change: '+8%', icon: User, theme: 'green' },
  { label: 'AI 生成纪要', value: '24', unit: '份', change: '+20%', icon: Document, theme: 'purple' }
])

const meetingTypeData = ref([
  { name: '需求评审', value: 35, theme: 'blue' },
  { name: '技术讨论', value: 28, theme: 'amber' },
  { name: '总结汇报', value: 20, theme: 'green' },
  { name: '敏捷开发', value: 17, theme: 'purple' }
])

const deptData = ref([
  { name: '产品部', value: 85 },
  { name: '技术部', value: 72 },
  { name: '设计部', value: 58 },
  { name: '运营部', value: 45 },
  { name: '市场部', value: 32 }
])

const durationData = ref([
  { name: '0-30分钟', value: 30, theme: 'blue' },
  { name: '30-60分钟', value: 40, theme: 'green' },
  { name: '60-90分钟', value: 20, theme: 'amber' },
  { name: '90分钟以上', value: 10, theme: 'red' }
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
      <div v-for="item in statsOverview" :key="item.label" class="stat-card ds-card ds-card--pad-lg ds-card--flat">
        <div class="stat-icon" :class="`stat-icon--${item.theme}`">
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
      <div class="chart-card ds-card ds-card--pad-lg ds-card--flat">
        <div class="section-head">
          <h3 class="section-head__title">会议类型分布</h3>
        </div>
        <div class="donut-chart">
          <svg viewBox="0 0 120 120" class="donut-svg">
            <circle cx="60" cy="60" r="45" fill="none" class="donut-track" stroke-width="16" />
            <circle
              cx="60" cy="60" r="45" fill="none"
              class="donut-segment donut-segment--blue"
              stroke-width="16"
              stroke-dasharray="99 283"
              stroke-dashoffset="0"
              transform="rotate(-90 60 60)"
            />
            <circle
              cx="60" cy="60" r="45" fill="none"
              class="donut-segment donut-segment--amber"
              stroke-width="16"
              stroke-dasharray="79 283"
              stroke-dashoffset="-99"
              transform="rotate(-90 60 60)"
            />
            <circle
              cx="60" cy="60" r="45" fill="none"
              class="donut-segment donut-segment--green"
              stroke-width="16"
              stroke-dasharray="57 283"
              stroke-dashoffset="-178"
              transform="rotate(-90 60 60)"
            />
            <circle
              cx="60" cy="60" r="45" fill="none"
              class="donut-segment donut-segment--purple"
              stroke-width="16"
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
            <span class="dot" :class="`dot--${item.theme}`" />
            <span>{{ item.name }}</span>
            <span class="legend-val">{{ item.value }}%</span>
          </div>
        </div>
      </div>

      <div class="chart-card ds-card ds-card--pad-lg ds-card--flat">
        <div class="section-head">
          <h3 class="section-head__title">会议趋势</h3>
        </div>
        <div class="line-chart-area">
          <svg viewBox="0 0 400 180" class="line-svg" preserveAspectRatio="none">
            <!-- 水平网格线 -->
            <line v-for="i in 4" :key="'g'+i" x1="0" :y1="i * 36" x2="400" :y2="i * 36"
              stroke="#f3f4f6" stroke-width="1" />
            <!-- 会议数折线 -->
            <polyline
              :points="trendMeetings.map((v, i) => `${i * 80 + 40},${180 - v * 5}`).join(' ')"
              fill="none" class="trend-line trend-line--meetings" stroke-width="2.5"
              stroke-linejoin="round" stroke-linecap="round"
            />
            <!-- 参会人次折线 -->
            <polyline
              :points="trendParticipants.map((v, i) => `${i * 80 + 40},${180 - v * 1.3}`).join(' ')"
              fill="none" class="trend-line trend-line--participants" stroke-width="2.5"
              stroke-dasharray="6 4" stroke-linejoin="round" stroke-linecap="round"
            />
            <!-- 会议数数据点 -->
            <circle v-for="(v, i) in trendMeetings" :key="'m'+i"
              :cx="i * 80 + 40" :cy="180 - v * 5" r="4"
              class="trend-dot trend-dot--meetings" />
            <!-- 参会人次数据点 -->
            <circle v-for="(v, i) in trendParticipants" :key="'p'+i"
              :cx="i * 80 + 40" :cy="180 - v * 1.3" r="4"
              class="trend-dot trend-dot--participants" />
          </svg>
          <div class="x-labels">
            <span v-for="m in trendMonths" :key="m">{{ m }}</span>
          </div>
        </div>
        <div class="chart-legend horizontal">
          <span class="legend-item"><span class="dot dot--blue" /> 会议数</span>
          <span class="legend-item"><span class="dot dot--green" /> 参会人次</span>
        </div>
      </div>
    </div>

    <!-- 图表行 2 -->
    <div class="charts-row three-col">
      <div class="chart-card ds-card ds-card--pad-lg ds-card--flat">
        <div class="section-head">
          <h3 class="section-head__title">部门会议 TOP5</h3>
        </div>
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

      <div class="chart-card ds-card ds-card--pad-lg ds-card--flat">
        <div class="section-head">
          <h3 class="section-head__title">会议时长分布</h3>
        </div>
        <div class="chart-legend duration-legend">
          <div v-for="item in durationData" :key="item.name" class="legend-item">
            <span class="dot" :class="`dot--${item.theme}`" />
            <span>{{ item.name }}</span>
            <span class="legend-val">{{ item.value }}%</span>
          </div>
        </div>
        <div class="duration-bars">
          <div
            v-for="item in durationData"
            :key="item.name"
            class="duration-segment"
            :class="`duration-segment--${item.theme}`"
            :style="{ width: item.value + '%' }"
          />
        </div>
      </div>

      <div class="chart-card ds-card ds-card--pad-lg ds-card--flat ai-stats">
        <div class="section-head">
          <h3 class="section-head__title">AI 使用情况</h3>
        </div>
        <div class="ai-stat-item">
          <p class="ai-stat-label">算法使用率</p>
          <div class="ai-gauge">
            <el-progress type="dashboard" :percentage="92" :width="100" :color="$primary-color" />
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
// ---- 颜色主题映射 ----
$theme-blue: $primary-color;
$theme-blue-bg: #e8efff;
$theme-amber: $warning-color;
$theme-amber-bg: #fef3c7;
$theme-green: $success-color;
$theme-green-bg: #dcfce7;
$theme-purple: #8b5cf6;
$theme-purple-bg: #ede9fe;
$theme-red: $danger-color;
$theme-red-bg: #fef2f2;

// ---- 指标卡片 ----
.stats-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: $space-5;
  margin-bottom: $space-5;

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

  .stat-icon {
    width: 44px;
    height: 44px;
    border-radius: $radius-md;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;

    &--blue   { background: $theme-blue-bg;   color: $theme-blue; }
    &--amber  { background: $theme-amber-bg;  color: $theme-amber; }
    &--green  { background: $theme-green-bg;  color: $theme-green; }
    &--purple { background: $theme-purple-bg; color: $theme-purple; }
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
    margin: 0 0 $space-1;
    line-height: $line-height-tight;

    .unit {
      font-size: $font-size-sm;
      font-weight: $font-weight-regular;
      color: $text-secondary;
      margin-left: $space-1;
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

// ---- 图表行 ----
.charts-row {
  display: grid;
  grid-template-columns: 1fr;
  gap: $space-5;
  margin-bottom: $space-5;

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
  min-width: 0;
}

// ---- 甜甜圈图 ----
.donut-chart {
  position: relative;
  width: 140px;
  height: 140px;
  margin: 0 auto $space-5;

  .donut-svg {
    width: 100%;
    height: 100%;
  }

  .donut-track {
    stroke: $border-light;
  }

  .donut-segment {
    &--blue   { stroke: $theme-blue; }
    &--amber  { stroke: $theme-amber; }
    &--green  { stroke: $theme-green; }
    &--purple { stroke: $theme-purple; }
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
      line-height: $line-height-tight;
    }

    .donut-label {
      font-size: $font-size-xs;
      color: $text-secondary;
    }
  }
}

// ---- 图例 ----
.chart-legend {
  display: flex;
  flex-direction: column;
  gap: $space-3;

  &.horizontal {
    flex-direction: row;
    justify-content: center;
    gap: $space-5;
    margin-top: $space-3;
  }

  .legend-item {
    display: flex;
    align-items: center;
    gap: $space-2;
    font-size: $font-size-sm;
    color: $text-regular;
  }
}

.dot {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  flex-shrink: 0;

  &--blue   { background: $theme-blue; }
  &--amber  { background: $theme-amber; }
  &--green  { background: $theme-green; }
  &--purple { background: $theme-purple; }
  &--red    { background: $theme-red; }
}

.legend-val {
  margin-left: auto;
  font-weight: $font-weight-semibold;
  color: $text-primary;
  font-family: $font-family-num;
}

// ---- 趋势折线图 ----
.line-chart-area {
  height: 200px;
  position: relative;

  .line-svg {
    width: 100%;
    height: calc(100% - 28px);
  }

  .trend-line {
    &--meetings    { stroke: $theme-blue; }
    &--participants { stroke: $theme-green; }
  }

  .trend-dot {
    stroke-width: 2;

    &--meetings {
      fill: $bg-white;
      stroke: $theme-blue;
    }

    &--participants {
      fill: $bg-white;
      stroke: $theme-green;
    }
  }

  .x-labels {
    display: flex;
    justify-content: space-between;
    padding: 0 $space-5;
    font-size: $font-size-xs;
    color: $text-secondary;
  }
}

// ---- 柱状图 ----
.bar-chart {
  .bar-row {
    display: flex;
    align-items: center;
    gap: $space-3;
    margin-bottom: $space-4;

    &:last-child {
      margin-bottom: 0;
    }

    .bar-label {
      width: 56px;
      font-size: $font-size-sm;
      color: $text-regular;
      flex-shrink: 0;
    }

    .bar-track {
      flex: 1;
      height: 8px;
      background: $border-light;
      border-radius: $radius-sm;
      overflow: hidden;

      .bar-fill {
        height: 100%;
        background: $primary-color;
        border-radius: $radius-sm;
        transition: width 0.5s ease-out;
      }
    }

    .bar-val {
      min-width: 28px;
      font-size: $font-size-sm;
      font-weight: $font-weight-semibold;
      font-family: $font-family-num;
      color: $text-primary;
      text-align: right;
    }
  }
}

// ---- 时长分布 ----
.duration-legend {
  margin-bottom: $space-4;
}

.duration-bars {
  display: flex;
  height: 12px;
  border-radius: $radius-md;
  overflow: hidden;

  .duration-segment {
    &--blue   { background: $theme-blue; }
    &--amber  { background: $theme-amber; }
    &--green  { background: $theme-green; }
    &--red    { background: $theme-red; }
  }
}

// ---- AI 统计 ----
.ai-stats {
  .ai-stat-item {
    text-align: center;
    padding: $space-4 0;

    &:first-child {
      padding-top: 0;
    }

    .ai-stat-label {
      font-size: $font-size-sm;
      color: $text-secondary;
      margin: 0 0 $space-3;
    }

    .ai-stat-num {
      font-size: $font-size-2xl;
      font-weight: $font-weight-bold;
      font-family: $font-family-num;
      color: $primary-color;
      margin: 0;

      span {
        font-size: $font-size-base;
        font-weight: $font-weight-regular;
        color: $text-secondary;
        margin-left: $space-1;
      }
    }
  }
}

.statistics-page.page-container {
  max-width: 90% !important;
  margin: 0 auto;
  width: 100%;

  @media (max-width: #{$bp-md - 1px}) {
    max-width: 100% !important;
  }
}
</style>
