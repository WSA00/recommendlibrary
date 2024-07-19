<template>
    <main class="relative w-full h-auto">
        <article class="w-full h-auto absolute top-0 left-0 flex flex-col items-center gap-4 pb-12">
            <!-- 表格 -->
            <el-table
                :data="getSource"
                v-if="getSource"
                v-loading="!getDataReady"
                stripe
                style="width: 100%"
                header-cell-class-name="text-black" 
                class="rounded-xl"
            >
                <!-- 占位列 -->
                <el-table-column width="10"></el-table-column>
                <el-table-column
                    prop="id"
                    label="借阅号"
                    width="100"
                ></el-table-column>
                <el-table-column
                    prop="bname"
                    label="图书名称"
                    width="150"
                ></el-table-column>
                <el-table-column
                    prop="author"
                    label="作者"
                    width="100"
                ></el-table-column>
                <el-table-column
                    prop="press"
                    label="出版社"
                    width="160"
                ></el-table-column>
                <el-table-column
                    prop="user"
                    label="借阅人"
                    width="80"
                ></el-table-column>
                <el-table-column
                    prop="phone"
                    label="联系电话"
                    width="120"
                ></el-table-column>
                <el-table-column
                    prop="warehouse"
                    label="来源仓库"
                    width="200"
                ></el-table-column>
                <el-table-column label="创建时间"
                width="160">
                    <template slot-scope="scope">
                    <p>{{ new Date(scope.row.begin_time).toLocaleDateString() + " " + new Date(scope.row.begin_time).toLocaleTimeString().slice(0,5) }}</p>
                    </template>
                </el-table-column>
                <el-table-column label="截止/归还时间"
                    width="160">
                    <template slot-scope="scope">
                    <p>{{ new Date(scope.row.end_time).toLocaleDateString() + " " + new Date(scope.row.end_time).toLocaleTimeString().slice(0,5) }}</p>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="status"
                    label="状态"
                    :formatter="formatStatus"
                    width="90"
                ></el-table-column>

                <!-- 编辑 -->
                <el-table-column width="80">
                    <template >
                    </template>
                </el-table-column>
                <el-table-column width="80" label="操作">
                    <template slot-scope="scope">
                        <el-button :disabled="scope.row.status !== 0" @click="handleHistoryContinue(scope.row)" type="text">续借</el-button>
                    </template>
                </el-table-column>
                <el-table-column  width="80" >
                    <template slot-scope="scope">
                        <el-button  :disabled="scope.row.status !== 0"  @click="handleHistoryDeal(scope.row)" type="text">归还</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <!-- 空状态 -->
            <el-empty description="" v-else class="bg-white w-full h-96 rounded-xl"></el-empty>
            <!-- 分页 -->
            <el-pagination
                layout="prev, pager, next" 
                :current-page="getPage" 
                @current-change="handleCurrentChange"
                :page-size="getPageSize"
                :total="getHistoryTotal"
            ></el-pagination>
        </article>
    </main>
</template>

<script>
import { sleep } from "@/util/sleep"
import { createNamespacedHelpers } from "vuex"
const { mapGetters, mapMutations, mapActions } = createNamespacedHelpers("historyArea")
export default {
    name: "HistoryTable",
    async created() {
        this.setDataReady(false)
        const { historyList } = await this.fetchSource()
        this.setSource(historyList)
        await sleep()
        this.setDataReady(true)
    },
    computed: {
        ...mapGetters([
            "getSource", "getDataReady", "getPage", "getPageSize", "getHistoryTotal"
        ])
    },
    methods: {
        ...mapMutations([
            "setSource", "setDialogFormVisible", "setDataReady", "setPage"
        ]),
        ...mapActions([
            "fetchSource", "dealHistory", "continueHistory"
        ]),
        // 处理续借操作
        handleHistoryContinue({ id }) {
            this.$confirm('确认续借该图书, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'error'
            }).then(async () => {
                await this.continueHistory(id)
                this.setDataReady(false)
                const { historyList } = await this.fetchSource()
                this.setSource(historyList)
                await sleep()
                this.setDataReady(true)
            }).catch(() => {})
        },
        // 处理归还操作
        handleHistoryDeal({ id }) {
            this.$confirm('确认归还该图书, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'error'
            }).then(async () => {
                await this.dealHistory(id)
                this.setDataReady(false)
                const { historyList } = await this.fetchSource()
                this.setSource(historyList)
                await sleep()
                this.setDataReady(true)
            }).catch(() => {})
        },
        // 处理页数切换
        async handleCurrentChange(newPage) {
            this.setDataReady(false)
            this.setPage(newPage)
            const { historyList } = await this.fetchSource()
            this.setSource(historyList)
            await sleep()
            this.setDataReady(true)
        },
        formatStatus(row, column) {
        // row 是当前行的数据对象
        // column 是当前列的配置对象
        const status = row[column.property]; // 获取 status 属性的值，即 0 或 1
        return status === 0 ? '借阅中' : '已归还';
        }
    }
}
</script>

<style scoped></style>