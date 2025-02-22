<template>
    <main class="w-full h-auto flex flex-col items-center gap-6">
      <!-- 头部 -->
      <header class="w-full flex justify-between">
        <h1 class="text-2xl font-bold">为你推荐</h1>
  
        <el-select v-model="selectedType" placeholder="随机推荐中...（点击我开启个性化推荐）" @change="handleRecommendChange(selectedType)" class="custom-select">
          <el-option
            v-for="item in recommendtypes"
            :key="item.value"
            :label="item.label"
            :value="item.value"
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
      <!-- <BookFactory/> -->
    </main>
  </template>
  
  <script>
  import { sleep } from '@/util/sleep'
  import BookCard from '@/views/home/recommend-area/BookCard.vue';
  import BookDetail from '@/views/home/recommend-area/BookDetail.vue';
  //import BookFactory from '@/views/home/recommend-area/BookFactory.vue';
  import { createNamespacedHelpers } from 'vuex'
  const { mapGetters, mapMutations, mapActions } = createNamespacedHelpers("recommendArea")
  export default {
    name: "RecommendArea",
    components: {
      BookCard, BookDetail//, BookFactory
    },
    data() {
      return {
        selectedType: null, 
        types:[],        // 图书类型列表
        recommendtypes: [
          { value: 'popularity', label: '图书热度榜' },
          { value: 'recommendation', label: '猜你喜欢' }
        ],
      }
    },
    async created() {
      this.setDataReady(false)

      // 定义默认参数
      let defaultParams = {
        hot: 0,
        smart: 0,
        uid: 0 
      };
      
      const { source } = await this.fetchSource(defaultParams)
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
        "fetchSource","fetchTypes"
      ]),

      // 处理页数切换
      async handleCurrentChange(newPage) {
        this.setDataReady(false)
        this.setPage(newPage)
        
        let hot=0;
        let smart=0;
        // 获取用户 ID
        const uid = this.$store.getters.getUser.id;

        // 根据 selectedType 进行不同的处理
        if (this.selectedType === 'popularity') {
          smart = 0; 
          hot = 1;
          } 
        else if (this.selectedType === 'recommendation') {
          smart = 1;
          hot = 0;
        }

        const { source } = await this.fetchSource({ hot, smart, uid});

        this.setSource(source);
        await sleep();
        this.setDataReady(true);
      },
      async handleRecommendChange(selectedType) {
        this.setDataReady(false);
        // 清空当前页数
        this.setPage(1);

        let hot=0;
        let smart=0;
        // 获取用户 ID
        const uid = this.$store.getters.getUser.id;

        // 根据 selectedType 进行不同的处理
        if (selectedType === 'popularity') {
          smart = 0; 
          hot = 1;
        } 
        else if (selectedType === 'recommendation') {
          smart = 1;
          hot = 0;
        }

        const { source } = await this.fetchSource({ hot, smart, uid});

          this.setSource(source);
          await sleep();
          this.setDataReady(true);
          
          // // 更新类型
          // this.types = await this.fetchTypes().then(types => types.map(type => ({
          //     value: type.id,
          //     label: type.tname
          // })))

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
    .custom-select {
      width: 300px; /* 设置最小宽度 */
      white-space: nowrap;/* 禁止换行 */
  }
  </style>