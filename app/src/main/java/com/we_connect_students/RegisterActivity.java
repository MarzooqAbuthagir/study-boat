package com.we_connect_students;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
    String TAG = "RegisterActivity";

    Utilis utilis;

    EditText etName, etEmail, etMobNum, etPassword,etfather, etSchool;
    Button btnRegister;
    String strName = "", strEmail = "", strMobNum = "", strPwd = "",strFath="",cityidinput="", strSchool="";
    String str_result = "", str_message = "";
    ArrayList<States> states = new ArrayList<States>();
    ArrayList<Citys> citys = new ArrayList<Citys>();
    Spinner STATES,CITYS,QUALIFICATION;
    JoinedAdapterlists adaptercity;
CheckBox check;
    Boolean chec;
TextView mytext;
String qualification;
    String[] qualification1 = new String[]{ "Select Qualification","Class 1 - 5","Class 6 - 10","Class 11 - 12","Diploma","Degree","Others"};



    public static    String inputid="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Window window = RegisterActivity.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(RegisterActivity.this,R.color.voil2));




      check=findViewById(R.id.cehck);

        utilis = new Utilis(RegisterActivity.this);
        etMobNum = findViewById(R.id.etMobNum);
        etPassword = findViewById(R.id.etPassword);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etfather=findViewById(R.id.etfName);
        etSchool = findViewById(R.id.etSchool);
        btnRegister = findViewById(R.id.btnRegister);
