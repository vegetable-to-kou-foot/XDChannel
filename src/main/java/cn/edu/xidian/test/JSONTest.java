package cn.edu.xidian.test;

import cn.edu.xidian.dao.UserInfoDao;
import cn.edu.xidian.domain.Account;
import cn.edu.xidian.domain.UserInfo;
import cn.edu.xidian.service.UserInfoService;
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
        jsonObject.put("ACM",80);
        jsonObject.put("xxx",90);
        jsonObject.put("ACM",90);
        map.put("jsonObject",jsonObject);
        List<JSONObject> accTagsList = userTagService.findAccountTagByTagName("b");
        map.put("accTagList",accTagsList);
        System.out.println(JSON.toJSONString(map));
        System.out.println(JSON.toJSONString(strList));
    }

    @Test
    public void ListTest(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserInfoService userInfoService = (UserInfoService) applicationContext.getBean("userInfoService");
        List<Integer> list = new ArrayList<>(Collections.emptyList());
        //list.add(2);
        //list.add(3);
        List<JSONObject> userInfos = userInfoService.getUserInfoByAidList(list);
        System.out.println(userInfos);
    }

    @Test
    public void DaoTest(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserInfoDao userInfoDao = (UserInfoDao) applicationContext.getBean("userInfoDao");
        List<UserInfo> userInfoList = userInfoDao.findUserInfoAll();
        String userInfoString = userInfoDao.findUserInfoUserInfoByAid(2);
        System.out.println(userInfoList);
        System.out.println(userInfoString);
    }

    @Test
    public void ParseTest(){
        System.out.println(System.getProperty("user.dir"));

    }
}
