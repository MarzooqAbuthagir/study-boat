package com.we_connect_students;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.we_connect_students.DataBase.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.we_connect_students.Teststepthree.SubjectName;

public class Testfinal extends AppCompatActivity {
    Utilis utilis;
    ArrayList<String> listName;
    ArrayList<String> subjectname;
    Cursor cursor;
    Toolbar toolbar;
    ActionBar actionBar = null;
    String TAG = "Teststepthree",classidsub="",classid="",testname="",subjectid="",Nquestion="",Repetanswer="yes",Negativemarker="yes",str_message="",testPackageId="",isCorrect="NO",isSkip="NO",isWrong="NO",isAll="NO";
TextView Tname,Tsubject,Tquestion,Ttime,Trepeat,Tnegative,sizetext;
    public static String Chapter="",unit="",str_result="",unitchapter="",time="";
Button submit,backb;
    DBHelper dbHelper;
    String str_itemSUBName="",str_itemUNIT="",str_itemCHAPTER="",Subjectonly="";
    Object[] objArray;
    String newString,subjectstring,Subject;
    int minteger = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvityfinal);
        utilis = new Utilis(Testfinal.this);
        initToolbar();
        getValues();
        dbHelper = new DBHelper(Testfinal.this);
        Tname=findViewById(R.id.tname);
        Tsubject=findViewById(R.id.tsubject);
        Tquestion=findViewById(R.id.tquestion);
        Ttime=findViewById(R.id.ttime);
        Trepeat=findViewById(R.id.trepet);
        Tnegative=findViewById(R.id.tnegtive);
submit=findViewById(R.id.submit);
        backb=findViewById(R.id.backb);
        sizetext = (TextView) findViewById(R.id.sizetext);
        Tname.setText(testname);
        Tsubject.setText(classidsub);
        Tquestion.setText(Nquestion);
        Ttime.setText(time+" "+"Mins");
        Trepeat.setText(Repetanswer);
        Tnegative.setText(Negativemarker);


//        String text = "Test text to display Large size text," +
//                " Medium size text and Small size text in a same TextView.";



