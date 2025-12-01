
@echo off
setlocal enabledelayedexpansion

for /f "tokens=2 delims==" %%a in ('findstr /b /c:"thread.count=" src\mainesources\config.properties') do set THREAD_COUNT=%%a
if "%THREAD_COUNT%"=="" set THREAD_COUNT=5

REM 更新 testng.xml 的线程数
if exist src	estesources	estng.xml (
  powershell -Command "(Get-Content src/test/resources/testng.xml) -replace 'thread-count="\d+"', 'thread-count="%THREAD_COUNT%"' | Set-Content src/test/resources/testng.xml"
)

mvn -q -Dthread.count=%THREAD_COUNT% clean test
mvn -q allure:report

echo Allure 报告已生成: target\sitellure-maven-plugin\index.html
echo 如需本地预览，执行: mvn allure:serve
