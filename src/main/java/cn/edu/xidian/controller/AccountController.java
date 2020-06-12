package cn.edu.xidian.controller;

import cn.edu.xidian.dao.FollowDao;
import cn.edu.xidian.domain.Account;
import cn.edu.xidian.domain.email.CodeUtil;
import cn.edu.xidian.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;


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

    @ResponseBody
    @RequestMapping("/testJSON")
    public String testJSON(@RequestParam Integer aaa,Model model){
        model.addAttribute("aaa",100);
        return utilService.modelToString(model);
    }

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

    @ResponseBody
    @RequestMapping(value = "/addAccount",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String addAccount(Account account, Model model){
        try{
            Integer aidTmp = accountService.findAccountAidByEmail(account.getEmail());
            if (aidTmp != null)throw new SecurityException();
            String code = CodeUtil.generateUniqueCode();
            accountService.sendEmail(code, account.getEmail());
            account.setCheckInfo(code);
            accountService.addAccount(account);
            Integer aid = accountService.findAccountAidByEmail(account.getEmail());
            followService.addFollowerNone(aid);
            userTagService.addUserTagNone(aid);
            model.addAttribute("success",1);
        }catch (Exception e){
            model.addAttribute("success",0);
        }
        return utilService.modelToString(model);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteAccount",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    //todo:维护accountTag表，把输出里的account去掉
    public String deleteAccountByEmail(Account account,Model model){
        try{
            Account willBeDelAcc = accountService.findAccountByEmail(account.getEmail());
            if (willBeDelAcc.getAccName().equals(account.getAccName()) &&
                    willBeDelAcc.getUserPswd().equals(account.getUserPswd()) &&
                    willBeDelAcc.getEmail().equals(account.getEmail())){
                accountService.deleteAccountByEmail(account.getEmail());
                model.addAttribute("success",1);
            }
        }catch (Exception e){
            model.addAttribute("success",0);
        }
        return utilService.modelToString(model);
    }

    @ResponseBody
    @RequestMapping(value = "/findAccount",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String findAccountByEmail(@RequestParam String email,Model model){
        try{
            Account account = accountService.findAccountByEmail(email);
            if (account!=null){
                model.addAttribute("success",1);
                model.addAttribute("isAccount",1);
            }else{
                model.addAttribute("success",1);
                model.addAttribute("isAccount",0);
            }
        }catch (Exception e){
            model.addAttribute("success",0);
            model.addAttribute("isAccount"," ");
        }
        return utilService.modelToString(model);
    }

    @ResponseBody
    @RequestMapping(value = "/editAccount/editUserName",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String updateAccountNameByAid(@RequestParam Integer aid,@RequestParam String ssid,
                                               @RequestParam String accName,Model model){
        try{
            String errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode)){
                model.addAttribute("success",0);
                model.addAttribute("errCode",errCode);
                return utilService.modelToString(model);
            }
            accountService.updateAccountNameByAid(accName,aid);
            model.addAttribute("success",1);
            model.addAttribute("errCode"," ");
            return utilService.modelToString(model);
        }catch (Exception e){
            model.addAttribute("success",0);
            model.addAttribute("errCode","Exception");
            return utilService.modelToString(model);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/editAccount/editUserPswd",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String updateAccountPswdByAid(@RequestParam Integer aid,@RequestParam String ssid,
                                         @RequestParam String oldPassword,@RequestParam String newPassword,
                                               Model model){
        try{
            String errCode = securityService.checkSsid(aid,ssid);
            if (!"OK".equals(errCode)){
                model.addAttribute("success",0);
                model.addAttribute("errCode",errCode);
                return utilService.modelToString(model);
            }
            String passwordBase = accountService.findAccountPswdByAid(aid);
            if (!passwordBase.equals(oldPassword)){
                model.addAttribute("success", 0);
                model.addAttribute("errCode", "Password wrong");
                return utilService.modelToString(model);
            }
            accountService.updateAccountPswdByAid(newPassword,aid);
            model.addAttribute("success", 1);
            model.addAttribute("errCode", " ");
            return utilService.modelToString(model);
        }catch (Exception e){
            model.addAttribute("success", 0);
            model.addAttribute("errCode", "Exception");
            return utilService.modelToString(model);
        }
    }

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
