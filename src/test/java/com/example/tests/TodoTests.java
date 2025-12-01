
package com.example.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

@Epic("JSONPlaceholder")
@Feature("Todos")
public class TodoTests extends BaseTest {

    @Test(description = "获取所有todos")
    @Story("GET /todos")
    public void getAllTodos() {
        Response resp = record(api.get("/todos"));
        assertStatus(resp, 200);
        Assert.assertTrue(resp.jsonPath().getList("").size() > 0);
    }

    @Test(description = "获取单个todo")
    @Story("GET /todos/{id}")
    public void getSingleTodo() {
        Response resp = record(api.get("/todos/1"));
        assertStatus(resp, 200);
        Assert.assertEquals((int)resp.jsonPath().getInt("id"), 1);
    }

    @Test(description = "创建todo")
    @Story("POST /todos")
    public void createTodo() {
        Map<String,Object> payload = Map.of(
                "title", "new todo",
                "completed", false,
                "userId", 1
        );
        Response resp = record(api.post("/todos", payload));
        assertStatus(resp, 201);
        Assert.assertEquals(resp.jsonPath().getString("title"), "new todo");
    }

    @Test(description = "更新todo")
    @Story("PATCH /todos/{id}")
    public void updateTodoPatch() {
        Map<String,Object> payload = Map.of(
                "completed", true
        );
        Response resp = record(api.patch("/todos/1", payload));
        assertStatus(resp, 200);
        Assert.assertTrue(resp.jsonPath().getBoolean("completed"));
    }

    @Test(description = "删除todo")
    @Story("DELETE /todos/{id}")
    public void deleteTodo() {
        Response resp = record(api.delete("/todos/1"));
        Assert.assertTrue(resp.getStatusCode() >= 200 && resp.getStatusCode() < 300);
    }
}
