package com.oanabalaita.oana_maria.erasmuscom2.fragment;

import android.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oanabalaita.oana_maria.erasmuscom2.R;
import com.oanabalaita.oana_maria.erasmuscom2.ui.activities.HomeMessagingActivity;


/**
 * Created by Oana-Maria on 23/04/2017.
 */

public class SocialFragment extends Fragment {



    private Button butonMesagerie, butonGrupuri;
    private TextView titlu;
    private ImageView imagine1, imagine2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_social, container, false);
        butonMesagerie = (Button) view.findViewById(R.id.butonMesagerie);
        butonGrupuri = (Button) view.findViewById(R.id.butonGrupuri);
        titlu = (TextView) view.findViewById(R.id.text_titlu);
        imagine1 = (ImageView) view.findViewById(R.id.imageMesagerie);
        imagine2 = (ImageView) view.findViewById(R.id.imageGroups);

        butonMesagerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeMessagingActivity.class);
                startActivity(intent);
            }

        });

        butonGrupuri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "AVAILABLE IN VERSION 2.0. COMMING SOON!", Toast.LENGTH_SHORT).show();
            }

        });
        return view;

    }
}