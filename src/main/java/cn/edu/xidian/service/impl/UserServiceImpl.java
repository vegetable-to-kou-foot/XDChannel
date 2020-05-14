package cn.edu.xidian.service.impl;

import cn.edu.xidian.dao.UserInfoDao;
import cn.edu.xidian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by 胡广鹏 on 2020/5/14 21:24
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public void updateUserInfoProfilePicByAid(Integer aid, MultipartFile upload, HttpServletRequest request) {
        String filePath = "F:\\WEB_Developing\\XDChannel\\src\\main\\webapp\\images\\"; //定义上传文件的存放位置
        String fileName = upload.getOriginalFilename();  //获取上传文件的名字
        //判断文件夹是否存在,不存在则创建
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        String newFilePath = filePath + UUID.randomUUID() + fileName; //新文件的路径

        try {
            upload.transferTo(new File(newFilePath));  //将传来的文件写入新建的文件

        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUserInfoUserInfoByAid(Integer aid, String userInfo) {
        userInfoDao.updateUserInfoUserInfoByAid(aid,userInfo);
    }

    @Override
    public String getUserInfoProfilePicByAid(Integer aid) {
        return userInfoDao.findUserInfoProfilePicByAid(aid);
    }

    @Override
    public String getUserInfoUserInfoByAid(Integer aid) {
        return userInfoDao.findUserInfoUserInfoByAid(aid);
    }
}
