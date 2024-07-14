<template>
    <main class="w-full h-auto flex flex-col items-center gap-6">
        <!-- 头部 -->
        <header class="w-full flex justify-between">
            <h1 class="text-2xl font-bold">借阅管理</h1>
            <el-button type="success" @click="setDialogFormVisible(true)" size="small">添加借阅记录</el-button>
        </header>
        <!-- 借阅记录列表 -->
        <HistoryallTableVue/>
        <!-- 添加仓库 -->
        <HistoryallFactory/>
    </main>
</template>

<script>
import HistoryallTableVue from '@/views/home/historyall-area/HistoryallTable.vue'
import HistoryallFactory from '@/views/home/historyall-area/HistoryallFactory.vue';
import { createNamespacedHelpers } from "vuex"
const { mapMutations } = createNamespacedHelpers("historyallArea")
export default {
    name: "HistoryallArea",
    components: { HistoryallTableVue, HistoryallFactory },
    methods: {
        ...mapMutations([
            "setDialogFormVisible"
        ])
    }
}
</script>

<style>

</style>