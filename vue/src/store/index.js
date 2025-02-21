import userArea from "./modules/userArea";
import bookArea from "./modules/bookArea";
import warehouseArea from "./modules/warehouseArea";
import historyArea from "./modules/historyArea";
import historyallArea from "./modules/historyallArea";
import stockinArea from "./modules/stockinArea";
import typeArea from "./modules/typeArea";
import recommendArea from "./modules/recommendArea";

export default () => {
    return {
        state: {
            user: null,
        },
        getters: {
            getUser: (state) => state.user,
        },
        mutations: {
            setUser: (state, payload) => { state.user = payload },
            clearUser: (state) => { state.user = null }
        },
        modules: {
            userArea,
            bookArea,
            warehouseArea,
            stockinArea,
            historyArea,
            historyallArea,
            recommendArea,
            typeArea
        }
    }
}