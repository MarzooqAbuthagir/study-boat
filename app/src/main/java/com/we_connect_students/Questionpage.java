package com.we_connect_students;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Questionpage extends AppCompatActivity {
    String TAG = "Question",ID="";
    DBHelper dbHelper;
    Utilis utilis;

    Toolbar toolbar;
    ActionBar actionBar = null;

    RecyclerView recyclerView;

    String str_result = "", str_message = "",text="";

    Spinner spin;
    Button submit;
    ArrayList<QustineList> list = new ArrayList<QustineList>();

    TextView _tv;
   public static String  mints ="1";
    private static CountDownTimer countDownTimer;
    Button stop;
    public static String  Qusno = "",
    answer = "",wrong="",result="",wresult="";
    Cursor cursor;

    int answercount,wanser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_test);
        dbHelper = new DBHelper(Questionpage.this);
        utilis = new Utilis(Questionpage.this);
        getValues();

        dbHelper = new DBHelper(Questionpage.this);
        recyclerView = findViewById(R.id.recyclerView);


        list = new ArrayList<>();


        allSubjectAPI();


        _tv = (TextView) findViewById(R.id.time);
        stop=findViewById(R.id.stop);







stop.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


String Anwercount="1";



            try {


                dbHelper.CreateTable(3);
                cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.question + " where Acount='"+Anwercount+"'", null);
                System.out.println(TAG + " getValueFromCart count " + cursor.getCount());
                answercount=cursor.getCount();

                if (cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {

                         Qusno = "";
                        answer = "";
                        wrong="";
//

                        do {
                            Qusno = cursor.getString(cursor.getColumnIndex("questionno"));
                            answer = cursor.getString(cursor.getColumnIndex("correctanswer"));
                       // wrong = cursor.getString(cursor.getColumnIndex("wronganswer"));
//                        str_itemIndex = cursor.getString(cursor.getColumnIndex("itemIndex"));
//                        str_itemQty = cursor.getString(cursor.getColumnIndex("itemQty"));
//                        str_itemVat = cursor.getString(cursor.getColumnIndex("itemVat"));
//                        str_itemPrice = cursor.getString(cursor.getColumnIndex("itemPrice"));
//                        str_itemVatAmt = cursor.getString(cursor.getColumnIndex("itemVatAmt"));
                            System.out.println(TAG + " From Db " + answer+""+Qusno+""+wrong);








                        } while (cursor.moveToNext());










                    }




                }
            } catch (Exception e) {
                System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
            }



        String Anwercounts="0";

        try {


            dbHelper.CreateTable(3);
            cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.question + " where Acount='"+Anwercounts+"'", null);
            System.out.println(TAG + " getValueFromCart count " + cursor.getCount());
            wanser=cursor.getCount();

            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {

                    Qusno = "";
                    wrong="";
//

                    do {
                        Qusno = cursor.getString(cursor.getColumnIndex("questionno"));
                        // wrong = cursor.getString(cursor.getColumnIndex("wronganswer"));









                    } while (cursor.moveToNext());










                }




            }
        } catch (Exception e) {
            System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
        }






            result= Integer.toString(answercount);
         wresult=Integer.toString(wanser);;

        sentapi(result,wresult,ID);











//        stopCountdown();//stop count down
//        _tv.setText(getString(R.string.timer));//Change Timer text
    }
});


    }

    private void sentapi(final String result, final String wresult, final String id) {




        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(Questionpage.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.submit, new Response.Listener<String>() {
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
                            dbHelper.vatbook.execSQL("delete from " + dbHelper.question);
                            Intent intent = new Intent(Questionpage.this, Resultpage.class); //OrderDetailsActivity
                            intent.putExtra("answer", result);


                            intent.putExtra("wronganswer", wresult);


                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);



//timercall();
                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(Questionpage.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(Questionpage.this, Questionpage.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
                    params.put("lastIndexId",ID );
                    params.put("correctAnswer",result );
                    params.put("wrongAnswer",wresult );

                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, Questionpage.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }






    }


    //Stop Countdown method
    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    //Start Countodwn method
    private void startTimer(int noOfMinutes) {
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                _tv.setText(hms);//set text
            }

            public void onFinish() {

                _tv.setText("TIME'S UP!!"); //On finish change timer text
                countDownTimer = null;//set CountDownTimer to null
               // startTimer.setText(getString(R.string.start_timer));//Change button text
            }
        }.start();

    }






    private void allSubjectAPI() {
        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(Questionpage.this);

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

                            str_message = obj.getString("Message");

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



                                QustineList values = new QustineList(jsonObject.getString("question"), jsonObject.getString("questionNumber"),
                                        jsonObject.getString("Option1"),jsonObject.getString("Option2"),jsonObject.getString("Option3"),jsonObject.getString("Option4"),jsonObject.getString("answer"));

                                list.add(values);

                            }

                            QuestionAdapter questionAdapter = new QuestionAdapter(Questionpage.this, list);
                            recyclerView.setAdapter(questionAdapter);

