<template>
  <!-- 图书详情 -->
  <el-drawer
    title="图书详情" 
    @open="handleDetail"
    :visible.sync="drawer"
    direction="ltr"
    size="800px"
  >
    <main v-if="getBook" v-loading="!getBook" class="w-full h-full flex p-6 gap-8 bg-gray-100 overflow-hidden">
      <!-- 左半部分 -->
      <article class="flex flex-col gap-4 flex-1 h-5/6 p-8 rounded-xl relative text-gray-100" style="background-color: #a78bfa;">
        <!-- 名称 & 类型 & 借阅量 &  简介 -->
        <div class="flex justify-between">
          <h1>名称:</h1>
          <p class="text-white font-bold text-xl w-5/6 text-right">{{ getBook.bname }}</p>
        </div>
        <div class="flex justify-between">
          <h1 class="whitespace-nowrap">类型:</h1>
          <p class="text-white font-bold text-xl w-5/6 text-right">{{ getBook.tname }}</p>
        </div>
        <div class="flex justify-between">
          <h1 class="whitespace-nowrap">借阅量:</h1>
          <p class="text-white font-bold text-xl w-5/6 text-right"><span class="px-4 text-red-500 text-2xl italic">借出{{ getBook.btimes }}</span>次</p>
        </div>
        <h1>简介:</h1>
        <p class="h-80 overflow-auto italic font-bold text-xl text-white" style="text-indent: 2em;">{{ getBook.introduce }}</p>
        <!-- 图书图片 -->
        <section class="w-full h-48 my-6 absolute left-0 bottom-0 transform translate-y-2/3 scale-125">
          <el-image v-if="getBook" :src="getBook?.poster" fit="cover" class="w-full h-full"></el-image>
        </section>
      </article>
      <!-- 右半部分 -->
      <aside class="flex-1 flex flex-col gap-6">
        <PieChart :title="'库存剩余情况'" :source="getChartSource?.pie" class="w-full flex-1 bg-white rounded-xl"/>
        <GradientBarChart :title="'历史借阅情况'" :source="getChartSource" class="w-full flex-1 bg-white rounded-xl"/>
      </aside>
    </main>
  </el-drawer>
</template>

<script>
import PieChart from "./PieChart.vue"
import GradientBarChart from "./GradientBarChart.vue"
import { createNamespacedHelpers } from "vuex"
const { mapGetters, mapMutations, mapActions } = createNamespacedHelpers("bookArea")
export default {
  name: "BookDetail",
  components: { PieChart, GradientBarChart },
  computed: {
    ...mapGetters([
      "getDrawer", "getBook", "getChartSource"
    ]),
    drawer: {
      get() {
        return this.getDrawer
      },
      set(newValue) {
        this.setDrawer(newValue)
      }
    }
  },
  methods: {
    ...mapMutations([
      "setDrawer", "setChartSource"
    ]),
    ...mapActions([
      "fetchBook"
    ]),
    // 处理打开抽屉
    async handleDetail() {
      const { id } = this.getBook
      this.setChartSource(await this.fetchBook(id))
    }
  }
}
</script>

<style>

</style>