package com.biyanzhi.utils;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qq.QQ.ShareParams;
import cn.sharesdk.tencent.qzone.QZone;

public class ShareUtil {
	public static void shareQQ(String content, String image_path) {
		Platform plat = ShareSDK.getPlatform(QQ.NAME);
		ShareParams sp = new ShareParams();
		sp.setTitle("比颜值");
		sp.setTitleUrl("http://123.56.46.254/biyanzhi/biyanzhi.html"); // 标题的超链接
		sp.setText(content);
		sp.setSite("比颜值");
		sp.setSiteUrl("http://123.56.46.254/biyanzhi/biyanzhi.html");
		sp.setImageUrl(image_path);
		sp.setShareType(Platform.SHARE_WEBPAGE);
		plat.share(sp);
	}

	public static void shareQQZone(String content, String image_path) {
		Platform plat = ShareSDK.getPlatform(QZone.NAME);
		ShareParams sp = new ShareParams();
		sp.setTitle("比颜值");
		sp.setTitleUrl("http://123.56.46.254/biyanzhi/biyanzhi.html"); // 标题的超链接
		sp.setText(content);
		sp.setImageUrl(image_path);
		sp.setSite("比颜值");
		sp.setSiteUrl("http://123.56.46.254/biyanzhi/biyanzhi.html");
		sp.setShareType(Platform.SHARE_WEBPAGE);
		plat.share(sp);
	}

	public static void shareWechat(String content, String image_path) {
		Platform plat = ShareSDK.getPlatform("Wechat");
		ShareParams sp = new ShareParams();
		sp.setTitle("比颜值");
		sp.setText(content);
		sp.setShareType(Platform.SHARE_WEBPAGE);
		sp.setUrl("http://123.56.46.254/biyanzhi/biyanzhi.html");
		sp.setImageUrl(image_path);
		plat.share(sp);

	}

	public static void shareWechatMoments(String title, String content,
			String image_path) {
		Platform plat = ShareSDK.getPlatform("WechatMoments");
		ShareParams sp = new ShareParams();
		sp.setTitle(title);
		sp.setText(content);
		sp.setShareType(Platform.SHARE_WEBPAGE);
		sp.setUrl("http://123.56.46.254/biyanzhi/biyanzhi.html");
		sp.setImageUrl(image_path);
		plat.share(sp);

	}

	public static void shareSinaWeiBo(String title, String content,
			String image_path) {
		ShareParams sp = new ShareParams();
		sp.setTitle(title);
		sp.setText(content);
		sp.setUrl("http://123.56.46.254/biyanzhi/biyanzhi.html");
		sp.setImageUrl(image_path);
		Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
		// 执行图文分享
		weibo.share(sp);
	}
}
