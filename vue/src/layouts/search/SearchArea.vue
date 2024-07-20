<template>
  <main class="w-full h-auto flex flex-col items-center gap-6">
    <!-- 头部 -->
    <header class="w-full flex justify-between">
      <h1 class="text-2xl font-bold">图书总览</h1>
    </header>
           
    <!-- 图书列表 -->
    <article v-if="getSource" v-loading="!getDataReady" class="w-full h-auto grid grid-cols-2 xl:grid-cols-3 2xl:grid-cols-4  grid-flow-row gap-4">
      <!-- 遍历图书卡片 -->
      <bookCard
        v-for="(book, index) in getSource" 
        :key="index"
        :book="book"
        class="aspect rounded-xl bg-white cursor-pointer"
      />
    </article>
    <!-- 空状态 -->
    <el-empty description="" v-else class="bg-white w-full h-96 rounded-xl"></el-empty>
    <!-- 分页 -->
    <el-pagination
        layout="prev, pager, next"
        :current-page="getPage"
        @current-change="handleCurrentChange"
        :page-size="getPageSize"
        :total="getBookTotal"
        :hide-on-single-page="true"
    ></el-pagination>
    <!-- 图书详情 -->
    <BookDetail/>
  </main>
</template>

<script>
import { sleep } from '@/util/sleep'
import BookCard from '@/views/home/book-area/BookCard.vue';
import BookDetail from '@/views/home/book-area/BookDetail.vue';

import { createNamespacedHelpers } from 'vuex'
const { mapGetters, mapMutations, mapActions } = createNamespacedHelpers("bookArea")
export default {
  name: "BookArea",
  components: {
    BookCard, BookDetail
  },
  data() {
    return {
    }
  },
  async created() {
    this.setDataReady(false)
    const q = this.$route.query.q; // 获取路由参数 q
    const { source } = await this.fetchSource2({ string: q }); // 调用 fetchSource2 方法，并传递 q 参数
    this.setSource(source); // 设置数据源
    await sleep();
    this.setDataReady(true);
  },
  computed: {
    ...mapGetters([
      "getSource", "getPage", "getPageSize", "getBookTotal", "getDataReady"
    ])
  },
  methods: {
    ...mapMutations([
      "setSource", "setPage", "setDataReady", "setDialogFormVisible"
    ]),
    ...mapActions([
      "fetchSource","fetchSource2"
    ]),
    // 处理页数切换
    async handleCurrentChange(newPage) {
      this.setDataReady(false)
      this.setPage(newPage)
      const q = this.$route.query.q; // 获取路由参数 q
      const { source } = await this.fetchSource2({ string: q }); // 调用 fetchSource2 方法，并传递 q 参数
      this.setSource(source); // 设置数据源
      await sleep();
      this.setDataReady(true);
    }
  }
}
</script>

<style scoped>
  .aspect {
    aspect-ratio: 4/3;
  }
</style>
