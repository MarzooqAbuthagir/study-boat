package com.we_connect_students;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.SQLException;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teststepthree extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{

    String TAG = "Teststepthree",classid="",testname="",subjectid="",subjectID="",Nquestion="",Repetanswer="",Negativemarker="",str_result="",str_message="",time="";

    Utilis utilis;
    DBHelper dbHelper;
    Toolbar toolbar;
    ActionBar actionBar = null;
    public static ExpandableListView history;
    String str_itemSUBName="",str_itemUNIT="",str_itemCHAPTER="",Subjectonly="";

    ArrayList<String> listName;
    ArrayList<String> subjectname;

    private ArrayList<TerbaruModel> ListTerbaru =  null;

    private ArrayList<ChildTerbaru> ListChildTerbaru =  null;
    private ArrayList<ArrayList<ChildTerbaru>> ListChildXXXXXXXXX = null;


    Button submit,backb;
    public static ArrayList<String> array;
    public static ArrayList<HashMap<String, String>> cartArrayList = null;
    Cursor cursor;
    HashMap<String, String> map;
    ArrayList<String> listOfValues;
    public static String str_itemCode = "", str_itemName = "", str_itemIndex = "", str_itemQty = "", str_itemVat = "", str_itemPrice = "", str_itemVatAmt = "", str_totalAmt = "", str_totalVat = "";
    public static String[] arr_exdate, arr_strItemName, arr_strItemIndex, arr_strItemQty, arr_strItemVat, arr_strItemPrice, arr_strItemVatAmt, arr_strTotalAmt, arr_strTotalVat;
    public  static String SubjectName="";
    TerbaruModel LT =null;
    ChildTerbaru CT =null;
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
        setContentView(R.layout.activity_create_test3);
        utilis = new Utilis(Teststepthree.this);
        dbHelper = new DBHelper(Teststepthree.this);
        initToolbar();
        getValues();
        //getValueFromCart();
        //chapterapi(subjectid);


        history=findViewById(R.id.categories);


        submit=findViewById(R.id.submit);

        backb=findViewById(R.id.backb);




        ListTerbaru = new ArrayList<TerbaruModel>();
        ListChildTerbaru = new ArrayList<ChildTerbaru>();//ArrayList<ArrayList<ChildTerbaru>>>();
        ListChildXXXXXXXXX = new ArrayList<ArrayList<ChildTerbaru>>();


        spinnerOfferType = (Spinner) findViewById(R.id.spinnerOfferType);
        spinnerOfferType.setOnItemSelectedListener(this);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, array);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerOfferType.setAdapter(arrayAdapter);

        spinnerOfferType.setPrompt("Select Subject");

        backb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



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



                    }
                } catch (Exception e) {
                    System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
                }


























                checkapicall(Nquestion,Subject);


















































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

    }

    private void checkapicall(final String nquestion, final String subject) {



        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(Teststepthree.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.checkcount, new Response.Listener<String>() {
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




                            dbHelper.CreateTable(5);
                            Cursor c = dbHelper.vatbook.rawQuery("select * from " + dbHelper.questionslist + "  ", null);
                            System.out.println("Add card table count" + c.getCount());
                            if (c.getCount() == 0) {

                                Toast.makeText(Teststepthree.this, "Select Chapter(s)", Toast.LENGTH_SHORT).show();
                            } else {



                                subjectid=SubjectName;


                                Intent intent = new Intent(Teststepthree.this, Testfinal.class); //OrderDetailsActivity
                                intent.putExtra("classid",subjectid );
                                intent.putExtra("subjectID",subjectID );
                                intent.putExtra("Chapter",Chapter );
                                intent.putExtra("unit",unit );
                                intent.putExtra("nquestion",Nquestion );
                                intent.putExtra("testname",testname );
                                intent.putExtra("repetanswer",Repetanswer );
                                intent.putExtra("negativemarker",Negativemarker );
                                intent.putExtra("time",time );

                                intent.putExtra("classidsub",classid );





                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);



                            }




                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");
                        String    result1 = obj.getString("result1");
                            String result2 = obj.getString("result2");

                            alertcount(result1,result2);
                           // Toast.makeText(Teststepthree.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                private void alertcount(String result1, String result2) {





// custom dialog
                    final Dialog dialog = new Dialog(Teststepthree.this);
                    dialog.setContentView(R.layout.customalert);
                    Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                    TextView text1 = (TextView) dialog.findViewById(R.id.text1);
                    TextView text2 = (TextView) dialog.findViewById(R.id.text2);
                  //  text1.setText(result1);
                  //  text2.setText(result2);
                    text1.setText("No. of Questions Selected :"+" "+result2);
                    text2.setText("No. of Test Questions Set  :"+" "+result1);

                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                        }
                    });
                    dialog.show();








                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(Teststepthree.this, Teststepthree.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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

                    params.put("noOfQuestion",nquestion );
                    params.put("unitChapter",subject );






                    System.out.println(" inputs_submitnew " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, Teststepthree.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }




























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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //  Toast.makeText(getApplicationContext(), array.get(position), Toast.LENGTH_LONG).show();

        SubjectName= array.get(position);

        allsubject();


    }

    private void allsubject() {
        history.setAdapter((ExpandableListAdapter) null);

        TerbaruAdapter adapter = new TerbaruAdapter(Teststepthree.this,ListTerbaru, ListChildXXXXXXXXX);
        history.setAdapter(adapter);

        ListTerbaru.clear();
        ListChildTerbaru.clear();
        ListChildXXXXXXXXX.clear();


        allSubjectAPI();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


//    private class PlaceOrder extends AsyncTask<String, Void, String> {
//        HashMap<String, String> hmap;
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//
//
//
//                for (int j = 0; j < cursor.getCount(); j++) {
//                    System.out.println("Array value" + j);
//
//                    hmap = new HashMap<String, String>();
//                    hmap = cartArrayList.get(j);
//
//                    Object[] objArray = cartArrayList.toArray();
//                    System.out.println("Elements in Array :");
//                    for(int i=0; i < objArray.length ; i++)
//
//                        System.out.println(objArray[i]);
//
//
//
//                    HttpClient httpclient = new DefaultHttpClient();
//
//                    HttpPost httppost = new HttpPost(Constants.BASE_URL + Constants.chapter);
//
//
//
//
//                    // Add your data
//                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//                   // nameValuePairs.add(new BasicNameValuePair("orderId", params[0]));
//
//                    nameValuePairs.add(new BasicNameValuePair("subjectName", hmap.get(Constants.HM_ITEMNAME).toString()));
//                    nameValuePairs.add(new BasicNameValuePair("className",classid ));
////                    nameValuePairs.add(new BasicNameValuePair("itemQuantity", hmap.get(Constants.HM_QTY).toString()));
////                    nameValuePairs.add(new BasicNameValuePair("itemPrice", hmap.get(Constants.HM_ITEMPRICE).toString()));
////                    nameValuePairs.add(new BasicNameValuePair("itemVatpercentage", hmap.get(Constants.HM_VATPERCENT).toString()));
////                    nameValuePairs.add(new BasicNameValuePair("itemVatAmount", hmap.get(Constants.HM_VATAMT).toString()));
//
//                    System.out.println(TAG + " PlaceOrder inputs " + nameValuePairs);
//                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//                    // Execute HTTP Post Request
//                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
//                    String response = httpclient.execute(httppost, responseHandler);
//                    System.out.println(TAG + " PlaceOrder response " + response.toString());
//
//                    JSONObject obj = new JSONObject(response);
//                    str_result = obj.getString("errorCode");
//                    System.out.println(TAG + " PlaceOrder result " + str_result);
//
//                    if (Integer.parseInt(str_result) == 1) {
//                        str_message = obj.getString("Message");
//
//
//
//                    } else if (Integer.parseInt(str_result) == 0) {
//
//                        str_message = obj.getString("Message");
//
//
//                        JSONArray heroArray = obj.getJSONArray("units");
//
//                          LT = null;
//                          CT = null;
//
//                            for(int i=0; i < heroArray.length(); i++){
//                                JSONObject jsonObject = heroArray.getJSONObject(i);
//
//                                ListChildTerbaru = new ArrayList<ChildTerbaru>();
//
//                               LT = new TerbaruModel(jsonObject.optString("unitName"));
//                               ListTerbaru.add(LT);
//                               Log.d("tag", "jsonObj Menu_Cat_Id->" + jsonObject.optString("unitName"));
//
//                               JSONArray jsonArrSubCat = jsonObject.getJSONArray("chapterName");//                                if (jsonArrSubCat != null && jsonArrSubCat.length() > 0) {
//                                 for (int subCnt = 0; subCnt < jsonArrSubCat.length(); subCnt++) {
//                                       JSONObject jsonObjSubCategory = jsonArrSubCat.getJSONObject(subCnt);
//
//
//                                     System.out.println("unitobject"+jsonObjSubCategory.optString("unitName"));
//
//
//
//
//                                       CT = new ChildTerbaru(jsonObjSubCategory.optString("unitName"), jsonObjSubCategory.optString("unitName"));
//                                        ListChildTerbaru.add(CT);
//
//                                        Log.d("tag", "jsonArrSubCat Item_NameEN->" + jsonObjSubCategory.optString("unitName")+jsonObjSubCategory.optString("unitName"));
//                                   }
//                              }
//
//
//
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println(TAG + " PlaceOrder Excep " + e.getMessage());
//            }
//            return str_result;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            utilis.showProgress(Teststepthree.this);
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            if (result != null) {
//                if (result.equals("0")) {
//
//
//
//
//
//                    ListChildXXXXXXXXX.add(ListChildTerbaru);
//
//                               TerbaruAdapter adapter = new TerbaruAdapter(Teststepthree.this,ListTerbaru, ListChildXXXXXXXXX);
//                               history.setAdapter(adapter);
//
//
//
//
//                    dbHelper.vatbook.execSQL("delete from " + dbHelper.Subject);
//
//
//
//                } else if (result.equals("null")) {
//
//
//                } else {
//
//                }
//            } else {
//
//            }
//
//            utilis.dismissProgress();
//        }
//    }








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


//    private void getValueFromCart() {
//        try {
//            listName = new ArrayList<String>();
//            cartArrayList = new ArrayList<HashMap<String, String>>();
//            dbHelper.CreateTable(2);
//            cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.Subject, null);
//            System.out.println(TAG + " getValueFromCart count " + cursor.getCount());
//
//            if (cursor.getCount() > 0) {
//                if (cursor.moveToFirst()) {
//
//                   // str_itemCode = "";
//                   str_itemName = "";
////                    str_itemIndex = "";
////                    str_itemQty = "";
////                    str_itemVat = "";
////                    str_itemPrice = "";
////                    str_itemVatAmt = "";
//
//                    do {
//                        str_itemName = cursor.getString(cursor.getColumnIndex("subjectName"));
////                        str_itemName = cursor.getString(cursor.getColumnIndex("itemName"));
////                        str_itemIndex = cursor.getString(cursor.getColumnIndex("itemIndex"));
////                        str_itemQty = cursor.getString(cursor.getColumnIndex("itemQty"));
////                        str_itemVat = cursor.getString(cursor.getColumnIndex("itemVat"));
////                        str_itemPrice = cursor.getString(cursor.getColumnIndex("itemPrice"));
////                        str_itemVatAmt = cursor.getString(cursor.getColumnIndex("itemVatAmt"));
//                        System.out.println(TAG + " From Db " + str_itemName );
//
//
//
//
//
//                        listName.add(str_itemName);
//
//
//
//                        map = new HashMap<String, String>();
//                       // map.put(Constants.HM_ITEMCODE, str_itemCode);
//                        map.put(Constants.HM_ITEMNAME, str_itemName);
//                      //  map.put(Constants.HM_ITEMINDEX, str_itemIndex);
//                       // map.put(Constants.HM_ITEMPRICE, str_itemPrice);
//                       // map.put(Constants.HM_VATPERCENT, str_itemVat);
//                       // map.put(Constants.HM_QTY, str_itemQty);
//                      //  map.put(Constants.HM_VATAMT, str_itemVatAmt);
//
//                        cartArrayList.add(map);
//
//
//
//                    } while (cursor.moveToNext());
//
//
//
//
//                    Object[] objNames = listName.toArray();
//
//                    //Second Step: convert Object array to String array
//
//                    String[] strNames = Arrays.copyOf(objNames, objNames.length, String[].class);
//
//                    System.out.println("ArrayList converted to String array");
//
//                    //print elements of String array
//                    for(int i=0; i < strNames.length; i++){
//                        System.out.println(strNames[i]);
//                    }
//
//
//                   allSubjectAPI();
////                    PlaceOrder placeOrder = new PlaceOrder();
////                    placeOrder.execute();
//
//
//                }
//
//                //callfuc();
//callfunc2();
//
//
//            }
//        } catch (Exception e) {
//            System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
//        }
//    }

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

    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {

        //    dbHelper.vatbook.execSQL("delete from " + dbHelper.Subject);
//        startActivity(new Intent(Teststepthree.this, MainActivity.class));
//        finish();



clearapi();












    }

    private void clearapi() {
        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(Teststepthree.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.clearapi, new Response.Listener<String>() {
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



                            dbHelper.CreateTable(5);
                            Cursor c = dbHelper.vatbook.rawQuery("select * from " + dbHelper.questionslist + "  ", null);
                            System.out.println("Add card table count" + c.getCount());
                            if (c.getCount() == 0) {


                                Intent intent = new Intent(Teststepthree.this, TeststepTwo.class);
                                //OrderDetailsActivity

                                intent.putExtra("examid",classid );


                                intent.putExtra("Qcount",subjectid );




                                finish();

                            } else {

                                dbHelper.vatbook.execSQL("delete from " + dbHelper.questionslist);
                                Intent intent = new Intent(Teststepthree.this, TeststepTwo.class);
                                //OrderDetailsActivity

                                intent.putExtra("examid",classid );


                                intent.putExtra("Qcount",subjectid );




                                finish();
                            }






                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(Teststepthree.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(Teststepthree.this, Teststepthree.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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

                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, Teststepthree.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }

    public class TerbaruAdapter extends BaseExpandableListAdapter {
        Context context;
        ArrayList<TerbaruModel> ListTerbaru;
        ArrayList<ArrayList<ChildTerbaru>> ListChildTerbaru;
        int count;

        public TerbaruAdapter (Teststepthree context, ArrayList<TerbaruModel>ListTerbaru, ArrayList<ArrayList<ChildTerbaru>> ListChildTerbaru){
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
        public ChildTerbaru getChild(int groupPosition, int childPosition) {
            return ListChildTerbaru.get(groupPosition).get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            final ChildTerbaru childTerbaru = getChild(groupPosition, childPosition);
            ViewHolder holder= null;


            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.expandablelistview_child, null);

                holder = new ViewHolder();
                holder.begdate1 = (TextView) convertView.findViewById(R.id.chaptres);
                holder.chapter = (CheckBox) convertView.findViewById(R.id.androidCheckBox);

                convertView.setTag(holder);
//
//
//                convertView.setTag(R.id.chaptres, holder.begdate1);
//                convertView.setTag(R.id.androidCheckBox, holder.chapter);


//                holder.chapter.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        boolean checked = ((CheckBox) v).isChecked();
//                        // Check which checkbox was clicked
//                        System.out.println("chck");
//
//
//
//
//
//                        if (checked){
//                            // Do your coding
//                            Chapter = childTerbaru.getchapter();
//                            unit = childTerbaru.getunit();
//
//                            System.out.println("chckyes"+Chapter+unit);
//                            utilis.getSharedPreference();
//                            System.out.println("id"+utilis.strStudentID);
//
//
//                            try {
//                                dbHelper.CreateTable(5);
//                                dbHelper.vatbook.execSQL("INSERT INTO "
//                                        + DBHelper.questionslist
//                                        + " (testname,subject,unit,chapter,userid)"
//                                        + " VALUES ( '" + testname + "', '" + SubjectName + "', '" + unit + "', '" + Chapter + "', '" + utilis.strStudentID + "')");
//
//
//
//
//
//                                System.out.println("INSERT INTO "
//                                        + DBHelper.questionslist
//
//                                        + " (testname,subject,unit,chapter,userid)"
//                                        + " VALUES ( '" + testname + "', '" + SubjectName + "', '" + unit + "', '" + Chapter + "', '" + utilis.strStudentID + "')");
//
//                            } catch (SQLException e) {
//                                System.out.println(TAG + " LocalDB " + e.getMessage());
//                            }
//
//
//
//                        }
//                        else{
//
//                            dbHelper.CreateTable(5);
//                            dbHelper.vatbook.execSQL("delete from " + dbHelper.questionslist + " where subject ='" +SubjectName + "' and unit = '"+unit+"'and chapter = '"+Chapter+"' ");
//                            // Do your coding
//
//                            System.out.println("chckno");
//                        }
//                    }
//                });


            }
            else{
                holder=(ViewHolder)convertView.getTag();
            }



            holder.chapter.setText(childTerbaru.getchapter()+" "+"("+childTerbaru.getcount()+" MCQs"+")");



            String Checkbox=childTerbaru.getisCheckeds();


            System.out.println("checkvalues"+Checkbox);
            System.out.println("checkgetset"+childTerbaru.getIsChecked());


            String ic=childTerbaru.getIsChecked();


            if(ic.equals("")) {
                holder.chapter.setChecked(false);
                notifyDataSetChanged();
            } else if(ic.equals("notChecked")){
                holder.chapter.setChecked(false);
                notifyDataSetChanged();
            }
            else if(ic.equals("No")){
                holder.chapter.setChecked(false);
                notifyDataSetChanged();
            }
            else {
                holder.chapter.setChecked(true);
                    notifyDataSetChanged();
            }






            if(ic.equals("Yes")) {


                holder.chapter.setChecked(true);
                notifyDataSetChanged();
            } else if(Checkbox.equals("Yes")){

                if(ic.equals("notChecked")){
                    System.out.println("setvaluenot");
                    holder.chapter.setChecked(false);
                    notifyDataSetChanged();

                }else {
                    holder.chapter.setChecked(true);
                    notifyDataSetChanged();

                }




                }

             else {
                holder.chapter.setChecked(false);
                notifyDataSetChanged();
            }


//            System.out.println("IC"+ic);
//            if(  childTerbaru.getIsChecked() != null && childTerbaru.getIsChecked().equalsIgnoreCase("Yes")){
//
//
//                holder.chapter.setChecked(true);
//                notifyDataSetChanged();
//
//
//            }else  if(Checkbox.equals("Yes")){
//
//                holder.chapter.setChecked(true);
//                notifyDataSetChanged();
//
////                if(ic.equals("notChecked") && ic == null && ic.equals("")) {
////                    holder.chapter.setChecked(false);
////                    notifyDataSetChanged();
////                }{
////                    holder.chapter.setChecked(true);
////                    notifyDataSetChanged();
////                }
//            }
//            else  if(Checkbox.equals("No")){
//                holder.chapter.setChecked(false);
//                notifyDataSetChanged();
//            }
//
//            else  if(ic.equals("notChecked")){
//                holder.chapter.setChecked(false);
//                notifyDataSetChanged();
//            }
//            else  if(TextUtils.isEmpty(ic)){
//                holder.chapter.setChecked(false);
//                notifyDataSetChanged();
//            }
//            else {
//                holder.chapter.setChecked(false);
//                notifyDataSetChanged();
//            }
//



//            if(childTerbaru.getIsChecked().equals("notChecked")){
//                holder.chapter.setChecked(false);
//                notifyDataSetChanged();
//            }





//            holder.chapter.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//
//
//
//
//
//
//                }
//            });

//            System.out.println("check"+childTerbaru.getIsChecked());
//
//            if (childTerbaru.getIsChecked() != null && childTerbaru.getIsChecked().equalsIgnoreCase("isChecked")) {
//
//                holder.chapter.setChecked(true);
//                notifyDataSetChanged();
//            }else{
//                holder.chapter.setChecked(false);
//                notifyDataSetChanged();
//            }





            holder.chapter.setTag( childTerbaru);


            final ViewHolder finalHolder = holder;
            final ViewHolder finalHolder1 = holder;


            final ViewHolder finalHolder2 = holder;
            holder.chapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = ((CheckBox) v).isChecked();
                    // Check which checkbox was clicked
                    System.out.println("chck");






//
//                        if(modelArrayList.get(pos).getSelected()){
//                            modelArrayList.get(pos).setSelected(false);
//                        }else {
//                            modelArrayList.get(pos).setSelected(true);
//                        }



                    if (checked){

                        String unit=childTerbaru.getunit();
                        String chapter=childTerbaru.getchapter();

                        checkapicacll(unit,chapter,SubjectName);
                        count = 0;
                        ListChildTerbaru.get(groupPosition).get(childPosition).setIsChecked("Yes");
                        notifyDataSetChanged();

                        finalHolder2.chapter.setChecked(true);
                        notifyDataSetChanged();
                        // Do your coding
                        Chapter = childTerbaru.getchapter();
                        unit = childTerbaru.getunit();

                        System.out.println("chckyes"+Chapter+unit);
                        utilis.getSharedPreference();
                        System.out.println("id"+utilis.strStudentID);


                        try {
                            dbHelper.CreateTable(5);
                            dbHelper.vatbook.execSQL("INSERT INTO "
                                    + DBHelper.questionslist
                                    + " (testname,subject,unit,chapter,userid)"
                                    + " VALUES ( '" + testname + "', '" + SubjectName + "', '" + unit + "', '" + Chapter + "', '" + utilis.strStudentID + "')");





                            System.out.println("INSERT INTO "
                                    + DBHelper.questionslist

                                    + " (testname,subject,unit,chapter,userid)"
                                    + " VALUES ( '" + testname + "', '" + SubjectName + "', '" + unit + "', '" + Chapter + "', '" + utilis.strStudentID + "')");

                        } catch (SQLException e) {
                            System.out.println(TAG + " LocalDB " + e.getMessage());
                        }








                    }
                    else{

//                        ListChildTerbaru.get(groupPosition).get(childPosition).setIsChecked("No");
//                        notifyDataSetChanged();


                        String unit=childTerbaru.getunit();
                        String chapter=childTerbaru.getchapter();

                        checkapicacll(unit,chapter,SubjectName);
                        count = 0;
                        ListChildTerbaru.get(groupPosition).get(childPosition).setIsChecked("notChecked");
                        notifyDataSetChanged();
                        finalHolder2.chapter.setChecked(false);
                        Chapter = childTerbaru.getchapter();
                        unit = childTerbaru.getunit();
                        dbHelper.CreateTable(5);
                        System.out.println("deletedsubj"+"delete from " + dbHelper.questionslist + " where subject ='" +SubjectName + "' and unit = '"+unit+"'and chapter = '"+Chapter+"' ");
                        dbHelper.vatbook.execSQL("delete from " + dbHelper.questionslist + " where subject ='" +SubjectName + "' and unit = '"+unit+"'and chapter = '"+Chapter+"' ");
                        // Do your coding

                        System.out.println("chckno");



                    }
                }
            });







//            cb.setCheck(groupList.get(groupPosition).isChecked());
//
//
//
//            holder.cb.setText(childTerbaru.getchapter());
//            holder.chapter.setTag(childTerbaru); // This line is important.
//
//            // viewHolder.text.setText(list.get(position).getName());
//            holder.chapter.setChecked(childTerbaru.isSelected());



            return convertView;
        }
        @Override
        public int getChildrenCount(int groupPosition) {
            return ListChildTerbaru.get(groupPosition).size();
        }

        @Override
        public TerbaruModel getGroup(int groupPosition) {
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

            final  TerbaruModel terbaruModel = (TerbaruModel) getGroup(groupPosition);
            ViewHolder holder= null;
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.expandablelistview_group, null);

                holder=new ViewHolder();
                holder.nama=(TextView)convertView.findViewById(R.id.unit);

                convertView.setTag(holder);

            }

            else{
                holder=(ViewHolder)convertView.getTag();
            }

            holder.nama.setText(terbaruModel.getunit());
//            holder.nama.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String items =terbaruModel.getunit();
//                    System.out.println("ite"+items);
//
//                }
//            });

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

//        @Override
//        public void onClick(View v) {
//            switch(v.getId())
//            {
//                case R.id.androidCheckBox:
//                    int pos = (Integer) v.findViewById(R.id.androidCheckBox).getTag();
//                   // ListChildTerbaru.get(pos).setSelected((CheckBox)v.isChecked());
//
//                    break;
//            }
//        }


        public class ViewHolder{
            TextView begdate1, enddate1,nama, alamat, imageid;
            CheckBox chapter;
        }

    }

    private void checkapicacll(final String unit, final String chapter, final String subjectName) {




        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(Teststepthree.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.checkchapter, new Response.Listener<String>() {
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



                            //  allsubject();




                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(Teststepthree.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(Teststepthree.this, Teststepthree.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
                    params.put("Subject",subjectName );
                    params.put("Unit",unit );
                    params.put("Chapter",chapter );





                    System.out.println(" inputs_submitnew " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, Teststepthree.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }







    }


    private void allSubjectAPI() {











        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(Teststepthree.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.chapter, new Response.Listener<String>() {
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

                                ListChildTerbaru = new ArrayList<ChildTerbaru>();

                                LT = new TerbaruModel(jsonObject.optString("unitName"));
                                ListTerbaru.add(LT);
                                Log.d("tag", "jsonObj Menu_Cat_Id->" + jsonObject.optString("unitName"));

                                JSONArray jsonArrSubCat = jsonObject.getJSONArray("chapterName");
                                if (jsonArrSubCat != null && jsonArrSubCat.length() > 0) {

                                    for (int subCnt = 0; subCnt < jsonArrSubCat.length(); subCnt++) {
                                        JSONObject jsonObjSubCategory = jsonArrSubCat.getJSONObject(subCnt);

                                        CT = new ChildTerbaru(jsonObjSubCategory.optString("chapterName"),jsonObjSubCategory.optString("unitName"),jsonObjSubCategory.optString("isChecked"),jsonObjSubCategory.optString("count"));
                                        ListChildTerbaru.add(CT);

                                        Log.d("tag", "jsonArrSubCat Item_NameEN->" + jsonObjSubCategory.optString("chapterName")+jsonObjSubCategory.optString("unitName"));
                                    }
                                }





                                ListChildXXXXXXXXX.add(ListChildTerbaru);


                                adapter = new TerbaruAdapter(Teststepthree.this,ListTerbaru, ListChildXXXXXXXXX);
                                history.setAdapter(adapter);

                                adapter.notifyDataSetChanged();
                                adapter.notifyDataSetInvalidated();



// refresh the View



                            }




                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(Teststepthree.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(Teststepthree.this, Teststepthree.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
                    params.put("subjectName",SubjectName );
                    System.out.println(TAG + " loginAPI inputs " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, Teststepthree.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }


//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            history.setIndicatorBounds(history.getWidth()-40,history.getWidth());
//        } else {
//            history.setIndicatorBoundsRelative(history.getWidth()-40,history.getWidth());
//        }
//    }

}


