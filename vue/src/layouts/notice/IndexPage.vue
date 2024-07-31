<template>
    <main class="bg-white rounded-lg p-6 mx-auto" style="max-width: 900px;">
        <el-page-header @back="$router.back()" class="py-4" content="公告"></el-page-header>
        <v-md-preview :text="content"></v-md-preview>
    </main>
</template>

<script>
export default {
    data() {
        return {
            content: `
# 图书馆借阅管理平台介绍

## 项目背景

图书馆借阅管理系统是广东药科大学，21届计算机科学与技术2024学年的课程设计项目

致力于提供一套完整的图书馆借阅、管理的解决方案，并结合现代技术进行最佳实践。

## 技术栈

- 后端：使用 SpringBoot，基于 SpringBoot + SpringSecurity 构建，使用 JWT 鉴权

- 前端：使用 Vue、Element UI、Echarts 和 TailwindCSS 设计用户界面

- 数据库：使用 MySQL，通过 Mybatis-PLUS 和 SQL 进行操作

- 云计算：阿里云云服务器 ECS、七牛云对象存储 Kodo、七牛云内容分发网络 CDN

- 部署：Ubuntu、使用 Nginx 进行反向代理

## 立即体验

[http://takoko.top](http://takoko.top) 

> 账号：18877445369（11位）
> 
> 密码：admin123

![image.png](http://cdn.takoko.top/markdown/login1.jpg)

## 运行环境

Java、MySQL

## 如何开始使用

1. 源代码（通过 zip 解压或 git 拉取）
    
\`\`\`shell
    git clone https://github.com/WSA00/library.git 
\`\`\`

2. 配置，数据库、七牛云等配置信息（后端userUpload()方法）

3. 安装 pnpm

\`\`\`shell
    npm install pnpm -g
\`\`\`

4. 安装依赖

\`\`\`shell
    pnpm install
\`\`\`

5. 数据库迁徙

6. 运行 springboot 后台（library目录下执行）

7. 运行 vue 前台（vue 目录下执行）

\`\`\`shell
    pnpm run serve
\`\`\`

8. 访问项目（http://localhost:8080）

## 主要功能模块

### 1. 工作台模块

- 提供 Echarts 可视化图表来展示销售业务的相关数据


![image.png](http://cdn.takoko.top/markdown/dashboard1.jpg)

### 2. 用户模块

- 实现用户信息管理，包括基本信息和权限控制。
- 支持角色和权限系统，确保安全可控的用户管理。


![image.png](http://cdn.takoko.top/markdown/user1.jpg)

![image.png](http://cdn.takoko.top/markdown/user2.jpg)

![image.png](http://cdn.takoko.top/markdown/user3.jpg)

![image.png](http://cdn.takoko.top/markdown/user4.jpg)


### 3. 图书模块

- 提供详细的图书信息，包括作者、出版社、简介等。

![image.png](http://cdn.takoko.top/markdown/product1.jpg)

![image.png](http://cdn.takoko.top/markdown/product2.jpg)

![image.png](http://cdn.takoko.top/markdown/product3.jpg)


### 4. 图书查询模块

- 对图书名称或作者模糊查询

![image.png](http://cdn.takoko.top/markdown/product1.jpg)

![image.png](http://cdn.takoko.top/markdown/product2.jpg)

![image.png](http://cdn.takoko.top/markdown/product3.jpg)


### 5. 仓库模块

- 追踪图书库存情况，包括图书的状态和位置。

![image.png](http://cdn.takoko.top/markdown/warehouse1.jpg)

![image.png](http://cdn.takoko.top/markdown/warehouse2.jpg)

![image.png](http://cdn.takoko.top/markdown/warehouse3.jpg)


### 6. 借阅模块

- 管理借阅订单生命周期，包括创建、编辑和取消订单。
- 记录订单状态，确保对借阅流程的完整掌控。

![image.png](http://cdn.takoko.top/markdown/order1.jpg)

![image.png](http://cdn.takoko.top/markdown/order2.jpg)

### 7. 入库记录模块

- 记录入库信息，包括入库数量、时间、图书等。

![image.png](http://cdn.takoko.top/markdown/supply1.jpg)

![image.png](http://cdn.takoko.top/markdown/supply2.jpg)


## 反馈与支持

如果您在使用过程中遇到任何问题、有建议，或者对该项目有任何反馈，请随时联系（2084977781@qq.com）。欢迎您的反馈，以不断改进和优化平台。

这是在大学学习过程中一个重要的项目，代表了本人对专业技术的深入理解和实际运用，感谢您选择该图书馆借阅管理平台！感谢您的支持！




            `
        }
    }
}
</script>