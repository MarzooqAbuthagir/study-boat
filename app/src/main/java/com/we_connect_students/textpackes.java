package com.we_connect_students;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class textpackes extends AppCompatActivity {

    String TAG = "Package";
    DBHelper dbHelper;
    Utilis utilis;
    PackageActivity packageActivity;
    Toolbar toolbar;
    ActionBar actionBar = null;

    RecyclerView recyclerView;

    String str_result = "", str_message = "",text="",examID="";

    ArrayList<packlistnew> listvalues;


TextView tvEmpty;
    String id="";
    ReportAdapter reportAdapter;
    LinearLayout listLayout;
    EditText etSearch;
    TextView tvClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textpackes);

        utilis = new Utilis(textpackes.this);

        dbHelper = new DBHelper(textpackes.this);
        Context mContext;


        tvEmpty = findViewById(R.id.tvEmpty);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        etSearch = findViewById(R.id.etSearch);
        listLayout = findViewById(R.id.listLayout);
        tvClose = findViewById(R.id.tvClose);




        Intent textpackage = getIntent();

        examID = textpackage.getStringExtra("examid");

        System.out.println("exam id new "+examID);


        listvalues =new ArrayList<>();

//
//        textrecycleradapter reportAdapter =new textrecycleradapter(getActivity(),listvalues);
//                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//                            recyclerView.setLayoutManager(linearLayoutManager);
//                            recyclerView.setAdapter(reportAdapter);


//         local();


        allSubjectAPI();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().length() > 0) {
                    reportAdapter.filter(charSequence.toString());
                    tvClose.setVisibility(View.VISIBLE);
                } else {
                    tvClose.setVisibility(View.GONE);
                    reportAdapter.filter("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etSearch.setText("");
                tvClose.setVisibility(View.GONE);
            }
        });
    }

    private void allSubjectAPI() {

        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(textpackes.this);

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

                            listLayout.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                            tvEmpty.setVisibility(View.GONE);

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

//                            Toast.makeText(getActivity(),"list size"+String.valueOf(listvalues.size()),Toast.LENGTH_LONG).show();
//                            Toast.makeText(getActivity(),listvalues.get(1).getpackageIndexId().toString(),Toast.LENGTH_LONG).show();
//                            System.out.println("list values"+listvalues.toString());

                            reportAdapter =new ReportAdapter(textpackes.this,listvalues);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(textpackes.this);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(reportAdapter);



                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

//                            Toast.makeText(textpackes.this, str_message, Toast.LENGTH_SHORT).show();

                            listLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.GONE);
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
                    Toast.makeText(textpackes.this, textpackes.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
            VolleySingleton.getInstance(textpackes.this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(textpackes.this, textpackes.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() >=0) {
                    reportAdapter.filter(newText);
                    reportAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }*/

    class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
        Context con;
        ArrayList<packlistnew> listvalues;
        ArrayList<packlistnew> searchValues = new ArrayList<>();

        public ReportAdapter(Context con, ArrayList<packlistnew> listvalues) {

            this.con = con;
            this.listvalues = listvalues;
            this.searchValues.addAll(listvalues);
        }



        @NonNull
        @Override
        public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pack_contentnew, viewGroup, false);

            return new ReportAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ReportAdapter.ViewHolder viewHolder, final int i) {

            Random rnd = new Random();
            int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            viewHolder.button.setBackgroundColor(currentColor);
            viewHolder.button.setBackground(ContextCompat.getDrawable(textpackes.this,R.drawable.p2));

            viewHolder.exames.setText(listvalues.get(i).getexamName());
            viewHolder.tittle.setText(listvalues.get(i).getpackageName());
            viewHolder.subjects.setText(": "+listvalues.get(i).getsubjectCout());
            viewHolder.prices.setText(": ₹ "+listvalues.get(i).getprice());
            viewHolder.noques.setText(": "+listvalues.get(i).getnoofQuestions()+" "+"Nos");
            viewHolder.exam.setText(": "+listvalues.get(i).getexamName());

//            viewHolder.exames.setText(listvalues.get(i).getpackageIndexId());
//            viewHolder.tittle.setText(listvalues.get(i).getpackageName());
//            viewHolder.subjects.setText(listvalues.get(i).getsubjectCout());
//            viewHolder.prices.setText("₹ "+listvalues.get(i).getprice());
//            viewHolder.noques.setText(listvalues.get(i).getnoofQuestions()+" "+"Nos");
//            viewHolder.exam.setText(listvalues.get(i).getexamName());


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
                    Intent intent = new Intent(textpackes.this, BuyActivity.class); //OrderDetailsActivity

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
                    Intent intent = new Intent(textpackes.this, CreateTestActivity.class); //OrderDetailsActivity

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
                    Intent intent = new Intent(textpackes.this, Packdetails.class); //OrderDetailsActivity

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

        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            listvalues.clear();

            if (charText.length() == 0) {
                listvalues.addAll(searchValues);

            } else {

                    for (packlistnew p : searchValues) {

                        if (p.getpackageName().toLowerCase(Locale.getDefault()).contains(charText) ||
                                p.getnoofQuestions().toLowerCase(Locale.getDefault()).contains(charText) ||
                                p.getexamName().toLowerCase(Locale.getDefault()).contains(charText) ||
                                p.getsubTitle().toLowerCase(Locale.getDefault()).contains(charText) ||
                                p.getsubjectCout().toLowerCase(Locale.getDefault()).contains(charText) ||
                                p.getprice().toLowerCase(Locale.getDefault()).contains(charText)) {
                            listvalues.add(p);
                        }
                    }


            }
            notifyDataSetChanged();

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

            utilis.showProgress(textpackes.this);

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

                        Toast.makeText(textpackes.this,"examid"+str_result,Toast.LENGTH_LONG).show();

                        if (Integer.parseInt(str_result) == 0) {
                            // dbHelper.vatbook.execSQL("delete from " + dbHelper.questionslist);
                            str_message = obj.getString("Message");
                            JSONObject json = obj.getJSONObject("result");

                            String  ids   = json.getString("lastIndexId");

                            allSubjectAPIs(ids);

                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(textpackes.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(textpackes.this, textpackes.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
            VolleySingleton.getInstance(textpackes.this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(textpackes.this, textpackes.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }

    }

    private void allSubjectAPIs(final String ids) {



        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(textpackes.this);

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




                                    Intent intent = new Intent(textpackes.this, Singlequestion.class); //OrderDetailsActivity

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

                            Toast.makeText(textpackes.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(textpackes.this, textpackes.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
            VolleySingleton.getInstance(textpackes.this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(textpackes.this, textpackes.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }




}
