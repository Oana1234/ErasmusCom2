package com.oanabalaita.oana_maria.erasmuscom2;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Oana-Maria on 07/04/2017.
 */

public class PrimaLansareClass {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    //shared preferences mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "lansare_test";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PrimaLansareClass(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}
