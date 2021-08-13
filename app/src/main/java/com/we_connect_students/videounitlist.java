package com.we_connect_students;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class videounitlist extends AppCompatActivity {

    String TAG = "videounitlistActivity";
    ListView listView;


    Toolbar toolbar;
    ActionBar actionBar = null;
    Button Button2,Button1;
    public static String Subjectid="",Subject="";
    TextView toolbar_title,topic;
    DBHelper dbHelper;
    Utilis utilis;
    String str_result = "", str_message = "",text="";
    ArrayList<videolisttwo> practiceliststwo = new ArrayList<videolisttwo>();

    FragmentManager fm = null;
    //  DefaultNoNet defaultNoNet = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videounitlist);
        fm = getSupportFragmentManager();

        utilis = new Utilis(videounitlist.this);
        Window window = videounitlist.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(videounitlist.this,R.color.video));

        Intent intent = getIntent();
        Subjectid = intent.getStringExtra("Subjectid");
        Subject =intent.getStringExtra("subject");



        initToolbar();


        toolbar_title=findViewById(R.id.toolbar_title);
        topic=findViewById(R.id.topic);

        topic.setText(Subject);

        Button2=findViewById(R.id.Button2);
        Button1=findViewById(R.id.Button1);

        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(videounitlist.this, videos.class);


                startActivity(i);
            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        listView = (ListView)findViewById(R.id.listView1);





        // ListView on item selected listener.
//         listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
//         {
//
//             @Override
//             public void onItemClick(AdapterView<?> parent, View view,
//                                     int position, long id) {
//                 // TODO Auto-generated method stub
//
//                 // Getting listview click value into String variable.
//                 String TempListViewClickedValue = listValue[position].toString();
//
//                 Intent intent = new Intent(PracticeunitActivity.this, PracticechapterActivity.class);
//
//                 // Sending value to another activity using intent.
//                 intent.putExtra("ListViewClickedValue", TempListViewClickedValue);
//
//                 startActivity(intent);
//
//             }
//         });


        allSubjectAPI(Subjectid);
    }

    private void allSubjectAPI(final String Subjectid) {

        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(videounitlist.this);

            StringRequest stringRequest
                    = new StringRequest(Request.Method.POST, utilis.Api + utilis.videounitlist, new Response.Listener<String>() {
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

                                videolisttwo practicelisttwos = new videolisttwo(jsonObject.getString("UnitId"),jsonObject.getString("UnitName"),
                                        jsonObject.getString("count"));

                                practiceliststwo.add(practicelisttwos);

                            }

                            JoinedAdapterlist adapter = new JoinedAdapterlist(videounitlist.this, practiceliststwo);


                            listView.setAdapter(adapter);


                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(videounitlist.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(videounitlist.this, videounitlist.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
                    System.out.println("subjectid"+Subjectid);

                    params.put("SubjectId",Subjectid );
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, videounitlist.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(videounitlist.this, videos.class);
        intent.putExtra("Subjectid", Subjectid);

        startActivity(intent);
    }

    /*@Override
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





    private class JoinedAdapterlist  extends BaseAdapter {

        private LayoutInflater mInflater;
        JoinedAdapterlist.ViewHolder holder;
        private List<videolisttwo> Modellist = null;
        private ArrayList<videolisttwo> Join_arraylist;

        public JoinedAdapterlist(Context context, ArrayList<videolisttwo> Joinmodellist) {

            mInflater = LayoutInflater.from(context);
            this.Modellist = Joinmodellist;
            this.Join_arraylist = new ArrayList<videolisttwo>();
            this.Join_arraylist.addAll(Joinmodellist);

        }

        @Override
        public int getCount() {

            return Modellist.size();
        }

        @Override
        public videolisttwo getItem(int position) {

            return Modellist.get(position);

        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {

                holder = new JoinedAdapterlist.ViewHolder();

                convertView = mInflater.inflate(R.layout.practicesactunit, null);

                holder.Title=convertView.findViewById(R.id.text1);

                holder.clicklay=convertView.findViewById(R.id.clicklay);

                convertView.setTag(holder);

            } else {
                holder = (JoinedAdapterlist.ViewHolder) convertView.getTag();
            }


            int pos = position+1;


            videolisttwo item = Modellist.get(position);
            holder.Title.setText(pos+". "+Modellist.get(position).getUnitName()+" "+"("+Modellist.get(position).getCount()+")");
            // holder.Type.setText(Modellist.get(position).getcount());
            pos++;



            holder.clicklay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String unit=Modellist.get(position).getUnitName();
                    String unitid = Modellist.get(position).getUnitid();

                    Intent intent = new Intent(videounitlist.this, videochapterlist.class);

                    // Sending value to another activity using intent.
                    intent.putExtra("Unit", unit);
                    intent.putExtra("Subject", Subject);
                    intent.putExtra("unitid",unitid);
                    intent.putExtra("Subjectid",Subjectid);

                    startActivity(intent);


                }
            });







            return convertView;
        }

        private class ViewHolder {

            TextView Title,Type;
            LinearLayout clicklay;

        }
    }


}
