package cn.edu.xidian.test;

import cn.edu.xidian.domain.Account;
import cn.edu.xidian.service.UserTagService;
import cn.edu.xidian.service.impl.UserTagServiceImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

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

    @Test
    public void JSONTest4(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserTagService userTagService = (UserTagService) applicationContext.getBean("userTagServiceImpl");
        String tmp = "{}";
        Map map = new HashMap();
        map.put("int",2);
        map.put("string","我");
        List<String> strList = new ArrayList<>();
        strList.add("日常");
        strList.add("美食");
        map.put("strList",strList);
        JSONObject jsonObject = JSONObject.parseObject(tmp);
        map.put("jsonObject",jsonObject);
        List<String> accTagsList = userTagService.findAccountTagByTagName("b");
        map.put("accTagList",accTagsList);
        System.out.println(JSON.toJSONString(map));
        System.out.println(JSON.toJSONString(strList));
    }

    @Test
    public void ListTest(){
        List<Integer> list = new ArrayList<>(Collections.emptyList());
        list.add(1);
        list.add(3);
        list.add(5);
        list.add(7);
        list.add(9);
        list.add(11);
        list.add(13);
        Integer pageSize = 10;
        Integer pageNum = 1;
        Integer st = pageSize*(pageNum-1);
        Integer ed = pageSize*pageNum;
        Integer len = list.size();
        if (st > len) st = len;
        if (st < 0) st = 0;
        if (ed > len) ed = len;
        if (ed < 0) ed = 0;

        list = list.subList(st,ed);
        System.out.println(list);
    }
}
