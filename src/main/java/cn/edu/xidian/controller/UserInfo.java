package cn.edu.xidian.controller;

import cn.edu.xidian.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 胡广鹏 on 2020/5/7 0:40
 */
@RestController
public class UserInfo {

    @Autowired
    private UtilService utilService;

    //该代码来自https://www.cnblogs.com/xQlover/p/9898255.html
    @RequestMapping("/editProfilePic")
    public String editProfilePic(MultipartFile upload, HttpServletRequest request){
        utilService.upLoadFile(upload);
        return "ok";
    }

}
