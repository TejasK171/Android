package com.jio.siops_ngo.network.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;


public class Console implements Serializable {
    private static final String TAG = "RTSS";
    public static boolean isPrint = true;
    public static boolean isPrintStackTrace = true;
    private Console() {
    }

    /**
     * print message
     *
     * @param msg
     */
    public static void print(String msg) {
        if (isPrint) {
            Log.d(TAG, msg);
        }
    }

    /**
     * print message
     *
     * @param msg
     */
    public static void toastMessage(Context context, String msg) {
        if (isPrint) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * print debug message
     *
     * @param tag
     * @param msg
     */
    public static void debug(String tag, String msg) {
        if (isPrint) {
            Log.d(tag, msg);
        }
    }

    /**
     * print error message
     *
     * @param tag
     * @param msg
     */
    public static void error(String tag, String msg) {
        if (isPrint) {
            Log.e(tag, msg);
        }
    }

    /**
     * print info message
     *
     * @param tag
     * @param msg
     */
    public static void info(String tag, String msg) {
        if (isPrint) {
            Log.i(tag, msg);
        }
    }

    /**
     * print warn message
     *
     * @param tag
     * @param msg
     */
    public static void warn(String tag, String msg) {
        if (isPrint) {
            Log.w(tag, msg);
        }
    }

    /**
     * print verbose message
     *
     * @param tag
     * @param msg
     */
    public static void verbose(String tag, String msg) {
        if (isPrint) {
            Log.d(tag, msg);
        }
    }

    /**
     * printStackTrace exception message
     *
     * @param
     */
    public static void printThrowable(Throwable e) {
        if (isPrintStackTrace) {
            e.printStackTrace();
        }
    }

    public static void debug(String string) {
        if (isPrint) {
            Log.d(TAG, string);
        }
    }

 }
