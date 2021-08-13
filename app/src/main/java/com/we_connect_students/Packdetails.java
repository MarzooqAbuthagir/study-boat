package com.we_connect_students;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Packdetails extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar = null;
    Button button2,button1,pay;
   public String id="",str_result="",str_message="",packageIndexId="",packageName="",examName="",subTitle="",subjectCount="",noOfQuestions="",validity="",price="",paid="";
    String examid="";
    Utilis utilis;
    String TAG = "Packdetails";

    ArrayList<Packagesub> packagesub = new ArrayList<Packagesub>();
public  static ListView list;
public static TextView btittle,
    stittle,
            exam,
    subject,
            totalq,
    prices
            ,valitity;


RelativeLayout container;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_packagedetials);

        utilis = new Utilis(Packdetails.this);

        Window window = Packdetails.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(Packdetails.this,R.color.packagesd));

        container=findViewById(R.id.container);
container.setVisibility(View.GONE);
        initToolbar();
        Intent intent = getIntent();
        id = intent.getStringExtra("Qcount");
        examid = intent.getStringExtra("examid");
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        pay=findViewById(R.id.pay);
list=findViewById(R.id.list);


        btittle=findViewById(R.id.btittle);
        stittle=findViewById(R.id.stittle);
        exam=findViewById(R.id.exam);
        subject=findViewById(R.id.subject);
        totalq=findViewById(R.id.totalq);
        prices=findViewById(R.id.prices);

        valitity=findViewById(R.id.valitity);











        packdetailsapi(id);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




String name=btittle.getText().toString();
String validity=valitity.getText().toString();
String price=prices.getText().toString();




                Intent intent = new Intent(Packdetails.this, BuyActivity.class); //OrderDetailsActivity

                intent.putExtra("Qcount",id );
                intent.putExtra("examid",examid);
                intent.putExtra("name",name );
                intent.putExtra("validity",validity);
                intent.putExtra("price",price );


                System.out.println("price"+price);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


//                Intent intent = new Intent(Packdetails.this, BuyActivity.class); //OrderDetailsActivity
//
//                intent.putExtra("Qcount",id );
////                    intent.putExtra("Id",ids);
//
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                back();

            }
        });
    }

    private void packdetailsapi(final String id) {


        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(Packdetails.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.packdetails, new Response.Listener<String>() {
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

                            Toast.makeText(Packdetails.this, str_message, Toast.LENGTH_SHORT).show();
                            container.setVisibility(View.GONE);
                        } else if (Integer.parseInt(str_result) == 0) {

                            str_message = obj.getString("Message");


                            JSONObject jsons = obj.getJSONObject("result");
                            packageIndexId = jsons.getString("packageIndexId");
                            packageName = jsons.getString("packageName");
                            examName = jsons.getString("examName");
                            subTitle = jsons.getString("subTitle");

                            subjectCount = jsons.getString("subjectCount");
                            noOfQuestions = jsons.getString("noOfQuestions");
                            validity = jsons.getString("validity");
                            price = jsons.getString("price");
                           paid = jsons.getString("flag");

                           if(paid.equals("1")){
                               pay.setVisibility(View.VISIBLE);
                               button1.setVisibility(View.GONE);
                           }else {
                               pay.setVisibility(View.GONE);
                               button1.setVisibility(View.VISIBLE);
                           }

                            container.setVisibility(View.VISIBLE);



                            subject.setText(subjectCount);
                            prices.setText("₹"+"  "+price);
                            valitity.setText(validity);
                            totalq.setText(noOfQuestions);

                            exam.setText(examName);
                            stittle.setText(subTitle);
                            btittle.setText(packageName);




                            JSONArray json = jsons.getJSONArray("subject");
                            System.out.println(TAG + " allSubjectAPI Length " + json.length());
                            for (int i = 0; i < json.length(); i++) {

                                JSONObject jsonObject = json.getJSONObject(i);

                                Packagesub values = new Packagesub(jsonObject.getString("subjectName"),jsonObject.getString("questionCount"));

                                packagesub.add(values);
                            }
                            JoinedAdapterlist adapter = new JoinedAdapterlist(Packdetails.this, packagesub);


                            list.setAdapter(adapter);



                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(Packdetails.this, str_message, Toast.LENGTH_SHORT).show();
                            container.setVisibility(View.GONE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(Packdetails.this, Packdetails.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
                    params.put("packageIndexId", id);



                    System.out.println(TAG + " loginAPI inputs " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, Packdetails.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
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
      //  startActivity(new Intent(Packdetails.this, PackageActivity.class));


        Intent intent = new Intent(Packdetails.this, PackageActivity.class); //OrderDetailsActivity

        intent.putExtra("Qcount",id );
        intent.putExtra("examid",examid);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }



    private class JoinedAdapterlist  extends BaseAdapter {

        private LayoutInflater mInflater;
        ViewHolder holder;
        private List<Packagesub> Modellist = null;
        private ArrayList<Packagesub> Join_arraylist;

        public JoinedAdapterlist(Context context, List<Packagesub> Joinmodellist) {

            mInflater = LayoutInflater.from(context);
            this.Modellist = Joinmodellist;
            this.Join_arraylist = new ArrayList<Packagesub>();
            this.Join_arraylist.addAll(Joinmodellist);

        }

        @Override
        public int getCount() {

            return Modellist.size();
        }

        @Override
        public Packagesub getItem(int position) {

            return Modellist.get(position);

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {

                holder = new ViewHolder();

                convertView = mInflater.inflate(R.layout.subject, null);

                holder.subject=convertView.findViewById(R.id.sub);
                holder.questioncount=convertView.findViewById(R.id.quest);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Packagesub item = Modellist.get(position);
            holder.subject.setText(Modellist.get(position).getsubject());
            holder.questioncount.setText(Modellist.get(position).getquestioncount());












            return convertView;
        }

        private class ViewHolder {

            TextView subject,questioncount;

        }
    }
}
