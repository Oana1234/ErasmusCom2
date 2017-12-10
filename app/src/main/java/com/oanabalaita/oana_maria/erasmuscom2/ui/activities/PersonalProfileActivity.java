package com.oanabalaita.oana_maria.erasmuscom2.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.oanabalaita.oana_maria.erasmuscom2.R;
import com.oanabalaita.oana_maria.erasmuscom2.ui.fragments.PersonalProfileFragment;

public class PersonalProfileActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, PersonalProfileActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile);
        bindViews();
        init();
    }

    private void bindViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void init() {
        // set the toolbar
        setSupportActionBar(mToolbar);

        // set the register screen fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_content_settings,
                PersonalProfileFragment.newInstance(),
                PersonalProfileFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }
}
