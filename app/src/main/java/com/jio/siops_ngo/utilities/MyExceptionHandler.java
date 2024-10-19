package com.jio.siops_ngo.utilities;

import android.content.Context;
import android.util.Log;

public class MyExceptionHandler {
    public static void handle(Exception e) {
        try {
            if(e.getMessage() != null) {
                Log.d("MyExceptionHandler", "handle() called with: " + "e = [" + e.getMessage() + "]");


                e.printStackTrace();
            }
        } catch (Exception e1) {
            
        }

    }

    public static void handle(Context context, Exception e) {
        //
        handle(e);
    }

    public static void initialize(Context context) {

    }
}
