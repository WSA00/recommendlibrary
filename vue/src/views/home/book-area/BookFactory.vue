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
            <section class="w-80 h-36 my-6 self-center">
                <el-image v-if="form.poster" :src="form.poster" fit="cover" class="w-full h-full" :preview-src-list="[form.poster]"></el-image>
                <el-empty v-if="!form.poster" class="w-full h-full"></el-empty>
            </section>
            <el-row class="flex justify-between">
                <el-form-item label="图书名称" prop="bname" class="mr-4">
                    <el-input v-model="form.bname"></el-input>
                </el-form-item>
                <!-- 请选择图书类型 -->
                <el-form-item label="图书类型" prop="tid">
                    <el-select v-model="form.bid" filterable placeholder="请选择图书类型" clearable>
                        <el-option
                            v-for="item in types"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
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
            <!--             
                <el-form-item label="车型" prop="poster">
                    <el-select v-model="form.poster" placeholder="请选择车型" clearable>
                        <el-option
                            v-for="item in types"
                            :key="item.poster"
                            :label="item.label"
                            :value="item.poster"
                        >
                            <el-image class="float-left w-10 h-full" :src="item.poster" fit="cover"></el-image>
                            <span class="float-right text-gray-500 font-bold">{{ item.label }}</span>
                        </el-option>
                    </el-select>
                </el-form-item> -->

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
const { mapGetters, mapMutations, mapActions } = createNamespacedHelpers("bookArea")
export default {
    name: "BookFactory",
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
                poster: '',
                introduce: '',
            },
            rules: {
                bname: { required: true, message: '请输入当前图书名称', trigger: 'blur' },
                tname: { required: true, message: '请输入当前图书类型', trigger: 'blur' },
                author: { required: true, message: '请输入当前图书作者', trigger: 'blur' },
                press: { required: true, message: '请输入当前图书出版社', trigger: 'blur' },
                poster: { required: true, message: '请上传当前图书图片', trigger: 'change' },
                introduce: [
                    { required: true, message: '请输入当前产品简介', trigger: 'blur' },
                    { min: 1, max: 200, message: '简介应当在 1 到 200 个字符', trigger: 'blur' },
                ]
            }
        }
    },
    computed: {
        ...mapGetters([
            "getDialogFormVisible", "getBookTotal", "getPageSize"
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
            "setDialogFormVisible", "setPage", "setSource", "setDataReady"
        ]),
        ...mapActions([
            "fetchSource", "createBook","fetchTypes"
        ]),
        // 提交表单 - 创建新产品
        async submitForm(formName) {
            await this.$refs[formName].validate(async valid => {
                if(valid) {
                    // 表单验证通过后...
                    await this.createBook(this.form)
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