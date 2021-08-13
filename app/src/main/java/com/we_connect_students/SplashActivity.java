package com.we_connect_students;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
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

public class SplashActivity extends AppCompatActivity {
    String TAG = "SplashActivity";
    private static int SPLASH_TIME_OUT = 1000;
String versioncode="",str_result="",str_message="";
    Utilis utilis;
    Intent intent ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        utilis = new Utilis(SplashActivity.this);

        int versionCode = BuildConfig.VERSION_CODE;
        versioncode=String.valueOf(versionCode);
        toHideStatusBar();
        splashTimeOut();
    }

    private void splashTimeOut() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                versioncehck(versioncode,"Android");




            }
        }, SPLASH_TIME_OUT);
    }

    private void versioncehck(final String versioncode, final String android) {





        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(SplashActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.checkupdate, new Response.Listener<String>() {
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

                            Toast.makeText(SplashActivity.this, str_message, Toast.LENGTH_SHORT).show();

                        } else if (Integer.parseInt(str_result) == 0) {


                            if (LoginSharedPreference.getLoggedStatus(SplashActivity.this)) {
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                finish();

                            } else {
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                finish();

                            }


                           // saveDetails();


                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");






                            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                            builder.setTitle("New Version Available");
                            builder.setMessage("Please update application to new version to continue")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {


                                            intent = new Intent(Intent.ACTION_VIEW);

                                            //Copy App URL from Google Play Store.
                                            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.we_connect_students"));

                                            startActivity(intent);


                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();






                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(SplashActivity.this, SplashActivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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

                    params.put("versioncode", versioncode);
                    params.put("osname", android);

                    System.out.println(TAG + " loginAPI inputs " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);



        } else {
            Toast.makeText(this, SplashActivity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }












    }

    private void toHideStatusBar() {
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
