<template>
<!-- 员工详情 -->
<el-dialog 
  title="员工详情" 
  :visible.sync="dialogTableVisible"
  top="1rem"
  width="800px"
> 
  <el-row class="w-full h-64 flex">
    <DashboardChartVue :title="'总借阅量（本）'" :value="count" :color="'#a162f7'" :rate="count" class="w-full h-full"/>
    <DashboardChartVue :title="'本月借阅(本)'" :value="monthcount" :color="'#a162f7'" :rate="monthcount/count" class="w-full h-full"/>
  </el-row>
  
  <section class="rounded-xl w-full h-96 overflow-auto">
    <!-- 表格 -->
    <el-table
      :data="source"
      v-if="source?.length"
      stripe
      style="width: 100%"
      header-cell-class-name="text-black" 
    >
      <!-- 占位列 -->
      <el-table-column prop="id" label="借阅号" width="80"></el-table-column>
      <el-table-column prop="bname" label="图书名称" width="120"></el-table-column>
      <el-table-column prop="author" label="作者" width="90"></el-table-column>
      <el-table-column prop="press" label="出版社" width="100"></el-table-column>
      <el-table-column prop="location" label="来源仓库"></el-table-column>
      <el-table-column label="创建时间"
                width="160">
                    <template slot-scope="scope">
                    <p>{{ new Date(scope.row.begin_time).toLocaleDateString() + " " + new Date(scope.row.begin_time).toLocaleTimeString().slice(0,5) }}</p>
                    </template>
                </el-table-column>
                <el-table-column label="截止/归还时间"
                    width="160">
                    <template slot-scope="scope">
                    <p>{{ new Date(scope.row.end_time).toLocaleDateString() + " " + new Date(scope.row.end_time).toLocaleTimeString().slice(0,5) }}</p>
                    </template>
                </el-table-column>
      <el-table-column prop="status" label="状态" :formatter="formatStatus" width="90" ></el-table-column>
    </el-table>
    <el-empty v-if="!source?.length" class="text-gray-300 font-bold">空空如也</el-empty>
  </section>

</el-dialog>
</template>

<script>
import DashboardChartVue from '../dashboard-area/DashboardChart.vue'
import { createNamespacedHelpers } from "vuex"
const { mapGetters, mapMutations } = createNamespacedHelpers("userArea")
export default {
  name: "UserDetail",
  components: { DashboardChartVue },
  computed: {
    count() {
      return this.getUserDetail?.count || 0
    },
    monthcount() {
      return this.getUserDetail?.monthcount || 0
    },
    source() {
      return this.getUserDetail?.source || []
    },
    ...mapGetters([
      "getDialogTableVisible", "getUserDetail"
    ]),
    dialogTableVisible: {
      get() {
        return this.getDialogTableVisible
      },
      set(newValue) {
        this.setDialogTableVisible(newValue)
      }
    }
  },
  methods: {
    formatStatus(row, column) {
    // row 是当前行的数据对象
    // column 是当前列的配置对象
    const status = row[column.property]; // 获取 status 属性的值，即 0 或 1
    return status === 0 ? '借阅中' : '已归还';
    },
    ...mapMutations([
      "setDialogTableVisible"
    ])
  }
}
</script>

<style>

</style>