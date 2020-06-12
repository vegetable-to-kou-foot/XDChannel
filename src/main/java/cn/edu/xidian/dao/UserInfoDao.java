package cn.edu.xidian.dao;

import cn.edu.xidian.domain.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 胡广鹏 on 2020/5/14 21:41
 */
@Repository
public interface UserInfoDao {

    @Insert("insert into UserInfo (aid, userInfo, userTag, profilePic) values (#{aid},#{userInfo},#{userTag},#{profilePic})")
    void addUserInfo(@Param("aid") Integer aid, @Param("userInfo") String userInfo,
                               @Param("userTag") String userTag, @Param("profilePic") String profilePic);

    @Update("update UserInfo set profilePic = #{profilePic} where aid=#{aid}")
    void updateUserInfoProfilePicByAid(@Param("aid") Integer aid, @Param("profilePic") String profilePic);

    @Update("update UserInfo set userInfo = #{userInfo} where aid=#{aid}")
    void updateUserInfoUserInfoByAid(@Param("aid") Integer aid, @Param("userInfo") String userInfo);

    @Update("update UserInfo set userTag=#{userTag} where aid=#{aid}")
    void updateUserInfoUserTagByAid(@Param("aid") Integer aid,@Param("userTag") String userTag);

    @Select("select userTag from UserInfo where aid=#{aid}")
    String getUserInfoUserTagByAid(Integer aid);

    @Select("select profilePic from UserInfo where aid=#{aid}")
    String findUserInfoProfilePicByAid(@Param("aid") Integer aid);

    @Select("select userInfo from UserInfo where aid=#{aid}")
    String findUserInfoUserInfoByAid(@Param("aid") Integer aid);

    @Select("select * from UserInfo")
    List<UserInfo> findUserInfoAll();
}
