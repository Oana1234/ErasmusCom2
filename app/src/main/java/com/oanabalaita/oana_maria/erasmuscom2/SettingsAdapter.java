package com.oanabalaita.oana_maria.erasmuscom2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Oana-Maria on 14/07/2017.
 */

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.MyViewHolder>{

    private List<Setting> settingsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description;


        public MyViewHolder(View view) {
            super(view);
            title =(TextView) view.findViewById(R.id.titlesetting);
            description =(TextView) view.findViewById(R.id.descriptionsetting);

        }

    }

    public SettingsAdapter(List<Setting> settingsList) {
        this.settingsList = settingsList;
    }

    @Override
    public SettingsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.setting_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SettingsAdapter.MyViewHolder holder, int position) {

        Setting setting = settingsList.get(position);
        holder.title.setText(setting.getTitle());
        holder.description.setText(setting.getDescription());

    }

    @Override
    public int getItemCount() {
        return settingsList.size();
    }
}
