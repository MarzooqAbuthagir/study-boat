package com.we_connect_students;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity {
    Utilis utilis;
    LinearLayout creat,iit,upsc,SSC,rrb,tnpsc,ibss,events;
    Toolbar toolbar;
    ActionBar actionBar = null;
    NavigationView navigationView;
    View header;
    ImageView img_dp;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    TextView textmarquee;
    String text="",  str_result="",str_message="",image = "";
    String TAG = "Main2Activity";

    RecyclerView recyclerView;
    private String examid;
    private static ViewPager mPager;
    private static int currentPage = 0;
//    private static final Integer[] XMEN= {R.drawable.mslide01, R.drawable.mslide02, R.drawable.msilde03};

    RequestQueue rq;

    ArrayList<packexam> examlist ;
    Bitmap mPhoto;

    private List<silder> silders;
    public static Handler handler;
    Timer swipeTimer;
    ViewPagerAdapter viewPagerAdapter;
    ReportAdapter reportAdapter;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Window window = Main2Activity.this.getWindow();
        utilis = new Utilis(Main2Activity.this);
//        dbHelper = new DBHelper(MainActivity.this);
        init();
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(Main2Activity.this, R.color.my_statusbar_color));
        creat = findViewById(R.id.creattest);
        tnpsc = findViewById(R.id.tnpsc);
        iit = findViewById(R.id.iit);
        upsc = findViewById(R.id.upsc);
        events = findViewById(R.id.events);
        textmarquee = findViewById(R.id.textmarquee);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        SSC = findViewById(R.id.SSC);
        rrb = findViewById(R.id.rrb);
        ibss = findViewById(R.id.ibss);

        textmarquee.setSelected(true);
        getnews();

        examlist = new ArrayList<packexam>();

        rq = Customizevolleysilder.getInstance(this).getRequestQueue();

        mPager = (ViewPager) findViewById(R.id.viewPager);

        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        silders = new ArrayList<>();


//        navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(Main2Activity.this);

