<template>
  <el-dialog 
    :title="'库存详情 - ' + location" 
    :visible.sync="dialogTableVisible"
    top="15rem"
    width="500px"
  > 
    <section class="rounded-xl w-full h-96 overflow-auto">
      <!-- 表格 -->
      <el-table
        :data="source"
        v-if="source?.length"
        stripe
        style="width: 100%"
        header-cell-class-name="text-black" 
      >
        <!-- 占位列 -->
        <el-table-column prop="id" label="图书ID" width="80"></el-table-column>
        <el-table-column prop="bname" label="图书名称" width="120"></el-table-column>
        <el-table-column prop="author" label="作者" width="90"></el-table-column>
        <el-table-column prop="press" label="出版社" width="100"></el-table-column>
        <el-table-column prop="quantity" label="数量"></el-table-column>
      </el-table>
      <el-empty v-if="!source?.length" class="text-gray-300 font-bold">空空如也</el-empty>
    </section>
  </el-dialog>
</template>

<script>
import { createNamespacedHelpers } from "vuex"
const { mapGetters, mapMutations } = createNamespacedHelpers("warehouseArea")
export default {
  name: "WarehouseDetail",
  computed: {
    source() {
      return this.getWarehouseDetail?.source || []
    },
    ...mapGetters([
      "getDialogTableVisible", "getWarehouseDetail", "getWarehouse"
    ]),
    dialogTableVisible: {
      get() {
        return this.getDialogTableVisible
      },
      set(newValue) {
        this.setDialogTableVisible(newValue)
      }
    },
    location() {
      return this.getWarehouse?.location || "";
    }
  },
  methods: {
    ...mapMutations([
      "setDialogTableVisible"
    ])
  }
}
</script>

<style>

</style>
