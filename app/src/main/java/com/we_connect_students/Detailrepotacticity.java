package com.we_connect_students;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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



public  class Detailrepotacticity extends AppCompatActivity {
    String TAG = "Detailrepotacticity",id="";
Button close,check;
    DBHelper dbHelper;
    Utilis utilis;
    public static ChildTerbarus childTerbaru;
    Toolbar toolbar;
    ActionBar actionBar = null;
    ListView Listview;

    String str_result = "", str_message = "",text="";
    ArrayList<Detailrepot> detailrepot = new ArrayList<Detailrepot>();
    public static TextView scores,persentage,btime,noof;
    public static ExpandableListView history;
    RelativeLayout reports;



    private int lastExpandedPosition = -1;

  public String  SubjectName="Biology";


    private ArrayList<TerbaruModels> ListTerbarunew =  null;

    private ArrayList<ChildTerbarus> ListChildTerbarunew =  null;
    private ArrayList<ArrayList<ChildTerbarus>> ListChildXXXXXXXXXnew= null;

    TerbaruAdapters adapters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporttwo);

        utilis = new Utilis(Detailrepotacticity.this);
        initToolbar();

        dbHelper = new DBHelper(Detailrepotacticity.this);
        Intent intent = getIntent();
        id = intent.getStringExtra("PackageIndexId");
        System.out.println(id);
       Listview = findViewById(R.id.listview);
        history=findViewById(R.id.listviewexband);
        ListTerbarunew = new ArrayList<TerbaruModels>();
        ListChildTerbarunew = new ArrayList<ChildTerbarus>();//ArrayList<ArrayList<ChildTerbaru>>>();
        ListChildXXXXXXXXXnew = new ArrayList<ArrayList<ChildTerbarus>>();



        scores=findViewById(R.id.score);
        persentage=findViewById(R.id.persentage);
        btime=findViewById(R.id.btime);
        noof=findViewById(R.id.noof);
        check=findViewById(R.id.check);
        close=findViewById(R.id.close);
        reports=findViewById(R.id.reports);

        reports.setVisibility(View.GONE);


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                back();

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Detailrepotacticity.this, MainActivity.class));
                finish();

            }
        });


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



        Intent intent = new Intent(Detailrepotacticity.this, subcription.class); //OrderDetailsActivity
        // intent.putExtra("subjectID", id);


        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);






    }

    private void allSubjectAPI(final String id) {

        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(Detailrepotacticity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.overall, new Response.Listener<String>() {
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


                            chapterapi(id);



                            str_message = obj.getString("Message");
                            JSONObject avarage=obj.getJSONObject("result");
                            String score=avarage.getString("score");
                            String averagepercentsge=avarage.getString("averagePercentage");
                            String avaragetime=avarage.getString("averageTime");
                            String noofattempt=avarage.getString("noOfAttempt");

                            scores.setText(score);
                            persentage.setText(averagepercentsge);
                            btime.setText(avaragetime);

                            noof.setText(noofattempt);

                            JSONArray json = avarage.getJSONArray("history");
                            System.out.println(TAG + " allSubjectAPI Length " + json.length());
                            for (int i = 0; i < json.length(); i++) {

                                JSONObject jsonObject = json.getJSONObject(i);

                                Detailrepot detailrepotlist = new Detailrepot(jsonObject.getString("serialNumber"),
                                        jsonObject.getString("date"),jsonObject.getString("rightAnswerCount"),
                                        jsonObject.getString("percentage"), jsonObject.getString("duration"),jsonObject.getString("time"),
                                        jsonObject.getString("Total"));

                                detailrepot.add(detailrepotlist);

                            }

                            JoinedAdapterlist adapter = new JoinedAdapterlist(Detailrepotacticity.this, detailrepot);


                            Listview.setAdapter(adapter);

                          //  Utility.setListViewHeightBasedOnChildren(Listview);


                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(Detailrepotacticity.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(Detailrepotacticity.this, Detailrepotacticity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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


                    params.put("packageIndexId",id);
                    System.out.println(TAG + "  packageIndexId " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, Detailrepotacticity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }

//    private void allsubject(String id) {
//
//
//
//
//
//
//
//        allSubjectAPIs();
//    }

        private void chapterapi(final String subjectid) {

        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(Detailrepotacticity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.progress, new Response.Listener<String>() {
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

                            Toast.makeText(Detailrepotacticity.this, str_message, Toast.LENGTH_SHORT).show();

                        } else if (Integer.parseInt(str_result) == 0) {

                            str_message = obj.getString("Message");



                            reports.setVisibility(View.VISIBLE);




                            JSONArray heroArray = obj.getJSONArray("result");

                            TerbaruModels LTT =null;
                            ChildTerbarus CTT =null;

                            for(int i=0; i < heroArray.length(); i++){
                                JSONObject jsonObject = heroArray.getJSONObject(i);

                                ListChildTerbarunew = new ArrayList<ChildTerbarus>();

                                LTT = new TerbaruModels(jsonObject.optString("SubjectName"));
                                ListTerbarunew.add(LTT);
                                Log.d("tag", "jsonObj Menu_Cat_Id->" + jsonObject.optString("SubjectName"));

                                JSONArray jsonArrSubCat = jsonObject.getJSONArray("Chaptername");
                                if (jsonArrSubCat != null && jsonArrSubCat.length() > 0) {

                                    for (int subCnt = 0; subCnt < jsonArrSubCat.length(); subCnt++) {
                                        JSONObject jsonObjSubCategory = jsonArrSubCat.getJSONObject(subCnt);

                                        CTT = new ChildTerbarus(jsonObjSubCategory.optString("ChapterName"),jsonObjSubCategory.optString("overallCount"),jsonObjSubCategory.optString("correctCount"),jsonObjSubCategory.optString("Percentage"));
                                        ListChildTerbarunew.add(CTT);

                                        Log.d("tag", "jsonArrSubCat Item_NameEN->" + jsonObjSubCategory.optString("chapterName")+jsonObjSubCategory.optString("overallCount")+jsonObjSubCategory.optString("correctCount"));
                                    }
                                }


                                ListChildXXXXXXXXXnew.add(ListChildTerbarunew);

                                 adapters = new TerbaruAdapters(Detailrepotacticity.this,ListTerbarunew, ListChildXXXXXXXXXnew);
                                history.setAdapter(adapters);
                            }










                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(Detailrepotacticity.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(Detailrepotacticity.this, Detailrepotacticity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

                    if (error instanceof NoConnectionError) {
                        System.out.println("NoConnectionError");
                    } else if (error instanceof TimeoutError) {
                        System.out.println("TimeoutError");

                    } else if (error instanceof ServerError) {
                        System.out.println("ServerError");

                    } else if (error instanceof AuthFailureError) {
                        System.out.println("AuthFailureError");
//                    } else if (error instanceof NetworkError) {
//
       System.out.println("NetworkError");
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("packageIndexId",subjectid);


                    System.out.println(TAG + " setpthreeAPI inputs " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, Detailrepotacticity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }





    private class JoinedAdapterlist  extends BaseAdapter {

        private LayoutInflater mInflater;
        ViewHolder holder;
        private List<Detailrepot> Modellist = null;
        private ArrayList<Detailrepot> Join_arraylist;

        public JoinedAdapterlist(Context context, List<Detailrepot> Joinmodellist) {

            mInflater = LayoutInflater.from(context);
            this.Modellist = Joinmodellist;
            this.Join_arraylist = new ArrayList<Detailrepot>();
            this.Join_arraylist.addAll(Joinmodellist);

        }

        @Override
        public int getCount() {

            return Modellist.size();
        }

        @Override
        public Detailrepot getItem(int position) {

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

                convertView = mInflater.inflate(R.layout.listview_rows, null);

                holder.Title=convertView.findViewById(R.id.Title);
                holder.Type=convertView.findViewById(R.id.Type);
                holder.Duration=convertView.findViewById(R.id.Duration);
                holder.Marks=convertView.findViewById(R.id.Marks);
                holder.Attempts=convertView.findViewById(R.id.Attempts);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Detailrepot item = Modellist.get(position);
            holder.Title.setText(Modellist.get(position).getserialNumber());
            holder.Type.setText(Modellist.get(position).getdate());
            holder.Duration.setText(Modellist.get(position).getrightAnswerCount()+"/"+Modellist.get(position).getTotal());
            holder.Marks.setText(Modellist.get(position).getpercentage());
              holder.Attempts.setText(Modellist.get(position).getduration());











            return convertView;
        }

        private class ViewHolder {

            TextView Title,Type,Duration,Marks,Attempts;

        }
    }



    private class TerbaruAdapters extends BaseExpandableListAdapter {
        Context context;
        ArrayList<TerbaruModels> ListTerbarunew;
        ArrayList<ArrayList<ChildTerbarus>> ListChildTerbarunew;
        int count;

        private TerbaruAdapters (Detailrepotacticity context, ArrayList<TerbaruModels>ListTerbaru, ArrayList<ArrayList<ChildTerbarus>> ListChildTerbaru){
            this.context= context;
            this.ListTerbarunew=ListTerbaru;
            this.ListChildTerbarunew=ListChildTerbaru;
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
            return ListChildTerbarunew.get(groupPosition).get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            childTerbaru = getChild(groupPosition, childPosition);
            ViewHolder holder= null;

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.expandablelistview_child2, null);

                holder=new ViewHolder();
                holder.begdate1=(TextView)convertView.findViewById(R.id.chaptres);
                holder.overall=(TextView)convertView.findViewById(R.id.overall);
                holder.presentage=(TextView)convertView.findViewById(R.id.presentage);
                holder.simpleProgressBar=(ProgressBar)convertView.findViewById(R.id.simpleProgressBar);


                convertView.setTag(holder);

            }
            else{
                holder=(ViewHolder)convertView.getTag();
            }
            holder.begdate1.setTag( childTerbaru);
            holder.begdate1.setText(childTerbaru.getchapterName());

            holder.overall.setText(childTerbaru.getpercentage()+"%");
            holder.presentage.setText(childTerbaru.getcorrectCount()+"/"+childTerbaru.getoverallCount());


            String prs = childTerbaru.getpercentage();

            int result = Integer.parseInt(prs);

//int result=50;

            holder.simpleProgressBar.setProgress(result);

//                String abcString2 = Integer.toString (result);
//                counter2.setText (abcString2);



            return convertView;
        }
        @Override
        public int getChildrenCount(int groupPosition) {
            return ListChildTerbarunew.get(groupPosition).size();
        }

        @Override
        public TerbaruModels getGroup(int groupPosition) {
            return ListTerbarunew.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return ListTerbarunew.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ExpandableListView eLV = (ExpandableListView) parent;
          eLV.expandGroup(groupPosition);

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
            TextView begdate1, presentage,nama, overall, imageid;

            ProgressBar simpleProgressBar;
            CheckBox chapter;
        }

    }

//    public class TerbaruAdapters extends BaseExpandableListAdapter {
//        Context context;
//        ArrayList<TerbaruModels> ListTerbarus;
//        ArrayList<ArrayList<ChildTerbarus>> ListChildTerbarus;
//        int count;
//
//        public TerbaruAdapters (Detailrepotacticity context, ArrayList<TerbaruModels>ListTerbarus, ArrayList<ArrayList<ChildTerbarus>> ListChildTerbarus){
//            this.context= context;
//            this.ListTerbarus=ListTerbarus;
//            this.ListChildTerbarus=ListChildTerbarus;
//
//        }
//        @Override
//        public boolean areAllItemsEnabled()
//        {
//            return true;
//        }
//
//        @Override
//        public void registerDataSetObserver(DataSetObserver observer) {
//            super.registerDataSetObserver(observer);
//        }
//
//        @Override
//        public void notifyDataSetChanged() {
//            super.notifyDataSetChanged();
//
//
//        }
//
//        @Override
//        public void notifyDataSetInvalidated() {
//            super.notifyDataSetInvalidated();
//        }
//
//        @Override
//        public ChildTerbarus getChild(int groupPosition, int childPosition) {
//            return ListChildTerbarus.get(groupPosition).get(childPosition);
//        }
//
//        @Override
//        public long getChildId(int groupPosition, int childPosition) {
//            return childPosition;
//        }
//
//        @Override
//        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//
//            final ChildTerbarus childTerbarus = getChild(groupPosition, childPosition);
//            ViewHolder holder= null;
//
//
//            if (convertView == null) {
//                LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                convertView = infalInflater.inflate(R.layout.expandablelistview_child2, null);
//
//                holder = new ViewHolder();
//                holder.begdate1 = (TextView) convertView.findViewById(R.id.chaptres);
//
//                convertView.setTag(holder);
//
//
//            }
//            else{
//                holder=(ViewHolder)convertView.getTag();
//            }
//
//
//
//            holder.begdate1.setText(childTerbarus.getchapterName());
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//            return convertView;
//        }
//        @Override
//        public int getChildrenCount(int groupPosition) {
//            return ListChildTerbarus.get(groupPosition).size();
//        }
//
//        @Override
//        public TerbaruModels getGroup(int groupPosition) {
//            return ListTerbarus.get(groupPosition);
//        }
//
//        @Override
//        public int getGroupCount() {
//            return ListTerbarus.size();
//        }
//
//        @Override
//        public long getGroupId(int groupPosition) {
//            return groupPosition;
//        }
//
//        @Override
//        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//
//            ExpandableListView eLV = (ExpandableListView) parent;
//            eLV.expandGroup(groupPosition);
//
//            final  TerbaruModels terbaruModels = (TerbaruModels) getGroup(groupPosition);
//            ViewHolder holder= null;
//            if (convertView == null) {
//                LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                convertView = infalInflater.inflate(R.layout.expandablelistview_group1, null);
//
//                holder=new ViewHolder();
//                holder.nama=(TextView)convertView.findViewById(R.id.unit);
//
//                convertView.setTag(holder);
//
//            }
//
//            else{
//                holder=(ViewHolder)convertView.getTag();
//            }
//
//           holder.nama.setText(terbaruModels.getsubjectName());
//
//
//            return convertView;
//        }
//
//        @Override
//        public boolean hasStableIds() {
//            return true;
//        }
//
//        @Override
//        public boolean isChildSelectable(int arg0, int arg1) {
//            return true;
//        }
//
//
//
//        public class ViewHolder{
//            TextView begdate1, enddate1,nama, alamat, imageid;
//
//        }
//
//    }




}
