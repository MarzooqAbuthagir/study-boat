package com.we_connect_students;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabWidget;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.we_connect_students.DataBase.DBHelper;

import java.util.ArrayList;

public class PackageActivity extends TabActivity {
    String TAG = "Package";
    DBHelper dbHelper;
    Utilis utilis;

    Toolbar toolbar;
    ActionBar actionBar = null;

    RecyclerView recyclerView;

    String str_result = "", str_message = "",text="",examID="";
    ArrayList<packlistnew> listvalues = new ArrayList<packlistnew>();

    FragmentManager fm = null;
    //  DefaultNoNet defaultNoNet = null;


    private TabWidget tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    private   viewpager adapter;
    TabHost TabHostWindow;


    // LinearLayout buy,viewdet,buy1,viewdet1,buy2,viewdet2,buy3,viewdet3,buy4,viewdet4,buy5,viewdet5,buy6,viewdet6,buy7,viewdet7,buy8,viewdet8;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_package);


//        mNoNet = new NoNet();
//        mNoNet.initNoNet(this, fm);
        Window window = PackageActivity.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(PackageActivity.this,R.color.packages));
        utilis = new Utilis(PackageActivity.this);
//        initToolbar();
        dbHelper = new DBHelper(PackageActivity.this);
        recyclerView = findViewById(R.id.recyclerView);

//        getValues();


//        tabLayout = (TabWidget) findViewById(R.id.tabs);

        //Adding the tabs using addTab() method


//        //Initializing viewPager
//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//
//        //Creating our pager adapter
////         adapter = new viewpager(getSupportFragmentManager(),tabLayout.getTabCount());
//
//        //Adding adapter to pager
//        viewPager.setAdapter(adapter);

        Intent intent =getIntent();
        examID=intent.getStringExtra("examid");



        final TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec1;            // Reusable TabSpec for each tab
        Intent intents;                   // Reusable Intent for each tab
        Bundle userData;

        if(examID.equals("5")) {
            intents = new Intent().setClass(this, textpackes.class);
            userData = new Bundle();
            userData.putString("examid", examID);
            intents.putExtras(userData);
            spec1 = tabHost.newTabSpec("Test").setIndicator("Test").setContent(intents);
            tabHost.addTab(spec1);


            intents = new Intent().setClass(this, videopackes.class);
            userData = new Bundle();
            userData.putString("examid", examID);
            intents.putExtras(userData);
            spec1 = tabHost.newTabSpec("Video").setIndicator("Video").setContent(intents);
            tabHost.addTab(spec1);
            tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).getBackground().setColorFilter(getResources().getColor(R.color.tab_package), PorterDuff.Mode.MULTIPLY);
        }
        else
        {
            intents = new Intent().setClass(this, textpackes.class);
            userData = new Bundle();
            userData.putString("examid", examID);
            intents.putExtras(userData);
            spec1 = tabHost.newTabSpec("Test Packages").setIndicator("Test Packages").setContent(intents);
            tabHost.addTab(spec1);


            intents = new Intent().setClass(this, videopackes.class);
            userData = new Bundle();
            userData.putString("examid", examID);
            intents.putExtras(userData);
            spec1 = tabHost.newTabSpec("Video Packages").setIndicator("Video Packages").setContent(intents);
            tabHost.addTab(spec1);
            tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).getBackground().setColorFilter(getResources().getColor(R.color.tab_package), PorterDuff.Mode.MULTIPLY);
        }


        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).getBackground().setColorFilter(getResources().getColor(R.color.tab_package), PorterDuff.Mode.MULTIPLY);
            }
        });


        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });








//      Bundle b = new Bundle();
//        b.putString("examID",examID);
//        textpackage textpackage =new textpackage();
//        videopackage videopackage = new videopackage();
//        textpackage.setArguments(b);
//        videopackage.setArguments(b);

//        System.out.println("textpackage"+b);

//
//        getSupportFragmentManager().beginTransaction().add(fragment,examID).commit();





//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//
//                if (tab.getPosition() ==0){
//                    adapter.notifyDataSetChanged();
//                }
//               else if (tab.getPosition() ==1){
//                    adapter.notifyDataSetChanged();
//                }
//               else if (tab.getPosition() ==2){
//                    adapter.notifyDataSetChanged();
//                }
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });



