package com.we_connect_students;



import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

import com.google.android.material.tabs.TabLayout;
import com.we_connect_students.DataBase.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;


public class videopackage extends Fragment {


    String TAG = "Package";
    DBHelper dbHelper;
    Utilis utilis;

    Toolbar toolbar;
    ActionBar actionBar = null;

    RecyclerView recyclerView;

    String str_result = "", str_message = "",text="",examID="";
    ArrayList<videolistpack> listvalues1 = new ArrayList<videolistpack>();


    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

   private View v;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_textpackage, container, false);

        utilis = new Utilis(getActivity());

        dbHelper = new DBHelper(getActivity());


        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);

        listvalues1 = new ArrayList<>();


        Intent videopackage = getActivity().getIntent();

        examID = videopackage.getStringExtra("examid");

        System.out.println("video package id"+examID);

        allSubjectAPI();

//        Recyclerviewadaptervideo recyclerviewadaptervideo = new Recyclerviewadaptervideo(getActivity(), listvalues1);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(recyclerviewadaptervideo);



        return v;


    }

    private void allSubjectAPI() {
        if (utilis.isAvailInternet() == true) {

//            utilis.showProgress(getActivity());

            StringRequest stringRequest = new StringRequest(Request.Method.GET, utilis.Api + utilis.videopackages+"?examIndexId="+examID+"&"+"studentId="+Utilis.strStudentID, new Response.Listener<String>() {
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

                                videolistpack pack = new videolistpack(jsonObject.getString("videoPackageId"),
                                        jsonObject.getString("packageName"),jsonObject.getString("Price"),
                                        jsonObject.getString("NoofVideos"), jsonObject.getString("ExamName"),
                                        jsonObject.getString("SubTitle"), jsonObject.getString("subjectCount"),
                                        jsonObject.getString("Flag"));

                                listvalues1.add(pack);



                            }

                            Recyclerviewadaptervideo recyclerviewadaptervideo = new Recyclerviewadaptervideo(getActivity(), listvalues1);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                             recyclerView.setLayoutManager(linearLayoutManager);
                             recyclerView.setAdapter(recyclerviewadaptervideo);

                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(getActivity(), str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    utilis.getSharedPreference();
//                    System.out.println("id"+utilis.strStudentID);
////                    params.put("studentId",utilis.strStudentID );
////
////                    if (getArguments() != null) {
////                        id= getArguments().getString("examID");
////                    }
////
////                    System.out.println("exam id " +id);
////
////                    params.put("examIndexId",examID );
////
////                    System.out.println((params));
////
////                    return params;
//                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }




    class Recyclerviewadaptervideo extends RecyclerView.Adapter<Recyclerviewadaptervideo.MyViewHolder> {


        Context mcontext;
        ArrayList<videolistpack>listvalues1;

        public Recyclerviewadaptervideo(Context mcontext, ArrayList<videolistpack> listvalues1) {
            this.mcontext = mcontext;
            this.listvalues1 = listvalues1;
        }

        @NonNull
        @Override
        public Recyclerviewadaptervideo.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v;
            v = LayoutInflater.from(mcontext).inflate(R.layout.pack_contentnew,parent,false);
            Recyclerviewadaptervideo.MyViewHolder myViewHolder =new Recyclerviewadaptervideo.MyViewHolder(v);
            return myViewHolder;
        }



        @Override
        public void onBindViewHolder(@NonNull Recyclerviewadaptervideo.MyViewHolder holder, final int position) {

            Random rnd = new Random();
            int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            holder.button.setBackgroundColor(currentColor);
            holder.button.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.p2));


            holder.exames.setText(listvalues1.get(position).getExamName());
            holder.tittle.setText(listvalues1.get(position).getPackageName());
            holder.subjects.setText(listvalues1.get(position).getSubjectCount());
            holder.prices.setText("₹ "+listvalues1.get(position).getPrice());
            holder.noques.setText(listvalues1.get(position).getNoofVideos()+" "+"Nos");
            holder.exam.setText(listvalues1.get(position).getExamName());
            holder.videos.setText("Videos       :");
            holder.viewvideos.setText("VIEW VIDEOS");

            System.out.println("buy"+listvalues1.get(position).getFlag());

            String busycount=listvalues1.get(position).getFlag();


            String id= utilis.BUYID;





            if(busycount.equals("0")){
                holder.buy.setVisibility(View.GONE);
                holder.create.setVisibility(View.GONE);

            }else {
                holder.buy.setVisibility(View.GONE);
                holder.create.setVisibility(View.VISIBLE);
            }


            holder.buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    viewHolder.buy.setVisibility(View.GONE);
//                    viewHolder.create.setVisibility(View.VISIBLE);


                    String id=listvalues1.get(position).getPackageIndexId();
                    Intent intent = new Intent(getActivity(), BuyActivity.class); //OrderDetailsActivity

                    intent.putExtra("Qcount",id );
                    intent.putExtra("examid",examID);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);



                }
            });


            holder.create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


//                    startActivity(new Intent(PackageActivity.this, CreateTestActivity.class));
//                    finish();

                    String id=listvalues1.get(position).getPackageIndexId();
                    Intent intent = new Intent(getActivity(), videos.class); //OrderDetailsActivity

                    intent.putExtra("Qcount",id );
                    intent.putExtra("examid",examID);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);






                }
            });



            holder.viewdet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {




                    String id=listvalues1.get(position).getPackageIndexId();
                    Intent intent = new Intent(getActivity(), videopackagedetails.class); //OrderDetailsActivity

                    intent.putExtra("Qcount",id );
                    intent.putExtra("examid",examID);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });










//            viewHolder.reset.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//
//
//String id=arrayList.get(i).gettid();
//
//
//
////cehckreattemtpt(id);
//        allSubjectAPIs(id);
//
//
//    }
//});







        }

        @Override
        public int getItemCount() {
            return listvalues1.size();
        }

        public  class MyViewHolder extends RecyclerView.ViewHolder{



            LinearLayout create,buy,viewdet,button;
            TextView subjects,exam,noques,tittle,prices,exames,videos,viewvideos;


            public MyViewHolder(@NonNull View view) {



                super(view);

                exames= view.findViewById(R.id.exames);
                subjects = view.findViewById(R.id.subjects);
                noques = view.findViewById(R.id.noques);
                exam = view.findViewById(R.id.exam);
                prices=view.findViewById(R.id.prices);
                videos = view.findViewById(R.id.videos);
                tittle=view.findViewById(R.id.tittle);
                viewvideos=view.findViewById(R.id.viewvideos);


                buy=view.findViewById(R.id.buy1);
                viewdet=view.findViewById(R.id.viewdet1);
                create=view.findViewById(R.id.create);
                button=view.findViewById(R.id.button);
                view.setTag(itemView);

            }
        }

    }











}