//        header = navigationView.getHeaderView(0);
        img_dp = findViewById(R.id.imageView);


        // tnpsc=findViewById(R.id.tnpsc);
        initToolbar();


    }


    private void allSubjectAPI() {

        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(Main2Activity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.getexamdetails, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);

                        System.out.println(TAG + " allSubjectAPI response - " + response);

                        utilis.dismissProgress();

                        str_result = obj.getString("errorCode");
                        System.out.print(TAG + " allSubjectAPI result " + str_result);

                        if (Integer.parseInt(str_result) == 0) {

                            str_message = obj.getString("Message");

                            JSONArray json = obj.getJSONArray("result");
                            System.out.println(TAG + " allSubjectAPI Length " + json.length());

                            for (int i = 0; i < json.length(); i++) {

                                JSONObject jsonObject = json.getJSONObject(i);

                                packexam pack =new packexam();
                                pack.setExamIndexId(jsonObject.getString("examIndexId"));
                                pack.setExamTittle(jsonObject.getString("examTitle"));
                                pack.setSubTittle(jsonObject.getString("subTilte"));
                                pack.setLogo(jsonObject.getString("logo"));
                                pack.setStartColor(jsonObject.getString("startcolor"));
                                pack.setEndColor(jsonObject.getString("endcolor"));

                                examlist.add(pack);

//                                examid= jsonObject.getString("examIndexId");

//                                System.out.println(examid+ "   examid");

                            }

//                            Toast.makeText(getActivity(),"list size"+String.valueOf(listvalues.size()),Toast.LENGTH_LONG).show();
//                            Toast.makeText(getActivity(),listvalues.get(1).getpackageIndexId().toString(),Toast.LENGTH_LONG).show();
//                            System.out.println("list values"+listvalues.toString());
                            reportAdapter =new ReportAdapter(Main2Activity.this,examlist);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Main2Activity.this);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(reportAdapter);



                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(Main2Activity.this, str_message, Toast.LENGTH_SHORT).show();

                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }



            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(Main2Activity.this, Main2Activity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
//                    utilis.getSharedPreference();
//                    System.out.println("id"+utilis.strStudentID);
//                    params.put("studentId",utilis.strStudentID );
//
//
//                    System.out.println("exam id " +examID);
//
//
//
//
//
//                    params.put("examIndexId",examID );
//
//                    System.out.println((params));
//
//                    return params;
//                }
//            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(Main2Activity.this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(Main2Activity.this, Main2Activity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }







    class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
        Context con;
        ArrayList<packexam> examlist;
        ArrayList<packexam> searchValues = new ArrayList<>() ;

        public ReportAdapter(Context con, ArrayList<packexam> examlist) {

            this.con = con;
            this.examlist = examlist;
            this.searchValues.addAll(examlist);
        }




        @NonNull
        @Override
        public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.packexam, viewGroup, false);

            return new ReportAdapter.ViewHolder(view);
        }



        @Override
        public void onBindViewHolder(@NonNull final ReportAdapter.ViewHolder viewHolder, final int i) {

            Random rnd = new Random();
            int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
//            viewHolder.button.setBackgroundColor(currentColor);
//            viewHolder.button.setBackground(ContextCompat.getDrawable(Main2Activity.this,R.drawable.p2));

            viewHolder.examtittle.setText(examlist.get(i).getExamTittle());
            viewHolder.subtittle.setText(examlist.get(i).getSubTittle());
//            viewHolder.logo.loadUrl(examlist.get(i).getLogo());
            System.out.println("image path main2 "+examlist.get(i).getLogo());
            Glide.with(con).load(examlist.get(i).getLogo()).error(R.mipmap.ic_launcher).into(viewHolder.imageView);

//            new GetImageFromUrl(viewHolder.logo).execute(examlist.get(i).getLogo());


//            viewHolder.logo.setText(examlist.get(i).getLogo());

            String startColor="", endColor="";
            if (examlist.get(i).getStartColor().isEmpty()) {
                startColor = "#297fb8";
            } else {
                startColor = examlist.get(i).getStartColor();
            }

            if (examlist.get(i).getEndColor().isEmpty()) {
endColor = "#59a4d6";
            } else {
endColor = examlist.get(i).getEndColor();
            }

            // dynamic color coding
            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[] {Color.parseColor(startColor),  Color.parseColor(endColor)});
            gd.setCornerRadius(0f);

            viewHolder.mainLayout.setBackgroundDrawable(gd);


            viewHolder.creattest.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(Main2Activity.this, PackageActivity.class);
                    intent.putExtra("examid",examlist.get(i).getExamIndexId());
                    startActivity(intent);

                }


            });


        }

        @Override
        public int getItemCount() {
            return examlist.size();
        }

        public void filter(String charText) {

            charText = charText.toLowerCase(Locale.getDefault());
            examlist.clear();

            if (charText.length() == 0) {
                examlist.addAll(searchValues);

            } else {

                for (packexam p : searchValues) {

                    if (p.getExamTittle().toLowerCase(Locale.getDefault()).contains(charText) ||
                            p.getSubTittle().toLowerCase(Locale.getDefault()).contains(charText)) {
                        examlist.add(p);
                    }
                }

            }
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout creattest, mainLayout;
            TextView examtittle,subtittle;
//            WebView logo;
            ImageView imageView;


            public ViewHolder(View view) {
                super(view);
                examtittle= view.findViewById(R.id.examtittle);
                subtittle = view.findViewById(R.id.subtitle);
//                logo = view.findViewById(R.id.logo);
                imageView = view.findViewById(R.id.imageView);
                creattest=view.findViewById(R.id.creattest);
mainLayout = view.findViewById(R.id.mainLayout);
                view.setTag(itemView);
            }
        }
    }

