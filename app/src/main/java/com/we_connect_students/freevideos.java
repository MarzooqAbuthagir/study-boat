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

public class freevideos extends AppCompatActivity {

    String TAG = "VideosActivity";
    ListView listView;

    DBHelper dbHelper;
    Utilis utilis;
    Toolbar toolbar;
    ActionBar actionBar = null;
    Button Button2,Button1;
    String str_result = "", str_message = "",text="",Subject="";

    ArrayList<videolist> practicelists = new ArrayList<videolist>();



    FragmentManager fm = null;
    //  DefaultNoNet defaultNoNet = null;
    TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freevideos);


        fm = getSupportFragmentManager();

        utilis = new Utilis(freevideos.this);
//        initToolbar();
        Window window = freevideos.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(freevideos.this,R.color.video));
        Intent intent = getIntent();
        Subject = intent.getStringExtra("Subject");
        listView = (ListView)findViewById(R.id.listView1);




//        Button2=findViewById(R.id.Button2);
//
//        Button1=findViewById(R.id.Button1);
//
//
//        Button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(freevideos.this, MainActivity.class);
//
//
//                startActivity(i);
//            }
//        });
//        Button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                back();
//            }
//        });
//

//        // ListView on item selected listener.
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                // TODO Auto-generated method stub
//
//                // Getting listview click value into String variable.
//                String TempListViewClickedValue = listValue[position].toString();
//
//                Intent intent = new Intent(PracticeActivity.this, PracticeunitActivity.class);
//
//                // Sending value to another activity using intent.
//                intent.putExtra("ListViewClickedValue", TempListViewClickedValue);
//
//                startActivity(intent);
//
//            }
//        });

tvEmpty = findViewById(R.id.tvEmpty);
        allSubjectAPI();
    }

    private void allSubjectAPI() {

        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(freevideos.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.videosubjectlist, new Response.Listener<String>() {
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

                            JSONArray json = obj.getJSONArray("freeresult");
                            System.out.println(TAG + " allSubjectAPI Length " + json.length());
                            for (int i = 0; i < json.length(); i++) {

                                JSONObject jsonObject = json.getJSONObject(i);

                                videolist practicelist = new videolist(jsonObject.getString("subjectId"),jsonObject.getString("subjectName"),
                                        jsonObject.getString("count"));

                                practicelists.add(practicelist);

                            }

                            if (json.length() > 0) {
                                listView.setVisibility(View.VISIBLE);
                                tvEmpty.setVisibility(View.GONE);
                            } else {
                                listView.setVisibility(View.GONE);
                                tvEmpty.setVisibility(View.VISIBLE);
                            }

                            JoinedAdapterlist adapter = new JoinedAdapterlist(freevideos.this, practicelists);


                            listView.setAdapter(adapter);


                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

//                            Toast.makeText(freevideos.this, str_message, Toast.LENGTH_SHORT).show();
                            listView.setVisibility(View.GONE);
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
                    Toast.makeText(freevideos.this, freevideos.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
            Toast.makeText(this,freevideos.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }


//    private void initToolbar() {
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        actionBar = getSupportActionBar();
//
//        if (actionBar != null) {
//            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                back();
//            }
//        });
//    }


    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        Intent i = new Intent(freevideos.this, MainActivity.class);


        startActivity(i);
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





    private class JoinedAdapterlist  extends BaseAdapter {

        private LayoutInflater mInflater;
        JoinedAdapterlist.ViewHolder holder;
        private List<videolist> Modellist = null;
        private ArrayList<videolist> Join_arraylist;

        public JoinedAdapterlist(Context context, List<videolist> Joinmodellist) {

            mInflater = LayoutInflater.from(context);
            this.Modellist = Joinmodellist;
            this.Join_arraylist = new ArrayList<videolist>();
            this.Join_arraylist.addAll(Joinmodellist);

        }

        @Override
        public int getCount() {

            return Modellist.size();
        }

        @Override
        public videolist getItem(int position) {

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

                convertView = mInflater.inflate(R.layout.practicesact, null);

                holder.Title=convertView.findViewById(R.id.text1);

                holder.clicklay=convertView.findViewById(R.id.clicklay);

                convertView.setTag(holder);

            } else {
                holder = (JoinedAdapterlist.ViewHolder) convertView.getTag();
            }


            int pos = position+1;


            videolist item = Modellist.get(position);
            holder.Title.setText(pos+". "+Modellist.get(position).getSubjectName()+" "+"("+Modellist.get(position).getCount()+")");
            // holder.Type.setText(Modellist.get(position).getcount());
            pos++;



            holder.clicklay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String subjectid=Modellist.get(position).getSubjectid();
                    String subject =Modellist.get(position).getSubjectName();

                    Intent intent = new Intent(freevideos.this, videounitlist.class);

                    // Sending value to another activity using intent.
                    intent.putExtra("Subjectid", subjectid);
                    intent.putExtra("subject",subject);

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