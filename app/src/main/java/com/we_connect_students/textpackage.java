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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.we_connect_students.DataBase.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;



public class textpackage extends Fragment {

    String TAG = "Package";
    DBHelper dbHelper;
    Utilis utilis;
    PackageActivity packageActivity;
    Toolbar toolbar;
    ActionBar actionBar = null;

    RecyclerView recyclerView;

    String str_result = "", str_message = "",text="",examID="";

    ArrayList<packlistnew> listvalues;

    View v;
    String id="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_textpackage, container, false);

        utilis = new Utilis(getActivity());

        dbHelper = new DBHelper(getActivity());
        Context mContext;

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);

        Intent textpackage = getActivity().getIntent();

        examID = textpackage.getStringExtra("examid");

        System.out.println("exam id new "+examID);


        listvalues =new ArrayList<>();

        allSubjectAPI();

        return v;

    }



    private void allSubjectAPI() {

        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(getActivity());

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.packagessnew, new Response.Listener<String>() {
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

                            packlistnew pack = new packlistnew();
                                  pack.setPackageIndexId(jsonObject.getString("packageIndexId"));
                                  pack.setPackageName(jsonObject.getString("packageName"));
                                  pack.setPrice(jsonObject.getString("Price"));
                                  pack.setNoofQuestions(jsonObject.getString("NoofQuestions"));
                                  pack.setExamName(jsonObject.getString("ExamName"));
                                  pack.setSubTitle(jsonObject.getString("SubTitle"));
                                  pack.setSubjectCout(jsonObject.getString("SubjectCout"));
                                  pack.setFlag(jsonObject.getString("Flag"));

                            listvalues.add(pack);

                        }

                            ReportAdapter reportAdapter =new ReportAdapter(getActivity(),listvalues);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(reportAdapter);

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
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    utilis.getSharedPreference();
                    System.out.println("id"+utilis.strStudentID);
                    params.put("studentId",utilis.strStudentID );

                    System.out.println("exam id " +examID);

                    params.put("examIndexId",examID );

                    System.out.println((params));

                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }






    class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
        Context con;
        ArrayList<packlistnew> listvalues;

        public ReportAdapter(Context con, ArrayList<packlistnew> listvalues) {

            this.con = con;
            this.listvalues = listvalues;
        }



        @NonNull
        @Override
        public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pack_contentnew, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ReportAdapter.ViewHolder viewHolder, final int i) {

            Random rnd = new Random();
            int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            viewHolder.button.setBackgroundColor(currentColor);
              viewHolder.button.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.p2));

            viewHolder.exames.setText(listvalues.get(i).getexamName());
            viewHolder.tittle.setText(listvalues.get(i).getpackageName());
            viewHolder.subjects.setText(listvalues.get(i).getsubjectCout());
            viewHolder.prices.setText("₹ "+listvalues.get(i).getprice());
            viewHolder.noques.setText(listvalues.get(i).getnoofQuestions()+" "+"Nos");
            viewHolder.exam.setText(listvalues.get(i).getexamName());


            System.out.println("buy"+listvalues.get(i).getflag());

            String busycount=listvalues.get(i).getflag();

           String id= utilis.BUYID;

            if(busycount.equals("0")){
                viewHolder.buy.setVisibility(View.GONE);
                viewHolder.create.setVisibility(View.GONE);

            }else {
                viewHolder.buy.setVisibility(View.GONE);
                viewHolder.create.setVisibility(View.VISIBLE);
            }


            viewHolder.buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    viewHolder.buy.setVisibility(View.GONE);
//                    viewHolder.create.setVisibility(View.VISIBLE);


                    String id=listvalues.get(i).getpackageIndexId();
                    Intent intent = new Intent(getActivity(), BuyActivity.class); //OrderDetailsActivity

                   intent.putExtra("Qcount",id );
                    intent.putExtra("examid",examID);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
            });

            viewHolder.create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


