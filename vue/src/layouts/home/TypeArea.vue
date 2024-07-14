<template>
  <main class="w-full h-auto flex flex-col items-center gap-6">
    <!-- 头部 -->
    <header class="w-full flex justify-between">
        <h1 class="text-2xl font-bold">图书类型总览</h1>
        <el-button 
          v-if="['ROOT'].includes($store.getters?.getUser?.role)" 
          type="success" 
          @click="setDialogFormVisible(true)" 
          size="small"
        >添加图书类型</el-button>
    </header>
    <TypeTableVue class="w-full"/>
    <!-- 添加图书类型 -->
    <TypeFactoryVue/>
    <!-- 编辑图书类型 - 修改类型名称 -->
    <TypeEditVue/>
  </main>
</template>

<script>
import TypeEditVue from '@/views/home/type-area/TypeEdit.vue'
import TypeFactoryVue from '@/views/home/type-area/TypeFactory.vue'
import TypeTableVue from '@/views/home/type-area/TypeTable.vue'
import { createNamespacedHelpers } from "vuex"
const { mapMutations } = createNamespacedHelpers("typeArea")
export default {
  name: "typeArea",
  components: {
    TypeTableVue, TypeFactoryVue, TypeEditVue
  },
  methods: {
    ...mapMutations([
      "setDialogFormVisible"
    ])
  }
}
</script>

<style>

</style>