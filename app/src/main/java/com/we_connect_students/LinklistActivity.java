package com.we_connect_students;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public  class LinklistActivity  extends AppCompatActivity {
    Toolbar toolbar;
    ActionBar actionBar = null;
    LinearLayout creat,iit,upsc,SSC,rrb,tnpsc,ibss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linklist);
        Window window = LinklistActivity.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(LinklistActivity.this,R.color.my_statusbar_color));
        creat=findViewById(R.id.creattest);
        tnpsc=findViewById(R.id.tnpsc);
        iit=findViewById(R.id.iit);
        upsc=findViewById(R.id.upsc);


        SSC=findViewById(R.id.SSC);
        rrb=findViewById(R.id.rrb);
        ibss=findViewById(R.id.ibss);

        initToolbar();



        creat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



String title="NEET";

                String link="https://ntaneet.nic.in/Ntaneet/Welcome.aspx";
                Intent intent = new Intent(LinklistActivity.this, linkActivity.class); //OrderDetailsActivity
                intent.putExtra("link",link );
                intent.putExtra("title",title );



                startActivity(intent);




            }
        });






        iit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title="IIT - JEE";
                String link="https://jeemain.nic.in/webinfo/Public/Home.aspx";
                Intent intent = new Intent(LinklistActivity.this, linkActivity.class); //OrderDetailsActivity

                intent.putExtra("link",link );
                intent.putExtra("title",title );

                startActivity(intent);
            }
        });



        tnpsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title="TNPSC";
                String link="http://www.tnpsc.gov.in/";
                Intent intent = new Intent(LinklistActivity.this, linkActivity.class); //OrderDetailsActivity

                intent.putExtra("link",link );
                intent.putExtra("title",title );

                startActivity(intent);


            }
        });




        upsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title="UPSC";
                String link="https://www.upsc.gov.in";
                Intent intent = new Intent(LinklistActivity.this, linkActivity.class); //OrderDetailsActivity

                intent.putExtra("link",link );
                intent.putExtra("title",title );

                startActivity(intent);
            }
        });


        ibss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title="IBPS";
                String link="https://www.ibps.in";
                Intent intent = new Intent(LinklistActivity.this, linkActivity.class); //OrderDetailsActivity

                intent.putExtra("link",link );

                intent.putExtra("title",title );
                startActivity(intent);
            }
        });




        rrb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title="RRB";
                String link="http://www.rrbcdg.gov.in/";
                Intent intent = new Intent(LinklistActivity.this, linkActivity.class); //OrderDetailsActivity

                intent.putExtra("link",link );
                intent.putExtra("title",title );

                startActivity(intent);
            }
        });
        SSC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title="SSC";
                String link="https://ssc.nic.in/";
                Intent intent = new Intent(LinklistActivity.this, linkActivity.class); //OrderDetailsActivity

                intent.putExtra("link",link );
                intent.putExtra("title",title );

                startActivity(intent);
            }
        });
















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
//        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.practis));
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
        Intent i = new Intent(LinklistActivity.this, MainActivity.class);


        startActivity(i);
    }


/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logouts) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/





    }
