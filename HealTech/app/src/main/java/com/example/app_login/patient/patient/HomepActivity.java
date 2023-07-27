package com.example.app_login.patient.patient;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.app_login.R;
import com.example.app_login.dbconnection.RequestHandler;
import com.example.app_login.signlogin.MainActivity;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomepActivity extends AppCompatActivity {


    String ID;
    private String JSON_STRING;
    TextView bp,o,t,hr;
    String BP,O,T,HR;

    private MeowBottomNavigation bottomNavigationp;
    public FloatingActionButton floatingsymptomsbutton;
    ImageView menu2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar

        setContentView(R.layout.activity_homep);

        //declaration Video button in bar
        ImageView videobutton= findViewById(R.id.video_p);

        SharedPreferences sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        ID = sharedPref.getString("patient_id", "");

        // Toast.makeText(this, ID, Toast.LENGTH_SHORT).show();

        //move to Video Activity
        videobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomepActivity.this, MeetingpActivity.class));

            }
        });

        //menu &sheetbutton in bar
        menu2=findViewById(R.id.menu_p);
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movementpatientFun();

            }
        });


        //FloatingActionButton(Scan ,Symptoms)
        floatingsymptomsbutton =findViewById(R.id.floatingsymptomsbutton);

        floatingsymptomsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomepActivity.this, SymptomsActivity.class));
            }
        });


        bp = findViewById(R.id.BP);
        o = findViewById(R.id.O);
        t = findViewById(R.id.T);
        hr = findViewById(R.id.HR);


        getJSON();
        try {
            vitals_home(ID);
        }catch (Exception e) {
            //  Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }


        //Bottom Navigation declaratin
        bottomNavigationp=findViewById(R.id.bottomNavigationp);

        bottomNavigationp.show(3,true);

        //Bottom Navigation icons
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
                        map(new View(HomepActivity.this));
                        break;

                    case 2:
                        startActivity(new Intent(HomepActivity.this, CalenderpActivity.class));
                        break;

                    case 3:
                        break;

                    case 4:
                        startActivity(new Intent(HomepActivity.this, HistorypActivity.class));
                        break;

                    case 5:
                        startActivity(new Intent(HomepActivity.this, ChatpActivity.class));
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
                        map(new View(HomepActivity.this));
                        break;

                }

            }
        });


        bottomNavigationp.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                switch (item.getId()){
                    case 2:
                        startActivity(new Intent(HomepActivity.this,CalenderpActivity.class));
                        break;

                }

            }
        });



        bottomNavigationp.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                switch (item.getId()){
                    case 3:
                        break;

                }

            }
        });



        bottomNavigationp.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                switch (item.getId()){
                    case 4:
                        startActivity(new Intent(HomepActivity.this,HistorypActivity.class));
                        break;

                }

            }
        });



        bottomNavigationp.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                switch (item.getId()){
                    case 5:
                        startActivity(new Intent(HomepActivity.this, ChatpActivity.class));
                        break;

                }

            }
        });

    }

    private void vitals_home(String ID){
        JSONObject jsonObject = null;
        System.out.println("ID-"+ID);
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("Sensor");
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                // to get only this patient's ID's data
                String ID_back = jo.getString("ID");
                ID_back=ID;
                System.out.println("ID_back-"+ID_back);
                if(ID_back.equals(ID)){

                    BP = jo.getString("blood_pressure");
                    O = jo.getString("oxygen");
                    T = jo.getString("temperature");
                    HR = jo.getString("heart_rate");

                    bp.setText(BP);
                    o.setText(O);
                    t.setText(T);
                    hr.setText(HR);

                    break;

                }}

        } catch (JSONException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }

  /*  private void number(String ID) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("number");
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                // to get only this patient's ID's data
                String ID_back = jo.getString("ID");
                if(ID_back.equals(ID)){
                    number = jo.getString("number");
                    break;
                }}

        } catch (JSONException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }*/

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(HomepActivity.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                vitals_home(ID);
            }

            @Override
            protected String doInBackground(Void... v) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam("http://192.168.1.5/connection/home_sensor.php");
                System.out.println("result in backgrpound ----------------->  "+s);
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

    //menu sheetbutton
    // movement Function
    private void movementpatientFun() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetpatientsetting_layout);

        //declaration layouts
        //LinearLayout layoutpatientsetting = dialog.findViewById(R.id.layoutpatientsetting);
        // LinearLayout layoutpatientaccount = dialog.findViewById(R.id.layoutpatientaccount);
        //ImageView darkmoodbutton = dialog.findViewById(R.id.darkmoodbutton);
        // LinearLayout layoutpatientinvite = dialog.findViewById(R.id.layoutpatientinvite);
        LinearLayout layoutpatientlogout = dialog.findViewById(R.id.layoutpatientlogout);




      /*  //movement&dialog message
        layoutpatientsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                startActivity(new Intent(HomepActivity.this, SettingpActivity.class));


            }
        });

        layoutpatientaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                startActivity(new Intent(HomepActivity.this, AccountinfopActivity.class));


            }
              darkmoodbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomepActivity.this, "Dark mood", Toast.LENGTH_SHORT).show();
            }
        });

        });*/

        layoutpatientlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Toast.makeText(HomepActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomepActivity.this, MainActivity.class));


            }
        });


    /*

        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
*/
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }


}