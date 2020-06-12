package cn.edu.xidian.controller;

import cn.edu.xidian.domain.Broadcast;
import cn.edu.xidian.domain.FindBroadcastRequset;
import cn.edu.xidian.service.BroadcastService;
import cn.edu.xidian.service.SecurityService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by 胡广鹏 on 2020/5/15 22:44
 */
@Controller
public class BroadcastController {
    @Autowired
    SecurityService securityService;
    @Autowired
    BroadcastService broadcastService;

    @ResponseBody
    @RequestMapping(value="/addBroadcast",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String addBroadcast(@RequestParam Integer aid, @RequestParam String ssid,@RequestParam Integer fid,
                               @RequestParam String bcScript,@RequestParam String bcTag,
                               @RequestParam long timestp){
        Map<String,Object> ans = new HashMap<>();
        String errCode = " ";
        try{
            errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode))throw new SecurityException();
            JSONArray bcTagJSON = JSON.parseArray(bcTag);
            List<String> list_tmp = bcTagJSON.toJavaList(String.class);
            HashSet<String> hs_tmp = new HashSet<>(list_tmp);
            bcTag = JSON.toJSONString(hs_tmp);
            Broadcast bc = new Broadcast(null,fid,aid,bcScript,0,"[]",bcTag,timestp," ");
            broadcastService.addBroadcast(bc);
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
    @RequestMapping(value = "/deleteBroadcast",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String deleteBroadcast(@RequestParam Integer aid,@RequestParam String ssid,@RequestParam Integer bid){
        Map<String,Object> ans = new HashMap<>();
        String errCode = " ";
        try{
            errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode))throw new SecurityException();
            broadcastService.deleteBroadcast(bid,aid);
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
    @RequestMapping(value = "/editBroadcast",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String editBroadcast(@RequestParam Integer aid, @RequestParam String ssid,@RequestParam Integer bid,
                               @RequestParam String bcScript, @RequestParam long timestp){
        Map<String,Object> ans = new HashMap<>();
        String errCode = " ";
        try{
            errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode))throw new SecurityException();
            broadcastService.editBroadcast(bid,bcScript,timestp);
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
    @RequestMapping(value = "/findBroadcast",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    //todo:所有内容加上limit分页查询功能
    //pojol类会自动封装，不需要加注解，加了反而会出错
    public String findBroadcast(@RequestParam Integer aid, @RequestParam String ssid, FindBroadcastRequset fbr){
        Map<String,Object> ans = new HashMap<>();
        String errCode = " ";
        try{
            errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode))throw new SecurityException();
            List<Broadcast> broadcasts = broadcastService.findBroadcast(fbr);
            ans.put("success",1);
            ans.put("broadcasts",JSON.toJSONString(broadcasts));
            ans.put("errCode",errCode);
        }catch (Exception e){
            ans.put("success",0);
            ans.put("broadcasts"," ");
            ans.put("errCode",errCode);
        }
        return JSON.toJSONString(ans);
    }
}
