package com.karlchu.medicalorder.core.utils;

import android.util.Log;

import com.karlchu.medicalorder.core.A;


/**
 * Created by hieu on 4/18/2016.
 */
public class ELog {
    public static int d(final String TAG, Throwable t) {
        return Log.wtf(TAG, t);
    }

    public static int d(final String TAG, String msg) {
        return Log.d(TAG, msg);
    }

    public static int d(Throwable t) {
        return Log.wtf(A.name(), t);
    }

    public static int d(String msg) {
        return Log.d(A.name(), msg);
    }

    public static int i(String msg) {
        return Log.i(A.name(), msg);
    }

    public static int i(String tag, String msg) {
        return Log.i(tag, msg);
    }

    public static int w(final String TAG, String msg) {
        return Log.w(TAG, msg);
    }

    public static int w(final String TAG, String msg, Throwable e) {
        return Log.e(TAG, msg, e);
    }

    public static int w(String msg) {
        return Log.w(A.name(), msg);
    }

    public static int e(final String TAG, String msg, Throwable e) {
        return Log.e(TAG, msg, e);
    }

    public static int e(String msg, Throwable e) {
        return Log.e(A.name(), msg, e);
    }

    public static int e(String msg) {
        return Log.e(A.name(), msg);
    }
}
