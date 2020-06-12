package cn.edu.xidian.service.impl;

import cn.edu.xidian.dao.SecurityDao;
import cn.edu.xidian.service.SecurityService;
import cn.edu.xidian.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by 胡广鹏 on 2020/5/6 14:56
 */
@Service("securityService")
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    private SecurityDao securityDao;
    @Autowired
    private UtilService utilService;


    @Override
    public void addAidSsid(Integer aid, String ssid, long time) {
        String nowSsid = securityDao.getAidSsidSsidByAid(aid);
        if (!(nowSsid==null || nowSsid.length()<=0)){
            securityDao.deleteAidSsidByAid(aid);
        }
        securityDao.addAidSsid(aid,ssid,time);
    }

    @Override
    public void deleteAidSsidByAid(Integer aid) {
        securityDao.deleteAidSsidByAid(aid);
    }

    @Override
    public String getAidSsidSsidByAid(Integer aid) {
        System.out.println("\n\n\nIn SecurityServiceImpl\n\n\n");
        return securityDao.getAidSsidSsidByAid(aid);
    }

    @Override
    public long getAidSsidTimeByAid(Integer aid) {
        return securityDao.getAidSsidTimeByAid(aid);
    }

    @Override
    public String checkSsid(Integer aid, String ssid) {
        String ssidBase = getAidSsidSsidByAid(aid);
        long timeBase = getAidSsidTimeByAid(aid);
        long timeNow = new Date().getTime();
        if (ssidBase==null){
            System.out.println("\n\n\n用户"+aid+"未登录！\n\n\n");
            return "Login";
        }
        if (!ssidBase.equals(ssid)){
            System.out.println("\n\n\n用户"+aid+"ssid出错！\n\n\n");
            return "Ssid wrong";
        }
        //时间有效期是5小时
        if (timeNow-timeBase>5*3600*1000){
            System.out.println("\n\n\n用户"+aid+"登录超时！\n\n\n");
            return "Timeout";
        }
        return "OK";
    }

    @Override
    public String refreshSsid(Integer aid) {
        String nowSsid = securityDao.getAidSsidSsidByAid(aid);
        if (!(nowSsid==null || nowSsid.length()<=0)){
            deleteAidSsidByAid(aid);
        }
        String ssid = utilService.getRandomSsid();
        long timeNow = new Date().getTime();
        securityDao.addAidSsid(aid,ssid,timeNow);
        return ssid;
    }
}
