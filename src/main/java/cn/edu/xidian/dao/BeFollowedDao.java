package cn.edu.xidian.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Created by 胡广鹏 on 2020/5/15 16:25
 */
@Repository
public interface BeFollowedDao {

    @Insert("insert into BeFollowed (aid,beFollowedInfo) values (#{aid},#{beFollowedInfo})")
    void addBeFollowed(@Param("aid") Integer aid, @Param("beFollowedInfo") String beFollowedInfo);

    @Delete("delete from BeFollowed where aid=#{aid}")
    void deleteBeFollowedByAid(@Param("aid") Integer aid);

    @Update("update BeFollowed set beFollowedInfo=#{beFollowedInfo} where aid=#{aid}")
    void updateBeFollowedBeFollowedInfoByAid(@Param("aid") Integer aid, @Param("beFollowedInfo") String beFollowedInfo);

    @Select("select beFollowedInfo from BeFollowed where aid=#{aid}")
    String getBeFollowedBeFollowedInfoByAid(Integer aid);
}
