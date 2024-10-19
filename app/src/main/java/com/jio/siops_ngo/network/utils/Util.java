package com.jio.siops_ngo.network.utils;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import com.jio.siops_ngo.MyApplication;
import com.jio.siops_ngo.utilities.MyExceptionHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

public class Util {

    private static Calendar calendar;

    private static String transKeyVal = "";
    private static String sessionIdVal = "";
    public static void showAlert(Context context) {
        showAlert(context, "Error During Connection.");
    }



    public static String[] getNextWeek() {
        DateFormat format = new SimpleDateFormat("M-dd");
        String[] days = new String[7];
        for (int i = 0; i < 7; i++) {
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }
        return days;
    }

    public static String[] getPreviousWeek() {
        calendar.add(Calendar.DATE, -14);
        return getNextWeek();
    }

    public static void showAlert(Context context, String msg) {
        if (isNullOrBlank(msg))
            return;
        showAlert(context, "ALERT", msg);
    }

    public static void showAlertSuccess(Context context, String msg) {
        if (isNullOrBlank(msg))
            return;
        showAlert(context, "Success", msg);
    }

    public static String loadJSONFromAsset(String name, Context context) {
        String json = null;
        JSONObject obj = null;

        try {
            AssetManager am = context.getAssets();
            List<String> mapList = Arrays.asList(am.list(""));
            if (mapList != null && mapList.size() > 0 && mapList.contains(name)) {
                if (context != null) {
                    InputStream is = context.getAssets().open(name);
                    if (is != null) {
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        json = new String(buffer, "UTF-8");
                    }
                }
            }
        } catch (Exception ex) {
            MyExceptionHandler.handle(ex);
            return null;
        }
        return json;
    }

