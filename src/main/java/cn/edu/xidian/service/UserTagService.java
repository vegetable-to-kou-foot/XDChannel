package cn.edu.xidian.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by 胡广鹏 on 2020/5/15 10:20
 */
public interface UserTagService {

    void addUserTag(Integer aid,String aTagName,Integer aTagValue);
    void addUserTagNone(Integer aid);
    void deleteUserTagByAid(Integer aid,String aTagName);
    String findUserTagByAid(Integer aid);
    List<JSONObject> findAccountTagByTagName(String aTagName);
}
