package cn.edu.xidian.service;

import cn.edu.xidian.domain.Broadcast;
import cn.edu.xidian.domain.FindBroadcastRequset;

import java.util.List;

/**
 * Created by 胡广鹏 on 2020/5/15 23:16
 */
public interface BroadcastService {
    void addBroadcast(Broadcast bc);
    void deleteBroadcast(Integer bid,Integer aid);
    void editBroadcast(Integer bid,String bcScript,long timestp);
    List<Broadcast> findBroadcast(FindBroadcastRequset fbr);
}
