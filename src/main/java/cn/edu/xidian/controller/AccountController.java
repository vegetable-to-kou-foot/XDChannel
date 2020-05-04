package cn.edu.xidian.controller;

import cn.edu.xidian.domain.Account;
import cn.edu.xidian.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

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
        //这里需要AidSsid的支撑代码，以便验证ssid的正确性
        accountService.updateAccountNameByAid(userName,aid);
        return "success";
    }
}
