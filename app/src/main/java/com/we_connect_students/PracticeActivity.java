package com.we_connect_students;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.we_connect_students.DataBase.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PracticeActivity extends AppCompatActivity {
    String TAG = "practicesActivrity";
    ListView listView;

    DBHelper dbHelper;
    Utilis utilis;
    Toolbar toolbar;
    ActionBar actionBar = null;
    Button Button2,Button1;
    String str_result = "", str_message = "",text="",Subject="";
    TextView textView;

    ArrayList<Practicelist> practicelists = new ArrayList<Practicelist>();
    TextView tvEmpty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        utilis = new Utilis(PracticeActivity.this);
        initToolbar();
        Window window = PracticeActivity.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(PracticeActivity.this,R.color.voil));
        Intent intent = getIntent();
        Subject = intent.getStringExtra("Subject");
        listView = (ListView)findViewById(R.id.listView1);
        tvEmpty = findViewById(R.id.tvEmpty);




        Button2=findViewById(R.id.Button2);

        Button1=findViewById(R.id.Button1);
//        textView=findViewById(R.id.textv);


        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PracticeActivity.this, MainActivity.class);


                startActivity(i);
            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });


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


        allSubjectAPI();
    }

    private void allSubjectAPI() {

        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(PracticeActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.learnsubjectlist, new Response.Listener<String>() {
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
                            String result = obj.getString("result");

                            if (result.equals("null")){
                                Toast.makeText(PracticeActivity.this, "Please subscribe any package", Toast.LENGTH_SHORT).show();
                            }

                            else {
                                JSONArray json = obj.getJSONArray("result");


                                System.out.println(TAG + " allSubjectAPI Length " + json.length());
                                for (int i = 0; i < json.length(); i++) {


                                    JSONObject jsonObject = json.getJSONObject(i);

                                    Practicelist practicelist = new Practicelist(jsonObject.getString("subjectName"),
                                            jsonObject.getString("count"));

                                    practicelists.add(practicelist);

                                }

                                listView.setVisibility(View.VISIBLE);
                                tvEmpty.setVisibility(View.GONE);

                                JoinedAdapterlist adapter = new JoinedAdapterlist(PracticeActivity.this, practicelists);


                                listView.setAdapter(adapter);

                            }
                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

//                            Toast.makeText(PracticeActivity.this, "Please subscribe any package", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(PracticeActivity.this, PracticeActivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
            Toast.makeText(this, PracticeActivity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
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
        Intent i = new Intent(PracticeActivity.this, MainActivity.class);


        startActivity(i);
    }


/*    @Override
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
        ViewHolder holder;
        private List<Practicelist> Modellist = null;
        private ArrayList<Practicelist> Join_arraylist;

        public JoinedAdapterlist(Context context, List<Practicelist> Joinmodellist) {

            mInflater = LayoutInflater.from(context);
            this.Modellist = Joinmodellist;
            this.Join_arraylist = new ArrayList<Practicelist>();
            this.Join_arraylist.addAll(Joinmodellist);

        }

        @Override
        public int getCount() {

            return Modellist.size();
        }

        @Override
        public Practicelist getItem(int position) {

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

                convertView = mInflater.inflate(R.layout.practicesact, null);

                holder.Title=convertView.findViewById(R.id.text1);

                holder.clicklay=convertView.findViewById(R.id.clicklay);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            int pos = position+1;


            Practicelist item = Modellist.get(position);
            holder.Title.setText(pos+". "+Modellist.get(position).getsubjectName()+" "+"("+Modellist.get(position).getcount()+")");
           // holder.Type.setText(Modellist.get(position).getcount());
            pos++;



holder.clicklay.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        String subject=Modellist.get(position).getsubjectName();

        Intent intent = new Intent(PracticeActivity.this, PracticeunitActivity.class);

                // Sending value to another activity using intent.
                intent.putExtra("Subject", subject);

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