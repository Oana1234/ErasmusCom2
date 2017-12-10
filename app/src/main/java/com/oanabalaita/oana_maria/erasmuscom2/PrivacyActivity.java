package com.oanabalaita.oana_maria.erasmuscom2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PrivacyActivity extends AppCompatActivity {

    TextView text1, text2, text3, text4, text5, text6, text7, text8, text9, text10, text11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        text1 = (TextView) findViewById(R.id.textTitlePriv);
        text2 = (TextView) findViewById(R.id.title_adv);
        text3 = (TextView) findViewById(R.id.content1adv);
        text4 = (TextView) findViewById(R.id.content2adv);
        text5 = (TextView) findViewById(R.id.title_pers);
        text6 = (TextView) findViewById(R.id.content_pers);
        text7 = (TextView) findViewById(R.id.title_changes);
        text8 = (TextView) findViewById(R.id.content_changes);
        text9 = (TextView) findViewById(R.id.title_contact);
        text10 = (TextView) findViewById(R.id.name_contact);
        text11 = (TextView) findViewById(R.id.email_contact);

    }
}