//                    startActivity(new Intent(PackageActivity.this, CreateTestActivity.class));
//                    finish();

                    String id=listvalues.get(i).getpackageIndexId();
                    Intent intent = new Intent(getActivity(), CreateTestActivity.class); //OrderDetailsActivity

                    intent.putExtra("Qcount",id );
                    intent.putExtra("examid",examID);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
            });



            viewHolder.viewdet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {




                    String id=listvalues.get(i).getpackageIndexId();
                    Intent intent = new Intent(getActivity(), Packdetails.class); //OrderDetailsActivity

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
                return listvalues.size();
            }

            public class ViewHolder extends RecyclerView.ViewHolder {
                LinearLayout create,buy,viewdet,button;
                TextView subjects,exam,noques,tittle,prices,exames;



                public ViewHolder(View view) {
                    super(view);
                    exames= view.findViewById(R.id.exames);
                    subjects = view.findViewById(R.id.subjects);
                    noques = view.findViewById(R.id.noques);
                    exam = view.findViewById(R.id.exam);
                    prices=view.findViewById(R.id.prices);

                    tittle=view.findViewById(R.id.tittle);


                    buy=view.findViewById(R.id.buy1);
                    viewdet=view.findViewById(R.id.viewdet1);
                    create=view.findViewById(R.id.create);
                    button=view.findViewById(R.id.button);
                    view.setTag(itemView);
                }
            }
        }

        private void cehckreattemtpt(final String id) {


            if (utilis.isAvailInternet() == true) {

                utilis.showProgress(getActivity());

                StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.reattampt, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            System.out.println(TAG + " allSubjectAPI response - " + response);

                            utilis.dismissProgress();

                            str_result = obj.getString("errorCode");
                            System.out.print(TAG + " allSubjectAPI result " + str_result);

                            Toast.makeText(getActivity(),"examid"+str_result,Toast.LENGTH_LONG).show();

                            if (Integer.parseInt(str_result) == 0) {
                                // dbHelper.vatbook.execSQL("delete from " + dbHelper.questionslist);
                                str_message = obj.getString("Message");
                                JSONObject json = obj.getJSONObject("result");

                                String  ids   = json.getString("lastIndexId");

                                allSubjectAPIs(ids);

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
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("lastIndexId",id );

                        return params;
                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

            } else {
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
            }

        }

        private void allSubjectAPIs(final String ids) {



            if (utilis.isAvailInternet() == true) {

                utilis.showProgress(getActivity());

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.question, new Response.Listener<String>() {
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
                           // dbHelper.vatbook.execSQL("delete from " + dbHelper.questionslist);
                            str_message = obj.getString("Message");
                            String Count=obj.getString("count");
                            JSONArray json = obj.getJSONArray("question");
                            System.out.println(TAG + " allSubjectAPI Length " + json.length());
                            for (int i = 0; i < json.length(); i++) {

                                JSONObject jsonObject = json.getJSONObject(i);

                                String Question=jsonObject.getString("question");
                                String questionNumber=jsonObject.getString("questionNumber");
                                String Option1=jsonObject.getString("Option1");
                                String Option2=jsonObject.getString("Option2");
                                String Option3=jsonObject.getString("Option3");
                                String Option4=jsonObject.getString("Option4");
                                String answer=jsonObject.getString("answer");


                                try {

                                    dbHelper.CreateTable(4);
                                    dbHelper.vatbook.execSQL("INSERT INTO "
                                            + dbHelper.questionresponse
                                            + " (Questionno,Question,option1,option2,option3,option4,Answer)"
                                            + " VALUES ('" + questionNumber + "','" + Question + "','" + Option1 + "','" + Option2 + "','" + Option3 + "','" + Option4 + "','" + answer + "')");
                                    System.out.println("login value" + questionNumber + " " + Question);




                                    Intent intent = new Intent(getActivity(), Singlequestion.class); //OrderDetailsActivity

                                    intent.putExtra("Qcount",Count );
                                    intent.putExtra("Id",ids);

                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);





                                } catch (Exception e) {
                                    System.out.println("loginresp=" + e.getMessage());
                                }






                            }



//timercall();
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
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    utilis.getSharedPreference();
                    System.out.println("id"+utilis.strStudentID);
                    params.put("lastIndexId",ids );
                    params.put("studentIndexId",utilis.strStudentID );

                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }





}
