
<template>
    <el-row>
        <!-- 选项 -->
        <el-menu-item 
            v-for="(item,index) in filteredItems" 
                :key="index"
                :index="item.path"
        >
            <i :class="item.meta.icon"></i>
            <span slot="title">{{ item.meta.title }}</span>
        </el-menu-item>
    </el-row>
</template>

<script>

import home from "@/routes/home.js"
import { store } from "@/plugins/index.js"

export default {
    name: "MenuOptions",
    data() {
        return {
            home
        }
    },
    computed: {
    checkUser() {
        return store.getters.getUser;
    },
    filteredItems() {
        // 检查是否有用户信息且用户角色为 'ROOT'，如果是，则返回所有菜单项
        if (this.checkUser.role === 'ROOT') {
            return this.home;
        } else {
            // 否则，返回包含 'USER' 角色的菜单项
            return this.home.filter(item => item.meta.role.includes('USER'));
        }
        }
    }

}
</script>

<style>