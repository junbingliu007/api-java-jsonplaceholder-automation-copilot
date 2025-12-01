
package com.example.listeners;

import com.example.tests.BaseTest;
import com.example.utils.AllureHelper;
import io.qameta.allure.Allure;
import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TestListener implements ITestListener, IAnnotationTransformer {

    @Override
    public void onTestFailure(ITestResult result) {
        var resp = BaseTest.getLastResponse();
        if (resp != null) {
            Allure.addAttachment("失败用例最后一次响应状态", String.valueOf(resp.getStatusCode()));
            Allure.addAttachment("失败用例最后一次响应体", resp.getBody().prettyPrint());
            AllureHelper.attachTextAsPng(resp.getBody().prettyPrint());
        }
        if (result.getThrowable() != null) {
            Allure.addAttachment("异常信息", result.getThrowable().toString());
        }
    }

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }

    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestSuccess(ITestResult result) {}
    @Override public void onTestSkipped(ITestResult result) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
}
