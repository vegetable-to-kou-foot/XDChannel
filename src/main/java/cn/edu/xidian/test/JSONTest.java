package cn.edu.xidian.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.List;

/**
 * Created by 胡广鹏 on 2020/5/15 17:33
 */
public class JSONTest {

    @Test
    public void JSONTest1(){
        String a = "[\"a\",\"b\"]";
        JSONArray t = JSON.parseArray(a);
        List<String> tmp = JSONObject.parseArray(a,String.class);
        if (!tmp.contains("a")) t.add("a");
        System.out.println(t.toJSONString());
    }

    @Test
    public void JSONTest2(){
        String a = "[\"a\",\"b\"]";
        List<String> tmp = JSONObject.parseArray(a,String.class);
        System.out.println(JSON.toJSONString(tmp));
    }

    @Test
    public void JSONTest3(){
        String a = "{\"a\":\"1\"}";
        JSONObject b = JSON.parseObject(a);
        Integer c = (Integer) b.get("a");
        System.out.println(c);
    }
}
