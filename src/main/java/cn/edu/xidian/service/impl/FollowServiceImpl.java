package cn.edu.xidian.service.impl;

import cn.edu.xidian.dao.BeFollowedDao;
import cn.edu.xidian.dao.FollowDao;
import cn.edu.xidian.service.FollowService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 胡广鹏 on 2020/5/15 17:19
 */
@Service("followServiceImpl")
public class FollowServiceImpl implements FollowService {

    @Autowired
    FollowDao followDao;
    @Autowired
    BeFollowedDao beFollowedDao;

    @Override
    public void addFollower(Integer aid, Integer followAid) {
        String followInfoString = followDao.getFollowFollowInfoByAid(aid);
        List<Integer> followInfo = JSONObject.parseArray(followInfoString,Integer.class);
        if (!followInfo.contains(followAid)) followInfo.add(followAid);
        followDao.updateFollowFollowInfoByAid(aid,JSON.toJSONString(followInfo));

        String beFollowedInfoString = beFollowedDao.getBeFollowedBeFollowedInfoByAid(followAid);
        List<Integer> beFollowedInfo = JSONObject.parseArray(beFollowedInfoString,Integer.class);
        if (!beFollowedInfo.contains(aid)) beFollowedInfo.add(aid);
        followDao.updateFollowFollowInfoByAid(followAid,JSON.toJSONString(beFollowedInfo));
    }

    @Override
    public void addFollowerNone(Integer aid) {
        followDao.addFollow(aid,"[]");
        beFollowedDao.addBeFollowed(aid,"[]");
    }

    @Override
    public void deleteFollower(Integer aid, Integer followAid) {
        String followInfoString = followDao.getFollowFollowInfoByAid(aid);
        List<Integer> followInfo = JSONObject.parseArray(followInfoString,Integer.class);
        followInfo.remove(followAid);
        followDao.updateFollowFollowInfoByAid(aid,JSON.toJSONString(followInfo));

        String beFollowedInfoString = beFollowedDao.getBeFollowedBeFollowedInfoByAid(followAid);
        List<Integer> beFollowedInfo = JSONObject.parseArray(beFollowedInfoString,Integer.class);
        beFollowedInfo.remove(aid);
        followDao.updateFollowFollowInfoByAid(followAid,JSON.toJSONString(beFollowedInfo));
    }

    @Override
    public List<Integer> findFollower(Integer aid) {
        String followInfoString = followDao.getFollowFollowInfoByAid(aid);
        return JSONObject.parseArray(followInfoString,Integer.class);
    }

    @Override
    public List<Integer> findBeFollowed(Integer aid) {
        String beFollowedInfoString = beFollowedDao.getBeFollowedBeFollowedInfoByAid(aid);
        return JSONObject.parseArray(beFollowedInfoString,Integer.class);
    }
}
