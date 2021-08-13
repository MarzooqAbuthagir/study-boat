package com.we_connect_students;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.we_connect_students.TeststepTwo.tv;

public class LoginActivity extends AppCompatActivity {
    String TAG = "LoginActivity";

    Utilis utilis;

    EditText etMobNum, etPassword;
    Button btnLogin;
    TextView tvFgtPwd, tvRegister;
    String strMobNum = "", strPwd = "", str_result = "", str_message = "";
    String str_name = "", str_mobNum = "", str_eMail = "", str_studentId = "",str_fname="";
    DBHelper dbHelper;
    String android_id="";
    MyApplication app;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Window window = LoginActivity.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(LoginActivity.this,R.color.voil2));





        app = (MyApplication) getApplication();
        utilis = new Utilis(LoginActivity.this);
        etMobNum = findViewById(R.id.etMobNum);
        etPassword = findViewById(R.id.etPassword);
        tvFgtPwd = findViewById(R.id.tvFgtPwd);
        tvRegister = findViewById(R.id.tvRegister);
        btnLogin = findViewById(R.id.btnLogin);

        dbHelper = new DBHelper(LoginActivity.this);

 android_id = Settings.Secure.getString(LoginActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);


        tableExists(dbHelper.vatbook,dbHelper.selctedlist);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strMobNum = etMobNum.getText().toString().trim();
                strPwd = etPassword.getText().toString().trim();

                if (strMobNum.equals("")) {
                    Toast.makeText(LoginActivity.this, "Enter mobile number", Toast.LENGTH_SHORT).show();
                } else if (strPwd.equals("")) {
                    Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                } else {
                    loginAPI();
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        tvFgtPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, FgtPwdActivity.class));
                finish();
            }
        });
    }

    private void loginAPI() {
        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(LoginActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.login, new Response.Listener<String>() {
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

                            Toast.makeText(LoginActivity.this, str_message, Toast.LENGTH_SHORT).show();

                        } else if (Integer.parseInt(str_result) == 0) {

                            str_message = obj.getString("Message");


                            JSONObject json = obj.getJSONObject("result");

                            str_name = json.getString("name");
                            str_fname = json.getString("fatherName");
                            str_mobNum = json.getString("mobileNumber");
                            str_eMail = json.getString("emailAddress");
                            str_studentId = json.getString("studentIndexId");




                            saveDetails();


                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(LoginActivity.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(LoginActivity.this, LoginActivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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

                    params.put("mobileNumber", strMobNum);
                    params.put("password", strPwd);

                    System.out.println(TAG + " loginAPI inputs " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);



        } else {
            Toast.makeText(this, LoginActivity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveDetails() {

        utilis.onSharedPreference();
        utilis.editor.putString(utilis.NAME, str_name);
        utilis.editor.putString(utilis.FNAME, str_fname);
        utilis.editor.putString(utilis.MOB_NUM, str_mobNum);
        utilis.editor.putString(utilis.EMAIL, str_eMail);
        utilis.editor.putString(utilis.STUDENT_ID, str_studentId);
        utilis.editor.commit();


        System.out.println("check");





        checkapicall(android_id);

    }


    private void checkapicall(final String android_id) {



        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(LoginActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.checkdevice, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);

                        System.out.println(TAG + " loginAPI response - " + response);

                        utilis.dismissProgress();

                        str_result = obj.getString("errorCode");
                        System.out.print(TAG + " loginAPI result " + str_result);

                        if (Integer.parseInt(str_result) == 3) {
                            str_message = obj.getString("Message");




                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("Already logged in on another device")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //do things


                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();




                        } else if (Integer.parseInt(str_result) == 0) {

                            str_message = obj.getString("Message");

                            check(android_id,str_studentId);
                            System.out.println("checkdevice yes no");


                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(LoginActivity.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(LoginActivity.this, LoginActivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
                    params.put("studentIndexId", utilis.strStudentID);
                    params.put("deviceId",android_id);

                    System.out.println(TAG + " loginAPI inputs " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);



        } else {
            Toast.makeText(this, LoginActivity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
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
                System.out.println("database");
                return true;


            }
            System.out.println("nodatabase");
            cursor.close();
        }
        return false;
    }



    public void check(final String android_id, final String str_studentId) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.updatetoken, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(response);

                    System.out.println(TAG + " sendError response - " + response);

                    String str_result = obj.getString("errorCode");
                    System.out.print(TAG + " sendError result" + str_result);
                    LoginSharedPreference.setLoggedIn(LoginActivity.this, true);

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NoConnectionError) {

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

                params.put("studentIndexId", str_studentId);
                params.put("deviceId", android_id);


                System.out.println(TAG + " sendError inputs " + params);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }



}
