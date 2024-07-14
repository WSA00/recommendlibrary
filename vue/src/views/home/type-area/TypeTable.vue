<template>
    <main class="relative w-full h-auto">
        <article class="w-full h-auto absolute top-0 left-0 flex flex-col items-center gap-4 pb-12">
            <!-- 表格 -->
            <el-table
                :data="getSource"
                v-loading="!getDataReady"
                v-if="getSource"
                stripe
                style="width: 100%"
                header-cell-class-name="text-black" 
                class="rounded-xl"
            >
                <!-- 占位列 -->
                <el-table-column width="10"></el-table-column>
                <el-table-column
                    prop="id"
                    label="序号"
                    width="80"
                ></el-table-column>
                <el-table-column
                    prop="tname"
                    label="图书类型名称"
                ></el-table-column>
                <!-- 编辑 -->
                <el-table-column v-if="['ROOT'].includes($store.getters.getUser?.role)" fixed="right" label="操作" width="120">
                    <template slot-scope="scope">
                        <el-button @click="handelTnameChange(scope.row)" type="text">修改</el-button>
                        <el-button @click="handleTypeDelete(scope.row)" type="text">删除</el-button>
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
                :total="getTypeTotal"
            ></el-pagination>
        </article>
    </main>
</template>

<script>
import { sleep } from "@/util/sleep"
import { createNamespacedHelpers } from "vuex"
const { mapGetters, mapMutations, mapActions } = createNamespacedHelpers("typeArea")
export default {
    name: "TypeTable",
    async created() {
        this.setDataReady(false)
        const { typeList } = await this.fetchSource()
        this.setSource(typeList)
        await sleep()
        this.setDataReady(true)
    },
    computed: {
        ...mapGetters([
            "getSource", "getDataReady", "getPage", "getPageSize", "getTypeTotal"
        ])
    },
    methods: {
        ...mapMutations([
            "setSource", "setDialogEditVisible", "setDataReady", "setType", "setPage"
        ]),
        ...mapActions([
            "fetchSource", "fetchType", "deleteType"
        ]),
        // 处理修改图书类型
        async handelTnameChange({ id }) {
            const type = await this.fetchType(id)
            this.setType(type)
            this.setDialogEditVisible(true)
        },
        // 处理删除图书类型
        async handleTypeDelete({ id }) {
            this.$confirm('此操作将永久删除该图书类型, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'error'
            }).then(async () => {
                await this.deleteType(id)
                this.setDataReady(false)
                const { typeList } = await this.fetchSource()
                this.setSource(typeList)
                await sleep()
                this.setDataReady(true)
            }).catch(() => {})
        },
        // 处理页面切换
        async handleCurrentChange(newPage) {
            this.setDataReady(false)
            this.setPage(newPage)
            const { typeList } = await this.fetchSource()
            this.setSource(typeList)
            await sleep()
            this.setDataReady(true)
        }
    }
}
</script>