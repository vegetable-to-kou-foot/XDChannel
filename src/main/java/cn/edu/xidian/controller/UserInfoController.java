package cn.edu.xidian.controller;

import cn.edu.xidian.domain.FindUserRequest;
import cn.edu.xidian.domain.UserInfo;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 胡广鹏 on 2020/5/7 0:40
 */
@Controller
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UtilService utilService;

    //该代码来自：https://www.cnblogs.com/xQlover/p/9898255.html
    @CrossOrigin
    @ResponseBody
    @RequestMapping("/editProfilePic")
    public String editProfilePic(@RequestParam Integer aid,@RequestParam String ssid,
                                 MultipartFile upload){
        Map<String,Object> ans = new HashMap<>();
        String errCode = " ";
        try{
            errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode))throw new SecurityException();
            userInfoService.updateUserInfoProfilePicByAid(aid, upload);
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
    @RequestMapping("/findProfilePic")
    public String findProfilePic(@RequestParam Integer aid, @RequestParam String ssid){
        Map<String,Object> ans = new HashMap<>();
        String errCode = " ";
        try{
            errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode))throw new SecurityException();
            String profilePic = userInfoService.getUserInfoProfilePicByAid(aid);
            if (profilePic!=null){
                ans.put("success",1);
                ans.put("errCode",errCode);
                ans.put("profilePic",profilePic);
            }else{
                throw new SecurityException();
            }
        }catch (Exception e){
            if ("OK".equals(errCode))errCode = "Exception";
            ans.put("success",0);
            ans.put("errCode",errCode);
            ans.put("profilePic"," ");
        }
        return JSON.toJSONString(ans);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/editUserInfo",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String editUserInfo(@RequestParam Integer aid,@RequestParam String ssid,
                               @RequestParam String userInfo){
        Map<String,Object> ans = new HashMap<>();
        String errCode = " ";
        try{
            errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode))throw new SecurityException();
            userInfoService.updateUserInfoUserInfoByAid(aid,userInfo);
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
    @RequestMapping(value = "/findUserInfo",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String findUserInfo(@RequestParam Integer aid,@RequestParam String ssid){
        Map<String,Object> ans = new HashMap<>();
        String errCode = " ";
        try{
            errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode))throw new SecurityException();
            String userInfo = userInfoService.getUserInfoUserInfoByAid(aid);
            JSONObject userInfoJsonObject = JSONObject.parseObject(userInfo);
            if (userInfo != null && userInfo.length() > 0){
                ans.put("success",1);
                ans.put("errCode",errCode);
                ans.put("userInfo",userInfoJsonObject);
            }else{
                throw new SecurityException();
            }
        }catch (Exception e){
            if ("OK".equals(errCode))errCode = "Exception";
            ans.put("success",0);
            ans.put("errCode",errCode);
            ans.put("userInfo"," ");
        }
        return JSON.toJSONString(ans);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/findUser",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String findUser(@RequestParam String ssid, FindUserRequest fur){
        Map<String,Object> ans = new HashMap<>();
        String errCode = " ";
        try{
            Integer aid = fur.getAid();
            errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode))throw new SecurityException();
            List<Integer> userAids = userInfoService.findUser(fur);
            List<JSONObject> userInfos = userInfoService.getUserInfoByAidList(userAids);
            ans.put("success",1);
            ans.put("errCode",errCode);
            ans.put("userInfos", userInfos);
        }catch (Exception e){
            if ("OK".equals(errCode))errCode = "Exception";
            ans.put("success",0);
            ans.put("errCode",errCode);
            ans.put("userInfos", " ");
        }
        return JSON.toJSONString(ans);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/findUserByList",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String findUserByList(@RequestParam Integer aid,@RequestParam String ssid,@RequestParam List<Integer> aidList){
        Map<String,Object> ans = new HashMap<>();
        String errCode = " ";
        try{
            errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode))throw new SecurityException();
            List<JSONObject> userInfos = userInfoService.getUserInfoByAidList(aidList);
            ans.put("success",1);
            ans.put("errCode",errCode);
            ans.put("userInfos", userInfos);
        }catch (Exception e){
            if ("OK".equals(errCode))errCode = "Exception";
            ans.put("success",0);
            ans.put("errCode",errCode);
            ans.put("userInfos", " ");
        }
        return JSON.toJSONString(ans);
    }

}
