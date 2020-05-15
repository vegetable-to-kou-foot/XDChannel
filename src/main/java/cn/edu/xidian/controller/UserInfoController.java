package cn.edu.xidian.controller;

import cn.edu.xidian.service.SecurityService;
import cn.edu.xidian.service.UserInfoService;
import cn.edu.xidian.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

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
    @ResponseBody
    @RequestMapping("/editProfilePic")
    public String editProfilePic(@RequestParam Integer aid,@RequestParam String ssid,
                                 MultipartFile upload,HttpServletRequest request){
        try{
            String errCode = securityService.checkSsid(aid,ssid);
            if (!errCode.equals("OK")){
                return "{\"success\":0}";
            }
            userInfoService.updateUserInfoProfilePicByAid(aid, upload, request);
            return "{\"success\":1}";
        }catch (Exception e){
            return "{\"success\":0}";
        }
    }

    @ResponseBody
    @RequestMapping("/findProfilePic")
    public String findProfilePic(@RequestParam Integer aid, @RequestParam String ssid, Model model){
        try{
            String errCode = securityService.checkSsid(aid,ssid);
            if (!errCode.equals("OK")){
                throw new SecurityException();
            }
            String profilePic = userInfoService.getUserInfoProfilePicByAid(aid);
            if (profilePic!=null){
                model.addAttribute("success",1);
                model.addAttribute("profilePic",profilePic);
            }else{
                throw new SecurityException();
            }
        }catch (Exception e){
            model.addAttribute("success",0);
            model.addAttribute("profilePic"," ");
        }
        return utilService.modelToString(model);
    }

    @ResponseBody
    @RequestMapping("/editUserInfo")
    public String editUserInfo(@RequestParam Integer aid,@RequestParam String ssid,
                               @RequestParam String userInfo,Model model){
        try{
            String errCode = securityService.checkSsid(aid,ssid);
            if (!errCode.equals("OK")){
                throw new SecurityException();
            }
            userInfoService.updateUserInfoUserInfoByAid(aid,userInfo);
            model.addAttribute("success",1);
        }catch (Exception e){
            model.addAttribute("success",0);
        }
        return utilService.modelToString(model);
    }

    @ResponseBody
    @RequestMapping("/findUserInfo")
    public String findUserInfo(@RequestParam Integer aid,@RequestParam String ssid,Model model){
        try{
            String errCode = securityService.checkSsid(aid,ssid);
            if (!errCode.equals("OK")){
                throw new SecurityException();
            }
            String userInfo = userInfoService.getUserInfoUserInfoByAid(aid);
            if (userInfo != null){
                model.addAttribute("success",1);
                model.addAttribute("userInfo",userInfo);
            }else{
                throw new SecurityException();
            }
        }catch (Exception e){
            model.addAttribute("success",0);
            model.addAttribute("userInfo"," ");
        }
        return utilService.modelToString(model);
    }

}
