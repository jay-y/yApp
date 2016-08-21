package com.dream.example.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

import com.dream.example.data.support.AppConsts;

import org.yapp.utils.FileUtil;
import org.yapp.utils.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ClassName: BitmapUtil <br>
 * Description: 位图操作相关工具类. <br>
 * Date: 2016-3-24 下午2:10:46 <br>
 *
 * @author ysj
 * @since JDK 1.7
 */
public class BitmapUtil {

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
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
            Log.e(e.getMessage(), e);
        }
        return degree;
    }

    /**
     * 旋转图片，使图片保持正确的方向。
     *
     * @param degrees 原始图片的角度
     * @param bitmap  原始图片
     * @return Bitmap 旋转后的图片
     */
    public static Bitmap rotateBitmap(int degrees, Bitmap bitmap) {
        if (degrees == 0 || null == bitmap) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (null != bitmap) {
            bitmap.recycle();
            bitmap = null;
        }
        return bmp;
    }

    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImage(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        if (null != bitmap && bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        return bmp;
    }

    /**
     * 将bitmap保存在SD
     *
     * @param bitmap   图片bitmap
     * @param fileName 要保存图片名
     * @param format   压缩格式
     * @param quality  压缩质量值
     */
    public static void saveImage(Bitmap bitmap, String fileName, Bitmap.CompressFormat format, int quality) {
        try {
            File out = new File(FileUtil.getCacheDir(AppConsts.AppConfig.CACHE_IMG), fileName);
            if (out.exists()) {
                out.delete();
            }
            FileOutputStream fos = new FileOutputStream(out);
            bitmap.compress(format, quality, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            Log.e(e.getMessage(), e);
        }
    }
}
