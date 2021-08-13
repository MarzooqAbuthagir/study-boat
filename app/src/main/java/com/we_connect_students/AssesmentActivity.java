package com.we_connect_students;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Locale;
import java.util.Map;

import static com.we_connect_students.Singlequestion.testName;

public class AssesmentActivity extends AppCompatActivity {
    String TAG = "Package";
    DBHelper dbHelper;
    Utilis utilis;

    Toolbar toolbar;
    ActionBar actionBar = null;

    RecyclerView recyclerView;

    String str_result = "", str_message = "", text = "", time = "10", isCorrect = "", isWrong = "", isSkip = "", isAll = "", Incliude = "";
    ArrayList<packlist> listvalues = new ArrayList<packlist>();
    int minteger = 0;
    public static CheckBox Wrong;
    public static CheckBox Correctly;
    public static CheckBox Skipped;
    public static CheckBox All, no;
    public static Button submit;
    public static LinearLayout allcheck;

    TextView tvEmpty;
    ReportAdapter reportAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_assment);


        Window window = AssesmentActivity.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(AssesmentActivity.this, R.color.my_statusbar_color1));
        utilis = new Utilis(AssesmentActivity.this);
        initToolbar();
        dbHelper = new DBHelper(AssesmentActivity.this);
        recyclerView = findViewById(R.id.recyclerView);
        tvEmpty = findViewById(R.id.tvEmpty);
        allSubjectAPI();
    }


    private void allSubjectAPI() {
        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(AssesmentActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.packagess, new Response.Listener<String>() {
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

                                packlist pack = new packlist(jsonObject.getString("subjectName"),
                                        jsonObject.getString("testPackageIndexId"), jsonObject.getString("noOfQuestion"),
                                        jsonObject.getString("testName"), jsonObject.getString("examTitle"),
                                        jsonObject.getString("subTitle"), jsonObject.getString("testTime"), jsonObject.getString("flag"));

                                listvalues.add(pack);

                            }

                            recyclerView.setVisibility(View.VISIBLE);
                            tvEmpty.setVisibility(View.GONE);
                            reportAdapter = new ReportAdapter(AssesmentActivity.this, listvalues);
                            recyclerView.setAdapter(reportAdapter);


                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

//                            Toast.makeText(AssesmentActivity.this, str_message, Toast.LENGTH_SHORT).show();

                            recyclerView.setVisibility(View.GONE);
                            tvEmpty.setVisibility(View.VISIBLE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(AssesmentActivity.this, AssesmentActivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, AssesmentActivity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("RestrictedApi")
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
        startActivity(new Intent(AssesmentActivity.this, MainActivity.class));
        finish();
    }


    class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
        Context con;
        private ArrayList<packlist> arrayList;
        private ArrayList<packlist> searchValues = new ArrayList<>();

        public ReportAdapter(Context con, ArrayList<packlist> listvalues) {
            this.con = con;
            this.arrayList = listvalues;
            this.searchValues.addAll(listvalues);
        }

        @NonNull
        @Override
        public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pack_content, viewGroup, false);
            return new ReportAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ReportAdapter.ViewHolder viewHolder, final int i) {
            viewHolder.subject.setText(arrayList.get(i).getsname());

            viewHolder.Testname.setText(arrayList.get(i).gettname());
            viewHolder.No.setText(arrayList.get(i).getnoq());

            viewHolder.subtittle.setText(arrayList.get(i).getsubTitle());
            viewHolder.title.setText(arrayList.get(i).getexamTitle());


//            viewHolder.buy.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(AssesmentActivity.this, "COMING SOON", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//
//            viewHolder.viewdet.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//
//                    startActivity(new Intent(AssesmentActivity.this, Packdetails.class));
//                    finish();
//                }
//            });


            viewHolder.reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String check = arrayList.get(i).getchecktest();


                    if (check.equals("YES")) {


                        AlertDialog.Builder builder2 = new AlertDialog.Builder(AssesmentActivity.this);
                        builder2.setMessage("You have not completed the previous test.");
                        builder2.setPositiveButton("Resume Test", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                final String id = arrayList.get(i).gettid();


                                final String testtime = arrayList.get(i).gettestTime();
                                System.out.println("time of life" + arrayList.get(i).gettestTime());
                                String Wrongt = "", Correctlyt = "", Skippedt = "", Allt = "";

                                allSubjectAPIsnew(id, testtime, Wrongt, Correctlyt, Skippedt, Allt, arrayList.get(i).gettname());

                            }

                        });


                        builder2.show();


                    } else {


                        final String id = arrayList.get(i).gettid();


                        final String testtime = arrayList.get(i).gettestTime();

                        final String testName = arrayList.get(i).gettname();

                        final Dialog dialogs = new Dialog(AssesmentActivity.this);
                        dialogs.setContentView(R.layout.custom);


                        Button dialogButton = (Button) dialogs.findViewById(R.id.dialogButtonOK);


                        Wrong = (CheckBox) dialogs.findViewById(R.id.Wrong);
                        Correctly = (CheckBox) dialogs.findViewById(R.id.correctly);
                        Skipped = (CheckBox) dialogs.findViewById(R.id.Skipped);
                        All = (CheckBox) dialogs.findViewById(R.id.All);
//    no = (CheckBox)dialogs. findViewById(R.id.correctly);
                        submit = (Button) dialogs.findViewById(R.id.submit);
//    allcheck = (LinearLayout)dialogs. findViewById(R.id.);
//


                        Wrong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (Wrong.isChecked()) {
//                All.setChecked(false);

                                } else {


                                }
                            }
                        });


                        Skipped.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (Skipped.isChecked()) {
//                All.setChecked(false);

                                } else {


                                }
                            }
                        });

                        Correctly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (Correctly.isChecked()) {
//                Wrong.setChecked(false);
//                Skipped.setChecked(false);
//                All.setChecked(false);

                                } else {


                                }
                            }
                        });


                        All.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (All.isChecked()) {
//                Wrong.setChecked(false);
//                Skipped.setChecked(false);
//                allcheck.setVisibility(View.VISIBLE);
                                } else {

//                allcheck.setVisibility(View.GONE);
                                }
                            }
                        });


