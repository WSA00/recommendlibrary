import api from "@/api/api"

export default {
    namespaced: true,
    state() {
        return {
            // 仓库相关配置
            source: new Array(),
            typeTotal: 0,
            type: null,
            // 分页相关配置
            page: 1,
            pageSize: 10,
            // 状态相关配置
            dataReady: false,
            dialogEditVisible: false,
            dialogFormVisible: false,
        }
    },
    getters: {
        getSource: state => state.source,
        getTypeTotal: state => state.typeTotal,
        getType: state => state.type,
        getPage: state => state.page,
        getPageSize: state => state.pageSize,
        getDataReady: state => state.dataReady,
        getDialogEditVisible: state => state.dialogEditVisible,
        getDialogFormVisible: state => state.dialogFormVisible,
    },
    mutations: {
        setSource: (state, payload) => { state.source = payload },
        setTypeTotal: (state, payload) => { state.typeTotal = payload },
        setType: (state, payload) => { state.type = payload },
        setPage: (state, payload) => { state.page = payload },
        setPageSize: (state, payload) => { state.pageSize = payload },
        setDataReady: (state, payload) => { state.dataReady = payload },
        setDialogEditVisible: (state, payload) => { state.dialogEditVisible = payload },
        setDialogFormVisible: (state, payload) => { state.dialogFormVisible = payload },
    },
    actions: {
        // 请求接口 - 分页获取图书类型信息
        async fetchSource({ state, commit }) {   
            const response = await api.get(`/api/type?page=${state.page}&pageSize=${state.pageSize}`,{ token: localStorage.getItem("token") })
            commit("setTypeTotal", response.typeTotal)
            return response
        },
        // 请求接口 - 创建图书类型
        async createType(context, payload) {
            await api.post(`/api/type`, payload, { token: localStorage.getItem("token") })
        },
        // 请求接口 - 获取指定的图书类型信息
        async fetchType(context, payload) {
            const response = await api.get(`/api/type/${payload}`, { token: localStorage.getItem("token") })
            return response.type
        },
        // 请求接口 - 修改图书类型信息
        async updateType(context, payload) {
            await api.patch(`/api/type/${payload.id}`, payload.data, { token: localStorage.getItem("token") })
        },
        // 请求接口 - 删除图书类型信息
        async deleteType(context, payload) {
            await api.delete(`/api/type/${payload}`, { token: localStorage.getItem("token") })
        }
    }
}