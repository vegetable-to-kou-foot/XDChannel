package cn.edu.xidian.controller;

import cn.edu.xidian.service.SecurityService;
import cn.edu.xidian.service.UserTagService;
import cn.edu.xidian.service.UtilService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/addUserTag",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String addUserTag(@RequestParam Integer aid,@RequestParam String ssid,
                             @RequestParam String tagName,@RequestParam String tagValue){
        Map<String,Object> ans = new HashMap<>();
        String errCode = " ";
        try{
            errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode))throw new SecurityException();
            userTagService.addUserTag(aid,tagName,Integer.parseInt(tagValue));
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
    @RequestMapping(value = "/deleteUserTag",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String deleteUserTag(@RequestParam Integer aid,@RequestParam String ssid,
                             @RequestParam String tagName){
        Map<String,Object> ans = new HashMap<>();
        String errCode = " ";
        try{
            errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode))throw new SecurityException();
            userTagService.deleteUserTagByAid(aid,tagName);
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
    @RequestMapping(value = "/findUserTag",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String findUserTag(@RequestParam Integer aid,@RequestParam String ssid){
        Map<String,Object> ans = new HashMap<>();
        String errCode = " ";
        try{
            errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode))throw new SecurityException();
            String userTag = userTagService.findUserTagByAid(aid);
            JSONObject userTagJsonObject = JSONObject.parseObject(userTag);
            List<JSONObject> userTagJsonArray = new ArrayList<>(Collections.emptyList());
            for(Map.Entry<String,Object> entry : userTagJsonObject.entrySet()){
                JSONObject tmp = new JSONObject();
                tmp.put(entry.getKey(),entry.getValue());
                userTagJsonArray.add(tmp);
            }
            ans.put("success",1);
            ans.put("ssid",ssid);
            ans.put("errCode",errCode);
            ans.put("userTag",userTagJsonArray);
        }catch (Exception e){
            if ("OK".equals(errCode))errCode = "Exception";
            ans.put("success",0);
            ans.put("ssid"," ");
            ans.put("errCode",errCode);
            ans.put("userTag"," ");
        }
        return JSON.toJSONString(ans);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/findAccountTag",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String findAccountTag(@RequestParam Integer aid,@RequestParam String ssid,
                                @RequestParam String tagName){
        Map<String,Object> ans = new HashMap<>();
        String errCode = " ";
        try{
            errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode))throw new SecurityException();
            List<JSONObject> accTagsList = userTagService.findAccountTagByTagName(tagName);
            ans.put("success",1);
            ans.put("errCode",errCode);
            ans.put("accTags",accTagsList);
        }catch (Exception e){
            if ("OK".equals(errCode))errCode = "Exception";
            ans.put("success",1);
            ans.put("errCode",errCode);
            ans.put("accTags"," ");
        }
        return JSON.toJSONString(ans);
    }
}
