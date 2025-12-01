
package com.example.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

@Epic("JSONPlaceholder")
@Feature("Comments")
public class CommentTests extends BaseTest {

    @Test(description = "获取所有comments")
    @Story("GET /comments")
    public void getAllComments() {
        Response resp = record(api.get("/comments"));
        assertStatus(resp, 200);
        Assert.assertTrue(resp.jsonPath().getList("").size() > 0);
    }

    @Test(description = "获取单个comment")
    @Story("GET /comments/{id}")
    public void getSingleComment() {
        Response resp = record(api.get("/comments/1"));
        assertStatus(resp, 200);
        Assert.assertEquals((int)resp.jsonPath().getInt("id"), 1);
    }

    @Test(description = "创建comment")
    @Story("POST /comments")
    public void createComment() {
        Map<String,Object> payload = Map.of(
                "name", "foo",
                "email", "foo@example.com",
                "body", "bar",
                "postId", 1
        );
        Response resp = record(api.post("/comments", payload));
        assertStatus(resp, 201);
        Assert.assertEquals(resp.jsonPath().getString("name"), "foo");
    }

    @Test(description = "更新comment")
    @Story("PUT /comments/{id}")
    public void updateCommentPut() {
        Map<String,Object> payload = Map.of(
                "id", 1,
                "name", "updated",
                "email", "updated@example.com",
                "body", "updated body",
                "postId", 1
        );
        Response resp = record(api.put("/comments/1", payload));
        assertStatus(resp, 200);
        Assert.assertEquals(resp.jsonPath().getString("name"), "updated");
    }

    @Test(description = "删除comment")
    @Story("DELETE /comments/{id}")
    public void deleteComment() {
        Response resp = record(api.delete("/comments/1"));
        Assert.assertTrue(resp.getStatusCode() >= 200 && resp.getStatusCode() < 300);
    }
}
