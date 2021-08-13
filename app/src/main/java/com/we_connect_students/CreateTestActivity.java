package com.we_connect_students;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import java.util.Random;

public class CreateTestActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
   public static String TAG = "CreateTestActivity",examid="",id="";
    DBHelper dbHelper;
    Utilis utilis;

    Toolbar toolbar;
    ActionBar actionBar = null;

    RecyclerView recyclerView;

    String str_result = "", str_message = "",text="";
    ArrayList<SubjectList> list = new ArrayList<SubjectList>();
    String[] plants = new String[]{
            "9",
            "10",
            "11",
            "12",

    };
    Spinner spin;
Button submit,backb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);
        utilis = new Utilis(CreateTestActivity.this);
        initToolbar();

        Intent intent = getIntent();
        id = intent.getStringExtra("Qcount");
        examid = intent.getStringExtra("examid");



        dbHelper = new DBHelper(CreateTestActivity.this);
        recyclerView = findViewById(R.id.recyclerView);
        submit=findViewById(R.id.submit);
        backb=findViewById(R.id.backb);

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
//        recyclerView.setLayoutManager(gridLayoutManager);

        list = new ArrayList<>();
        spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);


        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,plants
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spin.setAdapter(spinnerArrayAdapter);
        allSubjectAPI();

        backb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {









                dbHelper.CreateTable(2);
                Cursor c = dbHelper.vatbook.rawQuery("select * from " + dbHelper.Subject + "  ", null);
                System.out.println("Add card table count" + c.getCount());
                if (c.getCount() == 0) {

                    Toast.makeText(CreateTestActivity.this, "Select Subject(s)", Toast.LENGTH_SHORT).show();
                } else {



                    Intent intent = new Intent(CreateTestActivity.this, TeststepTwo.class); //OrderDetailsActivity
                   // intent.putExtra("subjectID", id);

                    intent.putExtra("Qcount",id );
                    intent.putExtra("examid",examid);


                    System.out.println("inputs"+id+""+text);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                }











            }
        });
    }

    private void allSubjectAPI() {
        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(CreateTestActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.allSubjectList, new Response.Listener<String>() {
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

                            JSONArray json = obj.getJSONArray("subject");
                            System.out.println(TAG + " allSubjectAPI Length " + json.length());
                            for (int i = 0; i < json.length(); i++) {

                                JSONObject jsonObject = json.getJSONObject(i);

                                SubjectList values = new SubjectList(jsonObject.getString("subjectIndexId"),
                                        jsonObject.getString("subjectName"));

                                list.add(values);

                            }

                            SubjectAdapter subjectAdapter = new SubjectAdapter(CreateTestActivity.this, list);
                            recyclerView.setAdapter(subjectAdapter);


                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(CreateTestActivity.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(CreateTestActivity.this, CreateTestActivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
                    params.put("packageIndexId",id );
                    System.out.println(TAG + "  inputs " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, CreateTestActivity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(CreateTestActivity.this, PackageActivity.class); //OrderDetailsActivity

        intent.putExtra("Qcount",id );
        intent.putExtra("examid",examid);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        //Toast.makeText(getApplicationContext(),plants[position] , Toast.LENGTH_LONG).show();
        text = spin.getSelectedItem().toString();
        //Toast.makeText(CreateTestActivity.this, text, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
        Context con;
       // private ArrayList<SubjectList> arrayList;
        List<SubjectList> getDataAdapter;
        public SubjectAdapter(Context con, List<SubjectList> getDataAdapter) {
            this.con = con;
          //  this.arrayList = list;
            this.getDataAdapter = getDataAdapter;
        }

        @NonNull
        @Override
        public SubjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.subject_content, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final SubjectAdapter.ViewHolder viewHolder, final int i) {

            final int pos = i;
            Random rnd = new Random();
//            int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
//            viewHolder.button.setBackgroundColor(currentColor);
          //  viewHolder.button.setBackground(ContextCompat.getDrawable(CreateTestActivity.this,R.drawable.rounded_corners));
            final SubjectList getDataAdapter1 =  getDataAdapter.get(i);
            viewHolder.checkBox1.setText(getDataAdapter.get(i).getSubName());

            viewHolder.checkBox1.setChecked(getDataAdapter.get(i).isSelected());

            viewHolder.checkBox1.setTag(getDataAdapter.get(i));


          // viewHolder.checkBox1.setTag(arrayList.get(i));

            viewHolder. checkBox1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if( viewHolder. checkBox1.isChecked())
                    {
                        //Toast.makeText(CreateTestActivity.this, arrayList.get(i).getSubName()+ " Checked!", Toast.LENGTH_SHORT).show();

                        CheckBox cb = (CheckBox) v;
                        SubjectList contact = (SubjectList) cb.getTag();

                        contact.setSelected(cb.isChecked());
                        getDataAdapter.get(pos).setSelected(cb.isChecked());

//                        Toast.makeText(
//                                v.getContext(),
//                                "Clicked on Checkbox: " + cb.getText() + " is "
//                                        + cb.isChecked(), Toast.LENGTH_LONG).show();



                      //  Toast.makeText(CreateTestActivity.this, arrayList.get(pos).getSubName() + " clicked!", Toast.LENGTH_SHORT).show();



//                        try {
//                            dbHelper.CreateTable(2);
//                            dbHelper.vatbook.execSQL("INSERT INTO "
//                                    + DBHelper.Subject
//                                    + " (status,classCode,subjectName,subjectid)"
//                                    + " VALUES ('1', '" + text + "', '" + getDataAdapter1.getSubName() + "', '" + getDataAdapter1.getSubIndexId() + "')");
//
//
//
//
//
//                            System.out.println("INSERT INTO "
//                                    + DBHelper.Subject
//
//                                    + " (status,classCode,subjectName,subjectid)"
//                                    + " VALUES ('1', '" + text + "', '" +getDataAdapter1.getSubName() + "', '" + getDataAdapter1.getSubIndexId() + "')");
//
//                        } catch (SQLException e) {
//                            System.out.println(TAG + " LocalDB " + e.getMessage());
//                        }





                        dbHelper.CreateTable(2);
                        Cursor c = dbHelper.vatbook.rawQuery("select * from " + dbHelper.Subject + " where subjectid ='" + getDataAdapter1.getSubIndexId() + "' ", null);
                        System.out.println("Add card table count" + c.getCount());
                        if (c.getCount() == 0) {
                            dbHelper.CreateTable(2);
                            dbHelper.vatbook.execSQL("INSERT INTO "
                                    + DBHelper.Subject
                                    + " (status,classCode,subjectName,subjectid)"
                                    + " VALUES ('1', '" + text + "', '" + getDataAdapter1.getSubName() + "', '" + getDataAdapter1.getSubIndexId() + "')");

                            System.out.println("INSERT INTO "
                                    + DBHelper.Subject

                                    + " (status,classCode,subjectName,subjectid)"
                                    + " VALUES ('1', '" + text + "', '" +getDataAdapter1.getSubName() + "', '" + getDataAdapter1.getSubIndexId() + "')");
                        } else {
                            dbHelper.CreateTable(2);
                            dbHelper.vatbook.execSQL("update " + dbHelper.Subject + " set subjectName = '" + getDataAdapter1.getSubName() + "' where subjectid = '" + getDataAdapter1.getSubIndexId() + "'  ");
                            System.out.println("updatecorrct4"+dbHelper.Subject + " set subjectName = '" + getDataAdapter1.getSubName() + "' where subjectid = '" + getDataAdapter1.getSubIndexId() + "'  ");

                        }
















                    }else {
                     //   Toast.makeText(CreateTestActivity.this, getDataAdapter1.getSubName()+ " Unchecked!", Toast.LENGTH_SHORT).show();


                        dbHelper.CreateTable(2);
                        dbHelper.vatbook.execSQL("delete from " + dbHelper.Subject + " where subjectid ='" +getDataAdapter1.getSubIndexId() + "'");
                    }


                }
            });

//            viewHolder.button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                    Intent intent = new Intent(CreateTestActivity.this, TeststepTwo.class); //OrderDetailsActivity
//                    intent.putExtra("subjectID", arrayList.get(i).getSubName());
//
//
//                    intent.putExtra("classid", text);
//
//
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//
//
//                  //  Toast.makeText(con, arrayList.get(i).getSubIndexId()+text, Toast.LENGTH_SHORT).show();
//
//
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return getDataAdapter.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout button;
            TextView  subject;
            CheckBox checkBox1;

            public ViewHolder(View view) {
                super(view);
                button = view.findViewById(R.id.button);
                subject = view.findViewById(R.id.subject);
                checkBox1 = view.findViewById(R.id.checkBox1);
                view.setTag(itemView);
            }
        }
    }

}
