package com.we_connect_students;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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



public class Progressactivity extends AppCompatActivity {
    String id="";
    DBHelper dbHelper;
    Utilis utilis;
    public static ExpandableListView history;
    String str_result = "", str_message = "",text="";
    String TAG = "Detailrepotacticity";

    public static ChildTerbarus childTerbaru;
    private ArrayList<TerbaruModels> ListTerbarunew =  null;

    private ArrayList<ChildTerbarus> ListChildTerbarunew =  null;
    private ArrayList<ArrayList<ChildTerbarus>> ListChildXXXXXXXXXnew= null;

    TerbaruAdapters adapters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_activity_2);
        utilis = new Utilis(Progressactivity.this);
        dbHelper = new DBHelper(Progressactivity.this);
        Intent intent = getIntent();
        id = intent.getStringExtra("UserID");

        System.out.println("progrss"+id);
        history=findViewById(R.id.listviewexband);
        ListTerbarunew = new ArrayList<TerbaruModels>();
        ListChildTerbarunew = new ArrayList<ChildTerbarus>();//ArrayList<ArrayList<ChildTerbaru>>>();
        ListChildXXXXXXXXXnew = new ArrayList<ArrayList<ChildTerbarus>>();
        chapterapi(id);
    }


    private void chapterapi(final String subjectid) {

        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(Progressactivity.this);

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

                            Toast.makeText(Progressactivity.this, str_message, Toast.LENGTH_SHORT).show();

                        } else if (Integer.parseInt(str_result) == 0) {

                            str_message = obj.getString("Message");








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

                                        CTT = new ChildTerbarus(jsonObjSubCategory.optString("ChapterName"),
                                                jsonObjSubCategory.optString("overallCount"),
                                                jsonObjSubCategory.optString("correctCount"),
                                                jsonObjSubCategory.optString("Percentage"));
                                        ListChildTerbarunew.add(CTT);

                                        Log.d("tag", "jsonArrSubCat Item_NameEN->" + jsonObjSubCategory.optString("chapterName")+jsonObjSubCategory.optString("overallCount")+jsonObjSubCategory.optString("correctCount"));

                                    }
                                }


                                ListChildXXXXXXXXXnew.add(ListChildTerbarunew);

                                adapters = new TerbaruAdapters(Progressactivity.this,ListTerbarunew, ListChildXXXXXXXXXnew);
                                history.setAdapter(adapters);
                            }










                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(Progressactivity.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(Progressactivity.this, Progressactivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
            Toast.makeText(this, Progressactivity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }




    private class TerbaruAdapters extends BaseExpandableListAdapter {
        Context context;
        ArrayList<TerbaruModels> ListTerbarunew;
        ArrayList<ArrayList<ChildTerbarus>> ListChildTerbarunew;
        int count;

        private TerbaruAdapters (Progressactivity context, ArrayList<TerbaruModels>ListTerbaru, ArrayList<ArrayList<ChildTerbarus>> ListChildTerbaru){
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
}
