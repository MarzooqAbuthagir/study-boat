package com.we_connect_students;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.List;
import java.util.Map;

public class subcription extends AppCompatActivity {
    String TAG = "subcriptionActivrity";
    DBHelper dbHelper;
    Utilis utilis;

    Toolbar toolbar;
    ActionBar actionBar = null;

    ListView Listview;

    String str_result = "", str_message = "",text="";
    ArrayList<Subcriptionlist> subcriptionlist = new ArrayList<Subcriptionlist>();

    Button backb,submits;
    TextView tvStudentName;
    TextView tvEmpty;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subcrption);
        utilis = new Utilis(subcription.this);
        initToolbar();
        dbHelper = new DBHelper(subcription.this);
        Listview = findViewById(R.id.listview);

        backb=findViewById(R.id.backb);
        submits=findViewById(R.id.submit);

        tvStudentName= findViewById(R.id.tvStudentName);
        utilis.getSharedPreference();
        tvStudentName.setText(utilis.strName);

        tvEmpty = findViewById(R.id.tvEmpty);

        backb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                back();

            }
        });

        submits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean b = tableExistsss(dbHelper.vatbook, dbHelper.selctedlist);


            }
        });

        allSubjectAPI();




    }
    private boolean tableExistsss(SQLiteDatabase vatbook, String selctedlist) {



        Cursor cursor =  vatbook.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+selctedlist+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                System.out.println("corser"+cursor.getCount());


                dbHelper.vatbook.execSQL("delete from " + dbHelper.questionresponse);
                dbHelper.vatbook.execSQL("delete from " + dbHelper.selctedlist);


                startActivity(new Intent(subcription.this, AssesmentActivity.class));
                finish();



                return true;
            }

            cursor.close();
        }

        startActivity(new Intent(subcription.this, AssesmentActivity.class));
        finish();
        System.out.println("corser"+cursor);


        return false;
    }

    private void allSubjectAPI() {

        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(subcription.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.reportnew, new Response.Listener<String>() {
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

                                Subcriptionlist supcription = new Subcriptionlist(jsonObject.getString("serialNo"),
                                        jsonObject.getString("testPackageIndexId"),jsonObject.getString("testName"),
                                        jsonObject.getString("rightAnswerCount"), jsonObject.getString("percentage"),jsonObject.getString("totalQuestions"));


                                subcriptionlist.add(supcription);

                            }

                            Listview.setVisibility(View.VISIBLE);
                            tvEmpty.setVisibility(View.GONE);

                            JoinedAdapterlist adapter = new JoinedAdapterlist(subcription.this, subcriptionlist);


                            Listview.setAdapter(adapter);


                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

//                            Toast.makeText(subcription.this, str_message, Toast.LENGTH_SHORT).show();

                            Listview.setVisibility(View.GONE);
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
                    Toast.makeText(subcription.this, subcription.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
            Toast.makeText(this, subcription.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(subcription.this, MainActivity.class));
        finish();
    }

    private class JoinedAdapterlist  extends BaseAdapter {

        private LayoutInflater mInflater;
        ViewHolder holder;
        private List<Subcriptionlist> Modellist = null;
        private ArrayList<Subcriptionlist> Join_arraylist;

        public JoinedAdapterlist(Context context, List<Subcriptionlist> Joinmodellist) {

            mInflater = LayoutInflater.from(context);
            this.Modellist = Joinmodellist;
            this.Join_arraylist = new ArrayList<Subcriptionlist>();
            this.Join_arraylist.addAll(Joinmodellist);

        }

        @Override
        public int getCount() {

            return Modellist.size();
        }

        @Override
        public Subcriptionlist getItem(int position) {

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

                convertView = mInflater.inflate(R.layout.listview_row, null);

                holder.Title=convertView.findViewById(R.id.Title);
                holder.Type=convertView.findViewById(R.id.Type);
                holder.Duration=convertView.findViewById(R.id.Duration);
                holder.Marks=convertView.findViewById(R.id.Marks);
                holder.Attempts=convertView.findViewById(R.id.Attempts);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Subcriptionlist item = Modellist.get(position);

            holder.Title.setText(String.valueOf(position+1));
            holder.Type.setText(Modellist.get(position).gettestName());
            holder.Duration.setText(Modellist.get(position).getrightAnswerCount()+"/"+Modellist.get(position).getTotalquestion());
            holder.Marks.setText(Modellist.get(position).getpercentage());
          //  holder.Attempts.setText(Modellist.get(position).getAttempts());





holder.Attempts.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

String id=Modellist.get(position).gettestPackageIndexId();

        Intent intent = new Intent(subcription.this, Reportofoverall.class); //OrderDetailsActivity
         intent.putExtra("PackageIndexId", id);

//        intent.putExtra("Qcount",id );
//        intent.putExtra("examid",examid);
//
//
//        System.out.println("inputs"+id+""+text);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
});






            return convertView;
        }

        private class ViewHolder {

            TextView Title,Type,Duration,Marks,Attempts;

        }
    }
}
