package cn.edu.xidian.dao;

import cn.edu.xidian.domain.AccountTag;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 胡广鹏 on 2020/5/15 9:58
 */
@Repository
public interface AccountTagDao {

    @Insert("insert into AccountTag (aTagName,aTagInfo) values (#{aTagName},#{aTagInfo})")
    void addAccountTag(@Param("aTagName") String aTagName,@Param("aTagInfo") String aTagInfo);

    @Delete("delete from AccountTag where atid=#{atid}")
    void deleteAccountTagByAtid(Integer atid);

    @Update("update AccountTag set aTagInfo=#{aTagInfo} where atid=#{atid}")
    void updateAccountTagATagInfoByAtid(@Param("atid") Integer atid,@Param("aTagInfo") String aTagInfo);

    @Select("select aTagName from AccountTag where atid=#{atid}")
    String getAccountTagATagNameByAtid(Integer atid);

    @Select("select atid from AccountTag where aTagName=#{aTagName}")
    Integer getAccountTagAtidByATagName(String aTagName);

    @Select("select aTagInfo from AccountTag where atid=#{atid}")
    String getAccountTagATagInfoByAtid(Integer atid);

    @Select("select aTagName from AccountTag where aTagName like #{aTagName}")
    List<String> getAccountTagATagNameByATagName(String aTagName);

    @Select("select * from AccountTag")
    List<AccountTag> getAccountTagAll();
}
