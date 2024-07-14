import api from "@/api/api"

export default {
    namespaced: true,
    state() {
        return {
            // 供应记录相关配置
            source: new Array(),
            stockinTotal: 0,
            stockin: null,
            // 分页相关配置
            page: 1,
            pageSize: 15,
            // 状态相关配置
            dataReady: false,
            dialogFormVisible: false,
        }
    },
    getters: {
        getSource: state => state.source,
        getStockinTotal: state => state.stockinTotal,
        getStockin: state => state.stockin,
        getPage: state => state.page,
        getPageSize: state => state.pageSize,
        getDataReady: state => state.dataReady,
        getDialogFormVisible: state => state.dialogFormVisible,
    },
    mutations: {
        setSource: (state, payload) => { state.source = payload },
        setStockinTotal: (state, payload) => { state.stockinTotal = payload },
        setStockin: (state, payload) => { state.stockin = payload },
        setPage: (state, payload) => { state.page = payload },
        setPageSize: (state, payload) => { state.pageSize = payload },
        setDataReady: (state, payload) => { state.dataReady = payload },
        setDialogFormVisible: (state, payload) => { state.dialogFormVisible = payload },
    },
    actions: {
        // 请求接口 - 分页获取入库记录
        async fetchSource({ state, commit }) {   
            const response = await api.get(`/api/stockin?page=${state.page}&pageSize=${state.pageSize}`,{ token: localStorage.getItem("token") })
            commit("setStockinTotal", response.stockinTotal)
            return response
        },
        // 请求接口 - 获取所有图书
        async fetchBooks() {
            return await api.get(`/api/book`, { token: localStorage.getItem("token") })
        },
        // 请求接口 - 获取所有仓库
        async fetchWarehouses() {
            return await api.get(`/api/warehouse`, { token: localStorage.getItem("token") })
        },
        // 请求接口 - 创建入库记录
        async createStockin(context, payload) {
            await api.post(`/api/stockin`, payload, { token: localStorage.getItem("token") })
        },
        // 请求接口 - 删除入库记录
        async deleteStockin(context, payload) {
            await api.delete(`/api/stockin/${payload}`, { token: localStorage.getItem("token") })
        }
    }
}