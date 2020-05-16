package cn.edu.xidian.service;

import cn.edu.xidian.domain.FindUserRequest;
import cn.edu.xidian.domain.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by 胡广鹏 on 2020/5/14 21:24
 */
public interface UserInfoService {
    void updateUserInfoProfilePicByAid(Integer aid, MultipartFile upload, HttpServletRequest request);
    void updateUserInfoUserInfoByAid(Integer aid,String userInfo);
    String getUserInfoProfilePicByAid(Integer aid);
    String getUserInfoUserInfoByAid(Integer aid);
    List<UserInfo> findUser(FindUserRequest fur);
}