    public static String readAssetFile(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets()
                    .open(fileName, Context.MODE_PRIVATE);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }

        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        // Log.d("StartAllAppView","  returnString.toString(): "+returnString.toString());
        return returnString.toString();
    }

    public static void showAlert(Context context, String tittle, String msg) {
        if (isNullOrBlank(msg) || isNullOrBlank(tittle))
            return;
        Builder alertBuilder = new Builder(context);
        alertBuilder.setTitle(tittle);
        alertBuilder.setMessage(msg);
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        alertBuilder.create();
        alertBuilder.show();
    }

    public static boolean isNullOrEmptyList(List<?> list) {
        return list == null || list.size() <= 0;
    }

    public static boolean isNullOrEmptyList(HashMap<?, ?> hashMap) {
        return hashMap == null || hashMap.size() <= 0;
    }

    public static boolean isNullOrZeroNumber(String text) {
        return text == null || text.trim().equalsIgnoreCase("")
                || text.trim().equalsIgnoreCase("null") || Integer.valueOf(text
                .trim()) == 0;
    }

    public static boolean isNullOrBlank(String text) {
        return text == null || text.trim().equalsIgnoreCase("") || text.trim()
                .equalsIgnoreCase("null");
    }

    public static boolean isValidMobileNo(String num) {

        return num.length() <= 10 && num.length() >= 10;

    }

    public static boolean isValidPurchaseYear(String year) {
        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);
        int purchaseYear = Integer.valueOf(year);
        return purchaseYear <= currentYear;

    }




    public static String base64Encode(String token) {
        byte[] encodedBytes = Base64.encode(token.getBytes(), Base64.DEFAULT);
        return new String(encodedBytes, Charset.forName("UTF-8"));
    }

    public static String base64Decode(String token) {
        byte[] decodedBytes = Base64.decode(token.getBytes(), Base64.DEFAULT);
        return new String(decodedBytes, Charset.forName("UTF-8"));
    }

    /**
     * Checks whether the network is available on Android device. If the network
     * signal is very low, it will be evaluated as NOT available. This routine
     * will check both MOBILE and WIFI signal. If both of them are in disable
     * status, <code>false</code> will be return absolutely. </p> Return
     * <code>true</code> if the network is available. Otherwise, return
     * <code>false</code>. </p>
     *
     * @param ctx Context.
     * @return <code>True</code> : the network is available.</br>
     * <code>False</code>: the network is NOT available.
     */
    public static boolean isNetworkAvailable(Context ctx) {
        try {
            ConnectivityManager connMgr = (ConnectivityManager) ctx
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null && info.isConnected()
                    || connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                    .isConnected()) {
                return true;
            }
        } catch (Exception e) {
            MyExceptionHandler.handle(e);
            return false;
        }
        return false;
    }

    public static boolean isDeviceLocationEnabled(Context mContext) {
        int locMode = 0;
        String locProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locMode = Settings.Secure.getInt(mContext.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locProviders = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locProviders);
        }
    }


    public static String getValue(String string) {
        if (Util.isNullOrBlank(string))
            return "";
        return string;
    }




    public static void saveInternalFile(String fileName, String content, Context context) {
        Log.d("saveInternalFile", "------saveInternalFile -- fileName started to write---------" + fileName);
        byte[] key = Arrays.copyOfRange("7K1?o:#@^&*RE;a6gfp:l0Z3*TvLefv4t".getBytes(), 0, 16);
        byte[] iv = Util.getJioMapping().getBytes();
        String encrypted_data = Base64.encodeToString(AesUtil.encrypt(content.getBytes(), key, iv), Base64.DEFAULT);
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(encrypted_data.getBytes());
            fos.close();
        } catch (Exception e) {
            //
            MyExceptionHandler.handle(e);
        }
        Log.d("saveInternalFile", "fileName written--" + fileName);
    }

    public static String getInternalFile(String fileName, Context context) {
        byte[] key = Arrays.copyOfRange("7K1?o:#@^&*RE;a6gfp:l0Z3*TvLefv4t".getBytes(), 0, 16);
        byte[] iv = Util.getJioMapping().getBytes();
        String ret = "";

        try {
            if (context != null && context.getFileStreamPath(fileName).exists()) {
                InputStream inputStream = context.openFileInput(fileName);

                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString = "";
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((receiveString = bufferedReader.readLine()) != null) {
                        stringBuilder.append(receiveString);
                    }

                    inputStream.close();
                    ret = new String((AesUtil.decrypt(Base64.decode(stringBuilder.toString(), Base64.DEFAULT), key, iv)));

                }
            }
        } catch (Exception e) {


        }

        return ret;

    }

    public static String getJioMapping() {
        byte[] data = Base64.decode(MyApplication.Companion.getInstance().getmMyJioKey(), Base64.DEFAULT);
        String text = "";
        try {
            text = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text;
    }

    public static int getResourseId(Context context, String pVariableName, String pResourcename, String pPackageName) throws RuntimeException {
        try {
            return context.getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            throw new RuntimeException("Error getting Resource ID.", e);
        }
    }



    public static String getJioMappingUpi(String key) {
        byte[] data = Base64.decode(key, Base64.DEFAULT);
        String text = "";
        try {
            text = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text;
    }
    /*
     * @param: package name to be found
     * purpose: to detect the old package name installed in the device
     */
    public static boolean isPackageExisted(String targetPackage, Context context) {
        try {
            List<ApplicationInfo> packages;
            PackageManager pm;

            pm = context.getPackageManager();
            packages = pm.getInstalledApplications(0);
            for (ApplicationInfo packageInfo : packages) {
                if (packageInfo.packageName.equals(targetPackage))
                    return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * Return all classes in Arraylist inside that packagename
     *
     * @param ctx         current Context, like Activity, App, or Service
     * @param packageName the full package name of the app to open
     * @return Arraylist
     */
    public static ArrayList<Class<?>> getClassesOfPackage(Context ctx, String packageName) {
        ArrayList<String> classes = new ArrayList<String>();
        ArrayList<Class<?>> final_Classes = new ArrayList<>();
        PathClassLoader classLoader = (PathClassLoader) ctx.getClassLoader();
        try {
            String packageCodePath = ctx.getPackageCodePath();
            DexFile df = new DexFile(packageCodePath);
            for (Enumeration<String> iter = df.entries(); iter.hasMoreElements(); ) {
                String className = iter.nextElement();
                if (className.contains(packageName)) {
                    classes.add(className.substring(className.lastIndexOf(".") + 1, className.length()));
                    try {
                        Class<?> c = classLoader.loadClass(className);
                        final_Classes.add(c);
                    } catch (Exception e) {

                    }

                }
            }
        } catch (Exception e) {

        }

        return final_Classes;
    }

    /**
     * Open another app.
     *
     * @param context     current Context, like Activity, App, or Service
     * @param packageName the full package name of the app to open
     * @return true if likely successful, false if unsuccessful
     */
    public static boolean openApp(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(packageName);
            if (i == null) {
                return false;
                //throw new PackageManager.NameNotFoundException();
            }
            i.setAction("android.intent.action.MAIN");
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void showInMarket(Context ctx, String packageName) {
        try {
            PackageManager packageManager = ctx.getPackageManager();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName + "&referrer=utm_source%3Dmyjio%26utm_medium%3Dapp%26utm_term%3Djpooffer-page-playstore%26utm_content%3D20160820%26utm_campaign%3Djpo"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (intent.resolveActivity(packageManager) != null) {
                ctx.startActivity(intent);
            }
        } catch (Exception e) {

        }
    }

    public static void showInBrowser(Context ctx, String url) {
        try {
            PackageManager packageManager = ctx.getPackageManager();
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                ctx.startActivity(i);
            }
        } catch (Exception e) {

        }
    }

    public static void animateFadeInOut(Context c, final View v, final Object new_data) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        final Animation anim_in = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (v instanceof ImageView)
                    ((ImageView) v).setImageBitmap((Bitmap) new_data);
                else if (v instanceof TextView)
                    ((TextView) v).setText((String) new_data);

                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }
                });
                v.startAnimation(anim_in);
            }
        });
        //anim_out.setDuration(700);
        v.startAnimation(anim_out);
    }

    private static Bitmap imageBitmap;

    public static String getTransKey() {
        return transKeyVal;
    }

    public static void setTransKey(String transKey) {
        transKeyVal = transKey;
    }

    public static String getSessionIdVal() {
        return sessionIdVal;
    }

    public static void setSessionIdVal(String sessionIdVal) {
        Util.sessionIdVal = sessionIdVal;
    }

    static class DownloadImageBitmap extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap image = null;
            try {
                URL url = new URL(urls[0]);
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream());


            } catch (IOException e) {

            }
            return image;
        }

        protected void onPostExecute(Bitmap result) {

            imageBitmap = result;
        }
    }

    public static Drawable getDrawableFromUrl(String urlStr, Context mContext) {
        Drawable d = null;
        try {
            imageBitmap = null;
            try {
                new DownloadImageBitmap().execute(urlStr);
                d = new BitmapDrawable(mContext.getResources(), imageBitmap);
            } catch (Exception e) {

            }
        } catch (Exception e) {

        }
        return d;
    }

    public final static String BitMapToString(Bitmap in) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            in.compress(Bitmap.CompressFormat.PNG, 100, bytes);
            return Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {

            return null;
        }
    }

    public final static Bitmap StringToBitMap(String in) {
        try {
            byte[] bytes = Base64.decode(in, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } catch (Exception e) {

            return null;
        }
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        if (keysItr != null) {
            while (keysItr.hasNext()) {
                String key = keysItr.next();
                Object value = object.get(key);

                if (value instanceof JSONArray) {
                    value = toList((JSONArray) value);
                } else if (value instanceof JSONObject) {
                    value = toMap((JSONObject) value);
                }
                map.put(key, value);
            }
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

}
