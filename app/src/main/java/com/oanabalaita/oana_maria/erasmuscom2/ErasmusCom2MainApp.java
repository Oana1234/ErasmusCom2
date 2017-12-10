package com.oanabalaita.oana_maria.erasmuscom2;

import android.app.Application;

/**
 * Created by Oana-Maria on 07/06/2017.
 */

public class ErasmusCom2MainApp extends Application {

    private static boolean isIsChatActivityOpen = false;

    public static boolean isChatActivityOpen() {
        return isIsChatActivityOpen;
    }

    public static void setChatActivityOpen(boolean isChatActivityOpen) {
        ErasmusCom2MainApp.isIsChatActivityOpen = isChatActivityOpen;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}

