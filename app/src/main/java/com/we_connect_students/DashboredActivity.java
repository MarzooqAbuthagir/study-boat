package com.we_connect_students;

import android.app.TabActivity;
import  android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DashboredActivity  extends TabActivity {
    String TAG = "Package";
    DBHelper dbHelper;
    Utilis utilis;
   Button Button2;
    Toolbar toolbar;
    ActionBar actionBar = null;

    RecyclerView recyclerView;

//    String str_result = "", str_message = "", text = "", examID = "";
//    ArrayList<dashlistnew> listvalues = new ArrayList<dashlistnew>();


    private TabWidget tabLayout;

    //This is our viewPager
//    private ViewPager viewPager;

    private   viewpager adapter;
    TabHost TabHostWindow;

    // LinearLayout buy,viewdet,buy1,viewdet1,buy2,viewdet2,buy3,viewdet3,buy4,viewdet4,buy5,viewdet5,buy6,viewdet6,buy7,viewdet7,buy8,viewdet8;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dash);


        Window window = DashboredActivity.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(DashboredActivity.this, R.color.colorPrimary));
        utilis = new Utilis(DashboredActivity.this);
//        initToolbar();
        dbHelper = new DBHelper(DashboredActivity.this);
        recyclerView = findViewById(R.id.recyclerView);

//        Button2= findViewById(R.id.Button2);
//         Button2.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        back();
//    }
//});

//        listvalues = new ArrayList<>();


//        allSubjectAPI();

//        TabHost tabHost = getTabHost();    // The activity TabHost
//        TabHost.TabSpec spec1;            // Reusable TabSpec for each tab
//        Intent intents;                   // Reusable Intent for each tab
//        Bundle userData;
//
//
//        intents = new Intent().setClass(this, textpackes.class);
//        userData = new Bundle();
//
//        intents.putExtras(userData);
//        spec1 =  spec1 = tabHost.newTabSpec("Video Subscribtion").setIndicator("Video Subscribtion").setContent(intents);
//        tabHost.addTab(spec1);
//
//
//        intents = new Intent().setClass(this, videopackes.class);
//        userData = new Bundle();
//
//        intents.putExtras(userData);
//        spec1 = tabHost.newTabSpec("Video Subscribtion").setIndicator("Video Subscribtion").setContent(intents);
//        tabHost.addTab(spec1);

        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec2;            // Reusable TabSpec for each tab
        Intent intents;                   // Reusable Intent for each tab
        Bundle userData;


        intents = new Intent().setClass(this, testsubscribtion.class);
        userData = new Bundle();

        intents.putExtras(userData);
        spec2 = tabHost.newTabSpec("Test Subscription").setIndicator("Test Subscription").setContent(intents);
        tabHost.addTab(spec2);


        intents = new Intent().setClass(this, videosubscribtion.class);
        userData = new Bundle();

        intents.putExtras(userData);
        spec2 = tabHost.newTabSpec("Video Subscription").setIndicator("Video Subscription").setContent(intents);        tabHost.addTab(spec2);


        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });


    }

//    private TabHost getTabHost() {
//    }


