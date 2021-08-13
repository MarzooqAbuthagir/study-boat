package com.we_connect_students;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class ReportPAge extends AppCompatActivity {

    String TAG = "ReportActivrity";
    DBHelper dbHelper;
    Utilis utilis;

    Toolbar toolbar;
    ActionBar actionBar = null;

    RecyclerView recyclerView;

    String str_result = "", str_message = "",text="";
    ArrayList<Reportlist> listvalues = new ArrayList<Reportlist>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        utilis = new Utilis(ReportPAge.this);
        initToolbar();
        dbHelper = new DBHelper(ReportPAge.this);
        recyclerView = findViewById(R.id.recyclerView);

        listvalues = new ArrayList<>();


        allSubjectAPI();




    }
    private void allSubjectAPI() {
        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(ReportPAge.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.report, new Response.Listener<String>() {
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

                                Reportlist report = new Reportlist(jsonObject.getString("subjectName"),
                                        jsonObject.getString("noOfQuestion"),jsonObject.getString("testName"),
                                        jsonObject.getString("correctAnswer"), jsonObject.getString("wrongAnswer"), jsonObject.getString("lastIndexId"));

                                listvalues.add(report);

                            }

                            ReportAdapter reportAdapter = new ReportAdapter(ReportPAge.this, listvalues);
                            recyclerView.setAdapter(reportAdapter);


                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(ReportPAge.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(ReportPAge.this, ReportPAge.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
                    System.out.println("id"+utilis.strStudentID);

                    params.put("studentIndexId",utilis.strStudentID );
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, ReportPAge.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
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
        startActivity(new Intent(ReportPAge.this, MainActivity.class));
        finish();
    }





//
//    private class ReportAdapter extends RecyclerView.Adapter {
//        public ReportAdapter(ReportPAge reportPAge, ArrayList<Reportlist> listvalues) {
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





    class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
        Context con;
        private ArrayList<Reportlist> arrayList;

        public ReportAdapter(Context con, ArrayList<Reportlist> listvalues) {
            this.con = con;
            this.arrayList = listvalues;
        }

        @NonNull
        @Override
        public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_content, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ReportAdapter.ViewHolder viewHolder, final int i) {
            viewHolder.subject.setText(arrayList.get(i).getsname());
            viewHolder.No.setText(arrayList.get(i).getnques());
            viewHolder.CANSWER.setText(arrayList.get(i).getcans());
            viewHolder.WAnswer.setText(arrayList.get(i).getwans());
            viewHolder.Testname.setText(arrayList.get(i).gettname());
viewHolder.reset.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String ids=arrayList.get(i).getlastIndexId();
        allSubjectAPIs(ids);
    }
});


                   }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout button;
            TextView subject,No,CANSWER,WAnswer,Testname;
Button reset;

            public ViewHolder(View view) {
                super(view);

                subject = view.findViewById(R.id.sname);
                No = view.findViewById(R.id.noques);
                CANSWER = view.findViewById(R.id.cans);
                WAnswer = view.findViewById(R.id.wans);
                Testname = view.findViewById(R.id.tname);
                reset= view.findViewById(R.id.reset);
                view.setTag(itemView);
            }
        }
    }

    private void allSubjectAPIs(final String ids) {



        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(ReportPAge.this);

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
                            String Count=obj.getString("count");
                            JSONArray json = obj.getJSONArray("question");
                            System.out.println(TAG + " allSubjectAPI Length " + json.length());
                            for (int i = 0; i < json.length(); i++) {

                                JSONObject jsonObject = json.getJSONObject(i);

                                String Question=jsonObject.getString("question");
                                String questionNumber=jsonObject.getString("questionNumber");
                                String Option1=jsonObject.getString("Option1");
                                String Option2=jsonObject.getString("Option2");
                                String Option3=jsonObject.getString("Option3");
                                String Option4=jsonObject.getString("Option4");
                                String answer=jsonObject.getString("answer");


                                try {

                                    dbHelper.CreateTable(4);
                                    dbHelper.vatbook.execSQL("INSERT INTO "
                                            + dbHelper.questionresponse
                                            + " (Questionno,Question,option1,option2,option3,option4,Answer)"
                                            + " VALUES ('" + questionNumber + "','" + Question + "','" + Option1 + "','" + Option2 + "','" + Option3 + "','" + Option4 + "','" + answer + "')");
                                    System.out.println("login value" + questionNumber + " " + Question);




                                    Intent intent = new Intent(ReportPAge.this, Singlequestion.class); //OrderDetailsActivity

                                    intent.putExtra("Qcount",Count );
                                    intent.putExtra("Id",ids);

                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);





                                } catch (Exception e) {
                                    System.out.println("loginresp=" + e.getMessage());
                                }






                            }




                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(ReportPAge.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(ReportPAge.this, ReportPAge.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
                    System.out.println("id"+utilis.strStudentID);
                    params.put("lastIndexId",ids );

                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, ReportPAge.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }


    }


}