//        viewdet=findViewById(R.id.viewdet);
//        viewdet1=findViewById(R.id.viewdet1);
//        viewdet2=findViewById(R.id.viewdet2);
//        viewdet3=findViewById(R.id.viewdet3);
//        viewdet4=findViewById(R.id.viewdet4);
//        viewdet5=findViewById(R.id.viewdet5);
//        viewdet6=findViewById(R.id.viewdet6);
//        viewdet7=findViewById(R.id.viewdet7);
//        viewdet8=findViewById(R.id.viewdet8);




//        buy=findViewById(R.id.buy);
//        buy1=findViewById(R.id.buy1);
//        buy2=findViewById(R.id.buy2);
//        buy3=findViewById(R.id.buy3);
//        buy4=findViewById(R.id.buy4);
//        buy5=findViewById(R.id.buy5);
//        buy6=findViewById(R.id.buy6);
//        buy7=findViewById(R.id.buy7);
//        buy8=findViewById(R.id.buy8);


//        buy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(PackageActivity.this, "COMING SOON", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//        viewdet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                startActivity(new Intent(PackageActivity.this, Packdetails.class));
//                finish();
//
//            }
//        });
//        buy1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(PackageActivity.this, "COMING SOON", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//        viewdet1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                startActivity(new Intent(PackageActivity.this, Packdetails.class));
//                finish();
//
//            }
//        });
//        buy2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(PackageActivity.this, "COMING SOON", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//        viewdet2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                startActivity(new Intent(PackageActivity.this, Packdetails.class));
//                finish();
//
//            }
//        });
//        buy3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(PackageActivity.this, "COMING SOON", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//        viewdet3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                startActivity(new Intent(PackageActivity.this, Packdetails.class));
//                finish();
//
//            }
//        });   buy4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(PackageActivity.this, "COMING SOON", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//        viewdet4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                startActivity(new Intent(PackageActivity.this, Packdetails.class));
//                finish();
//
//            }
//        });
//        buy5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(PackageActivity.this, "COMING SOON", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//        viewdet5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                startActivity(new Intent(PackageActivity.this, Packdetails.class));
//                finish();
//
//            }
//        });   buy6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(PackageActivity.this, "COMING SOON", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//        viewdet6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                startActivity(new Intent(PackageActivity.this, Packdetails.class));
//                finish();
//
//            }
//        });   buy7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(PackageActivity.this, "COMING SOON", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//        viewdet7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                startActivity(new Intent(PackageActivity.this, Packdetails.class));
//                finish();
//
//            }
//        });
//        buy8.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(PackageActivity.this, "COMING SOON", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//        viewdet8.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                startActivity(new Intent(PackageActivity.this, Packdetails.class));
//                finish();
//
//            }
//        });




//
//        listvalues = new ArrayList<>();
//
//
//        allSubjectAPI();




    }




//    private void allSubjectAPI() {
//        if (utilis.isAvailInternet() == true) {
//
//            utilis.showProgress(PackageActivity.this);
//
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.packagessnew, new Response.Listener<String>() {
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
//                            packlistnew pack = new packlistnew(jsonObject.getString("packageIndexId"),
//                                    jsonObject.getString("packageName"),jsonObject.getString("Price"),
//                                    jsonObject.getString("NoofQuestions"), jsonObject.getString("ExamName"),
//                                    jsonObject.getString("SubTitle"), jsonObject.getString("SubjectCout"),
//                                    jsonObject.getString("Flag"));
//
//                            listvalues.add(pack);
//
//                        }
//
//                            ReportAdapter reportAdapter = new ReportAdapter(PackageActivity.this, listvalues);
//                            recyclerView.setAdapter(reportAdapter);
//
//
//                        } else if (Integer.parseInt(str_result) == 2) {
//                            str_message = obj.getString("Message");
//
//                            Toast.makeText(PackageActivity.this, str_message, Toast.LENGTH_SHORT).show();
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
//                    Toast.makeText(PackageActivity.this, PackageActivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();
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
//                    System.out.println("id"+utilis.strStudentID);
//
//                    params.put("studentId",utilis.strStudentID );
//                    params.put("examIndexId",examID );
//
//                    return params;
//                }
//            };
//
//            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
//
//        } else {
//            Toast.makeText(this, PackageActivity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(PackageActivity.this, Main2Activity.class));
        finish();
    }





//
//    private class ReportAdapter extends RecyclerView.Adapter {
//        public ReportAdapter(ReportPAge reportPAge, ArrayList<packlist> listvalues) {
//
//        }
//
//        @NonNull
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//            return null;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return 0;
//        }
//    }





