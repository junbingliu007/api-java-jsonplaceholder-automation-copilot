
package com.example.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

@Epic("JSONPlaceholder")
@Feature("Photos")
public class PhotoTests extends BaseTest {

    @Test(description = "获取所有photos")
    @Story("GET /photos")
    public void getAllPhotos() {
        Response resp = record(api.get("/photos"));
        assertStatus(resp, 200);
        Assert.assertTrue(resp.jsonPath().getList("").size() > 0);
    }

    @Test(description = "获取单个photo")
    @Story("GET /photos/{id}")
    public void getSinglePhoto() {
        Response resp = record(api.get("/photos/1"));
        assertStatus(resp, 200);
        Assert.assertEquals((int)resp.jsonPath().getInt("id"), 1);
    }

    @Test(description = "创建photo")
    @Story("POST /photos")
    public void createPhoto() {
        Map<String,Object> payload = Map.of(
                "title", "new photo",
                "url", "http://example.com/photo.jpg",
                "thumbnailUrl", "http://example.com/thumb.jpg",
                "albumId", 1
        );
        Response resp = record(api.post("/photos", payload));
        assertStatus(resp, 201);
        Assert.assertEquals(resp.jsonPath().getString("title"), "new photo");
    }

    @Test(description = "删除photo")
    @Story("DELETE /photos/{id}")
    public void deletePhoto() {
        Response resp = record(api.delete("/photos/1"));
        Assert.assertTrue(resp.getStatusCode() >= 200 && resp.getStatusCode() < 300);
    }
}
