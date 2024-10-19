package com.jio.siops_ngo.network;

import android.content.Context;
import android.os.Message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.jio.myjio.bean.CoroutinesResponse;
import com.jio.siops_ngo.R;
import com.jio.siops_ngo.network.net.MappClient;
import com.jio.siops_ngo.network.utils.Console;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappActor {

    public static final String BROADCAST_SESSION_INVALID = "BROADCAST_SESSION_INVALID";
    public static final int MESSAGE_TYPE_OTP = 100;
    public static final int MESSAGE_TYPE_JIOFIBER_OTP = 100;
    public static final int MESSAGE_TYPE_VERIFY_OTP = 101;
    public static final int MESSAGE_TYPE_VERIFY_JIOFIBER_OTP = 261;
    public static final int MESSAGE_TYPE_VERIFY_USERID = 103;
    public static final int MESSAGE_TYPE_ACTIVATION = 104;
    public static final int MESSAGE_TYPE_LOGIN = 105;
    public static final int MESSAGE_TYPE_CHANGE_PASSWORD = 106;
    public static final int MESSAGE_TYPE_SYNC_CUSTOMER = 107;
    public static final int MESSAGE_TYPE_RESET_PASSWORD = 108;
    public static final int MESSAGE_TYPE_SYNC_PROPERTY = 109;
    public static final int MESSAGE_TYPE_UPDATE_PORTRAIT = 110;
    public static final int MESSAGE_TYPE_REMOVE_PORTRAIT = 111;
    public static final int MESSAGE_TYPE_TRANSFER_BALANCE = 112;
    public static final int MESSAGE_TYPE_QUERY_USAGE = 114;
    public static final int MESSAGE_TYPE_RECHARGE = 115;
    public static final int MESSAGE_TYPE_QUERY_ORDER = 116;
    public static final int MESSAGE_TYPE_TOPUP = 117;
    public static final int MESSAGE_TYPE_QUERY_OFFER = 118;
    public static final int MESSAGE_TYPE_LOGOUT = 120;
    public static final int MESSAGE_TYPE_NON_JIO_LOGOUT = 152;
    public static final int MESSAGE_TYPE_QUERY_CUSTOMERINFO = 121;
    public static final int MESSAGE_TYPE_SYNC_ACCOUNT = 122;
    public static final int MESSAGE_TYPE_GET_BILL = 126;
    public static final int MESSAGE_TYPE_GET_BILL_DETAIL = 127;
    public static final int MESSAGE_TYPE_GET_SERVICE_REQUEST = 129;
    public static final int MESSAGE_TYPE_GET_SERVICE_REQUEST_CATEGORY = 131;
    public static final int MESSAGE_TYPE_CREATE_SERVICE_REQUEST = 134;
    public static final int MESSAGE_TYPE_GET_SERVICE_REQUEST_SUB_CATEGORY = 136;
    public static final int MESSAGE_TYPE_GET_CUSTMER_DETAILS = 138;
    public static final int MESSAGE_TYPE_GET_VERIFY_REG_INFO = 139;
    public static final int MESSAGE_TYPE_INFO_SEND_OTP = 140;
    public static final int MESSAGE_TYPE_INFO_UPDATE_BY_OTP = 141;
    public static final int MESSAGE_TYPE_PREFERED_LANGUAGE = 142;
    public static final int MESSAGE_TYPE_BESTWAY_OF_COMMUNICATION = 143;
    public static final int MESSAGE_TYPE_CHANGE_REMOVE_PRODUT_OFFER_SUBMIT = 147;
    public static final int MESSAGE_TYPE_FIND_PLAN_OFFERINGS = 150;
    public static final int MESSAGE_TYPE_GET_DIGITAL_SERVICE_CONFIGURATION = 151;
    public static final int MESSAGE_TYPE_LOOK_UP_VALUE = 152;
    public static final int MESSAGE_TYPE_UNSUBSCRIBE_APP = 153;
    public static final int MESSAGE_TYPE_GET_DIGITAL_SERVICE_HISTORY = 154;

    public static final int MESSAGE_TYPE_CUSTOMER_BILL_DETAIL = 179;
    public static final int MESSAGE_TYPE_GET_USAGE = 180;
    public static final int MESSAGE_TYPE_GET_POSTPAID_GET_BILLING_STATEMENT_DETAIL = 181;
    public static final int STATUS_OK = 0;
    public static final int SERVER_ERROR = 1;
    public static final int STATUS_INTERNAL_ERROR = -1;
    public static final String STATUS_NON_JIO_NO = "01044";
    public static final int ALREADY_JIO_CUSTOMER_POPUP = 1001;
    public static final int STATUS_NETWORK_ERROR = -2;
    public static final int STATUS_OTP_MISSMATCH = -3;
    public static final int STATUS_USERID_DUPLICATE = -4;
    public static final int STATUS_TRANSACTION_EXIST = -5;
    public static final int STATUS_USER_LOCKED = -6;
    public static final int STATUS_USAGE_NODATA = -7;
    public static final int STATUS_ALREADY_ACTIVATED = -8;
    public static final int STATUS_GET_PAY_URL_FAILURE = -9;
    public static final int MESSAGE_TYPE_GETRILPOIHOTSPOT = 179;
    public static final int MESSAGE_TYPE_GETRILPOISERVICECENTER = 179;
    public static final int MESSAGE_TYPE_GETCOVERAGEINFO = 180;
    public static final int MESSAGE_TYPE_GETCOVERAGEMAP = 181;
    public static final int MESSAGE_TYPE_GETGOOGLEGEOCODING = 182;
    public static final int MESSAGE_TYPE_QUERY_CUSTOMER_ORDER_DETAIL = 183;
    public static final int MESSAGE_TYPE_DND_SUBMIT = 184;
    public static final int MESSAGE_TYPE_RETRIEVE_SERVICES_ORDER = 185;
    public static final int MESSAGE_TYPE_EXCEPTION_HANDLING = 186;
    // AboutActivity
    public static final int UPDATA_VERSION = 189;
    public static final int MSG_TYPE_UPDATE_ON_SERVER = 190;
    // ChangeAddressActivity
    public static final int MESSAGE_TYPE_QUERY_SERVICE_REQUEST_CATEGORY = 191;
    public static final int MESSAGE_TYPE_QUERY_SERVICE_REQUEST_CATEGORY_LEVEL_1 = 192;
    public static final int MESSAGE_TYPE_QUERY_SERVICE_REQUEST_CATEGORY_LEVEL_2 = 193;
    // ChangeEmailOTPActivity
    public static final int START_COUNT_DOWN = 194; // 55

    // BillPreferenceActivityNew
    public static final int STOP_COUNT_DOWN = 195; // 56
    public static final int START_COUNT_DOWN1 = 196; // 12; = 55;
    public static final int STOP_COUNT_DOWN2 = 197; // 13; // = 56;
    // MyAppRechargeSubscribeActivity
    public static final int MESSAGE_TYPE_DIGITAL_RECHARGE = 198; // 1200;
    // SafeCustodyActivity
    public static final int MESSAGE_FIND_BUSINESS_INTERACTION = 199; // 123;
    // ServiceRequestActivity
    public static final int mAddServiceRequestNo = 200; // 1;

    // FirstTimeActivationStepTwoActivity // check values repeated
    public static final int MESSAGE_TYPE_SERVICE_REQUEST_DETAIL = 201; // 100;
    // StartActivity
    public static final int HAND_SHAKE_OK = 202; // 0;


    public static final int NET_ERROR = 203; // 1;

    public static final int COUNT_DOWN_SUM = 204; // 61;

    public static final int COUNT_DOWN_SUM1 = 205; // 16; // = 61;
    public static final int MSG_CREATE_PROSPECT = 206;// 110;

    public static final int MSG_LOOK_UP_VALUE = 207; // 111;
    // CreateGroupFragment
    public static final int MSG_CREATE_GROUP = 208; // 111;
    // private static final int UPDATA_VERSION = 5;
    public static final int EGAIN_EMAIL_MESSAGE = 209; // 2025;


    public static final int GET_MY_BILL = 213; // 1005;

    // EmailFragment
    public static final int MESSAGE_TYPE_OUTAGE_ALERT = 214; // 210;

    public static final int MESSAGE_TYPE_DELETE_IDENTITY = 50072;

    public static final int MESSAGE_TYPE_GET_ASSOCIATEDCUSTOMERS = 215; // 77777;
    public static final int MESSAGE_UPDATE_DATA = 216; // 1001;

    // Enterprise Jio Preview
    public static final int MESSAGE_LOAD_DEVICE_DATA = 217; // 1002;
    public static final int MESSAGE_LOAD_DEVICE_DETAIL = 218; // 1003;
    public static final int MSG_GET_MY_BILL = 219;// 101;

    // ManageAcountFragment
    public static final int QUERY_ACCOUNT_STATEMENT = 220; // 102;

    public static final int MESSAGE_LOAD_DATA = 228; // 1001;
    public static final int QUERY_CHARGE = 229; // 1;

    // MyGroupFragment
    public static final int MSG_QUERY_GET_OUTSTANDING_BALACE = 230; // 100;
    public static final int MSG_QUERY_PAY_BILL = 231; // 101;
    public static final int MESSAGE_BILL_PLAN = 232; // 1002;


    public static final int MESSAGE_TYPE_GET_UPDATE_SUCCESS = 233; // 1234;

    // PayBillFragment
    public static final int MESSAGE_TYPE_CREATE_SERVICE_REQUEST_FOR_UPLOAD_SR = 234; // 222;


    public static final int MESSAGE_LOAD_TOPUPS = 235; // 1003;
    public static final int MESSAGE_USER_VERIFY_BROWSE_PLAN = 236; // 1004;

    public static final int MESSAGE_ACTIVE_ACCOUNT = 237; // 102;
    public static final int MESSAGE_ADHARBASED_MOBILE_NUMBER = 800; // 102;

    // RaiseRequestFragment
    public static final int MESSAGE_REQUEST_NEW_ACTIVATION_OTP = 238; // 181;

    // RechargeForAnotherNumberFragment
    public static final int QUERY_CHARGE2 = 239; // 2;
    // UsageAlertFragment
    public static final int MESSAGE_SET_DATA = 240;// 1002;

    public static final int MSG_READ_STATUS_ON_SERVER = 241; // 101;
    public static final int MSG_DELETE_ACCOUNT = 242; // 111; //end

    // ReclaimOptionFragment
    public static final int MESSAGE_FORGOT_PASSWORD_SEND_OTP = 243;

    // Coupon SDK
    public static final int MESSAGE_TYPE_GET_CUSTOMER_COUPONS = 254;
    public static final int MESSAGE_TYPE_GET_TOKEN = 260;
    public static final int MESSAGE_TYPE_GET_SSO_TOKEN_JIOMONEY = 264;
    public static final int MESSAGE_TYPE_GET_ACCESS_TOKEN = 261;
    public static final int MESSAGE_TYPE_GET_ACCESS_TOKEN_BEFORE_J_EVENT = 461;

    public static final int MESSAGE_TYPE_BANNER_IMAGE = 262;
    public static final int MESSAGE_GET_COUPON_COUNT = 263;
    // ManageAccountMyAccountViewholder
    public static final int REGISTER_INFO_MOBILE_NUMBER_RECLAIM = 50021;
    public static final int REGISTER_INFO_MOBILE_NUMBER_ALREDY_EXIST = 58000;
    public static final int REGISTER_INFO_MOBILE_NUMBER_ALREDY_EXIST_SAME_USER = 58002;

    // Voucher Badge count
    public static final int MESSAGE_TYPE_GET_VOUCHER_COUNT = 444;

    // GetAutoPayStatus
    public static final int MESSAGE_TYPE_GET_AUTO_PAY_STATUS = 445;
    public static final String ISTATUS_SESSION_INVALID_1 = "01001";// use for
    public static final int MSG_NON_JIO_SEND_OTP = 264;
    public static final int MSG_NON_JIO_VALIDATE_OTP = 265;
    public static final int MSG_NON_JIO_LOGIN = 266;
    public static final int MSG_NON_JIO_Linkink = 267;
    public static final int MSG_NON_JIO_Linkink_DATA = 268;
    public static final int MSG_NON_JIO_WEB_TOKEN = 269;
    public static final int MSG_NON_JIO_ACC_DELETE = 270;
    public static final int MSG_SCREENZ_WHITE_LISTED = 10270;
    public static final int SCREENZ_JWT_TOKEN = 10531;
    public static final int HELLOJIO_JWT_TOKEN = 10534;

    public static final int IPL_WIDGET_JWT_TOKEN = 10537;
    public static final String SESSION_INVALID = "80000";
    public static final String INPUT_MISMATCH = "20001";
    public static final String PERMISSION_DENIED = "20005";
    // handling
    // idam
    // token
    // expired
    public static final String ISTATUS_SESSION_INVALID_2 = "02002";// use for
    // handling
    // idam
    // token
    // expired
    public static final String ISTATUS_SESSION_INVALID_3 = "30001";// used for

    public static boolean ENCRYPTION_ENABLED = false;
    public static boolean REPLICA_ENABLED = false;
    public static boolean DEVICE_INFO_ENABLE_HANDSHAKE = false;

    public static boolean QUERY_PRODUCT_3 = false;
    public static boolean generatePPUrlWithoutMAPP = false;

    public static String ppUrlGenerator;

    public static String ppUrlGeneratorPre;


    public static String rtssChannel;

    public static String topupProduct;

    public static String topupAccountCharName;

    public static String topupAccountCharValue;

    public static String xApiKey;

    public static String paymentProxyVersion;

    public static final String VERSION_UPDATE_CODE = "30002";
    public static final String VERSION_SESSION_INVALID = "80000";
    public static final String UNAUTHORIZED_ACCESS_ERROR = "30000";
    public static final String USER_NOT_LOGGED_IN = "30001";

    private ObjectMapper mapper = new ObjectMapper();

    public void execute(final String busiCode, final Map<String, Object> requestEntity, final IMappActor actor) {
        Executor executor = new Executor(busiCode, requestEntity, actor);
        new Thread(executor).start();
    }

    public void execute(final List<Map<String, Object>> requestEntityList, final IMappActor actor) {
        Executor executor = new Executor(requestEntityList, actor);
        new Thread(executor).start();
    }


    public void upload(final String name, final String mimeType, final byte[] data, final IMappActor actor) {
        UploadExecutor executor = new UploadExecutor(name, mimeType, data, actor);
        new Thread(executor).start();
    }

    public int clientException2Mail(String id, String name, String reqTime, String operationType, String exceptionSource, String mobileModel,
                                    String exceptionCode, String exceptionMessage, String requestMessage, String responseMessage, String appVersion, String isNeedMail,
                                    final Message message) {
        int status = MappActor.STATUS_OK;
        try {

            Map<String, Object> busiParams = new HashMap<String, Object>();
            busiParams.put("id", id);
            busiParams.put("name", name);
            busiParams.put("reqTime", reqTime);
            busiParams.put("operationType", operationType);
            busiParams.put("exceptionSource", exceptionSource);
            busiParams.put("mobileModel", mobileModel);
            busiParams.put("exceptionCode", exceptionCode);
            busiParams.put("exceptionMessage", exceptionMessage);
            busiParams.put("osType", "Android");
            busiParams.put("requestMessage", requestMessage);
            busiParams.put("responseMessage", responseMessage);
            busiParams.put("appVersion", appVersion);
            busiParams.put("isNeedMail", isNeedMail);

            final String transactionId = MappClient.generateTransactionId();
            String busiCode = "ClientException2Mail";
            Map<String, Object> requestEntity = new HashMap<String, Object>();
            requestEntity.put("busiParams", busiParams);
            requestEntity.put("busiCode", busiCode);
            requestEntity.put("transactionId", transactionId);
            requestEntity.put("isEncrypt", MappActor.ENCRYPTION_ENABLED);

            execute(busiCode, requestEntity, new IMappActor() {

                @Override
                public void onExecuted(int execStatus, Map<String, Object> responseEntity) {
                    int status = execStatus;
                    try {
                        if (0 == status) {
                            String code = (String) responseEntity.get("code");
                            Map<String, Object> respMsg = null;
                            respMsg = (Map<String, Object>) responseEntity.get("respMsg");
                            if (message != null && message.obj != null) {
                                if ("0".equals(code)) {
                                    message.obj = respMsg;

                                } else {
                                    status = 1;
                                    message.obj = responseEntity;
                                }
                            }

                        }

                    } catch (Exception e) {
                        Console.printThrowable(e);
                        status = MappActor.STATUS_INTERNAL_ERROR;
                    } finally {
                        try {
                            if (message != null && message.obj != null) {
                                message.arg1 = status;
                                message.sendToTarget();
                            }
                        } catch (Exception e) {
                            Console.printThrowable(e);
                        }
                    }
                }

            });

        } catch (Exception e) {
            Console.printThrowable(e);
            status = MappActor.STATUS_INTERNAL_ERROR;
        }
        return status;

    }

    /**
     * @param transactionType
     * @param message
     * @return
     */
    public int getTransactionRefNum(int transactionType, final Message message) {
        int status = MappActor.STATUS_OK;
        try {

            Map<String, Object> busiParams = new HashMap<String, Object>();
            busiParams.put("transactionType", transactionType);

            final String transactionId = MappClient.generateTransactionId();
            String busiCode = "GetTransactionRefNum";
            Map<String, Object> requestEntity = new HashMap<String, Object>();
            requestEntity.put("busiParams", busiParams);
            requestEntity.put("busiCode", busiCode);
            requestEntity.put("transactionId", transactionId);
            requestEntity.put("isEncrypt", MappActor.ENCRYPTION_ENABLED);

            execute(busiCode, requestEntity, new IMappActor() {

                @Override
                public void onExecuted(int execStatus, Map<String, Object> responseEntity) {
                    int status = execStatus;
                    try {
                        if (0 == status) {
                            String code = (String) responseEntity.get("code");
                            Map<String, Object> respMsg = null;
                            respMsg = (Map<String, Object>) responseEntity.get("respMsg");
                            if ("0".equals(code)) {
                                message.obj = respMsg;
                            } else {
                                status = 1;
                                message.obj = responseEntity;
                            }
                        }

                    } catch (Exception e) {
                        Console.printThrowable(e);
                        status = MappActor.STATUS_INTERNAL_ERROR;
                    } finally {
                        try {
                            message.arg1 = status;
                            message.sendToTarget();
                        } catch (Exception e) {
                            Console.printThrowable(e);
                        }
                    }
                }

            });

        } catch (Exception e) {
            Console.printThrowable(e);
            status = MappActor.STATUS_INTERNAL_ERROR;
        }
        return status;
    }

    public void executeOnSameThread(final String busiCode, final Map<String, Object> requestEntity, final IMappActor actor) {
        Executor executor = new Executor(busiCode, requestEntity, actor);
        executor.run();
    }

    public interface IMappActor {
        void onExecuted(final int execStatus, final Map<String, Object> responseEntity);
    }

    private class Executor implements Runnable {

        private String busiCode;
        private Map<String, Object> requestEntity;
        private List<Map<String, Object>> requestEntityList;

        private IMappActor actor;

        public Executor(String busiCode, Map<String, Object> requestEntity, IMappActor actor) {
            this.busiCode = busiCode;
            this.requestEntity = requestEntity;
            this.actor = actor;
        }

        public Executor(List<Map<String, Object>> requestEntityList, IMappActor actor) {
            this.requestEntityList = requestEntityList;
            this.actor = actor;
        }

        @Override
        public void run() {
            int status = 0;

            Map<String, Object> responseEntity = new HashMap<String, Object>();
            try {
                MappClient client = MappClient.getMappClient();
                if (this.requestEntityList != null && this.requestEntityList.size() > 0) {

                    /**
                     * tell the server it need concurrency
                     */
                    status = client.callMapp(this.requestEntityList, responseEntity);

                    /*
                     * release requestEntityList in time
                     */
                    for (Map<String, Object> reqMap : requestEntityList) {
                        reqMap.clear();
                        reqMap = null;
                    }
                    requestEntityList.clear();
                    requestEntityList = null;
                } else {
                    /**
                     * tell the server no need concurrency
                     */
                    status = client.callMapp(this.busiCode, this.requestEntity, responseEntity);
                }

            } catch (Exception e) {
                Console.printThrowable(e);
                status = -1;
            }

            try {
                if (null != actor) {
                    actor.onExecuted(status, responseEntity);
                }
            } catch (Exception e) {
                Console.printThrowable(e);
            }
        }
    }

    public CoroutinesResponse executeOnCoroutines(Context mContext, String busiCode, HashMap<String, Object> requestEntity, List<Map<String, Object>> requestEntityList) {
        int status = MappActor.STATUS_OK;
        Map<String, Object> responseEntity = new HashMap<String, Object>();
        try {
            MappClient client = MappClient.getMappClient();
            if (requestEntityList != null && requestEntityList.size() > 0) {

                /**
                 * tell the server it need concurrency
                 */
                status = client.callMapp(requestEntityList, responseEntity);

                /*
                 * release requestEntityList in time
                 */
                for (Map<String, Object> reqMap : requestEntityList) {
                    reqMap.clear();
                    reqMap = null;
                }
                requestEntityList.clear();
                requestEntityList = null;
            } else {
                /**
                 * tell the server no need concurrency
                 */
                status = client.callMapp(busiCode, requestEntity, responseEntity);
            }

        } catch (Exception e) {
            Console.printThrowable(e);
            status = -1;
        }
        CoroutinesResponse mCoroutinesResponse = new CoroutinesResponse();
        mCoroutinesResponse.setStatus(status);
        //mCoroutinesResponse.setResponseEntity(responseEntity);
        if(responseEntity.containsKey("code")){
            String statusCodeStr = String.valueOf(responseEntity.get("code"));
            if(statusCodeStr== null || statusCodeStr.equalsIgnoreCase("null")){
                if(responseEntity.containsKey("message")){
                    String errorMsg = String.valueOf(responseEntity.get("message"));
                    mCoroutinesResponse.setResponseEntity(null);
                    mCoroutinesResponse.setErrorMsg(errorMsg);
                }
            }
            else if(statusCodeStr.equalsIgnoreCase(UNAUTHORIZED_ACCESS_ERROR)){
                String errorMsg = "Unauthorized Access";
                mCoroutinesResponse.setResponseEntity(null);
                mCoroutinesResponse.setErrorMsg(errorMsg);


            }
            else if(statusCodeStr.equalsIgnoreCase(VERSION_UPDATE_CODE)){
                String errorMsg = mContext.getString(R.string.version_update_msg);
                mCoroutinesResponse.setResponseEntity(null);
                mCoroutinesResponse.setErrorMsg(errorMsg);


            }else if(statusCodeStr.equalsIgnoreCase(VERSION_SESSION_INVALID) || statusCodeStr.equalsIgnoreCase(USER_NOT_LOGGED_IN)){
                String errorMsg = mContext.getString(R.string.session_expired);
                mCoroutinesResponse.setResponseEntity(null);
                mCoroutinesResponse.setErrorMsg(errorMsg);
                mCoroutinesResponse.setErrorCode(VERSION_SESSION_INVALID);


            }else{

                if (responseEntity.containsKey("respMsg")){
                    Map<String, Map<String, Object>> responseEntityMap = (Map<String, Map<String, Object>>) responseEntity.get("respMsg");
                    if (responseEntityMap.containsKey("responseHeader")) {
                        Map<String, Object> responseHeaderMap = responseEntityMap.get("responseHeader");
                        Map<String, Object> responsePayloadMap = responseEntityMap.get("responsePayload");
                        if (responseHeaderMap.containsKey("status")) {
                            int statusCode = ((Integer) responseHeaderMap.get("status")).intValue();
                            if (statusCode == 0) {
                                if (responsePayloadMap.containsKey("messageDetails")) {
                                    String errorMsg = String.valueOf(responsePayloadMap.get("messageDetails"));
                                    mCoroutinesResponse.setResponseEntity(null);
                                    mCoroutinesResponse.setErrorMsg(errorMsg);


                                }

                            } else {

                                mCoroutinesResponse.setResponseEntity(responsePayloadMap);
                            }
                        }
                    }else{
                        FirebaseCrashlytics.getInstance().recordException(new RuntimeException(busiCode + " - Something Went Wrong"));

                    }
                }

            }
        }


        return mCoroutinesResponse;
    }


    private class Executor2nd implements Runnable {

        private String busiCode;
        private Map<String, Object> requestEntity;
        private List<Map<String, Object>> requestEntityList;

        private IMappActor actor;

        public Executor2nd(String busiCode, Map<String, Object> requestEntity, IMappActor actor) {
            this.busiCode = busiCode;
            this.requestEntity = requestEntity;
            this.actor = actor;
        }

        public Executor2nd(List<Map<String, Object>> requestEntityList, IMappActor actor) {
            this.requestEntityList = requestEntityList;
            this.actor = actor;
        }

        @Override
        public void run() {
            int status = 0;

            Map<String, Object> responseEntity = new HashMap<String, Object>();
            try {
                MappClient client = MappClient.getMappClient();
                if (this.requestEntityList != null && this.requestEntityList.size() > 0) {

                    /**
                     * tell the server it need concurrency
                     */
                    status = client.callMapp(this.requestEntityList, responseEntity);

                    /*
                     * release requestEntityList in time
                     */
                    for (Map<String, Object> reqMap : requestEntityList) {
                        reqMap.clear();
                        reqMap = null;
                    }
                    requestEntityList.clear();
                    requestEntityList = null;
                } else {
                    /**
                     * tell the server no need concurrency
                     */
                    status = client.callMapp(this.busiCode, this.requestEntity, responseEntity);
                }

            } catch (Exception e) {
                Console.printThrowable(e);
                status = -1;
            }

            try {
                if (null != actor) {
                    actor.onExecuted(status, responseEntity);
                }
            } catch (Exception e) {
                Console.printThrowable(e);
            }
        }
    }

    private class UploadExecutor implements Runnable {

        private String name;

        private String mimeType;

        private byte[] data;

        private IMappActor actor;

        public UploadExecutor(String name, String mimeType, byte[] data, IMappActor actor) {
            this.name = name;
            this.mimeType = mimeType;
            this.data = data;
            this.actor = actor;
        }

        @Override
        public void run() {
            int status = 0;

            Map<String, Object> responseEntity = new HashMap<String, Object>();
            try {
                MappClient client = MappClient.getMappClient();

                String uploadId = client.uploadNew(name, mimeType, data);
                responseEntity.put("uploadId", uploadId);
            } catch (Exception e) {
                Console.printThrowable(e);
                status = -1;
            }

            try {
                if (null != actor) {
                    actor.onExecuted(status, responseEntity);
                }
            } catch (Exception e) {
                Console.printThrowable(e);
            }
        }
    }
}
