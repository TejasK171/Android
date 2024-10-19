package com.jio.siops_ngo.network;

import android.content.Context;
import android.util.Log;

import com.jio.siops_ngo.network.utils.Console;
import com.jio.siops_ngo.network.utils.Tools;
import com.jio.siops_ngo.utilities.MyExceptionHandler;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Session implements Serializable {

    private long lastActivityTimeStamp;
    private long lastActivityExpireDifference = TimeUnit.HOURS.toMillis(48);

    private static final String TAG = Session.class.getSimpleName();
    private static final String SESSION_FILE = "Session.dat";

    private static final long OFFER_LIVE_TIME = 24 * 60 * 60 * 1000;

    private static Session instance;

    private Context applicationContext;

    private boolean active = false;



    private String sessionid;

    private String normalLoginJtokenStatus = "false";


    private String token;
    private String jToken;
    private String nonJiojToken="";
    private String nonJioPrimaryNumber="";
    private String lbCookie;
    private String unique;
    private String isPersistentLogin;
    private String timeToLiveFlag;

    private HashMap<String, Object> msisdnInfoHashmap = new HashMap<>();
    private HashMap<String, Object> msisdnMainInfoHashmap = new HashMap<>();
    private String mPIN;

    public HashMap<String, Object> getMsisdnInfo() {
        return msisdnInfoHashmap;
    }

    public void setMsisdnInfo(HashMap<String, Object> msisdnInfoHashmap) {
        this.msisdnInfoHashmap = msisdnInfoHashmap;
    }

    public HashMap<String, Object> getMsisdnMainInfo() {
        return msisdnMainInfoHashmap;
    }

    public void setMsisdnMainInfo(HashMap<String, Object> msisdnMainInfoHashmap) {
        this.msisdnMainInfoHashmap = msisdnMainInfoHashmap;
    }


    private Session() {
        try {
            lastActivityTimeStamp = 0;
        }catch (Exception e)
        {
            MyExceptionHandler.handle(e);
        }
    }

    synchronized public static Session getSession() {
        if (null == instance) {
            instance = new Session();
        }

        return instance;
    }
    /*
    UPI

 */
    public String getmPIN() {
        return mPIN;
    }

    public void setmPIN(String mPIN) {
        this.mPIN = mPIN;
    }

    public String getNormalLoginJtokenStatus() {
        return normalLoginJtokenStatus;
    }

    public void setNormalLoginJtokenStatus(String normalLoginJtokenStatus) {
        this.normalLoginJtokenStatus = normalLoginJtokenStatus;
    }



    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean state) {
        this.active = state;
    }





    public String getIsPersistentLogin() {
        return isPersistentLogin;
    }

    public void setIsPersistentLogin(String isPersistentLogin) {
        this.isPersistentLogin = isPersistentLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public void load() {
        try {
            Map<String, Object> sessionInfo = (Map<String, Object>) Tools.readObject(applicationContext, SESSION_FILE);

            if (sessionInfo == null) {
                Console.debug(String.format("Session::load:detailRegistry This is null on Load"));

            } else {

                String token = (String) sessionInfo.get("token");
                if (null != token) {
                    Console.debug(String.format("Session::load:token=%s", token));
                    this.token = token;
                }

                }
                String sessionlastactivity = (String) sessionInfo.get("sessionlastactivity");
                if (null != sessionlastactivity) {
                    Console.debug(String.format("Session::load:sessionlasttime=%s", sessionlastactivity));
                    this.lastActivityTimeStamp = Long.parseLong(sessionlastactivity);
                }

                String jToken = (String) sessionInfo.get("jToken");
                if (null != jToken) {
                    Console.debug(String.format("Session::jToken=%s", jToken));
                    this.jToken = jToken;
                }
                String norlmalLoginjTokenStatus = (String) sessionInfo.get("normalLoginJtokenStatus");
                if (null != norlmalLoginjTokenStatus) {
                    Console.debug(String.format("Session::norlmalLoginjTokenStatus=%s", norlmalLoginjTokenStatus));
                    this.normalLoginJtokenStatus = norlmalLoginjTokenStatus;
                }




        } catch (Exception e) {
            Console.printThrowable(e);
        }
    }

    public void save() {
        try {
            Map<String, Object> sessionInfo = (Map<String, Object>) Tools.readObject(applicationContext, SESSION_FILE);
            if (null == sessionInfo) {
                sessionInfo = new HashMap<String, Object>();
            }

            if (null != token) {
                sessionInfo.put("token", token);
            }
            if (null != sessionid) {
                sessionInfo.put("sessionid", sessionid);
            }


            if (lastActivityTimeStamp != 0) {
                sessionInfo.put("sessionlastactivity", String.valueOf(lastActivityTimeStamp));
            }



            if (null != lbCookie) {
                sessionInfo.put("lbCookie", lbCookie);
            }
            if (null != jToken) {
                sessionInfo.put("jToken", jToken);
            }

            if (null != normalLoginJtokenStatus) {
                sessionInfo.put("normalLoginJtokenStatus", normalLoginJtokenStatus);
            }


            Console.debug(String.format("Session::save:sessionInfo=%s", sessionInfo.toString()));
            Tools.writeObject(applicationContext, SESSION_FILE, sessionInfo);
        } catch (Exception e) {
            Console.printThrowable(e);
        }
    }

    public void delete() {
        try {

            instance = null;

            Console.debug(String.format("Session::Delete:sessionInfo=%s", "Its Empty Now"));
            Tools.writeObject(applicationContext, SESSION_FILE, null);
        } catch (Exception e) {
            Console.printThrowable(e);
        }
    }

    public String getJToken() {

        return jToken;
    }

    public void setJToken(String jToken) {

        this.jToken = jToken;
    }
    public String getNonJioPrimaryNumber() {

        return nonJioPrimaryNumber;
    }

    public void setNonJioPrimaryNumber(String nonJioPrimaryNumber) {

        this.nonJioPrimaryNumber = nonJioPrimaryNumber;
    }

    public String getNonJioJToken() {

        return nonJiojToken;
    }

    public void setNonJioJToken(String nonJiojToken) {

        this.nonJiojToken = nonJiojToken;
    }



    public String getLbCookie() {
        return lbCookie;
    }

    public void setLbCookie(String lbCookie) {
        this.lbCookie = lbCookie;
    }

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }


    public String getSessionid() {

        if(sessionid==null)
        {
            Log.d(TAG,"Get Session id: null");
        }

        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
        Log.d(TAG,"Set Session id: "+sessionid);

    }
    public boolean hasSessionExpired() {
        long now = System.currentTimeMillis();

        if (lastActivityTimeStamp==0)
            return false;

        return now - lastActivityTimeStamp >= lastActivityExpireDifference;
    }

    public long getLastActivityTimeStamp() {
        return lastActivityTimeStamp;
    }

    public void setLastActivityTimeStamp(long lastActivityTimeStamp) {
        this.lastActivityTimeStamp = lastActivityTimeStamp;
    }
}
