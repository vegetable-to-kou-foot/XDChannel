package cn.edu.xidian.service.impl;

import cn.edu.xidian.dao.AccountTagDao;
import cn.edu.xidian.dao.UserTagDao;
import cn.edu.xidian.service.UserTagService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 胡广鹏 on 2020/5/15 10:20
 */
@Service("userTagServiceImpl")
public class UserTagServiceImpl implements UserTagService {

    @Autowired
    UserTagDao userTagDao;
    @Autowired
    AccountTagDao accountTagDao;

    @Override
    public void addUserTag(Integer aid, String aTagName, String aTagValue) {
        String userTagString = userTagDao.getUserTagUserTagByAid(aid);
        JSONObject userTagJSON = JSON.parseObject(userTagString);
        userTagJSON.put(aTagName,aTagValue);
        userTagDao.updateUserTagUserTagByAid(aid,userTagJSON.toJSONString());

        Integer atid = accountTagDao.getAccountTagAtidByATagName(aTagName);
        String accTagString = accountTagDao.getAccountTagATagInfoByAtid(atid);
        JSONObject accTagJSON = JSON.parseObject(accTagString);
        accTagJSON.put(aid.toString(),aTagValue);
        accountTagDao.updateAccountTagATagInfoByAtid(atid,accTagJSON.toJSONString());

    }

    @Override
    public void deleteUserTagByAid(Integer aid, String aTagName) {
        String userTagString = userTagDao.getUserTagUserTagByAid(aid);
        JSONObject userTagJSON = JSON.parseObject(userTagString);
        userTagJSON.remove(aTagName);
        userTagDao.updateUserTagUserTagByAid(aid,userTagJSON.toJSONString());

        Integer atid = accountTagDao.getAccountTagAtidByATagName(aTagName);
        String accTagString = accountTagDao.getAccountTagATagInfoByAtid(atid);
        JSONObject accTagJSON = JSON.parseObject(accTagString);
        accTagJSON.remove(aid.toString());
        accountTagDao.updateAccountTagATagInfoByAtid(atid,accTagJSON.toJSONString());
    }

    @Override
    public String findUserTagByAid(Integer aid) {
        return userTagDao.getUserTagUserTagByAid(aid);
    }

    @Override
    public String findAccountTagByTagName(String aTagName) {
        List<String> accTags = accountTagDao.getAccountTagATagNameByATagName(aTagName+"%%");
        return JSON.toJSONString(accTags);
    }
}
