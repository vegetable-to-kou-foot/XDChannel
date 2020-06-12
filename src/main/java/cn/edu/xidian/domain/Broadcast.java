package cn.edu.xidian.domain;

/**
 * Created by 胡广鹏 on 2020/5/15 22:31
 */
public class Broadcast {
    private Integer bid;
    private Integer fid;
    private Integer aid;
    private String bcScript;
    private Integer likeIt;
    private String review;
    private String bcTag;
    private long timestp;
    private String limits;

    public Broadcast(Integer bid, Integer fid, Integer aid, String bcScript, Integer likeIt, String review, String bcTag, long timestp, String limits) {
        this.bid = bid;
        this.fid = fid;
        this.aid = aid;
        this.bcScript = bcScript;
        this.likeIt = likeIt;
        this.review = review;
        this.bcTag = bcTag;
        this.timestp = timestp;
        this.limits = limits;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getBcScript() {
        return bcScript;
    }

    public void setBcScript(String bcScript) {
        this.bcScript = bcScript;
    }

    public Integer getLikeIt() {
        return likeIt;
    }

    public void setLikeIt(Integer likeIt) {
        this.likeIt = likeIt;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getBcTag() {
        return bcTag;
    }

    public void setBcTag(String bcTag) {
        this.bcTag = bcTag;
    }

    public long getTimestp() {
        return timestp;
    }

    public void setTimestp(long timestp) {
        this.timestp = timestp;
    }

    public String getLimits() {
        return limits;
    }

    public void setLimits(String limits) {
        this.limits = limits;
    }

    @Override
    public String toString() {
        return "Broadcast{" +
                "bid=" + bid +
                ", fid=" + fid +
                ", aid=" + aid +
                ", bcScript='" + bcScript + '\'' +
                ", likeIt=" + likeIt +
                ", review='" + review + '\'' +
                ", bcTag='" + bcTag + '\'' +
                ", timestp=" + timestp +
                ", limits='" + limits + '\'' +
                '}';
    }
}
