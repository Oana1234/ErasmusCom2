package com.oanabalaita.oana_maria.erasmuscom2;

/**
 * Created by Oana-Maria on 23/04/2017.
 */

public class Variabile {

   public static final String TAG = "Comunitatea Erasmus";
    private static final String REGISTER_OPERATION = "register";
    private static final String LOGIN_OPERATION = "login";
    private static final String CHANGE_PASSWORD_OPERATION = "chgPass";
    private static final String ADD_INFORMATIONS_OPERATION = "addinfos";
    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String STATUS = "erasmus_status";
    public static final String EMAIL = "email";
    private static final String UNIQUE_ID = "unique_id";
    private static final String BASE_URL = "http://erasmuscommunity.esy.es/";

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getRegisterOperation() {
        return REGISTER_OPERATION;
    }

    public static String getLoginOperation() {
        return LOGIN_OPERATION;
    }

    public static String getChangePasswordOperation() {
        return CHANGE_PASSWORD_OPERATION;
    }

    public static String getAddInformationsOperation() {
        return ADD_INFORMATIONS_OPERATION;
    }

    public static String getSUCCESS() {
        return SUCCESS;
    }

    public static String getFAILURE() {
        return FAILURE;
    }

    public static String getIsLoggedIn() {
        return IS_LOGGED_IN;
    }

    public static String getSTATUS() {
        return STATUS;
    }

    public static String getEMAIL() {
        return EMAIL;
    }

    public static String getUniqueId() {
        return UNIQUE_ID;
    }

    public static String getTAG() {
        return TAG;
    }
}
