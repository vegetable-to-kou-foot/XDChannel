package cn.edu.xidian.service.impl;

import cn.edu.xidian.dao.UserInfoDao;
import cn.edu.xidian.domain.FindUserRequest;
import cn.edu.xidian.domain.UserInfo;
import cn.edu.xidian.service.UserInfoService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by 胡广鹏 on 2020/5/14 21:24
 */
@Service("userServiceImpl")
public class UserInfoServiceImpl implements UserInfoService {

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

    @Override
    public List<UserInfo> findUser(FindUserRequest fur) {
        List<UserInfo> ansTmp = Collections.emptyList();
        List<UserInfo> ans = Collections.emptyList();
        //用userInfo过滤一次
        List<UserInfo> userInfoList = userInfoDao.findUserInfoAll();
        JSONObject baseUserInfo = JSON.parseObject(fur.getUserInfo());
        for (UserInfo ui : userInfoList){
            JSONObject nowUserInfo = JSON.parseObject(ui.getUserInfo());
            if (JSONAContainsB(nowUserInfo,baseUserInfo)){
                ansTmp.add(ui);
            }
        }

        //用userTag再过滤一次
        JSONArray baseUserTagCond = JSON.parseArray(fur.getUserTag());
        for (UserInfo ui : ansTmp){
            JSONObject nowUserTag = JSON.parseObject(ui.getUserTag());
            if (JSONAAccordB(nowUserTag,baseUserTagCond)){
                ans.add(ui);
            }
        }

        return ans;

    }

    private boolean JSONAContainsB(JSONObject A, JSONObject B){
        for (Map.Entry<String, Object> entry : B.entrySet()){
            if (!(A.containsKey(entry.getKey()) && A.get(entry.getKey()) == B.get(entry.getKey()))){
                return false;
            }
        }
        return true;
    }


    private boolean JSONAAccordB(JSONObject target, JSONArray cond){
        boolean ans = false;
        for (int i=0;i<cond.size();i++){
            JSONObject c = cond.getJSONObject(i);
            String tagName = (String) c.get("tag");
            if (!target.containsKey(tagName))return false;
            Integer tagValA = (Integer) target.get(tagName);
            Integer tagValB = (Integer) c.get("tagVal");
            String op = (String) c.get("tagOp");
            switch (op) {
                case ">":
                    if (tagValA > tagValB) ans = true;
                    else return false;
                    break;
                case "<":
                    if (tagValA < tagValB) ans = true;
                    else return false;
                    break;
                case "=":
                    if (tagValA.equals(tagValB)) ans = true;
                    else return false;
                    break;
                default:
                    return false;
            }
        }
        return ans;
    }
}
