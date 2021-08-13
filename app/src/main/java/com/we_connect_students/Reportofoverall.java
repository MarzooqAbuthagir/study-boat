package com.we_connect_students;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

@SuppressWarnings("deprecation")
public class Reportofoverall extends TabActivity {
    Button close,check;

    TabHost TabHostWindow;
    String id="";
    Toolbar toolbar;
    ActionBar actionBar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityovrall);

        check = findViewById(R.id.check);
        close = findViewById(R.id.close);
        Intent intent = getIntent();
        id = intent.getStringExtra("PackageIndexId");

        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;            // Reusable TabSpec for each tab
        Intent intents;                   // Reusable Intent for each tab
        Bundle userData;


        intent = new Intent().setClass(this, Historyactivity.class);
        userData = new Bundle();
        userData.putString("UserID", id);
        intent.putExtras(userData);
        spec = tabHost.newTabSpec("History").setIndicator("History").setContent(intent);
        tabHost.addTab(spec);


        intent = new Intent().setClass(this, Progressactivity.class);
        userData = new Bundle();
        userData.putString("UserID", id);
        intent.putExtras(userData);
        spec = tabHost.newTabSpec("Progress").setIndicator("Progress").setContent(intent);
        tabHost.addTab(spec);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                back();

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Reportofoverall.this, MainActivity.class));
                finish();

            }
        });

    }




    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {



        Intent intent = new Intent(Reportofoverall.this, subcription.class); //OrderDetailsActivity
        // intent.putExtra("subjectID", id);


        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);






    }
}

        //Assign id to Tabhost.
//        TabHostWindow = (TabHost)findViewById(android.R.id.tabhost);
//
//        //Creating tab menu.
//        TabHost.TabSpec TabMenu1 = TabHostWindow.newTabSpec("First tab");
//        TabHost.TabSpec TabMenu2 = TabHostWindow.newTabSpec("Second Tab");
//
//
//        //Setting up tab 1 name.
//        TabMenu1.setIndicator("Tab1");
//        //Set tab 1 activity to tab 1 menu.
//        TabMenu1.setContent(new Intent(this,Historyactivity.class));
//
//        //Setting up tab 2 name.
//        TabMenu2.setIndicator("Tab2");
//        //Set tab 3 activity to tab 1 menu.
//        TabMenu2.setContent(new Intent(this,Progressactivity.class));
//
//        //Setting up tab 2 name.
//
//        //Adding tab1, tab2, tab3 to tabhost view.
//
//        TabHostWindow.addTab(TabMenu1);
//        TabHostWindow.addTab(TabMenu2);













        //Assign id to Tabhost.
//        TabHostWindow = (TabHost)findViewById(android.R.id.tabhost);
//
//        //Creating tab menu.
////        TabHost.TabSpec TabMenu1 = TabHostWindow.newTabSpec("History");
////        TabHost.TabSpec TabMenu2 = TabHostWindow.newTabSpec("Progresses");
//
//
////        //Setting up tab 1 name.
////        TabMenu1.setIndicator("Tab1");
////        //Set tab 1 activity to tab 1 menu.
////        TabMenu1.setContent(new Intent(this,Historyactivity.class));
//
//
//        Intent passing1 = new Intent();
//        passing1.putExtra("iduser", id);
//        passing1.setClass(this, Historyactivity.class);
//
//        TabHost host = getTabHost();
//        host.addTab(host.newTabSpec("History").setIndicator("History")
//                .setContent(passing1));
//
//        Intent passing12 = new Intent();
//        passing1.putExtra("iduser", id);
//        passing1.setClass(this, Progressactivity.class);
//
//        TabHost hosta = getTabHost();
//        hosta.addTab(hosta.newTabSpec("Progresses").setIndicator("Progresses")
//                .setContent(passing12));
//
//
//        //Setting up tab 2 name.
////        TabMenu2.setIndicator("Tab2");
////        //Set tab 3 activity to tab 1 menu.
////        TabMenu2.setContent(new Intent(this,Progressactivity.class));
////
////
////        TabHostWindow.addTab(TabMenu1);
////        TabHostWindow.addTab(TabMenu2);



//public class Reportofoverall extends AppCompatActivity {
//
//    Button close,check;
//    Toolbar toolbar;
//
//    ActionBar actionBar = null;
//    TabLayout tabLayout;
//    String id="";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//        setContentView(R.layout.activityovrall);
//        initToolbar();
//        close=findViewById(R.id.close);
//        check=findViewById(R.id.check);
//
//        Intent intent = getIntent();
//        id = intent.getStringExtra("PackageIndexId");
//        System.out.println(id);
//
//
//
//
//
//
//
//        check.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                back();
//
//            }
//        });
//
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                startActivity(new Intent(Reportofoverall.this, MainActivity.class));
//                finish();
//
//            }
//        });
//
//
//
//
//    }
//    private void initToolbar() {
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        actionBar = getSupportActionBar();
//
//        if (actionBar != null) {
//            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                back();
//            }
//        });
//    }
//
//    @Override
//    public void onBackPressed() {
//        back();
//    }
//
//    private void back() {
//
//
//
//        Intent intent = new Intent(Reportofoverall.this, subcription.class); //OrderDetailsActivity
//        // intent.putExtra("subjectID", id);
//
//
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//
//
//
//
//
//
//    }
//
//}