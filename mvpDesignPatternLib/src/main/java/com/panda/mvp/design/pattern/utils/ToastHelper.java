package com.panda.mvp.design.pattern.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * @author Pearce
 * @date 07/17/2019
 * @description
 */
public class ToastHelper {

    private static String sOldMsg = "";
    private static Toast sToast;
    private static long sLastTime;
    private static long sCurrentTime;

    /**
     * 显示时间长
     *
     * @param context
     * @param text
     */
    public static void showLong(Context context, String text) {
        show(context, text, Toast.LENGTH_LONG);
    }

    /**
     * 显示时间短
     *
     * @param context
     * @param text
     */
    public static void showShort(Context context, String text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    private static void show(Context mContext, String msg, int mode) {
        Context context = new WeakReference<>(mContext).get();
        if (sToast == null) {
            // 这样的话，不管传递什么content进来，都只会引用全局唯一的Content，不会产生内存泄露
            sToast = Toast.makeText(context.getApplicationContext(), messageWap(msg), mode);
            sToast.show();
            sLastTime = System.currentTimeMillis();
        } else {
            sCurrentTime = System.currentTimeMillis();
            if (sOldMsg.equals(messageWap(msg))) {
                if (sCurrentTime - sLastTime > mode) {
                    sToast.show();
                }
            } else {
                sOldMsg = messageWap(msg);
                sToast.setDuration(mode);
                sToast.setText(messageWap(msg));
                sToast.show();
            }
        }
        sLastTime = sCurrentTime;
    }

    /**
     * 包装错误信息
     *
     * @param msg
     * @return
     */
    private static String messageWap(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return "";
        }
        String tempMsg = msg.toLowerCase().replace(" ", "");
        if (tempMsg.contains("HTTP500internal".toLowerCase())) {
            return "服务器 500";
        } else if (tempMsg.contains("returnednull")) {
            return "返回数据为空";
        } else {
            return msg;
        }
    }
}