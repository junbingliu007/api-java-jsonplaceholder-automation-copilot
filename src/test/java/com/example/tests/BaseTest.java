
package com.example.tests;

import com.example.base.ApiClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import com.example.listeners.TestListener;

@Listeners({TestListener.class})
public class BaseTest {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected ApiClient api;
    private static final ThreadLocal<Response> lastResponse = new ThreadLocal<>();

    @BeforeClass
    public void setUp() {
        api = new ApiClient();
    }

    protected Response record(Response resp) {
        lastResponse.set(resp);
        return resp;
    }

    public static Response getLastResponse() {
        return lastResponse.get();
    }

    @Step("断言状态码为 {expected}")
    protected void assertStatus(Response resp, int expected) {
        Assert.assertNotNull(resp, "响应为空");
        Assert.assertEquals(resp.getStatusCode(), expected, "状态码不匹配");
    }
}
