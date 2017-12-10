package com.oanabalaita.oana_maria.erasmuscom2.ui.adapters;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.oanabalaita.oana_maria.erasmuscom2.R;
import com.oanabalaita.oana_maria.erasmuscom2.models.UserChat;
import com.oanabalaita.oana_maria.erasmuscom2.ui.activities.ProfileActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by Oana-Maria on 05/06/2017.
 */

public class NotificationRecyclerAdapter extends RecyclerView.Adapter<NotificationRecyclerAdapter.ViewHolder> {

    private List<UserChat> mUsers;


    public NotificationRecyclerAdapter(List<UserChat> users) {
        this.mUsers = users;
    }

    public void add(UserChat userChat) {
        mUsers.add(userChat);
        notifyItemInserted(mUsers.size() - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_friend_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NotificationRecyclerAdapter.ViewHolder holder, final int position) {
        final UserChat userChat = mUsers.get(position);
        holder.mBttnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitNotificationAnswer("accepted",mUsers.get(position).uid);
                mUsers.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mUsers.size());


            }
        });
        holder.mBttnIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitNotificationAnswer("ignored",mUsers.get(position).uid);
                mUsers.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mUsers.size());
            }
        });
        holder.mBttnBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitNotificationAnswer("blocked",mUsers.get(position).uid);
                mUsers.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mUsers.size());

            }
        });


        String alphabet;

        try {
            holder.txtUsername.setText(userChat.nameAndSurname);
            alphabet = userChat.nameAndSurname.substring(0, 1);
        } catch (Exception e) {
            holder.txtUsername.setText(userChat.email);
            alphabet = userChat.email.substring(0, 1);
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

    private void SubmitNotificationAnswer(String answer, String uid) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mDatabase.child("friendRequest").child(uid).setValue(answer);
        DatabaseReference otherOne=FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("friends");
        if(answer.equals("accepted")){

            mDatabase.child("friends").child(uid).setValue(true);
            otherOne.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(true);




        }else {
            mDatabase.child("friends").child(uid).setValue(false);
            otherOne.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(false);
        }




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

        private TextView txtUsername, txtRequest;
        private ImageView imgViewUser;
        private Button mBttnAccept, mBttnIgnore, mBttnBlock;

        ViewHolder(View itemView) {
            super(itemView);
            imgViewUser = (ImageView) itemView.findViewById(R.id.user_photo);
            txtUsername = (TextView) itemView.findViewById(R.id.text_view_username);
            mBttnAccept = (Button) itemView.findViewById(R.id.button_accept);
            mBttnIgnore = (Button) itemView.findViewById(R.id.button_ignore);
            mBttnBlock = (Button) itemView.findViewById(R.id.button_block);
            txtRequest = (TextView) itemView.findViewById(R.id.text_view_friend_request);
        }
    }

}
