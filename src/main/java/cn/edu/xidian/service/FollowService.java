package cn.edu.xidian.service;

import java.util.List;

/**
 * Created by 胡广鹏 on 2020/5/15 17:17
 */
public interface FollowService {

    void addFollower(Integer aid,Integer followAid);
    void deleteFollower(Integer aid,Integer followAid);
    List<Integer> findFollower(Integer aid);
    List<Integer> findBeFollowed(Integer aid);

}
