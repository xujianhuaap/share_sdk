package com.ppdai.share_sdk.utils;

import android.content.ClipData;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.ppdai.share_sdk.R;
import com.ppdai.share_sdk.onekeyshare.OnekeyShare;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;


/**
 * Created by xujianhua on 2016/4/8.
 */
public class ShareUtils {
    public static final String APPKEY="AppKey";
    public static final String IMGURL="imgUrl";
    public static void oneKeyShare(final Context context, String title, String titleUrl, final String content){
        oneKeyShare(context,title,titleUrl,content,null);
    }
    public static void oneKeyShare(final Context context, final String title, final String titleUrl, final String content, String imgUrl){
        if(context==null)return;
        if(title==null)return;
        if(titleUrl==null)return;
        ShareSDK.initSDK(context,APPKEY);
        PlatInit.initPlat();
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();  //关闭sso授权
        oks.setTitle(title);
        oks.setTitleUrl(titleUrl);  // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setText(content);// text是分享文本，所有平台都需要这个字段
        if(imgUrl==null) {
            imgUrl = IMGURL;
        }
        oks.setImageUrl(imgUrl);
        oks.setUrl(titleUrl ); // url仅在微信（包括好友和朋友圈）中使用
        oks.setSite("ppdai.com");  // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSiteUrl("http://m.ppdai.com");// siteUrl是分享此内容的网站地址，仅在QQ空间使用

        View.OnClickListener stickBoardListenre=new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                copyShareContentIntoClipboard(context,content);
            }
        };

        addStickBoardPlat(context,stickBoardListenre,oks);
        oks.addHiddenPlatform(WechatFavorite.NAME);
        oks.show(context);
    }
    private static void addStickBoardPlat(Context context,View.OnClickListener listener,OnekeyShare onekeyShare){
        if(context!=null&&listener!=null){
            addCustomerPlat(context,R.string.ssdk_stickboard,R.drawable.ssdk_oks_classic_stickboard,listener,onekeyShare);
        }
    }
    private static void addCustomerPlat(Context context,int lableId,int bitmapId,View.OnClickListener listener,OnekeyShare onekeyShare){
        if(context!=null&&listener!=null){
            Resources resources=context.getResources();
            String lable=resources.getString(lableId);
            Bitmap bitmap=BitmapFactory.decodeResource(resources, bitmapId);
            addPlat(lable,bitmap,listener,onekeyShare);
        }
    }
    private static void addPlat(String lable,Bitmap bitmap,View.OnClickListener listener,OnekeyShare onekeyShare){
        if(!TextUtils.isEmpty(lable)&&bitmap!=null){
            onekeyShare.setCustomerLogo(bitmap,lable,listener);
        }
    }

    private static void copyShareContentIntoClipboard(Context context,String content) {
        if (android.os.Build.VERSION.SDK_INT > 11) {
            android.content.ClipboardManager c = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            c.setPrimaryClip(ClipData.newPlainText("ppdai分享", content));
        } else {
            android.text.ClipboardManager c = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            c.setText(content);
        }
        Toast.makeText(context,"已复制至剪贴板中",Toast.LENGTH_SHORT).show();
    }
}
