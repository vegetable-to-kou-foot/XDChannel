package cn.edu.xidian.controller;

import cn.edu.xidian.domain.Broadcast;
import cn.edu.xidian.domain.FindBroadcastRequset;
import cn.edu.xidian.service.BroadcastService;
import cn.edu.xidian.service.SecurityService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    @RequestMapping("/addBroadcast")
    public String addBroadcast(@RequestParam Integer aid, @RequestParam String ssid,@RequestParam Integer fid,
                               @RequestParam String bcScript,@RequestParam String bcTag,
                               @RequestParam Integer timestp,@RequestParam String limits){
        JSONObject ans = new JSONObject();
        try{
            Broadcast bc = new Broadcast(null,fid,aid,bcScript,0,"[]",bcTag,timestp,limits);
            String errCode = securityService.checkSsid(aid,ssid);
            if (!errCode.equals("OK")) throw new SecurityException();
            broadcastService.addBroadcast(bc);
            ans.put("success",1);
        }catch (Exception e){
            ans.put("success",0);
        }
        return ans.toJSONString();
    }

    @ResponseBody
    @RequestMapping("/deleteBroadcast")
    public String deleteBroadcast(@RequestParam Integer aid,@RequestParam String ssid,@RequestParam Integer bid){
        JSONObject ans = new JSONObject();
        try{
            String errCode = securityService.checkSsid(aid,ssid);
            if (!errCode.equals("OK")) throw new SecurityException();
            broadcastService.deleteBroadcast(bid);
            ans.put("success",1);
        }catch (Exception e){
            ans.put("success",0);
        }
        return ans.toJSONString();
    }

    @ResponseBody
    @RequestMapping("/editBroadcast")
    public String editBroadcast(@RequestParam Integer aid, @RequestParam String ssid,@RequestParam Integer bid,
                               @RequestParam String bcScript){
        JSONObject ans = new JSONObject();
        try{
            String errCode = securityService.checkSsid(aid,ssid);
            if (!errCode.equals("OK")) throw new SecurityException();
            broadcastService.editBroadcast(bid,bcScript);
            ans.put("success",1);
        }catch (Exception e){
            ans.put("success",0);
        }
        return ans.toJSONString();
    }

    @ResponseBody
    @RequestMapping("/findBroadcast")
    //todo:所有内容加上limit分页查询功能
    public String findBroadcast(@RequestParam Integer aid, @RequestParam String ssid, @RequestParam FindBroadcastRequset fbr){
        JSONObject ans = new JSONObject();
        try{
            String errCode = securityService.checkSsid(aid,ssid);
            if (!errCode.equals("OK")) throw new SecurityException();
            List<Broadcast> broadcasts = broadcastService.findBroadcast(fbr);
            ans.put("success",1);
            ans.put("broadcasts",JSON.toJSONString(broadcasts));
        }catch (Exception e){
            ans.put("success",0);
            ans.put("broadcasts"," ");
        }
        return ans.toJSONString();
    }
}
