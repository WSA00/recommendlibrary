<template>
    <!-- 编辑图书类型信息 -->
    <el-dialog 
        title="编辑图书类型信息" 
        :visible.sync="dialogEditVisible"
        center
        width="600px"
    >
        <el-form 
            :model="getType" 
            v-if="getType" 
            ref="form" 
            status-icon
            label-width="120px" 
            label-position="left"
        >
            <el-form-item label="图书类型名称" prop="tname" class="w-full">
                <el-input type="textarea" v-model="getType.tname" rows="1"></el-input>
            </el-form-item>
        </el-form>
        <!-- 取消 & 保存 -->
        <section slot="footer" class="dialog-footer">
            <el-button @click="setDialogEditVisible(false)">取 消</el-button>
            <el-button @click="handleTypeChange" type="primary">保 存</el-button>
        </section>
    </el-dialog>
</template>

<script>
import { sleep } from "@/util/sleep"
import { createNamespacedHelpers } from "vuex"
const { mapGetters, mapMutations, mapActions } = createNamespacedHelpers("typeArea")
export default {
    name: "TypeEdit",
    computed: {
        ...mapGetters([
            "getDialogEditVisible", "getType"
        ]),
        dialogEditVisible: {
            get() {
                return this.getDialogEditVisible
            },
            set(newValue) {
                this.setDialogEditVisible(newValue)
            }
        }
    },
    methods: {
        ...mapMutations([
            "setDialogEditVisible", "setDataReady", "setSource"
        ]),
        ...mapActions([
            "updateType", "fetchSource"
        ]),
        // 处理修改图书类型
        async handleTypeChange() {
            const type = this.getType
            const { id, tname } = type
            await this.updateType({
                id,
                data: {
                    tname
                }
            })
            this.setDialogEditVisible(false)
            this.setDataReady(false)
            const { typeList } = await this.fetchSource()
            this.setSource(typeList)
            await sleep()
            this.setDataReady(true)
        }
    }
}
</script>

<style>

</style>