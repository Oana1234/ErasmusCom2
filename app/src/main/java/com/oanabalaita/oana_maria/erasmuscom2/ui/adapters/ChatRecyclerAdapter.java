package com.oanabalaita.oana_maria.erasmuscom2.ui.adapters;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.oanabalaita.oana_maria.erasmuscom2.R;
import com.oanabalaita.oana_maria.erasmuscom2.models.Chat;
import com.oanabalaita.oana_maria.erasmuscom2.ui.activities.ProfileActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by Oana-Maria on 05/06/2017.
 */

public class ChatRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ME = 1;
    private static final int VIEW_TYPE_OTHER = 2;

    private List<Chat> mChats;

    public ChatRecyclerAdapter(List<Chat> chats) {
        mChats = chats;
    }

    public void add(Chat chat) {
        mChats.add(chat);
        notifyItemInserted(mChats.size() - 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case VIEW_TYPE_ME:
                View viewChatMine = layoutInflater.inflate(R.layout.item_chat_mine, parent, false);
                viewHolder = new MyChatViewHolder(viewChatMine);

                break;
            case VIEW_TYPE_OTHER:
                View viewChatOther = layoutInflater.inflate(R.layout.item_chat_other, parent, false);
                viewHolder = new OtherChatViewHolder(viewChatOther);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (TextUtils.equals(mChats.get(position).senderUid,
                FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            configureMyChatViewHolder((MyChatViewHolder) holder, position);
        } else {
            configureOtherChatViewHolder((OtherChatViewHolder) holder, position);
        }
    }

    private void configureMyChatViewHolder(final MyChatViewHolder myChatViewHolder, int position) {
        final Chat chat = mChats.get(position);

        //String alphabet = chat.sender.substring(0, 1);

        myChatViewHolder.txtChatMessage.setText(chat.message);


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://erasmusapp-30e7b.appspot.com");
        storageRef.child("profilePictures/" + chat.sender + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                try {
                    Glide
                            .with(myChatViewHolder.imgViewUser.getContext())
                            .load("" + uri.toString())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
                            .into(myChatViewHolder.imgViewUser);
                } catch (Exception e) {

                }
            }
        });

        myChatViewHolder.imgViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myChatViewHolder.imgViewUser.getContext(), ProfileActivity.class);
                Bundle b = new Bundle();
                b.putString("key", chat.sender); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                myChatViewHolder.imgViewUser.getContext().startActivity(intent);

            }
        });


        //   myChatViewHolder.imgViewUser.setText(alphabet);
    }

    private void configureOtherChatViewHolder(final OtherChatViewHolder otherChatViewHolder, int position) {
        final Chat chat = mChats.get(position);


        otherChatViewHolder.txtChatMessage.setText(chat.message);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://erasmusapp-30e7b.appspot.com");
        storageRef.child("profilePictures/" + chat.sender + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                try {
                    Glide
                            .with(otherChatViewHolder.imgViewUser.getContext())
                            .load("" + uri.toString())
                            .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
                            .into(otherChatViewHolder.imgViewUser);
                } catch (Exception e) {

                }
            }
        });
        otherChatViewHolder.imgViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(otherChatViewHolder.imgViewUser.getContext(), ProfileActivity.class);
                Bundle b = new Bundle();
                b.putString("key", chat.sender); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                otherChatViewHolder.imgViewUser.getContext().startActivity(intent);

            }
        });
        //otherChatViewHolder.imgViewUser.setText(alphabet);
    }

    @Override
    public int getItemCount() {
        if (mChats != null) {
            return mChats.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (TextUtils.equals(mChats.get(position).senderUid,
                FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            return VIEW_TYPE_ME;
        } else {
            return VIEW_TYPE_OTHER;
        }
    }


    private static class MyChatViewHolder extends RecyclerView.ViewHolder {
        private TextView txtChatMessage;
        private ImageView imgViewUser;

        public MyChatViewHolder(View itemView) {
            super(itemView);
            txtChatMessage = (TextView) itemView.findViewById(R.id.text_view_chat_message);
            imgViewUser = (ImageView) itemView.findViewById(R.id.text_view_user_alphabet);
        }
    }

    private static class OtherChatViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgViewUser;
        private TextView txtChatMessage;

        public OtherChatViewHolder(View itemView) {
            super(itemView);
            txtChatMessage = (TextView) itemView.findViewById(R.id.text_view_chat_message);
            imgViewUser = (ImageView) itemView.findViewById(R.id.text_view_user_alphabet);
        }
    }
}
