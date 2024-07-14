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
                    label="入库号"
                    width="70"
                ></el-table-column>
                <el-table-column
                    prop="createtime"
                    label="入库时间"
                >
                <template slot-scope="scope">
                    {{ new Date(scope.row.createtime).toLocaleDateString() }}
                </template>
                </el-table-column>
                <el-table-column
                    prop="bname"
                    label="图书名称"
                ></el-table-column>
                <el-table-column
                    prop="author"
                    label="作者"
                ></el-table-column>
                <el-table-column
                    prop="press"
                    label="出版社"
                ></el-table-column>
                <el-table-column
                    prop="warehouse"
                    label="仓库"
                ></el-table-column>
                <el-table-column
                    prop="quantity"
                    label="入库数量"
                    width="80"
                ></el-table-column>
                <!-- 编辑 -->
                <el-table-column fixed="right" label="操作" width="120">
                    <template slot-scope="scope">
                        <el-button @click="handleStockinDelete(scope.row)" type="text">删除</el-button>
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
                :total="getStockinTotal"
            ></el-pagination>
        </article>
    </main>
</template>

<script>
import { sleep } from "@/util/sleep"
import { createNamespacedHelpers } from "vuex"
const { mapGetters, mapMutations, mapActions } = createNamespacedHelpers("stockinArea")
export default {
    name: "StockinTable",
    async created() {
        this.setDataReady(false)
        const { stockinList } = await this.fetchSource()
        this.setSource(stockinList)
        await sleep()
        this.setDataReady(true)
    },
    computed: {
        ...mapGetters([
            "getSource", "getDataReady", "getPage", "getPageSize", "getStockinTotal"
        ])
    },
    methods: {
        ...mapMutations([
            "setSource", "setDialogFormVisible", "setDataReady", "setPage"
        ]),
        ...mapActions([
            "fetchSource", "deleteStockin"
        ]),
        // 处理删除订单
        handleStockinDelete({ id }) {
            this.$confirm('此操作将永久删除该供应记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'error'
            }).then(async () => {
                await this.deleteStockin(id)
                this.setDataReady(false)
                const { stockinList } = await this.fetchSource()
                this.setSource(stockinList)
                await sleep()
                this.setDataReady(true)
            }).catch(() => {})
        },
        // 处理页数切换
        async handleCurrentChange(newPage) {
            this.setDataReady(false)
            this.setPage(newPage)
            const { stockinList } = await this.fetchSource()
            this.setSource(stockinList)
            await sleep()
            this.setDataReady(true)
        }
    }
}
</script>