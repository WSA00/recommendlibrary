<template>
    <!-- 搜索栏 -->
    <main class="w-120">
      <el-input 
        v-model="input" 
        placeholder="You can search here" 
        maxlength="50"
        size="medium"
        clearable
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
        lastInput: ''
      };
    },
    mounted() {
      // 初始化时如果路由中有 'q' 参数，则更新输入框内容
      if (this.$route.query.q !== undefined && this.$route.query.q !== 'undefined') {
        this.input = decodeURIComponent(this.$route.query.q);
        this.lastInput = this.input;
      } else {
        this.input = ''; // 如果没有 'q' 参数或者为 'undefined'，则将输入框置为空
        this.lastInput = '';
      }
    },
    methods: {
      handleSearch() {
        const encodedSearchString = encodeURIComponent(this.input);
    
        // 导航到 /search 页面并传递搜索关键词参数
        this.$router.push({ name: 'search', query: { q: encodedSearchString } })
          .catch(error => console.error('路由导航错误:', error));
      }
    },
    watch: {
      // 监听 $route.query.q 的变化
      '$route.query.q'(newQ, oldQ) {
        if (newQ !== oldQ) {
          this.input = decodeURIComponent(newQ);
          this.lastInput = this.input;
        }
      }
    }
  };
  </script>
  
  <style>
  </style>
  
  