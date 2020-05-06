package cn.edu.xidian.service.impl;

import cn.edu.xidian.service.UtilService;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by 胡广鹏 on 2020/5/6 20:20
 */
@Service("utilServiceImpl")
public class UtilServiceImpl implements UtilService {

    @Override
    public String getRandomSsid() {
            String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            Random random=new Random();
            StringBuffer sb=new StringBuffer();
            for(int i=0;i<16;i++){
                int number=random.nextInt(62);
                sb.append(str.charAt(number));
            }
            return sb.toString();
    }
}
