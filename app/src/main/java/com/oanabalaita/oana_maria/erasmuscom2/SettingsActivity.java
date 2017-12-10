package com.oanabalaita.oana_maria.erasmuscom2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.oanabalaita.oana_maria.erasmuscom2.serverclient.ServerRequest;
import com.oanabalaita.oana_maria.erasmuscom2.serverclient.ServerResponse;
import com.oanabalaita.oana_maria.erasmuscom2.serverclient.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingsActivity extends AppCompatActivity {


    private List<Setting> settingsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SettingsAdapter sAdapter;
    private TextView tv_message;
    private EditText et_old_password, et_new_password;
    private AlertDialog dialog;
    private ProgressBar progress;
    private  SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        pref = this.getPreferences(0);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_settings);

        sAdapter = new SettingsAdapter(settingsList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(sAdapter);}}
//
//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Setting setting = settingsList.get(position);
//                if (setting.getDescription() == "Change Password") {
//
//                    showDialog();
//                }
////                Toast.makeText(getApplicationContext(), setting.getTitle() + " !", Toast.LENGTH_SHORT).show();
//            }

//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
//
//        Setting setting = new Setting("SECURITY", "Change Password");
//        settingsList.add(setting);
//
//        Setting setting2 = new Setting("PERSONAL INFO", "Change Status");
//        settingsList.add(setting2);
//
//        sAdapter.notifyDataSetChanged();
//    }


    //    private TextView txtChange,tv_message;
//    private EditText et_old_password,et_new_password;
//    private AlertDialog dialog;
//    private ProgressBar progress;
//    private static SharedPreferences pref;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_settings);
//
//        pref = getPreferences(0);
//
//
//        txtChange=(TextView)findViewById(R.id.changepass);
//        txtChange.setOnClickListener(this);
//
//
//    }
//
//
//    private void showDialog() {
//
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
//        LayoutInflater inflater = this.getLayoutInflater();
//        View view = inflater.inflate(R.layout.change_password, null);
//
//        et_old_password = (EditText) view.findViewById(R.id.et_old_password);
//        et_new_password = (EditText) view.findViewById(R.id.et_new_password);
//        tv_message = (TextView) view.findViewById(R.id.tv_message);
//        progress = (ProgressBar) view.findViewById(R.id.progress);
//
//        builder.setView(view);
//        builder.setTitle("Change Password");
//        builder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//
//
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//

//        dialog = builder.create();
//        dialog.show();
//        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String old_password = et_old_password.getText().toString();
//                String new_password = et_new_password.getText().toString();
//                if (!old_password.isEmpty() && !new_password.isEmpty()) {
//
//                    progress.setVisibility(View.VISIBLE);
//                    changePasswordProcess(pref.getString(Variabile.EMAIL,""), old_password, new_password);
//
//                } else {
//
//                    tv_message.setVisibility(View.VISIBLE);
//                    tv_message.setText("Fields are empty");
//                }
//            }
//        });
//    }

//
//    private void changePasswordProcess(String email, String old_password, String new_password) {
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
//        user.setOld_password(old_password);
//        user.setNew_password(new_password);
//        ServerRequest request = new ServerRequest();
     //   request.setOperation(Variabile.getChangePasswordOperation());
//        request.setUser(user);
//        Call<ServerResponse> response = requestInterface.operation(request);

//        response.enqueue(new Callback<ServerResponse>() {
//            @Override
//            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
//
//                ServerResponse resp = response.body();
//                if (resp.getResult().equals(Variabile.getSUCCESS())) {
//                    progress.setVisibility(View.GONE);
//                    tv_message.setVisibility(View.GONE);
//                    dialog.dismiss();
//                    //      Snackbar.make( view, resp.getMessage(), Snackbar.LENGTH_LONG).show();
//
//                } else {
//                    progress.setVisibility(View.GONE);
//                    tv_message.setVisibility(View.VISIBLE);
//                    tv_message.setText(resp.getMessage());
//
//                }
//            }

//            @Override
//            public void onFailure(Call<ServerResponse> call, Throwable t) {
//
//                Log.d(Variabile.getTAG(), "failed");
//                progress.setVisibility(View.GONE);
//                tv_message.setVisibility(View.VISIBLE);
//                tv_message.setText(t.getLocalizedMessage());
//
//            }
//        });
//    }