//    Correct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//        @Override
//        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//            if(Correct.isChecked()){
//
//
//                no.setChecked(false);
//            }
//            else{
//
//                no.setChecked(true);
//            }
//        }
//    });
//
//    no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//        @Override
//        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//            if(no.isChecked()){
//
//
//                Correct.setChecked(false);
//            }
//            else{
//
//                Correct.setChecked(true);
//            }
//        }
//    });

                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String Wrongt = "", Correctt = "", Skippedt = "", Allt = "";
                                if (Wrong.isChecked()) {
                                    Wrongt = "WrongQuestions ";
                                    isWrong = "YES";
                                } else {
                                    Wrongt = "";
                                    isWrong = "";

                                    System.out.println("wromgnot");
                                }

                                if (Skipped.isChecked()) {
                                    Skippedt = "SkippedQuestions";
                                    isSkip = "YES";
                                } else {
                                    Skippedt = "";
                                    isSkip = "";
                                    System.out.println("skipno");
                                }
                                if (Correctly.isChecked()) {
                                    Correctt = "Correctly Questions";
                                    isCorrect = "YES";
                                } else {
                                    Correctt = "";
                                    System.out.println("correctno");
                                    isCorrect = "";
                                }
                                if (All.isChecked()) {
                                    Allt = "AllQuestions";
                                    isAll = "YES";
                                } else {
                                    Allt = "";
                                    System.out.println("allcheck");
                                    isAll = "";
                                }
