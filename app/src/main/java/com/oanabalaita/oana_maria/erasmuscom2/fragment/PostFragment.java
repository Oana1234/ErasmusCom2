package com.oanabalaita.oana_maria.erasmuscom2.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.oanabalaita.oana_maria.erasmuscom2.R;
import com.oanabalaita.oana_maria.erasmuscom2.models.Post;
import com.oanabalaita.oana_maria.erasmuscom2.ui.activities.CommunityNewsActivity;
import com.oanabalaita.oana_maria.erasmuscom2.utils.Utility;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class PostFragment extends Fragment implements View.OnClickListener {

    private static int i;
    Post tempPost;
    AppCompatButton butonPost;
    TextView textView;
    ImageView imageUpload;
    EditText editDescription, editLocation;
    int PICK_IMAGE_REQUEST = 111;
    Uri filePath;
    ProgressDialog pd;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;


    public static PostFragment newInstance() {
        Bundle args = new Bundle();
        PostFragment fragment = new PostFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_post, container, false);

        butonPost = (AppCompatButton) view.findViewById(R.id.button_post);
        textView = (TextView) view.findViewById(R.id.text_titlu);
        imageUpload = (ImageView) view.findViewById(R.id.imageUploadPost);
        editDescription = (EditText) view.findViewById(R.id.edit_text_description);
        editLocation = (EditText) view.findViewById(R.id.edit_location);

        tempPost = new Post();
        return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        butonPost.setOnClickListener(this);
        imageUpload.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        switch (viewId) {
            case R.id.button_post:

                String postId = submitPost();
                try {
                    savePicFirebase(postId, ((BitmapDrawable) imageUpload.getDrawable()).getBitmap());
                } catch (Exception e) {
                }

                Intent i = new Intent(getActivity(), CommunityNewsActivity.class);
                startActivity(i);
                break;


            case R.id.imageUploadPost:
                uploadImage();
                break;
        }

    }

    private void uploadImage() {

        final CharSequence[] items = {"Take Photo", "Choose from Gallery",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity().getWindow().getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(getActivity());
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Gallery")) {
                    userChoosenTask = "Choose from Gallery";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();


    }


    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }


    private String submitPost() {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference newRef = mDatabase.child("posts").push();
        newRef.child("location").setValue(editLocation.getText().toString());
        newRef.child("description").setValue(editDescription.getText().toString());
        newRef.child("postUid").setValue(newRef.getKey());


        DatabaseReference currentUser = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        currentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newRef.child("postOwnerUid").setValue(dataSnapshot.child("uid").getValue());
                newRef.child("postOwnerEmail").setValue(dataSnapshot.child("email").getValue());
                try {


                    newRef.child("postOwner").setValue(dataSnapshot.child("nameAndSurname").getValue());


                } catch (Exception e) {
                    try {
                        newRef.child("postOwner").setValue(dataSnapshot.child("email").getValue());
                    } catch (Exception e2) {
                        newRef.child("postOwner").setValue("Undefined User");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        newRef.child("date").setValue(dateFormat.format(cal.getTime()));

        return newRef.getKey();
    }


    private void savePicFirebase(String postId, final Bitmap bitmap) {


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://erasmusapp-30e7b.appspot.com");
        StorageReference mountainImagesRef = storageRef.child("postpictures/" + postId + ".jpg");


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("posts").child(postId);

        mDatabase.child("imgUrl").setValue(postId + ".jpg");


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap converetdImage = getResizedBitmap(bitmap, 430);
        converetdImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                //     @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                //  sendMsg("" + downloadUrl, 2);
                //  Log.d("downloadUrl-->", "" + downloadUrl);
                //    tempUrl=downloadUrl.toString();
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Gallery"))
                        galleryIntent();
                } else {
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imageUpload.setImageBitmap(bm);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUpload.setImageBitmap(thumbnail);

    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}