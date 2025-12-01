
package com.example.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

@Epic("JSONPlaceholder")
@Feature("Albums")
public class AlbumTests extends BaseTest {

    @Test(description = "获取所有albums")
    @Story("GET /albums")
    public void getAllAlbums() {
        Response resp = record(api.get("/albums"));
        assertStatus(resp, 200);
        Assert.assertTrue(resp.jsonPath().getList("").size() > 0);
    }

    @Test(description = "获取单个album")
    @Story("GET /albums/{id}")
    public void getSingleAlbum() {
        Response resp = record(api.get("/albums/1"));
        assertStatus(resp, 200);
        Assert.assertEquals((int)resp.jsonPath().getInt("id"), 1);
    }

    @Test(description = "创建album")
    @Story("POST /albums")
    public void createAlbum() {
        Map<String,Object> payload = Map.of(
                "title", "new album",
                "userId", 1
        );
        Response resp = record(api.post("/albums", payload));
        assertStatus(resp, 201);
        Assert.assertEquals(resp.jsonPath().getString("title"), "new album");
    }

    @Test(description = "更新album")
    @Story("PUT /albums/{id}")
    public void updateAlbumPut() {
        Map<String,Object> payload = Map.of(
                "id", 1,
                "title", "updated album",
                "userId", 1
        );
        Response resp = record(api.put("/albums/1", payload));
        assertStatus(resp, 200);
        Assert.assertEquals(resp.jsonPath().getString("title"), "updated album");
    }

    @Test(description = "删除album")
    @Story("DELETE /albums/{id}")
    public void deleteAlbum() {
        Response resp = record(api.delete("/albums/1"));
        Assert.assertTrue(resp.getStatusCode() >= 200 && resp.getStatusCode() < 300);
    }
}
