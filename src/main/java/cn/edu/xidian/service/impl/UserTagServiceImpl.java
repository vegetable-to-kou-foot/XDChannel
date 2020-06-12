package cn.edu.xidian.service.impl;

import cn.edu.xidian.dao.AccountTagDao;
import cn.edu.xidian.dao.UserInfoDao;
import cn.edu.xidian.service.UserTagService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 胡广鹏 on 2020/5/15 10:20
 */
@Service("userTagServiceImpl")
public class UserTagServiceImpl implements UserTagService {

    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    AccountTagDao accountTagDao;

    @Override
    public void addUserTag(Integer aid, String aTagName, String aTagValue) {
        String userTagString = userInfoDao.getUserInfoUserTagByAid(aid);
        JSONObject userTagJSON = JSON.parseObject(userTagString);
        userTagJSON.put(aTagName,aTagValue);
        userInfoDao.updateUserInfoUserTagByAid(aid,userTagJSON.toJSONString());

        Integer atid = accountTagDao.getAccountTagAtidByATagName(aTagName);
        if (atid == null){
            accountTagDao.addAccountTag(aTagName,"{}");
            atid = accountTagDao.getAccountTagAtidByATagName(aTagName);
        }
        String accTagString = accountTagDao.getAccountTagATagInfoByAtid(atid);
        JSONObject accTagJSON = JSON.parseObject(accTagString);
        accTagJSON.put(aid.toString(),aTagValue);
        accountTagDao.updateAccountTagATagInfoByAtid(atid,accTagJSON.toJSONString());
    }

    @Override
    public void addUserTagNone(Integer aid) {
        userInfoDao.addUserInfo(aid,"{}","{}"," ");
    }

    @Override
    public void deleteUserTagByAid(Integer aid, String aTagName) {
        String userTagString = userInfoDao.getUserInfoUserTagByAid(aid);
        JSONObject userTagJSON = JSON.parseObject(userTagString);
        userTagJSON.remove(aTagName);
        userInfoDao.updateUserInfoUserTagByAid(aid,userTagJSON.toJSONString());

        Integer atid = accountTagDao.getAccountTagAtidByATagName(aTagName);
        if (atid == null){
            return;
        }
        String accTagString = accountTagDao.getAccountTagATagInfoByAtid(atid);
        JSONObject accTagJSON = JSON.parseObject(accTagString);
        accTagJSON.remove(aid.toString());
        accountTagDao.updateAccountTagATagInfoByAtid(atid,accTagJSON.toJSONString());
    }

    @Override
    public String findUserTagByAid(Integer aid) {
        return userInfoDao.getUserInfoUserTagByAid(aid);
    }

    @Override
    public List<String> findAccountTagByTagName(String aTagName) {
        return accountTagDao.getAccountTagATagNameByATagName("%%" + aTagName+"%%");
    }
}
