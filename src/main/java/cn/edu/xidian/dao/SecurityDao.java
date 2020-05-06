package cn.edu.xidian.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by 胡广鹏 on 2020/5/6 14:30
 */
@Repository
public interface SecurityDao {

    @Insert("insert into AidSsid (aid,ssid,timestp) values (#{aid},#{ssid},#{time})")
    void addAidSsid(@Param("aid") Integer aid, @Param("ssid") String ssid, @Param("time") long time);

    @Delete("delete from AidSsid where aid=#{aid}")
    void deleteAidSsidByAid(Integer aid);

    @Select("select ssid from AidSsid where aid=#{aid}")
    String getAidSsidSsidByAid(Integer aid);

    @Select("select timestp from AidSsid where aid=#{aid}")
    long getAidSsidTimeByAid(Integer aid);
}
