package cn.edu.xidian.service;

import cn.edu.xidian.domain.Account;

import java.util.List;

public interface AccountService {
    public List<Account> findAll();
    public void saveAccount(Account account);
}
