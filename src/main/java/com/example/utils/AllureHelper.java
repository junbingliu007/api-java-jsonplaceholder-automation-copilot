
package com.example.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.restassured.response.Response;

import java.nio.charset.StandardCharsets;

public class AllureHelper {
    public static void attachResponse(String name, Response response) {
        if (response == null) return;
        Allure.addAttachment(name + " - Status", String.valueOf(response.getStatusCode()));
        Allure.addAttachment(name + " - Headers", response.getHeaders().toString());
        Allure.addAttachment(name + " - Body", response.getBody().prettyPrint());
    }

    public static void attachRequestBody(String name, Object body) {
        if (body == null) return;
        Allure.addAttachment(name, String.valueOf(body));
    }

    @Attachment(value = "失败响应截图", type = "image/png")
    public static byte[] attachTextAsPng(String text) {
        try {
            return ScreenshotUtils.renderTextToPng(text);
        } catch (Exception e) {
            return ("Screenshot error: " + e.getMessage()).getBytes(StandardCharsets.UTF_8);
        }
    }
}
