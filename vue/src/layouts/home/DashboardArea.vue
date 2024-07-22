<template>
  <main class="w-full h-auto flex flex-col gap-6" v-loading="!dataReady" >
    <!-- 块行容器 -->
    <article class="w-full h-80 flex justify-between gap-6">
      <!-- 仪表盘遍历 -->
      <section
        v-for="(dashboard,index) in dashboards" 
        :key="index" 
        class="flex-1 h-full pt-5 flex flex-col justify-around items-center gap-4 overflow-hidden bg-white rounded-xl relative transition duration-300 hover:bg-purple-400"
      >
        <i :class="dashboard.icon" class="text-3xl" :style="{ 'color': dashboard.color }"></i>
        <component
          v-if="dataReady"
          :is="dashboard.component"
          :title="dashboard.title"
          :value="dashboard.value"
          :rate="dashboard.rate"
          :color="dashboard.color"
          class="w-full h-full"
        />
        <div class="w-full h-full absolute top-0 left-0 z-10 cursor-pointer"></div>
      </section>
    </article>
    <!-- 块行容器 -->
    <article class="w-full h-auto flex gap-6 select-none">
      <!-- 柱状图 -->
      <section class="flex-1 h-auto bg-white rounded-xl py-10 flex flex-col gap-10">
        <h1 class="text-left font-bold text-xl pl-10">活跃读者榜 (近 3 年榜单, 前 7 名数据, 借阅次数)</h1>
        <HistogramChart class="w-full h-96"/>
      </section>
      <!-- 折线图 -->
      <section class="flex-1 h-auto bg-white rounded-xl py-10 flex flex-col gap-10">
        <h1 class="text-left font-bold text-xl pl-10">热门图书榜 (近 4 月榜单, 前 9 名数据, 借出次数)</h1>
        <LineChart class="w-full h-96"/>
      </section>
    </article>
  </main>
</template>

<script>
import api from '@/api/api'
import { sleep } from '@/util/sleep'

const DashboardChart = () => import('@/views/home/dashboard-area/DashboardChart.vue')
const HistogramChart = () => import('@/views/home/dashboard-area/HistogramChart.vue')
const LineChart = () => import("@/views/home/dashboard-area/LineChart.vue")
export default {
  name: "DashboardArea",
  components: {
    DashboardChart, HistogramChart, LineChart
  },
  async created() {
    await sleep()
    const { total: income } = await api.get("/api/chart/total/income")
    const { total: orders } = await api.get("/api/chart/total/orders")
    const { total: warehouses } = await api.get("/api/chart/total/warehouses")
    const { total: users } = await api.get("/api/chart/total/users")
    this.income = income 
    this.orders = orders 
    this.warehouses = warehouses 
    this.users = users 
    this.dataReady = true
  },
  data() {
    return {
      dataReady: false,
      income: 0,
      orders: 0,
      warehouses: 0,
      users: 0
    }
  },
  computed: {
    dashboards() {
      return [
        {
          icon: "el-icon-s-flag text-3xl",
          title: "本月借阅量",
          value: this.income || 0,
          rate: 0.64,
          color: "#a162f7",
          component: DashboardChart
        },
        {
          icon: "el-icon-s-marketing text-3xl",
          title: "图书总借阅量",
          value: this.orders || 0,
          rate: 0.52,
          color: "#ff7e86",
          component: DashboardChart
        },
        {
          icon: "el-icon-s-data text-3xl",
          title: "图书馆总数",
          value: this.warehouses || 0,
          rate: 0.42,
          color: "#f6cc0d",
          component: DashboardChart
        },
        {
          icon: "el-icon-menu",
          title: "读者总数",
          value: this.users || 0,
          rate: 0.77,
          color: "#70cf97",
          component: DashboardChart
        }
      ]
    }
  }
}
</script>

<style>

</style>