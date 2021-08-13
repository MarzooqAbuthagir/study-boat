package com.we_connect_students;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.we_connect_students.DataBase.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TestcheckActivity extends AppCompatActivity {
    String TAG = "TestcheckActivity";
    DBHelper dbHelper;
    Utilis utilis;

    Toolbar toolbar;
    ActionBar actionBar = null;
    String Testid="",Timetaken="";
    private CheckBox Wrong;
    private CheckBox Correct;
    private CheckBox Skipped;
    private CheckBox All;
    private Button submit;
    Integer First,Second,Third,Four;
    Integer Total=0;
    String str_result = "", str_message = "",text="",time="10";
    int minteger = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_testcheck);


        Window window = TestcheckActivity.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(TestcheckActivity.this, R.color.my_statusbar_color1));
        utilis = new Utilis(TestcheckActivity.this);

        getValues();
        initToolbar();
        dbHelper = new DBHelper(TestcheckActivity.this);
        initControls();




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Wrongt = "",Correctt="",Skippedt="",Allt="";
                if(Wrong.isChecked()){
                    Wrongt = "WrongQuestions ";
                }else {
                    Wrongt="";
                }
                if(Correct.isChecked()){
                    Correctt = "RightQuestions";
                }else {
                    Correctt="";
                }
                if(Skipped.isChecked()){
                    Skippedt = "SkippedQuestions";
                }else {
                    Skippedt="";
                }
                if(All.isChecked()){
                    Allt = "AllQuestions";
                }else {
                    Allt ="";
                }


                if (Wrongt.equals("no")&& Correctt.equals("no")&&  Skippedt.equals("no")&& Allt.equals("no")){
                    Toast.makeText(getApplicationContext(), "select any one", Toast.LENGTH_SHORT).show();

                }else {
                    //Toast.makeText(getApplicationContext(), Wrongt+""+Correctt+""+Skippedt+""+Allt, Toast.LENGTH_SHORT).show();


                     //allSubjectAPIs(Testid,Timetaken,Wrongt,Correctt,Skippedt,Allt);


                }


            }
        });




//        submit.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                if(Wrong.isChecked())
//                {
//                    First = 1;
//                    Total = First;
//                }
//                if(Correct.isChecked())
//                {
//                    Second = 2;
//                    Total = Second;
//                }
//                if(Skipped.isChecked())
//                {
//                    Third = 3;
//                    Total = Third;
//                }
//                if(All.isChecked())
//                {
//                    Four = 4;
//                    Total = Four;
//                }
//
//
//
//                if(Wrong.isChecked() && Correct.isChecked())
//                {
//                    Total = 5;
//                }
//                if(Wrong.isChecked() && Skipped.isChecked())
//                {
//                    Total = 6;
//                }
//                if(Wrong.isChecked() && All.isChecked())
//                {
//                    Total = 7;
//                }
//
//
//
//                if(Correct.isChecked() && Skipped.isChecked())
//                {
//                    Total = 8;
//                }
//
//                if(Correct.isChecked() && All.isChecked())
//                {
//                    Total = 9;
//                }
//
//
//                if(Skipped.isChecked() && All.isChecked())
//                {
//                    Total = 10;
//                }
//
//
//
//
//                if(Wrong.isChecked() && Correct.isChecked() && Skipped.isChecked())
//                {
//                    Total = 11;
//                }
//
//                if(Wrong.isChecked() && Skipped.isChecked() && All.isChecked())
//                {
//                    Total = 12;
//                }
//                if(Wrong.isChecked() && Correct.isChecked() && All.isChecked())
//                {
//                    Total = 13;
//                }
//
//                if(Correct.isChecked() && Skipped.isChecked() && All.isChecked())
//                {
//                    Total = 14;
//                }
//
//
//
//                if(Wrong.isChecked() && Correct.isChecked() && Skipped.isChecked()&& All.isChecked())
//                {
//                    Total = 15;
//                }
//
//                switch(Total)
//                {
//                    case 1:
//                        Toast.makeText(TestcheckActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 2:
//                        Toast.makeText(TestcheckActivity.this, "Correct", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 3:
//                        Toast.makeText(TestcheckActivity.this, "Skipped", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 4:
//                        Toast.makeText(TestcheckActivity.this, "All", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 5:
//                        Toast.makeText(TestcheckActivity.this, "Wrong,Correct", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 6:
//                        Toast.makeText(TestcheckActivity.this, "Wrong,Skipped", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 7:
//                        Toast.makeText(TestcheckActivity.this, "Wrong,All", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 8:
//                        Toast.makeText(TestcheckActivity.this, "Correct,Skipped", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 9:
//                        Toast.makeText(TestcheckActivity.this, "Correct & All", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 10:
//                        Toast.makeText(TestcheckActivity.this, "Skipped & All", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 11:
//                        Toast.makeText(TestcheckActivity.this, ",Wrong,Correct & Skipped", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 12:
//                        Toast.makeText(TestcheckActivity.this, "Wrong,Skipped &All", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 13:
//                        Toast.makeText(TestcheckActivity.this, "Wrong,Correct & All", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 14:
//                        Toast.makeText(TestcheckActivity.this, "Correct,Skipped & All", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 15:
//                        Toast.makeText(TestcheckActivity.this, "All", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 0:
//                        Toast.makeText(TestcheckActivity.this, "Uncheck all", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//
//            }
//        });


    }





    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        startActivity(new Intent(TestcheckActivity.this, AssesmentActivity.class));
        finish();
    }
    private void getValues() {
        Bundle b = getIntent().getExtras();
        if (b != null) {








            Testid = b.getString("Id");
            Timetaken = b.getString("time");


            System.out.println(" getValues " + b);





        }
    }



    private void initControls() {
        Wrong = (CheckBox) findViewById(R.id.Wrong);
        Correct = (CheckBox) findViewById(R.id.Correct);
        Skipped = (CheckBox) findViewById(R.id.Skipped);
        All = (CheckBox) findViewById(R.id.All);
        submit = (Button) findViewById(R.id.submit);

    }





}