//        String text="(You can find this Test under "+"\"Practice Test \""+"Menu after saving.)";
//        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(text);
//        RelativeSizeSpan mediumSizeText = new RelativeSizeSpan(1.0f);
//        ssBuilder.setSpan(
//                mediumSizeText,
//                text.indexOf("\"Practice Test \""),
//                text.indexOf("\"Practice Test \"") + String.valueOf("\"Practice Test\"").length(),
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
//        );
//
//        System.out.println("string"+ssBuilder);
//
//        sizetext.setText(ssBuilder);
        getValueFromCart();


        backb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repetanswer="yes";
                Negativemarker="yes";
                alldetailsAPI(testname,subjectid,Nquestion,Nquestion,Repetanswer,Negativemarker,unit,Chapter);

                 unitchapter=subjectid+","+unit+","+Chapter;

                //inputobjectparamjson(testname,subjectid,Nquestion,Nquestion,Repetanswer,Negativemarker,unit,Chapter);
            }
        });

    }



    private void getValueFromCart() {
        try {
            listName = new ArrayList<String>();
            subjectname = new ArrayList<String>();
            //cartArrayList = new ArrayList<HashMap<String, String>>();
            dbHelper.CreateTable(5);
            cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.questionslist, null);
            System.out.println(TAG + " getValueFromCart count " + cursor.getCount());

            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {


                    str_itemSUBName="";str_itemUNIT="";str_itemCHAPTER="";
                    do {
                        str_itemSUBName = cursor.getString(cursor.getColumnIndex("subject"));
                        str_itemUNIT = cursor.getString(cursor.getColumnIndex("unit"));
                       str_itemCHAPTER = cursor.getString(cursor.getColumnIndex("chapter"));
//                        str_itemQty = cursor.getString(cursor.getColumnIndex("itemQty"));
//                        str_itemVat = cursor.getString(cursor.getColumnIndex("itemVat"));
//                        str_itemPrice = cursor.getString(cursor.getColumnIndex("itemPrice"));
//                        str_itemVatAmt = cursor.getString(cursor.getColumnIndex("itemVatAmt"));
                        System.out.println(TAG + " From Db " + str_itemSUBName );





                        listName.add(str_itemSUBName+"-"+str_itemUNIT+"-"+str_itemCHAPTER);

subjectname.add(str_itemSUBName);





                    } while (cursor.moveToNext());




                    Object[] objNames = listName.toArray();

                    //Second Step: convert Object array to String array

                    String[] strNames = Arrays.copyOf(objNames, objNames.length, String[].class);

                    System.out.println("ArrayList converted to String array");

                    //print elements of String array
                    for(int i=0; i < strNames.length; i++){
                        System.out.println(strNames[i]);
                    }





                    Object[] objNamessub = listName.toArray();

                    //Second Step: convert Object array to String array

                    String[] strNamessub = Arrays.copyOf(objNamessub, objNamessub.length, String[].class);

                    System.out.println("ArrayList converted to String array");

                    //print elements of String array
                    for(int i=0; i < strNamessub.length; i++){
                        System.out.println(strNamessub[i]);
                    }





                }

                callfunc2();
                callfunc1();


            }
        } catch (Exception e) {
            System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
        }
    }

    private void callfunc1() {



        //First Step: convert ArrayList to an Object array.
        Object[] objNames = subjectname.toArray();

        //Second Step: convert Object array to String array

        String[] strNames = Arrays.copyOf(objNames, objNames.length, String[].class);

        System.out.println("ArrayList converted to String array");

        //print elements of String array
        for(int i=0; i < strNames.length; i++){
            System.out.println(strNames[i]);
        }





        for (int i = 0; i < subjectname.size(); i++) {
            System.out.println("subjectname listName"+subjectname.get(i));





            //Second Step: convert Object array to String array






            objArray = subjectname.toArray();



            String[] stringArray = new String[objArray.length];

            // copy elements from object array to string array
            for (int ik = 0; ik < objArray.length; ik++)
                stringArray[ik] = String.valueOf(objArray[ik]);

            System.out.println(Arrays.toString(stringArray));

            System.out.println("string listName"+convertObjectArrayToStrings(stringArray, ","));

//            for(int k=0; k < objArray.length ; k++)
//
//                System.out.println("Elements in Array"+objArray[k]);


            subjectstring = Arrays.toString(stringArray);

            subjectstring = subjectstring.substring(1, subjectstring.length()-1);

            Subjectonly=subjectstring;

            System.out.println("New New String: " + subjectstring);


            System.out.println("inputSubjectonly String: " + Subjectonly);


        }
    }

    private String convertObjectArrayToStrings(String[] stringArray, String s) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : stringArray)
            sb.append(obj.toString()).append(s);
        return sb.substring(0, sb.length() - 1);
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
    private void alldetailsAPI(final String testname, final String subjectid, String nquestion, final String s, String repetanswer, String negativemarker, final String unit, String chapter) {


        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(Testfinal.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.savetestpackage, new Response.Listener<String>() {
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

                            testPackageId=obj.getString("testPackageId");


                            allSubjectAPI();




                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(Testfinal.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(Testfinal.this, Testfinal.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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

                    params.put("studentIndexId",utilis.strStudentID );
                    params.put("packageId",subjectid );
                    params.put("class",SubjectName );
                    params.put("noOfQuestion",Nquestion );
                    params.put("testName",testname );
                    params.put("repeatWrongQuestion",Repetanswer );
                    params.put("negativeMark",Negativemarker );
                   // params.put("unit",unit );
                    params.put("testTime",time );
                    params.put("unitChapter",Subject );
                    params.put("subject",Subjectonly );



                    System.out.println(" inputs_submit " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, Testfinal.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }





    private void inputobjectparamjson(final String testname, final String subjectid, final String nquestion, String s, final String repetanswer, final String negativemarker, String unit, String chapter) {

        utilis.showProgress(Testfinal.this);
        String tag_json_obj = "json_obj_req";





        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                utilis.Api + utilis.savetestpackage, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        utilis.dismissProgress();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                utilis.dismissProgress();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                utilis.getSharedPreference();
                System.out.println("id"+utilis.strStudentID);

                params.put("studentIndexId",utilis.strStudentID );
                // params.put("subject",subjectid );
                params.put("class",subjectid );
                params.put("noOfQuestion",nquestion );
                params.put("testName",testname);
                params.put("repeatWrongQuestion",repetanswer );
                params.put("negativeMark",negativemarker );
                // params.put("unit",unit );
                //params.put("chapter",Chapter );
                params.put("unitChapter",Subject );

                System.out.println(TAG + "  inputs " + params);

                return params;
            }

        };

// Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);


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






        Intent intent = new Intent(Testfinal.this, Teststepthree.class); //OrderDetailsActivity

        intent.putExtra("classid",classid );
        intent.putExtra("subjectID",subjectid );
        intent.putExtra("nquestion",Nquestion );
        intent.putExtra("testname",testname );
        intent.putExtra("repetanswer",Repetanswer );
        intent.putExtra("negativemarker",Negativemarker );
        intent.putExtra("Chapter",Chapter );
        intent.putExtra("unit",unit );
        intent.putExtra("classidsub",classidsub );
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
    }


    private void getValues() {
        Bundle b = getIntent().getExtras();
        if (b != null) {






            classid = b.getString("classid");
            subjectid = b.getString("subjectID");
            Nquestion = b.getString("nquestion");
            Repetanswer = b.getString("repetanswer");
            Negativemarker = b.getString("negativemarker");
            testname = b.getString("testname");
            Chapter = b.getString("Chapter");
            unit = b.getString("unit");
            time = b.getString("time");
            classidsub = b.getString("classidsub");



            System.out.println(TAG + " getValues " + b);





        }
    }





    private void allSubjectAPI() {
        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(Testfinal.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.question, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);

                        System.out.println(TAG + " allSubjectAPI response - " + response);



                        str_result = obj.getString("errorCode");
                        System.out.print(TAG + " allSubjectAPI result " + str_result);

                        if (Integer.parseInt(str_result) == 0) {
                            dbHelper.vatbook.execSQL("delete from " + dbHelper.questionslist);
                            str_message = obj.getString("Message");
                              String Count=obj.getString("count");
                              JSONArray json = obj.getJSONArray("question");
                            System.out.println(TAG + " allSubjectAPI Length " + json.length());
                            for (int i = 0; i < json.length(); i++) {

                                JSONObject jsonObject = json.getJSONObject(i);

                                String Question=jsonObject.getString("question");

                                String questionIndexId=jsonObject.getString("questionIndexId");
                                String questionNumber=jsonObject.getString("questionNumber");
                                String Option1=jsonObject.getString("Option1");
                                String Option2=jsonObject.getString("Option2");
                                String Option3=jsonObject.getString("Option3");
                                String Option4=jsonObject.getString("Option4");
                                String answer=jsonObject.getString("answer");
                                String explanation=jsonObject.getString("explanation");
                                String subjectName=jsonObject.getString("subjectName");
                                String chapterName=jsonObject.getString("chapterName");

                                Question=Question.replaceAll("'" ," \"");
                                Option1=Option1.replaceAll("'"," \"");
                                Option2=Option2.replaceAll("'"," \"");
                                Option3=Option3.replaceAll("'","\" ");
                                Option4=Option4.replaceAll("'","\" ");
                                answer=answer.replaceAll("'"," \"");




                                Question=Question.replaceAll("''" ," \"\"");
                                Option1=Option1.replaceAll("''"," \"\"");
                                Option2=Option2.replaceAll("''"," \"\"");
                                Option3=Option3.replaceAll("''","\"\" ");
                                Option4=Option4.replaceAll("''","\"\" ");
                                answer=answer.replaceAll("''"," \"\"");



                                try {
                                    String sectionids="0";
                                    dbHelper.CreateTable(4);
                                    dbHelper.vatbook.execSQL("INSERT INTO "
                                            + dbHelper.questionresponse
                                            + " (Questionno,Question,option1,option2,option3,option4,Answer,Explanation,subjectName,chapterName,questionIndexId,sectionid)"
                                            + " VALUES ('" + questionNumber + "','" + Question + "','" + Option1 + "','" + Option2 + "','" + Option3 + "','" + Option4 + "','" + answer + "','" + explanation + "','" + subjectName + "','" + chapterName + "','" + questionIndexId + "','" + sectionids + "')");
                                    System.out.println("login value" + questionNumber + " " + Question);



                                    try {
                                    String Acoun="3";
                                    String selected="";
String IsCorrect="NO";
                                        dbHelper.CreateTable(6);
                                    dbHelper.vatbook.execSQL("INSERT INTO "
                                            + dbHelper.selctedlist
                                            + "(Questionno, correctanser,selectedanser,Acount,Questoin,Explains,Answer,Option1,Option2,Option3,Option4,Wrongquestion,wronganser,subjectName,chapterName,questionIndexId,isCorrect)"
                                            + " VALUES ('" + questionNumber + "', '" + selected + "',  '" + selected + "','" + Acoun + "',  '" + Question + "',' " + explanation +  "',' " + answer +  "', ' " + Option1 +  "',  ' " + Option2 +  "',' " + Option3 +  "',' " + Option4 +  "',' " + minteger +  "',' " + selected +  "','" + subjectName + "','" + chapterName + "','" + questionIndexId + "','" + IsCorrect +"')");


                                        System.out.println("insert6" + dbHelper.selctedlist
                                                + "(Questionno, correctanser,selectedanser,Acount,Questoin,Explains,Answer,Option1,Option2,Option3,Option4,Wrongquestion,wronganser,isCorrect)"
                                                + " VALUES ('" + questionNumber + "', '" + selected + "',  '" + selected + "','" + Acoun + "',  '" + Question + "',' " + explanation +  "',' " + answer +  "', ' " + Option1 +  "',  ' " + Option2 +  "',' " + Option3 +  "',' " + Option4 +  "',' " + minteger +  "',' " + selected +  "','" + subjectName + "','" + chapterName + "','" + questionIndexId + "','" + IsCorrect +"')");

//                                    dbHelper.CreateTable(6);
//                                    dbHelper.vatbook.execSQL("INSERT INTO "
//                                            + dbHelper.selctedlist
//                                            + " (Questionno,Questoin,Option1,Option2,Option3,Option4,Answer,Explains)"
//                                            + " VALUES ('" + questionNumber + "','" + Question + "','" + Option1 + "','" + Option2 + "','" + Option3 + "','" + Option4 + "','" + answer + "','" + explanation + "')");
//                                    System.out.println("login value" + questionNumber + " " + Question);

                                    } catch (Exception e) {
                                        System.out.println("loginresp=" + e.getMessage());
                                    }







                                } catch (Exception e) {
                                    System.out.println("loginresp=" + e.getMessage());
                                }






                            }







                            utilis.dismissProgress();



                            Intent intent = new Intent(Testfinal.this, Singlequestion.class); //OrderDetailsActivity

                            intent.putExtra("Qcount",Count );
                            intent.putExtra("Id",testPackageId );
                            intent.putExtra("time",time );
                            intent.putExtra("testName",testname );
                            intent.putExtra("isCorrect",isCorrect );
                            intent.putExtra("isWrong",isWrong );
                            intent.putExtra("isSkip",isSkip );
                            intent.putExtra("isAll",isAll );
                            //  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            overridePendingTransition (0, 0);




//timercall();
                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(Testfinal.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(Testfinal.this, Testfinal.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
                    params.put("lastIndexId",testPackageId );
                    params.put("studentIndexId",utilis.strStudentID );
                    System.out.println(TAG + " loginAPI inputs " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, Testfinal.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }



    }
