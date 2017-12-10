package com.oanabalaita.oana_maria.erasmuscom2.ui.fragments;


import android.app.Activity;
import android.app.AlertDialog;
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
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.oanabalaita.oana_maria.erasmuscom2.CerereInterfata;
import com.oanabalaita.oana_maria.erasmuscom2.MainActivity;
import com.oanabalaita.oana_maria.erasmuscom2.R;
import com.oanabalaita.oana_maria.erasmuscom2.Variabile;
import com.oanabalaita.oana_maria.erasmuscom2.serverclient.ServerRequest;
import com.oanabalaita.oana_maria.erasmuscom2.serverclient.ServerResponse;
import com.oanabalaita.oana_maria.erasmuscom2.serverclient.User;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonalProfileFragment extends Fragment implements View.OnClickListener {


    private static final String TAG = PersonalProfileFragment.class.getSimpleName();

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    private AppCompatButton mBtnSave, mBtnCancel;
    private EditText mETxtUsername, mETxtCity, mETxtCountry, mETxtUniversity;
    private ImageView ivImage;

    //private SharedPreferences pref=getActivity().getPreferences(Context.MODE_PRIVATE);


    public static PersonalProfileFragment newInstance() {
        Bundle args = new Bundle();
        PersonalProfileFragment fragment = new PersonalProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_personal_profile, container, false);
        bindViews(fragmentView);
        return fragmentView;
        // Toast.makeText(getActivity(),,Toast.LENGTH_SHORT).show();
    }

    private void bindViews(View view) {

        mBtnCancel = (AppCompatButton) view.findViewById(R.id.button_cancel);
        mBtnSave = (AppCompatButton) view.findViewById(R.id.button_save);
        mETxtUsername = (EditText) view.findViewById(R.id.edit_text_nameAndSurname);
        mETxtCountry = (EditText) view.findViewById(R.id.edit_text_country);
        mETxtCity = (EditText) view.findViewById(R.id.edit_text_city);
        mETxtUniversity = (EditText) view.findViewById(R.id.edit_text_university);
        ivImage = (ImageView) view.findViewById(R.id.imageViewPicture);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

    }

    private void init() {

        mBtnCancel.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
        ivImage.setOnClickListener(this);
        initProfile();

    }

    private void initProfile() {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        mDatabase.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    try {
                        mETxtUsername.setText(snapshot.child("nameAndSurname").getValue().toString());
                        mETxtCity.setText(snapshot.child("erasmusCity").getValue().toString());
                        mETxtCountry.setText(snapshot.child("erasmusCountry").getValue().toString());
                        mETxtUniversity.setText(snapshot.child("erasmusUniversity").getValue().toString());
                    } catch (Exception e) {
                        Log.d(getTag(), "Fields uncompleted");
                    }
//                    try {
//                        mETxtStatus.setText(snapshot.child("status").getValue().toString());
//                    } catch (Exception e) {
//                        Log.d(getTag(), "No status defined");
//                    }
                }

                try {


                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReferenceFromUrl("gs://erasmusapp-30e7b.appspot.com");
                    storageRef.child("profilePictures/" + snapshot.child("email").getValue().toString() + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            try {
                                Glide
                                        .with(getActivity())
                                        .load("" + uri.toString())
                                        .diskCacheStrategy(DiskCacheStrategy.ALL) //use this to cache
                                        .into(ivImage);
                            } catch (Exception e) {
                                Log.d(getTag(), "Connection too slow before download picture, picture saved");
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.d(getTag(), "no profile picture");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        switch (viewId) {
            case R.id.button_cancel:
                MainActivity.startActivity(getActivity());
                break;
            case R.id.button_save:

                String city = mETxtCity.getText().toString();
                String country = mETxtCountry.getText().toString();
                String university = mETxtUniversity.getText().toString();
                String name = mETxtUsername.getText().toString();


          //      addInfos(Variabile.getEMAIL(), name, country, city, university);

                saveSettings();

                MainActivity.startActivity(getActivity());
                break;

            case R.id.imageViewPicture:
                addPhoto();
                break;
        }
    }

//    private void addInfos(String email, String name, String country, String city, String university) {
//
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Variabile.getBaseUrl())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        CerereInterfata requestInterface = retrofit.create(CerereInterfata.class);
//
//        User user = new User();
//        user.setEmail(email);
//        user.setName(name);
//        user.setCity(city);
//        user.setCountry(country);
//        user.setFaculty(university);
//        ServerRequest request = new ServerRequest();
//        request.setOperation(Variabile.getAddInformationsOperation());
//        request.setUser(user);
//        Call<ServerResponse> response = requestInterface.operation(request);
//
//        response.enqueue(new Callback<ServerResponse>() {
//            @Override
//            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
//
//                ServerResponse resp = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<ServerResponse> call, Throwable t) {
//
//                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//    }

    private void addPhoto() {


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

    private void saveSettings() {
        try {
            saveUsername();
            saveErasmusCity();
            saveErasmusCountry();
            saveErasmusUniversity();
            //      saveStatus();
            saveProfilePicture(((BitmapDrawable) ivImage.getDrawable()).getBitmap());
        } catch (Exception e) {
        }
    }


    private void saveProfilePicture(final Bitmap bitmap) {


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        final String[] email = new String[1];
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    email[0] = snapshot.child("email").getValue().toString();


                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReferenceFromUrl("gs://erasmusapp-30e7b.appspot.com");

                    StorageReference mountainImagesRef = storageRef.child("profilePictures/" + email[0] + ".jpg");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    Bitmap converetdImage = getResizedBitmap(bitmap, 480);
                    converetdImage.compress(Bitmap.CompressFormat.JPEG, 70, baos);

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

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }


    private void saveUsername() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("nameAndSurname");
        mDatabase.setValue(mETxtUsername.getText().toString());

    }

    private void saveErasmusCity(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("erasmusCity");
        mDatabase.setValue(mETxtCity.getText().toString());

    }

    private void saveErasmusUniversity(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("erasmusCountry");
        mDatabase.setValue(mETxtCountry.getText().toString());
    }

    private void saveErasmusCountry(){

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("erasmusUniversity");
        mDatabase.setValue(mETxtUniversity.getText().toString());

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
        ivImage.setImageBitmap(bm);
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
        ivImage.setImageBitmap(thumbnail);

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
