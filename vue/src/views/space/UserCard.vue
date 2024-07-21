<template>
  <main class="w-full h-auto flex flex-col xl:flex-row items-start gap-4">
    <aside class="xl:w-96 w-full h-auto flex flex-col gap-4">
      <!-- 我的头像 -->
      <el-card v-loading="!getUser" shadow="never">
        <h1 slot="header" class="clearfix text-xl font-bold">我的头像</h1>
        <el-image :src="getUser?.avatar" fit="cover" class="w-full rounded-lg" style="aspect-ratio: 1;"></el-image>
      </el-card>

      <!-- 我的信息 -->
      <el-card v-loading="!getUser || !count || !monthcount" shadow="never">
        <h1 slot="header" class="clearfix text-xl font-bold">我的信息</h1>
        <el-descriptions :border="true" :column="1" :labelStyle="{ 'color': 'black', 'white-space': 'nowrap' }">
          <el-descriptions-item label="用户名">{{ getUser?.username }}</el-descriptions-item>
          <el-descriptions-item label="手机号">{{ getUser?.phone }}</el-descriptions-item>
          <el-descriptions-item label="注册时间">{{ joined_date }}</el-descriptions-item>
          <el-descriptions-item label="权限">
            <el-tag v-if="getUser?.role==='USER'" size="small">普通用户</el-tag>
            <el-tag v-if="getUser?.role==='ROOT'" size="small">管理员</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="总借阅量">{{ count }}</el-descriptions-item>
          <el-descriptions-item label="本月借阅量">{{ monthcount }}</el-descriptions-item>
        </el-descriptions>
      </el-card>
    </aside>

    <!-- 我的借阅 -->
    <el-card shadow="never" class="w-full h-auto">
      <h1 slot="header" class="clearfix text-xl font-bold">我的借阅</h1>
      <article class="w-full flex flex-col justify-start items-start p-4 rounded-lg relative overflow-hidden" style="height: 960px;">
        <section v-if="source && source.length > 0" class="w-full h-full overflow-auto absolute top-0 left-0">
          <el-table
            :data="source"
            stripe
            style="width: 100%;"
            header-cell-class-name="text-black"
          >
            <el-table-column prop="id" fixed label="借阅号" width="80"></el-table-column>
            <el-table-column prop="bname" label="图书名称" width="150"></el-table-column>
            <el-table-column prop="author" label="作者" width="165"></el-table-column>
            <el-table-column prop="press" label="出版社" width="165"></el-table-column>
            <el-table-column prop="location" label="来源仓库" width="180"></el-table-column>
            <el-table-column label="创建时间" width="180">
              <template slot-scope="scope">
                <p>{{ new Date(scope.row.begin_time).toLocaleDateString() + " " + new Date(scope.row.begin_time).toLocaleTimeString().slice(0,5) }}</p>
              </template>
            </el-table-column>
            <el-table-column label="截止/归还时间" width="180">
              <template slot-scope="scope">
                <p>{{ new Date(scope.row.end_time).toLocaleDateString() + " " + new Date(scope.row.end_time).toLocaleTimeString().slice(0,5) }}</p>
              </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" :formatter="formatStatus" width="120"></el-table-column>
          </el-table>
        </section>
        <section v-else class="w-full h-full flex items-center justify-center absolute top-0 left-0">
          <p class="text-5xl text-blue-1000">空空如也</p>
        </section>
      </article>
    </el-card>
  </main>
</template>

<script>
import api from "@/api/api"
import { sleep } from "@/util/sleep"
import { mapGetters } from "vuex"
import { createNamespacedHelpers } from "vuex"
const { mapActions: mapUserActions } = createNamespacedHelpers("userArea")
export default {
  name: "UserCard",
  async created() {
    await sleep()
    const response = await api.get(`/api/user/${this.getUser?.id}/history`, { token: localStorage.getItem("token") })
    this.count = response?.count
    this.monthcount = response?.monthcount
    this.source = response?.source || []
  },
  data() {
    return {
      source: [],
      count: "",
      monthcount: ""
    }
  },
  computed: {
    ...mapGetters([
      "getUser"
    ]),
    joined_date() {
      return new Date(this.getUser?.joined_date).toLocaleDateString()
    }
  },
  methods: {
    ...mapUserActions([
      "fetchUserDetail"
    ]),
    formatStatus(row, column) {
    // row 是当前行的数据对象
    // column 是当前列的配置对象
    const status = row[column.property]; // 获取 status 属性的值，即 0 或 1
    return status === 0 ? '借阅中' : '已归还';
    }
  }
}
</script>

<style>

</style>