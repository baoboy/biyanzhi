package com.biyanzhi.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

public class BitmapUtils {
	/**
	 * ��Bitmap�ļ�����Ϊ�����ļ�
	 * 
	 * @param bmp
	 * @param filename
	 */
	public static void createImgToFile(Bitmap bmp, String filename) {
		FileOutputStream b = null;
		try {
			b = new FileOutputStream(filename);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, b);// ������д���ļ�
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				b.flush();
				b.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

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

	public static int[] getBitmapHeightAndWidth(String path) {

		Bitmap bmp = FitSizeImg(path);
		if (bmp != null) {
			return new int[] { bmp.getHeight(), bmp.getWidth() };
		}
		return new int[] { 0, 0 };
	}

	public static File getImageFile(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// ��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// ��ʱ����bmΪ��
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// ���������ֻ��Ƚ϶���800*480�ֱ��ʣ����ԸߺͿ���������Ϊ
		float hh = 800f;// �������ø߶�Ϊ800f
		float ww = 480f;// �������ÿ��Ϊ480f
		// ���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��
		int be = 1;// be=1��ʾ������
		if (w > h && w > ww) {// �����ȴ�Ļ����ݿ�ȹ̶���С����
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// ����߶ȸߵĻ����ݿ�ȹ̶���С����
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// �������ű���
		// ���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return compressPicture(bitmap, srcPath);// ѹ���ñ�����С���ٽ�������ѹ��
	}

	/**
	 * ѹ��ͼƬ�ϴ�
	 * 
	 * @param picPath
	 * @return
	 */
	public static File compressPicture(Bitmap bmp, String picPath) {
		if (bmp == null) {
			return null;
		}
		String fileName = picPath.substring(picPath.lastIndexOf("/"));
		// ������sdCard
		String filePthh = FileUtils.getAppRootDir() + fileName;
		File file = new File(filePthh);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 70;//
		bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
		while (baos.toByteArray().length / 1024 > 100) {
			baos.reset();
			options -= 10;
			bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}
}
