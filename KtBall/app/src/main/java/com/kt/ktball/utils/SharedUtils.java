package com.kt.ktball.utils;

import android.graphics.Bitmap;

import com.frame.app.utils.LogUtils;
import com.ktfootball.app.Constants;


import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.twitter.Twitter;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;


public class SharedUtils {

    /*
     * @param title    测试分享的标题
     * @param titleUrl  标题的超链接
     * @param text      测试分享的文本
     * @param imageUrl  测试图片网络地址
     * @param site     发布分享的网站名称
     * @param siteUrl  发布分享网站的地址
     */

    public String title = "测试分享的标题";
    public String titleUrl = "标题的超链接";
    public String text = "测试分享的文本";
    public String imageUrl = "http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg";
    public String site = Constants.HEAD_HOST;
    public String siteUrl = Constants.HEAD_HOST;
    public Bitmap bitmap = null;

    public SharedUtils() {

    }


    public void Shared_xlvb(PlatformActionListener paListener) {
        SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setTitle(title);
//        sp.setTitleUrl(titleUrl);
        sp.setText(text);
        sp.setImageData(bitmap);
        sp.setUrl(titleUrl);
        LogUtils.e(titleUrl);

        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        weibo.setPlatformActionListener(paListener); // 设置分享事件回调
        weibo.authorize();
        // 执行图文分享
        weibo.share(sp);
    }

    public void Shared_wx(PlatformActionListener paListener) {
        Wechat.ShareParams sp = new Wechat.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setTitle(title);
//        sp.setTitleUrl(titleUrl);
        sp.setText(text);
        sp.setImageData(bitmap);
        sp.setUrl(titleUrl);
        LogUtils.e(titleUrl);

        Platform qzone = ShareSDK.getPlatform(Wechat.NAME);
        qzone.setPlatformActionListener(paListener); // 设置分享事件回调
        // 执行图文分享
        qzone.share(sp);
    }

    public void Shared_wxsc(PlatformActionListener paListener) {
        WechatFavorite.ShareParams sp = new WechatFavorite.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setTitle(title);
//        sp.setTitleUrl(titleUrl);
        sp.setText(text);
        sp.setImageData(bitmap);
        sp.setUrl(titleUrl);
        LogUtils.e(titleUrl);

        Platform qzone = ShareSDK.getPlatform(WechatFavorite.NAME);
        qzone.setPlatformActionListener(paListener); // 设置分享事件回调
        // 执行图文分享
        qzone.share(sp);
    }

    public void Shared_qq(PlatformActionListener paListener) {
        QQ.ShareParams sp = new QQ.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setTitle(title);
        sp.setTitleUrl(titleUrl);
        sp.setText(text);
        sp.setImageData(bitmap);
        sp.setUrl(titleUrl);
        LogUtils.e(titleUrl);

        Platform qzone = ShareSDK.getPlatform(QQ.NAME);
        qzone.setPlatformActionListener(paListener); // 设置分享事件回调
        // 执行图文分享
        qzone.share(sp);
    }


    public void Shared_qqZom(PlatformActionListener paListener) {
        QZone.ShareParams sp = new QZone.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setTitle(title);
        sp.setTitleUrl(titleUrl);
        sp.setText(text);
        sp.setImageData(bitmap);
        sp.setUrl(titleUrl);
        LogUtils.e(titleUrl);

        Platform qzone = ShareSDK.getPlatform(QZone.NAME);
        qzone.setPlatformActionListener(paListener); // 设置分享事件回调
        // 执行图文分享
        qzone.share(sp);
    }


    public void Shared_pyq(PlatformActionListener paListener) {
        WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setTitle(title);
        sp.setText(text);
        sp.setImageData(bitmap);
        sp.setUrl(titleUrl);

        Platform qzone = ShareSDK.getPlatform(WechatMoments.NAME);
        qzone.setPlatformActionListener(paListener); // 设置分享事件回调
        // 执行图文分享
        qzone.share(sp);
    }

    public void Shared_twitter(PlatformActionListener paListener) {
        Twitter.ShareParams sp = new Twitter.ShareParams();
        sp.setText(text+titleUrl);
        sp.setImageUrl(imageUrl);

        Platform twitter = ShareSDK.getPlatform(Twitter.NAME);
        twitter.setPlatformActionListener(paListener); // 设置分享事件回调
        twitter.authorize();
        // 执行图文分享
        twitter.share(sp);
    }


    public void Shared_facebook(PlatformActionListener paListener) {
        Facebook.ShareParams sp = new Facebook.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setText(text);
        sp.setImageUrl(imageUrl);

        Platform facebook = ShareSDK.getPlatform(Facebook.NAME);
        facebook.setPlatformActionListener(paListener); // 设置分享事件回调
        facebook.authorize();
        // 执行图文分享
        facebook.share(sp);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleUrl() {
        return titleUrl;
    }

    public void setTitleUrl(String titleUrl) {
        this.titleUrl = titleUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
