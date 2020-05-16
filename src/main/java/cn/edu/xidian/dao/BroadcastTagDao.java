package cn.edu.xidian.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 胡广鹏 on 2020/5/15 22:53
 */
@Repository
public interface BroadcastTagDao {
    @Insert("insert into BroadcastTag (bTagName,bTagInfo) values (#{bTagName},#{bTagInfo})")
    void addBroadcastTag(@Param("bTagName") String bTagName,@Param("bTagInfo") String bTagInfo);

    @Delete("delete from BroadcastTag where btid=#{btid}")
    void deleteBroadcastTagByBtid(Integer btid);

    @Update("update BroadcastTag set bTagInfo=#{bTagInfo} where btid=#{btid}")
    void updateBroadcastTagBTagInfoByBtid(@Param("btid") Integer btid,@Param("bTagInfo") String bTagInfo);

    @Select("select bTagName from BroadcastTag where btid=#{btid}")
    String getBroadcastTagBTagNameByBtid(Integer btid);

    @Select("select btid from BroadcastTag where bTagName=#{bTagName}")
    Integer getBroadcastTagBtidByBTagName(String bTagName);

    @Select("select bTagInfo from BroadcastTag where btid=#{btid}")
    String getBroadcastTagBTagInfoByBtid(Integer btid);

    @Select("select bTagName from BroadcastTag where bTagName like #{bTagName}")
    List<String> getBroadcastTagBTagNameByBTagName(String bTagName);
}
