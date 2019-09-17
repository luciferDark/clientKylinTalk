package com.ll.common.app.Util;

import android.util.Log;

public class Logger {
    private Logger() {

    }

    private static boolean isDebug = true;//是否是测试包
    private static final String TAG = "com.ll";//是否是测试包

    public static void LogI(String msg) {
        Logger.LogI(msg, TAG);
    }

    public static void LogI(String msg, String tag) {
        if (!isDebug) {
            return;
        }

        Log.i(tag, "==>\t" + msg);
    }

    public static void LogD(String msg) {
        Logger.LogD(msg, TAG);
    }

    public static void LogD(String msg, String tag) {
        if (!isDebug) {
            return;
        }

        Log.d(tag, "==>\t" + msg);
    }

    public static void LogW(String msg) {
        Logger.LogW(msg, TAG);
    }

    public static void LogW(String msg, String tag) {
        if (!isDebug) {
            return;
        }

        Log.w(tag, "==>\t" + msg);
    }

    public static void LogE(String msg) {
        Logger.LogE(msg, TAG);
    }

    public static void LogE(String msg, String tag) {
        if (!isDebug) {
            return;
        }

        Log.e(tag, "==>\t" + msg);
    }

    public static void LogV(String msg) {
        Logger.LogV(msg, TAG);
    }

    public static void LogV(String msg, String tag) {
        if (!isDebug) {
            return;
        }

        Log.v(tag, "==>\t" + msg);
    }
}
