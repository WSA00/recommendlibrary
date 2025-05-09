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

基于智能推荐的图书馆借阅管理系统是广东药科大学2025届计算机科学与技术毕业设计项目

致力于提供一套完整的图书馆借阅、管理的解决方案，提供智能推荐服务，并结合现代技术进行最佳实践。

## 技术栈

- 后端：使用 SpringBoot，基于 SpringBoot + SpringSecurity 构建，使用 JWT 鉴权

- 前端：使用 Vue、Element UI、Echarts 和 TailwindCSS 设计用户界面

- 数据库：使用 MySQL，通过 Mybatis-PLUS 和 SQL 进行操作

- 云计算：七牛云对象存储 Kodo、七牛云内容分发网络 CDN

## 立即体验

> 账号：18877445369（11位）
> 
> 密码：admin123

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/userlogin.jpg)

## 运行环境

Java、MySQL

## 如何开始使用

1. 源代码（通过 zip 解压或 git 拉取）
    
\`\`\`shell
    git clone https://github.com/WSA00/recommendlibrary.git 
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


![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/dashboard.jpg)


- 若有借阅即将到期或已过期，弹出提示
![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/warn.jpg)


### 2. 用户模块

- 实现用户信息管理，包括基本信息和权限控制。
- 支持角色和权限系统，确保安全可控的用户管理。


![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/user.jpg)

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/user2.jpg)

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/user3.jpg)

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/user4.jpg)

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/user5.jpg)


### 3. 图书类型模块

- 提供详细的图书类型信息。

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/type.jpg)

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/type2.jpg)

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/type3.jpg)


### 4. 图书模块

- 提供详细的图书信息，包括作者、出版社、简介等。

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/book.jpg)

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/book1.jpg)

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/book2.jpg)


### 5. 图书查询模块

- 对图书名称或作者模糊查询

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/search.jpg)

### 6. 图书推荐模块

- 图书推荐，包括随机推荐、图书热度推荐、个性推荐。

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/RandomBook.jpg)

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/HotBook.jpg)

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/smartbook.jpg)

### 7. 个人借阅模块

- 用户进行借阅操作，包括创建、续借和归还。
- 记录订单状态，确保用户对借阅流程的了解、掌控。

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/history1.jpg)

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/history2.jpg)


### 8. 借阅总览模块

- 管理借阅记录生命周期，包括创建、续借、归还和删除。
- 记录订单状态，确保对借阅流程的完整掌控。

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/historyall1.jpg)

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/history2.jpg)


### 9. 仓库模块

- 追踪图书库存情况，包括图书的状态和位置。

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/warehouse1.jpg)

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/warehouse2.jpg)

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/warehouse3.jpg)


### 10. 入库记录模块

- 记录入库信息，包括入库数量、时间、图书等。

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/stockin1.jpg)

![image.png](http://sufe58uk3.hn-bkt.clouddn.com/%E7%B3%BB%E7%BB%9F%E6%96%87%E4%BB%B6/%E6%93%8D%E4%BD%9C%E5%85%AC%E5%91%8A%E5%9B%BE/stockin2.jpg)


## 反馈与支持

如果您在使用过程中遇到任何问题、有建议，或者对该项目有任何反馈，请随时联系（2084977781@qq.com）。欢迎您的反馈，以不断改进和优化平台。

这是在大学学习过程中一个重要的项目，代表了本人对专业技术的深入理解和实际运用，感谢您选择该图书馆借阅管理平台！感谢您的支持！


            `
        }
    }
}
</script>