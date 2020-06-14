package cn.edu.xidian.service.impl;

import cn.edu.xidian.dao.BroadcastDao;
import cn.edu.xidian.dao.BroadcastTagDao;
import cn.edu.xidian.domain.Broadcast;
import cn.edu.xidian.domain.FindBroadcastRequset;
import cn.edu.xidian.service.BroadcastService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 胡广鹏 on 2020/5/15 23:21
 */
@Service("broadcastServiceImpl")
public class BroadcastServiceImpl implements BroadcastService {

    @Autowired
    BroadcastDao broadcastDao;
    @Autowired
    BroadcastTagDao broadcastTagDao;

    @Override
    public void addBroadcast(Broadcast bc) {
        broadcastDao.addUBC(bc);
        JSONArray bcTagArray = JSONObject.parseArray(bc.getBcTag());
        for (Object o : bcTagArray) {
            String bcTagNow = (String) o;
            Integer btid = broadcastTagDao.getBroadcastTagBtidByBTagName(bcTagNow);
            if (btid == null) {
                broadcastTagDao.addBroadcastTag(bcTagNow, "[]");
                btid = broadcastTagDao.getBroadcastTagBtidByBTagName(bcTagNow);
            }
            String bTagInfoString = broadcastTagDao.getBroadcastTagBTagInfoByBtid(btid);
            JSONArray bTagInfoArray = JSONArray.parseArray(bTagInfoString);
            bTagInfoArray.add(bc.getBid());
            broadcastTagDao.updateBroadcastTagBTagInfoByBtid(btid, bTagInfoArray.toJSONString());
        }
    }

    @Override
    public void deleteBroadcast(Integer bid, Integer aid) {
        Broadcast bc = broadcastDao.getUBCByBid(bid);
        if (!bc.getAid().equals(aid))return;
        JSONArray bcTagArray = JSON.parseArray(bc.getBcTag());
        for (Object o:bcTagArray){
            String bcTagNow = (String) o;
            Integer btid = broadcastTagDao.getBroadcastTagBtidByBTagName(bcTagNow);
            if (btid != null) {
                String bTagInfoString = broadcastTagDao.getBroadcastTagBTagInfoByBtid(btid);
                JSONArray bTagInfoArray = JSONArray.parseArray(bTagInfoString);
                bTagInfoArray.remove(bc.getBid());
                broadcastTagDao.updateBroadcastTagBTagInfoByBtid(btid, bTagInfoArray.toJSONString());
            }
        }
        broadcastDao.deleteUBCByBid(bid);
    }

    @Override
    public void editBroadcast(Integer bid, String bcScript,long timestp) {
        broadcastDao.updateUBCBCScriptByBid(bid,bcScript,timestp);
    }

    @Override
    public List<JSONObject> findBroadcast(FindBroadcastRequset fbr) {
        String condition = "";
        String baseString = ">,<,=";
        Set<String> baseOps = new HashSet<>(Arrays.asList(baseString.split(",")));

        if (baseOps.contains(fbr.getBidOp())){
            condition += "bid"+fbr.getBidOp()+fbr.getBidVal();
        }else{
            condition += "1=1";
        }

        if (baseOps.contains(fbr.getFidOp())){
            condition += " AND fid"+fbr.getFidOp()+fbr.getFidVal();
        }else{
            condition += " AND 1=1";
        }

        if (baseOps.contains(fbr.getAidOp())){
            condition += " AND aid"+fbr.getAidOp()+fbr.getAidVal();
        }else{
            condition += " AND 1=1";
        }

        if (baseOps.contains(fbr.getLikeItOp())){
            condition += " AND likeIt"+fbr.getLikeItOp()+fbr.getLikeItVal();
        }else{
            condition += " AND 1=1";
        }

        if (baseOps.contains(fbr.getTimestpOp())){
            condition += " AND timestp"+fbr.getTimestpOp()+fbr.getTimestpVal();
        }else{
            condition += " AND 1=1";
        }

        if (fbr.getBcScript()!=null && fbr.getBcScript().length() > 0){
            condition += " AND bcScript LIKE \'%%"+fbr.getBcScript()+"%%\'";
        }else{
            condition += " AND 1=1";
        }

        condition += " ORDER BY bid DESC";

        Integer lines = broadcastDao.getUBCLines();

        Integer x = fbr.getPageSize();
        Integer y = fbr.getPageNum();
        int from = x*y-x;
        Integer to = x*y;
        if (from < 0)from = 0;
        if (to > lines)to = lines;
        if (from > lines)from = lines;
        if (to < 0)to = 0;

        condition += " LIMIT "+from + "," + to;

        List<Broadcast> ansTmp = broadcastDao.getUBCByCond(condition);

        if (ansTmp == null || ansTmp.size() == 0)return Collections.emptyList();

        String baseBCTagsString = fbr.getBcTagsString();
        List<String> baseBCTagsList = JSONArray.parseArray(baseBCTagsString,String.class);

        List<JSONObject> ans = new ArrayList<>(Collections.emptyList());

        for (Broadcast bc : ansTmp){
            String bcTagString  = bc.getBcTag();
            List<String> bcTagList = JSONArray.parseArray(bcTagString,String.class);
            if (bcTagList.containsAll(baseBCTagsList)){
                JSONObject tmp = (JSONObject)JSONObject.toJSON(bc);
                List<String> tmp2 = JSON.parseArray(bc.getBcTag(),String.class);
                tmp.put("bcTag",tmp2);
                ans.add(tmp);
            }
        }

        return ans;

    }
}
