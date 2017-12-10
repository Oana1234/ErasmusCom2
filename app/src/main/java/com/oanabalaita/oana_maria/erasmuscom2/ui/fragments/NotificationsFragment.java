package com.oanabalaita.oana_maria.erasmuscom2.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oanabalaita.oana_maria.erasmuscom2.R;
import com.oanabalaita.oana_maria.erasmuscom2.core.users.get.all.GetUsersContract;
import com.oanabalaita.oana_maria.erasmuscom2.core.users.get.all.GetUsersPresenter;
import com.oanabalaita.oana_maria.erasmuscom2.models.UserChat;
import com.oanabalaita.oana_maria.erasmuscom2.ui.adapters.NotificationRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment implements GetUsersContract.View, SwipeRefreshLayout.OnRefreshListener {


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerViewNotifications;

    private NotificationRecyclerAdapter mNotificationsRecyclerAdapter;

    private GetUsersPresenter mGetNotificationsPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_notifications, container, false);
        bindViews(fragmentView);
        return fragmentView;
    }

    private void bindViews(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mRecyclerViewNotifications = (RecyclerView) view.findViewById(R.id.recycler_view_notifications);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        mGetNotificationsPresenter = new GetUsersPresenter(this);
        getUsers();
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });


        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        getUsers();
    }

    private void getUsers() {
        mGetNotificationsPresenter.getAllNotifications(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

    }

    @Override
    public void onGetAllUsersSuccess(List<UserChat> users) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(false);


            }
        });


        mNotificationsRecyclerAdapter = new NotificationRecyclerAdapter(users);

        mRecyclerViewNotifications.setAdapter(mNotificationsRecyclerAdapter);
        mNotificationsRecyclerAdapter.notifyDataSetChanged();


    }

    @Override
    public void onGetAllUsersFailure(String message) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                onGetAllUsersSuccess(null);
            }
        });

        mRecyclerViewNotifications.setAdapter(null);


    }

    @Override
    public void onGetChatUsersSuccess(List<UserChat> users) {

        mNotificationsRecyclerAdapter = new NotificationRecyclerAdapter(users);
        mRecyclerViewNotifications.setAdapter(mNotificationsRecyclerAdapter);
        mNotificationsRecyclerAdapter.notifyDataSetChanged();

    }


    @Override
    public void onGetChatUsersFailure(String message) {


    }

}
