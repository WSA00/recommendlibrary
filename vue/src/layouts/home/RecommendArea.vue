<template>
    <main class="w-full h-auto flex flex-col items-center gap-6">
      <!-- 头部 -->
      <header class="w-full flex justify-between">
        <h1 class="text-2xl font-bold">为你推荐</h1>
  
        <el-select v-model="selectedRecommend" @change="handleRecommendChange(selectedType)">
          <el-option
            v-for="type in recommendtypes"
            :key="type.value"
            :label="type.label"
            :value="type.value"
          ></el-option>
        </el-select>
        
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
      
      <!-- 无表单功能 - 用于获取图书数据 -->
      <BookFactory/>
    </main>
  </template>
  
  <script>
  import { sleep } from '@/util/sleep'
  import BookCard from '@/views/home/recommend-area/BookCard.vue';
  import BookDetail from '@/views/home/recommend-area/BookDetail.vue';
  import BookFactory from '@/views/home/recommend-area/BookFactory.vue';
  import { createNamespacedHelpers } from 'vuex'
  const { mapGetters, mapMutations, mapActions } = createNamespacedHelpers("recommendArea")
  export default {
    name: "RecommendArea",
    components: {
      BookCard, BookFactory, BookDetail
    },
    data() {
      return {
        selectedType: 'popularity', 
        recommendtypes: [
          { value: 'popularity', label: '图书热度榜' },
          { value: 'recommendation', label: '猜你喜欢' }
        ],
      }
    },
    async created() {
      this.setDataReady(false)
      const { source } = await this.fetchRandomSource()
      this.setSource(source)
      await sleep()
      this.setDataReady(true)
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
        "fetchRandomSource","fetchTypes","fetchSource1"
      ]),

      // 处理页数切换
      async handleCurrentChange(newPage) {
        this.setDataReady(false)
        this.setPage(newPage)
        const { source } = await this.fetchRandomSource()
        this.setSource(source)
        await sleep()
        this.setDataReady(true)
      },
      async handleRecommendChange(/*selectedTid*/) {
      this.setDataReady(false);
      // 清空当前页数
      this.setPage(1);
      const { source } = await this.fetchRandomSource()
      this.setSource(source)
      await sleep()
      this.setDataReady(true)
      this.types = await this.fetchTypes().then(types => types.map(type => ({
              value: type.id,
              label: type.tname
          })))

      /*
      // 处理页数切换
      async handleCurrentChange(newPage) {
        this.setDataReady(false)
        this.setPage(newPage)
        const { source } = await this.fetchSource1({ tid: this.selectedType })
        this.setSource(source)
        await sleep()
        this.setDataReady(true)
      },
      async handleRecommendChange(selectedTid) {
      this.setDataReady(false);
      // 清空当前页数
      this.setPage(1);
      const { source } = await this.fetchSource1({ tid: selectedTid })
      this.setSource(source)
      await sleep()
      this.setDataReady(true)
      this.types = await this.fetchTypes().then(types => types.map(type => ({
              value: type.id,
              label: type.tname
          })))
        */
      }
    }
  }
  </script>
  
  <style scoped>
    .aspect {
      aspect-ratio: 4/3;
    }
  </style>