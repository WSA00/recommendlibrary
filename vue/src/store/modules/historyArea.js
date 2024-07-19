import api from "@/api/api"

export default {
    namespaced: true,
    state() {
        return {
            // 用户相关配置
            source: new Array(),
            historyTotal: 0,
            history: null,
            // 分页相关配置
            page: 1,
            pageSize: 15,
            // 状态相关配置
            dataReady: false,
            dialogTableVisible: false,
            dialogFormVisible: false,
        }
    },
    getters: {
        getSource: state => state.source,
        getHistoryTotal: state => state.historyTotal,
        getHistory: state => state.history,
        getPage: state => state.page,
        getPageSize: state => state.pageSize,
        getDataReady: state => state.dataReady,
        getDialogTableVisible: state => state.dialogTableVisible,
        getDialogFormVisible: state => state.dialogFormVisible,
    },
    mutations: {
        setSource: (state, payload) => { state.source = payload },
        setHistoryTotal: (state, payload) => { state.historyTotal = payload },
        setHistory: (state, payload) => { state.history = payload },
        setPage: (state, payload) => { state.page = payload },
        setPageSize: (state, payload) => { state.pageSize = payload },
        setDataReady: (state, payload) => { state.dataReady = payload },
        setDialogTableVisible: (state, payload) => { state.dialogTableVisible = payload },
        setDialogFormVisible: (state, payload) => { state.dialogFormVisible = payload },
    },
    actions: {
        // 请求接口 - 分页获取借阅记录信息//改为登录用户借阅信息
        async fetchSource({ state, commit }) {   
            const response = await api.get(`/api/history?page=${state.page}&pageSize=${state.pageSize}`,{ token: localStorage.getItem("token") })
            commit("setHistoryTotal", response.historyTotal)
            return response
        },
        // 请求接口 - 获取所有用户//需要改为登录用户
        async fetchUsers() {
            return await api.get(`/api/user`, { token: localStorage.getItem("token") })
        },
        // 请求接口 - 获取所有图书
        async fetchBooks() {
            return await api.get(`/api/book`, { token: localStorage.getItem("token") })
        },
        // 请求接口 - 获取所有仓库
        async fetchWarehouses() {
            return await api.get(`/api/warehouse`, { token: localStorage.getItem("token") })
        },
        // 请求接口 - 获取相应图书的库存 
        async fetchBookInventory(context, payload) {
            return await api.get(`/api/book/${payload}/inventory`, { token: localStorage.getItem("token") })
        },
        // 请求接口 - 获取相应仓库的库存 
        async fetchWarehouseInventory(context, payload) {
            return await api.get(`/api/warehouse/${payload}/inventory`, { token: localStorage.getItem("token") })
        },
        // 请求接口 - 创建借阅记录
        async createHistory(context, payload) {
            await api.post(`/api/history`, payload, { token: localStorage.getItem("token") })
        },
        // 请求接口 - 续借操作
        async continueHistory(context, payload) {
            await api.patch(`/api/history/${payload}/continue`,`${payload}`, { token: localStorage.getItem("token") })
        },
        // 请求接口 - 归还操作
        async dealHistory(context, payload) {
            await api.patch(`/api/history/${payload}/deal`,`${payload}`, { token: localStorage.getItem("token") })
        }


    }
}