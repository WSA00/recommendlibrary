<template>
    <main class="w-full h-auto flex flex-col items-center gap-6">
        <!-- 头部 -->
        <header class="w-full flex justify-between">
            <h1 class="text-2xl font-bold">我的借阅</h1>
            <el-button type="success" @click="setDialogFormVisible(true)" size="small">我要借阅</el-button>
        </header>
        <!-- 借阅记录列表 -->
        <HistoryTableVue/>
        <!-- 添加仓库 -->
        <HistoryFactory/>
    </main>
</template>

<script>
import HistoryTableVue from '@/views/home/history-area/HistoryTable.vue'
import HistoryFactory from '@/views/home/history-area/HistoryFactory.vue';
import { createNamespacedHelpers } from "vuex"
const { mapMutations } = createNamespacedHelpers("historyArea")
export default {
    name: "HistoryArea",
    components: { HistoryTableVue, HistoryFactory },
    methods: {
        ...mapMutations([
            "setDialogFormVisible"
        ])
    }
}
</script>

<style>

</style>