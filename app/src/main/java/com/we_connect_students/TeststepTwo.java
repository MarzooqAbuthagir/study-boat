package com.we_connect_students;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeststepTwo extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String TAG = "Teststeptwo",classid="",subjectid="",Nquestion="",Repetanswer="",Negativemarker="",str_result="",str_message="",Time="";
   public static ArrayList<String> listName;
    Utilis utilis;
    DBHelper dbHelper;

    Toolbar toolbar;
    ActionBar actionBar = null;


    String[] repet_question = {"Yes","No"};
    String[] negativemarker = {"Yes","No"};
   Button submit,backb;
EditText Testname;
    public static ArrayList<HashMap<String, String>> cartArrayList = null;
    Cursor cursor;
    HashMap<String, String> map;
    Object[] objArray;
    String newString;
    public  static String Subject="",str_itemName="";
   public static Spinner spin,spintwo;
   public static String SpinnerValue="",SpinnerValues="";
//ListView spin;
    ArrayList<Questiontime> questiontime = new ArrayList<Questiontime>();
    ArrayList<Questiontimes> questiontime2 = new ArrayList<Questiontimes>();

public static TextView tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test2);
        utilis = new Utilis(TeststepTwo.this);
        getValues();
        initToolbar();

        dbHelper = new DBHelper(TeststepTwo.this);
         spin = (Spinner) findViewById(R.id.stateSpinner);

        spintwo = (Spinner) findViewById(R.id.stateSpinners);





        Spinner spin2 = (Spinner) findViewById(R.id.repetwrong);
        ArrayAdapter<String> bb = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, repet_question);
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(bb);
        spin2.setOnItemSelectedListener(this);

        Spinner spin3 = (Spinner) findViewById(R.id.mark);
        ArrayAdapter<String> cc = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, negativemarker);
        cc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin3.setAdapter(cc);
        spin3.setOnItemSelectedListener(this);
Testname =findViewById(R.id.et);
        submit=findViewById(R.id.submit);

        backb=findViewById(R.id.backb);

        backb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String testname=Testname.getText().toString().trim();


                System.out.println("selectspinner"+SpinnerValue);
                System.out.println("selectspinnertwo"+SpinnerValues);
                Nquestion =SpinnerValue;
                Time=SpinnerValues;
                if (testname.equals("")){
                    Toast.makeText(TeststepTwo.this, "Please Enter Test Name", Toast.LENGTH_SHORT).show();
                }else {

                    System.out.println(classid+""+subjectid+""+Nquestion+""+testname+""+Repetanswer+""+Negativemarker);
                    apicallfunction(classid,subjectid,Nquestion,testname,Repetanswer,Negativemarker,Time);
                }


            }
        });