//    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {
//        ImageView imageView;
//        public GetImageFromUrl(ImageView img){
//            this.imageView = img;
//        }
//        @Override
//        protected Bitmap doInBackground(String... url) {
//            String stringUrl = url[0];
//            mPhoto = null;
//            InputStream inputStream;
//            try {
//                inputStream = new java.net.URL(stringUrl).openStream();
//                mPhoto = BitmapFactory.decodeStream(inputStream);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return mPhoto;
//        }
//        @Override
//        protected void onPostExecute(Bitmap mPhoto){
//            super.onPostExecute(mPhoto);
//            imageView.setImageBitmap(mPhoto);
//        }
//    }




    private void getnews() {
        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(Main2Activity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.getnews, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);

                        System.out.println(TAG + " getnews response - " + response.toString());



                        utilis.dismissProgress();

                        String get =obj.getString("result");

//                        Toast.makeText(MainActivity.this,"result"+get,Toast.LENGTH_LONG).show();
                        textmarquee.setText(get);



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
                    Toast.makeText(Main2Activity.this, Main2Activity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    utilis.getSharedPreference();
                    System.out.println("id"+utilis.strStudentID);
                    params.put("studentIndexId", utilis.strStudentID);

                    System.out.println(TAG + " getnews inputs " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);



        } else {
            Toast.makeText(this, Main2Activity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }








    private void silder() {

        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(Main2Activity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.dynamicimage, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);

                        System.out.println(TAG + " image response - " + response.toString());



                        utilis.dismissProgress();

                        String get =obj.getString("errorCode");

                        if (Integer.parseInt(get) == 0) {



                            str_message = obj.getString("Message");

                            final JSONArray json = obj.getJSONArray("result");
                            for (int i = 0; i < json.length(); i++) {

                                silder silder = new silder();

                                try {


                                    JSONObject jsonObject = json.getJSONObject(i);

                                    silder.setImage(jsonObject.getString("image"));
                                } catch (JSONException e){

                                }


                                silders.add(silder);
                            }

                            setViewPager(json.length());

                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(Main2Activity.this, str_message, Toast.LENGTH_SHORT).show();

                        }
                        allSubjectAPI();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        utilis.dismissProgress();
                        allSubjectAPI();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(Main2Activity.this, Main2Activity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();
                    allSubjectAPI();
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
            }) ;
//            {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//
//                    utilis.getSharedPreference();
//                    System.out.println("id"+utilis.strStudentID);
//                    params.put("studentIndexId", utilis.strStudentID);
//
//                    System.out.println(TAG + " getnews inputs " + params);
//                    return params;
//                }
//            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Customizevolleysilder.getInstance(this).addToRequestQueue(stringRequest);



        } else {
            Toast.makeText(this, Main2Activity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }

    private void setViewPager(final int length) {
        viewPagerAdapter = new ViewPagerAdapter(silders, Main2Activity.this);

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

            dots[j] = new ImageView(Main2Activity.this);
            dots[j].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[j], params);

        }
        dots[dotPosition].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
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
        startActivity(new Intent(Main2Activity.this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);

        SearchManager searchManager = (SearchManager) getApplicationContext().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            EditText txtSearch = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
            txtSearch.setTextColor(Color.WHITE);
            ImageView searchClose = (ImageView) searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
            searchClose.setImageResource(R.drawable.ic_close_24);
            ImageView searchIcon = (ImageView)searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
            searchIcon.setImageResource(R.drawable.ic_arrow_back);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(Main2Activity.this.getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                public boolean onQueryTextChange(String newText) {
                    if (reportAdapter != null) {
                        reportAdapter.filter(newText);
                    }
                    return true;
                }

                public boolean onQueryTextSubmit(String query) {
                    return true;
                }
            });
        }

        return super.onCreateOptionsMenu(menu);
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

    private void init() {
//        for(int i=0;i<XMEN.length;i++)
//            XMENArray.add(XMEN[i]);

//        mPager = (ViewPager) findViewById(R.id.viewPager);
//        mPager.setAdapter(new MyAdapter(Main2Activity.this,XMENArray));
//        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
//        indicator.setViewPager(mPager);
//
//        // Auto start of viewpager
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == XMEN.length) {
//                    currentPage = 0;
//                }
//                mPager.setCurrentItem(currentPage++, true);
//            }
//        };
//        swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 500, 3000);
    }



}

