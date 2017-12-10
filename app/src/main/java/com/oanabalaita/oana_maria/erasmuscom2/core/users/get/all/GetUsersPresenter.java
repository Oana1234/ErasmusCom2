package com.oanabalaita.oana_maria.erasmuscom2.core.users.get.all;

import com.oanabalaita.oana_maria.erasmuscom2.models.UserChat;

import java.util.List;

/**
 * Created by Oana-Maria on 05/06/2017.
 */

public class GetUsersPresenter implements GetUsersContract.Presenter, GetUsersContract.OnGetAllUsersListener{

    private GetUsersContract.View mView;
    private GetUsersInteractor mGetUsersInteractor;

    public GetUsersPresenter(GetUsersContract.View view) {
        this.mView = view;
        mGetUsersInteractor = new GetUsersInteractor(this);
    }

    @Override
    public void getAllUsers() {
        mGetUsersInteractor.getAllUsersFromFirebase();
    }
    @Override
    public void getAllNotifications(String uid){
        mGetUsersInteractor.getNotificationsFromFirebase(uid);
    }
    public void getAllConversations(){

        mGetUsersInteractor.getConversationsFromFirebase();
    }

    @Override
    public void getChatUsers() {
        mGetUsersInteractor.getChatUsersFromFirebase();
    }

    @Override
    public void onGetAllUsersSuccess(List<UserChat> users) {
        mView.onGetAllUsersSuccess(users);
    }

    @Override
    public void onGetAllUsersFailure(String message) {
        mView.onGetAllUsersFailure(message);
    }
}
