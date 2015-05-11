package com.biyanzhi.utils;

import java.io.File;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

public class BitmapUtils {
	/**
	 * ��ȡͼƬ�ļ���ת�ĽǶ�
	 * 
	 * @param path
	 *            ͼƬ����·��
	 * @return ͼƬ��ת�ĽǶ�
	 */
	public static int getPicRotate(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * ��ȡͼƬ�ļ�����Ϣ���Ƿ���ת��90�ȣ��������ת
	 * 
	 * @param bitmap
	 *            ��Ҫ��ת��ͼƬ
	 * @param path
	 *            ͼƬ��·��
	 */
	public static Bitmap reviewPicRotate(Bitmap bitmap, String path) {
		int degree = getPicRotate(path);
		if (degree != 0) {
			Matrix m = new Matrix();
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			m.setRotate(degree); // ��תangle��
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);// ��������ͼƬ

		}
		return bitmap;
	}

	/**
	 * / ��ͼƬ��С(�ֽڴ�С)����ͼƬ
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap FitSizeImg(String path) {
		if (path == null || path.length() < 1)
			return null;
		File file = new File(path);
		Bitmap resizeBmp = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// ����Խ�������ͼƬռ�õ�heapԽС ��Ȼ�������
		if (file.length() < 20480) { // 0-20k
			opts.inSampleSize = 1;
		} else if (file.length() < 51200) { // 20-50k
			opts.inSampleSize = 2;
		} else if (file.length() < 307200) { // 50-300k
			opts.inSampleSize = 4;
		} else if (file.length() < 819200) { // 300-800k
			opts.inSampleSize = 6;
		} else if (file.length() < 1048576) { // 800-1024k
			opts.inSampleSize = 8;
		} else {
			opts.inSampleSize = 10;
		}
		resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
		return resizeBmp;
	}

	public static int getBitmapHeight(String path) {
		Bitmap bmp = FitSizeImg(path);
		if (bmp != null) {
			return bmp.getHeight();
		}
		return 0;
	}
}
