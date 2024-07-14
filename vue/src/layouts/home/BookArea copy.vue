<template>
  <main class="w-full h-auto flex flex-col items-center gap-6">
    <!-- 头部 -->
    <header class="w-full flex justify-between">
      <h1 class="text-2xl font-bold">图书总览</h1>
      <el-button 
        v-if="['ROOT'].includes($store.getters.getUser?.role)" 
        type="success" 
        @click="setDialogFormVisible(true)" 
        size="small"
      >添加图书</el-button>
    </header>
    <el-select v-model="selectedType" placeholder="请选择图书类型" clearable @change="handleTypeChange" @clear="handleTypeChange">
      <el-option
        v-for="type in types"
        :key="type.value"
        :label="type.label"
        :value="type.value"
      ></el-option>
    </el-select>    
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
    <!-- 表单 - 用于添加图书 -->
    <BookFactory/>
  </main>
</template>

<script>
import { sleep } from '@/util/sleep'
import BookCard from '@/views/home/book-area/BookCard.vue';
import BookDetail from '@/views/home/book-area/BookDetail.vue';
import BookFactory from '@/views/home/book-area/BookFactory.vue';
import { createNamespacedHelpers } from 'vuex'
const { mapGetters, mapMutations, mapActions } = createNamespacedHelpers("bookArea")
export default {
  name: "BookArea",
  components: {
    BookCard, BookFactory, BookDetail
  },
  async created() {
    this.setDataReady(false)
    const { source } = await this.fetchSource()
    this.setSource(source)
    await sleep()
    this.setDataReady(true)
    this.types = await this.fetchTypes().then(types => types.map(type => ({
            value: type.id,
            label: type.tname
        })))
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
      "fetchSource","fetchTypes"
    ]),
    // 处理页数切换
    async handleCurrentChange(newPage) {
      this.setDataReady(false)
      this.setPage(newPage)
      const { source } = await this.fetchSource()
      this.setSource(source)
      await sleep()
      this.setDataReady(true)
    }
  }
}
</script>

<style scoped>
  .aspect {
    aspect-ratio: 4/3;
  }
</style>