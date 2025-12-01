
package com.example.dataprovider;

import com.example.utils.JsonUtils;
import org.testng.annotations.DataProvider;

import java.util.List;
import java.util.Map;

public class PostDataProvider {
    @DataProvider(name = "postCreateData")
    public Object[][] postCreateData() {
        List<Map<String,Object>> list = JsonUtils.readListMapFromResource("testdata/posts.json");
        Object[][] data = new Object[list.size()][3];
        for (int i = 0; i < list.size(); i++) {
            Map<String,Object> m = list.get(i);
            data[i][0] = m.get("title");
            data[i][1] = m.get("body");
            data[i][2] = m.get("userId");
        }
        return data;
    }
}
