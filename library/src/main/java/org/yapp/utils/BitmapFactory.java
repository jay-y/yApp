package org.yapp.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapFactory extends android.graphics.BitmapFactory {
	private static Options opts;

	private static void init() {
		if (opts == null) {
			opts = new Options();
		}
		/** 为位图设置100K的缓存 **/
		opts.inTempStorage = new byte[100 * 1024];
		/** 设置位图颜色显示优化方式 **/
		// ALPHA_8：每个像素占用1byte内存(8位)
		// ARGB_4444:每个像素占用2byte内存(16位)
		// ARGB_8888:每个像素占用4byte内存(32位)
		// RGB_565:每个像素占用2byte内存(16位)
		// Android默认的颜色模式为ARGB_8888，这个颜色模式色彩最细腻，显示质量最高。但同样的，占用的内存也最大。
		// 也就意味着一个像素点占用4个字节的内存。我们来做一个简单的计算题：3200*2400*4 bytes = 30M
		opts.inPreferredConfig = Bitmap.Config.RGB_565;
	}

	public static Bitmap decodeFile(String path) {
		init();
		opts.inSampleSize = 1;
		return decodeFile(path, opts);
	}

	/**
	 * getBitmap:(根据输入流,缩放系数,获取处理后的位图). <br>
	 * 
	 * @author ysj
	 * @param fis
	 * @param inSampleSize
	 * @return
	 * @since JDK 1.7 date: 2016-1-16 下午3:13:51 <br>
	 */
	public static Bitmap getBitmap(InputStream fis, int inSampleSize) {
		ByteArrayOutputStream bos = null;
		Bitmap bm = null;
		byte[] picByte = null;
		try {
			bos = new ByteArrayOutputStream();
			byte[] bytes = new byte[1024];
			int length = -1;
			while ((length = fis.read(bytes)) != -1) {
				bos.write(bytes, 0, length);
			}
			picByte = bos.toByteArray();
			Options opts = new Options();
			// true,只是读图片大小，不申请bitmap内存
			opts.inJustDecodeBounds = true;
			opts.inPreferredConfig = Bitmap.Config.RGB_565;
			BitmapFactory.decodeByteArray(picByte, 0, picByte.length, opts);
			// 设为false，这次不是预读取图片大小，而是返回申请内存，bitmap数据
			opts.inJustDecodeBounds = false;
			opts.inSampleSize = inSampleSize;
			bm = BitmapFactory
					.decodeByteArray(picByte, 0, picByte.length, opts);
		} catch (Exception e) {
			Log.e(e.getMessage(), e);
		} finally {
			if (fis != null) {
				try {
					bos.close();
					fis.close();
				} catch (IOException e) {
					Log.e(e.getMessage(), e);
				}
			}
		}
		return (bm == null) ? null : bm;
	}

	/**
	 * getBitmap:(根据输入流,ImageView组件高宽属性,获取处理后的位图). <br>
	 * 
	 * @author ysj
	 * @param fis
	 * @param imageView
	 * @return
	 * @since JDK 1.7 date: 2016-1-16 下午3:13:51 <br>
	 */
	public static Bitmap getBitmap(InputStream fis, ImageView imageView) {
		ByteArrayOutputStream bos = null;
		Bitmap bm = null;
		byte[] picByte = null;
		try {
			bos = new ByteArrayOutputStream();
			byte[] bytes = new byte[1024];
			int length = -1;
			while ((length = fis.read(bytes)) != -1) {
				bos.write(bytes, 0, length);
			}
			picByte = bos.toByteArray();
			Options opts = new Options();
			// true,只是读图片大小，不申请bitmap内存
			opts.inJustDecodeBounds = true;
			opts.inPreferredConfig = Bitmap.Config.RGB_565;
			BitmapFactory.decodeByteArray(picByte, 0, picByte.length, opts);
			// 设为false，这次不是预读取图片大小，而是返回申请内存，bitmap数据
			opts.inJustDecodeBounds = false;
			opts.inSampleSize = BitmapFactory
					.calculateInSampleSize(opts, imageView.getMeasuredWidth(),
							imageView.getMeasuredHeight());
			bm = BitmapFactory
					.decodeByteArray(picByte, 0, picByte.length, opts);
		} catch (Exception e) {
			Log.e(e.getMessage(), e);
		} finally {
			if (fis != null) {
				try {
					bos.close();
					fis.close();
				} catch (IOException e) {
					Log.e(e.getMessage(), e);
				}
			}
		}
		return (bm == null) ? null : bm;
	}

	/**
	 * getBitmap:(根据输入流,限制内存大小,获取处理后的位图). <br>
	 * 
	 * @author ysj
	 * @param fis
	 * @param limitMem
	 * @return
	 * @since JDK 1.7 date: 2016-1-16 下午3:13:51 <br>
	 */
	public static Bitmap getBitmap(InputStream fis, long limitMem) {
		ByteArrayOutputStream bos = null;
		Bitmap bm = null;
		byte[] picByte = null;
		try {
			bos = new ByteArrayOutputStream();
			byte[] bytes = new byte[1024];
			int length = -1;
			while ((length = fis.read(bytes)) != -1) {
				bos.write(bytes, 0, length);
			}
			picByte = bos.toByteArray();
			Options opts = new Options();
			// true,只是读图片大小，不申请bitmap内存
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeByteArray(picByte, 0, picByte.length, opts);
			// 设为false，这次不是预读取图片大小，而是返回申请内存，bitmap数据
			opts.inJustDecodeBounds = false;
			opts.inSampleSize = BitmapFactory.calculateInSampleSize(opts, limitMem);
			bm = BitmapFactory.decodeByteArray(picByte, 0, picByte.length, opts);
		} catch (Exception e) {
			Log.e(e.getMessage(), e);
		} finally {
			if (fis != null) {
				try {
					bos.close();
					fis.close();
				} catch (IOException e) {
					Log.e(e.getMessage(), e);
				}
			}
		}
		return (bm == null) ? null : bm;
	}
	
	/**
	 * getBitmap:(根据输入流,限制内存大小,获取处理后的位图). <br>
	 * 
	 * @author ysj
	 * @param fis
	 * @param n 最大分配数
	 * @param limitMem
	 * @return
	 * @since JDK 1.7 date: 2016-1-16 下午3:13:51 <br>
	 */
	public static Bitmap getBitmap(InputStream fis,int n, long limitMem) {
		ByteArrayOutputStream bos = null;
		Bitmap bm = null;
		byte[] picByte = null;
		try {
			bos = new ByteArrayOutputStream();
			byte[] bytes = new byte[1024];
			int length = -1;
			while ((length = fis.read(bytes)) != -1) {
				bos.write(bytes, 0, length);
			}
			picByte = bos.toByteArray();
			Options opts = new Options();
			// true,只是读图片大小，不申请bitmap内存
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeByteArray(picByte, 0, picByte.length, opts);
			// 设为false，这次不是预读取图片大小，而是返回申请内存，bitmap数据
			opts.inJustDecodeBounds = false;
			opts.inSampleSize = BitmapFactory.calculateInSampleSize(opts,n,limitMem);
			bm = BitmapFactory.decodeByteArray(picByte, 0, picByte.length, opts);
		} catch (Exception e) {
			Log.e(e.getMessage(), e);
		} finally {
			if (fis != null) {
				try {
					bos.close();
					fis.close();
				} catch (IOException e) {
					Log.e(e.getMessage(), e);
				}
			}
		}
		return (bm == null) ? null : bm;
	}

	/**
	 * calculateInSampleSize:(缩放系数计算). <br>
	 * 
	 * @author
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 * @since JDK 1.7 date: 2015-12-30 下午6:20:31 <br>
	 */
	public static int calculateInSampleSize(Options options,
			int reqWidth, int reqHeight) {
		// 源图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			// 计算出实际宽高和目标宽高的比率
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
			// 一定都会大于等于目标的宽和高。
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		Log.d("DEBUG:[BitmapFactory][height]" + height);
		Log.d("DEBUG:[BitmapFactory][width]" + width);
		Log.d("DEBUG:[BitmapFactory][reqHeight]" + reqHeight);
		Log.d("DEBUG:[BitmapFactory][reqWidth]" + reqWidth);
		Log.d("DEBUG:[BitmapFactory][inSampleSize]"+inSampleSize);
		return inSampleSize;
	}

	/**
	 * calculateInSampleSize:(缩放系数计算). <br>
	 * 
	 * @author
	 * @param options
	 * @param limitMem
	 * @return
	 * @since JDK 1.7 date: 2015-12-30 下午6:20:31 <br>
	 */
	public static int calculateInSampleSize(Options options,
			long limitMem) {
		int byteLength = 2;
		if (options.inPreferredConfig == Bitmap.Config.ARGB_8888) {
			byteLength = 4;
		} else if (options.inPreferredConfig == Bitmap.Config.RGB_565
				|| options.inPreferredConfig == Bitmap.Config.ARGB_4444) {
			byteLength = 2;
		} else if (options.inPreferredConfig == Bitmap.Config.ALPHA_8) {
			byteLength = 1;
		}
		// 源图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int totalMem = (int) (Runtime.getRuntime().totalMemory() / 1024 / 1024);
		int availMem = (int) limitMem - totalMem;
		int needMem = ((height * width) * byteLength) / 1024 / 1024;
		int inSampleSize = 1;
		double x = needMem / availMem;
		if (needMem > 0 && x > 1) {
			inSampleSize = (int) Math.ceil(x);
			System.gc();
		}else{
			inSampleSize = 2;
		}
		Log.d("DEBUG:[BitmapFactory][needMem]" + needMem);
		Log.d("DEBUG:[BitmapFactory][availMem]" + availMem);
		Log.d("DEBUG:[BitmapFactory][inSampleSize]"+inSampleSize);
		return inSampleSize;
	}
	
	/**
	 * calculateInSampleSize:(缩放系数计算). <br>
	 * 
	 * @author
	 * @param options
	 * @param n 最大分配数
	 * @param limitMem
	 * @return
	 * @since JDK 1.7 date: 2015-12-30 下午6:20:31 <br>
	 */
	public static int calculateInSampleSize(Options options,int n,
			long limitMem) {
		int byteLength = 2;
		if (options.inPreferredConfig == Bitmap.Config.ARGB_8888) {
			byteLength = 4;
		} else if (options.inPreferredConfig == Bitmap.Config.RGB_565
				|| options.inPreferredConfig == Bitmap.Config.ARGB_4444) {
			byteLength = 2;
		} else if (options.inPreferredConfig == Bitmap.Config.ALPHA_8) {
			byteLength = 1;
		}
		// 源图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int totalMem = (int) (Runtime.getRuntime().totalMemory() / 1024 / 1024);
		int availMem = (int) (limitMem - totalMem)/n;
		int needMem = ((height * width) * byteLength) / 1024 / 1024;
		int inSampleSize = 1;
		double x = needMem / availMem;
		if (needMem > 0 && x > 1) {
			inSampleSize = (int) Math.ceil(x);
			System.gc();
		}else{
			inSampleSize = 2;
		}
		Log.d("DEBUG:[BitmapFactory][needMem]" + needMem);
		Log.d("DEBUG:[BitmapFactory][availMem]" + availMem);
		Log.d("DEBUG:[BitmapFactory][inSampleSize]"+inSampleSize);
		return inSampleSize;
	}
}
