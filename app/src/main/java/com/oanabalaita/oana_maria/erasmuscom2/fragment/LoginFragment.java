package com.oanabalaita.oana_maria.erasmuscom2.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.oanabalaita.oana_maria.erasmuscom2.CerereInterfata;
import com.oanabalaita.oana_maria.erasmuscom2.R;
import com.oanabalaita.oana_maria.erasmuscom2.Variabile;
import com.oanabalaita.oana_maria.erasmuscom2.core.login.LoginContract;
import com.oanabalaita.oana_maria.erasmuscom2.core.login.LoginPresenter;
import com.oanabalaita.oana_maria.erasmuscom2.serverclient.ServerRequest;
import com.oanabalaita.oana_maria.erasmuscom2.serverclient.ServerResponse;
import com.oanabalaita.oana_maria.erasmuscom2.serverclient.User;
import com.oanabalaita.oana_maria.erasmuscom2.ui.activities.PersonalProfileActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Oana-Maria on 23/04/2017.
 */

public class LoginFragment extends Fragment implements View.OnClickListener, LoginContract.View {

    private AppCompatButton btn_login;
    private EditText et_email, et_password;
    private TextView tv_register;
    private ProgressBar progress;
    private SharedPreferences pref;
    private LoginPresenter mLoginPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        pref = getActivity().getPreferences(0);

        btn_login = (AppCompatButton) view.findViewById(R.id.btn_login);
        tv_register = (TextView) view.findViewById(R.id.tv_register);
        et_email = (EditText) view.findViewById(R.id.et_email);
        et_password = (EditText) view.findViewById(R.id.et_password);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        btn_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        mLoginPresenter = new LoginPresenter(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_register:
                goToRegister();
                break;

            case R.id.btn_login:
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {

                    progress.setVisibility(View.VISIBLE);
                    //loginProcess(email, password);

                } else {

                    Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }

                mLoginPresenter.login(getActivity(), email, password);
                break;

        }
    }

//    private void loginProcess(String email, String password) {
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
//        user.setPassword(password);
//        ServerRequest request = new ServerRequest();
//        request.setOperation(Variabile.getLoginOperation());
//        request.setUser(user);
//        Call<ServerResponse> response = requestInterface.operation(request);
//
//        response.enqueue(new Callback<ServerResponse>() {
//            @Override
//            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
//
//                ServerResponse resp = response.body();
//                try {
//
//
//                    if (resp.getResult().equals(Variabile.getSUCCESS())) {
//                        SharedPreferences.Editor editor = pref.edit();
//                        editor.putBoolean(Variabile.getIsLoggedIn(), true);
//                        editor.putString(Variabile.getEMAIL(), resp.getUser().getEmail());
//                        editor.putString(Variabile.getSTATUS(), resp.getUser().getName());
//                        editor.putString(Variabile.getUniqueId(), resp.getUser().getUnique_id());
//                        editor.apply();
//
//                    }
//                } catch (Exception e) {
//
//                }
//                progress.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onFailure(Call<ServerResponse> call, Throwable t) {
//
//                progress.setVisibility(View.INVISIBLE);
//                Log.d(Variabile.getTAG(), "failed");
//
//            }
//        });
//    }


    private void goToRegister() {

        Fragment register = new RegisterFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_fragment, register);
        ft.commit();
    }


    @Override
    public void onLoginSuccess(String message) {
//        Toast.makeText(getActivity(), "Logged in successfully", Toast.LENGTH_SHORT).show();
        PersonalProfileActivity.startActivity(getActivity());
    }

    @Override
    public void onLoginFailure(String message) {
        Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }


}