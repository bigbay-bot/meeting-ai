<script setup lang="ts">
import { ref } from 'vue'

const statsOverview = ref([
  { label: '本月会议数', value: 42, unit: '场', change: '+12%' },
  { label: '纪要生成数', value: 38, unit: '份', change: '+8%' },
  { label: '平均会议时长', value: 95, unit: '分钟', change: '-5%' },
  { label: '任务完成率', value: 87, unit: '%', change: '+3%' }
])

const meetingTypeData = ref([
  { value: 15, name: '需求评审' },
  { value: 12, name: '技术讨论' },
  { value: 8, name: '总结汇报' },
  { value: 7, name: '敏捷开发' }
])

const monthlyData = ref({
  months: ['1月', '2月', '3月', '4月', '5月'],
  meetings: [28, 32, 35, 38, 42],
  minutes: [25, 28, 32, 35, 38]
})
</script>

<template>
  <div class="statistics">
    <el-row :gutter="16" class="overview-row">
      <el-col :span="6" v-for="item in statsOverview" :key="item.label">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-label">{{ item.label }}</div>
          <div class="stat-value">
            {{ item.value }}<span class="unit">{{ item.unit }}</span>
          </div>
          <div class="stat-change" :class="item.change.startsWith('+') ? 'up' : 'down'">
            {{ item.change }} 环比
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16" class="chart-row">
      <el-col :span="12">
        <el-card shadow="never">
          <template #header>
            <div class="card-header">会议类型分布</div>
          </template>
          <div class="chart-placeholder pie-chart">
            <div class="mock-pie">
              <div
                v-for="(item, index) in meetingTypeData"
                :key="item.name"
                class="pie-legend"
              >
                <span class="dot" :style="{ background: ['#4f46e5', '#10b981', '#f59e0b', '#ef4444'][index] }" />
                <span>{{ item.name }}: {{ item.value }}场</span>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="12">
        <el-card shadow="never">
          <template #header>
            <div class="card-header">月度趋势</div>
          </template>
          <div class="chart-placeholder line-chart">
            <div v-for="(month, index) in monthlyData.months" :key="month" class="bar-item">
              <div class="bar-group">
                <div
                  class="bar meeting"
                  :style="{ height: `${monthlyData.meetings[index] * 3}px` }"
                />
                <div
                  class="bar minutes"
                  :style="{ height: `${monthlyData.minutes[index] * 3}px` }"
                />
              </div>
              <span class="month-label">{{ month }}</span>
            </div>
          </div>
          <div class="legend">
            <span><span class="dot meeting-dot" /> 会议数</span>
            <span><span class="dot minutes-dot" /> 纪要数</span>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16" class="chart-row">
      <el-col :span="24">
        <el-card shadow="never">
          <template #header>
            <div class="card-header">参会人员活跃度</div>
          </template>
          <el-table :data="[
            { name: '张三', meetings: 15, minutes: 12, tasks: 8, completion: '92%' },
            { name: '李四', meetings: 12, minutes: 10, tasks: 6, completion: '85%' },
            { name: '王五', meetings: 10, minutes: 9, tasks: 5, completion: '90%' },
            { name: '赵六', meetings: 8, minutes: 7, tasks: 4, completion: '88%' }
          ]" style="width: 100%">
            <el-table-column prop="name" label="姓名" />
            <el-table-column prop="meetings" label="参会会议数" />
            <el-table-column prop="minutes" label="生成纪要数" />
            <el-table-column prop="tasks" label="负责行动项" />
            <el-table-column prop="completion" label="完成率" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped lang="scss">
.statistics {
  .overview-row {
    margin-bottom: 20px;

    .stat-card {
      .stat-label {
        font-size: 14px;
        color: #999;
        margin-bottom: 8px;
      }

      .stat-value {
        font-size: 32px;
        font-weight: 600;
        color: #333;

        .unit {
          font-size: 14px;
          font-weight: normal;
          color: #999;
          margin-left: 4px;
        }
      }

      .stat-change {
        font-size: 13px;
        margin-top: 8px;

        &.up {
          color: #10b981;
        }

        &.down {
          color: #ef4444;
        }
      }
    }
  }

  .chart-row {
    margin-bottom: 20px;

    .card-header {
      font-weight: 600;
    }

    .chart-placeholder {
      height: 300px;
      display: flex;
      align-items: center;
      justify-content: center;
    }

    .mock-pie {
      .pie-legend {
        display: flex;
        align-items: center;
        gap: 8px;
        margin: 12px 0;
        font-size: 14px;

        .dot {
          width: 12px;
          height: 12px;
          border-radius: 50%;
        }
      }
    }

    .line-chart {
      display: flex;
      align-items: flex-end;
      gap: 40px;
      padding-bottom: 30px;

      .bar-item {
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 8px;

        .bar-group {
          display: flex;
          gap: 4px;
          align-items: flex-end;

          .bar {
            width: 20px;
            border-radius: 4px 4px 0 0;
            transition: height 0.3s;

            &.meeting {
              background: #4f46e5;
            }

            &.minutes {
              background: #10b981;
            }
          }
        }

        .month-label {
          font-size: 12px;
          color: #999;
        }
      }
    }

    .legend {
      display: flex;
      justify-content: center;
      gap: 24px;
      margin-top: 12px;
      font-size: 13px;

      .dot {
        display: inline-block;
        width: 10px;
        height: 10px;
        border-radius: 50%;
        margin-right: 6px;

        &.meeting-dot {
          background: #4f46e5;
        }

        &.minutes-dot {
          background: #10b981;
        }
      }
    }
  }
}
</style>
