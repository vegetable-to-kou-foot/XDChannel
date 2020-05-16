package cn.edu.xidian.domain;

/**
 * Created by 胡广鹏 on 2020/5/15 23:10
 */
public class FindBroadcastRequset {
    private Integer bidVal;
    private String bidOp;
    private Integer fidVal;
    private String fidOp;
    private Integer aidVal;
    private String aidOp;
    private Integer likeItVal;
    private String likeItOp;
    private Integer timestpVal;
    private String timestpOp;
    private String bcTagsString;
    private String bcScript;

    public Integer getBidVal() {
        return bidVal;
    }

    public void setBidVal(Integer bidVal) {
        this.bidVal = bidVal;
    }

    public String getBidOp() {
        return bidOp;
    }

    public void setBidOp(String bidOp) {
        this.bidOp = bidOp;
    }

    public Integer getFidVal() {
        return fidVal;
    }

    public void setFidVal(Integer fidVal) {
        this.fidVal = fidVal;
    }

    public String getFidOp() {
        return fidOp;
    }

    public void setFidOp(String fidOp) {
        this.fidOp = fidOp;
    }

    public Integer getAidVal() {
        return aidVal;
    }

    public void setAidVal(Integer aidVal) {
        this.aidVal = aidVal;
    }

    public String getAidOp() {
        return aidOp;
    }

    public void setAidOp(String aidOp) {
        this.aidOp = aidOp;
    }

    public Integer getLikeItVal() {
        return likeItVal;
    }

    public void setLikeItVal(Integer likeItVal) {
        this.likeItVal = likeItVal;
    }

    public String getLikeItOp() {
        return likeItOp;
    }

    public void setLikeItOp(String likeItOp) {
        this.likeItOp = likeItOp;
    }

    public Integer getTimestpVal() {
        return timestpVal;
    }

    public void setTimestpVal(Integer timestpVal) {
        this.timestpVal = timestpVal;
    }

    public String getTimestpOp() {
        return timestpOp;
    }

    public void setTimestpOp(String timestpOp) {
        this.timestpOp = timestpOp;
    }

    public String getBcTagsString() {
        return bcTagsString;
    }

    public void setBcTagsString(String bcTagsString) {
        this.bcTagsString = bcTagsString;
    }

    public String getBcScript() {
        return bcScript;
    }

    public void setBcScript(String bcScript) {
        this.bcScript = bcScript;
    }

    @Override
    public String toString() {
        return "FindBroadcastRequset{" +
                "bidVal=" + bidVal +
                ", bidOp='" + bidOp + '\'' +
                ", fidVal=" + fidVal +
                ", fidOp='" + fidOp + '\'' +
                ", aidVal=" + aidVal +
                ", aidOp='" + aidOp + '\'' +
                ", likeItVal=" + likeItVal +
                ", likeItOp='" + likeItOp + '\'' +
                ", timestpVal=" + timestpVal +
                ", timestpOp='" + timestpOp + '\'' +
                ", bcTagsString='" + bcTagsString + '\'' +
                ", bcScript='" + bcScript + '\'' +
                '}';
    }
}
