<template>
    <main>
        <!-- 上传图书文件 -->
        <el-upload
            list-type="picture-card"
            :action="action"
            :show-file-list="false"
            :http-request="handleUpload"
            :on-success="handlePosterSuccess"
            :before-upload="beforePosterUpload"
            class="overflow-hidden"
        >
            <el-image
                :src="getPreviewImage || poster ||'http://sh4kj2q5q.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E5%9B%BE%E7%89%87/Book.jpg'"
                class="w-full h-full"
                fit="cover"
            ></el-image>
        </el-upload>
    </main>
</template>

<script>
import { hostname, action } from "@/api/upload"
import { createNamespacedHelpers } from "vuex"
const { mapGetters, mapMutations } = createNamespacedHelpers("bookArea")
export default {
    name: "UploadPoster",
    props: [
        "poster"
    ],
    data() {
        return {
            action,
            hostname,
        }
    },
    computed: {
        ...mapGetters([
            "getPreviewImage"
        ])
    },
    methods: {
        ...mapMutations([
            "setFile", "setPreviewImage"
        ]),
        // 上传图片前...约束
        beforePosterUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
            this.$message.error('上传图书图片只能是 JPG 格式!');
            }
            if (!isLt2M) {
            this.$message.error('上传图书图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
        },
        // 上传图片后...预览图片
        handlePosterSuccess(res, file) {
            this.setPreviewImage(URL.createObjectURL(file.raw))
        },
        // 上传图片
        async handleUpload(upload) {
            this.setFile(upload.file)
        }
    }
}
</script>

<style>

</style>