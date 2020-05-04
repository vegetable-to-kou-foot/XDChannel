package cn.edu.xidian.dao;

import cn.edu.xidian.domain.Account;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountDao {

    @Insert("insert into Account (accName,userPswd,email) values (#{accName},#{userPswd},#{email})")
    void addAccount(Account account);

    @Delete("delete from Account where email=#{email}")
    void deleteAccountByEmail(String email);

    @Update("update Account set accName=#{accName} where aid=#{aid}")
    void updateAccountNameByAid(String accName,Integer aid);

    @Update("update Account set email=#{newEmail} where email=#{oldEmail}")
    void updateAccountEmailByEmail(String newEmail,String oldEmail);

    @Update("update Account set userPswd=#{userPswd} where email=#{email}")
    void updateAccountPswdByEmail(String userPswd,String email);

    @Select("select aid from Account where email=#{email}")
    Integer findAccountAidByEmail(String email);

    @Select("select * from Account where aid=#{aid}")
    List<Account> findAccountById(Integer aid);

    @Select("select * from Account where accName=#{accName}")
    List<Account> findAccountByName(String accName);

    @Select("select * from Account where email=#{email}")
    Account findAccountByEmail(String email);
}
