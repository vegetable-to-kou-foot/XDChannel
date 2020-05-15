package cn.edu.xidian.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Created by 胡广鹏 on 2020/5/14 22:46
 */
@Repository
public interface UserTagDao {

    @Insert("insert into UserTag (aid,userTag) values (#{aid},#{userTag})")
    void addUserTag(@Param("aid") Integer aid, @Param("userTag") String userTag);

    @Delete("delete from UserTag where aid=#{aid}")
    void deleteUserTagByAid(Integer aid);

    @Update("update UserTag set userTag=#{userTag} where aid=#{aid}")
    void updateUserTagUserTagByAid(@Param("aid") Integer aid,@Param("userTag") String userTag);

    @Select("select userTag from UserTag where aid=#{aid}")
    String getUserTagUserTagByAid(Integer aid);
}
