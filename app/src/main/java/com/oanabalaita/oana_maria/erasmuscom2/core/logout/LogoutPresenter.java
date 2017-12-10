package com.oanabalaita.oana_maria.erasmuscom2.core.logout;

/**
 * Created by Oana-Maria on 05/06/2017.
 */

public class LogoutPresenter implements LogoutContract.Presenter, LogoutContract.OnLogoutListener {

    private LogoutContract.View mLogoutView;
    private LogoutInteractor mLogoutInteractor;

    public LogoutPresenter(LogoutContract.View logoutView) {
        mLogoutView = logoutView;
        mLogoutInteractor = new LogoutInteractor(this);
    }

    @Override
    public void logout() {
        mLogoutInteractor.performFirebaseLogout();


    }

    @Override
    public void onSuccess(String message) {
        mLogoutView.onLogoutSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        mLogoutView.onLogoutFailure(message);
    }
}
