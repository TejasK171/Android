package com.jio.siops_ngo.network.utils;

import android.util.Base64;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


import java.io.Serializable;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesUtil implements Serializable {
    public static byte[] encrypt(byte[] plainData, byte[] key, byte[] iv) {
        byte[] crypted = null;

        try {
          if(key!=null) {
              SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
              Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//              IvParameterSpec ips = new IvParameterSpec(Util.getJioMapping().getBytes());
              IvParameterSpec ips = new IvParameterSpec(iv);
              cipher.init(Cipher.ENCRYPT_MODE, keySpec, ips);

              crypted = cipher.doFinal(plainData);
          }
        } catch (Exception e) {
            Console.printThrowable(e);
        }

        return crypted;
    }

    public static String encryptJson(Object jsonData, byte[] key, byte[] iv) {
        String result = null;

        try {
            if(key!=null) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
                mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                String jsonText = mapper.writeValueAsString(jsonData);
                byte[] cryptedJson = AesUtil.encrypt(jsonText.getBytes(), key, iv);
                if (cryptedJson != null)
                    result = Base64.encodeToString(cryptedJson, Base64.DEFAULT);
            }
        } catch (Exception e) {
            Console.printThrowable(e);
        }

        return result;
    }

    public static byte[] decrypt(byte[] encryptedData, byte[] key, byte[] iv) {
        byte[] data = null;

        try {
            if(key!=null) {
                SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//                IvParameterSpec ips = new IvParameterSpec(Util.getJioMapping().getBytes());
                IvParameterSpec ips = new IvParameterSpec(iv);
                cipher.init(Cipher.DECRYPT_MODE, keySpec, ips);
                if (encryptedData != null) {
                    data = cipher.doFinal(encryptedData);
                }
            }
        } catch (Exception e) {
            Console.printThrowable(e);
        }

        return data;
    }

    public static Object decryptJson(String encryptedJson, byte[] key, byte[] iv) {
        Object result = null;

        try {
            if(key!=null) {
                byte[] cryptedJson = Base64.decode(encryptedJson, Base64.DEFAULT);

                byte[] jsonData = AesUtil.decrypt(cryptedJson, key, iv);

                ObjectMapper mapper = new ObjectMapper();
                result = mapper.readValue(jsonData, Map.class);
            }
        } catch (Exception e) {
            Console.printThrowable(e);
        }

        return result;
    }

    public static String decryptJsonForIPL(String encryptedJson, byte[] key, byte[] iv) {
        String result = null;

        try {
            if(key!=null) {
                byte[] cryptedJson = Base64.decode(encryptedJson, Base64.DEFAULT);

                result = AesUtil.decryptForIPL(cryptedJson, key, iv);
            }

        } catch (Exception e) {
            Console.printThrowable(e);
        }

        return result;
    }

    public static String decryptForIPL(byte[] encryptedData, byte[] key, byte[] iv) {
        byte[] data = null;
        String urlConstant="";

        try {
            if(key!=null) {
                SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                IvParameterSpec ips = new IvParameterSpec(Util.getJioMapping().getBytes());
                cipher.init(Cipher.DECRYPT_MODE, keySpec, ips);
                if (encryptedData != null) {
                    data = cipher.doFinal(encryptedData);
                }
                urlConstant = new String(data, "US-ASCII");
            }
        } catch (Exception e) {
            Console.printThrowable(e);
        }

        return urlConstant;
    }


}
