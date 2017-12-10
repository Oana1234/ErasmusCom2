package com.oanabalaita.oana_maria.erasmuscom2.ui.adapters;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.oanabalaita.oana_maria.erasmuscom2.R;
import com.oanabalaita.oana_maria.erasmuscom2.models.UserChat;
import com.oanabalaita.oana_maria.erasmuscom2.ui.activities.ProfileActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by Oana-Maria on 05/06/2017.
 */

public class UserListingRecyclerAdapter extends RecyclerView.Adapter<UserListingRecyclerAdapter.ViewHolder> {

    private List<UserChat> mUsers;


    public UserListingRecyclerAdapter(List<UserChat> users) {
        this.mUsers = users;
    }

    public void add(UserChat userChat) {
        mUsers.add(userChat);
        notifyItemInserted(mUsers.size() - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_user_listing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final UserChat userChat = mUsers.get(position);

        String alphabet;

        //  holder.txtLastMessage.setText(user.email);
        try {
            holder.txtUsername.setText(userChat.nameAndSurname);
            alphabet = userChat.nameAndSurname.substring(0, 1);
        } catch (Exception e) {
            try {


            holder.txtUsername.setText(userChat.email);
            alphabet = userChat.email.substring(0, 1);
        }catch (Exception e1){

            }
        }


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://erasmusapp-30e7b.appspot.com");
        storageRef.child("profilePictures/" + userChat.email + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                try {
                    Glide
                            .with(holder.imgViewUser.getContext())
                            .load("" + uri.toString())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
                            .into(holder.imgViewUser);
                } catch (Exception e) {

                }
            }
        });
        holder.imgViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.imgViewUser.getContext(), ProfileActivity.class);
                Bundle b = new Bundle();
                b.putString("key", userChat.email); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                holder.imgViewUser.getContext().startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        if (mUsers != null) {
            return mUsers.size();
        }
        return 0;
    }

    public UserChat getUser(int position) {
        return mUsers.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtUsername, txtLastMessage;
        private ImageView imgViewUser;

        ViewHolder(View itemView) {
            super(itemView);
            imgViewUser = (ImageView) itemView.findViewById(R.id.user_photo);
            txtUsername = (TextView) itemView.findViewById(R.id.text_view_username);
        }
    }


}