//            if (no.isChecked()){
//                Incliude="NO";
//            }else {
//
//            }
//
//            if (Correct.isChecked()){
//                Incliude="YES";
//            }else {
//
//            }


                                if (Wrongt.equals("") && Skippedt.equals("") && Correctt.equals("") && Allt.equals("")) {
                                    Toast.makeText(getApplicationContext(), "select any one", Toast.LENGTH_SHORT).show();
                                } else {
                                    dialogs.dismiss();

                                    System.out.println("INciuled" + Incliude);
                                    allSubjectAPIs(id, testtime, Wrongt, Correctt, Skippedt, Allt, testName);
                                }
                            }
                        });


                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogs.dismiss();
                            }
                        });
                        dialogs.show();


                    }


                }
            });
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            listvalues.clear();

            if (charText.length() == 0) {
                listvalues.addAll(searchValues);

            } else {

                for (packlist p : searchValues) {

                    if (p.getsname().toLowerCase(Locale.getDefault()).contains(charText) ||
                            p.getnoq().toLowerCase(Locale.getDefault()).contains(charText) ||
                            p.gettname().toLowerCase(Locale.getDefault()).contains(charText) ||
                            p.getexamTitle().toLowerCase(Locale.getDefault()).contains(charText) ||
                            p.getsubTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                        listvalues.add(p);
                    }
                }

            }
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout button, buy, viewdet;
            TextView subject, No, subtittle, title, Testname;
            LinearLayout reset;


            public ViewHolder(View view) {
                super(view);

                subject = view.findViewById(R.id.sname);
                No = view.findViewById(R.id.noques);
                Testname = view.findViewById(R.id.tname);
                reset = view.findViewById(R.id.reset);
                subtittle = view.findViewById(R.id.subtittle);
                title = view.findViewById(R.id.title);
                //buy=view.findViewById(R.id.buy);
                //viewdet=view.findViewById(R.id.viewdet);
                view.setTag(itemView);
            }
        }
    }


    private void cehckreattemtpt(final String id) {


        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(AssesmentActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.reattampt, new Response.Listener<String>() {
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
                            // dbHelper.vatbook.execSQL("delete from " + dbHelper.questionslist);
                            str_message = obj.getString("Message");
                            JSONObject json = obj.getJSONObject("result");

                            String ids = json.getString("lastIndexId");

                            //  allSubjectAPIs(ids);

                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(AssesmentActivity.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(AssesmentActivity.this, AssesmentActivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
                    params.put("lastIndexId", id);

                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, AssesmentActivity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }

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
            searchView.setSearchableInfo(searchManager.getSearchableInfo(AssesmentActivity.this.getComponentName()));
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

    private void allSubjectAPIs(final String ids, final String testtime, final String wrongt, final String correctt, final String skippedt, final String allt, final String testName) {


        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(AssesmentActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.requestion, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);

                        System.out.println(TAG + " allSubjectAPI response - " + response);


                        str_result = obj.getString("errorCode");
                        System.out.print(TAG + " allSubjectAPI result " + str_result);

                        if (Integer.parseInt(str_result) == 0) {
                            // dbHelper.vatbook.execSQL("delete from " + dbHelper.questionslist);
                            str_message = obj.getString("Message");
                            String Count = obj.getString("count");
                            System.out.println("qcount" + Count);
                            JSONArray json = obj.getJSONArray("question");
                            System.out.println(TAG + " allSubjectAPI Length " + json.length());
                            for (int i = 0; i < json.length(); i++) {

                                JSONObject jsonObject = json.getJSONObject(i);

                                String Question = jsonObject.getString("question");
                                String questionIndexId = jsonObject.getString("questionIndexId");
                                String questionNumber = jsonObject.getString("questionNumber");
                                String Option1 = jsonObject.getString("Option1");
                                String Option2 = jsonObject.getString("Option2");
                                String Option3 = jsonObject.getString("Option3");
                                String Option4 = jsonObject.getString("Option4");
                                String answer = jsonObject.getString("answer");
                                String explanation = jsonObject.getString("explanation");
                                String subjectName = jsonObject.getString("subjectName");
                                String chapterName = jsonObject.getString("chapterName");

                                Question = Question.replaceAll("'", " \"");
                                Option1 = Option1.replaceAll("'", " \"");
                                Option2 = Option2.replaceAll("'", " \"");
                                Option3 = Option3.replaceAll("'", "\" ");
                                Option4 = Option4.replaceAll("'", "\" ");
                                answer = answer.replaceAll("'", " \"");


                                Question = Question.replaceAll("''", " \"\"");
                                Option1 = Option1.replaceAll("''", " \"\"");
                                Option2 = Option2.replaceAll("''", " \"\"");
                                Option3 = Option3.replaceAll("''", "\"\" ");
                                Option4 = Option4.replaceAll("''", "\"\" ");
                                answer = answer.replaceAll("''", " \"\"");

                                try {
                                    String sectionids = "0";
                                    dbHelper.CreateTable(4);
                                    dbHelper.vatbook.execSQL("INSERT INTO "
                                            + dbHelper.questionresponse
                                            + " (Questionno,Question,option1,option2,option3,option4,Answer,Explanation,subjectName,chapterName,questionIndexId,sectionid)"
                                            + " VALUES ('" + questionNumber + "','" + Question + "','" + Option1 + "','" + Option2 + "','" + Option3 + "','" + Option4 + "','" + answer + "','" + explanation + "','" + subjectName + "','" + chapterName + "','" + questionIndexId + "','" + sectionids + "')");
                                    System.out.println("login value" + questionNumber + " " + Question);


                                    try {
                                        String Acoun = "3";
                                        String selected = "";

                                        dbHelper.CreateTable(6);
                                        dbHelper.vatbook.execSQL("INSERT INTO "
                                                + dbHelper.selctedlist
                                                + "(Questionno, correctanser,selectedanser,Acount,Questoin,Explains,Answer,Option1,Option2,Option3,Option4,Wrongquestion,wronganser,subjectName,chapterName,questionIndexId)"
                                                + " VALUES ('" + questionNumber + "', '" + selected + "',  '" + selected + "','" + Acoun + "',  '" + Question + "',' " + explanation + "',' " + answer + "', ' " + Option1 + "',  ' " + Option2 + "',' " + Option3 + "',' " + Option4 + "',' " + minteger + "',' " + selected + "','" + subjectName + "','" + chapterName + "','" + questionIndexId + "')");


                                        System.out.println("insert6" + dbHelper.selctedlist
                                                + "(Questionno, correctanser,selectedanser,Acount,Questoin,Explains,Answer,Option1,Option2,Option3,Option4,Wrongquestion,wronganser)"
                                                + " VALUES ('" + questionNumber + "', '" + selected + "',  '" + selected + "','" + Acoun + "',  '" + Question + "',' " + explanation + "',' " + answer + "', ' " + Option1 + "',  ' " + Option2 + "',' " + Option3 + "',' " + Option4 + "',' " + minteger + "',' " + selected + "','" + subjectName + "','" + chapterName + "','" + questionIndexId + "')");

//                                    dbHelper.CreateTable(6);
//                                    dbHelper.vatbook.execSQL("INSERT INTO "
//                                            + dbHelper.selctedlist
//                                            + " (Questionno,Questoin,Option1,Option2,Option3,Option4,Answer,Explains)"
//                                            + " VALUES ('" + questionNumber + "','" + Question + "','" + Option1 + "','" + Option2 + "','" + Option3 + "','" + Option4 + "','" + answer + "','" + explanation + "')");
//                                    System.out.println("login value" + questionNumber + " " + Question);

                                    } catch (Exception e) {
                                        System.out.println("loginresp=" + e.getMessage());
                                    }


                                } catch (Exception e) {
                                    System.out.println("loginresp=" + e.getMessage());
                                }


                            }

                            utilis.dismissProgress();
                            Intent intent = new Intent(AssesmentActivity.this, Singlequestion.class); //OrderDetailsActivity

                            intent.putExtra("Qcount", Count);
                            intent.putExtra("Id", ids);
                            intent.putExtra("time", testtime);
                            intent.putExtra("testName", testName);
                            intent.putExtra("isCorrect", isCorrect);

                            intent.putExtra("isWrong", isWrong);
                            intent.putExtra("isSkip", isSkip);
                            intent.putExtra("isAll", isAll);

                            System.out.println("isall" + "" + isAll);

                            //  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            overridePendingTransition(0, 0);

//timercall();
                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

//                            Toast.makeText(AssesmentActivity.this, str_message, Toast.LENGTH_SHORT).show();


                            utilis.dismissProgress();

                            final AlertDialog.Builder builder = new AlertDialog.Builder(
                                    AssesmentActivity.this);
                            builder.setMessage("Selected option has no questions.");
                            builder.setNegativeButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            builder.show();


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(AssesmentActivity.this, AssesmentActivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
                    params.put("lastIndexId", ids);
                    params.put("studentIndexId", utilis.strStudentID);
                    params.put("WrongQuestions", wrongt);
                    params.put("RightQuestions", correctt);
                    params.put("SkippedQuestions", skippedt);
                    params.put("AllQuestions", allt);
                    params.put("isRightAnswerInclude", Incliude);


                    System.out.println("  Retest " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, AssesmentActivity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }


    private void allSubjectAPIsnew(final String ids, final String testtime, final String wrongt, final String correctt, final String skippedt, final String allt, final String gettname) {


        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(AssesmentActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.question, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);

                        System.out.println(TAG + " allSubjectAPI response - " + response);


                        str_result = obj.getString("errorCode");
                        System.out.print(TAG + " allSubjectAPI result " + str_result);

                        if (Integer.parseInt(str_result) == 0) {
                            // dbHelper.vatbook.execSQL("delete from " + dbHelper.questionslist);
                            str_message = obj.getString("Message");
                            String Count = obj.getString("count");
                            System.out.println("qcount" + Count);
                            JSONArray json = obj.getJSONArray("question");
                            System.out.println(TAG + " allSubjectAPI Length " + json.length());
                            for (int i = 0; i < json.length(); i++) {

                                JSONObject jsonObject = json.getJSONObject(i);

                                String Question = jsonObject.getString("question");
                                String questionIndexId = jsonObject.getString("questionIndexId");
                                String questionNumber = jsonObject.getString("questionNumber");
                                String Option1 = jsonObject.getString("Option1");
                                String Option2 = jsonObject.getString("Option2");
                                String Option3 = jsonObject.getString("Option3");
                                String Option4 = jsonObject.getString("Option4");
                                String answer = jsonObject.getString("answer");
                                String explanation = jsonObject.getString("explanation");
                                String subjectName = jsonObject.getString("subjectName");
                                String chapterName = jsonObject.getString("chapterName");

                                Question = Question.replaceAll("'", " \"");
                                Option1 = Option1.replaceAll("'", " \"");
                                Option2 = Option2.replaceAll("'", " \"");
                                Option3 = Option3.replaceAll("'", "\" ");
                                Option4 = Option4.replaceAll("'", "\" ");
                                answer = answer.replaceAll("'", " \"");


                                Question = Question.replaceAll("''", " \"\"");
                                Option1 = Option1.replaceAll("''", " \"\"");
                                Option2 = Option2.replaceAll("''", " \"\"");
                                Option3 = Option3.replaceAll("''", "\"\" ");
                                Option4 = Option4.replaceAll("''", "\"\" ");
                                answer = answer.replaceAll("''", " \"\"");

                                try {
                                    String sectionids = "0";
                                    dbHelper.CreateTable(4);
                                    dbHelper.vatbook.execSQL("INSERT INTO "
                                            + dbHelper.questionresponse
                                            + " (Questionno,Question,option1,option2,option3,option4,Answer,Explanation,subjectName,chapterName,questionIndexId,sectionid)"
                                            + " VALUES ('" + questionNumber + "','" + Question + "','" + Option1 + "','" + Option2 + "','" + Option3 + "','" + Option4 + "','" + answer + "','" + explanation + "','" + subjectName + "','" + chapterName + "','" + questionIndexId + "','" + sectionids + "')");
                                    System.out.println("login value" + questionNumber + " " + Question);


                                    try {
                                        String Acoun = "3";
                                        String selected = "";

                                        dbHelper.CreateTable(6);
                                        dbHelper.vatbook.execSQL("INSERT INTO "
                                                + dbHelper.selctedlist
                                                + "(Questionno, correctanser,selectedanser,Acount,Questoin,Explains,Answer,Option1,Option2,Option3,Option4,Wrongquestion,wronganser,subjectName,chapterName,questionIndexId)"
                                                + " VALUES ('" + questionNumber + "', '" + selected + "',  '" + selected + "','" + Acoun + "',  '" + Question + "',' " + explanation + "',' " + answer + "', ' " + Option1 + "',  ' " + Option2 + "',' " + Option3 + "',' " + Option4 + "',' " + minteger + "',' " + selected + "','" + subjectName + "','" + chapterName + "','" + questionIndexId + "')");


                                        System.out.println("insert6" + dbHelper.selctedlist
                                                + "(Questionno, correctanser,selectedanser,Acount,Questoin,Explains,Answer,Option1,Option2,Option3,Option4,Wrongquestion,wronganser)"
                                                + " VALUES ('" + questionNumber + "', '" + selected + "',  '" + selected + "','" + Acoun + "',  '" + Question + "',' " + explanation + "',' " + answer + "', ' " + Option1 + "',  ' " + Option2 + "',' " + Option3 + "',' " + Option4 + "',' " + minteger + "',' " + selected + "','" + subjectName + "','" + chapterName + "','" + questionIndexId + "')");

//                                    dbHelper.CreateTable(6);
//                                    dbHelper.vatbook.execSQL("INSERT INTO "
//                                            + dbHelper.selctedlist
//                                            + " (Questionno,Questoin,Option1,Option2,Option3,Option4,Answer,Explains)"
//                                            + " VALUES ('" + questionNumber + "','" + Question + "','" + Option1 + "','" + Option2 + "','" + Option3 + "','" + Option4 + "','" + answer + "','" + explanation + "')");
//                                    System.out.println("login value" + questionNumber + " " + Question);

                                    } catch (Exception e) {
                                        System.out.println("loginresp=" + e.getMessage());
                                    }


                                } catch (Exception e) {
                                    System.out.println("loginresp=" + e.getMessage());
                                }


                            }

                            utilis.dismissProgress();
                            Intent intent = new Intent(AssesmentActivity.this, Singlequestion.class); //OrderDetailsActivity

                            intent.putExtra("Qcount", Count);
                            intent.putExtra("Id", ids);
                            intent.putExtra("time", testtime);
                            intent.putExtra("testName", gettname);
                            intent.putExtra("isCorrect", isCorrect);

                            intent.putExtra("isWrong", isWrong);
                            intent.putExtra("isSkip", isSkip);
                            intent.putExtra("isAll", isAll);

                            System.out.println("isall" + "" + isAll);

                            //  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
//timercall();
                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(AssesmentActivity.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(AssesmentActivity.this, AssesmentActivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
                    params.put("lastIndexId", ids);
                    params.put("studentIndexId", utilis.strStudentID);
                    params.put("WrongQuestions", wrongt);
                    params.put("RightQuestions", correctt);
                    params.put("SkippedQuestions", skippedt);
                    params.put("AllQuestions", allt);
                    params.put("isRightAnswerInclude", Incliude);


                    System.out.println("  Retest " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, AssesmentActivity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }


}
