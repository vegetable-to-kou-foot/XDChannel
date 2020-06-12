package cn.edu.xidian.controller;

import cn.edu.xidian.service.FollowService;
import cn.edu.xidian.service.SecurityService;
import cn.edu.xidian.service.UtilService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
            ans.put("success",0);
            ans.put("ssid"," ");
            ans.put("errCode",errCode);
        }
        return JSON.toJSONString(ans);
    }

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
            ans.put("success",0);
            ans.put("ssid"," ");
            ans.put("errCode",errCode);
        }
        return JSON.toJSONString(ans);
    }

    @ResponseBody
    @RequestMapping(value = "/findFollower",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String findFollower(@RequestParam Integer aid, @RequestParam String ssid){
        Map<String,Object> ans = new HashMap<>();
        String errCode = " ";
        try{
            errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode))throw new SecurityException();
            List<Integer> followers = followService.findFollower(aid);
            ans.put("success",1);
            ans.put("errCode",errCode);
            ans.put("followers",followers);
        }catch (Exception e){
            ans.put("success",0);
            ans.put("errCode",errCode);
            ans.put("followers"," ");
        }
        return JSON.toJSONString(ans);
    }

    @ResponseBody
    @RequestMapping(value = "/findBeFollowed",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String findBeFollowed(@RequestParam Integer aid, @RequestParam String ssid){
        Map<String,Object> ans = new HashMap<>();
        String errCode = " ";
        try{
            errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode))throw new SecurityException();
            List<Integer> beFollowers = followService.findBeFollowed(aid);
            ans.put("success",1);
            ans.put("errCode",errCode);
            ans.put("beFollowed",beFollowers);
        }catch (Exception e){
            ans.put("success",0);
            ans.put("errCode",errCode);
            ans.put("beFollowed"," ");
        }
        return JSON.toJSONString(ans);
    }
}
