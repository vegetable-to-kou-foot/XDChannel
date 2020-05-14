package cn.edu.xidian.service.impl;

import cn.edu.xidian.service.UtilService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by 胡广鹏 on 2020/5/6 20:20
 */
@Service("utilServiceImpl")
public class UtilServiceImpl implements UtilService {

    private static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    @Override
    public String getRandomSsid() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }


    @Override
    public String modelToString(Model model) {
        Map<String,Object> mp = model.asMap();
        String ans = "{";
        for (Map.Entry<String ,Object> entry : mp.entrySet()) {
            String str = entry.getValue().toString();
            if (isInteger(str) || str.startsWith("{") && str.endsWith("}")){
                ans += String.format("\"%s\":%s,",entry.getKey(),entry.getValue().toString());
            }else{
                ans += String.format("\"%s\":\"%s\",",entry.getKey(),entry.getValue().toString());
            }
        }
        ans = ans.substring(0,ans.length()-1);
        ans += "}";
        return ans;
    }
}
