package com.example.app_login.patient.patient.vitals;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.app_login.R;

import java.util.ArrayList;

public class vitalsViewAdapter extends ArrayAdapter<vitalsdata> {

    private ArrayList<vitalsdata> arrayList;
    public vitalsViewAdapter(@NonNull Context context, ArrayList<vitalsdata> arrayList) {

        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, arrayList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.vitals_list_raw, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        vitalsdata schData = getItem(position);

        // then according to the position of the view assign the desired TextView 1 for the same
        TextView BP = currentItemView.findViewById(R.id.BP);
        BP.setText(String.valueOf(schData.getBP()) );

        TextView O = currentItemView.findViewById(R.id.Oxygen);
        O.setText(String.valueOf(schData.getO()) );

        TextView Temp = currentItemView.findViewById(R.id.Temp);
        Temp.setText(String.valueOf(schData.getTemp()) );

        TextView HR = currentItemView.findViewById(R.id.HR);
        HR.setText(String.valueOf(schData.getHR()) );

        // then according to the position of the view assign the desired TextView 2 for the same
        TextView date = currentItemView.findViewById(R.id.date);
        date.setText(String.valueOf(schData.getDate()));

        // then according to the position of the view assign the desired TextView 2 for the same
        TextView time = currentItemView.findViewById(R.id.time);
        time.setText(String.valueOf(schData.getTime()));


        return currentItemView;

    }


}