//timercall();
                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(Questionpage.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(Questionpage.this, Questionpage.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
                    params.put("lastIndexId",ID );
                    params.put("studentIndexId",utilis.strStudentID );
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, Questionpage.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }

    private void timercall() {


        if (countDownTimer == null) {
            String getMinutes = mints;//Get minutes from edittexf
            //Check validation over edittext
            if (!getMinutes.equals("") && getMinutes.length() > 0) {
                int noOfMinutes = Integer.parseInt(getMinutes) * 60 * 1000;//Convert minutes into milliseconds

                startTimer(noOfMinutes);//start countdown
                //  startTimer.setText(getString(R.string.stop_timer));//Change Text

            } else
                Toast.makeText(Questionpage.this, "Please enter no. of Minutes.", Toast.LENGTH_SHORT).show();//Display toast if edittext is empty
        } else {
            //Else stop timer and change text
            stopCountdown();
            // startTimer.setText(getString(R.string.start_timer));
        }

    }


    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        startActivity(new Intent(Questionpage.this, MainActivity.class));
        finish();
    }
    private void getValues() {
        Bundle b = getIntent().getExtras();
        if (b != null) {






            ID = b.getString("classid");





            System.out.println(TAG + " getValues " + b);





        }
    }




    class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
        Context con;
        private ArrayList<QustineList> arrayList;

        public QuestionAdapter(Context con, ArrayList<QustineList> list) {
            this.con = con;
            this.arrayList = list;
        }

        @NonNull
        @Override
        public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.qustioncontent, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final QuestionAdapter.ViewHolder viewHolder, final int i) {
            viewHolder.question.setText(arrayList.get(i).getQno()+"."+arrayList.get(i).getquestion());
            viewHolder.radia_id1.setText(arrayList.get(i).getoption1());
            viewHolder.radia_id2.setText(arrayList.get(i).getoption2());
            viewHolder.radia_id3.setText(arrayList.get(i).getoption3());
            viewHolder.radia_id4.setText(arrayList.get(i).getoption4());


            viewHolder.radia_id1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){

                       String Questionno=arrayList.get(i).Qno;
                        String check=arrayList.get(i).Answer;
                        String text=arrayList.get(i).getoption1();
                        System.out.println("ansercheck"+arrayList.get(i).getoption1());

                        if(text.equals(check)){


                            String co="correct";
                            String Acoun="1";
                            dbHelper.CreateTable(3);
                            Cursor c = dbHelper.vatbook.rawQuery("select * from " + dbHelper.question + " where questionno ='" + Questionno + "' ", null);
                            System.out.println("Add card table count" + c.getCount());
                            if (c.getCount() == 0) {
                                dbHelper.CreateTable(3);
                                dbHelper.vatbook.execSQL("INSERT INTO "
                                        + dbHelper.question
                                        + "(questionno, correctanswer,Acount )"
                                        + " VALUES ('" + Questionno + "', '" + text + "', '" + Acoun + "')");

                            } else {
                                dbHelper.CreateTable(3);
                                dbHelper.vatbook.execSQL("update " + dbHelper.question + " set correctanswer = '" + text + "' where questionno = '" + Questionno + "'  ");

                            }





                            System.out.println("correctanswer");
                        }
                        else{

                            String was="wronganswer";

                            String Acoun="0";

                            dbHelper.CreateTable(3);
                            Cursor c = dbHelper.vatbook.rawQuery("select * from " + dbHelper.question + " where questionno ='" + Questionno + "' ", null);
                            System.out.println("Add card table count" + c.getCount());
                            if (c.getCount() == 0) {
                                dbHelper.CreateTable(3);
                                dbHelper.vatbook.execSQL("INSERT INTO "
                                        + dbHelper.question
                                        + "(questionno, wronganswer,Acount )"
                                + " VALUES ('" + Questionno + "', '" + text + "', '" + Acoun + "')");

                            } else {
                                dbHelper.CreateTable(3);
                                dbHelper.vatbook.execSQL("update " + dbHelper.question + " set wronganswer = '" + text + "' where questionno = '" + Questionno + "'  ");

                            }




                            System.out.println("notcorrectanswer");
                        }



                       // System.out.println("checkedis"+check);

                    }else {
                        System.out.println("not");
                    }
                }
            });



            viewHolder.radia_id2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        System.out.println("checkedis");
                        String Questionno=arrayList.get(i).Qno;
                        String check=arrayList.get(i).Answer;
                        String text=arrayList.get(i).getoption2();
                        System.out.println("ansercheck"+arrayList.get(i).getoption2());




                        if(text.equals(check)){


                            String co="correct";
                            String Acoun="1";
                            dbHelper.CreateTable(3);
                            Cursor c = dbHelper.vatbook.rawQuery("select * from " + dbHelper.question + " where questionno ='" + Questionno + "' ", null);
                            System.out.println("Add card table count" + c.getCount());
                            if (c.getCount() == 0) {
                                dbHelper.CreateTable(3);
                                dbHelper.vatbook.execSQL("INSERT INTO "
                                        + dbHelper.question
                                        + "(questionno, correctanswer,Acount )"
                                        + " VALUES ('" + Questionno + "', '" + text + "', '" + Acoun + "')");

                            } else {
                                dbHelper.CreateTable(3);
                                dbHelper.vatbook.execSQL("update " + dbHelper.question + " set correctanswer = '" + text + "' where questionno = '" + Questionno + "'  ");

                            }





                            System.out.println("correctanswer");
                        }
                        else{

                            String was="wronganswer";

                            String Acoun="0";

                            dbHelper.CreateTable(3);
                            Cursor c = dbHelper.vatbook.rawQuery("select * from " + dbHelper.question + " where questionno ='" + Questionno + "' ", null);
                            System.out.println("Add card table count" + c.getCount());
                            if (c.getCount() == 0) {
                                dbHelper.CreateTable(3);
                                dbHelper.vatbook.execSQL("INSERT INTO "
                                        + dbHelper.question
                                        + "(questionno, wronganswer,Acount )"
                                        + " VALUES ('" + Questionno + "', '" + text + "', '" + Acoun + "')");

                            } else {
                                dbHelper.CreateTable(3);
                                dbHelper.vatbook.execSQL("update " + dbHelper.question + " set wronganswer = '" + text + "' where questionno = '" + Questionno + "'  ");

                            }




                            System.out.println("notcorrectanswer");
                        }


                    }else {
                        System.out.println("not");
                    }
                }
            });

            viewHolder.radia_id3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        String Questionno=arrayList.get(i).Qno;
                        String check=arrayList.get(i).Answer;
                        String text=arrayList.get(i).getoption3();
                        System.out.println("ansercheck"+arrayList.get(i).getoption3());

                        if(text.equals(check)){


                            String co="correct";
                            String Acoun="1";
                            dbHelper.CreateTable(3);
                            Cursor c = dbHelper.vatbook.rawQuery("select * from " + dbHelper.question + " where questionno ='" + Questionno + "' ", null);
                            System.out.println("Add card table count" + c.getCount());
                            if (c.getCount() == 0) {
                                dbHelper.CreateTable(3);
                                dbHelper.vatbook.execSQL("INSERT INTO "
                                        + dbHelper.question
                                        + "(questionno, correctanswer,Acount )"
                                        + " VALUES ('" + Questionno + "', '" + text + "', '" + Acoun + "')");

                            } else {
                                dbHelper.CreateTable(3);
                                dbHelper.vatbook.execSQL("update " + dbHelper.question + " set correctanswer = '" + text + "' where questionno = '" + Questionno + "'  ");

                            }





                            System.out.println("correctanswer");
                        }
                        else{

                            String was="wronganswer";


                            String Acoun="0";
                            dbHelper.CreateTable(3);
                            Cursor c = dbHelper.vatbook.rawQuery("select * from " + dbHelper.question + " where questionno ='" + Questionno + "' ", null);
                            System.out.println("Add card table count" + c.getCount());
                            if (c.getCount() == 0) {
                                dbHelper.CreateTable(3);
                                dbHelper.vatbook.execSQL("INSERT INTO "
                                        + dbHelper.question
                                        + "(questionno, wronganswer,Acount )"
                                        + " VALUES ('" + Questionno + "', '" + text + "', '" + Acoun + "')");

                            } else {
                                dbHelper.CreateTable(3);
                                dbHelper.vatbook.execSQL("update " + dbHelper.question + " set wronganswer = '" + text + "' where questionno = '" + Questionno + "'  ");

                            }




                            System.out.println("notcorrectanswer");
                        }

                        System.out.println("checkedis");

                    }else {
                        System.out.println("not");
                    }
                }
            });

            viewHolder.radia_id4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        System.out.println("checkedis");
                        String check=arrayList.get(i).Answer;
                        String text=arrayList.get(i).getoption4();
                        System.out.println("ansercheck"+arrayList.get(i).getoption4());
                        String Questionno=arrayList.get(i).Qno;
                        if(text.equals(check)){


                            String co="correct";
                            String Acoun="1";
                            dbHelper.CreateTable(3);
                            Cursor c = dbHelper.vatbook.rawQuery("select * from " + dbHelper.question + " where questionno ='" + Questionno + "' ", null);
                            System.out.println("Add card table count" + c.getCount());
                            if (c.getCount() == 0) {
                                dbHelper.CreateTable(3);
                                dbHelper.vatbook.execSQL("INSERT INTO "
                                        + dbHelper.question
                                        + "(questionno, correctanswer,Acount )"
                                        + " VALUES ('" + Questionno + "', '" + text + "', '" + Acoun + "')");

                            } else {
                                dbHelper.CreateTable(3);
                                dbHelper.vatbook.execSQL("update " + dbHelper.question + " set correctanswer = '" + text + "' where questionno = '" + Questionno + "'  ");

                            }





                            System.out.println("correctanswer");
                        }
                        else{

                            String was="wronganswer";

                            String Acoun="0";

                            dbHelper.CreateTable(3);
                            Cursor c = dbHelper.vatbook.rawQuery("select * from " + dbHelper.question + " where questionno ='" + Questionno + "' ", null);
                            System.out.println("Add card table count" + c.getCount());
                            if (c.getCount() == 0) {
                                dbHelper.CreateTable(3);
                                dbHelper.vatbook.execSQL("INSERT INTO "
                                        + dbHelper.question
                                        + "(questionno, wronganswer,Acount )"
                                        + " VALUES ('" + Questionno + "', '" + text + "', '" + Acoun + "')");

                            } else {
                                dbHelper.CreateTable(3);
                                dbHelper.vatbook.execSQL("update " + dbHelper.question + " set wronganswer = '" + text + "' where questionno = '" + Questionno + "'  ");

                            }




                            System.out.println("notcorrectanswer");
                        }
                    }else {
                        System.out.println("not");
                    }
                }
            });





        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView  question;
RadioButton radia_id1,radia_id2,radia_id3,radia_id4;

            public ViewHolder(View view) {
                super(view);

                question = view.findViewById(R.id.question);
radia_id1=view.findViewById(R.id.radia_id1);
                radia_id2=view.findViewById(R.id.radia_id2);
                radia_id3=view.findViewById(R.id.radia_id3);
                radia_id4=view.findViewById(R.id.radia_id4);
                view.setTag(itemView);
            }
        }
    }

}


