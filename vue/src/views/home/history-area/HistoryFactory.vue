<template>
    <!-- 添加借阅记录 -->
    <el-dialog 
        title="添加借阅记录" 
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
            <!-- 请选择用户 -->
            <el-form-item label="用户" prop="uid">
                <el-select v-model="form.uid" filterable placeholder="请选择用户" clearable>
                    <el-option
                        v-for="item in users"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    ></el-option>
                </el-select>
            </el-form-item>
            <!-- 请选择图书 -->
            <el-form-item label="图书" prop="bid">
                <el-select v-model="form.bid" @change="changeBook" filterable placeholder="请选择图书" clearable>
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
                <el-select v-model="form.wid" @change="changeWarehouse" filterable placeholder="请选择仓库" clearable>
                    <el-option
                        v-for="item in warehouses"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    ></el-option>
                </el-select>
            </el-form-item>
            <!-- 请选择成交时间 -->
            <el-form-item label="成交时间" required>
                <el-row class="flex justify-start max-w-md">
                    <el-form-item prop="date">
                        <el-date-picker 
                            type="date" 
                            placeholder="选择日期" 
                            v-model="form.date" 
                            class="mr-6"
                            :picker-options="pickerOptions"
                        ></el-date-picker>
                    </el-form-item>
                    <el-form-item prop="time">
                        <el-time-picker placeholder="选择时间" v-model="form.time"></el-time-picker>
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
const { mapGetters, mapMutations, mapActions } = createNamespacedHelpers("historyArea")
export default {
    name: "HistoryFactory",
    async created() {
        this.users = await this.fetchUsers().then(users => users.map(user => ({
            value: user.id,
            label: user.username
        })))
        this.products = await this.fetchBooks().then(books => books.map(book => ({
            value: book.id,
            label: book.bname + "-" + book.author+ "-" + book.press
        })))
        this.warehouses = await this.fetchWarehouses().then(warehouses => warehouses.map(warehouse => ({
            value: warehouse.id,
            label: warehouse.location
        })))
    },
    data() {
        return {
            form: {
                uid: '',
                bid: '',
                wid: '',
                date: new Date(),
                time: new Date()
            },
            users: [],
            books: [],
            warehouses: [],
            rules: {
                uid: { required: true, message: '请输入当前借阅申请人', trigger: 'blur' },
                bid: { required: true, message: '请输入当前借阅图书', trigger: 'blur' },
                wid: { required: true, message: '请输入当前借阅图书所属仓库', trigger: 'blur' },
                date: { required: true, message: '请输入具体借阅日期', trigger: 'blur' },
                time: { required: true, message: '请输入具体借阅时间', trigger: 'blur' },
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
            "getDialogFormVisible", "getHistoryTotal", "getPageSize"
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
            "fetchSource", "createHistory", "fetchUsers", "fetchBooks", "fetchWarehouses", "fetchBookInventory", "fetchWarehouseInventory"
        ]),
        // 提交表单 - 添加新供应记录
        async submitForm(formName) {
            await this.$refs[formName].validate(async valid => {
                if(valid) {
                    const { uid, bid, wid } = this.form
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
                    await this.createHistory({
                        uid,
                        bid,
                        wid,
                        createtime
                    })
                    this.setDataReady(false)
                    await sleep()
                    this.setPage(Math.ceil(this.getHistoryTotal / this.getPageSize) || 0)
                    const { historyList } = await this.fetchSource()
                    this.setSource(historyList)
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
        },
        // 切换产品
        async changeBook(id) {
            if(id) {
                this.warehouses = await this.fetchBookInventory(id)
            .then(warehouses => warehouses.map(warehouse => ({
                value: warehouse.id,
                label: warehouse.location
            })))
            }
            else {
                this.warehouses = await this.fetchWarehouses()
                .then(warehouses => warehouses.map(warehouse => ({
                    value: warehouse.id,
                    label: warehouse.location
                })))
            }
        },
        // 切换产品
        async changeWarehouse(id) {
            if(id) {
                this.books = await this.fetchWarehouseInventory(id)
                .then(books => books.map(book => ({
                    value: book.id,
                    label: book.bname + "-" + book.author+ "-" + book.press
                })))
            }
            else {
                this.books = await this.fetchBooks().then(books => books.map(book => ({
                    value: book.id,
                    label: book.bname + "-" + book.author+ "-" + book.press
                })))
            }
        }
    }
}
</script>

<style>

</style>