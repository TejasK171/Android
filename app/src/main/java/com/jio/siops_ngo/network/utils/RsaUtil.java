package com.jio.siops_ngo.network.utils;

import android.util.Base64;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RsaUtil implements Serializable {

    //generate RSAkeypair and return a commonBeanMap
    public static Map<String, Key> generateRSAKeyPair() {
        Map<String, Key> map = new HashMap<String, Key>();
        KeyPairGenerator rsaKeyGen = null;
        KeyPair rsaKeyPair = null;
        try {
            rsaKeyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = new SecureRandom();
            random.nextBytes(new byte[1]);
            rsaKeyGen.initialize(1024, random);
            rsaKeyPair = rsaKeyGen.genKeyPair();
            Key publicKey = rsaKeyPair.getPublic();
            Key privateKey = rsaKeyPair.getPrivate();
            map.put("PUBLIC_KEY", publicKey);
            map.put("PRIVATE_KEY", privateKey);

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }

        return map;
    }

    //publickey export to base64
    public static String wrapPublicKey(Key publicKey) {
        String base64EncodedPublicKey = null;
        byte[] keyBytes = publicKey.getEncoded();
        base64EncodedPublicKey = Base64.encodeToString(keyBytes, Base64.DEFAULT);
        return base64EncodedPublicKey;

    }

    public static byte[] encrypt(byte[] plainData, Key key) {
        byte[] encryptedData = null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("RSA/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encryptedData = cipher.doFinal(plainData);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return encryptedData;
    }

    public static byte[] decrypt(byte[] data, Key key) {
        byte[] plainData = null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            plainData = cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return plainData;
    }
}
