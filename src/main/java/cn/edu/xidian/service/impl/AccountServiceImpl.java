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
    public List<Account> findAll(){
        return accountDao.findAll();
    }

    @Override
    public void saveAccount(Account account){
        accountDao.saveAccount(account);
    }
}
