<template>
    <!-- 搜索栏 -->
    <main class="w-120">
      <el-input 
        v-model="input" 
        placeholder="You can search here" 
        maxlength="50"
        size="medium"
        @keyup.enter.native="handleSearch"
      >
        <template slot="prepend"><i class="el-icon-search transform scale-150"></i></template>
        <!-- 添加搜索按钮 -->
        <template slot="append">
          <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
        </template>
      </el-input>
    </main>
  </template>
  
  <script>
  export default {
    name: 'SearchBar',
    data() {
      return {
        input: '',
        // 记录上一次的搜索参数，用于检测变化
        lastInput: ''
      };
    },
    watch: {
      // 监听路由参数的变化
      '$route.query.q': {
        handler(newVal) {
          // 如果新的搜索参数不等于上次的参数，更新搜索框内容
          if (newVal !== this.lastInput) {
            this.input = decodeURIComponent(newVal);
            this.lastInput = newVal;
          }
        },
        immediate: true // 立即执行，以便在组件创建时就能获取并设置初始搜索参数
      }
    },
    methods: {
      handleSearch() {
        const encodedSearchString = encodeURIComponent(this.input);
  
        // 导航到 /search 页面并传递搜索关键词参数
        this.$router.push({ name: 'search', query: { q: encodedSearchString } })
          .catch(error => console.error('Router navigation error:', error)); // 捕获导航错误

        location.reload(); // 刷新页面
      }
    }
  };
  </script>
  
  <style>
  </style>
  
  