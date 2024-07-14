<template>
    <!-- 添加入库记录 -->
    <el-dialog 
        title="添加入库记录" 
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
            label-width="80px" 
            label-position="left"
            class="flex flex-col items-start"
        >
            <!--         
             请选择供应商 
            <el-form-item label="供应商" prop="supplierId">
                <el-select v-model="form.supplierId" filterable placeholder="请选择供应商" clearable>
                    <el-option
                        v-for="item in suppliers"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    ></el-option>
                </el-select>
            </el-form-item> -->
            <!-- 请选择图书 -->
            <el-form-item label="图书" prop="bid">
                <el-select v-model="form.bid" filterable placeholder="请选择图书" clearable>
                    <el-option
                        v-for="item in books"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    ></el-option>
                </el-select>
            </el-form-item>
            <!-- 请选择仓库 -->
            <el-form-item label="仓库" prop="wid">
                <el-select v-model="form.wid" filterable placeholder="请选择仓库" clearable>
                    <el-option
                        v-for="item in warehouses"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    ></el-option>
                </el-select>
            </el-form-item>
            <!-- 请填写入库数量 -->
            <el-form-item label="入库量" prop="quantity">
                <el-input v-model="form.quantity"></el-input>
            </el-form-item>
            <!-- 请选择入库时间 -->
            <el-form-item label="入库时间" required>
                <el-row class="flex justify-start max-w-md">
                    <el-form-item prop="date">
                        <el-date-picker type="date" placeholder="选择日期" v-model="form.date" class="mr-6" :picker-options="pickerOptions"></el-date-picker>
                    </el-form-item>
                    <el-form-item prop="time">
                        <el-time-picker 
                            placeholder="选择时间" 
                            v-model="form.time"
                        ></el-time-picker>
                    </el-form-item>
                </el-row>
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
const { mapGetters, mapMutations, mapActions } = createNamespacedHelpers("stockinArea")
export default {
    name: "StockinFactory",
    async created() {
        this.books = await this.fetchBooks().then(books => books.map(book => ({
            value: book.id,
            label: book.bname + "-" + book.author+ "-" + book.press
        })))
        this.warehouses = await this.fetchWarehouses().then(warehouses => warehouses.map(warehouse => ({
            value: warehouse.id,
            label: warehouse.location
        })))
    },
    data() {
        let validateInteger = (rule, value, callback) => {
            if (!Number.isInteger(+value) || +value <= 0) {
                callback(new Error('必须是正整数'))
            }
            else {
                callback();
            }
        };
        return {
            form: {
                quantity: '',
                bid: '',
                wid: '',
                date: new Date(),
                time: new Date()
            },
            books: [],
            warehouses: [],
            rules: {
                bid: { required: true, message: '请输入入库图书', trigger: 'blur' },
                wid: { required: true, message: '请输入入库仓库', trigger: 'blur' },
                quantity: [
                    { required: true, message: '请输入入库数量', trigger: 'blur' },
                    { validator: validateInteger, trigger: 'blur' }
                ],
                date: { required: true, message: '请输入具体入库日期', trigger: 'blur' },
                time: { required: true, message: '请输入具体入库时间', trigger: 'blur' },
            },
            pickerOptions: {
                disabledDate(time) {
                    return time.getTime() > Date.now()
                }
            }
        }
    },
    computed: {
        ...mapGetters([
            "getDialogFormVisible", "getStockinTotal", "getPageSize"
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
            "fetchSource", "createStockin", "fetchBooks", "fetchWarehouses"
        ]),
        // 提交表单 - 添加新入库记录
        async submitForm(formName) {
            await this.$refs[formName].validate(async valid => {
                if(valid) {
                    const { quantity, bid, wid } = this.form
                    // 合并表单中的日期与时间
                    const date = new Date(this.form.date)
                    const time = new Date(this.form.time)
                    const year = date.getFullYear()
                    const month = date.getMonth()
                    const day = date.getDate()
                    const hours = time.getHours()
                    const minutes = time.getMinutes()
                    const seconds = time.getSeconds()
                    const createtime = new Date(year, month, day, hours, minutes, seconds)
                    // 表单验证通过后...
                    await this.createStockin({
                        quantity,
                        bid,
                        wid,
                        createtime
                    })
                    this.setDataReady(false)
                    await sleep()
                    this.setPage(Math.ceil(this.getStockinTotal / this.getPageSize) || 1)
                    const { stockinList } = await this.fetchSource()
                    this.setSource(stockinList)
                    this.setDialogFormVisible(false)
                    this.setDataReady(true)
                    await this.resetForm(formName)
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

<style scoped>

</style>