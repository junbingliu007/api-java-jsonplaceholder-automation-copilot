
#!/usr/bin/env bash
set -e

# 读取线程数并更新 testng.xml 或使用 surefire 配置
THREAD_COUNT=$(grep '^thread.count=' src/main/resources/config.properties | cut -d'=' -f2)
if [ -z "$THREAD_COUNT" ]; then THREAD_COUNT=5; fi

# 更新 testng.xml 的线程数（如果存在）
if [ -f src/test/resources/testng.xml ]; then
  echo "更新 testng.xml 并行线程数为 ${THREAD_COUNT}"
  sed -i.bak -E "s/thread-count="[0-9]+"/thread-count="${THREAD_COUNT}"/" src/test/resources/testng.xml
fi

# 使用 Maven 运行测试
mvn -q -Dthread.count=${THREAD_COUNT} clean test

# 生成并查看 Allure 报告
if command -v mvn >/dev/null 2>&1; then
  mvn -q allure:report
  echo "Allure 报告已生成: target/site/allure-maven-plugin/index.html"
  echo "如需本地预览，执行: mvn allure:serve"
else
  echo "未检测到 Maven 命令。"
fi
