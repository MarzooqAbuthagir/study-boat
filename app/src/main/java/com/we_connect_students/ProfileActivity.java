package com.we_connect_students;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    Utilis utilis;

    Toolbar toolbar;
    ActionBar actionBar = null;

    TextView name,mobile,email,testgiven,totlaques,correct,wrong,fname, qualification, school;
    String str_result="",str_message="";
    Button back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        utilis = new Utilis(ProfileActivity.this);
        name=findViewById(R.id.name);
        fname=findViewById(R.id.fname);
        mobile=findViewById(R.id.mobile);
        email=findViewById(R.id.email);
        qualification = findViewById(R.id.qualification);
        school = findViewById(R.id.school);

        testgiven=findViewById(R.id.testgiven);
        totlaques=findViewById(R.id.totlaques);
        correct=findViewById(R.id.state);
        wrong=findViewById(R.id.disc);

        back=findViewById(R.id.backb);
        initToolbar();
        utilis.getSharedPreference();








back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        back();
    }
});
        submittrsult();


    }



    private void submittrsult( ) {




        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(ProfileActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.profilecount, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);

                        System.out.println(" allSubjectAPI response - " + response);

                        utilis.dismissProgress();

                        str_result = obj.getString("errorCode");
                        System.out.print( " allSubjectAPI result " + str_result);

                        if (Integer.parseInt(str_result) == 0) {

                            str_message = obj.getString("Message");

                            JSONObject emp=obj.getJSONObject("result");

                            String  studentName = emp.getString("studentName");
                            String  fatherName = emp.getString("fatherName");
                            String  emails = emp.getString("email");
                            String  mobileNumber = emp.getString("mobileNumber");

                            String  state = emp.getString("state");
                            String  district = emp.getString("district");

                            String  strQualification = emp.getString("Qualification");
                            String  strSchool = emp.getString("SchoolName");

                            name.setText(studentName);
                            mobile.setText(mobileNumber);
                            if(!emails.isEmpty()) {
                                email.setText(emails);
                            } else {
                                email.setText("Email Not Registered");
                            }
                            fname.setText(fatherName);

                            qualification.setText(strQualification);
                            school.setText(strSchool);

                            correct.setText(state);
                            wrong.setText(district);
                            String totalquestioncount = emp.getString("noOfPackagePurchase");

                            testgiven.setText(totalquestioncount);

                            String totalcorrectcount = emp.getString("noOfTestTaken");

                            totlaques.setText(totalcorrectcount);



                            String totaltestcount=emp.getString("subscriptionPackDetails");



                          String  totalwrongcount = emp.getString("totalwrongcount");
//                          if(totaltestcount.equals("")){
//                              correct.setText("Subscription Pack Details :"+" - ");
//                          } else if(totaltestcount.equals("null")){
//                              correct.setText("Subscription Pack Details :"+" - ");
//                            }
//                          else{
//                              correct.setText("Subscription Pack Details :"+" "+totaltestcount);
//                          }


//                            if(totalcorrectcount.equals("")){
//                                totlaques.setText("No of Test Taken:"+" - ");
//                            }else if(totalcorrectcount.equals("null")){
//                                totlaques.setText("No of Test Taken:"+" - ");
//                            }
//                            else{
//                                totlaques.setText("No of Test Taken:"+" "+totalcorrectcount);
//                            }
//
//
//                            if(totalquestioncount.equals("")){
//                                testgiven.setText("No of Package Purchase :"+" -  ");
//                            }else if(totalquestioncount.equals("null")) {
//                                testgiven.setText("No of Package Purchase :"+" - ");
//                            }
//                            {
//                                    testgiven.setText("No of Package Purchase :"+" "+totalquestioncount);
//                                }
//
//                            totlaques.setText("No of Test Taken:"+" "+totalcorrectcount);
//
//                            testgiven.setText("No of Package Purchase :"+" "+totalquestioncount);

                            //wrong.setText("Total InCorrect Answer :"+" "+totalwrongcount);








//                            totlaques.setText("Total Question :"+""+totalquestioncount);
//                            correct.setText("Total Correct Answer :"+""+totalcorrectcount);
//                            wrong.setText("Total InCorrect Answer :"+""+totalwrongcount);




                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(ProfileActivity.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(ProfileActivity.this, ProfileActivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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


                    System.out.println( "  inputs " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, ProfileActivity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        finish();
    }
}
