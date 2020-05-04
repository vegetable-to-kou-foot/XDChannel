package cn.edu.xidian.domain;

public class Account {
    private Integer aid;
    private String accName;
    private String userPswd;
    private String email;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getUserPswd() {
        return userPswd;
    }

    public void setUserPswd(String userPswd) {
        this.userPswd = userPswd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Account{" +
                "aid=" + aid +
                ", accName='" + accName + '\'' +
                ", userPswd='" + userPswd + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
