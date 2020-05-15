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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    @RequestMapping("/addFollower")
    public String addFollower(@RequestParam Integer aid, @RequestParam String ssid,
                              @RequestParam Integer followAid, Model model){
        try{
            String errCode = securityService.checkSsid(aid,ssid);
            if (!errCode.equals("OK")) throw new SecurityException();
            followService.addFollower(aid,followAid);
            model.addAttribute("success",1);
        }catch (Exception e){
            model.addAttribute("success",0);
        }
        return utilService.modelToString(model);
    }

    @ResponseBody
    @RequestMapping("/deleteFollower")
    public String deleteFollower(@RequestParam Integer aid, @RequestParam String ssid,
                              @RequestParam Integer followAid, Model model){
        try{
            String errCode = securityService.checkSsid(aid,ssid);
            if (!errCode.equals("OK")) throw new SecurityException();
            followService.deleteFollower(aid,followAid);
            model.addAttribute("success",1);
        }catch (Exception e){
            model.addAttribute("success",0);
        }
        return utilService.modelToString(model);
    }

    @ResponseBody
    @RequestMapping("/findFollower")
    public String findFollower(@RequestParam Integer aid, @RequestParam String ssid){
        JSONObject ans = new JSONObject();
        try{
            String errCode = securityService.checkSsid(aid,ssid);
            if (!errCode.equals("OK")) throw new SecurityException();
            List<Integer> followers = followService.findFollower(aid);
            String followersJSON = JSON.toJSONString(followers);
            ans.put("success",1);
            ans.put("followers",followersJSON);
        }catch (Exception e){
            ans.put("success",0);
            ans.put("followers"," ");
        }
        return ans.toJSONString();
    }

    @ResponseBody
    @RequestMapping("/findBeFollowed")
    public String findBeFollowed(@RequestParam Integer aid, @RequestParam String ssid){
        JSONObject ans = new JSONObject();
        try{
            String errCode = securityService.checkSsid(aid,ssid);
            if (!errCode.equals("OK")) throw new SecurityException();
            List<Integer> beFollowers = followService.findBeFollowed(aid);
            String beFollowersJSON = JSON.toJSONString(beFollowers);
            ans.put("success",1);
            ans.put("beFollowed",beFollowersJSON);
        }catch (Exception e){
            ans.put("success",0);
            ans.put("beFollowed"," ");
        }
        return ans.toJSONString();
    }
}
