package com.we_connect_students;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
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
import java.util.Map;

public class detaillast extends AppCompatActivity {

    String TAG = "Teststepthree",classid="",testname="",subjectid="",subjectID="",Nquestion="",Repetanswer="",Negativemarker="",str_result="",str_message="",time="";

    Utilis utilis;
    DBHelper dbHelper;
    Toolbar toolbar;
    ActionBar actionBar = null;
    public static ExpandableListView history;

    ArrayList<String> listName;


    private ArrayList<TerbaruModels> ListTerbaru =  null;

    private ArrayList<ChildTerbarus> ListChildTerbaru =  null;
    private ArrayList<ArrayList<ChildTerbarus>> ListChildXXXXXXXXX = null;


    Button submit,backb;
    public static ArrayList<String> array;
    public static ArrayList<HashMap<String, String>> cartArrayList = null;
    Cursor cursor;
    HashMap<String, String> map;
    ArrayList<String> listOfValues;
    public static String id = "", str_itemName = "", str_itemIndex = "", str_itemQty = "", str_itemVat = "", str_itemPrice = "", str_itemVatAmt = "", str_totalAmt = "", str_totalVat = "";
    public static String[] arr_exdate, arr_strItemName, arr_strItemIndex, arr_strItemQty, arr_strItemVat, arr_strItemPrice, arr_strItemVatAmt, arr_strTotalAmt, arr_strTotalVat;
    public  static String SubjectName="Biology";
    TerbaruModels LT =null;
    ChildTerbarus CT =null;
    String[] strNames;
    Object[] objArray;
    String newString;
    public  static String Subject="";
    Spinner spinnerOfferType;
    TerbaruAdapter adapter;
    public static String Chapter="",unit="";
    public static boolean selecteds;
    private int lastExpandedPosition = -1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporttwo);
        utilis = new Utilis(detaillast.this);
        dbHelper = new DBHelper(detaillast.this);
        initToolbar();
       // getValues();

        Intent intent = getIntent();
        id = intent.getStringExtra("PackageIndexId");

        history=findViewById(R.id.listviewexband);


        submit=findViewById(R.id.submit);

        backb=findViewById(R.id.backb);




        ListTerbaru = new ArrayList<TerbaruModels>();
        ListChildTerbaru = new ArrayList<ChildTerbarus>();//ArrayList<ArrayList<ChildTerbaru>>>();
        ListChildXXXXXXXXX = new ArrayList<ArrayList<ChildTerbarus>>();



        backb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

















            }
        });

        history.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                System.out.println("haiiiii"+groupPosition);

                return false;
            }
        });


        history.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    history.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        allsubject(id);

    }








    private void getValues() {
        Bundle b = getIntent().getExtras();


        if (b != null) {






            classid = b.getString("examid");
            subjectid = b.getString("Qcount");
            subjectID = b.getString("Qcount");
            Nquestion = b.getString("nquestion");
            Repetanswer = b.getString("repetanswer");
            Negativemarker = b.getString("negativemarker");
            testname = b.getString("testname");
            time = b.getString("time");
            System.out.println(TAG + " getValues " + b);


            array = (ArrayList<String>) b.getStringArrayList("array_list");
            System.out.println("ARRay"+array);



        }
    }



    private void allsubject(String id) {
        history.setAdapter((ExpandableListAdapter) null);

        TerbaruAdapter adapter = new TerbaruAdapter(detaillast.this,ListTerbaru, ListChildXXXXXXXXX);
        history.setAdapter(adapter);

        ListTerbaru.clear();
        ListChildTerbaru.clear();
        ListChildXXXXXXXXX.clear();


        allSubjectAPI(id);
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

















    }


    public class TerbaruAdapter extends BaseExpandableListAdapter {
        Context context;
        ArrayList<TerbaruModels> ListTerbaru;
        ArrayList<ArrayList<ChildTerbarus>> ListChildTerbaru;
        int count;

        public TerbaruAdapter (detaillast context, ArrayList<TerbaruModels>ListTerbaru, ArrayList<ArrayList<ChildTerbarus>> ListChildTerbaru){
            this.context= context;
            this.ListTerbaru=ListTerbaru;
            this.ListChildTerbaru=ListChildTerbaru;
//      this.count=ListTerbaru.size();
//      this.count=ListChildTerbaru.size();
        }
        @Override
        public boolean areAllItemsEnabled()
        {
            return true;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {
            super.registerDataSetObserver(observer);
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();


        }

        @Override
        public void notifyDataSetInvalidated() {
            super.notifyDataSetInvalidated();
        }

        @Override
        public ChildTerbarus getChild(int groupPosition, int childPosition) {
            return ListChildTerbaru.get(groupPosition).get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            final ChildTerbarus childTerbaru = getChild(groupPosition, childPosition);
            ViewHolder holder= null;


            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.expandablelistview_child2, null);

                holder = new ViewHolder();
                holder.begdate1 = (TextView) convertView.findViewById(R.id.chaptres);
                holder.chapter = (CheckBox) convertView.findViewById(R.id.androidCheckBox);

                convertView.setTag(holder);



            }
            else{
                holder=(ViewHolder)convertView.getTag();
            }



            holder.chapter.setText(childTerbaru.getchapterName());






















            return convertView;
        }
        @Override
        public int getChildrenCount(int groupPosition) {
            return ListChildTerbaru.get(groupPosition).size();
        }

        @Override
        public TerbaruModels getGroup(int groupPosition) {
            return ListTerbaru.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return ListTerbaru.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

//            ExpandableListView eLV = (ExpandableListView) parent;
//            eLV.expandGroup(groupPosition);

            final  TerbaruModels terbaruModel = (TerbaruModels) getGroup(groupPosition);
            ViewHolder holder= null;
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.expandablelistview_group1, null);

                holder=new ViewHolder();
                holder.nama=(TextView)convertView.findViewById(R.id.unit);

                convertView.setTag(holder);

            }

            else{
                holder=(ViewHolder)convertView.getTag();
            }

            holder.nama.setText(terbaruModel.getsubjectName());


            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int arg0, int arg1) {
            return true;
        }




        public class ViewHolder{
            TextView begdate1, enddate1,nama, alamat, imageid;
            CheckBox chapter;
        }

    }



    private void allSubjectAPI(final String id) {











        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(detaillast.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.progress, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);
                        history.setAdapter((BaseExpandableListAdapter)null);
                        System.out.println(TAG + " allSubjectAPI response - " + response);

                        utilis.dismissProgress();

                        str_result = obj.getString("errorCode");
                        System.out.print(TAG + " allSubjectAPI result " + str_result);

                        if (Integer.parseInt(str_result) == 0) {

                            history.setAdapter((ExpandableListAdapter) null);
                            dbHelper.vatbook.execSQL("delete from " + dbHelper.Subject);
                            str_message = obj.getString("Message");


                            LT=null;
                            CT=null;



                            JSONArray heroArray = obj.getJSONArray("result");

                            for(int i=0; i < heroArray.length(); i++){
                                JSONObject jsonObject = heroArray.getJSONObject(i);

                                ListChildTerbaru = new ArrayList<ChildTerbarus>();

                                LT = new TerbaruModels(jsonObject.optString("SubjectName"));
                                ListTerbaru.add(LT);
                                Log.d("tag", "jsonObj Menu_Cat_Id->" + jsonObject.optString("SubjectName"));

                                JSONArray jsonArrSubCat = jsonObject.getJSONArray("Chaptername");
                                if (jsonArrSubCat != null && jsonArrSubCat.length() > 0) {

                                    for (int subCnt = 0; subCnt < jsonArrSubCat.length(); subCnt++) {
                                        JSONObject jsonObjSubCategory = jsonArrSubCat.getJSONObject(subCnt);

                                        CT = new ChildTerbarus(jsonObjSubCategory.optString("ChapterName"),jsonObjSubCategory.optString("overallCount"),jsonObjSubCategory.optString("correctCount"), jsonObjSubCategory.optString("Percentage"));
                                        ListChildTerbaru.add(CT);

                                        Log.d("tag", "jsonArrSubCat Item_NameEN->" + jsonObjSubCategory.optString("chapterName")+jsonObjSubCategory.optString("overallCount"));
                                    }
                                }





                                ListChildXXXXXXXXX.add(ListChildTerbaru);


                                adapter = new TerbaruAdapter(detaillast.this,ListTerbaru, ListChildXXXXXXXXX);
                                history.setAdapter(adapter);

                                adapter.notifyDataSetChanged();
                                adapter.notifyDataSetInvalidated();



// refresh the View



                            }




                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(detaillast.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(detaillast.this, detaillast.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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

                    params.put("packageIndexId",id );

                    System.out.println(TAG + " loginAPI inputs " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, detaillast.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }



}


