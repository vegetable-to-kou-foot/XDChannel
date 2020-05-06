package cn.edu.xidian.controller;

import cn.edu.xidian.domain.Account;
import cn.edu.xidian.service.AccountService;
import cn.edu.xidian.service.SecurityService;
import cn.edu.xidian.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UtilService utilService;

    @RequestMapping("/addAccount")
    public ModelAndView addAccount(Account account, ModelAndView mav){
        try{
            accountService.addAccount(account);
            mav.setViewName("success");
            mav.addObject("success",1);
        }catch (Exception e){
            mav.setViewName("failed");
            mav.addObject("success",0);
        }
        return mav;
    }

    @RequestMapping("/deleteAccount")
    public ModelAndView deleteAccountByEmail(Account account,ModelAndView mav){
        try{
            Account willBeDelAcc = accountService.findAccountByEmail(account.getEmail());
            if (willBeDelAcc.getAccName().equals(account.getAccName()) &&
                    willBeDelAcc.getUserPswd().equals(account.getUserPswd()) &&
                    willBeDelAcc.getEmail().equals(account.getEmail())){
                accountService.deleteAccountByEmail(account.getEmail());
                mav.setViewName("success");
                mav.addObject("success",1);
            }
        }catch (Exception e){
            mav.setViewName("failed");
            mav.addObject("success",0);
        }
        return mav;
    }

    @RequestMapping("/findAccount")
    public ModelAndView findAccountByEmail(@RequestParam String email,ModelAndView mav){
        try{
            Account account = accountService.findAccountByEmail(email);
            if (account!=null){
                mav.setViewName("success");
                mav.addObject("success",1);
                mav.addObject("account",account);
            }
        }catch (Exception e){
            mav.setViewName("failed");
            mav.addObject("success",0);
            mav.addObject("account","");
        }
        return mav;
    }

    @RequestMapping("/editAccount/editUserName")
    public ModelAndView updateAccountNameByAid(@RequestParam Integer aid,@RequestParam String ssid,
                                               @RequestParam String accName,ModelAndView mav){
        try{
            String errCode = securityService.checkSsid(aid,ssid);
            switch (errCode){
                case "Login":
                    mav.setViewName("failed");
                    mav.addObject("success",0);
                    mav.addObject("errCode","Login");
                    return mav;
                case "Timeout":
                    mav.setViewName("failed");
                    mav.addObject("success",0);
                    mav.addObject("errCode","Timeout");
                    return mav;
                case "Ssid wrong":
                    mav.setViewName("failed");
                    mav.addObject("success",0);
                    mav.addObject("errCode","Ssid wrong");
                    return mav;
            }
            accountService.updateAccountNameByAid(accName,aid);
            mav.setViewName("success");
            mav.addObject("success",1);
            mav.addObject("errCode","");
        }catch (Exception e){
            mav.setViewName("failed");
            mav.addObject("success",0);
            mav.addObject("errCode","Exception");
        }
        return mav;

    }

    @RequestMapping("/editAccount/editUserPswd")
    public ModelAndView updateAccountPswdByAid(@RequestParam Integer aid,@RequestParam String ssid,
                                         @RequestParam String oldPassword,@RequestParam String newPassword,
                                               ModelAndView mav){
        try{
            String errCode = securityService.checkSsid(aid,ssid);
            switch (errCode) {
                case "Login":
                    mav.setViewName("failed");
                    mav.addObject("success", 0);
                    mav.addObject("errCode", "Login");
                    return mav;
                case "Timeout":
                    mav.setViewName("failed");
                    mav.addObject("success", 0);
                    mav.addObject("errCode", "Timeout");
                    return mav;
                case "Ssid wrong":
                    mav.setViewName("failed");
                    mav.addObject("success", 0);
                    mav.addObject("errCode", "Ssid wrong");
                    return mav;
            }
            String passwordBase = accountService.findAccountPswdByAid(aid);
            if (!passwordBase.equals(oldPassword)){
                mav.setViewName("failed");
                mav.addObject("success", 0);
                mav.addObject("errCode", "Password wrong");
                return mav;
            }
            accountService.updateAccountPswdByAid(newPassword,aid);
            mav.setViewName("success");
            mav.addObject("success", 1);
            mav.addObject("errCode", "");
        }catch (Exception e){
            mav.setViewName("failed");
            mav.addObject("success", 0);
            mav.addObject("errCode", "Exception");
        }
        return mav;
    }

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam String email,@RequestParam String password,ModelAndView mav){
        try{
            String passwordBase = accountService.findAccountPswdByEmail(email);
            if (!passwordBase.equals(password)){
                mav.setViewName("failed");
                mav.addObject("success", 0);
                mav.addObject("aid", "");
                mav.addObject("ssid", "");
                mav.addObject("errCode", "Password wrong");
                return mav;
            }
            String ssid = utilService.getRandomSsid();
            Integer aid = accountService.findAccountAidByEmail(email);
            long timeNow = new Date().getTime();
            securityService.addAidSsid(aid,ssid,timeNow);
            mav.setViewName("success");
            mav.addObject("success", 1);
            mav.addObject("aid", aid);
            mav.addObject("ssid", ssid);
            mav.addObject("errCode", "");
            return mav;
        }catch (Exception e){
            mav.setViewName("failed");
            mav.addObject("success", 0);
            mav.addObject("aid", "");
            mav.addObject("ssid", "");
            mav.addObject("errCode", "Exception");
            return mav;
        }
    }


}