//    class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
//        Context con;
//        private ArrayList<packlistnew> arrayList;
//
//        public ReportAdapter(PackageActivity con, ArrayList<packlistnew> listvalues) {
//            this.con = con;
//            this.arrayList = listvalues;
//        }
//
//        @NonNull
//        @Override
//        public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pack_contentnew, viewGroup, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull final ReportAdapter.ViewHolder viewHolder, final int i) {
//
//            Random rnd = new Random();
//            int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
//            viewHolder.button.setBackgroundColor(currentColor);
//              viewHolder.button.setBackground(ContextCompat.getDrawable(PackageActivity.this,R.drawable.p2));
//
//viewHolder.exames.setText(arrayList.get(i).getexamName());
//            viewHolder.tittle.setText(arrayList.get(i).getpackageName());
//            viewHolder.subjects.setText(arrayList.get(i).getsubjectCout());
//            viewHolder.prices.setText("₹ "+arrayList.get(i).getprice());
//            viewHolder.noques.setText(arrayList.get(i).getnoofQuestions()+" "+"Nos");
//            viewHolder.exam.setText(arrayList.get(i).getexamName());
//
//
//            System.out.println("buy"+arrayList.get(i).getflag());
//
//            String busycount=arrayList.get(i).getflag();
//
//           String id= utilis.BUYID;
//
//
//
//
//
//            if(busycount.equals("0")){
//                viewHolder.buy.setVisibility(View.GONE);
//                viewHolder.create.setVisibility(View.GONE);
//
//            }else {
//                viewHolder.buy.setVisibility(View.GONE);
//                viewHolder.create.setVisibility(View.VISIBLE);
//            }
//
//
//            viewHolder.buy.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
////                    viewHolder.buy.setVisibility(View.GONE);
////                    viewHolder.create.setVisibility(View.VISIBLE);
//
//
//                    String id=arrayList.get(i).getpackageIndexId();
//                    Intent intent = new Intent(PackageActivity.this, BuyActivity.class); //OrderDetailsActivity
//
//                   intent.putExtra("Qcount",id );
//                    intent.putExtra("examid",examID);
//
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//
//
//
//                }
//            });
//
//
//            viewHolder.create.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//
////                    startActivity(new Intent(PackageActivity.this, CreateTestActivity.class));
////                    finish();
//
//                    String id=arrayList.get(i).getpackageIndexId();
//                    Intent intent = new Intent(PackageActivity.this, CreateTestActivity.class); //OrderDetailsActivity
//
//                    intent.putExtra("Qcount",id );
//                    intent.putExtra("examid",examID);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//
//
//
//
//
//
//                }
//            });
//
//
//
//            viewHolder.viewdet.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//
//
//
//                    String id=arrayList.get(i).getpackageIndexId();
//                    Intent intent = new Intent(PackageActivity.this, Packdetails.class); //OrderDetailsActivity
//
//                    intent.putExtra("Qcount",id );
//                    intent.putExtra("examid",examID);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                }
//            });
//
//
//







//            viewHolder.reset.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//
//
//String id=arrayList.get(i).gettid();
//
//
//
////cehckreattemtpt(id);
//        allSubjectAPIs(id);
//
//
//    }
//});




//        }

