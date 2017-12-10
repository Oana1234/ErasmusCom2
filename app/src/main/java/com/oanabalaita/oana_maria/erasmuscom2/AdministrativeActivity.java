package com.oanabalaita.oana_maria.erasmuscom2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.oanabalaita.oana_maria.erasmuscom2.models.Tip;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdministrativeActivity extends AppCompatActivity implements View.OnClickListener{


    Button butonAdd,butonBack,butonChooseCity;
    Tip tipnew;
    TextView text1;
    // private static int nrTipsMoneyGeneral = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrative);

        butonAdd = (Button) findViewById(R.id.add_tip);
        butonBack = (Button) findViewById(R.id.buton_back_categ);
        butonChooseCity = (Button) findViewById(R.id.buttonChooseCity);
        text1 = (TextView) findViewById(R.id.textForSp);

        butonAdd.setOnClickListener(this);
        butonBack.setOnClickListener(this);
        butonChooseCity.setOnClickListener(this);



        RecyclerView tipRecycler = (RecyclerView) findViewById(R.id.recycler_view_all_tips);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tips_adm");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        tipRecycler.setLayoutManager(layoutManager);


        FirebaseRecyclerAdapter mAdapter = new FirebaseRecyclerAdapter<Tip, AdministrativeActivity.TipHolder>(
                Tip.class,
                R.layout.item_money_general,
                AdministrativeActivity.TipHolder.class,
                reference) {



            @Override
            public void populateViewHolder(final AdministrativeActivity.TipHolder viewHolder, final Tip tipmodel, int position) {

                viewHolder.txtNumberPost.setText(tipmodel.getNumberTip());
                viewHolder.tipDescription.setText(tipmodel.getTipDescription());
                viewHolder.txtNamePoster.setText("by " + tipmodel.tipOwner);
                viewHolder.txtCountryPost.setText(tipmodel.getCountryTip());
            }
        };

        tipRecycler.setAdapter(mAdapter);

        tipnew= new Tip();
    }

    private static class TipHolder extends RecyclerView.ViewHolder {
        private TextView txtNumberPost;
        private TextView tipDescription;
        private TextView txtNamePoster;
        private TextView txtCountryPost;

        public TipHolder(View itemView) {
            super(itemView);
            txtNumberPost = (TextView) itemView.findViewById(R.id.number_post_tip);
            tipDescription = (TextView) itemView.findViewById(R.id.tip);
            txtNamePoster = (TextView) itemView.findViewById(R.id.name_poster_tip);
            txtCountryPost = (TextView) itemView.findViewById(R.id.tip_country);

        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.add_tip:

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
                LayoutInflater inflater = this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_alert_edit, null);
                dialogBuilder.setView(dialogView);

                final EditText edt = (EditText) dialogView.findViewById(R.id.editTip);

                dialogBuilder.setTitle("ADD TIP \n");
                dialogBuilder.setMessage("Enter your advice in text below:");
                dialogBuilder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // edt.getText().toString();

                        Intent ipass = getIntent();
                        String nameCountry = ipass.getStringExtra("nameCountry");


                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                        final DatabaseReference newRef = mDatabase.child("tips_adm").push();
                        newRef.child("numberTip").setValue("-> ");
                        //  nrTipsMoneyGeneral++;

                        newRef.child("tipDescription").setValue(edt.getText().toString());
                        newRef.child("countryTip").setValue(nameCountry);

                        newRef.child("tipUid").setValue(newRef.getKey());


                        DatabaseReference currentUser = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                        currentUser.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                newRef.child("tipOwnerUid").setValue(dataSnapshot.child("uid").getValue());
                                newRef.child("tipOwnerEmail").setValue(dataSnapshot.child("email").getValue());
                                try {


                                    newRef.child("tipOwner").setValue(dataSnapshot.child("nameAndSurname").getValue());


                                } catch (Exception e) {
                                    try {
                                        newRef.child("tipOwner").setValue(dataSnapshot.child("email").getValue());
                                    } catch (Exception e2) {
                                        newRef.child("tipOwner").setValue("Undefined User");
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        Toast.makeText(AdministrativeActivity.this,"Thank you for your contribution!", Toast.LENGTH_SHORT).show();


                        dialog.dismiss();
                    }
                });
                dialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                AlertDialog b = dialogBuilder.create();
                b.show();
                break;
            case R.id.buton_back_categ:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
            case R.id.buttonChooseCity:
                Toast.makeText(AdministrativeActivity.this, "AVAILABLE IN VERSION 2.0. COMMING SOON!",Toast.LENGTH_LONG).show();
//                 i = new Intent(this, MoneyPerCityActivity.class);
//                startActivity(i);
                break;

            default: break;
        }

    }
}


