package com.we_connect_students;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Historyactivity extends AppCompatActivity {
    DBHelper dbHelper;
    Utilis utilis;
    String id="";
    ListView Listview;
    public static TextView scores,persentage,btime,noof;
    ArrayList<Detailrepot> detailrepot = new ArrayList<Detailrepot>();
    String TAG = "Detailrepotacticity";
    String str_result = "", str_message = "",text="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_activity_1);
        utilis = new Utilis(Historyactivity.this);
        dbHelper = new DBHelper(Historyactivity.this);
        Intent intent = getIntent();
        id = intent.getStringExtra("UserID");

        System.out.println("Historyactivity"+id);

        Listview = findViewById(R.id.listview);
        scores=findViewById(R.id.score);
        persentage=findViewById(R.id.persentage);
        btime=findViewById(R.id.btime);
        noof=findViewById(R.id.noof);
        allSubjectAPI(id);



     }


    private void allSubjectAPI(final String id) {

        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(Historyactivity.this);

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






                            str_message = obj.getString("Message");
                            JSONObject avarage=obj.getJSONObject("result");
                            String score=avarage.getString("score");
                            String averagepercentsge=avarage.getString("averagePercentage");
                            String avaragetime=avarage.getString("averageTime");
                            String noofattempt=avarage.getString("noOfAttempt");

//                            scores.setText(score);
//                            persentage.setText(averagepercentsge);
//                            btime.setText(avaragetime);
//
//                            noof.setText(noofattempt);

                            JSONArray json = avarage.getJSONArray("history");
                            System.out.println(TAG + " allSubjectAPI Length " + json.length());
                            for (int i = json.length()-1; i >=0; i--) {

                                JSONObject jsonObject = json.getJSONObject(i);

                                Detailrepot detailrepotlist = new Detailrepot(jsonObject.getString("serialNumber"),
                                        jsonObject.getString("date"),jsonObject.getString("rightAnswerCount"),
                                        jsonObject.getString("percentage"), jsonObject.getString("duration"),jsonObject.getString("Time")
                                        ,jsonObject.getString("total"));

                                detailrepot.add(detailrepotlist);

                            }

                            ArrayList<Detailrepot> tempList = new ArrayList<>();

                            for (int i=detailrepot.size()-1; i>=0; i--) {
                                System.out.println("serial value "+ i);
                                System.out.println("serial value "+ detailrepot.get(i).getTime());
                                Detailrepot detailrepotlist = new Detailrepot(String.valueOf(i+1),
                                        detailrepot.get(i).getdate(),detailrepot.get(i).getrightAnswerCount(),
                                        detailrepot.get(i).getpercentage(), detailrepot.get(i).getduration(),
                                        detailrepot.get(i).getTime(),detailrepot.get(i).getTotal());

                                tempList.add(detailrepotlist);
                            }

                         JoinedAdapterlist adapter = new JoinedAdapterlist(Historyactivity.this, tempList);


                            Listview.setAdapter(adapter);

                            //  Utility.setListViewHeightBasedOnChildren(Listview);


                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(Historyactivity.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(Historyactivity.this, Historyactivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
                    params.put("studentIndexId",utilis.strStudentID);
                    System.out.println(TAG + "  packageIndexId " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, Historyactivity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
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
                holder.Type1=convertView.findViewById(R.id.Type1);
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
            holder.Type1.setText(Modellist.get(position).getTime());
            holder.Duration.setText(Modellist.get(position).getrightAnswerCount()+"/"+Modellist.get(position).getTotal());
            holder.Marks.setText(Modellist.get(position).getpercentage());
            holder.Attempts.setText(Modellist.get(position).getduration());











            return convertView;
        }

        private class ViewHolder {

            TextView Title,Type,Duration,Marks,Attempts,Type1;

        }
    }
}
