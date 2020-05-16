package cn.edu.xidian.domain;

/**
 * Created by 胡广鹏 on 2020/5/16 12:01
 */
public class FindUserRequest {

    private Integer aid;
    private String userInfo;
    private String userTag;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getUserTag() {
        return userTag;
    }

    public void setUserTag(String userTag) {
        this.userTag = userTag;
    }

    @Override
    public String toString() {
        return "FindUserRequest{" +
                "aid=" + aid +
                ", userInfo='" + userInfo + '\'' +
                ", userTag='" + userTag + '\'' +
                '}';
    }
}
