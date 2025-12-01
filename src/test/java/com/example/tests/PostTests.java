
package com.example.tests;

import com.example.dataprovider.PostDataProvider;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

@Epic("JSONPlaceholder")
@Feature("Posts")
public class PostTests extends BaseTest {

    @Test(description = "获取所有posts")
    @Story("GET /posts")
    public void getAllPosts() {
        Response resp = record(api.get("/posts"));
        assertStatus(resp, 200);
        Assert.assertTrue(resp.jsonPath().getList("").size() > 0, "posts 列表应非空");
    }

    @Test(description = "获取单个post")
    @Story("GET /posts/{id}")
    public void getSinglePost() {
        Response resp = record(api.get("/posts/1"));
        assertStatus(resp, 200);
        Assert.assertEquals((int)resp.jsonPath().getInt("id"), 1);
    }

    @Test(description = "创建post", dataProvider = "postCreateData", dataProviderClass = PostDataProvider.class)
    @Story("POST /posts")
    public void createPost(Object title, Object body, Object userId) {
        Map<String,Object> payload = Map.of(
                "title", String.valueOf(title),
                "body", String.valueOf(body),
                "userId", Integer.parseInt(String.valueOf(userId))
        );
        Response resp = record(api.post("/posts", payload));
        assertStatus(resp, 201);
        Assert.assertEquals(resp.jsonPath().getString("title"), payload.get("title"));
        Assert.assertEquals(resp.jsonPath().getString("body"), payload.get("body"));
    }

    @Test(description = "更新post(完整)")
    @Story("PUT /posts/{id}")
    public void updatePostPut() {
        Map<String,Object> payload = Map.of(
                "id", 1,
                "title", "updated title",
                "body", "updated body",
                "userId", 1
        );
        Response resp = record(api.put("/posts/1", payload));
        assertStatus(resp, 200);
        Assert.assertEquals(resp.jsonPath().getString("title"), "updated title");
    }

    @Test(description = "更新post(部分)")
    @Story("PATCH /posts/{id}")
    public void updatePostPatch() {
        Map<String,Object> payload = Map.of("title", "patched title");
        Response resp = record(api.patch("/posts/1", payload));
        assertStatus(resp, 200);
        Assert.assertEquals(resp.jsonPath().getString("title"), "patched title");
    }

    @Test(description = "删除post")
    @Story("DELETE /posts/{id}")
    public void deletePost() {
        Response resp = record(api.delete("/posts/1"));
        Assert.assertTrue(resp.getStatusCode() >= 200 && resp.getStatusCode() < 300, "删除应返回2xx");
    }
}
