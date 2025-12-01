
# API 自动化工程 (Java + TestNG + Allure + REST Assured)

针对 `https://jsonplaceholder.typicode.com` 站点的 API 自动化测试示例，覆盖 GET/POST/PUT/PATCH/DELETE 等用例，支持数据驱动、日志、异常捕获、Allure 报告、失败重试、多线程运行与灵活配置。

## 环境要求
- JDK 17+
- Maven 3.8+
- 可选：安装 Allure Commandline（若使用 `allure serve`）

## 快速开始
```bash
# 克隆/下载后进入项目目录
cd api-automation-jsonplaceholder

# 一键运行（自动并行 & 生成报告）
chmod +x run.sh
./run.sh

# Windows
run.bat
```

## 手动运行
```bash
mvn clean test
mvn allure:report     # 生成报告
mvn allure:serve      # 启动本地预览服务
```

## 配置
`src/main/resources/config.properties`
```properties
base.url=https://jsonplaceholder.typicode.com
retry.count=2
thread.count=5
```
- 修改 `thread.count` 后，一键脚本会同步更新 `testng.xml` 并以并行方式运行。
- 也可通过 `-Dthread.count=8` 命令行覆盖。

## 数据驱动
- 示例：`src/test/resources/testdata/posts.json`
- 由 `PostDataProvider` 读取为 TestNG DataProvider。

## 报告与失败截图
- Allure 自动附加每个请求的 **状态码、Header、响应体**。
- 失败时，监听器会将 **最后一次响应体** 渲染为 PNG 并作为“失败截图”附加到报告中。

## 目录结构
```
api-automation-jsonplaceholder/
├── pom.xml
├── run.sh / run.bat
├── src
│   ├── main
│   │   ├── java/com/example/config/Config.java
│   │   ├── java/com/example/base/ApiClient.java
│   │   └── java/com/example/utils/{AllureHelper,JsonUtils,ScreenshotUtils}.java
│   │   └── resources/config.properties
│   └── test
│       ├── java/com/example/listeners/{RetryAnalyzer,TestListener}.java
│       ├── java/com/example/dataprovider/PostDataProvider.java
│       └── java/com/example/tests/{BaseTest,PostTests,CommentTests,AlbumTests,PhotoTests,TodoTests,UserTests}.java
│       └── resources/testng.xml
│       └── resources/testdata/posts.json
```

## 说明
- JSONPlaceholder 为模拟 API 服务，写操作会返回伪造的成功响应（201/200），实际不会持久化；因此断言以响应结构为主。[官方文档](https://jsonplaceholder.typicode.com/)
- Allure 集成依赖 `allure-testng` 与 `allure-maven` 插件，报告文件输出在 `target/allure-results` 与 `target/site/allure-maven-plugin`。
```
