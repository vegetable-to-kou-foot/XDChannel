package cn.edu.xidian.dao;

import cn.edu.xidian.domain.Account;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountDao {

    @Insert("insert into Account (accName,userPswd,email,checkInfo) values (#{accName},#{userPswd},#{email},#{checkInfo})")
    void addAccount(Account account);

    @Delete("delete from Account where email=#{email}")
    void deleteAccountByEmail(String email);

    @Update("update Account set accName=#{accName} where aid=#{aid}")
    void updateAccountNameByAid(@Param("accName") String accName, @Param("aid") Integer aid);

    @Update("update Account set email=#{newEmail} where aid=#{aid}")
    void updateAccountEmailByAid(@Param("newEmail")String newEmail,@Param("aid")int aid);

    @Update("update Account set checkInfo=#{checkInfo} where aid=#{aid}")
    void updateAccountCheckInfoByAid(@Param("checkInfo")String checkInfo,@Param("aid")int aid);

    @Update("update Account set userPswd=#{userPswd} where aid=#{aid}")
    void updateAccountPswdByAid(@Param("userPswd")String userPswd,@Param("aid")int aid);

    @Update("update Account set email=#{newEmail} where email=#{oldEmail}")
    void updateAccountEmailByEmail(@Param("newEmail")String newEmail,@Param("oldEmail")String oldEmail);

    @Update("update Account set userPswd=#{userPswd} where email=#{email}")
    void updateAccountPswdByEmail(@Param("userPswd")String userPswd,@Param("email")String email);

    @Select("select userPswd from Account where aid=#{aid}")
    String findAccountPswdByAid(Integer aid);

    @Select("select userPswd from Account where email=#{email}")
    String findAccountPswdByEmail(String email);

    @Select("select email from Account where aid=#{aid}")
    String findAccountEmailByAid(Integer aid);

    @Select("select aid from Account where email=#{email}")
    Integer findAccountAidByEmail(String email);

    @Select("select aid from Account where checkInfo=#{checkInfo}")
    Integer findAccountAidByCheckInfo(String checkInfo);

    @Select("select checkInfo from Account where aid=#{aid}")
    String findAccountCheckInfoByAid(Integer aid);

    @Select("select * from Account where aid=#{aid}")
    Account findAccountByAid(Integer aid);

    @Select("select * from Account where accName=#{accName}")
    List<Account> findAccountByName(String accName);

    @Select("select * from Account where email=#{email}")
    Account findAccountByEmail(String email);

    @Select({
            "<script>" +
                    "select accName from Account where aid in " +
                    "<foreach item = 'aidItem' index = 'index' collection = 'aidList' open='(' separator=',' close=')'>" +
                    "#{aidItem}" +
                    "</foreach>"+
                    "</script>"})
    List<String> findAccountAccNameByAidList(@Param("aidList") List<Integer> aidList);
}
