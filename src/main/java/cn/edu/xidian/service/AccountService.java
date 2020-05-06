package cn.edu.xidian.service;

import cn.edu.xidian.domain.Account;

import java.util.List;

public interface AccountService {
    void addAccount(Account account);
    void deleteAccountByEmail(String email);
    void updateAccountNameByAid(String accName,Integer aid);
    void updateAccountEmailByAid(String newEmail,Integer aid);
    void updateAccountPswdByAid(String userPswd,Integer aid);
    void updateAccountEmailByEmail(String newEmail,String oldEmail);
    void updateAccountPswdByEmail(String userPswd,String email);
    String findAccountPswdByAid(Integer aid);
    String findAccountPswdByEmail(String email);
    String findAccountEmailByAid(Integer aid);
    Integer findAccountAidByEmail(String email);
    List<Account> findAccountByAid(Integer aid);
    List<Account> findAccountByName(String accName);
    Account findAccountByEmail(String email);
}
