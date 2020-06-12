package cn.edu.xidian.domain.email;

import java.util.UUID;

/**
 * Created by 胡广鹏 on 2020/6/10 16:53
 */
public class CodeUtil {
    //生成唯一的激活码
    public static String generateUniqueCode(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}