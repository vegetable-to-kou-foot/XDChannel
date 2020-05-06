package cn.edu.xidian.service;

/**
 * Created by 胡广鹏 on 2020/5/6 14:42
 */
public interface SecurityService {
    void addAidSsid(Integer aid,String ssid,long time);
    void deleteAidSsidByAid(Integer aid);
    String getAidSsidSsidByAid(Integer aid);
    long getAidSsidTimeByAid(Integer aid);
    String checkSsid(Integer aid,String ssid);
}