//            @Override
//            public int getItemCount() {
//                return arrayList.size();
//            }
//
//            public class ViewHolder extends RecyclerView.ViewHolder {
//                LinearLayout create,buy,viewdet,button;
//                TextView subjects,exam,noques,tittle,prices,exames;
//
//
//
//                public ViewHolder(View view) {
//                    super(view);
//                    exames= view.findViewById(R.id.exames);
//                    subjects = view.findViewById(R.id.subjects);
//                    noques = view.findViewById(R.id.noques);
//                    exam = view.findViewById(R.id.exam);
//                    prices=view.findViewById(R.id.prices);
//
//                    tittle=view.findViewById(R.id.tittle);
//
//
//                    buy=view.findViewById(R.id.buy1);
//                    viewdet=view.findViewById(R.id.viewdet1);
//                    create=view.findViewById(R.id.create);
//                    button=view.findViewById(R.id.button);
//                    view.setTag(itemView);
//                }
//            }
//        }
//
//        private void cehckreattemtpt(final String id) {
//
//
//            if (utilis.isAvailInternet() == true) {
//
//                utilis.showProgress(PackageActivity.this);
//
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.reattampt, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try {
//                            //converting response to json object
//                            JSONObject obj = new JSONObject(response);
//
//                            System.out.println(TAG + " allSubjectAPI response - " + response);
//
//                            utilis.dismissProgress();
//
//                            str_result = obj.getString("errorCode");
//                            System.out.print(TAG + " allSubjectAPI result " + str_result);
//
//                            if (Integer.parseInt(str_result) == 0) {
//                                // dbHelper.vatbook.execSQL("delete from " + dbHelper.questionslist);
//                                str_message = obj.getString("Message");
//                                JSONObject json = obj.getJSONObject("result");
//
//                                String  ids   = json.getString("lastIndexId");
//
//                                allSubjectAPIs(ids);
//
//                            } else if (Integer.parseInt(str_result) == 2) {
//                                str_message = obj.getString("Message");
//
//                                Toast.makeText(PackageActivity.this, str_message, Toast.LENGTH_SHORT).show();
//
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        utilis.dismissProgress();
//                        Toast.makeText(PackageActivity.this, PackageActivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();
//
//                        if (error instanceof NoConnectionError) {
//                            System.out.println("NoConnectionError");
//                        } else if (error instanceof TimeoutError) {
//                            System.out.println("TimeoutError");
//
//                        } else if (error instanceof ServerError) {
//                            System.out.println("ServerError");
//
//                        } else if (error instanceof AuthFailureError) {
//                            System.out.println("AuthFailureError");
//
//                        } else if (error instanceof NetworkError) {
//                            System.out.println("NetworkError");
//                        }
//                    }
//                }) {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<>();
//                        params.put("lastIndexId",id );
//
//                        return params;
//                    }
//                };
//
//                stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
//
//            } else {
//                Toast.makeText(this, PackageActivity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
//            }
//
//        }
//
//        private void allSubjectAPIs(final String ids) {
//
//
//
//            if (utilis.isAvailInternet() == true) {
//
//                utilis.showProgress(PackageActivity.this);
//
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.question, new Response.Listener<String>() {
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
//                           // dbHelper.vatbook.execSQL("delete from " + dbHelper.questionslist);
//                            str_message = obj.getString("Message");
//                            String Count=obj.getString("count");
//                            JSONArray json = obj.getJSONArray("question");
//                            System.out.println(TAG + " allSubjectAPI Length " + json.length());
//                            for (int i = 0; i < json.length(); i++) {
//
//                                JSONObject jsonObject = json.getJSONObject(i);
//
//                                String Question=jsonObject.getString("question");
//                                String questionNumber=jsonObject.getString("questionNumber");
//                                String Option1=jsonObject.getString("Option1");
//                                String Option2=jsonObject.getString("Option2");
//                                String Option3=jsonObject.getString("Option3");
//                                String Option4=jsonObject.getString("Option4");
//                                String answer=jsonObject.getString("answer");
//
//
//                                try {
//
//                                    dbHelper.CreateTable(4);
//                                    dbHelper.vatbook.execSQL("INSERT INTO "
//                                            + dbHelper.questionresponse
//                                            + " (Questionno,Question,option1,option2,option3,option4,Answer)"
//                                            + " VALUES ('" + questionNumber + "','" + Question + "','" + Option1 + "','" + Option2 + "','" + Option3 + "','" + Option4 + "','" + answer + "')");
//                                    System.out.println("login value" + questionNumber + " " + Question);
//
//
//
//
//                                    Intent intent = new Intent(PackageActivity.this, Singlequestion.class); //OrderDetailsActivity
//
//                                    intent.putExtra("Qcount",Count );
//                                    intent.putExtra("Id",ids);
//
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    startActivity(intent);
//
//
//
//
//
//                                } catch (Exception e) {
//                                    System.out.println("loginresp=" + e.getMessage());
//                                }
//
//
//
//
//
//
//                            }
//
//
//
////timercall();
//                        } else if (Integer.parseInt(str_result) == 2) {
//                            str_message = obj.getString("Message");
//
//                            Toast.makeText(PackageActivity.this, str_message, Toast.LENGTH_SHORT).show();
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
//                    Toast.makeText(PackageActivity.this, PackageActivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();
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
//                    System.out.println("id"+utilis.strStudentID);
//                    params.put("lastIndexId",ids );
//                    params.put("studentIndexId",utilis.strStudentID );
//
//                    return params;
//                }
//            };
//
//            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
//
//        } else {
//            Toast.makeText(this, PackageActivity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
//        }
//    }
//    private void getValues() {
//        Bundle b = getIntent().getExtras();
//        if (b != null) {
//
////
//
//            examID = b.getString("examid");
//
//
//
//            System.out.println(" getValues " + b);
//
//
//
//
//
//        }
//    }



}



