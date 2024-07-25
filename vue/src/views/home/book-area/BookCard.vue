<template>
    <main @click="handleDetail" class="root p-4 flex flex-col font-bold select-none transition duration-200 hover:bg-purple-400 hover:text-white">
        <!-- 图书类型 & 名称 -->
        <h1 class="text-lg">{{ book.tname }}</h1>
        <p style="color: #a162f7;">{{ book.bname }}</p>
        <el-image
            class="w-full flex-1"
            :src="book.poster"
            fit="contain"
        >
            <!-- 加载图片失败 -->
            <div slot="error" class="image-slot">
                <i class="el-icon-picture-outline"></i>
            </div>
        </el-image>
        <section class="flex justify-between items-center">
            
            <span class="flex items-center gap-1">
                <!-- 作者 -->
                <span>作者：{{ book.author }}</span>
                
            </span>
             <span>出版社：{{ book.press }}</span>
        </section>
        <!-- 点击删除图书 -->
        <i 
            v-if="['ROOT'].includes($store.getters.getUser?.role)" 
            @click.stop="handleDelete(book.id)" 
            class="delete el-icon-error text-gray-700 text-2xl "
        ></i>
    </main>
</template>

<script>
import { sleep } from "@/util/sleep"
import { createNamespacedHelpers } from 'vuex'
const { mapMutations, mapActions } = createNamespacedHelpers("bookArea")
export default {
    name: "bookCard",
    props: [
        "book", "isSeleted"
    ],
    methods: {
        ...mapMutations([
            "setDrawer", "setBook", "setDataReady", "setSource"
        ]),
        ...mapActions([
            "fetchSource", "fetchBook", "deleteBook"
        ]),
        // 处理图书详情
        async handleDetail() {
            const { id } = this.book
            const { book } = await this.fetchBook(id)
            this.setBook(book)
            this.setDrawer(true)
        },
        // 处理删除图书
        async handleDelete(id) {
            this.$confirm('此操作将永久删除该图书, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'error'
            }).then(async () => {
                // 点击确定删除后...
                await this.deleteBook(id)
                this.setDataReady(false)
                const { source } = await this.fetchSource()
                this.setSource(source)
                await sleep()
                this.setDataReady(true)
            }).catch(() => {})
        }
    }
}
</script>

<style scoped>
    .root {
        position: relative;
    }
    .root:hover .delete {
        visibility: visible;
    }
    .delete {
        visibility: hidden;
        position: absolute;
        top: 10px;
        right: 10px;
    }
</style>