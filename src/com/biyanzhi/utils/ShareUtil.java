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
		sp.setTitle("����ֵ");
		sp.setTitleUrl("http://123.56.46.254/biyanzhi/biyanzhi.html"); // ����ĳ�����
		sp.setText(content);
		sp.setSite("����ֵ");
		sp.setSiteUrl("http://123.56.46.254/biyanzhi/biyanzhi.html");
		sp.setImageUrl(image_path);
		sp.setShareType(Platform.SHARE_WEBPAGE);
		plat.share(sp);
	}

	public static void shareQQZone(String content, String image_path) {
		Platform plat = ShareSDK.getPlatform(QZone.NAME);
		ShareParams sp = new ShareParams();
		sp.setTitle("����ֵ");
		sp.setTitleUrl("http://123.56.46.254/biyanzhi/biyanzhi.html"); // ����ĳ�����
		sp.setText(content);
		sp.setImageUrl(image_path);
		sp.setSite("����ֵ");
		sp.setSiteUrl("http://123.56.46.254/biyanzhi/biyanzhi.html");
		sp.setShareType(Platform.SHARE_WEBPAGE);
		plat.share(sp);
	}

	public static void shareWechat(String content, String image_path) {
		Platform plat = ShareSDK.getPlatform("Wechat");
		ShareParams sp = new ShareParams();
		sp.setTitle("����ֵ");
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
		// ִ��ͼ�ķ���
		weibo.share(sp);
	}
}
