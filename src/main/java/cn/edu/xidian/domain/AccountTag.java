package cn.edu.xidian.domain;

/**
 * Created by 胡广鹏 on 2020/5/16 12:09
 */
public class AccountTag {

    private Integer atid;
    private String aTagName;
    private String aTagInfo;

    public Integer getAtid() {
        return atid;
    }

    public void setAtid(Integer atid) {
        this.atid = atid;
    }

    public String getaTagName() {
        return aTagName;
    }

    public void setaTagName(String aTagName) {
        this.aTagName = aTagName;
    }

    public String getaTagInfo() {
        return aTagInfo;
    }

    public void setaTagInfo(String aTagInfo) {
        this.aTagInfo = aTagInfo;
    }

    @Override
    public String toString() {
        return "AccountTag{" +
                "atid=" + atid +
                ", aTagName='" + aTagName + '\'' +
                ", aTagInfo='" + aTagInfo + '\'' +
                '}';
    }
}
