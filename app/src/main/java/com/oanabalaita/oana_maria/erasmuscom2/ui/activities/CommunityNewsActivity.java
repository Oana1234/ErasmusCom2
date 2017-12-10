package com.oanabalaita.oana_maria.erasmuscom2.ui.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.oanabalaita.oana_maria.erasmuscom2.MainActivity;
import com.oanabalaita.oana_maria.erasmuscom2.R;
import com.oanabalaita.oana_maria.erasmuscom2.models.Post;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class CommunityNewsActivity extends AppCompatActivity {



    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_news);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fabmain);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityNewsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView postRecycler = (RecyclerView) findViewById(R.id.recycler_view_all_posts);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("posts");



        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        postRecycler.setLayoutManager(layoutManager);


        FirebaseRecyclerAdapter mAdapter = new FirebaseRecyclerAdapter<Post, PostHolder>(
                Post.class,
                R.layout.item_post,
                PostHolder.class,
                ref) {



            @Override
            protected void populateViewHolder(final PostHolder viewHolder, final Post model, int position) {

                viewHolder.txtDescription.setText(model.getDescription());//+
                viewHolder.txtLocation.setText(model.getLocation());//+
                viewHolder.txtTimeStamp.setText(model.date);//+
                viewHolder.txtName.setText(model.postOwner);//+


                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReferenceFromUrl("gs://erasmusapp-30e7b.appspot.com");
                storageRef.child("profilePictures/" + model.getPostOwnerEmail() + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        try {
                            Glide
                                    .with(viewHolder.imgProfile.getContext())
                                    .load("" + uri.toString())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
                                    .centerCrop()
                                    .crossFade()
                                    .into(viewHolder.imgProfile);
                        } catch (Exception e) {

                        }
                    }
                });


                storageRef.child("postpictures/" +model.getPostUid() + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {


                        try {
                            Glide
                                    .with(viewHolder.imgPicture.getContext())
                                    .load("" + uri.toString())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
                                    .centerCrop()
                                    .crossFade()
                                    .into(viewHolder.imgPicture);
                        } catch (Exception e) {

                        }
                    }
                });


            }
        };

        postRecycler.setAdapter(mAdapter);

    }

    private static class PostHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtTimeStamp;
        private TextView txtLocation;
        private TextView txtDescription;
        private ImageView imgProfile;
        private ImageView imgPicture;

        public PostHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.name_poster);
            txtTimeStamp = (TextView) itemView.findViewById(R.id.date);
            txtLocation = (TextView) itemView.findViewById(R.id.txtLocation);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            imgPicture = (ImageView) itemView.findViewById(R.id.feedImage);
            imgProfile = (ImageView) itemView.findViewById(R.id.item_image);
        }

    }
}
