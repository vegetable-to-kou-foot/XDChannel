package cn.edu.xidian.controller;

import cn.edu.xidian.domain.UserInfo;
import cn.edu.xidian.service.FollowService;
import cn.edu.xidian.service.SecurityService;
import cn.edu.xidian.service.UserInfoService;
import cn.edu.xidian.service.UtilService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 胡广鹏 on 2020/5/15 16:59
 */
@Controller
public class FollowController {

    @Autowired
    SecurityService securityService;
    @Autowired
    UtilService utilService;
    @Autowired
    FollowService followService;
    @Autowired
    UserInfoService userInfoService;

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/addFollower",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String addFollower(@RequestParam Integer aid, @RequestParam String ssid,
                              @RequestParam Integer followAid){
        Map<String,Object> ans = new HashMap<>();
        String errCode = " ";
        try{
            errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode))throw new SecurityException();
            followService.addFollower(aid,followAid);
            ssid = securityService.refreshSsid(aid);
            ans.put("success",1);
            ans.put("ssid",ssid);
            ans.put("errCode",errCode);
        }catch (Exception e){
            if ("OK".equals(errCode))errCode = "Exception";
            ans.put("success",0);
            ans.put("ssid"," ");
            ans.put("errCode",errCode);
        }
        return JSON.toJSONString(ans);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/deleteFollower",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String deleteFollower(@RequestParam Integer aid, @RequestParam String ssid,
                              @RequestParam Integer followAid){
        Map<String,Object> ans = new HashMap<>();
        String errCode = " ";
        try{
            errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode))throw new SecurityException();
            followService.deleteFollower(aid,followAid);
            ssid = securityService.refreshSsid(aid);
            ans.put("success",1);
            ans.put("ssid",ssid);
            ans.put("errCode",errCode);
        }catch (Exception e){
            if ("OK".equals(errCode))errCode = "Exception";
            ans.put("success",0);
            ans.put("ssid"," ");
            ans.put("errCode",errCode);
        }
        return JSON.toJSONString(ans);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/findFollower",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String findFollower(@RequestParam Integer aid, @RequestParam String ssid,
                               @RequestParam Integer pageSize, @RequestParam Integer pageNum){
        Map<String,Object> ans = new HashMap<>();
        String errCode = " ";
        try{
            errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode))throw new SecurityException();
            List<Integer> followers = followService.findFollower(aid);
            followers = getSubList(followers,pageSize,pageNum);
            List<JSONObject> userInfos = userInfoService.getUserInfoByAidList(followers);
            ans.put("success",1);
            ans.put("errCode",errCode);
            ans.put("followers",userInfos);
        }catch (Exception e){
            if ("OK".equals(errCode))errCode = "Exception";
            ans.put("success",0);
            ans.put("errCode",errCode);
            ans.put("followers"," ");
        }
        return JSON.toJSONString(ans);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/findBeFollowed",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String findBeFollowed(@RequestParam Integer aid, @RequestParam String ssid,
                                 @RequestParam Integer pageSize, @RequestParam Integer pageNum){
        Map<String,Object> ans = new HashMap<>();
        String errCode = " ";
        try{
            errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode))throw new SecurityException();
            List<Integer> beFollowers = followService.findBeFollowed(aid);
            beFollowers = getSubList(beFollowers,pageSize,pageNum);
            List<JSONObject> userInfos = userInfoService.getUserInfoByAidList(beFollowers);
            ans.put("success",1);
            ans.put("errCode",errCode);
            ans.put("beFollowed",userInfos);
        }catch (Exception e){
            if ("OK".equals(errCode))errCode = "Exception";
            ans.put("success",0);
            ans.put("errCode",errCode);
            ans.put("beFollowed"," ");
        }
        return JSON.toJSONString(ans);
    }

    private List<Integer> getSubList(List<Integer> query, Integer pageSize, Integer pageNum){
        Integer st = pageSize*(pageNum-1);
        Integer ed = pageSize*pageNum;
        Integer len = query.size();
        if (st > len) st = len;
        if (st < 0) st = 0;
        if (ed > len) ed = len;
        if (ed < 0) ed = 0;
        return query.subList(st,ed);
    }
}
