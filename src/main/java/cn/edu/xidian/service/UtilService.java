package cn.edu.xidian.service;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 胡广鹏 on 2020/5/6 20:19
 */
public interface UtilService {
    String getRandomSsid();
    String modelToString(Model model);
}
