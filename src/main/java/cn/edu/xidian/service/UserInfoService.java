package cn.edu.xidian.service;

import cn.edu.xidian.domain.FindUserRequest;
import cn.edu.xidian.domain.UserInfo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by 胡广鹏 on 2020/5/14 21:24
 */
public interface UserInfoService {
    void updateUserInfoProfilePicByAid(Integer aid, MultipartFile upload);
    void updateUserInfoUserInfoByAid(Integer aid,String userInfo);
    String getUserInfoProfilePicByAid(Integer aid);
    String getUserInfoUserInfoByAid(Integer aid);
    List<Integer> findUser(FindUserRequest fur);
    List<JSONObject> getUserInfoByAidList(List<Integer> aidList);
}
