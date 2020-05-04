package cn.edu.xidian.service;

import cn.edu.xidian.domain.Account;

import java.util.List;

public interface AccountService {
    void addAccount(Account account);
    void deleteAccountByEmail(String email);
    void updateAccountNameByAid(String accName,Integer aid);
    void updateAccountEmailByEmail(String newEmail,String oldEmail);
    void updateAccountPswdByEmail(String userPswd,String email);
    Integer findAccountAidByEmail(String email);
    List<Account> findAccountById(Integer aid);
    List<Account> findAccountByName(String accName);
    Account findAccountByEmail(String email);
}
