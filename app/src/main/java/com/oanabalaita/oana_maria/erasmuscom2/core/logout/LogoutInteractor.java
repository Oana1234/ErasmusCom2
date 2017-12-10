package com.oanabalaita.oana_maria.erasmuscom2.core.logout;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Oana-Maria on 05/06/2017.
 */

public class LogoutInteractor implements LogoutContract.Interactor {

    private LogoutContract.OnLogoutListener mOnLogoutListener;

    public LogoutInteractor(LogoutContract.OnLogoutListener onLogoutListener) {
        mOnLogoutListener = onLogoutListener;
    }

    @Override
    public void performFirebaseLogout() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth.getInstance().signOut();
            mOnLogoutListener.onSuccess("Successfully logged out!");
        } else {
            mOnLogoutListener.onFailure("No user logged in yet!");
        }
    }
}
