package cn.edu.xidian.service.impl;

import cn.edu.xidian.dao.AccountDao;
import cn.edu.xidian.domain.Account;
import cn.edu.xidian.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

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
    public void updateAccountEmailByEmail(String newEmail,String oldEmail){
        accountDao.updateAccountEmailByEmail(newEmail,oldEmail);
    }

    @Override
    public void updateAccountPswdByEmail(String userPswd,String email){
        accountDao.updateAccountPswdByEmail(userPswd,email);
    }

    @Override
    public Integer findAccountAidByEmail(String email){
        return accountDao.findAccountAidByEmail(email);
    }

    @Override
    public List<Account> findAccountById(Integer aid){
        return accountDao.findAccountById(aid);
    }

    @Override
    public List<Account> findAccountByName(String accName){
        return accountDao.findAccountByName(accName);
    }

    @Override
    public Account findAccountByEmail(String email){
        return accountDao.findAccountByEmail(email);
    }
}
