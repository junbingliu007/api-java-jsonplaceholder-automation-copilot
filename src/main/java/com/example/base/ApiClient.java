
package com.example.base;

import com.example.config.Config;
import com.example.utils.AllureHelper;
import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiClient {
    private final RequestSpecification baseSpec;

    public ApiClient() {
        baseSpec = new RequestSpecBuilder()
                .setBaseUri(Config.get("base.url"))
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    @Step("GET {path}")
    public Response get(String path) {
        Response resp = given().spec(baseSpec)
                .when().get(path);
        AllureHelper.attachResponse("GET " + path, resp);
        return resp;
    }

    @Step("GET {path} with query params")
    public Response get(String path, java.util.Map<String, Object> queryParams) {
        Response resp = given().spec(baseSpec)
                .queryParams(queryParams)
                .when().get(path);
        AllureHelper.attachResponse("GET " + path + "?" + queryParams, resp);
        return resp;
    }

    @Step("POST {path}")
    public Response post(String path, Object body) {
        Response resp = given().spec(baseSpec)
                .body(body)
                .when().post(path);
        AllureHelper.attachRequestBody("POST " + path + " body", body);
        AllureHelper.attachResponse("POST " + path, resp);
        return resp;
    }

    @Step("PUT {path}")
    public Response put(String path, Object body) {
        Response resp = given().spec(baseSpec)
                .body(body)
                .when().put(path);
        AllureHelper.attachRequestBody("PUT " + path + " body", body);
        AllureHelper.attachResponse("PUT " + path, resp);
        return resp;
    }

    @Step("PATCH {path}")
    public Response patch(String path, Object body) {
        Response resp = given().spec(baseSpec)
                .body(body)
                .when().patch(path);
        AllureHelper.attachRequestBody("PATCH " + path + " body", body);
        AllureHelper.attachResponse("PATCH " + path, resp);
        return resp;
    }

    @Step("DELETE {path}")
    public Response delete(String path) {
        Response resp = given().spec(baseSpec)
                .when().delete(path);
        AllureHelper.attachResponse("DELETE " + path, resp);
        return resp;
    }
}
