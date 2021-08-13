package com.we_connect_students;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.we_connect_students.DataBase.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

//import com.fivemin.chief.nonetworklibrary.networkBroadcast.NoNet;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Utilis utilis;
    AppBarLayout appBarLayout;
    LinearLayout creat, report, packages, Profile, learn, assess, sub, link, support, admission, video, events, demo, updates;
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawer;
//    String[] studentcalss = { "9", "10", "11", "12",};

    String text = "", str_result = "", str_message = "";
    String TAG = "MainActivity";
    TextView Name;
    View header;
    ImageView img_dp;
    TextView tv_mob, textView2, textmarquee;
    DBHelper dbHelper;
    //    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;


    RequestQueue rq;

    private static ViewPager mPager;
    private static int currentPage = 0;
    //    private static final Integer[] XMEN= {R.drawable.mslide01, R.drawable.mslide02, R.drawable.msilde03 };
    private ArrayList<silder> silders;

    public static Handler handler;
    Timer swipeTimer;

    ViewPagerAdapter viewPagerAdapter;

    FragmentManager fm = null;
    //  DefaultNoNet defaultNoNet = null;
//    NoNet mNoNet;
    //  private NetworkMonitor mNetworkMonitor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        utilis = new Utilis(MainActivity.this);
        dbHelper = new DBHelper(MainActivity.this);
        init();

        textmarquee = (TextView) findViewById(R.id.textmarquee);

        Profile = findViewById(R.id.Profile);
        report = findViewById(R.id.report);
        assess = findViewById(R.id.assess);
        packages = findViewById(R.id.packeds);
        learn = findViewById(R.id.lern);
        admission = findViewById(R.id.admission);
        video = findViewById(R.id.video);
        events = findViewById(R.id.events);
        demo = findViewById(R.id.demo);
        updates = findViewById(R.id.updates);

        support = findViewById(R.id.support);
        link = findViewById(R.id.link);

        fm = getSupportFragmentManager();
//        mNoNet = new NoNet();
//        mNoNet.initNoNet(this, fm);


        textmarquee.setSelected(true);
//        textmarquee.setMovementMethod(LinkMovementMethod.getInstance());

        getnews();

        rq = Customizevolleysilder.getInstance(this).getRequestQueue();

        mPager = (ViewPager) findViewById(R.id.viewPager);

        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        silders = new ArrayList<>();


//        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                for(int i = 0; i< dotscount; i++){
//                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
//                }
//
//                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });


        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeTimer.cancel();

                Toast.makeText(MainActivity.this, "Will be Updated!", Toast.LENGTH_SHORT).show();


            }
        });


        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   Toast.makeText(MainActivity.this, "Will be Updated link!", Toast.LENGTH_SHORT).show();

                swipeTimer.cancel();
                startActivity(new Intent(MainActivity.this, LinklistActivity.class));
                finish();


            }
        });


        admission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeTimer.cancel();

                open("https://weconnectstudents.in/");

            }
        });


        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                swipeTimer.cancel();
                startActivity(new Intent(MainActivity.this, videos.class));
                finish();

            }
        });

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                swipeTimer.cancel();
                startActivity(new Intent(MainActivity.this, CustomersuportActivity.class));
                finish();


            }
        });
        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                swipeTimer.cancel();
                // boolean b = tableExists(dbHelper.vatbook, dbHelper.selctedlist);
                boolean b = tableExistsssnew(dbHelper.vatbook, dbHelper.questionanswer);


            }
        });


        assess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                swipeTimer.cancel();

                boolean b = tableExistsss(dbHelper.vatbook, dbHelper.selctedlist);


            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeTimer.cancel();
                startActivity(new Intent(MainActivity.this, subcription.class));
                finish();

            }
        });


        packages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // handler.removeCallbacksAndMessages(null);
                swipeTimer.cancel();


                boolean b = tableExistss(dbHelper.vatbook, dbHelper.selctedlist);
            }
        });

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // handler.removeCallbacksAndMessages(null);
                swipeTimer.cancel();
dbHelper.CreateTable(6);
                dbHelper.vatbook.execSQL("delete from " + dbHelper.selctedlist);
                startActivity(new Intent(MainActivity.this, eventpackage.class));
                finish();
