
package com.example.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

@Epic("JSONPlaceholder")
@Feature("Users")
public class UserTests extends BaseTest {

    @Test(description = "获取所有users")
    @Story("GET /users")
    public void getAllUsers() {
        Response resp = record(api.get("/users"));
        assertStatus(resp, 200);
        Assert.assertTrue(resp.jsonPath().getList("").size() > 0);
    }

    @Test(description = "获取单个user")
    @Story("GET /users/{id}")
    public void getSingleUser() {
        Response resp = record(api.get("/users/1"));
        assertStatus(resp, 200);
        Assert.assertEquals((int)resp.jsonPath().getInt("id"), 1);
    }

    @Test(description = "创建user")
    @Story("POST /users")
    public void createUser() {
        Map<String,Object> payload = Map.of(
                "name", "new user",
                "username", "newuser",
                "email", "newuser@example.com"
        );
        Response resp = record(api.post("/users", payload));
        assertStatus(resp, 201);
        Assert.assertEquals(resp.jsonPath().getString("name"), "new user");
    }

    @Test(description = "删除user")
    @Story("DELETE /users/{id}")
    public void deleteUser() {
        Response resp = record(api.delete("/users/1"));
        Assert.assertTrue(resp.getStatusCode() >= 200 && resp.getStatusCode() < 300);
    }
}
