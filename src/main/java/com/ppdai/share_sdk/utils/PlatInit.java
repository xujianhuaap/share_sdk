package com.ppdai.share_sdk.utils;

import java.util.HashMap;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by xujianhua on 2016/5/10.
 */
public class PlatInit {
    public static void initPlat(){
        initWechat("AppId","AppSecret");
        initWechatMoments("AppId","AppSecret");
        initQQ("AppId","AppKey");
        initQZone("AppId","AppKey");
        initShortMessage();
    }
    public static void initShortMessage(){
        HashMap<String,Object> hashMap=new HashMap<String,Object>();
        initPlatFormInfo(hashMap,"13","5");
        ShareSDK.setPlatformDevInfo(ShortMessage.NAME,hashMap);
    }
    public static void initQZone(String appKey,String appSeccret){
        HashMap<String,Object> hashMap =initQQPlatFormInfo("3","4",appKey,appSeccret);
        hashMap.put("ShareByAppClient","false");
        ShareSDK.setPlatformDevInfo(QZone.NAME,hashMap);
    }
    public static void initQQ(String appKey,String appSeccret){
        HashMap<String,Object> hashMap =initQQPlatFormInfo("7","3",appKey,appSeccret);
        hashMap.put("ShareByAppClient","false");
        ShareSDK.setPlatformDevInfo(QQ.NAME,hashMap);
    }
    public static void initWechatMoments(String appKey,String appSeccret){
        HashMap<String,Object> hashMap =initWeChatPlatFormInfo("5","2",appKey,appSeccret);
        hashMap.put("BypassApproval","false");
        ShareSDK.setPlatformDevInfo(WechatMoments.NAME,hashMap);
    }
    public static void initWechat(String appKey,String appSeccret){
        HashMap<String,Object> hashMap =initWeChatPlatFormInfo("4","1",appKey,appSeccret);
        hashMap.put("BypassApproval","false");
        ShareSDK.setPlatformDevInfo(Wechat.NAME,hashMap);
    }
    public static HashMap<String,Object> initQQPlatFormInfo(String id,String sortId,String appId,String appKey){
        HashMap<String,Object> hashMap = new HashMap<String, Object>();
        initPlatFormInfo(hashMap,id,sortId);
        if(appKey!=null&&appId!=null){
            hashMap.put("AppKey",appKey);
            hashMap.put("AppId",appId);
        }
        return hashMap;
    }
    public static HashMap<String,Object> initWeChatPlatFormInfo(String id,String sortId,String appId,String appSecret){
        HashMap<String,Object> hashMap = new HashMap<String, Object>();
        initPlatFormInfo(hashMap,id,sortId);
        if(appId!=null&&appSecret!=null){
            hashMap.put("AppId",appId);
            hashMap.put("AppSecrete",appSecret);
        }
        return hashMap;
    }
    public  static  void initPlatFormInfo(HashMap<String,Object> hashMap,String id,String sortId){

        hashMap.put("Id",id);
        hashMap.put("SortId",sortId);
        hashMap.put("Enable","true");
    }
}
