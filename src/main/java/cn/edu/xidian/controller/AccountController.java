package cn.edu.xidian.controller;

import cn.edu.xidian.domain.Account;
import cn.edu.xidian.service.AccountService;
import cn.edu.xidian.service.SecurityService;
import cn.edu.xidian.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @ResponseBody
    @RequestMapping("/testJSON")
    public String testJSON(@RequestParam Integer aaa,Model model){
        model.addAttribute("aaa",100);
        return utilService.modelToString(model);
    }

    @ResponseBody
    @RequestMapping("/addAccount")
    public String addAccount(Account account, Model model){
        try{
            accountService.addAccount(account);
            model.addAttribute("success",1);
        }catch (Exception e){
            model.addAttribute("success",0);
        }
        return utilService.modelToString(model);
    }

    @ResponseBody
    @RequestMapping("/deleteAccount")
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
    @RequestMapping("/findAccount")
    public String findAccountByEmail(@RequestParam String email,Model model){
        try{
            Account account = accountService.findAccountByEmail(email);
            if (account!=null){
                model.addAttribute("success",1);
                model.addAttribute("account",account);
            }
        }catch (Exception e){
            model.addAttribute("success",0);
            model.addAttribute("account"," ");
        }
        return utilService.modelToString(model);
    }

    @ResponseBody
    @RequestMapping("/editAccount/editUserName")
    public String updateAccountNameByAid(@RequestParam Integer aid,@RequestParam String ssid,
                                               @RequestParam String accName,Model model){
        try{
            String errCode = securityService.checkSsid(aid,ssid);
            switch (errCode){
                case "Login":
                    model.addAttribute("success",0);
                    model.addAttribute("errCode","Login");
                    return utilService.modelToString(model);
                case "Timeout":
                    model.addAttribute("success",0);
                    model.addAttribute("errCode","Timeout");
                    return utilService.modelToString(model);
                case "Ssid wrong":
                    model.addAttribute("success",0);
                    model.addAttribute("errCode","Ssid wrong");
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
    @RequestMapping("/editAccount/editUserPswd")
    public String updateAccountPswdByAid(@RequestParam Integer aid,@RequestParam String ssid,
                                         @RequestParam String oldPassword,@RequestParam String newPassword,
                                               Model model){
        try{
            String errCode = securityService.checkSsid(aid,ssid);
            switch (errCode) {
                case "Login":
                    model.addAttribute("success", 0);
                    model.addAttribute("errCode", "Login");
                    return utilService.modelToString(model);
                case "Timeout":
                    model.addAttribute("success", 0);
                    model.addAttribute("errCode", "Timeout");
                    return utilService.modelToString(model);
                case "Ssid wrong":
                    model.addAttribute("success", 0);
                    model.addAttribute("errCode", "Ssid wrong");
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
    @RequestMapping("/login")
    public String login(@RequestParam String email,@RequestParam String password,Model model){
        try{
            String passwordBase = accountService.findAccountPswdByEmail(email);
            if (!passwordBase.equals(password)){
                model.addAttribute("success", 0);
                model.addAttribute("aid", " ");
                model.addAttribute("ssid", " ");
                model.addAttribute("errCode", "Password wrong");
                return utilService.modelToString(model);
            }
            String ssid = utilService.getRandomSsid();
            Integer aid = accountService.findAccountAidByEmail(email);
            long timeNow = new Date().getTime();
            securityService.addAidSsid(aid,ssid,timeNow);
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
