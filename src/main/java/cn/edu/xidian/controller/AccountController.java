package cn.edu.xidian.controller;

import cn.edu.xidian.dao.FollowDao;
import cn.edu.xidian.domain.Account;
import cn.edu.xidian.domain.email.CodeUtil;
import cn.edu.xidian.service.*;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UtilService utilService;
    @Autowired
    private FollowService followService;
    @Autowired
    private UserTagService userTagService;

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/confirmAddAccount")
    public String confirmAddAccount(String code, Model model){
        try{
            Integer aid = accountService.findAccountAidByCheckInfo(code);
            accountService.updateAccountCheckInfoByAid("-", aid);
            model.addAttribute("success",1);
        }catch (Exception e){
            model.addAttribute("success",0);
        }
        return utilService.modelToString(model);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/addAccount",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String addAccount(Account account){
        Map<String,Object> ans = new HashMap<>();
        try{
            System.out.println("Print successfully.");
            Integer aidTmp = accountService.findAccountAidByEmail(account.getEmail());
            System.out.println("aidTmp="+aidTmp);
            if (aidTmp != null)throw new SecurityException();
            System.out.println("aidTmp is null.");
            String code = CodeUtil.generateUniqueCode();
            System.out.println("Create UUID successfully.");
            accountService.sendEmail(code, account.getEmail());
            System.out.println("Send email successfully.");
            account.setCheckInfo(code);
            accountService.addAccount(account);
            Integer aid = accountService.findAccountAidByEmail(account.getEmail());
            followService.addFollowerNone(aid);
            userTagService.addUserTagNone(aid);
            ans.put("success",1);
        }catch (Exception e){
            ans.put("success",0);
        }
        return JSON.toJSONString(ans);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/deleteAccount",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    //todo:维护accountTag表，把输出里的account去掉
    public String deleteAccountByEmail(@RequestParam String ssid, Account account){
        Map<String,Object> ans = new HashMap<>();
        String errCode = " ";
        try{
            Integer aid = account.getAid();
            errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode))throw new SecurityException();
            Account willBeDelAcc = accountService.findAccountByEmail(account.getEmail());
            if (willBeDelAcc.getAccName().equals(account.getAccName()) &&
                    willBeDelAcc.getUserPswd().equals(account.getUserPswd()) &&
                    willBeDelAcc.getEmail().equals(account.getEmail())){
                accountService.deleteAccountByEmail(account.getEmail());
                ans.put("success",1);
            }
        }catch (Exception e){
            ans.put("success",0);
        }
        return JSON.toJSONString(ans);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/findAccount",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String findAccountByEmail(@RequestParam String email){
        Map<String,Object> ans = new HashMap<>();
        try{
            Account account = accountService.findAccountByEmail(email);
            if (account!=null){
                ans.put("success",1);
                ans.put("isAccount",1);
            }else{
                ans.put("success",1);
                ans.put("isAccount",0);
            }
        }catch (Exception e){
            ans.put("success",0);
            ans.put("isAccount",0);
        }
        return JSON.toJSONString(ans);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/editAccount/editUserName",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String updateAccountNameByAid(@RequestParam Integer aid,@RequestParam String ssid,
                                               @RequestParam String accName){
        Map<String,Object> ans = new HashMap<>();
        String errCode = " ";
        try{
            errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode))throw new SecurityException();
            accountService.updateAccountNameByAid(accName,aid);
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
    @RequestMapping(value = "/editAccount/editUserPswd",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String updateAccountPswdByAid(@RequestParam Integer aid,@RequestParam String ssid,
                                         @RequestParam String oldPassword,@RequestParam String newPassword){
        Map<String,Object> ans = new HashMap<>();
        String errCode = " ";
        try{
            errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode))throw new SecurityException();
            String passwordBase = accountService.findAccountPswdByAid(aid);
            if (!passwordBase.equals(oldPassword)){
                errCode = "Password wrong";
                throw new SecurityException();
            }
            accountService.updateAccountPswdByAid(newPassword,aid);
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
    @RequestMapping(value = "/login",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String login(@RequestParam String email,@RequestParam String password,Model model){
        try{
            Integer aid = accountService.findAccountAidByEmail(email);
            String checkInfo = accountService.findAccountCheckInfoByAid(aid);
            String passwordBase = accountService.findAccountPswdByEmail(email);
            if (!passwordBase.equals(password)){
                model.addAttribute("success", 0);
                model.addAttribute("aid", " ");
                model.addAttribute("ssid", " ");
                model.addAttribute("errCode", "Password wrong");
                return utilService.modelToString(model);
            }
            if (!"-".equals(checkInfo)){
                model.addAttribute("success", 0);
                model.addAttribute("aid", " ");
                model.addAttribute("ssid", " ");
                model.addAttribute("errCode", "Not verification");
                return utilService.modelToString(model);
            }
            String ssid = securityService.refreshSsid(aid);
            model.addAttribute("success", 1);
            model.addAttribute("aid", aid);
            model.addAttribute("ssid", ssid);
            model.addAttribute("errCode", " ");
            return utilService.modelToString(model);
        }catch (Exception e){
            model.addAttribute("success", 0);
            model.addAttribute("aid", " ");
            model.addAttribute("ssid", " ");
            model.addAttribute("errCode", "Exception");
            return utilService.modelToString(model);
        }
    }
}