locallvalues();
getquestionapi();



        //Adding setOnItemSelectedListener method on spinner.
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

               // Toast.makeText(getApplicationContext(),"You Selected "+questiontime.get(position).getnumber()+ " as Country",Toast.LENGTH_SHORT).show();


                SpinnerValue=questiontime.get(position).getnumber();
               // Toast.makeText(TeststepTwo.this, spin.getSelectedItem().toString(), Toast.LENGTH_LONG).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });




        spintwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                // Toast.makeText(getApplicationContext(),"You Selected "+questiontime.get(position).getnumber()+ " as Country",Toast.LENGTH_SHORT).show();


                SpinnerValues=questiontime2.get(position).gettime();
                // Toast.makeText(TeststepTwo.this, spin.getSelectedItem().toString(), Toast.LENGTH_LONG).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });








    }





    private void getquestionapi() {


        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(TeststepTwo.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.questioncount, new Response.Listener<String>() {
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

                                Questiontime values = new Questiontime(jsonObject.getString("numbers"));

                                questiontime.add(values);
                            }
                            JoinedAdapterlist adapter = new JoinedAdapterlist(TeststepTwo.this, questiontime);


                            spin.setAdapter(adapter);





                            gettimeapi();


                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(TeststepTwo.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(TeststepTwo.this, TeststepTwo.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, TeststepTwo.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }






    }

    private void gettimeapi() {




        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(TeststepTwo.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.questiontime, new Response.Listener<String>() {
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

                                Questiontimes values = new Questiontimes(jsonObject.getString("numbers"));

                                questiontime2.add(values);
                            }
                            JoinedAdapterlist2 adapters = new JoinedAdapterlist2(TeststepTwo.this, questiontime2);






                            spintwo.setAdapter(adapters);


                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(TeststepTwo.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(TeststepTwo.this, TeststepTwo.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, TeststepTwo.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }




    }

    private void locallvalues() {


        try {
            listName = new ArrayList<String>();
            cartArrayList = new ArrayList<HashMap<String, String>>();
            dbHelper.CreateTable(2);
            cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.Subject, null);
            System.out.println(TAG + " getValueFromCart count " + cursor.getCount());

            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {

                    // str_itemCode = "";
                    str_itemName = "";
//                    str_itemIndex = "";
//                    str_itemQty = "";
//                    str_itemVat = "";
//                    str_itemPrice = "";
//                    str_itemVatAmt = "";

                    do {
                        str_itemName = cursor.getString(cursor.getColumnIndex("subjectName"));
//                        str_itemName = cursor.getString(cursor.getColumnIndex("itemName"));
//                        str_itemIndex = cursor.getString(cursor.getColumnIndex("itemIndex"));
//                        str_itemQty = cursor.getString(cursor.getColumnIndex("itemQty"));
//                        str_itemVat = cursor.getString(cursor.getColumnIndex("itemVat"));
//                        str_itemPrice = cursor.getString(cursor.getColumnIndex("itemPrice"));
//                        str_itemVatAmt = cursor.getString(cursor.getColumnIndex("itemVatAmt"));
                        System.out.println(TAG + " From Db " + str_itemName );





                        listName.add(str_itemName);



                        map = new HashMap<String, String>();
                        // map.put(Constants.HM_ITEMCODE, str_itemCode);
                        map.put(Constants.HM_ITEMNAME, str_itemName);
                        //  map.put(Constants.HM_ITEMINDEX, str_itemIndex);
                        // map.put(Constants.HM_ITEMPRICE, str_itemPrice);
                        // map.put(Constants.HM_VATPERCENT, str_itemVat);
                        // map.put(Constants.HM_QTY, str_itemQty);
                        //  map.put(Constants.HM_VATAMT, str_itemVatAmt);

                        cartArrayList.add(map);



                    } while (cursor.moveToNext());




                    Object[] objNames = listName.toArray();

                    //Second Step: convert Object array to String array

                    String[] strNames = Arrays.copyOf(objNames, objNames.length, String[].class);

                    System.out.println("ArrayList converted to String array");

                    //print elements of String array
                    for(int i=0; i < strNames.length; i++){
                        System.out.println(strNames[i]);
                    }




                }


                callfunc2();


            }
        } catch (Exception e) {
            System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
        }

    }


    private void callfunc2() {









        //First Step: convert ArrayList to an Object array.
        Object[] objNames = listName.toArray();

        //Second Step: convert Object array to String array

        String[] strNames = Arrays.copyOf(objNames, objNames.length, String[].class);

        System.out.println("ArrayList converted to String array");

        //print elements of String array
        for(int i=0; i < strNames.length; i++){
            System.out.println(strNames[i]);
        }





        for (int i = 0; i < listName.size(); i++) {
            System.out.println("arraylist listName"+listName.get(i));





            //Second Step: convert Object array to String array






            objArray = listName.toArray();



            String[] stringArray = new String[objArray.length];

            // copy elements from object array to string array
            for (int ik = 0; ik < objArray.length; ik++)
                stringArray[ik] = String.valueOf(objArray[ik]);

            System.out.println(Arrays.toString(stringArray));

            System.out.println("string listName"+convertObjectArrayToString(stringArray, ","));

//            for(int k=0; k < objArray.length ; k++)
//
//                System.out.println("Elements in Array"+objArray[k]);


            newString = Arrays.toString(stringArray);

            newString = newString.substring(1, newString.length()-1);

            Subject=newString;

            System.out.println("New New String: " + newString);

            System.out.println("input String: " + Subject);


        }



    }


    private String convertObjectArrayToString(String[] stringArray, String s) {


        StringBuilder sb = new StringBuilder();
        for (Object obj : stringArray)
            sb.append(obj.toString()).append(s);
        return sb.substring(0, sb.length() - 1);

    }




    private void apicallfunction(String classid, String subjectid, String nquestion, String testname, String repetanswer, String negativemarker, String time) {

        System.out.println("noquestion"+nquestion);

        Intent intent = new Intent(TeststepTwo.this, Teststepthree.class); //OrderDetailsActivity





        intent.putExtra("examid",Subject );
        intent.putExtra("Qcount",subjectid );
         intent.putExtra("nquestion",nquestion );
        intent.putExtra("testname",testname );
        intent.putExtra("repetanswer",repetanswer );
        intent.putExtra("negativemarker",negativemarker );
        intent.putExtra("time",time );
        intent.putExtra("array_list", listName);



        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id ){

        Spinner spin2 = (Spinner)parent;
        Spinner spin3 = (Spinner)parent;

        if(spin2.getId() == R.id.repetwrong)
        {
           // Toast.makeText(this, "Your repet_question :" + repet_question[position],Toast.LENGTH_SHORT).show();
        Repetanswer=repet_question[position];
        }
        if(spin3.getId() == R.id.mark)
        {
          //  Toast.makeText(this, "Your negative marker :" + negativemarker[position],Toast.LENGTH_SHORT).show();
        Negativemarker=negativemarker[position];
        }

    }

    public void onNothingSelected(AdapterView<?> parent){
        Toast.makeText(this, "Choose Countries :", Toast.LENGTH_SHORT).show();
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




        dbHelper.CreateTable(5);
        Cursor c = dbHelper.vatbook.rawQuery("select * from " + dbHelper.Subject + "  ", null);
        System.out.println("Add card table count" + c.getCount());
        if (c.getCount() == 0) {


            Intent intent = new Intent(TeststepTwo.this, CreateTestActivity.class); //OrderDetailsActivity
            intent.putExtra("Qcount", subjectid);


            intent.putExtra("examid", classid);

            System.out.println("TESTTWO"+subjectid+classid);
            // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);




            finish();

        } else {

            dbHelper.vatbook.execSQL("delete from " + dbHelper.Subject);
            Intent intent = new Intent(TeststepTwo.this, CreateTestActivity.class); //OrderDetailsActivity
            intent.putExtra("Qcount", subjectid);


            intent.putExtra("examid", classid);

            System.out.println("TESTTWO"+subjectid+classid);
            // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }











//        startActivity(new Intent(TeststepTwo.this, CreateTestActivity.class));
//
//        finish();
    }


    private void getValues() {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            classid = b.getString("examid");
            subjectid = b.getString("Qcount");

            System.out.println(TAG + " getValues " + b);





        }
    }




    private class JoinedAdapterlist  extends BaseAdapter {

        private LayoutInflater mInflater;
        ViewHolder holder;
        private List<Questiontime> Modellist = null;
        private ArrayList<Questiontime> Join_arraylist;

        public JoinedAdapterlist(Context context, List<Questiontime> Joinmodellist) {

            mInflater = LayoutInflater.from(context);
            this.Modellist = Joinmodellist;
            this.Join_arraylist = new ArrayList<Questiontime>();
            this.Join_arraylist.addAll(Joinmodellist);

        }

        @Override
        public int getCount() {

            return Modellist.size();
        }

        @Override
        public Questiontime getItem(int position) {

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

                convertView = mInflater.inflate(R.layout.question, null);

                holder.Title=convertView.findViewById(R.id.Title);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Questiontime item = Modellist.get(position);
            holder.Title.setText(Modellist.get(position).getnumber());













            return convertView;
        }

        private class ViewHolder {

            TextView Title;

        }
    }













    private class JoinedAdapterlist2  extends BaseAdapter {

        private LayoutInflater mInflater;
        ViewHolder holder;
        private List<Questiontimes> Modellist = null;
        private ArrayList<Questiontimes> Join_arraylist;

        public JoinedAdapterlist2(Context context, List<Questiontimes> Joinmodellist) {

            mInflater = LayoutInflater.from(context);
            this.Modellist = Joinmodellist;
            this.Join_arraylist = new ArrayList<Questiontimes>();
            this.Join_arraylist.addAll(Joinmodellist);

        }

        @Override
        public int getCount() {

            return Modellist.size();
        }

        @Override
        public Questiontimes getItem(int position) {

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

                convertView = mInflater.inflate(R.layout.question, null);

                holder.Title=convertView.findViewById(R.id.Title);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Questiontimes item = Modellist.get(position);
            holder.Title.setText(Modellist.get(position).gettime());













            return convertView;
        }

        private class ViewHolder {

            TextView Title;

        }
    }

}