mytext=findViewById(R.id.my_text_view);

        STATES=findViewById(R.id.stateSpinners);
        QUALIFICATION=findViewById(R.id.stateSpinners1);

        CITYS=findViewById(R.id.stateSpinners2);
        String text = "I accept all the Terms & Conditions.";
        SpannableString spannableString = new SpannableString(text);

        QUALIFICATION.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,qualification1);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        QUALIFICATION.setAdapter(aa);






        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
               // Toast.makeText(RegisterActivity.this, "CLICKED", Toast.LENGTH_SHORT).show();




                Intent intent = new Intent(RegisterActivity.this, ShowWebView2.class); //OrderDetailsActivity


                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();





            }
        };

        spannableString.setSpan(clickableSpan1, 17,36, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        // Initialize a new StyleSpan to display bold text
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);



        // Initialize a new UnderlineSpan
        UnderlineSpan underlineSpan = new UnderlineSpan();

        spannableString.setSpan(underlineSpan, 17,36, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(
//                underlineSpan,
//                text.indexOf("Underline")+17,
//                text.indexOf("Underline") +47+ String.valueOf("Underline").length(),
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
//        );


        mytext.setText(spannableString);
        mytext.setMovementMethod(LinkMovementMethod.getInstance());


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                System.out.println("check"+check.isChecked());

               chec=check.isChecked();

                strMobNum = etMobNum.getText().toString().trim();
                strPwd = etPassword.getText().toString().trim();
                strName = etName.getText().toString().trim();
                strEmail = etEmail.getText().toString().trim();
                strFath=etfather.getText().toString();
                strSchool = etSchool.getText().toString().trim();
                if (strName.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Enter name", Toast.LENGTH_SHORT).show();
                } else if (strFath.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Enter Father's name", Toast.LENGTH_SHORT).show();
                } else if(strEmail.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                } else if (strEmail.length()>0) {
                    if (!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
                        Toast.makeText(RegisterActivity.this, "Enter valid Email", Toast.LENGTH_SHORT).show();
                    } else {
                        otherFieldValidate();
                    }
                } else {
                    otherFieldValidate();
                }
            }
        });


        getsate();


        STATES.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();


                System.out.println("ID"+states.get(position).GETID() );

                inputid=states.get(position).GETID();


                getcity(inputid);



            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        CITYS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();


                System.out.println("ID"+citys.get(position).GETCITY() );

                cityidinput=citys.get(position).GETCID();



            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });




    }

    private void otherFieldValidate() {
        if (strMobNum.equals("")) {
            Toast.makeText(RegisterActivity.this, "Enter mobile number", Toast.LENGTH_SHORT).show();
        } else if (strPwd.equals("")) {
            Toast.makeText(RegisterActivity.this, "Enter password", Toast.LENGTH_SHORT).show();

        } else if (qualification.equalsIgnoreCase("Select Qualification")) {
            Toast.makeText(RegisterActivity.this, "Select Qualification", Toast.LENGTH_SHORT).show();

        } else if (strSchool.equals("")) {
            Toast.makeText(RegisterActivity.this, "Enter School/College/Institution Name", Toast.LENGTH_SHORT).show();

        } else if (inputid.equalsIgnoreCase("4")) {
            Toast.makeText(RegisterActivity.this, "Select State", Toast.LENGTH_SHORT).show();

        } else if (cityidinput.equals("3")) {
            Toast.makeText(RegisterActivity.this, "Select District", Toast.LENGTH_SHORT).show();

        }  else if (check.isChecked()==false) {
            Toast.makeText(RegisterActivity.this, "Please accept Terms and Conditions", Toast.LENGTH_SHORT).show();
        } else {
            registerAPI();
        }
    }


    private void getsate() {


        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(RegisterActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.getsataes, new Response.Listener<String>() {
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

                                States values = new States(jsonObject.getString("stateName"),jsonObject.getString("stateId"));

                                states.add(values);
                            }


                            JoinedAdapterlist adapter = new JoinedAdapterlist(RegisterActivity.this, states);

                            STATES.setAdapter(adapter);



                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(RegisterActivity.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(RegisterActivity.this, RegisterActivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
            Toast.makeText(this, RegisterActivity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }






    }


    private void getcity(final String inputid) {





        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(RegisterActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.getcity, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);

                        System.out.println(TAG + " CITY response - " + response);
                        if (adaptercity==null){

                        }else {
                            adaptercity.clearData();
                        }




                        utilis.dismissProgress();

                        str_result = obj.getString("errorCode");
                        System.out.print(TAG + " result " + str_result);



                        if (Integer.parseInt(str_result) == 0) {

                            if (adaptercity== null) {
                                System.out.println("null");
                            } else {
                                System.out.println("not null");

                                adaptercity.notifyDataSetChanged();
                                CITYS.setAdapter(null);

                                CITYS.setSelection(0);
                            }

                            str_message = obj.getString("Message");

                            JSONArray json = obj.getJSONArray("result");
                            System.out.println(TAG + " allSubjectAPI Length " + json.length());
                            for (int i = 0; i < json.length(); i++) {
                                JSONObject jsonObject = json.getJSONObject(i);
                                Citys values = new Citys(jsonObject.getString("cityName"),jsonObject.getString("cityId"));

                                citys.add(values);


                            }

                            adaptercity = new JoinedAdapterlists(RegisterActivity.this, citys);

                            CITYS.setAdapter(adaptercity);


                            adaptercity.notifyDataSetChanged();


                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(RegisterActivity.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(RegisterActivity.this, RegisterActivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
                    params.put("stateId", inputid);

                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, RegisterActivity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }




    }



    private void registerAPI() {
        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(RegisterActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.register, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);

                        System.out.println(TAG + " registerAPI response - " + response);

                        utilis.dismissProgress();

                        str_result = obj.getString("errorCode");
                        System.out.print(TAG + " registerAPI result " + str_result);

                        if (Integer.parseInt(str_result) == 1) {
                            str_message = obj.getString("Message");

                            Toast.makeText(RegisterActivity.this, str_message, Toast.LENGTH_SHORT).show();

                        } else if (Integer.parseInt(str_result) == 0) {

                            str_message = obj.getString("Message");

                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();

                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(RegisterActivity.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(RegisterActivity.this, RegisterActivity.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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

                    params.put("name", strName);
                    params.put("fatherName", strFath);
                    params.put("emailAddress", strEmail);
                    params.put("mobileNumber", strMobNum);
                    params.put("password", strPwd);
                    params.put("cityId", cityidinput);
                    params.put("stateId",inputid);
                    params.put("Qualification",qualification);
                    params.put("SchoolName",strSchool);
                    System.out.println(TAG + " registerAPI inputs " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, RegisterActivity.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        ((TextView) adapterView.getChildAt(0)).setTextSize(14);





        qualification = qualification1[i].toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }

    private class JoinedAdapterlist  extends BaseAdapter {

        private LayoutInflater mInflater;
        ViewHolder holder;
        private List<States> Modellist = null;
        private ArrayList<States> Join_arraylist;

        public JoinedAdapterlist(Context context, List<States> Joinmodellist) {

            mInflater = LayoutInflater.from(context);
            this.Modellist = Joinmodellist;
            this.Join_arraylist = new ArrayList<States>();
            this.Join_arraylist.addAll(Joinmodellist);

        }

        @Override
        public int getCount() {

            return Modellist.size();
        }

        @Override
        public States getItem(int position) {

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
            States item = Modellist.get(position);
            holder.Title.setText(Modellist.get(position).GETNAME());













            return convertView;
        }

        private class ViewHolder {

            TextView Title;

        }
    }


}
