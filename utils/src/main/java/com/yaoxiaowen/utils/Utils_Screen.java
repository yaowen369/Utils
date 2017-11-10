/**
 * 获得屏幕相关的辅助类
 *
 */
package com.yaoxiaowen.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

/**
 *  和屏幕 尺寸,dp,px转换有关的工具类  <br/>
 *
 * @author <a href="http://www.yaoxiaowen.com/">www.yaoxiaowen.com</a>
 * @version 0.0.1
 * <p>
 */
public final class Utils_Screen {
	private Utils_Screen() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 *获取屏幕宽度
	 */
	public static int getScreenWidth(Context context) {
		if (null != context) {
			WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics outMetrics = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(outMetrics);
			return outMetrics.widthPixels;
		}
		else {
			return 1280;
		}
	}

	/**
	 * 获取屏幕高度
	 */
	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

	/**
	 *获取状态栏高度
	 */
	public static int getStatusHeight(Context context) {

		int statusHeight = -1;
		try {
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusHeight;
	}

    /**
     * 获取导航栏高度
     */
    public static int getNaviBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /**
     *  屏幕截图带状态栏
     * @return 返回截图的Bitmap
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
		if(bmp != null && !bmp.isRecycled()){
			bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		}
        view.destroyDrawingCache();
        return bp;

    }

	/**
	 *屏幕截图去除状态栏
	 *  @return 返回截图的Bitmap
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		if(bmp != null && !bmp.isRecycled()){
			bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
		}
		view.destroyDrawingCache();
		return bp;
	}

	/**
	 * 获取屏幕密度 (所谓屏幕密度，就是 dp/px)
	 */
	public static float getDensityDpi(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.density / 1.0f;
	}

	/**
	 * 根据本手机屏幕密度,dp值转化为px值
	 */
	public static int dp2px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	/**
	 * 根据本手机屏幕密度,px值转化为px值
	 */
	public static int px2dp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 根据本手机屏幕密度,px值转化为sp值
	 * sp值专供字体字号使用. 更适合字体的缩放
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 根据本手机屏幕密度,sp值转化为px值
	 * sp值专供字体字号使用. 更适合字体的缩放
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

}
