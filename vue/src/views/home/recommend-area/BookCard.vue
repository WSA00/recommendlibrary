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
        
    </main>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
const { mapMutations, mapActions } = createNamespacedHelpers("recommendArea")
export default {
    name: "bookCard",
    props: [
        "book", "isSeleted"
    ],
    methods: {
        ...mapMutations([
            "setDrawer", "setBook"
        ]),
        ...mapActions([
            "fetchSource", "fetchBook"
        ]),
        // 处理图书详情
        async handleDetail() {
            const { id } = this.book
            const { book } = await this.fetchBook(id)
            this.setBook(book)
            this.setDrawer(true)
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