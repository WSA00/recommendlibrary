<template>
    <main class="w-full h-auto flex flex-col items-center gap-6">
        <!-- 头部 -->
        <header class="w-full flex justify-between">
            <h1 class="text-2xl font-bold">入库记录</h1>
            <el-button type="success" @click="setDialogFormVisible(true)" size="small">添加入库记录</el-button>
        </header>
        <StockinTableVue/>
        <!-- 添加入库记录 -->
        <StockinFactory/>
    </main>
</template>

<script>
import StockinTableVue from '@/views/home/stockin-area/StockinTable.vue'
import StockinFactory from '@/views/home/stockin-area/StockinFactory.vue';
import { createNamespacedHelpers } from "vuex"
const { mapMutations } = createNamespacedHelpers("stockinArea")
export default {
    name: "StockinArea",
    components: {
        StockinTableVue, StockinFactory
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