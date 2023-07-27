package com.example.app_login.patient.patient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.app_login.R;
import com.example.app_login.dbconnection.RequestHandler;
import com.example.app_login.patient.patient.vitals.vitalsViewAdapter;
import com.example.app_login.patient.patient.vitals.vitalsdata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistorypActivity extends AppCompatActivity {
    Button b;
    String ID="20303140102388";
    private String JSON_STRING;
    ListView listView;
    private MeowBottomNavigation bottomNavigationp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historyp);
        listView = findViewById(R.id.BP_data);

        // page title
        getSupportActionBar().setTitle("Vitals");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getJSON();
        try {
            vitals(ID);
        }catch (Exception e) {
            //Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }

        bottomNavigationp=findViewById(R.id.bottomNavigationp);
        bottomNavigationp.show(4,true);

        bottomNavigationp.add(new MeowBottomNavigation.Model(1,R.drawable.ic_map3));
        bottomNavigationp.add(new MeowBottomNavigation.Model(2,R.drawable.ic_calender));
        bottomNavigationp.add(new MeowBottomNavigation.Model(3,R.drawable.ic_house_solid));
        bottomNavigationp.add(new MeowBottomNavigation.Model(4,R.drawable.ic_history3));
        bottomNavigationp.add(new MeowBottomNavigation.Model(5,R.drawable.ic_chatp));

        //Bottom Navigation selected item for movement to anthor activity
        bottomNavigationp.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                switch (item.getId()){
                    case 1:
                        startActivity(new Intent(HistorypActivity.this, MapActivity.class));
                        map(new View(HistorypActivity.this));

                        break;

                    case 2:
                        startActivity(new Intent(HistorypActivity.this, CalenderpActivity.class));
                        break;

                    case 3:
                        startActivity(new Intent(HistorypActivity.this, HomepActivity.class));
                        break;

                    case 4:
                        startActivity(new Intent(HistorypActivity.this,HistorypActivity.class));
                        break;

                    case 5:
                        break;

                }


            }
        });


        //Show icons clear and bigger
        bottomNavigationp.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                switch (item.getId()){
                    case 1:
                        startActivity(new Intent(HistorypActivity.this, MapActivity.class));
                        break;

                }

            }
        });


        bottomNavigationp.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                switch (item.getId()){
                    case 2:
                        startActivity(new Intent(HistorypActivity.this,CalenderpActivity.class));
                        break;

                }

            }
        });



        bottomNavigationp.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                switch (item.getId()){
                    case 3:
                        startActivity(new Intent(HistorypActivity.this, HomepActivity.class));
                        break;

                }

            }
        });



        bottomNavigationp.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                switch (item.getId()){
                    case 4:
                        break;

                }

            }
        });



        bottomNavigationp.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                switch (item.getId()){
                    case 5:
                        startActivity(new Intent(HistorypActivity.this, ChatpActivity.class));
                        break;

                }

            }
        });
    }

    private void vitals(String ID) {
        JSONObject jsonObject = null;
        ArrayList<vitalsdata> list = new ArrayList<>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("vital");
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                // to get only this patient's ID's data
                String ID_back = jo.getString("ID");
                if(ID_back.equals(ID)){

                    String BP = jo.getString("blood_pressure");
                    String O = jo.getString("oxygen");
                    String Temp = jo.getString("temperature");
                    String HR = jo.getString("heart_rate");
                    String date = jo.getString("date");
                    String time = jo.getString("time");
                    vitalsdata fschdata=new vitalsdata(BP,O,Temp,HR,date,time);
                    list.add(fschdata);
                }}

        } catch (JSONException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        vitalsViewAdapter numbersArrayAdapter = new vitalsViewAdapter(this, list);
        listView.setAdapter(numbersArrayAdapter);


    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(HistorypActivity.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                vitals(ID);
            }

            @Override
            protected String doInBackground(Void... v) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam("http://192.168.1.5/connection/vitals.php");
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    public void map(View view) {

        Intent in=new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Nearest+Hospital"));
        startActivity(in);
    }
}