//                boolean b = tableExistss(dbHelper.vatbook, dbHelper.selctedlist);
            }
        });

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                swipeTimer.cancel();
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                finish();

            }
        });


        demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                swipeTimer.cancel();
//                open("https://youtu.be/P2Nmv6FF2T8");
                open("https://studyboat.app/demo.php");
            }
        });

        updates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeTimer.cancel();
                open("https://studyboat.app/updates.php");
            }
        });


//        viewPager = (ViewPager) findViewById(R.id.viewPager);
//
//        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
//
//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
//
//        viewPager.setAdapter(viewPagerAdapter);
//
//        dotscount = viewPagerAdapter.getCount();
//        dots = new ImageView[dotscount];
//
//        for(int i = 0; i < dotscount; i++){
//
//            dots[i] = new ImageView(this);
//            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
//
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//            params.setMargins(8, 0, 8, 0);
//
//            sliderDotspanel.addView(dots[i], params);
//
//        }
//
//        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
//
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                for(int i = 0; i< dotscount; i++){
//                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
//                }
//
//                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });


//        packages=findViewById(R.id.packages);
//        profile=findViewById(R.id.profile);
//        logout=findViewById(R.id.logout);


        Name = findViewById(R.id.name);


        appBarLayout = findViewById(R.id.view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.headmenu);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        header = navigationView.getHeaderView(0);
        img_dp = header.findViewById(R.id.imageView);

        textView2 = header.findViewById(R.id.textView2);
        tv_mob = header.findViewById(R.id.textView);
        utilis.getSharedPreference();
        System.out.println("id" + utilis.strName);

        Name.setText("Hi" + "," + " " + utilis.strName);
        textView2.setText(utilis.strName);
        tv_mob.setText(utilis.strMobNum);
        // boolean b = tableExists(dbHelper.vatbook, dbHelper.selctedlist);


        Log.e(TAG, "Creating table " + dbHelper.questionresponse + "because it doesn't exist!");


//        packages.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
////                dbHelper.vatbook.execSQL("delete from " + dbHelper.questionresponse);
////                dbHelper.vatbook.execSQL("delete from " + dbHelper.selctedlist);
////
//
//
//                boolean b = tableExistss(dbHelper.vatbook, dbHelper.selctedlist);
//
//
//
////                startActivity(new Intent(MainActivity.this, PackageActivity.class));
////                finish();
//
//            }
//        });


//        profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
//                finish();
//
//            }
//        });


//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                logout();
//
//            }
//        });

        TextView tvVersion = findViewById(R.id.tvVersion);
        tvVersion.setText("StudyBoat V"+BuildConfig.VERSION_NAME);
        tvVersion.setTextColor(Color.GRAY);
    }


    public void open(String url) {

        Intent intent = (new Intent(Intent.ACTION_VIEW));
        intent.setData(Uri.parse(url));
        startActivity(intent);

    }


    private boolean tableExistsssnew(SQLiteDatabase vatbook, String questionanswer) {


        Cursor cursor = vatbook.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + questionanswer + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                System.out.println("corser" + cursor.getCount());

                dbHelper.CreateTable(7);
                dbHelper.vatbook.execSQL("delete from " + dbHelper.questionanswer);


                startActivity(new Intent(MainActivity.this, PracticeActivity.class));
                finish();


                return true;
            }

            cursor.close();
        }

        startActivity(new Intent(MainActivity.this, PracticeActivity.class));
        finish();
        System.out.println("corser" + cursor);


        return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.navDashboard) {
            drawer.closeDrawers();

            startActivity(new Intent(MainActivity.this, MainActivity.class));
            finish();
            return true;
        } else if (id == R.id.navProfile) {
            swipeTimer.cancel();
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            finish();

            drawer.closeDrawers();
        } else if(id==R.id.navUpdates) {
            swipeTimer.cancel();
            open("https://studyboat.app/updates.php");

            drawer.closeDrawers();
        } else if (id == R.id.navSubscription) {

            swipeTimer.cancel();
            startActivity(new Intent(MainActivity.this, DashboredActivity.class));
            finish();

            drawer.closeDrawers();
        } else if (id == R.id.navEvents) {
            swipeTimer.cancel();
            dbHelper.CreateTable(6);
            dbHelper.vatbook.execSQL("delete from " + dbHelper.selctedlist);
            startActivity(new Intent(MainActivity.this, eventpackage.class));
            finish();

            drawer.closeDrawers();
        } else if (id == R.id.navDemo) {

            swipeTimer.cancel();
            open("https://studyboat.app/demo.php");

            drawer.closeDrawers();
        } else if (id == R.id.navResetPwd) {

            swipeTimer.cancel();
            resetPwd();
            /*startActivity(new Intent(MainActivity.this, ResetPwdActivity.class));
            finish();*/
            drawer.closeDrawers();
        } else if (id == R.id.navSupport) {

            swipeTimer.cancel();
            startActivity(new Intent(MainActivity.this, CustomersuportActivity.class));
            finish();

            drawer.closeDrawers();
        } else if (id == R.id.navShare) {

            swipeTimer.cancel();
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String shareMessage= "\nStudyBoat – One App For All Competitive Exams | Practice Test and Learning App\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }

            drawer.closeDrawers();
        } else if (id == R.id.navLogout) {
            drawer.closeDrawers();
            swipeTimer.cancel();
            logout();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

 String strPwd="";
    Dialog add_dialog;

    private void resetPwd() {
         add_dialog= new Dialog(MainActivity.this, R.style.NewDialog);
        add_dialog.setCancelable(false);
        add_dialog.getWindow().setContentView(R.layout.activity_reset_pwd);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(add_dialog.getWindow().getAttributes());
        //lp.setMargins(30,0,30,0);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        add_dialog.getWindow().setAttributes(lp);
        add_dialog.show();

        add_dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final EditText etPwd = add_dialog.findViewById(R.id.etPassword);
final Button btnResetPwd = add_dialog.findViewById(R.id.btnResetPwd);
final Button btnCancel = add_dialog.findViewById(R.id.btnCancel);
btnCancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        add_dialog.dismiss();
    }
});

        btnResetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strPwd = etPwd.getText().toString().trim();

                resetPwdApi();

            }
        });

    }

    private void resetPwdApi() {
        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(MainActivity.this);

            StringRequest stringRequest
                    = new StringRequest(Request.Method.POST, utilis.Api + utilis.resetPassword, new Response.Listener<String>() {
                @SuppressLint("SetJavaScriptEnabled")
                @Override
                public void onResponse(String response) {

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);

                        System.out.println(TAG + " resetPwd response - " + response);

                        utilis.dismissProgress();


                        str_result = obj.getString("errorCode");
                        if (Integer.parseInt(str_result) == 1) {
                            str_message = obj.getString("Message");

                            Toast.makeText(MainActivity.this, str_message, Toast.LENGTH_SHORT).show();

                        } else if (Integer.parseInt(str_result) == 0) {

                            str_message = obj.getString("Message");
                            add_dialog.dismiss();
                            showConfirmDialog();

                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(MainActivity.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(MainActivity.this, MainActivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

                    if (error instanceof NoConnectionError) {
                        System.out.println("NoConnectionError");
                    } else if (error instanceof TimeoutError) {
                        System.out.println("TimeoutError");

                    } else if (error instanceof ServerError) {
                        System.out.println("ServerError");

                    } else if (error instanceof AuthFailureError) {
                        System.out.println("AuthFailureError");

                    } else if (error instanceof NetworkError) {
                        System.out.println("NetworkError");
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    utilis.getSharedPreference();
                    params.put("studentIndexId", utilis.strStudentID);
                    params.put("password", strPwd);
                    System.out.println("resetPwd input "+params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }
    }

    private void showConfirmDialog() {
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(
                MainActivity.this);
        builder.setMessage("Password Changed");
        builder.setNegativeButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss();
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        finish();
                    }
                });
        builder.show();
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle(MainActivity.this.getResources().getString(R.string.logout_title))
                .setMessage(MainActivity.this.getResources().getString(R.string.logout_msg))
                .setPositiveButton(MainActivity.this.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        dialog.dismiss();


                        logoutapi();


                    }
                })
                .setNegativeButton(MainActivity.this.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }


    private void logoutapi() {
        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(MainActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.logout, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);

                        System.out.println(TAG + " loginAPI response - " + response);

                        utilis.dismissProgress();

                        str_result = obj.getString("errorCode");
                        System.out.print(TAG + " loginAPI result " + str_result);

                        if (Integer.parseInt(str_result) == 1) {
                            str_message = obj.getString("Message");


                        } else if (Integer.parseInt(str_result) == 0) {

                            str_message = obj.getString("Message");
                            LoginSharedPreference.setLoggedIn(MainActivity.this, false);

                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            finish();

                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(MainActivity.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(MainActivity.this, MainActivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

                    if (error instanceof NoConnectionError) {
                        System.out.println("NoConnectionError");
                    } else if (error instanceof TimeoutError) {
                        System.out.println("TimeoutError");

                    } else if (error instanceof ServerError) {
                        System.out.println("ServerError");

                    } else if (error instanceof AuthFailureError) {
                        System.out.println("AuthFailureError");

                    } else if (error instanceof NetworkError) {
                        System.out.println("NetworkError");
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    utilis.getSharedPreference();
                    System.out.println("id" + utilis.strStudentID);
                    params.put("studentIndexId", utilis.strStudentID);

                    System.out.println(TAG + " loginAPI inputs " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


        } else {
            Toast.makeText(this, MainActivity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }


    private void getnews() {
        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(MainActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.getnews, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);

                        System.out.println(TAG + " getnews response - " + response.toString());


                        utilis.dismissProgress();

                        String get = obj.getString("result");

//                        Toast.makeText(MainActivity.this,"result"+get,Toast.LENGTH_LONG).show();
                        textmarquee.setText(get);


//                        if (Integer.parseInt(str_result) == 1) {
//                            str_message = obj.getString("Message");
//
//
//                        } else if (Integer.parseInt(str_result) == 0) {
//
//                            str_message = obj.getString("Message");
//                            LoginSharedPreference.setLoggedIn(MainActivity.this, false);
//
//                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                            finish();
//
//                        } else if (Integer.parseInt(str_result) == 2) {
//                            str_message = obj.getString("Message");
//
//                            Toast.makeText(MainActivity.this, str_message, Toast.LENGTH_SHORT).show();
//
//                        }
                        silder();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        utilis.dismissProgress();
                        silder();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(MainActivity.this, MainActivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();
                    silder();
                    if (error instanceof NoConnectionError) {
                        System.out.println("NoConnectionError");
                    } else if (error instanceof TimeoutError) {
                        System.out.println("TimeoutError");

                    } else if (error instanceof ServerError) {
                        System.out.println("ServerError");

                    } else if (error instanceof AuthFailureError) {
                        System.out.println("AuthFailureError");

                    } else if (error instanceof NetworkError) {
                        System.out.println("NetworkError");
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    utilis.getSharedPreference();
                    System.out.println("id" + utilis.strStudentID);
                    params.put("studentIndexId", utilis.strStudentID);

                    System.out.println(TAG + " getnews inputs " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


        } else {
            Toast.makeText(this, MainActivity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }


    private void silder() {
        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(MainActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.dynamicimage, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);

                        System.out.println(TAG + " image response - " + response.toString());


                        utilis.dismissProgress();

                        String get = obj.getString("errorCode");

                        if (Integer.parseInt(get) == 0) {


                            str_message = obj.getString("Message");

                            final JSONArray json = obj.getJSONArray("result");
                            for (int i = 0; i < json.length(); i++) {

                                silder silder = new silder();

                                try {


                                    JSONObject jsonObject = json.getJSONObject(i);

                                    silder.setImage(jsonObject.getString("image"));
                                } catch (JSONException e) {

                                }


                                silders.add(silder);


//                                dotscount = viewPagerAdapter.getCount();
//                                dots = new ImageView[dotscount];
//
//                                for(int j = 0; j < dotscount; j++){
//
//                                    dots[j] = new ImageView(MainActivity.this);
//                                    dots[j].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
//
//                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//                                    params.setMargins(8, 0, 8, 0);
//
//                                    sliderDotspanel.addView(dots[j], params);
//
//                                }
//
//                                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
//


                            }

                            setViewPager(json.length());

                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(MainActivity.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(MainActivity.this, MainActivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

                    if (error instanceof NoConnectionError) {
                        System.out.println("NoConnectionError");
                    } else if (error instanceof TimeoutError) {
                        System.out.println("TimeoutError");

                    } else if (error instanceof ServerError) {
                        System.out.println("ServerError");

                    } else if (error instanceof AuthFailureError) {
                        System.out.println("AuthFailureError");

                    } else if (error instanceof NetworkError) {
                        System.out.println("NetworkError");
                    }
                }
            });
//            {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//
////                    utilis.getSharedPreference();
////                    System.out.println("id"+utilis.strStudentID);
////                    params.put("studentIndexId", utilis.strStudentID);
////
////                    System.out.println(TAG + " getnews inputs " + params);
//                    return params;
//                }
//            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Customizevolleysilder.getInstance(this).addToRequestQueue(stringRequest);


        } else {
            Toast.makeText(this, MainActivity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }

    private void setViewPager(final int length) {
        viewPagerAdapter = new ViewPagerAdapter(silders, MainActivity.this);
        mPager.setAdapter(viewPagerAdapter);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        handler.postDelayed(Update, 1000);
        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 500, 3000);

        addDot(0);


        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
addDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addDot(int dotPosition) {
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        sliderDotspanel.removeAllViews();

        for(int j = 0; j < dotscount; j++){

            dots[j] = new ImageView(MainActivity.this);
            dots[j].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[j], params);

        }
        dots[dotPosition].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
    }


    @Override
    public void onBackPressed() {
        finishAffinity();
    }


    private boolean tableExists(SQLiteDatabase vatbook, String selctedlist) {


        Cursor cursor = vatbook.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + selctedlist + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                System.out.println("corser" + cursor.getCount());

dbHelper.CreateTable(4);
                dbHelper.vatbook.execSQL("delete from " + dbHelper.questionresponse);
                dbHelper.CreateTable(6);
                dbHelper.vatbook.execSQL("delete from " + dbHelper.selctedlist);

                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                finish();
                return true;
            }

            cursor.close();
        }

        startActivity(new Intent(MainActivity.this, Main2Activity.class));
        finish();
        System.out.println("corser" + cursor);


        return false;
    }


    private boolean tableExistss(SQLiteDatabase vatbook, String selctedlist) {


        Cursor cursor = vatbook.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + selctedlist + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                System.out.println("corser" + cursor.getCount());

                dbHelper.CreateTable(4);
                dbHelper.vatbook.execSQL("delete from " + dbHelper.questionresponse);
                dbHelper.CreateTable(6);
                dbHelper.vatbook.execSQL("delete from " + dbHelper.selctedlist);

                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                finish();
                return true;
            }

            cursor.close();
        }

        startActivity(new Intent(MainActivity.this, Main2Activity.class));
        finish();
        System.out.println("corser" + cursor);


        return false;
    }

    private boolean tableExistsss(SQLiteDatabase vatbook, String selctedlist) {


        Cursor cursor = vatbook.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + selctedlist + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                System.out.println("corser" + cursor.getCount());

                dbHelper.CreateTable(4);
                dbHelper.vatbook.execSQL("delete from " + dbHelper.questionresponse);
                dbHelper.CreateTable(6);
                dbHelper.vatbook.execSQL("delete from " + dbHelper.selctedlist);


                startActivity(new Intent(MainActivity.this, AssesmentActivity.class));
                finish();


                return true;
            }

            cursor.close();
        }

        startActivity(new Intent(MainActivity.this, AssesmentActivity.class));
        finish();
        System.out.println("corser" + cursor);


        return false;
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
            // logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


    private void init() {
//        for(int i=0;i<XMEN.length;i++)
//            XMENArray.add(XMEN[i]);
//
//        mPager = (ViewPager) findViewById(R.id.viewPager);
//        mPager.setAdapter(new MyAdapter(MainActivity.this,XMENArray));
//        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
////        indicator.setViewPager(mPager);
////
////        // Auto start of viewpager
////        final Handler handler = new Handler();
////        final Runnable Update = new Runnable() {
////            public void run() {
////                if (currentPage == XMEN.length) {
////                    currentPage = 0;
////                }
////                mPager.setCurrentItem(currentPage++, true);
////            }
////        };
        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
//                handler.post(Update);
            }
        }, 500, 3000);
    }
//    @Override
//    protected void onResume() {
//        mNoNet.RegisterNoNet();
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        mNoNet.unRegisterNoNet();
//        super.onPause();
//    }
}

