<template>
    <!-- 添加图书类型 -->
    <el-dialog 
        title="添加图书类型" 
        :visible.sync="dialogFormVisible"
        center
        width="600px"
    >   
        <el-form 
            ref="form" 
            :model="form" 
            v-if="form" 
            :rules="rules" 
            status-icon
            label-width="110px" 
            label-position="left"
            class="flex flex-col items-start"
        >
            <el-form-item label="图书类型名称" prop="tname" class="w-full">
                <el-input type="textarea" v-model="form.tname" rows="1"></el-input>
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
const { mapGetters, mapMutations, mapActions } = createNamespacedHelpers("typeArea")
export default {
    name: "TypeFactory",
    data() {
        return {
            form: {
                tname: ''
            },
            rules: {
                tname: [
                    { required: true, message: '请输入当前图书类型名称', trigger: 'blur' },
                    { min: 1, max: 25, message: '名称应当在 25 个字符以内', trigger: 'blur' },
                ]
            }
        }
    },
    computed: {
        ...mapGetters([
            "getDialogFormVisible", "getTypeTotal", "getPageSize"
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
            "fetchSource", "createType"
        ]),
        // 提交表单 - 创建新产品
        async submitForm(formName) {
            await this.$refs[formName].validate(async valid => {
                if(valid) {
                    // 表单验证通过后...
                    await this.createType(this.form)
                    this.setDataReady(false)
                    await sleep()
                    this.setPage(Math.ceil(this.getTypeTotal / this.getPageSize) || 1)
                    const { typeList } = await this.fetchSource()
                    this.setSource(typeList)
                    this.setDialogFormVisible(false)
                    this.setDataReady(true)
                    // 重置表单
                    this.resetForm(formName)
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