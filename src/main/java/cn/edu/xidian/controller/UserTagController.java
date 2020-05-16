package cn.edu.xidian.controller;

import cn.edu.xidian.service.SecurityService;
import cn.edu.xidian.service.UserTagService;
import cn.edu.xidian.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 胡广鹏 on 2020/5/15 10:12
 */
@Controller
public class UserTagController {
    @Autowired
    SecurityService securityService;
    @Autowired
    UserTagService userTagService;
    @Autowired
    UtilService utilService;

    @ResponseBody
    @RequestMapping("/addUserTag")
    public String addUserTag(@RequestParam Integer aid,@RequestParam String ssid,
                             @RequestParam String tagName,@RequestParam String tagValue, Model model){
        try{
            String errCode = securityService.checkSsid(aid,ssid);
            if (!errCode.equals("OK")) throw new SecurityException();
            userTagService.addUserTag(aid,tagName,tagValue);
            model.addAttribute("success",1);
        }catch (Exception e){
            model.addAttribute("success",0);
        }
        return utilService.modelToString(model);
    }


    @ResponseBody
    @RequestMapping("/deleteUserTag")
    public String deleteUserTag(@RequestParam Integer aid,@RequestParam String ssid,
                             @RequestParam String tagName, Model model){
        try{
            String errCode = securityService.checkSsid(aid,ssid);
            if (!errCode.equals("OK")) throw new SecurityException();
            userTagService.deleteUserTagByAid(aid,tagName);
            model.addAttribute("success",1);
        }catch (Exception e){
            model.addAttribute("success",0);
        }
        return utilService.modelToString(model);
    }


    @ResponseBody
    @RequestMapping("/findUserTag")
    public String findUserTag(@RequestParam Integer aid,@RequestParam String ssid, Model model){
        try{
            String errCode = securityService.checkSsid(aid,ssid);
            if (!errCode.equals("OK")) throw new SecurityException();
            String userTag = userTagService.findUserTagByAid(aid);
            if (userTag!=null){
                model.addAttribute("success",1);
                model.addAttribute("userTag",userTag);
            }else{
                model.addAttribute("success",0);
                model.addAttribute("userTag"," ");
            }
        }catch (Exception e){
            model.addAttribute("success",0);
            model.addAttribute("userTag"," ");
        }
        return utilService.modelToString(model);
    }


    @ResponseBody
    @RequestMapping("/findAccountTag")
    public String findAccountTag(@RequestParam Integer aid,@RequestParam String ssid,
                                @RequestParam String tagName, Model model){
        try{
            String errCode = securityService.checkSsid(aid,ssid);
            if (!errCode.equals("OK")) throw new SecurityException();
            String tagList = userTagService.findAccountTagByTagName(tagName);
            if (tagList!=null){
                model.addAttribute("success",1);
                model.addAttribute("accTags",tagList);
            }else{
                throw new SecurityException();
            }
        }catch (Exception e){
            model.addAttribute("success",0);
            model.addAttribute("accTags"," ");
        }
        return utilService.modelToString(model);
    }
}
