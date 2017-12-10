package com.oanabalaita.oana_maria.erasmuscom2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Oana-Maria on 15/07/2017.
 */

  public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder>{

    private Context mContext;
    private List<Category> categoryList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titlecateg;
        public ImageView imageCat;

        public MyViewHolder(View view) {
            super(view);
            titlecateg = (TextView) view.findViewById(R.id.title_categ);
            imageCat = (ImageView) view.findViewById(R.id.image_categori);


        }
    }

        public CategoriesAdapter(Context mContext, List<Category> categoryList) {
            this.mContext = mContext;
            this.categoryList = categoryList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_card, parent, false);



            return new MyViewHolder(itemView);
        }





    @Override
    public void onBindViewHolder(final CategoriesAdapter.MyViewHolder holder, int position) {

        final Category category = categoryList.get(position);
        holder.titlecateg.setText(category.getName());

        Glide.with(mContext).load(category.getPic()).into(holder.imageCat);

        holder.imageCat.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {


                                                   AlertDialog alertDialog = new AlertDialog.Builder(mContext, R.style.MyAlertDialogStyle).create();

                                                   LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                                                   View cView = inflater.inflate(R.layout.dialog_spinner, null);

                                                   alertDialog.setTitle("PICK THE COUNTRY");

                                                   final Spinner mSpinner = (Spinner) cView.findViewById(R.id.spinner_countries);
                                                   ArrayAdapter<String> cAdapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item,
                                                           mContext.getResources().getStringArray(R.array.countries_list));
                                                   cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                   mSpinner.setAdapter(cAdapter);


                                                   alertDialog.setMessage("");
                                                   alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "CONTINUE",
                                                           new DialogInterface.OnClickListener() {
                                                               public void onClick(DialogInterface dialog, int which) {


                                                                   if(!mSpinner.getSelectedItem().toString().equalsIgnoreCase("Choose a country…")){





                                                                                       if (category.getName() == "EDUCATION") {

                                                                                           Intent i = new Intent(mContext, EducationActivity.class);
                                                                                           i.putExtra("nameCountry",  mSpinner.getSelectedItem().toString());

                                                                                           mContext.startActivity(i);
                                                                                          }

                                                                                       else {

                                                                                           if (category.getName() == "ADMINISTRATIVE") {

                                                                                               Intent i = new Intent(mContext, AdministrativeActivity.class);
                                                                                               i.putExtra("nameCountry",  mSpinner.getSelectedItem().toString());

                                                                                               mContext.startActivity(i);
                                                                                           }

                                                                                           else {

                                                                                               if (category.getName() == "SOCIAL") {

                                                                                                   Intent i = new Intent(mContext, SocialActivity.class);
                                                                                                   i.putExtra("nameCountry",  mSpinner.getSelectedItem().toString());

                                                                                                   mContext.startActivity(i);
                                                                                               }

                                                                                               else
                                                                                               {

                                                                                                   if (category.getName() == "GASTRONOMY") {

                                                                                                       Intent i = new Intent(mContext, FoodActivity.class);
                                                                                                       i.putExtra("nameCountry",  mSpinner.getSelectedItem().toString());

                                                                                                       mContext.startActivity(i);
                                                                                                   }

                                                                                                   else {

                                                                                                       if (category.getName() == "MONEY") {

                                                                                                           Intent i = new Intent(mContext, MoneyActivity.class);
                                                                                                           i.putExtra("nameCountry",  mSpinner.getSelectedItem().toString());

                                                                                                           mContext.startActivity(i);
                                                                                                       }

                                                                                                       else
                                                                                                       {
                                                                                                           if (category.getName() == "OTHERS") {

                                                                                                               Intent i = new Intent(mContext, OthersActivity.class);
                                                                                                               i.putExtra("nameCountry",  mSpinner.getSelectedItem().toString());
                                                                                                               mContext.startActivity(i);
                                                                                                           }
                                                                                                       }
                                                                                                   }
                                                                                               }
                                                                                           }
                                                                                       }



//

                                                                   }

                                                                   else Toast.makeText(mContext, "Oups! You didn`t choose anything!", Toast.LENGTH_SHORT).show();

                                                                   dialog.dismiss();

                                                               }
                                                           });

                                                   alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                                                           new DialogInterface.OnClickListener() {
                                                               public void onClick(DialogInterface dialog, int which) {
                                                                   dialog.dismiss();
                                                               }
                                                           });

                                                   alertDialog.setView(cView);
                                                   alertDialog.show();




            }
        });

    }




    @Override
    public int getItemCount() {
        return categoryList.size();
    }



}
