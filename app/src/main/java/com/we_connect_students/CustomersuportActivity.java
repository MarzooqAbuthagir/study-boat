package com.we_connect_students;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.we_connect_students.DataBase.DBHelper;

public class CustomersuportActivity extends AppCompatActivity {
CardView tollfree,mobile,mail,chat, whatsapp;
ImageView whatimage,liveimage,mailimage,headsetimage,mobileimage;
    Button button1,check;
    DBHelper dbHelper;
    Utilis utilis;

    Toolbar toolbar;
    ActionBar actionBar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customreport);
        Window window = CustomersuportActivity.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(CustomersuportActivity.this,R.color.my_statusbar_color4));
        initToolbar();

        dbHelper = new DBHelper(CustomersuportActivity.this);
        // Listview = findViewById(R.id.listview);

        button1=findViewById(R.id.button1);

       // tollfree=findViewById(R.id.tollfree);

        mobile=findViewById(R.id.mobile);
        mail=findViewById(R.id.mail);
        chat=findViewById(R.id.chat);
        whatsapp=findViewById(R.id.whatsapp);

        whatimage=findViewById(R.id.whatimage);
        liveimage=findViewById(R.id.liveimage);

        mailimage=findViewById(R.id.mailimage);


//        headsetimage=findViewById(R.id.headsetimage);

        mobileimage=findViewById(R.id.mobileimage);


        mobileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+917810811111"));
                startActivity(intent);
            }
        });


        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+917810811111"));
                startActivity(intent);
            }
        });


//        headsetimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:180089251235"));
//                startActivity(intent);
//
//            }
//        });


//        tollfree.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:180089251235"));
//                startActivity(intent);
//            }
//        });

        whatimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=917810811111"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=917810811111"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://tawk.to/chat/5d6b5fb877aa790be331ce32/default"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);


            }
        });

        liveimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://tawk.to/chat/5d6b5fb877aa790be331ce32/default"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);


            }
        });


        mail.setOnClickListener(new
                                        View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {



                                                Intent intent=new Intent(Intent.ACTION_SEND);
                                                String[] recipients={"info@weconnectstudents.in"};
                                                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
//                                                intent.putExtra(Intent.EXTRA_SUBJECT,"Subject text here...");
//                                                intent.putExtra(Intent.EXTRA_TEXT,"Body of the content here...");
                                                intent.putExtra(Intent.EXTRA_CC,"studyboat@weconnectstudents.in");
                                                intent.setType("text/html");
                                                intent.setPackage("com.google.android.gm");
                                                startActivity(Intent.createChooser(intent, "Send mail"));


                                            }
                                        });
        mailimage.setOnClickListener(new
                                        View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {






                                                Intent intent=new Intent(Intent.ACTION_SEND);
                                                String[] recipients={"info@weconnectstudents.in"};
                                                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
//                                                intent.putExtra(Intent.EXTRA_SUBJECT,"Subject text here...");
//                                                intent.putExtra(Intent.EXTRA_TEXT,"Body of the content here...");
                                                intent.putExtra(Intent.EXTRA_CC,"studyboat@weconnectstudents.in");
                                                intent.setType("text/html");
                                               intent.setPackage("com.google.android.gm");
                                                startActivity(Intent.createChooser(intent, "Send mail"));



                                            }
                                        });


button1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        back();
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



        Intent intent = new Intent(CustomersuportActivity.this, MainActivity.class); //OrderDetailsActivity
        // intent.putExtra("subjectID", id);



        startActivity(intent);






    }
}
