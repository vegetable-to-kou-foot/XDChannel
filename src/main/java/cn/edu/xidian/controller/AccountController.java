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

    @RequestMapping("/addAccount")
    public String addAccount(Account account){
        accountService.addAccount(account);
        return "success";
    }

    @RequestMapping("/deleteAccount")
    public String deleteAccountByEmail(Account account){
        Account willBeDelAcc = accountService.findAccountByEmail(account.getEmail());
        if (willBeDelAcc.getAccName().equals(account.getAccName()) &&
                willBeDelAcc.getUserPswd().equals(account.getUserPswd()) &&
                willBeDelAcc.getEmail().equals(account.getEmail())){
            accountService.deleteAccountByEmail(account.getEmail());
            return "success";
        }
        return "failed";
    }

    @ResponseBody
    @RequestMapping("/findAccount")
    public Account findAccountByEmail(String email){
        return accountService.findAccountByEmail(email);
    }

    @RequestMapping("/editAccount/editUserName")
    public String updateAccountNameByAid(@RequestParam Integer aid,@RequestParam String ssid,@RequestParam String userName){
        String errCode = securityService.checkSsid(aid,ssid);
        switch (errCode){
            case "Login":
            case "Timeout":
            case "Ssid wrong":
                //上面的返回位置是不同的，与前端合并时要注意
                return "failed";
        }

        accountService.updateAccountNameByAid(userName,aid);
        //返回个人信息页面
        return "success";
    }

    @RequestMapping("/editAccount/editUserPswd")
    public String updateAccountPswdByAid(@RequestParam Integer aid,@RequestParam String ssid,
                                         @RequestParam String oldPassword,@RequestParam String newPassword){
        String errCode = securityService.checkSsid(aid,ssid);
        switch (errCode){
            case "Login":
            case "Timeout":
            case "Ssid wrong":
                //上面的返回位置是不同的，与前端合并时要注意
                return "failed";
        }
        String passwordBase = accountService.findAccountPswdByAid(aid);
        if (!passwordBase.equals(oldPassword)){
            //返回密码错误
            return "failed";
        }

        accountService.updateAccountPswdByAid(newPassword,aid);
        //返回修改成功
        return "success";
    }

    @ResponseBody
    @RequestMapping("/login")
    public void login(@RequestParam String email,@RequestParam String password,Model model){
        String passwordBase = accountService.findAccountPswdByEmail(email);
        if (!passwordBase.equals(password)){
            model.addAttribute("success",0);
            model.addAttribute("ssid","");
            model.addAttribute("aid","");
            //返回密码错误
            return;
        }
        String ssid = utilService.getRandomSsid();
        System.out.println("\n\n\n"+ssid+"\n\n\n");
        Integer aid = accountService.findAccountAidByEmail(email);
        System.out.println("\n\n\n"+aid+"\n\n\n");
        long timeNow = new Date().getTime();
        System.out.println("\n\n\n"+timeNow+"\n\n\n");
        model.addAttribute("success",1);
        model.addAttribute("ssid",ssid);
        model.addAttribute("aid",aid);
        securityService.addAidSsid(aid,ssid,timeNow);

        //返回索引页
        return;
    }


}
