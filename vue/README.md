# 系统介绍

## 技术栈

- 后端：使用 Nest.Js，基于 Node.Js 和 TypeScript 构建，使用 JWT 鉴权

- 前端：使用 Vue、Element UI、Echarts 和 TailwindCSS 设计用户界面

- 数据库：使用 MySQL，通过 Prisma ORM 和 SQL 进行操作

- 云计算：七牛云对象存储 Kodo、七牛云内容分发网络 CDN

## 运行环境

Node.Js、MySQL

## 如何开始使用


1. 配置 .env.example 示例文件为 .env 文件（补充数据库、七牛云等配置信息）

2. 安装 pnpm

```shell
    npm install pnpm -g
```

3. 安装依赖（根目录和 vue 目录下分别执行）

```shell
    pnpm install
```

4. 数据库迁徙


```shell
    npx prisma generate
```

```shell
    npx prisma migrate dev
```
5. 运行 nest 后台（根目录下执行）

```shell
    pnpm dev
```

6. 运行 vue 前台（vue 目录下执行）

```shell
    pnpm run serve
```

7. 访问项目（http://localhost:8080）

