package com.oanabalaita.oana_maria.erasmuscom2.core.users.get.all;

import android.support.v7.widget.RecyclerView;

import com.oanabalaita.oana_maria.erasmuscom2.models.UserChat;

import java.util.List;

/**
 * Created by Oana-Maria on 05/06/2017.
 */

public interface GetUsersContract {

    interface View {
        void onItemClicked(RecyclerView recyclerView, int position, android.view.View v);

        void onGetAllUsersSuccess(List<UserChat> users);

        void onGetAllUsersFailure(String message);

        void onGetChatUsersSuccess(List<UserChat> users);

        void onGetChatUsersFailure(String message);


    }

    interface Presenter {
        void getAllUsers();

        void getChatUsers();
    }

    interface Interactor {
        void getAllUsersFromFirebase();

        void getChatUsersFromFirebase();


        void getConversationsFromFirebase();

        void getNotificationsFromFirebase(String uid);
    }

    interface OnGetAllUsersListener {
        void getAllNotifications(String uid);

        void onGetAllUsersSuccess(List<UserChat> users);

        void onGetAllUsersFailure(String message);
    }

    interface OnGetChatUsersListener {
        void onGetChatUsersSuccess(List<UserChat> users);

        void onGetChatUsersFailure(String message);
    }
}
