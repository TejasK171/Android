package com.jio.siops_ngo.network.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jio.jioinfra.utilities.ViewUtils;
import com.jio.siops_ngo.ApplicationDefine;
import com.jio.siops_ngo.MyApplication;
import com.jio.siops_ngo.network.MappActor;
import com.jio.siops_ngo.network.Session;
import com.jio.siops_ngo.network.utils.AesUtil;
import com.jio.siops_ngo.network.utils.Console;
import com.jio.siops_ngo.network.utils.RsaUtil;
import com.jio.siops_ngo.network.utils.Tools;
import com.jio.siops_ngo.network.utils.Util;
import com.jio.siops_ngo.utilities.MyExceptionHandler;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.security.Key;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MappClient implements Serializable {

    private static final String MAPP_WEB_SERVICE = "/MWS/servlet/Service";
    private static final String MAPP_APPLICATION_SERVICE = "/MAS/servlet/Service";
    private static final String MAS_UPLOAD_SERVICE_SPEC = "/MAS/servlet/Upload";
    public static final String MAPP_SERVICE_SPEC = "/" + ApplicationDefine.Companion.getCONTEXT_PATH() + "/servlet/Service";
    private static final String MAPP_UPLOAD_SERVICE_SPEC = "/" + ApplicationDefine.Companion.getCONTEXT_PATH() + "/servlet/Upload";
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final boolean SUPPORT_MULTI_THREADED = false;
    public static final int CONNECTION_TIMEOUT = 15 * 1000;
    public static final int SOCKET_TIMEOUT = 60 * 1000;
    private static final String CONTENT_TYPE = "application/json";
    private static MappClient instance;

    private static long transactionId;
    private static String payloadLast;
    private static String urlLast;
    private static Map<String, String> headersLast;
    private static String SPName_Overlay = "overlay";
    public String serverAddress;
    public String serviceUrl;
    public String uploadServiceUrl;
    public boolean dispatcher;
    private long requestTime;
    private boolean isSessionOK;
    private Map<String, Key> keys;
    public byte[] transportKey;
    private ObjectMapper mapper = new ObjectMapper();
    public String sessionId;
    private HttpsURLConnection urlConnection = null;
    private String TAG = "MappClient";
    private OkHttpClient okHttpClient;

    private MappClient() {
        try {
            transactionId = System.currentTimeMillis();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        } catch (Exception e) {
            Console.printThrowable(e);
        }
    }


    synchronized public static MappClient getMappClient() {
        if (null == instance) {
            instance = new MappClient();
        }

        return instance;
    }

    synchronized public static String generateTransactionId() {
        return String.format(Locale.US, "%016d", transactionId++);
    }

    public static void addString(Context con, String key, String value) {
        if (con != null) {
            SharedPreferences sp = con.getSharedPreferences(SPName_Overlay, Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sp.edit();
            ed.putString(key, value);
            ed.commit();
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public String getServiceUrl() {
        return String.format(Locale.US, "%s%s", serverAddress, MappClient.MAPP_SERVICE_SPEC);
    }

    public String getUploadServiceUrl() {
        return String.format(Locale.US, "%s%s", serverAddress, MappClient.MAPP_UPLOAD_SERVICE_SPEC);
    }

    public void prepare(String serverAddress, boolean dispatcher, MappActor.IMappActor callback) {
        int status = 0;
        Map<String, Object> responseEntity = new HashMap<String, Object>();
        try {
            this.serverAddress = String.format(Locale.US, "%s%s", serverAddress, MappClient.MAPP_SERVICE_SPEC);
            ;
            this.serviceUrl = String.format(Locale.US, "%s%s", serverAddress, MappClient.MAPP_SERVICE_SPEC);
            this.uploadServiceUrl = String.format(Locale.US, "%s%s", serverAddress, MappClient.MAPP_UPLOAD_SERVICE_SPEC);
            this.dispatcher = dispatcher;

            this.keys = RsaUtil.generateRSAKeyPair();


        } catch (Exception e) {
            Console.printThrowable(e);
        } finally {
            if (null != callback) {
                callback.onExecuted(status, responseEntity);
            }
        }
    }

    public void prepare(String serverAddress, boolean dispatcher) {
        int status = 0;
        Map<String, Object> responseEntity = new HashMap<String, Object>();
        try {
            this.serverAddress = String.format(Locale.US, "%s%s", serverAddress, MappClient.MAPP_SERVICE_SPEC);
            this.serviceUrl = String.format(Locale.US, "%s%s", serverAddress, MappClient.MAPP_SERVICE_SPEC);
            this.uploadServiceUrl = String.format(Locale.US, "%s%s", serverAddress, MappClient.MAPP_UPLOAD_SERVICE_SPEC);
            this.dispatcher = dispatcher;

            this.keys = RsaUtil.generateRSAKeyPair();
        } catch (Exception e) {
            Console.printThrowable(e);
        }
    }

    public int callMapp(final String busiCode, final Map<String, Object> requestEntity, final Map<String, Object> responseEntity) {
        int status = 0;
        isSessionOK = true;

        final Map<String, Object> requestEntityAct = requestEntity;
        final Map<String, Object> responseEntityAct = responseEntity;

        try {
            // Console.debug(String.format("MappClient::callMapp:requestMessage=%s", requestEntity));
            String requestMessage = buildMessage(requestEntity);
            String url = dispatcher == true ? getUrl(busiCode) : serverAddress;
            Console.debug(String.format(Locale.US, "MappClient::callMapp:url=%s", url));

            Map<String, String> header = new HashMap<String, String>();
            header.put("Content-Type", CONTENT_TYPE);

            String responseMessage = true == SUPPORT_MULTI_THREADED ? asyncSend(requestMessage, url, header) : syncSend(requestMessage,
                    url, header);
            if (responseMessage != null) {
                parseMessage(responseMessage, responseEntity);
            } else {
                status = -1;
            }
        } catch (IOException e) {
            Console.printThrowable(e);
            status = -2;
        } catch (Exception e) {
            Console.printThrowable(e);
            status = -1;
        }
        return status;
    }

    public Map<String, Object> userlogin(final String userId, final String password, final boolean rememberMe) {

        Map<String, Object> busiParams = new HashMap<String, Object>();
        if (null != userId && null != password) {
            busiParams.put("userId", userId);
            busiParams.put("password", password);

            if (true == rememberMe) {
                busiParams.put("type", 3);

                Map<String, String> deviceInfo = Tools.getDeviceInfo();
                if (deviceInfo != null) {
                    busiParams.put("deviceInfo", deviceInfo);
                }

            } else {
                busiParams.put("type", 1);
            }
        } else {

            String jToken = "";//Session.getSession().getJToken();
            if (jToken != null && !ViewUtils.Companion.isEmptyString(jToken)) {
                busiParams.put("jToken", jToken);
            }

            busiParams.put("type", 2);

            Map<String, String> deviceInfo = Tools.getDeviceInfo();
            if (deviceInfo != null) {
                busiParams.put("deviceInfo", deviceInfo);
            }
        }

        final String transactionId = MappClient.generateTransactionId();

        String busiCode = "Login";
        Map<String, Object> requestEntity = new HashMap<String, Object>();
        requestEntity.put("busiParams", busiParams);
        requestEntity.put("busiCode", busiCode);
        requestEntity.put("transactionId", transactionId);
        requestEntity.put("isEncrypt", MappActor.ENCRYPTION_ENABLED);

        return requestEntity;

    }

    public int callMapp(final List<Map<String, Object>> requestEntities, final Map<String, Object> responseEntities) {
        int status = 0;
        isSessionOK = true;

        try {
            String requestMessage = buildMessage(requestEntities);
            Console.debug(String.format(Locale.US, "MappClient::callMapp:url=%s", serviceUrl));

            Map<String, String> header = new HashMap<String, String>();
            header.put("Content-Type", CONTENT_TYPE);

            String responseMessage = true == SUPPORT_MULTI_THREADED ? asyncSend(requestMessage, serviceUrl, header) : syncSend(
                    requestMessage, serviceUrl, header);
            if (responseMessage != null) {
                parseMessageForMultipleEntity(responseMessage, responseEntities);

            } else
                status = -1;
        } catch (IOException e) {
            Console.printThrowable(e);
            status = -2;
        } catch (Exception e) {
            Console.printThrowable(e);
            status = -1;
        }

        return status;
    }


    private String getUrl(String busiCode) {
        String url = null;

        try {
            if (true == busiCode.equals("Activation") || true == busiCode.equals("ChangePassword") || true == busiCode.equals("DoTopUp")
                    || true == busiCode.equals("GetAccountBalance") || true == busiCode.equals("GetUserInfo")
                    || true == busiCode.equals("Login") || true == busiCode.equals("Logout") || true == busiCode.equals("QueryOrderStatus")
                    || true == busiCode.equals("QueryProductDetail") || true == busiCode.equals("QueryServiceProductOffer")
                    || true == busiCode.equals("QueryUsage") || true == busiCode.equals("Recharge")
                    || true == busiCode.equals("RequestOTP") || true == busiCode.equals("ResetUserPassword")
                    || true == busiCode.equals("TransferBalance") || true == busiCode.equals("VerifyUserIDUniqueness")
                    || true == busiCode.equals("GetTransactionRefNum") || true == busiCode.equals("SSOLogin")) {

                url = String.format(Locale.US, "%s%s", serverAddress, MappClient.MAPP_WEB_SERVICE);

            } else if (true == busiCode.equals("GenTransferSession") || true == busiCode.equals("JoinTransferSession")
                    || true == busiCode.equals("GetTransKey") || true == busiCode.equals("QueryTransferSessionUsers")
                    || true == busiCode.equals("QueryTransferStatus") || true == busiCode.equals("UpdateTransferStatus")
                    || true == busiCode.equals("UpdateUserPro") || true == busiCode.equals("UploadLog")) {

                url = String.format(Locale.US, "%s%s", serverAddress, MappClient.MAPP_APPLICATION_SERVICE);
            } else if (true == busiCode.equals("Upload")) {
                url = String.format(Locale.US, "%s%s", serverAddress, MappClient.MAS_UPLOAD_SERVICE_SPEC);
            }
        } catch (Exception e) {
            Console.printThrowable(e);
        }

        return url;
    }

    @SuppressLint("NewApi")
    public Map<String, Object> handshake() {
        int status = 0;
        Map<String, Object> responseEntity = new HashMap<String, Object>();
        try {
            RSAPublicKey publicKey = (RSAPublicKey) keys.get("PUBLIC_KEY");

            Map<String, Object> busiParams = new HashMap<String, Object>();
            busiParams.put("type", 0);
            busiParams.put("key", RsaUtil.wrapPublicKey(publicKey));
            if (MappActor.DEVICE_INFO_ENABLE_HANDSHAKE == false) {

                Map<String, String> deviceInfo = Tools.getDeviceInfoHanshake();
                if (deviceInfo != null) {
                    busiParams.put("deviceInfo", deviceInfo);
                }
            }


            String transactionId = MappClient.generateTransactionId();

            String busiCode = "GetTransKey";
            Map<String, Object> requestEntity = new HashMap<String, Object>();
            requestEntity.put("busiParams", busiParams);
            requestEntity.put("busiCode", busiCode);
            requestEntity.put("transactionId", transactionId);
            requestEntity.put("isEncrypt", false);


            status = callMapp(busiCode, requestEntity, responseEntity);
            if (0 == status) {
                String code = (String) responseEntity.get("code");
                if ("0".equals(code)) {
                    Map<String, Object> respMsg = (Map<String, Object>) responseEntity.get("respMsg");

                    String transKeyValue = (String) respMsg.get("transKey");
                    //  Console.debug(String.format(Locale.US, "MappClient::handshake:transKeyValue=%s", transKeyValue));

                    String sessionId = (String) respMsg.get("sessionId");
                    Console.debug(String.format(Locale.US, "MappClient::handshake:sessionId=%s", sessionId));
                    transportKey = Arrays.copyOfRange(sessionId.getBytes(), 0, 16);

                    this.sessionId = sessionId;
                    Session.getSession().setSessionid(sessionId);
                    Session.getSession().setLastActivityTimeStamp(System.currentTimeMillis());
                    try {
                        // Session.getSession().setSessionid(sessionId);
                        Session.getSession().save();
                    } catch (Exception e) {
                        MyExceptionHandler.handle(e);
                    }
                } else {
                    String message = (String) responseEntity.get("message");
                    Console.debug((String.format(Locale.US, "MappClient::handshake:code=%s, message=%s", code, message)));
                }
            }
        } catch (Exception e) {
            Console.printThrowable(e);
            status = -1;
        }

        return responseEntity;

    }

    private String generateTimestamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        return String.format(Locale.US, formatter.format(new Date()));
    }

    private long generateCurrentTimeInMiliSecond() {

        long time = System.currentTimeMillis();
        return time;

    }

    private String buildMessage(Map<String, Object> entity) {
        String message = null;

        try {

            Map<String, Object> pubInfo = new HashMap<String, Object>();
            pubInfo.put("appId", MyApplication.Companion.getInstance().getAppId());
//            pubInfo.put("sessionId", null == this.sessionId ? "" : this.sessionId);
//            pubInfo.put("sessionId", Util.getSessionIdVal());
            pubInfo.put("sessionId", MyApplication.Companion.getInstance().getSessionId());
            pubInfo.put("version", MyApplication.Companion.getInstance().getAppVersion());
            pubInfo.put("timestamp", generateTimestamp());
            pubInfo.put("lang", MyApplication.Companion.getInstance().getLang());


            pubInfo.put("osType", "android");

            Map<String, Object> request = new HashMap<String, Object>();
            request.put("pubInfo", pubInfo);
            request.put("requestList", new Object[]{entity});
            message = mapper.writeValueAsString(request);
            requestTime = generateCurrentTimeInMiliSecond();

            Console.debug(String.format(Locale.US, "MappClient::callMapp:requestMessage=%s", message));

            boolean isEncrypt = (Boolean) entity.get("isEncrypt");
            if (true == isEncrypt) {

                Map<String, Object> busiParams = (Map<String, Object>) entity.get("busiParams");
//                String encryptedBusiParams = AesUtil.encryptJson(busiParams, Util.getJioMapping().getBytes(), Util.getJioMapping().getBytes());
//                String encryptedBusiParams = AesUtil.encryptJson(busiParams, Util.getJioMapping().getBytes(), MyApplication.Companion.getInstance().getTransKey().getBytes());
                /*String encryptedBusiParams = AesUtil.encryptJson(busiParams, MyApplication.Companion.getInstance().getTransKey().getBytes(), Util.getJioMapping().getBytes());
                entity.put("busiParams", encryptedBusiParams);*/

                transportKey = Arrays.copyOfRange(MyApplication.Companion.getInstance().getTransKey().getBytes(), 0, 16);
//                    String encryptedBusiParams = AesUtil.encryptJson(busiParams, MyApplication.Companion.getInstance().getTransKey().getBytes(), Util.getJioMapping().getBytes());
                String encryptedBusiParams = AesUtil.encryptJson(busiParams, transportKey, Util.getJioMapping().getBytes());
                entity.put("busiParams", encryptedBusiParams);
            }
            request.put("requestList", new Object[]{entity});
            message = mapper.writeValueAsString(request);
            Console.debug(String.format(Locale.US, "MappClient::callMapp:requestEncryptedMessage=%s", message));
        } catch (Exception e) {
            Console.printThrowable(e);
        }

        return message;
    }

    private String buildMessage(List<Map<String, Object>> entities) {
        String message = null;

        try {
            Map<String, Object> pubInfo = new HashMap<String, Object>();
            pubInfo.put("appId", MyApplication.Companion.getInstance().getAppId());
//            pubInfo.put("sessionId", null == this.sessionId ? "" : this.sessionId);
//            pubInfo.put("sessionId", Util.getSessionIdVal());
            pubInfo.put("sessionId", MyApplication.Companion.getInstance().getSessionId());
            pubInfo.put("version", MyApplication.Companion.getInstance().getAppVersion());
            pubInfo.put("timestamp", generateTimestamp());
            pubInfo.put("lang", MyApplication.Companion.getInstance().getLang());


            pubInfo.put("osType", "android");

            requestTime = generateCurrentTimeInMiliSecond();

            Map<String, Object> request = new HashMap<String, Object>();
            request.put("pubInfo", pubInfo);
            request.put("requestList", entities);
            message = mapper.writeValueAsString(request);
            Console.debug(String.format(Locale.US, "MappClient::callMapp:requestMessage=%s", message));

            for (Map<String, Object> entity : entities) {
                boolean isEncrypt = (Boolean) entity.get("isEncrypt");
                if (true == isEncrypt) {
                    Map<String, Object> busiParams = (Map<String, Object>) entity.get("busiParams");
                    transportKey = Arrays.copyOfRange(MyApplication.Companion.getInstance().getTransKey().getBytes(), 0, 16);
//                    String encryptedBusiParams = AesUtil.encryptJson(busiParams, MyApplication.Companion.getInstance().getTransKey().getBytes(), Util.getJioMapping().getBytes());
                    String encryptedBusiParams = AesUtil.encryptJson(busiParams, transportKey, Util.getJioMapping().getBytes());
                    entity.put("busiParams", encryptedBusiParams);
                }

            }
            request.put("requestList", entities);

            message = mapper.writeValueAsString(request);
        } catch (Exception e) {
            Console.printThrowable(e);
        }

        return message;
    }

    @SuppressWarnings("unchecked")
    private void parseMessage(String message, Map<String, Object> entity) throws Exception {
        try {
            Map<String, Object> response = mapper.readValue(message, HashMap.class);

            Map<String, Object> respInfo = (Map<String, Object>) response.get("respInfo");
            String code = (String) respInfo.get("code");

            if ("0".equals(code)) {
                List<Map<String, Object>> respData = (List<Map<String, Object>>) response.get("respData");
                Map<String, Object> responseEntity = respData.get(0);

                String respCode = (String) responseEntity.get("code");
                String logoutBusiCode = (String) responseEntity.get("logoutBusiCode");
                String jioFiberPersistentLoginUpdate = (String) responseEntity.get("busiCode");

//                Console.debug(String.format(Locale.US, "MappClien Logout", code, logoutBusiCode));
                if (!MappActor.ISTATUS_SESSION_INVALID_1.equals(respCode) && !MappActor.ISTATUS_SESSION_INVALID_2.equals(respCode)
                        && !MappActor.ISTATUS_SESSION_INVALID_3.equals(respCode) || "Logout".equalsIgnoreCase(logoutBusiCode)) {
                    boolean isEncrypt = (Boolean) responseEntity.get("isEncrypt");
                    if (isEncrypt) {
                        String encryptedRespMsg = (String) responseEntity.get("respMsg");
                        if (encryptedRespMsg != null) {
                            transportKey = Arrays.copyOfRange(MyApplication.Companion.getInstance().getTransKey().getBytes(), 0, 16);
                            /*Map<String, Object> respMsg = (Map<String, Object>) AesUtil.decryptJson(encryptedRespMsg, Util.getJioMapping().getBytes(),
                                    Util.getJioMapping().getBytes());*/
                            Map<String, Object> respMsg = (Map<String, Object>) AesUtil.decryptJson(encryptedRespMsg, transportKey,
                                    Util.getJioMapping().getBytes());

                            responseEntity.put("respMsg", respMsg);
                        }

                    }
                    entity.putAll(responseEntity);

                    /*if (!respCode.trim().equals("0")) {
                        Log.d(TAG, String.format("Response code: %s",respCode));
                        notifySessionInvalid();
                    }*/

                    Log.d(TAG, String.format("Response code: %s", respCode));

                    if (respCode != null && respCode.trim().equals("20001")) {

                        notifySessionInvalid();

                    }


                    if (respCode != null && respCode.trim().equals("30001")) {
                        if (!ViewUtils.Companion.isEmptyString(jioFiberPersistentLoginUpdate) && jioFiberPersistentLoginUpdate.equalsIgnoreCase("JioFiberPersistentLoginUpdate")) {
                        } else {
                            notifySessionInvalid();
                        }

                    }


                    if (respCode != null && respCode.trim().equals("80000")) {

                        notifySessionInvalid();
                    }
                    if (respCode != null && respCode.trim().equals("20005")) {

                        notifySessionInvalid();
                    }


                    String responseMessage = mapper.writeValueAsString(response);
                    int length = responseMessage.length();
                    Console.debug("responseMessage.length()-->" + length);
                    long totalTime = generateCurrentTimeInMiliSecond() - requestTime;

                    Console.debug("resonseTimeTotalMiliSeconds::" + Long.toString(totalTime) + " :busiCode: " + responseEntity.get("busiCode"));
                    if (length > 4000) {
                        int j = 0;
                        for (j = 0; j < length / 4000; j++) {
                            Console.debug(String.format(Locale.US, "MappClient::callMapp:responseMessage=%s",
                                    responseMessage.substring(j * 4000, (j + 1) * 4000)));
                        }
                        Console.debug(String.format(Locale.US, "MappClient::callMapp:responseMessage=%s", responseMessage.substring(j * 4000, length)));
                    } else
                        Console.debug(String.format(Locale.US, "MappClient::callMapp:responseMessage=%s", responseMessage));
                } else {
                    /*if (!ViewUtils.Companion.isEmptyString(jioFiberPersistentLoginUpdate) && jioFiberPersistentLoginUpdate.equalsIgnoreCase("JioFiberPersistentLoginUpdate")) {
                    } else {
                        notifySessionInvalid();
                    }*/
                    boolean isEncrypt = (Boolean) responseEntity.get("isEncrypt");
                    if (isEncrypt) {
                        String encryptedRespMsg = (String) responseEntity.get("respMsg");
                        if (encryptedRespMsg != null) {
                            transportKey = Arrays.copyOfRange(MyApplication.Companion.getInstance().getTransKey().getBytes(), 0, 16);
                            /*Map<String, Object> respMsg = (Map<String, Object>) AesUtil.decryptJson(encryptedRespMsg, Util.getJioMapping().getBytes(),
                                    Util.getJioMapping().getBytes());*/
                            Map<String, Object> respMsg = (Map<String, Object>) AesUtil.decryptJson(encryptedRespMsg, transportKey,
                                    Util.getJioMapping().getBytes());

                            responseEntity.put("respMsg", respMsg);
                        }

                    }
                    entity.putAll(responseEntity);
                }
            } else {
                if (MappActor.ISTATUS_SESSION_INVALID_1.equals(code) || MappActor.ISTATUS_SESSION_INVALID_2.equals(code)) {
                    notifySessionInvalid();
                }
                String msg = (String) respInfo.get("message");
                Console.debug(String.format(Locale.US, "MappClient::parseMessage:code=%s, message=%s", code, msg));
            }
        } catch (Exception e) {
            Console.printThrowable(e);
            throw e;
        }

    }

    private void parseMessageForMultipleEntity(String message, Map<String, Object> entities) throws Exception {
        try {
            Map<String, Object> response = mapper.readValue(message, HashMap.class);

            Map<String, Object> respInfo = (Map<String, Object>) response.get("respInfo");
            String code = (String) respInfo.get("code");


            if ("0".equals(code)) {
                List<Map<String, Object>> respData = (List<Map<String, Object>>) response.get("respData");
                for (Map<String, Object> responseEntity : respData) {
                    String respCode = (String) responseEntity.get("code");
                    String jioFiberPersistentLoginUpdate = (String) responseEntity.get("busiCode");
                    if (!MappActor.ISTATUS_SESSION_INVALID_1.equals(respCode) && !MappActor.ISTATUS_SESSION_INVALID_2.equals(respCode)
                            && !MappActor.ISTATUS_SESSION_INVALID_3.equals(respCode)) {
                        boolean isEncrypt = (Boolean) responseEntity.get("isEncrypt");
                        if (isEncrypt) {
                            String encryptedRespMsg = (String) responseEntity.get("respMsg");
                            if (encryptedRespMsg != null) {
                               /* Map<String, Object> respMsg = (Map<String, Object>) AesUtil.decryptJson(encryptedRespMsg, Util.getJioMapping().getBytes(),
                                        Util.getJioMapping().getBytes());*/

                                transportKey = Arrays.copyOfRange(MyApplication.Companion.getInstance().getTransKey().getBytes(), 0, 16);
                            /*Map<String, Object> respMsg = (Map<String, Object>) AesUtil.decryptJson(encryptedRespMsg, Util.getJioMapping().getBytes(),
                                    Util.getJioMapping().getBytes());*/
                                Map<String, Object> respMsg = (Map<String, Object>) AesUtil.decryptJson(encryptedRespMsg, transportKey,
                                        Util.getJioMapping().getBytes());


                                responseEntity.put("respMsg", respMsg);
                            }

                        }


                        String transactionId = (String) responseEntity.get("transactionId");
                        entities.put(transactionId, responseEntity);


                        Log.d(TAG, String.format("Response code: %s", respCode));
                        String responseMessage = mapper.writeValueAsString(response);
                        int length = responseMessage.length();
                        Console.debug("responseMessage.length()-->" + length);
                        long totalTime = generateCurrentTimeInMiliSecond() - requestTime;

                        Console.debug("resonseTimeTotalMiliSeconds::" + Long.toString(totalTime) + " :busiCode: " + responseEntity.get("busiCode"));

                        if (respCode != null && respCode.trim().equals("20001")) {

                            notifySessionInvalid();

                        }


                        if (respCode != null && respCode.trim().equals("30001")) {

                            if (!ViewUtils.Companion.isEmptyString(jioFiberPersistentLoginUpdate) && jioFiberPersistentLoginUpdate.equalsIgnoreCase("JioFiberPersistentLoginUpdate")) {
                            } else {
                                notifySessionInvalid();
                            }

                        }


                        if (respCode != null && respCode.trim().equals("80000")) {

                            notifySessionInvalid();
                        }
                        if (respCode != null && respCode.trim().equals("20005")) {

                            notifySessionInvalid();
                        }


                    } else {
                        if (!ViewUtils.Companion.isEmptyString(jioFiberPersistentLoginUpdate) && jioFiberPersistentLoginUpdate.equalsIgnoreCase("JioFiberPersistentLoginUpdate")) {
                        } else {
                            notifySessionInvalid();
                        }
                        break;
                    }
                }
                response.put("respInfo", respInfo);
                response.put("respData", respData);
                String responseMessage = mapper.writeValueAsString(response);
                int length = responseMessage.length();
                Console.debug("responseMessage.length()-->" + length);
                if (length > 40000) {
                    int j = 0;
                    for (j = 0; j < length / 40000; j++) {
                        Console.debug(String.format(Locale.US, "MappClient::callMapp:responseMessage=%s",
                                responseMessage.substring(j * 40000, (j + 1) * 40000)));
                    }
                    Console.debug(String.format(Locale.US, "MappClient::callMapp:responseMessage=%s", responseMessage.substring(j * 40000, length)));
                } else
                    Console.debug(String.format(Locale.US, "MappClient::callMapp:responseMessage=%s", responseMessage));

            } else {
                if (MappActor.ISTATUS_SESSION_INVALID_1.equals(code) || MappActor.ISTATUS_SESSION_INVALID_2.equals(code)) {
                    notifySessionInvalid();
                }
                String msg = (String) respInfo.get("message");
                Console.debug(String.format(Locale.US, "MappClient::parseMessage:code=%s, message=%s", code, msg));
            }
        } catch (Exception e) {
            Console.printThrowable(e);
            throw e;
        }

    }

    synchronized private String syncSend(String payload, String url, Map<String, String> headers) {
        return sendOkHttpClient(payload, url, headers);
    }

    private String asyncSend(String payload, String url, Map<String, String> headers) {
        return sendOkHttpClient(payload, url, headers);
    }

    private String sendOkHttpClient(String payload, String urlString, Map<String, String> headers) {
        Response response = null;
        RequestBody requestBody;
        Request request = null;
        SSLContext sslContext = null;
        try {
            if (!ViewUtils.Companion.isEmptyString(urlString)) {
                if (ApplicationDefine.Companion.IsBuildUnpnned()) {


                    final TrustManager[] trustAllCerts = new TrustManager[]{
                            new X509TrustManager() {
                                @Override
                                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                                }

                                @Override
                                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                                }

                                @Override
                                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                    return new X509Certificate[0];
                                }
                            }
                    };
                    sslContext = SSLContext.getInstance("SSL");
                    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                    // Create an ssl socket factory with our all-trusting manager
//                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                    okHttpClient = new OkHttpClient.Builder()
                            .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
                            .connectTimeout(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS)
                            .readTimeout(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS)
                            .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                            .hostnameVerifier(new HostnameVerifier() {
                                @Override
                                public boolean verify(String hostname, SSLSession session) {
                                    return true;
                                }
                            })
                            // .certificatePinner(certificatePinner)
                            .build();


                    /*TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                    tmf.init((KeyStore) null);
                    sslContext = SSLContext.getInstance("TLS");
                    sslContext.init(null, tmf.getTrustManagers(), null);
                *//*CertificatePinner certificatePinner = new CertificatePinner.Builder()
                        .add(ApplicationDefine.MY_PIN_SERVER_URL, ApplicationDefine.MY_PIN_ONE)
                        .add(ApplicationDefine.MY_PIN_SERVER_URL, ApplicationDefine.MY_PIN_TWO)
                        *//**//*.add(ApplicationDefine.MY_PIN_SERVER_URL, ApplicationDefine.MY_PIN_THREE)
                        .add(ApplicationDefine.MY_PIN_SERVER_URL, ApplicationDefine.MY_PIN_FOUR)*//**//*
                        .build();*//*

                    okHttpClient = new OkHttpClient.Builder()
                            .sslSocketFactory(sslContext.getSocketFactory())
                            .connectTimeout(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS)
                            .readTimeout(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS)
                            .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                            // .certificatePinner(certificatePinner)
                            .build();*/
                } else {

                    TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                    tmf.init((KeyStore) null);
                    sslContext = SSLContext.getInstance("TLS");
                    sslContext.init(null, tmf.getTrustManagers(), null);
                /*CertificatePinner certificatePinner = new CertificatePinner.Builder()
                        .add(ApplicationDefine.MY_PIN_SERVER_URL, ApplicationDefine.MY_PIN_ONE)
                        .add(ApplicationDefine.MY_PIN_SERVER_URL, ApplicationDefine.MY_PIN_TWO)
                        *//*.add(ApplicationDefine.MY_PIN_SERVER_URL, ApplicationDefine.MY_PIN_THREE)
                        .add(ApplicationDefine.MY_PIN_SERVER_URL, ApplicationDefine.MY_PIN_FOUR)*//*
                        .build();*/

                    okHttpClient = new OkHttpClient.Builder()
                            .sslSocketFactory(sslContext.getSocketFactory())
                            .connectTimeout(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS)
                            .readTimeout(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS)
                            .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                            // .certificatePinner(certificatePinner)
                            .build();

                }

                if (headers != null) {
                    requestBody = RequestBody.create(JSON, new JSONObject(payload).toString());
                    URL url = new URL(urlString);
                    request = new Request.Builder()
                            .addHeader("Content-Type", CONTENT_TYPE)
                            .url(url)
                            .post(requestBody)
                            .build();

                }

                response = okHttpClient.newCall(request).execute();

                return response.body().string();
            }
        } catch (Exception e) {
            MyExceptionHandler.handle(e);
            return null;
        }

        return null;
    }


    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        // Close Stream
        if (null != inputStream) {
            inputStream.close();
        }
        return result;
    }


    public String uploadNew(String filename, String mimeType, byte[] data) throws IOException {
        String uploadId = null;
        InputStream inputStream = null;
        String response = "";
        try {
            String url = dispatcher == true ? getUrl("Upload") : uploadServiceUrl;
            Console.debug(String.format("MappClient::upload:url=%s", url));

            HttpsURLConnection urlConnection = null;
            URL urlData = new URL(url);
            urlConnection = (HttpsURLConnection) urlData.openConnection();
            urlConnection.setRequestProperty("Content-Type", mimeType);
            urlConnection.setRequestProperty("Content-Disposition", "attachment;filename=" + filename);
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            urlConnection.setReadTimeout(SOCKET_TIMEOUT);
            urlConnection.setRequestProperty("SessionId", this.sessionId);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setSSLSocketFactory(new MappSocketFactoryNew(null).getSSLontext().getSocketFactory());

            OutputStream writer = urlConnection.getOutputStream();
            writer.write(data);
            writer.close();

            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            if (inputStream != null) {
                response = convertInputStreamToString(inputStream);

                Console.debug(String.format("MappClient::upload:message=%s", response));

                Map<String, Object> uploadInfo = mapper.readValue(response, HashMap.class);
                int errorCode = (Integer) uploadInfo.get("errorCode");
                if (0 == errorCode) {
                    String id = (String) uploadInfo.get("id");
                    if (null != id && 0 < id.length()) {
                        uploadId = id;
                    }
                }
            } else {
                Console.debug("MappClient::upload:inputStream null......");
            }

        } catch (IOException e) {
            Console.printThrowable(e);
            throw e;
        } catch (Exception e) {
            Console.printThrowable(e);
        } finally {
            Console.debug("MappClient::send:closing......");

            try {
                inputStream.close();
                urlConnection.disconnect();
            } catch (Exception e) {
                Console.printThrowable(e);
            }
        }

        return uploadId;

    }


    private void notifySessionInvalid() {
        try {
            Intent intent = new Intent(MappActor.BROADCAST_SESSION_INVALID);
            //  Session.getSession().getApplicationContext().sendBroadcast(intent);
        } catch (Exception e) {
        }
    }

    public String callWebService(String url, String body, Map<String, String> header) {

        String responseMessage = null;

        try {
            responseMessage = asyncSend(body, url, header);
            Console.debug(String.format(Locale.US, "MappClient::callWebService:responseMessage=%s", responseMessage));

        } catch (Exception e) {
            Console.printThrowable(e);
        }

        return responseMessage;
    }

    private class MappSocketFactoryNew extends javax.net.ssl.SSLSocketFactory {

        private SSLContext sslContext = SSLContext.getInstance("TLS");

        public MappSocketFactoryNew(KeyStore truststore)
                throws NoSuchAlgorithmException, KeyManagementException {
            // super(truststore);

            TrustManager trustManager = new X509TrustManager() {

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    // TODO Auto-generated method stub

                }
            };

            sslContext.init(null, new TrustManager[]{trustManager}, null);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
                throws IOException {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public String[] getDefaultCipherSuites() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String[] getSupportedCipherSuites() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Socket createSocket(String host, int port) throws IOException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localHost, int localPort)
                throws IOException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Socket createSocket(InetAddress host, int port) throws IOException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort)
                throws IOException {
            // TODO Auto-generated method stub
            return null;
        }

        public SSLContext getSSLontext() {
            return sslContext;

        }
    }

    //Validates Certificate  Public key
    private boolean validatePinning(HttpsURLConnection conn, String[] validPins) {
        try {
            Certificate[] certs = conn.getServerCertificates();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            for (Certificate cert : certs) {
                X509Certificate x509Certificate = (X509Certificate) cert;
                byte[] key = x509Certificate.getPublicKey().getEncoded();
                md.update(key, 0, key.length);
                byte[] hashBytes = md.digest();

                String base64Encoded = Base64.encodeToString(hashBytes, Base64.DEFAULT)
                        .replace("\n", "");
                for (String pin : validPins) {
                    if (pin.equals(base64Encoded)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}


