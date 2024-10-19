package com.jio.siops_ngo.network.utils;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import com.jio.siops_ngo.MyApplication;
import com.jio.siops_ngo.utilities.DeviceSoftwareInfo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class Tools implements Serializable {
    //	private final int PERMISSION_TELEPHONY_MANAGER = 100;
    Context applicationContext = MyApplication.Companion.getInstance().getApplicationContext();

    /*
     * @param a
     * @param num
     * @return
     */
    public static double getDoubleFormate(double a, int num) {
        double c = Math.pow(10, num);
        double b = (Math.round(a * c)) / (int) c;//
        return b;
    }

    /**
     * Description:
     *
     * @return String
     */



    /**
     * Description:Function to return converted data value without attatching its UNIT
     *
     * @return Double
     */


    public static boolean deleteWithProject(Context context, String fileName) {
        if (null != context) {
            return context.deleteFile(fileName);
        }
        return false;
    }

    /**
     * @param mContext
     * @param fileName
     * @param data
     */
    public static void writeData2Project(Context mContext, String fileName, byte[] data) {
        try {
            if (mContext != null) {
                File file = mContext.getFileStreamPath(fileName);
                if(file.exists()) {
                    FileOutputStream outStream = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
                    outStream.write(data);
                    outStream.flush();
                    outStream.close();
                    outStream = null;
                }
            }
        } catch (FileNotFoundException e) {
            Console.printThrowable(e);
        } catch (IOException e) {


            Console.printThrowable(e);
        }
    }

    /**
     * @param mContext
     * @param fileName
     * @return
     */
    public static byte[] readData2Project(Context mContext, String fileName) {
        byte[] buffer = null;
        try {
            if (mContext != null && fileName != null) {
                File file = mContext.getFileStreamPath(fileName);
                if(file.exists()) {
                    Log.d("Tools", "readData2Project() called with: " + "mContext = [" + mContext + "], fileName = [" + fileName + "]"+" exists : "+file.exists());
                    FileInputStream inStream = mContext.openFileInput(fileName);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    if (inStream != null) {
                        buffer = new byte[1024];
                        int length = -1;
                        while ((length = inStream.read(buffer)) != -1) {
                            stream.write(buffer, 0, length);
                        }
                        stream.close();
                        inStream.close();
                    }
                    return stream.toByteArray();
                }

            }

        } catch (FileNotFoundException e) {
            Console.printThrowable(e);
        }catch (Exception e) {
            Console.printThrowable(e);
        }
        return buffer;
    }

    public static void writeObject(Context context, String key, Object object) {
        try {
            if (context != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024 * 1024);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(byteArrayOutputStream));
                objectOutputStream.writeObject(object);
                objectOutputStream.flush();
                byte[] buffer = byteArrayOutputStream.toByteArray();
                byte[] bufferEncrypted = AesUtil.encrypt(buffer, Util.getJioMapping().getBytes(), null);

                objectOutputStream.close();

                Tools.deleteWithProject(context, key);
                Tools.writeData2Project(context, key, bufferEncrypted);
            }
        } catch (Exception e) {
            Console.printThrowable(e);

        }
    }

    public static Object readObject(Context context, String key) {
        Object object = null;
        try {
            if (context != null) {

                byte[] buffer = Tools.readData2Project(context, key);
                byte[] bufferDecrepted = AesUtil.decrypt(buffer, Util.getJioMapping().getBytes(), null);

                if (bufferDecrepted != null) {
                    ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(new ByteArrayInputStream(bufferDecrepted)));
                    object = objectInputStream.readObject();
                    objectInputStream.close();
                }
            }

        } catch (FileNotFoundException e) {
            Console.printThrowable(e);
        }catch (Exception e) {
            Console.printThrowable(e);
        }

        return object;
    }

    /**
     * @param s
     * @return
     */
    public static boolean checkPassWord(String s) {
        if (TextUtils.isEmpty(s)) {
            return false;
        }
        return s.matches("^(?![a-zA-Z0-9]+$)(?![^a-zA-Z/D]+$)(?![^0-9/D]+$).{8,100}$");
    }

    /**
     * @param activity
     */
    public static void closeSoftKeyboard(Activity activity) {
        try {
            if (activity != null) {
                View view = activity.getWindow().peekDecorView();
                if (view != null) {
                    InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        } catch (Exception e) {
            //MyExceptionHandler.handle(e);
        }
    }



    /**
     * @param newUserID
     * @return
     */
    public static boolean isValidFormater(String newUserID) {
        boolean isAalid = false;
        isAalid = newUserID.matches("(?![^a-zA-Z]+$)(?!\\D+$).+");
        return isAalid;
    }

    /**
     * @return
     */
    public static HashMap<String, String> getDeviceInfo() {
        Context applicationContext = MyApplication.Companion.getInstance().getApplicationContext();
        TelephonyManager tm = (TelephonyManager) applicationContext.getSystemService(Context.TELEPHONY_SERVICE);
        DeviceSoftwareInfo sm = new DeviceSoftwareInfo();


        HashMap<String, String> devicesInfo = new HashMap<String, String>();
        String iMsi = "";
        try {
           // iMsi = tm.getSubscriberId();
        } catch (SecurityException e) {
            iMsi = "";
        } catch (Exception e) {
            iMsi = "";
        }

        String deviceSoftwareVersion = Build.VERSION.RELEASE;
        devicesInfo.put("platform", "android");
        devicesInfo.put("version", deviceSoftwareVersion);
        devicesInfo.put("imsi", iMsi);
        devicesInfo.put("mac", getMacAddr(applicationContext));
        devicesInfo.put("consumptionDeviceName", "Mytablet");
        //	devicesInfo.put("bluetoothAddress", getIPAddress(true));
        devicesInfo.put("bluetoothAddress", "");
        devicesInfo.put("device", getDeviceId(applicationContext));
        devicesInfo.put("imei1", getImei(applicationContext, 0));
        List<SubscriptionInfo> subInfoList = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                SubscriptionManager subscriptionManager = SubscriptionManager.from(applicationContext);
               /* if (ViewUtils.hasReadPhoneStatePermissions(applicationContext)) {
                    subInfoList = subscriptionManager.getActiveSubscriptionInfoList();
                }*/
            }
            if (subInfoList != null && subInfoList.size()>1){
                devicesInfo.put("imei2", getImei(applicationContext, 1));
            }
            else{
                devicesInfo.put("imei2", "");
            }
        } catch (Exception e) {
            devicesInfo.put("imei2", "");
           // MyExceptionHandler.handle(e);
        }
        devicesInfo.put("xandroidId", sm.getAndroidID(applicationContext));


        return devicesInfo;
    }

    public static String getImei(Context appContext, int index) {
        TelephonyManager TelephonyMgr = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
        String m_deviceId = "";
        try {
            if (TelephonyMgr != null) {
                try {
                   /* if (ViewUtils.hasReadPhoneStatePermissions(appContext)) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            m_deviceId = TelephonyMgr.getImei(index);
                        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            m_deviceId = TelephonyMgr.getDeviceId(index);
                        }else if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP){
                            m_deviceId = TelephonyMgr.getDeviceId();
                        }
                    }*/

                } catch (SecurityException e) {
                    m_deviceId = "";
                } catch (Exception e) {
                    m_deviceId = "";
                }

            }
        }catch (Exception e){
           // MyExceptionHandler.handle(e);
        }
        Log.d("Tools", "getImei() m_deviceId = [" + m_deviceId + "], index = [" + index + "]");
        return m_deviceId;
    }


    public static HashMap<String, String> getDeviceInfoHanshake() {
        String imeiPipeseperated = "";

        Context applicationContext = MyApplication.Companion.getInstance().getApplicationContext();
        TelephonyManager tm = (TelephonyManager) applicationContext.getSystemService(Context.TELEPHONY_SERVICE);
        DeviceSoftwareInfo sm = new DeviceSoftwareInfo();

        HashMap<String, String> devicesInfo = new HashMap<String, String>();
        String iMsi = "";
        try {
           // iMsi = tm.getSubscriberId();
        } catch (SecurityException e) {
            iMsi = "";
        }catch (Exception e) {
            iMsi = "";
        }

        String deviceSoftwareVersion = Build.VERSION.RELEASE;
        devicesInfo.put("platform", "android");
        devicesInfo.put("version", deviceSoftwareVersion);
        devicesInfo.put("imsi", iMsi);
        try {

            {
                /*DeviceInfoBean deviceInfoBean = Tools.getDeviceInFoBean(applicationContext);

                if (deviceInfoBean != null && deviceInfoBean.getIMEINo_Array() != null && deviceInfoBean.getIMEINo_Array().size() > 0) {
                    imeiPipeseperated = "";
                    for (int i = 0; i < deviceInfoBean.getIMEINo_Array().size(); i++) {
                        if (!TextUtils.isEmpty(imeiPipeseperated)) {
                            imeiPipeseperated = imeiPipeseperated + "|" + deviceInfoBean.getIMEINo_Array().get(i);
                        } else {
                            imeiPipeseperated = deviceInfoBean.getIMEINo_Array().get(i);
                        }
                    }
                }*/
            }
            devicesInfo.put("imei", imeiPipeseperated);
        } catch (Exception infoHanshake) {
            devicesInfo.put("imei", "");

        }


        devicesInfo.put("mac", getMacAddr(applicationContext));
        devicesInfo.put("device", getDeviceId(applicationContext));

        try {
            devicesInfo.put("type", (getPhoneType(applicationContext)));
            devicesInfo.put("model", (Build.MODEL));
            devicesInfo.put("manufacturer", (Build.BRAND));
            devicesInfo.put("product", (Build.PRODUCT));
            devicesInfo.put("cpuAbi", (Build.HARDWARE));
            devicesInfo.put("serial", (Build.SERIAL));
            devicesInfo.put("host", getIPAddress(true));
            devicesInfo.put("xandroidId", sm.getAndroidID(applicationContext));

        } catch (Exception deviceInfoHanshake) {
            devicesInfo.put("host", "");

        }


        return devicesInfo;
    }

    public static String getMacAddr(Context appContext) {
        String m_macAddr = null;
        try {
            Context applicationContext = MyApplication.Companion.getInstance().getApplicationContext();
            WifiManager manager = (WifiManager) applicationContext.getSystemService(Context.WIFI_SERVICE);
            m_macAddr = "";
            if (manager != null) {
               /* WifiInfo info = manager.getConnectionInfo();
                if (info != null) {
                    m_macAddr = info.getMacAddress();
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
           // MyExceptionHandler.handle(e);
        }

        return m_macAddr;
    }

    public static String getDeviceId(Context appContext) {
        TelephonyManager TelephonyMgr = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
        String m_deviceId = "";
        if (TelephonyMgr != null) {
            try {
                /*if(ViewUtils.hasReadPhoneStatePermissions(appContext)){
                m_deviceId = TelephonyMgr.getDeviceId();
                }*/

            } catch (SecurityException e) {
                m_deviceId = "";
            }catch (Exception e) {
                m_deviceId = "";
            }

        }
        return m_deviceId;
    }

    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
           // MyExceptionHandler.handle(e);

        }
        return "";
    }



    public static String getStringByMillisecond(final String strMillisecond, String style) {
        Date date = new Date(Long.valueOf(strMillisecond));
        SimpleDateFormat format = new SimpleDateFormat(style,Locale.US);
        String time = format.format(date);
        return time;
    }

    /**
     * @param decimal
     * @return
     */
    public static String getStringDecimalFormat(double decimal) {
        String string = new DecimalFormat("0.00").format(decimal);
        String result;
        if (decimal == 0 && string.startsWith("-")) {
            result = string.substring(1, string.length());
        } else {
            result = string;
        }
        return result;
    }

    public static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat dataFormat = new SimpleDateFormat(format, Locale.US);
        String time = dataFormat.format(date);
        return time;
    }

    public static String getStream2String(InputStream inputStream) {
        StringBuilder total = new StringBuilder();
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
           // MyExceptionHandler.handle(e);
            Log.d("ABC", "" + e.getMessage());
        }

        return total.toString();

    }



    public static double round(double value, int numberOfDigitsAfterDecimalPoint) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(numberOfDigitsAfterDecimalPoint,
                BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }

    public static String getTypeOfNetworkConnected(Context appContext) {

        // Get the type of network you are connected with
        Context applicationContext = MyApplication.Companion.getInstance().getApplicationContext();
        TelephonyManager tm = (TelephonyManager) applicationContext.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = tm.getNetworkType();
        String typeOfNetwork = "";
        switch (networkType) {
            case (TelephonyManager.NETWORK_TYPE_GPRS):
                typeOfNetwork = "GPRS";
                break;
            case (TelephonyManager.NETWORK_TYPE_CDMA):
                typeOfNetwork = "CDMA";
                break;
            case (TelephonyManager.NETWORK_TYPE_EDGE):
                typeOfNetwork = "EDGE";
                break;
            case (TelephonyManager.NETWORK_TYPE_EVDO_0):
                typeOfNetwork = "LTE";
                break;
            case (TelephonyManager.NETWORK_TYPE_LTE):
                typeOfNetwork = "LTE";
                break;
            default:
                typeOfNetwork = "None";
                break;
        }
        return typeOfNetwork;
    }

    public static String getPhoneType(Context appContext) {
        //Get the type of network you are connected with
        Context applicationContext = MyApplication.Companion.getInstance().getApplicationContext();
        TelephonyManager tm = (TelephonyManager) applicationContext.getSystemService(Context.TELEPHONY_SERVICE);
        int phoneType = tm.getPhoneType();
        String phoneTypeString = "";

        switch (phoneType) {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                // your code
                phoneTypeString = "CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                // your code
                phoneTypeString = "GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                phoneTypeString = "";
                break;

        }
        return phoneTypeString;
    }

