package cn.edu.xidian.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Created by 胡广鹏 on 2020/5/15 16:24
 */
@Repository
public interface FollowDao {

    @Insert("insert into Follow (aid,followInfo) values (#{aid},#{followInfo})")
    void addFollow(@Param("aid") Integer aid, @Param("followInfo") String followInfo);

    @Delete("delete from Follow where aid=#{aid}")
    void deleteFollowByAid(@Param("aid") Integer aid);

    @Update("update Follow set followInfo=#{followInfo} where aid=#{aid}")
    void updateFollowFollowInfoByAid(@Param("aid") Integer aid,@Param("followInfo") String followInfo);

    @Select("select followInfo from Follow where aid=#{aid}")
    String getFollowFollowInfoByAid(Integer aid);

}
