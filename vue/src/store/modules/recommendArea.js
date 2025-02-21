import api from "@/api/api"

export default {
    namespaced: true,
    state() {
        return {
            // 图书相关配置
            source: new Array(),
            bookTotal: 0,
            book: null,
            // 上传相关配置
            file: null,
            previewImage: '',
            // 图表相关配置
            chartSource: null,
            // 分页相关配置
            page: 1,
            pageSize: 12,
            // 状态相关配置
            dataReady: false,
            drawer: false,
            dialogFormVisible: false,
        }
    },
    getters: {
        getSource: state => state.source,
        getChartSource: state => state.chartSource,
        getBookTotal: state => state.bookTotal,
        getBook: state => state.book,
        getFile: state => state.file,
        getPreviewImage: state => state.previewImage,
        getPage: state => state.page,
        getPageSize: state => state.pageSize,
        getDataReady: state => state.dataReady,
        getDrawer: state => state.drawer,
        getDialogFormVisible: state => state.dialogFormVisible,
    },
    mutations: {
        setSource: (state, payload) => { state.source = payload },
        setChartSource: (state, payload) => { state.chartSource = payload },
        setBookTotal: (state, payload) => { state.bookTotal = payload },
        setBook: (state, payload) => { state.book = payload },
        setFile: (state, payload) => { state.file = payload },
        setPreviewImage: (state, payload) => { state.previewImage = payload },
        setPage: (state, payload) => { state.page = payload },
        setPageSize: (state, payload) => { state.pageSize = payload },
        setDataReady: (state, payload) => { state.dataReady = payload },
        setDrawer: (state, payload) => { state.drawer = payload },
        setDialogFormVisible: (state, payload) => { state.dialogFormVisible = payload },
    },
    actions: {
        // 请求接口 - 分页获取随机推荐图书信息
        async fetchRandomSource({ state, commit }) {   
            const response = await api.get(`/api/book/random?page=${state.page}&pageSize=${state.pageSize}`,{ token: localStorage.getItem("token") })
            commit("setBookTotal", response.bookTotal)
            return response
        },
        //请求接口 - 分页获取特定类型图书信息
        async fetchSource1({ state, commit }, payload) {   
            let url = `/api/book?page=${state.page}&pageSize=${state.pageSize}`;

            if (payload && payload.tid) {
                url = `/api/book/type?page=${state.page}&pageSize=${state.pageSize}&tid=${payload.tid}`;
            }

            const response = await api.get(url, { token: localStorage.getItem("token") });
            commit("setBookTotal", response.bookTotal);
            return response;
        },
        // 请求接口 - 获取指定图书信息
        async fetchBook(context, payload) {
            return await api.get(`/api/book/${payload}`, { token: localStorage.getItem("token") })
        },
        // 请求接口 - 获取所有图书类型
        async fetchTypes() {
            return await api.get(`/api/type`, { token: localStorage.getItem("token") })
        }
    }
}