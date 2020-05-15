package cn.edu.xidian.service;

/**
 * Created by 胡广鹏 on 2020/5/15 10:20
 */
public interface UserTagService {

    void addUserTag(Integer aid,String aTagName,String aTagValue);
    void deleteUserTagByAid(Integer aid,String aTagName);
    String findUserTagByAid(Integer aid);
    String findAccountTagByTagName(String aTagName);

}
