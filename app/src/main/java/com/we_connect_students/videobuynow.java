package com.we_connect_students;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
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

public class videobuynow extends AppCompatActivity {

    String TAG = "Package";
    DBHelper dbHelper;
    Utilis utilis;

    Toolbar toolbar;
    ActionBar actionBar = null;



    String str_result = "", str_message = "",text="",str_messageresult="",referl="";

    Button apply,close,check;
    TextView prices,valitity,Name;

    EditText reffr;

    String id="1",name="",validity="",price="";
    String examid="";

    FragmentManager fm = null;
    //  DefaultNoNet defaultNoNet = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videobuynow);

        fm = getSupportFragmentManager();


        Window window = videobuynow.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(videobuynow.this, R.color.voil));
        utilis = new Utilis(videobuynow.this);
        initToolbar();
        Intent intent = getIntent();
        id = intent.getStringExtra("Qcount");
        examid = intent.getStringExtra("examid");

        name = intent.getStringExtra("name");
        validity = intent.getStringExtra("validity");
        price = intent.getStringExtra("price");







        apply=findViewById(R.id.apply);

        close=findViewById(R.id.close);

        check=findViewById(R.id.check);
        prices=findViewById(R.id.prices);
        reffr=findViewById(R.id.reffr);
        reffr.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        valitity=findViewById(R.id.valitity);
        Name=findViewById(R.id.editText1);


        valitity.setText(validity);
        String removeCurrency=price.substring(3);
        prices.setText(removeCurrency);
        Name.setText(name);



        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String remove=apply.getText().toString();

                System.out.println("remove"+remove);

                if ((remove.equals("Apply"))){


                    System.out.println("APPLY");
                    referl=reffr.getText().toString();
                    if (referl.equals("")){
                        Toast.makeText(videobuynow.this, "Enter Referal Code", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String nprics= prices.getText().toString();


                        apicallamount(referl,nprics);
                    }

                } else{

                    System.out.println("REMOVE");

                    String removeCurrencyn=price.substring(3);
                    System.out.println("removeCurrency"+removeCurrencyn);
                    prices.setText(removeCurrencyn);

                    apply.setText("Apply");
                    apply.setClickable(true);
                    reffr.setText("");
                }





            }
        });

















        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nprics= prices.getText().toString();


                //  String removeCurrency=nprics.substring(3);
                referl=reffr.getText().toString();

                System.out.println("refferalcode"+id+referl+nprics);
                apicall(id,nprics,referl);



            }
        });
    }

    private void apicallamount(final String referl, final String price) {

        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(videobuynow.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.check, new Response.Listener<String>() {
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
                            str_messageresult = obj.getString("amount");

                            prices.setText(str_messageresult);

                            System.out.println("str_messageresult"+str_messageresult);

                            prices.getText().toString();
                            System.out.println("pricesapii"+  prices.getText().toString());

                            apply.setText("REMOVE");








                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(videobuynow.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(videobuynow.this,videobuynow.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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

                    params.put("amount",price );
                    params.put("memberId",referl );

                    System.out.println( "  inputs " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this,videobuynow.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }




    }

    private void apicall(final String id, final String nprics, final String referl) {



        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(videobuynow.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.videosbuy, new Response.Listener<String>() {
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
                            str_messageresult = obj.getString("result");

                            // utilis.BUYID="1";
                            //  startActivity(new Intent(BuyActivity.this, PackageActivity.class));


                            Intent intent = new Intent(videobuynow.this, videoshowwebview.class); //OrderDetailsActivity

                            intent.putExtra("Qcount", videobuynow.this.id);
                            intent.putExtra("examid",examid);
                            intent.putExtra("str_messageresult",str_messageresult);


                            intent.putExtra("name", name);
                            intent.putExtra("validity",validity);
                            intent.putExtra("price",price);

                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();


                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(videobuynow.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(videobuynow.this, videobuynow.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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

                    params.put("packageIndexId",id );
                    params.put("studentIndexId",utilis.strStudentID );
                    params.put("marketerId",referl);
                    params.put("amount",nprics);
                    System.out.println( " inputs " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, videobuynow.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
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
//        startActivity(new Intent(BuyActivity.this, PackageActivity.class));
//
//
//
//
//        finish();



        Intent intent = new Intent(videobuynow.this, PackageActivity.class); //OrderDetailsActivity

        intent.putExtra("Qcount",id );
        intent.putExtra("examid",examid);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);




    }



    @Override
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
    }





}
