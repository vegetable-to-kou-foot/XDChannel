package cn.edu.xidian.service.impl;

import cn.edu.xidian.dao.AccountDao;
import cn.edu.xidian.domain.Account;
import cn.edu.xidian.domain.email.CodeUtil;
import cn.edu.xidian.domain.email.SendEmailUtil;
import cn.edu.xidian.service.AccountService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private SendEmailUtil sendEmailUtil;

    @Override
    public void addAccount(Account account) {
        accountDao.addAccount(account);
    }

    @Override
    public void deleteAccountByEmail(String email){
        accountDao.deleteAccountByEmail(email);
    }

    @Override
    public void updateAccountNameByAid(String accName,Integer aid){
        accountDao.updateAccountNameByAid(accName,aid);
    }

    @Override
    public void updateAccountEmailByAid(String newEmail, Integer aid) {
        accountDao.updateAccountEmailByAid(newEmail,aid);
    }

    @Override
    public void updateAccountPswdByAid(String userPswd, Integer aid) {
        accountDao.updateAccountPswdByAid(userPswd,aid);
    }

    @Override
    public void updateAccountEmailByEmail(String newEmail,String oldEmail){
        accountDao.updateAccountEmailByEmail(newEmail,oldEmail);
    }

    @Override
    public void updateAccountPswdByEmail(String userPswd,String email){
        accountDao.updateAccountPswdByEmail(userPswd,email);
    }

    @Override
    public void updateAccountCheckInfoByAid(String checkInfo, int aid) {
        accountDao.updateAccountCheckInfoByAid(checkInfo,aid);
    }

    @Override
    public String findAccountPswdByAid(Integer aid) {
        return accountDao.findAccountPswdByAid(aid);
    }

    @Override
    public String findAccountPswdByEmail(String email) {
        return accountDao.findAccountPswdByEmail(email);
    }

    @Override
    public String findAccountEmailByAid(Integer aid) {
        return accountDao.findAccountEmailByAid(aid);
    }

    @Override
    public String findAccountCheckInfoByAid(Integer aid) {
        return accountDao.findAccountCheckInfoByAid(aid);
    }

    @Override
    public Integer findAccountAidByEmail(String email){
        return accountDao.findAccountAidByEmail(email);
    }

    @Override
    public Account findAccountByAid(Integer aid){
        return accountDao.findAccountByAid(aid);
    }

    @Override
    public Integer findAccountAidByCheckInfo(String checkInfo) {
        return accountDao.findAccountAidByCheckInfo(checkInfo);
    }

    @Override
    public List<Account> findAccountByName(String accName){
        return accountDao.findAccountByName(accName);
    }

    @Override
    public Account findAccountByEmail(String email){
        return accountDao.findAccountByEmail(email);
    }

    @Override
    public void sendEmail(String code, String toEmail) throws IOException, MessagingException {
        sendEmailUtil.sendEmail(code, toEmail);
    }
}
