package com.we_connect_students;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FgtPwdActivity extends AppCompatActivity {
    String TAG = "fgtpwdActivity";
    EditText etMobNum;
    Button btnFgtPwd;
    String strMobNum = "";
    Utilis utilis;
    String str_result = "";
    String str_message = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fgtpwd);
        utilis = new Utilis(FgtPwdActivity.this);
        Window window = FgtPwdActivity.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(FgtPwdActivity.this,R.color.voil2));




        etMobNum = findViewById(R.id.etMobNum);
        btnFgtPwd = findViewById(R.id.btnFgtPwd);

        btnFgtPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strMobNum = etMobNum.getText().toString().trim();



             allSubjectAPI();




//                if (strMobNum.equals("")) {
//                    Toast.makeText(FgtPwdActivity.this, "Enter mobile number", Toast.LENGTH_SHORT).show();
//                } else {
//                    startActivity(new Intent(FgtPwdActivity.this, LoginActivity.class));
//                    finish();
//                }
            }
        });
    }


    private void allSubjectAPI( ) {



        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(FgtPwdActivity.this);

            StringRequest stringRequest
                    = new StringRequest(Request.Method.POST, utilis.Api + utilis.sendcredentials, new Response.Listener<String>() {
                @SuppressLint("SetJavaScriptEnabled")
                @Override
                public void onResponse(String response) {

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);

                        System.out.println(TAG + " allSubjectAPI response - " + response);

                        utilis.dismissProgress();

                        str_result = obj.getString("errorCode");
                        if (Integer.parseInt(str_result) == 1) {
                            str_message = obj.getString("Message");

                            Toast.makeText(FgtPwdActivity.this, str_message, Toast.LENGTH_SHORT).show();

                        } else if (Integer.parseInt(str_result) == 0) {

                            str_message = obj.getString("Message");

                            showConfirmDialog();

                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(FgtPwdActivity.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(FgtPwdActivity.this, FgtPwdActivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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



                    params.put("mobileNumber",strMobNum );

                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        }

    }

    private void showConfirmDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                FgtPwdActivity.this);
        builder.setMessage("Your Username & Password has been sent to your registered Mobile Number.");
        builder.setNegativeButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss();
                        startActivity(new Intent(FgtPwdActivity.this, LoginActivity.class));
                        finish();
                    }
                });
        builder.show();
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(FgtPwdActivity.this, LoginActivity.class));
        finish();
    }
}
