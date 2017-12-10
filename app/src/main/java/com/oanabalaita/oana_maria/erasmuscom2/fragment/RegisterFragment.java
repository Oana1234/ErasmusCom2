package com.oanabalaita.oana_maria.erasmuscom2.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
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
import com.oanabalaita.oana_maria.erasmuscom2.core.registration.RegisterContract;
import com.oanabalaita.oana_maria.erasmuscom2.core.registration.RegisterPresenter;
import com.oanabalaita.oana_maria.erasmuscom2.core.users.add.AddUserContract;
import com.oanabalaita.oana_maria.erasmuscom2.core.users.add.AddUserPresenter;
import com.oanabalaita.oana_maria.erasmuscom2.serverclient.ServerRequest;
import com.oanabalaita.oana_maria.erasmuscom2.serverclient.ServerResponse;
import com.oanabalaita.oana_maria.erasmuscom2.serverclient.User;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import static com.oanabalaita.oana_maria.erasmuscom2.Variabile.TAG;

/**
 * Created by Oana-Maria on 23/04/2017.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener, RegisterContract.View, AddUserContract.View {

    private AppCompatButton btn_register;
    private EditText et_email, et_password, et_status;
    private TextView tv_login;
    private ProgressBar progress;
    private RegisterPresenter mRegisterPresenter;
    private AddUserPresenter mAddUserPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initViews(view);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }


    private void init() {
        mRegisterPresenter = new RegisterPresenter(this);
        mAddUserPresenter = new AddUserPresenter(this);
    }


    private void initViews(View view) {

        btn_register = (AppCompatButton) view.findViewById(R.id.btn_register);
        tv_login = (TextView) view.findViewById(R.id.tv_login);
        et_status = (EditText) view.findViewById(R.id.et_status);
        et_email = (EditText) view.findViewById(R.id.et_email);
        et_password = (EditText) view.findViewById(R.id.et_password);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        btn_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_login:
                goToLogin();
                break;

            case R.id.btn_register:

                String erasmus_status = et_status.getText().toString();
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();


                if (!email.isEmpty() && !password.isEmpty() && !erasmus_status.isEmpty()) {

                    progress.setVisibility(View.VISIBLE);
                  //  registerProcess(erasmus_status, email, password);
                    mRegisterPresenter.register(getActivity(), email, password);
                } else {

                    Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }

                break;

        }

    }


    @Override
    public void onRegistrationSuccess(FirebaseUser firebaseUser) {
        mAddUserPresenter.addUser(getActivity().getApplicationContext(), firebaseUser);
    }

    @Override
    public void onRegistrationFailure(String message) {
       // Log.e( TAG, "onRegistrationFailure: " + message);
        Toast.makeText(getActivity(), "Registration failed!+\n" + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAddUserSuccess(String message) {
       // Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), "User added! You can login now!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAddUserFailure(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void registerProcess(String status, String email, String password) {
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Variabile.getBaseUrl())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        CerereInterfata requestInterface = retrofit.create(CerereInterfata.class);

//        User user = new User();
//        user.setErasmus_status(status);
//        user.setEmail(email);
//        user.setPassword(password);
//        ServerRequest request = new ServerRequest();
//        request.setOperation(Variabile.getRegisterOperation());
//        request.setUser(user);
//        Call<ServerResponse> response = requestInterface.operation(request);
//
//        response.enqueue(new Callback<ServerResponse>() {
//            @Override
//            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
//
//                ServerResponse resp = response.body();
//                progress.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onFailure(Call<ServerResponse> call, Throwable t) {
//
//                progress.setVisibility(View.INVISIBLE);
//                Log.d(TAG, "failed");
//
            }
//        });
//    }

    private void goToLogin() {

        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_fragment, login);
        ft.commit();
    }
}
