package cn.edu.xidian.dao;

import cn.edu.xidian.domain.Broadcast;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 胡广鹏 on 2020/5/15 19:16
 */
@Repository
public interface BroadcastDao {

    @Options(useGeneratedKeys=true,keyProperty = "bid")
    @Insert(value = "insert into UserBroadcast (fid,aid,bcScript,likeIt,review,bcTag,timestp,limits) values (#{fid},#{aid},#{bcScript},#{likeIt},#{review},#{bcTag},#{timestp},#{limits})")
    void addUBC(Broadcast bc);

    @Delete("delete from UserBroadcast where bid=#{bid}")
    void deleteUBCByBid(Integer bid);

    @Update("update UserBroadcast set likeIt=#{likeIt} where bid=#{bid}")
    void updateUBCLikeItByBid(Integer bid,Integer likeIt);

    @Update("update UserBroadcast set review=#{review} where bid=#{bid}")
    void updateUBCReviewByBid(Integer bid, String review);

    @Update("update UserBroadcast set bcScript=#{bcScript},timestp=#{timestp} where bid=#{bid}")
    void updateUBCBCScriptByBid(@Param("bid") Integer bid, @Param("bcScript") String bcScript,@Param("timestp") long timestp);

    @Select("select * from UserBroadcast where bid=#{bid}")
    Broadcast getUBCByBid(Integer bid);

    @Select("select count(*) from UserBroadcast")
    Integer getUBCLines();

    @Select("select * from UserBroadcast where aid=#{aid}")
    List<Broadcast> getUBCByAid(Integer aid);

    @Select("select * from UserBroadcast where ${condition}")
    List<Broadcast> getUBCByCond(String condition);

}
