package com.oanabalaita.oana_maria.erasmuscom2;

import android.app.Fragment;
import android.app.FragmentTransaction;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.oanabalaita.oana_maria.erasmuscom2.fragment.LoginFragment;

public class PreMainActivity extends AppCompatActivity {

    private SharedPreferences pref;


    public static void startIntent(Context context) {
        Intent intent = new Intent(context, PreMainActivity.class);
        context.startActivity(intent);
    }


    public static void startIntent(Context context, int flags) {
        Intent intent = new Intent(context, PreMainActivity.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_main);
        pref = getPreferences(0);
        initFragment();
    }

    private void initFragment() {


        if (!(pref.getBoolean(Variabile.getIsLoggedIn(), false))) {


            Fragment fragment;
            fragment = new LoginFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.frame_fragment, fragment);
            ft.addToBackStack(null);
            ft.commit();

        } else if((pref.getBoolean(Variabile.getIsLoggedIn(), false))) {
            Intent intent = new Intent(PreMainActivity.this, MainActivity.class);
            startActivity(intent);


        }


    }

}

