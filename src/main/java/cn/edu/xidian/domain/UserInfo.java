package cn.edu.xidian.domain;

/**
 * Created by 胡广鹏 on 2020/5/16 12:57
 */
public class UserInfo {

    private Integer aid;
    private String userInfo;
    private String userTag;
    private String profilePic;

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

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "aid=" + aid +
                ", userInfo='" + userInfo + '\'' +
                ", userTag='" + userTag + '\'' +
                ", profilePic='" + profilePic + '\'' +
                '}';
    }
}
