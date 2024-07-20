export default   {
    path: "/search",
    meta: { title: "搜索" },
    component: () => import("@/layouts/home/IndexPage.vue"),
    children: [
      {
        path: "",
        name: "search",
        meta: { auth: true, role: [ "USER", "ROOT" ]},
        component: () => import("@/layouts/search/SearchArea.vue"),
      }
    ]
  }