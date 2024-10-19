package com.jio.siops_ngo.utilities;


import android.content.Context;
import android.content.SharedPreferences;
import android.security.keystore.KeyGenParameterSpec;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;


public class PreferenceUtility {

    private static String IPl_SharedPref = "jioInfra";
    private static KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
    //    private static String masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);
    private static SharedPreferences sharedPreferences;


    public static SharedPreferences createSharedPrefs(Context con){
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);
            sharedPreferences = EncryptedSharedPreferences.create(IPl_SharedPref, masterKeyAlias, con,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);

        } catch (Exception e) {
            MyExceptionHandler.handle(e);
        }
        return sharedPreferences;

    }


    public static void addString(Context con, String key, String value) {
        try {

            if (con != null && sharedPreferences!=null) {
                /*SharedPreferences sp = con.getSharedPreferences(IPl_SharedPref, Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putString(key, value);
                ed.commit();*/


                sharedPreferences.edit()
                        .putString(key, value)
                        .apply();
            }




        } catch (Exception e) {
            MyExceptionHandler.handle(e);
        }
    }

    public static void addBoolean(Context con, String key, boolean value) {
        try {
            if (con != null && sharedPreferences!=null) {
                /*SharedPreferences sp = con.getSharedPreferences(IPl_SharedPref, Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putBoolean(key, value);
                ed.commit();*/


                sharedPreferences.edit()
                        .putBoolean(key, value)
                        .apply();
            }
        } catch (Exception e) {
            MyExceptionHandler.handle(e);
        }
    }

    public static String getString(Context con, String key, String defaultValue) {
        String result = defaultValue;
        if (con != null && sharedPreferences!=null) {
            /*SharedPreferences sp = con.getSharedPreferences(IPl_SharedPref, Context.MODE_PRIVATE);
            result = sp.getString(key, defaultValue);*/

            result = sharedPreferences.getString(key, "");
        }
        return result;
    }

    public static boolean getBoolean(Context con, String key, boolean defaultValue) {
        boolean result = defaultValue;
        if (con != null) {
            /*SharedPreferences sp = con.getSharedPreferences(IPl_SharedPref, Context.MODE_PRIVATE);
            result = sp.getBoolean(key, defaultValue);*/

            result = sharedPreferences.getBoolean(key, defaultValue);
        }
        return result;
    }

    public static void setIplEnableFlag(Context con, String key, String value) {
        try {
            if (con != null && sharedPreferences!=null) {
                SharedPreferences sp = con.getSharedPreferences(IPl_SharedPref, Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putString(key, value);
                ed.commit();
            }
        } catch (Exception e) {
            MyExceptionHandler.handle(e);
        }
    }
    public static String getIplEnableFlag(Context con, String key, String defaultValue) {
        String result = defaultValue;
        if (con != null && sharedPreferences!=null) {
            SharedPreferences sp = con.getSharedPreferences(IPl_SharedPref, Context.MODE_PRIVATE);
            result = sp.getString(key, defaultValue);
        }
        return result;
    }

    public static void deletePre(Context con) {
        /*SharedPreferences settings = con.getSharedPreferences(IPl_SharedPref, Context.MODE_PRIVATE);
        settings.edit().clear().commit();*/

        sharedPreferences.edit().clear().commit();
    }

}
