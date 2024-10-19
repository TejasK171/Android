package com.jio.siops_ngo.network.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetUtil implements Serializable {

    /**
     * no network
     */
    public final static int TYPE_NETWORK_DISABLE = 0;
    /**
     * moblie network
     */
    public final static int TYPE_NETWORK_MOBILE = 1;
    /**
     * wifi network
     */
    public final static int TYPE_NETWORK_WIFI = 2;

    /**
     * enable and disable 3G signal
     *
     * @param context
     * @param enabled
     * @throws ClassNotFoundException
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static void setMobileDataEnabled(Context context, boolean enabled)
            throws ClassNotFoundException, SecurityException,
            NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException, NoSuchMethodException,
            InvocationTargetException {

        final ConnectivityManager conman = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final Class conmanClass = Class.forName(conman.getClass().getName());
        final Field iConnectivityManagerField = conmanClass
                .getDeclaredField("mService");
        iConnectivityManagerField.setAccessible(true);
        final Object iConnectivityManager = iConnectivityManagerField
                .get(conman);
        final Class iConnectivityManagerClass = Class
                .forName(iConnectivityManager.getClass().getName());
        final Method setMobileDataEnabledMethod = iConnectivityManagerClass
                .getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        setMobileDataEnabledMethod.setAccessible(true);

        setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
    }

    public static int getTypeNet(Context context) {
        int type = TYPE_NETWORK_DISABLE;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isAvailable()) {
            type = TYPE_NETWORK_DISABLE;// network disable
        } else if (networkInfo == null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            type = TYPE_NETWORK_MOBILE;
        } else if (networkInfo == null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            type = TYPE_NETWORK_WIFI;
        }
        return type;
    }

    /**
     * <uses-permission
     * android:name="android.permission.INTERNET"></uses-permission>
     * <uses-permission
     * android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
     *
     * @return
     */
    public static String getLocalIPAddress() {
        try {
            for (Enumeration<NetworkInterface> mEnumeration = NetworkInterface
                    .getNetworkInterfaces(); mEnumeration.hasMoreElements(); ) {
                NetworkInterface intf = mEnumeration.nextElement();
                for (Enumeration<InetAddress> enumIPAddr = intf
                        .getInetAddresses(); enumIPAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIPAddr.nextElement();
                    // If it is not the loopback address
                    if (!inetAddress.isLoopbackAddress()) {
                        // Direct return local IP address
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.d("Error", ex.toString());
        }
        return null;
    }

    // obtain local IP function

    /**
     * if open wifi, true: open false:close
     * <p>
     * must add permission： <uses-permission
     * android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
     * <uses-permission
     * android:name="android.permission.CHANGE_WIFI_STATE"></uses-permission>
     *
     * @param isEnable
     */
    public static void setWifi(Context context, boolean isEnable) {

        //
        WifiManager mWm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        if (isEnable) {//  open wifi

            if (!mWm.isWifiEnabled()) {

                mWm.setWifiEnabled(true);

            }
        } else {
            // close wifi
            if (mWm.isWifiEnabled()) {
                mWm.setWifiEnabled(false);
            }
        }

    }

    public void setNet(Context context) {
        Intent mIntent = new Intent("/");
        ComponentName comp = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        mIntent.setComponent(comp);
        mIntent.setAction("android.intent.action.VIEW");
        context.startActivity(mIntent); // If you need to operate again after setup is complete , you can override the operation code , not rewrite it here
    }
}
