<template>
    <!-- 添加图书 -->
    <el-dialog 
        title="添加图书" 
        :visible.sync="dialogFormVisible"
        center
        width="600px"
        top="2%"
    >   
        <el-form 
            ref="form" 
            :model="form" 
            v-if="form" 
            :rules="rules" 
            status-icon
            label-width="80px" 
            label-position="left"
            class="flex flex-col items-start"
        >
        
            <UploadPosterVue class="self-center mb-6"/>
            
            <el-row class="flex justify-between">
                <el-form-item label="图书名称" prop="bname" class="mr-4">
                    <el-input v-model="form.bname"></el-input>
                </el-form-item>
                <!-- 请选择图书类型 -->
                <el-form-item label="图书类型" prop="tname">
                    <el-select v-model="form.tname" filterable placeholder="请选择图书类型" clearable>
                        <el-option
                            v-for="item in types"
                            :key="item.value"
                            :label="item.label"
                            :value="item.label"
                        ></el-option>
                    </el-select>
                </el-form-item>
            </el-row>
            <el-row class="flex justify-between">
                <el-form-item label="作者" prop="author" class="mr-4">
                    <el-input v-model="form.author"></el-input>
                </el-form-item>
                <el-form-item label="出版社" prop="press">
                    <el-input v-model="form.press"></el-input>
                </el-form-item>
            </el-row>

            <el-form-item label="简介" prop="introduce" class="w-full">
                <el-input type="textarea" v-model="form.introduce"></el-input>
            </el-form-item>
        </el-form>
        <section slot="footer" class="dialog-footer">
            <el-button type="success" @click="submitForm('form')">立即创建</el-button>
            <el-button @click="resetForm('form')">重 置</el-button>
        </section>
    </el-dialog>
</template>

<script>
import { sleep } from "@/util/sleep"
import { createNamespacedHelpers } from "vuex"
import UploadPosterVue from "./UploadPoster.vue"
import { uploadQiniuImage, hostname } from "@/api/upload"
const { mapGetters, mapMutations, mapActions } = createNamespacedHelpers("bookArea")
export default {
    name: "BookFactory",
    components: {
        UploadPosterVue
    },
    async created() {
        this.types = await this.fetchTypes().then(types => types.map(type => ({
            value: type.id,
            label: type.tname
        })))
    },
    data() {
        return {
            form: {
                bname: '',
                tname: '',
                author:'',
                press:'',
                introduce: '',
            },
            rules: {
                bname: { required: true, message: '请输入当前图书名称', trigger: 'blur' },
                tname: { required: true, message: '请输入当前图书类型', trigger: 'blur' },
                author: { required: true, message: '请输入当前图书作者', trigger: 'blur' },
                press: { required: true, message: '请输入当前图书出版社', trigger: 'blur' },
                introduce: [
                    { required: true, message: '请输入当前产品简介', trigger: 'blur' },
                    { min: 1, max: 500, message: '简介应当在 1 到 500 个字符', trigger: 'blur' },
                ]
            }
        }
    },
    computed: {
        ...mapGetters([
            "getDialogFormVisible", "getBookTotal", "getPageSize", "getFile"
        ]),
        dialogFormVisible: {
            get() {
                return this.getDialogFormVisible
            },
            set(newValue) {
                this.setDialogFormVisible(newValue)
            }
        }
    },
    methods: {
        ...mapMutations([
            "setDialogFormVisible", "setPage", "setSource", "setDataReady", "setFile", "setPreviewImage"
        ]),
        ...mapActions([
            "fetchSource", "createBook","fetchTypes"
        ]),
        // 提交表单 - 创建新产品
        async submitForm(formName) {
            await this.$refs[formName].validate(async valid => {
                if(valid) {
                    this.setPreviewImage(null)
                    // 表单验证通过后...
                    const { bname, tname, author, press, introduce } = this.form
                    let poster = "http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/Book.jpg"
                    // 判断有没有上传图片
                    if(this.getFile) {
                        const { key } = await uploadQiniuImage(this.getFile)
                        poster = `${hostname}/${key}`
                        this.setFile(null)
                    }
                    await this.createBook({
                        bname, 
                        tname, 
                        author, 
                        press, 
                        poster,
                        introduce
                    })
                    
                    this.setDataReady(false)
                    await sleep()
                    this.setPage(Math.ceil(this.getBookTotal / this.getPageSize))
                    const { source } = await this.fetchSource()
                    this.setSource(source)
                    this.setDialogFormVisible(false)
                    this.setDataReady(true)
                    this.$refs[formName].resetFields()
                }
                else return false
            })
        },
        // 重置表单
        async resetForm(formName) {
            this.$refs[formName].resetFields()
        }
    }
}
</script>

<style>

</style>