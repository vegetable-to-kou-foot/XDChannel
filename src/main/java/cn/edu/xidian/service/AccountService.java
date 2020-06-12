package cn.edu.xidian.service;

import cn.edu.xidian.domain.Account;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface AccountService {
    void addAccount(Account account);
    void deleteAccountByEmail(String email);
    void updateAccountNameByAid(String accName,Integer aid);
    void updateAccountEmailByAid(String newEmail,Integer aid);
    void updateAccountPswdByAid(String userPswd,Integer aid);
    void updateAccountEmailByEmail(String newEmail,String oldEmail);
    void updateAccountPswdByEmail(String userPswd,String email);
    void updateAccountCheckInfoByAid(String checkInfo, int aid);
    String findAccountPswdByAid(Integer aid);
    String findAccountPswdByEmail(String email);
    String findAccountEmailByAid(Integer aid);
    String findAccountCheckInfoByAid(Integer aid);
    Integer findAccountAidByEmail(String email);
    Account findAccountByAid(Integer aid);
    Integer findAccountAidByCheckInfo(String checkInfo);
    List<Account> findAccountByName(String accName);
    Account findAccountByEmail(String email);
    void sendEmail(String code, String toEmail) throws IOException, MessagingException;
}
