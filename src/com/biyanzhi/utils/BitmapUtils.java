package com.biyanzhi.utils;

import java.io.IOException;

import android.graphics.Bitmap;
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
}
