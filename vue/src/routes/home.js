export default [
  {
    path: "/",
    component: () => import("@/layouts/home/IndexPage.vue"),
    meta: { title: "首页", icon: "el-icon-pie-chart" , role: [ "USER", "ROOT" ]},
    children: [
      {
        path: "",
        name: "dashboard",
        meta: { auth: true, role: [ "USER", "ROOT" ]},
        component: () => import("@/layouts/home/DashboardArea.vue"),
      }
    ]
  },
  {
    path: "/book",
    component: () => import("@/layouts/home/IndexPage.vue"),
    meta: { title: "图书总览", icon: "el-icon-shopping-cart-2" , role: [ "USER", "ROOT" ]},//book书本
    children: [
      {
        path: "",
        name: "book",
        meta: { auth: true, role: [ "USER", "ROOT" ]},
        component: () => import("@/layouts/home/BookArea.vue"),
      }
    ]
  },
  {
    path: "/type",
    meta: { title: "图书类型", icon: "el-icon-office-building",role: [ "USER", "ROOT" ]},//图书类型
    component: () => import("@/layouts/home/IndexPage.vue"),
    children: [
      {
        path: "",
        name: "type",
        meta: { auth: true, role: [ "USER", "ROOT" ] },
        component: () => import("@/layouts/home/TypeArea.vue"),
      }
    ]
  },
  {
    path: "/recommend",
    meta: { title: "图书推荐", icon: "el-icon-document-copy", role: [ "USER", "ROOT" ]},  //recommend推荐
    component: () => import("@/layouts/home/IndexPage.vue"),    
    children: [
      {
        path: "",
        name: "recommend",
        meta: { auth: true, role: [ "USER","ROOT" ] },
        component: () => import("@/layouts/home/RecommendArea.vue"),
      }
    ]
  },
  {
    path: "/history",
    meta: { title: "借阅记录", icon: "el-icon-document-copy", role: [ "USER", "ROOT" ]},  //history借阅，个人用户借阅
    component: () => import("@/layouts/home/IndexPage.vue"),    //可续期。正常借阅、将要过期、已过期。
    children: [
      {
        path: "",
        name: "history",
        meta: { auth: true, role: [ "USER","ROOT" ] },
        component: () => import("@/layouts/home/HistoryArea.vue"),
      }
    ]
  },
  {
    path: "/historyall",
    meta: { title: "借阅总览", icon: "el-icon-document-copy", role: [ "ROOT" ]},                     
    component: () => import("@/layouts/home/IndexPage.vue"),   
    children: [
      {
        path: "",
        name: "historyall",
        meta: { auth: true, role: [ "ROOT" ] },
        component: () => import("@/layouts/home/HistoryallArea.vue"),
      }
    ]
  },
  

  {
    path: "/warehouse",
    component: () => import("@/layouts/home/IndexPage.vue"),  //+库存详情
    meta: { title: "仓库总览", icon: "el-icon-box" ,role:"ROOT"},
    children: [
      {
        path: "",
        name: "warehouse",
        meta: { auth: true, role: [ "ROOT" ]},
        component: () => import("@/layouts/home/WarehouseArea.vue"),
      }
    ]
  },
  {
    path: "/stockin",
    meta: { title: "入库记录", icon: "el-icon-view" ,role:"ROOT"},        //stockin入库
    component: () => import("@/layouts/home/IndexPage.vue"),
    children: [
      {
        path: "",
        name: "stockin",
        meta: { auth: true, role: [ "ROOT" ] },
        component: () => import("@/layouts/home/StockinArea.vue"),
      }
    ]
  },
  {
    path: "/user",
    component: () => import("@/layouts/home/IndexPage.vue"),
    meta: { title: "用户总览", icon: "el-icon-user" ,role:"ROOT"}, 
    children: [
      {
        path: "",
        name: "user",
        meta: { auth: true, role: [ "ROOT" ] },
        component: () => import("@/layouts/home/UserArea.vue"),
      }
    ]
  }
  
]