//    public static DeviceInfoBean getDeviceInFoBean(Context context) {
//        DeviceInfoBean deviceInfoBean = null;
//        try {
//            setDeviceInfoBean(context);
//            String deviceInfoBeanString;
//            SharedPreferences DeviceInfosharedPreferences = context.getSharedPreferences("DeviceInfo", Context.MODE_PRIVATE);
//            Gson gson = new Gson();
//            deviceInfoBeanString = DeviceInfosharedPreferences.getString("DeviceInfoBean", "");
//            if (deviceInfoBeanString.equalsIgnoreCase("")) {
//                deviceInfoBeanString = DeviceInfosharedPreferences.getString("DeviceInfoBean", "");
//            }
//            deviceInfoBean = gson.fromJson(deviceInfoBeanString, DeviceInfoBean.class);
//
//        } catch (Exception e) {
//            Console.printThrowable(e);
//            Log.d("DeviceInfoBean", e.toString());
//        }
//
//        return deviceInfoBean;
//    }
//
//    public static void setDeviceInfoBean(Context context) {
//
//        try {
//            SharedPreferences DeviceInfosharedPreferences = context.getSharedPreferences("DeviceInfo", Context.MODE_PRIVATE);
//            SharedPreferences.Editor prefsEditor = DeviceInfosharedPreferences.edit();
//            Gson gson = new Gson();
//            DeviceInfoBean deviceInfoBean = new DeviceInfoBean();
//            ArrayList<String> ImeiArray = new ArrayList<String>();
//            ArrayList<String> ImsiArray = new ArrayList<String>();
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if(ViewUtils.hasReadPhoneStatePermissions(context)) {
//                    TelephonyManager Telephony23 = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
//                    String imsiSIM1 = Telephony23.getDeviceId(0);
//                    String imsiSIM2 = Telephony23.getDeviceId(1);
//
//                    if (imsiSIM1 != null && !imsiSIM1.trim().equalsIgnoreCase("")) {
//                        ImsiArray.add(imsiSIM1);
//                        ImeiArray.add(imsiSIM1);
//                    }
//                    if (imsiSIM2 != null && !imsiSIM2.trim().equalsIgnoreCase("")) {
//                        ImsiArray.add(imsiSIM2);
//                        ImeiArray.add(imsiSIM2);
//                    }
//                    Log.d("setDeviceInfoBean", "setDeviceInfoBean ImeiArray.size " + "context = [" + ImeiArray.size() + "" + imsiSIM1 + "|" + imsiSIM2 + "]" + ImeiArray);
//                }
//            } else {
//                TelephonyInfo telephonyInfo = TelephonyInfo.getInstance(context);
//                String imsiSIM1 = telephonyInfo.getImsiSIM1();
//                String imsiSIM2 = telephonyInfo.getImsiSIM2();
//                if (imsiSIM1 != null && !imsiSIM1.trim().equalsIgnoreCase("")) {
//                    ImsiArray.add(imsiSIM1);
//                    ImeiArray.add(imsiSIM1);
//                }
//                if (imsiSIM2 != null && !imsiSIM2.trim().equalsIgnoreCase("")) {
//                    ImsiArray.add(imsiSIM2);
//                    ImeiArray.add(imsiSIM2);
//                }
//            }
//
//            deviceInfoBean.setIMSI_Array(ImsiArray);
//            deviceInfoBean.setIMEINo_Array(ImeiArray);
//            String deviceSoftwareVersion = Build.VERSION.RELEASE;
//            deviceInfoBean.setMAC_Address(getMacAddr(context));
//            deviceInfoBean.setDeviceId(getDeviceId(context));
//            deviceInfoBean.setPhoneType(getPhoneType(context));
//            deviceInfoBean.setManufacturer(Build.MANUFACTURER);
//            deviceInfoBean.setModel(Build.MODEL);
//            deviceInfoBean.setBrand(Build.BRAND);
//            deviceInfoBean.setProduct(Build.PRODUCT);
//            deviceInfoBean.setHardware(Build.HARDWARE);
//            deviceInfoBean.setSerial_Number(Build.SERIAL);
//
//            //	devicesInfo.put("bluetoothAddress", getIPAddress(true));
//
//            String json = gson.toJson(deviceInfoBean); // myObject - instance of MyObject
//            prefsEditor.putString("DeviceInfoBean", json);
//            prefsEditor.commit();
//        } catch (Exception e) {
//            Console.printThrowable(e);
//        }
//
//
//    }

/*
    public static DeviceInfoBean getDeviceInFoBean(Context context) {
        DeviceInfoBean deviceInfoBean=null;
        try {
            // SharedPreferences DeviceInfosharedPreferences = context.getSharedPreferences("DeviceInfo", Context.MODE_PRIVATE);
            // SharedPreferences.Editor prefsEditor = DeviceInfosharedPreferences.edit();
            //  Gson gson = new Gson();
            if(RtssApplication.deviceInfoBean==null ||(ViewUtils.hasReadPhoneStatePermissions(context) && (RtssApplication.deviceInfoBean.getIMEINo_Array()==null || RtssApplication.deviceInfoBean.getIMEINo_Array().size()==0))) {
                deviceInfoBean = new DeviceInfoBean();
                ArrayList<String> ImeiArray = new ArrayList<String>();
                ArrayList<String> ImsiArray = new ArrayList<String>();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ViewUtils.hasReadPhoneStatePermissions(context)) {
                        TelephonyManager Telephony23 = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                        String imsiSIM1 = "";
                        String imsiSIM2 = "";
                        try {
                             imsiSIM1 = Telephony23.getDeviceId(0);
                             imsiSIM2 = Telephony23.getDeviceId(1);

                        }catch(SecurityException e)
                        {
                            MyExceptionHandler.handle(e);
                        }catch(Exception e)
                        {
                            MyExceptionHandler.handle(e);
                        }

                        if (imsiSIM1 != null && !imsiSIM1.trim().equalsIgnoreCase("")) {
                            ImsiArray.add(imsiSIM1);
                            ImeiArray.add(imsiSIM1);
                        }
                        if (imsiSIM2 != null && !imsiSIM2.trim().equalsIgnoreCase("")) {
                            ImsiArray.add(imsiSIM2);
                            ImeiArray.add(imsiSIM2);
                        }
                        //Log.d("setDeviceInfoBean", "setDeviceInfoBean ImeiArray.size " + "context = [" + ImeiArray.size() + "" + imsiSIM1 + "|" + imsiSIM2 + "]" + ImeiArray);
                    }
                } else {
                    TelephonyInfo telephonyInfo = TelephonyInfo.getInstance(context);
                    String imsiSIM1 = telephonyInfo.getImsiSIM1();
                    String imsiSIM2 = telephonyInfo.getImsiSIM2();
                    if (imsiSIM1 != null && !imsiSIM1.trim().equalsIgnoreCase("")) {
                        ImsiArray.add(imsiSIM1);
                        ImeiArray.add(imsiSIM1);
                    }
                    if (imsiSIM2 != null && !imsiSIM2.trim().equalsIgnoreCase("")) {
                        ImsiArray.add(imsiSIM2);
                        ImeiArray.add(imsiSIM2);
                    }
                }

                deviceInfoBean.setIMSI_Array(ImsiArray);
                deviceInfoBean.setIMEINo_Array(ImeiArray);
                String deviceSoftwareVersion = Build.VERSION.RELEASE;
                deviceInfoBean.setMAC_Address(getMacAddr(context));
                deviceInfoBean.setDeviceId(getDeviceId(context));
                deviceInfoBean.setPhoneType(getPhoneType(context));
                deviceInfoBean.setManufacturer(Build.MANUFACTURER);
                deviceInfoBean.setModel(Build.MODEL);
                deviceInfoBean.setBrand(Build.BRAND);
                deviceInfoBean.setProduct(Build.PRODUCT);
                deviceInfoBean.setHardware(Build.HARDWARE);
                deviceInfoBean.setSerial_Number(Build.SERIAL);


                MyApplication.deviceInfoBean = deviceInfoBean;
            }
            //	devicesInfo.put("bluetoothAddress", getIPAddress(true));

            //  String json = gson.toJson(deviceInfoBean); // myObject - instance of MyObject
            //  prefsEditor.putString("DeviceInfoBean", json);
            // prefsEditor.commit();
        } catch (Exception e) {
            Console.printThrowable(e);
        }


        return MyApplication.deviceInfoBean;
    }

 */


    long getCeilValue(Double value) {
        if (value.floatValue() == 0) {
            return value.intValue();
        } else {
            return value.intValue() + 1;
        }


    }
//	private void showPermissionPoup() {
//		if (ContextCompat.checkSelfPermission(applicationContext,
//				Manifest.permission.READ_PHONE_STATE)
//
//				!= PackageManager.PERMISSION_GRANTED) {
//
//			ActivityCompat.requestPermissions((Activity) applicationContext, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_TELEPHONY_MANAGER);
//		}
//
//
//	}


}