//    private void allSubjectAPI() {
//        if (utilis.isAvailInternet() == true) {
//
//            utilis.showProgress(DashboredActivity.this);
//
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.viewsubscriptionlist, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//
//                    try {
//                        //converting response to json object
//                        JSONObject obj = new JSONObject(response);
//
//                        System.out.println(TAG + " allSubjectAPI response - " + response);
//
//                        utilis.dismissProgress();
//
//                        str_result = obj.getString("errorCode");
//                        System.out.print(TAG + " allSubjectAPI result " + str_result);
//
//                        if (Integer.parseInt(str_result) == 0) {
//
//                            str_message = obj.getString("Message");
//
//                            JSONArray json = obj.getJSONArray("result");
//                            System.out.println(TAG + " allSubjectAPI Length " + json.length());
//                            for (int i = 0; i < json.length(); i++) {
//
//                                JSONObject jsonObject = json.getJSONObject(i);
//
//                                dashlistnew pack = new dashlistnew(jsonObject.getString("packageIndexId"),
//                                        jsonObject.getString("packageName"), jsonObject.getString("Price"),
//                                        jsonObject.getString("NoofQuestions"), jsonObject.getString("ExamName"),
//                                        jsonObject.getString("SubTitle"), jsonObject.getString("SubjectCout"),
//                                        jsonObject.getString("purchasedDate"), jsonObject.getString("expiryLeft"));
//
//                                listvalues.add(pack);
//
//                            }
//
//                            ReportAdapter reportAdapter = new ReportAdapter(DashboredActivity.this, listvalues);
//                            recyclerView.setAdapter(reportAdapter);
//
//
//                        } else if (Integer.parseInt(str_result) == 2) {
//                            str_message = obj.getString("Message");
//
//                            Toast.makeText(DashboredActivity.this, str_message, Toast.LENGTH_SHORT).show();
//
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                    utilis.dismissProgress();
//                    Toast.makeText(DashboredActivity.this, DashboredActivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();
//
//                    if (error instanceof NoConnectionError) {
//                        System.out.println("NoConnectionError");
//                    } else if (error instanceof TimeoutError) {
//                        System.out.println("TimeoutError");
//
//                    } else if (error instanceof ServerError) {
//                        System.out.println("ServerError");
//
//                    } else if (error instanceof AuthFailureError) {
//                        System.out.println("AuthFailureError");
//
//                    } else if (error instanceof NetworkError) {
//                        System.out.println("NetworkError");
//                    }
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    utilis.getSharedPreference();
//                    System.out.println("id" + utilis.strStudentID);
//
//                    params.put("studentId", utilis.strStudentID);
//
//
//                    return params;
//                }
//            };
//
//            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
//
//        } else {
//            Toast.makeText(this, DashboredActivity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
//        }
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

    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        startActivity(new Intent(DashboredActivity.this, MainActivity.class));
        finish();
    }



//    class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
//        Context con;
//        private ArrayList<dashlistnew> arrayList;
//
//        public ReportAdapter(Context con, ArrayList<dashlistnew> listvalues) {
//            this.con = con;
//            this.arrayList = listvalues;
//        }
//
//        @NonNull
//        @Override
//        public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dash_contentnew, viewGroup, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull final ReportAdapter.ViewHolder viewHolder, final int i) {
//
//            Random rnd = new Random();
//            int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
//            viewHolder.button.setBackgroundColor(currentColor);
//            viewHolder.button.setBackground(ContextCompat.getDrawable(DashboredActivity.this, R.drawable.p2));
//
//            viewHolder.exames.setText(arrayList.get(i).getexamName());
//            viewHolder.tittle.setText(arrayList.get(i).getpackageName());
//            viewHolder.subjects.setText(": "+arrayList.get(i).getsubjectCout());
//            viewHolder.prices.setText(": ₹ " + arrayList.get(i).getprice());
//            viewHolder.noques.setText(": "+arrayList.get(i).getnoofQuestions() + " " + "Nos");
//            viewHolder.exam.setText(": "+arrayList.get(i).getexamName());
//
//
//            viewHolder.purs.setText(": "+arrayList.get(i).getflag());
//            viewHolder.expri.setText(": "+arrayList.get(i).getexpiryLeft());
//
//
//
//
//
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return arrayList.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            LinearLayout create, buy, viewdet, button;
//            TextView subjects, exam, noques, tittle, prices, exames,purs,expri;
//
//
//            public ViewHolder(View view) {
//                super(view);
//                exames = view.findViewById(R.id.exames);
//                subjects = view.findViewById(R.id.subjects);
//                noques = view.findViewById(R.id.noques);
//                exam = view.findViewById(R.id.exam);
//                prices = view.findViewById(R.id.prices);
//
//                tittle = view.findViewById(R.id.tittle);
//
//
//                purs = view.findViewById(R.id.purs);
//                expri = view.findViewById(R.id.exprie);
//
//                buy = view.findViewById(R.id.buy1);
//                viewdet = view.findViewById(R.id.viewdet1);
//                create = view.findViewById(R.id.create);
//                button = view.findViewById(R.id.button);
//                view.setTag(itemView);
//            }
//        }
//    }
}