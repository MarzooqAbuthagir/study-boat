package com.we_connect_students;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.we_connect_students.DataBase.DBHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import katex.hourglass.in.mathlib.MathView;

public class singlequstionevent extends AppCompatActivity {


    public static ArrayList<HashMap<String, String>> cartArrayList = null;
    public static ArrayList<HashMap<String, String>> cartArrayLists = null;
    int answercount =0,wrongcount=0,lefts=0;
    HashMap<String, String> map;
    public static   String TAG = "Question",ID="",IDs="", timer="",time="",isCorrect="NO",isWrong="NO",isSkip="NO",isAll="YES", testName="";

    DBHelper dbHelper;
    Utilis utilis;

    Toolbar toolbar;
    ActionBar actionBar = null;

    RecyclerView recyclerView;

    String str_result = "", str_message = "",text="";

    Spinner spin;
    Button submit;
    ArrayList<QustineList> list = new ArrayList<QustineList>();

    public static TextView _tv,Qno,Single;
    public static String  mints ="";
    private static CountDownTimer countDownTimer;
    Button stop;
    public static String  Qusno = "",
            answer = "",wrong="",result="",wrongs="",Qust="",
            Qus="",
            opt1="",
            opt2="",
            opt3="",
            opt4="",explain="",
            subjectName="",
            sectionid="",
            chapterName="";

    Cursor cursor;

    TextView txtQuestion,sub,chap;
    RadioButton rda,rdb,rdc,rde;
    Button butNext,butprev,btsubmit;
    int score = 0;
    int quid = 1;
    int wr;
    String qu="1";
    String Answer="",Wrong="",LEFTANSWER="",Explain="";
    String SCore="";

    private static TextView countdownTimerText;

    //    CheckBox ch1,ch2,ch3,ch4;
    private RadioGroup radioGroup;
    RadioButton radio1,radio2,radio3,radio4;


    public Dialog dialog;
    public static   Chronometer crom;
    int minteger = 0;


    public static  String  timern="",input31="";




    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;


    String i="1";
    public static     MathView radia_id1f1,radia_id1f2,radia_id1f3,radia_id1f4,questionm,questionm2,questionm3,questionm4,questionm5;
    LinearLayout op4,op3,op2,op1;


    public static   ImageView radio1image,radio2image,radio3image,radio4image,qimage4,qimage3,qimage2,qimage;


    String butttontext="";


    ArrayList<String> Correctqus;
    ArrayList<String> Wrongqus;
    ArrayList<String> Skipequs;
    ArrayList<String> Allqus;
    ArrayList<String> iscorrect;
    Object[] objArray,objArray2,objArray3,objArray4,objArray5;
    String newString,newString2,Subject,newString3,newString4,newString5;
    String CorrectQusno="", Wrongquestiono="", Skipqno="",Allqusno="", Iscorrect="", LASTID="";
    String wrongQuestions="",right_questions="",skippedQuestions="",allquestiono="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlequstionevent);


        dbHelper = new DBHelper(singlequstionevent.this);
        utilis = new Utilis(singlequstionevent.this);
        Window window = singlequstionevent.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(singlequstionevent.this,R.color.my_statusbar_color1));




        getValues();
        initToolbar();
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(testName);

        txtQuestion = (TextView)findViewById(R.id.question);
        questionm=findViewById(R.id.questionm);
        questionm2=findViewById(R.id.questionm2);
        questionm3=findViewById(R.id.questionm3);
        questionm4=findViewById(R.id.question4);
        questionm5=findViewById(R.id.question5);
        //questionm=findViewById(R.id.questionm);

        qimage=findViewById(R.id.qimage);
        qimage2=findViewById(R.id.qimage2);
        qimage3=findViewById(R.id.qimage3);
        qimage4=findViewById(R.id.qimage4);


        sub = (TextView)findViewById(R.id.sub);
        chap = (TextView)findViewById(R.id.chap);

        Qno = (TextView)findViewById(R.id.qno);
        Single = (TextView)findViewById(R.id.single);

        countdownTimerText = (TextView) findViewById(R.id.countdownTexts);




        butNext = (Button)findViewById(R.id.button1);
        butprev= (Button)findViewById(R.id.button2);
        btsubmit= (Button)findViewById(R.id.button3);
        radio1=findViewById(R.id.radia_id1);

        radio2=findViewById(R.id.radia_id2);
        radio3=findViewById(R.id.radia_id3);
        radio4=findViewById(R.id.radia_id4);

        radia_id1f1=findViewById(R.id.radia_id1f1);

        radia_id1f2=findViewById(R.id.radia_id1f2);
        radia_id1f3=findViewById(R.id.radia_id1f3);
        radia_id1f4=findViewById(R.id.radia_id1f4);

        op1=findViewById(R.id.op1);
        op2=findViewById(R.id.op2);
        op3=findViewById(R.id.op3);
        op4=findViewById(R.id.op4);


        op1.setBackground(getResources().getDrawable(R.drawable.ash,null));
        op2.setBackground(getResources().getDrawable(R.drawable.ash,null));
        op3.setBackground(getResources().getDrawable(R.drawable.ash,null));
        op4.setBackground(getResources().getDrawable(R.drawable.ash,null));


        op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                radio1.setChecked(true);

                radio2.setChecked(false);
                radio3.setChecked(false);
                radio4.setChecked(false);

                String Qno=Qusno;
                String selected=opt1;


                op1.setBackground(getResources().getDrawable(R.drawable.yourbuttonbackgrounds,null));
                op2.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op3.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op4.setBackground(getResources().getDrawable(R.drawable.ash,null));

                dbHelper.CreateTable(4);
                String sectionid="1";

                dbHelper.vatbook.execSQL("update " + dbHelper.questionresponse + " set sectionid ='" + sectionid + "'where Questionno='" + Qno + "'");

                System.out.println("sectionidsout"+ dbHelper.questionresponse + " set sectionid ='" + sectionid + "'where Questionno='" + Qno + "'");
                System.out.println("selected"+selected);

                System.out.println("selected"+answer);
                if(selected.equals(answer)) {

                    System.out.println("Radiocheckcorrect1");


                    String co = "correct";
                    String Acoun = "1";
                    String IsCorrect="YES";
                    dbHelper.CreateTable(6);
                    dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set correctanser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "' , isCorrect = '" + IsCorrect + "'where Questionno='" + Qno + "'");
                    System.out.println("correct"+selected);
                    System.out.println("update " + dbHelper.selctedlist + " set correctanser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "' where Questionno='" + Qno + "'");
                } else{
                    System.out.println("Radiocheckwrong1");
                    String was="wronganswer";

                    String Acoun="0";
                    String IsCorrect="NO";

                    dbHelper.CreateTable(6);
                    dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set wronganser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "' , isCorrect = '" + IsCorrect + "' where Questionno='" + Qno + "'");
                    System.out.println("wronganswer"+selected);
                    System.out.println("update " + dbHelper.selctedlist + " set wronganser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "' where Questionno='" + Qno + "'");
                }

//                if(radio1.isChecked())
//                {
//
//
//
//
//                }






//                radio1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                        if(radio1.isChecked())
//                        {
//                            String Qno=Qusno;
//                            String selected=radio1.getText().toString();
//                            if(selected.equals(answer)) {
//
//                                System.out.println("Radiocheckcorrect1");
//
//
//                                String co = "correct";
//                                String Acoun = "1";
//                                String IsCorrect="YES";
//                                dbHelper.CreateTable(6);
//                                dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set correctanser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "' , isCorrect = '" + IsCorrect + "'where Questionno='" + Qno + "'");
//                                System.out.println("correct"+selected);
//                                System.out.println("update " + dbHelper.selctedlist + " set correctanser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "' where Questionno='" + Qno + "'");
//                            } else{
//                                System.out.println("Radiocheckwrong1");
//                                String was="wronganswer";
//
//                                String Acoun="0";
//                                String IsCorrect="NO";
//
//                                dbHelper.CreateTable(6);
//                                dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set wronganser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "' , isCorrect = '" + IsCorrect + "' where Questionno='" + Qno + "'");
//                                System.out.println("wronganswer"+selected);
//                                System.out.println("update " + dbHelper.selctedlist + " set wronganser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "' where Questionno='" + Qno + "'");
//                            }
//
//
//
//                        }
//
//
//
//                    }
//                });
            }
        });

        op2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                radio1.setChecked(false);

                radio2.setChecked(true);
                radio3.setChecked(false);
                radio4.setChecked(false);
                op2.setBackground(getResources().getDrawable(R.drawable.yourbuttonbackgrounds,null));
                op1.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op3.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op4.setBackground(getResources().getDrawable(R.drawable.ash,null));


                String Qno=Qusno;
                String selected=opt2;


                dbHelper.CreateTable(4);


                String sectionid="2";

                dbHelper.vatbook.execSQL("update " + dbHelper.questionresponse + " set sectionid ='" + sectionid + "'where Questionno='" + Qno + "'");

                System.out.println("sectionidsout"+ dbHelper.questionresponse + " set sectionid ='" + sectionid + "'where Questionno='" + Qno + "'");

                System.out.println("selected"+selected);

                System.out.println("selected"+answer);


                if(selected.equals(answer)) {

                    System.out.println("Radiocheckcorrect2");


                    String co = "correct";
                    String Acoun = "1";
                    String IsCorrect="YES";
                    dbHelper.CreateTable(6);
                    dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set correctanser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "', isCorrect = '" + IsCorrect + "' where Questionno='" + Qno + "'");
                    System.out.println("correct"+selected);
                    System.out.println("update " + dbHelper.selctedlist + " set correctanser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "', isCorrect = '" + IsCorrect +"' where Questionno='" + Qno + "'");

                } else{
                    System.out.println("Radiocheckwrong2");
                    String was="wronganswer";

                    String Acoun="0";
                    String IsCorrect="NO";
                    dbHelper.CreateTable(6);
                    dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set wronganser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "' , isCorrect = '" + IsCorrect + "'where Questionno='" + Qno + "'");
                    System.out.println("wronganswer"+selected);
                    System.out.println("update " + dbHelper.selctedlist + " set wronganser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "' where Questionno='" + Qno + "'");

                }


            }
        });
        op3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                radio1.setChecked(false);

                radio2.setChecked(false);
                radio3.setChecked(true);
                radio4.setChecked(false);

                op3.setBackground(getResources().getDrawable(R.drawable.yourbuttonbackgrounds,null));
                op2.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op1.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op4.setBackground(getResources().getDrawable(R.drawable.ash,null));
                String Qno=Qusno;
                String selected=opt3;



                dbHelper.CreateTable(4);

                String sectionid="3";

                dbHelper.vatbook.execSQL("update " + dbHelper.questionresponse + " set sectionid ='" + sectionid + "'where Questionno='" + Qno + "'");

                System.out.println("sectionidsout"+ dbHelper.questionresponse + " set sectionid ='" + sectionid + "'where Questionno='" + Qno + "'");

                System.out.println("selected"+selected);

                System.out.println("selected"+answer);


                if(selected.equals(answer)) {

                    System.out.println("Radiocheckcorrect3");


                    String co = "correct";
                    String Acoun = "1";
                    String IsCorrect="YES";
                    dbHelper.CreateTable(6);
                    dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set correctanser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "', isCorrect = '" + IsCorrect + "' where Questionno='" + Qno + "'");
                    System.out.println("correct"+selected);
                    System.out.println("update " + dbHelper.selctedlist + " set correctanser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "', isCorrect = '" + IsCorrect + "' where Questionno='" + Qno + "'");

                } else{
                    System.out.println("Radiocheckwrong3");
                    String was="wronganswer";

                    String Acoun="0";
                    String IsCorrect="NO";
                    dbHelper.CreateTable(6);
                    dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set wronganser ='" + selected + "', selectedanser = '" +selected+ "', Acount = '" + Acoun + "' , isCorrect = '" + IsCorrect + "'where Questionno='" + Qno + "'");
                    System.out.println("wronganswer"+selected);
                    System.out.println("update " + dbHelper.selctedlist + " set wronganser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "' where Questionno='" + Qno + "'");

                }
            }
        });
        op4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                radio1.setChecked(false);

                radio2.setChecked(false);
                radio3.setChecked(false);
                radio4.setChecked(true);
                op4.setBackground(getResources().getDrawable(R.drawable.yourbuttonbackgrounds,null));
                op2.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op3.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op1.setBackground(getResources().getDrawable(R.drawable.ash,null));
                String Qno=Qusno;
                String selected=opt4;
                dbHelper.CreateTable(4);

                String sectionid="4";

                dbHelper.vatbook.execSQL("update " + dbHelper.questionresponse + " set sectionid ='" + sectionid + "'where Questionno='" + Qno + "'");
                System.out.println("sectionidsout"+ dbHelper.questionresponse + " set sectionid ='" + sectionid + "'where Questionno='" + Qno + "'");


                System.out.println("selected"+selected);

                System.out.println("selected"+answer);


                if(selected.equals(answer)) {

                    System.out.println("Radiocheckcorrect4");


                    String co = "correct";
                    String Acoun = "1";
                    String IsCorrect="YES";
                    dbHelper.CreateTable(6);
                    dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set correctanser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "', isCorrect = '" + IsCorrect + "' where Questionno='" + Qno + "'");
                    System.out.println("correct"+selected);
                    System.out.println("update " + dbHelper.selctedlist + " set correctanser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "' where Questionno='" + Qno + "'");

                } else{
                    System.out.println("Radiocheckwrong4");
                    String was="wronganswer";

                    String Acoun="0";
                    String IsCorrect="NO";
                    dbHelper.CreateTable(6);
                    dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set wronganser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "', isCorrect = '" + IsCorrect + "' where Questionno='" + Qno + "'");
                    System.out.println("wronganswer"+selected);
                    System.out.println("update " + dbHelper.selctedlist + " set wronganser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "' where Questionno='" + Qno + "'");

                }

            }
        });









        tableExists(dbHelper.vatbook,dbHelper.questionresponse);
        views();
        String questionid=String.valueOf(quid);
        setQuestionView(questionid);




        System.out.println("timenew"+time);
        long millisInput = Long.parseLong(time) * 60000;

        setTime(millisInput);

        startTimer();

        crom=findViewById(R.id.chrome);

        crom.start();


//        if (countDownTimer == null) {
//            System.out.println("TIME"+time);
//            String getMinutes = time;//Get minutes from edittexf
//            //Check validation over edittext
//            if (!getMinutes.equals("") && getMinutes.length() > 0) {
//                int noOfMinutes = Integer.parseInt(getMinutes) * 60 * 1000;//Convert minutes into milliseconds
//
//                startTimer(noOfMinutes);
//                System.out.println("nomints"+noOfMinutes);//start countdown
//                countdownTimerText.setText(getString(R.string.stop_timer));//Change Text
//
//            } else
//                Toast.makeText(Singlequestion.this, "Please enter no. of Minutes.", Toast.LENGTH_SHORT).show();//Display toast if edittext is empty
//        } else {
//            //Else stop timer and change text
//            stopCountdown();
//            countdownTimerText.setText(getString(R.string.start_timer));
//        }




        setlist();
        // allSubjectAPI();





//        if (countDownTimer == null) {
//
//            System.out.println("timer"+ID);
//            String getMinutes = "30";//Get minutes from edittexf
//            //Check validation over edittext
//            if (!getMinutes.equals("") && getMinutes.length() > 0) {
//                int noOfMinutes = Integer.parseInt(getMinutes) * 60 * 1000;//Convert minutes into milliseconds
//
//                startTimer(noOfMinutes);
//                System.out.println("start"+noOfMinutes);//start countdown
//                //startTimer.setText(getString(R.string.stop_timer));//Change Text
//
//            } else
//                Toast.makeText(Singlequestion.this, "Please enter no. of Minutes.", Toast.LENGTH_SHORT).show();//Display toast if edittext is empty
//        } else {
//            //Else stop timer and change text
//            stopCountdown();
//            // startTimer.setText(getString(R.string.start_timer));
//        }


















        radioGroup.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() {
                    @Override

                    // The flow will come here when
                    // any of the radio buttons in the radioGroup
                    // has been clicked

                    // Check which radio button has been clicked
                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId)
                    {

                        // Get the selected Radio Button
                        RadioButton
                                radioButton
                                = (RadioButton)group
                                .findViewById(checkedId);
                    }
                });






        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                butttontext="Next";


                radio1.setChecked(false);

                radio2.setChecked(false);
                radio3.setChecked(false);
                radio4.setChecked(false);

                int result = Integer.parseInt(ID);

                if(quid<result){
                    quid++;
                    String questionid=String.valueOf(quid);
                    System.out.println("check"+questionid);

                    setQuestionView(questionid);
                    setlist();
                    views();
                    System.out.println("icremtrnt"+quid);

                    layoutlist();


                }else{

                    SCore=String.valueOf(score);
                    System.out.println("check"+SCore);


                    //  btsubmit.setVisibility(View.VISIBLE);

                    final AlertDialog.Builder builder = new AlertDialog.Builder(
                            singlequstionevent.this);
                    builder.setTitle("Test Completed");
                    builder.setMessage("Do you want to submit the test?");
                    builder.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {





                                }
                            });
                    builder.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {


                                    crom.stop();
                                    timern=crom.getText().toString();

                                    stopCountdown();

                                    resetTimer();
                                    System.out.println("timer"+timern);




                                    String Anwercount="1";



                                    try {

                                        cartArrayList = new ArrayList<HashMap<String, String>>();
                                        dbHelper.CreateTable(6);
                                        cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.selctedlist + " where Acount='"+Anwercount+"'", null);
                                        System.out.println( " correctgetValueanswer count " + cursor.getCount());
                                        answercount=cursor.getCount();

                                        if (cursor.getCount() > 0) {
                                            if (cursor.moveToFirst()) {

                                                Qusno = "";
                                                answer = "";
                                                wrong="";
//

                                                do {
                                                    Qusno = cursor.getString(cursor.getColumnIndex("Questionno"));
                                                    answer = cursor.getString(cursor.getColumnIndex("correctanser"));
                                                    wrong = cursor.getString(cursor.getColumnIndex("selectedanser"));

                                                    System.out.println(" Fromcorrect Db " + answer+""+Qusno+"");




                                                    map = new HashMap<String, String>();
                                                    map.put(Constants.HM_ITEMCODE, Qusno);
                                                    map.put(Constants.HM_ITEMNAME, answer);
//
                                                    cartArrayList.add(map);
                                                    System.out.println("correct"+map);

                                                } while (cursor.moveToNext());










                                            }




                                        }
                                    } catch (Exception e) {
                                        System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
                                    }







                                    String Anwercounts="0";



                                    try {

                                        dbHelper.CreateTable(6);
                                        cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.selctedlist + " where Acount='"+Anwercounts+"'", null);
                                        System.out.println(" wronggetValueFromwrong count " + cursor.getCount());
                                        wrongcount=cursor.getCount();

                                        if (cursor.getCount() > 0) {
                                            if (cursor.moveToFirst()) {

                                                Qusno = "";
                                                answer = "";
                                                wrongs="";
//

                                                do {
                                                    Qusno = cursor.getString(cursor.getColumnIndex("Questionno"));
                                                    wrongs = cursor.getString(cursor.getColumnIndex("wronganser"));

                                                    System.out.println( "wrong From Db " + ""+wrongs+"");







                                                } while (cursor.moveToNext());










                                            }




                                        }
                                    } catch (Exception e) {
                                        System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
                                    }







                                    System.out.println("totla--"+answercount+"----"+wrongcount);


                                    int left=answercount+wrongcount;

                                    int totals=
                                            Integer.parseInt(ID);

                                    lefts=totals-left;
                                    Answer= String.valueOf(answercount);
                                    Wrong= String.valueOf(wrongcount);

                                    LEFTANSWER= String.valueOf(lefts);



















                                    /*Intent intent = new Intent(singlequstionevent.this, saveanswerevent.class);


                                    intent.putExtra("count",ID);

                                    intent.putExtra("ID",IDs);

                                    intent.putExtra("timer",timern);
                                    intent.putExtra("Answer",Answer);
                                    intent.putExtra("Wrong",Wrong);
                                    intent.putExtra("LEFTANSWER",LEFTANSWER);
                                    intent.putExtra("isCorrect",isCorrect);
                                    intent.putExtra("isWrong",isWrong );
                                    intent.putExtra("isSkip",isSkip );
                                    intent.putExtra("isAll",isAll );
                                    System.out.println("isall"+isAll);
                                    System.out.println("wrong"+wrong);

                                    startActivity(intent);
                                    finish();*/

                                    onFinishProcess();

                                }
                            });
                    builder.show();



                }

            }
        });


//        butNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//                int selectedId = radioGroup.getCheckedRadioButtonId();
//                if (selectedId == -1) {
//                    Toast.makeText(Singlequestion.this,
//                            "No answer has been selected",
//                            Toast.LENGTH_SHORT)
//                            .show();
//
//
//                }
//                else {
//
//
//
//
//
//                    RadioButton radioButton
//                            = (RadioButton)radioGroup
//                            .findViewById(selectedId);
//
//
//
//
//
//
//
//                String Qno=Qusno;
//                String answers=answer;
//                String Question=Qus;
//                String expl=explain;
//
//                String opts1=opt1;
//                    String opts2=opt2;
//                    String opts3=opt3;
//                    String opts4=opt4;
//
//
//                String selected=radioButton.getText().toString();
//
//                System.out.println("Check4"+Qno+"====anwer=="+answers+"===selected==="+selected);
//                if(selected.equals(answer)){
//
//
//                    String co="correct";
//                    String Acoun="1";
//
//
//                     dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set correctanser ='" + selected + "', Acount = '" + Acoun + "' where Questionno='" + Qno + "'");
//
//
////                    dbHelper.CreateTable(6);
////                    Cursor c = dbHelper.vatbook.rawQuery("select * from " + dbHelper.selctedlist + " where Questionno ='" + Qno + "' ", null);
////                    System.out.println("Add card table count" + c.getCount());
////                    if (c.getCount() == 0) {
////                        dbHelper.CreateTable(6);
////
////
////                        dbHelper.vatbook.execSQL("INSERT INTO "
////                                + dbHelper.selctedlist
////                                + "(Questionno, correctanser,selectedanser,Acount,Questoin,Explains,Answer,Option1,Option2,Option3,Option4,Wrongquestion)"
////                                + " VALUES ('" + Qno + "', '" + selected + "',  '" + selected + "','" + Acoun + "',  '" + Question + "',' " + expl +  "',' " + answers +  "', ' " + opts1 +  "',  ' " + opts2 +  "',' " + opts3 +  "',' " + opts4 +  "',' " + minteger +  "')");
////
////
////
////                        System.out.println("insert4"+dbHelper.selctedlist
////                                + "(Questionno, correctanser,selectedanser,Acount)"
////                                + " VALUES ('" + Qno + "', '" + selected + "',  '" + selected + "','" + Acoun + "',  ' " + Question +  "',' " + expl +  "',' " + answers +  "', ' " + opts1 +  "',  ' " + opts2 +  "',' " + opts3 +  "',' " + opts4 +  "',' " + minteger +  "')");
////
////
////
////                       // dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set correctanser ='" + selected + "', Acount = '" + Acoun + "' where Questionno='" + Qno + "'");
////
////
////
////                    } else {
////                        dbHelper.CreateTable(6);
////
////                        dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set correctanser = '" + selected + "' where Questionno = '" + Qno + "'  ");
////                        System.out.println("updatecorrct4"+dbHelper.selctedlist + " set correctanser = '" + selected + "' where Questionno = '" + Qno + "'  ");
////
////
////
////
////
////
////                    }
//
//
//
//
//
//                    System.out.println("correctanswer");
//                }
//                else{
//
//                    String was="wronganswer";
//
//                    String Acoun="0";
//                    minteger = minteger + 1;
//                    System.out.println("min"+minteger);
//                    dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set wronganser ='" + selected + "', Acount = '" + Acoun + "' where Questionno='" + Qno + "'");
//                    System.out.println("wronganswer"+selected);
//
////                    dbHelper.CreateTable(6);
////                    Cursor c = dbHelper.vatbook.rawQuery("select * from " + dbHelper.selctedlist + " where Questionno ='" + Qno + "' ", null);
////                    System.out.println("Add card table count" + c.getCount());
////                    if (c.getCount() == 0) {
////                        dbHelper.CreateTable(6);
////                        dbHelper.vatbook.execSQL("INSERT INTO "
////                                + dbHelper.selctedlist
////                                + "(Questionno, wronganser,selectedanser, Acount,Questoin,Explains,Answer,Option1,Option2,Option3,Option4,Wrongquestion)"
////                                + " VALUES ('" + Qno + "', '" + selected + "',  '" + selected + "','" + Acoun + "',  '" + Question + "','" + expl + "','" + answers + "', '" + opts1 + "',  '" + opts2 + "','" + opts3 + "','" + opts4 + "','" + minteger + "')");
////                        System.out.println("wronginsert4"+ dbHelper.selctedlist
////                                + "(Questionno, wronganser,selectedanser, Acount,Questoin,Explains)"
////                                + " VALUES ('" + Qno + "', '" + selected + "',  '" + selected + "','" + Acoun + "',  '" + Question + "','" + expl + "','" + answers + "', '" + opts1 + "',  '" + opts2 + "','" + opts3 + "','" + opts4 + "','" + minteger + "')");
////
////
////
////
////
////
////
////                    } else {
////                        dbHelper.CreateTable(6);
////                        dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set wronganser = '" + selected + "' where Questionno = '" + Qno + "'  ");
////                        System.out.println("update4"+dbHelper.selctedlist + " set wronganser = '" + selected + "' where Questionno = '" + Qno + "'  ");
////
////                    }
//
//
//
//
//                    System.out.println("notcorrectanswer");
//                }
//
//
//                                   int result = Integer.parseInt(ID);
//
//                if(quid<result){
//                    quid++;
//                    String questionid=String.valueOf(quid);
//                    System.out.println("check"+questionid);
//
//                    setQuestionView(questionid);
//                    setlist();
//                    views();
//                    System.out.println("icremtrnt"+quid);
//
//                }else{
//
//                    SCore=String.valueOf(score);
//                    System.out.println("check"+SCore);
//
//
//                    //  btsubmit.setVisibility(View.VISIBLE);
//
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(
//                            Singlequestion.this);
//                    builder.setTitle("Test Complited");
//                    builder.setMessage("Do You Want Submit Your Test?");
//                    builder.setNegativeButton("NO",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog,
//                                                    int which) {
//
//
//
//
//
//                                }
//                            });
//                    builder.setPositiveButton("YES",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog,
//                                                    int which) {
//
//
//
//                                    timer=crom.getText().toString();
//                                    crom.stop();
//                                    stopCountdown();
//
//
//
//
//
//
//
//                                    String Anwercount="1";
//
//
//
//                                    try {
//
//                                        cartArrayList = new ArrayList<HashMap<String, String>>();
//                                        dbHelper.CreateTable(6);
//                                        cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.selctedlist + " where Acount='"+Anwercount+"'", null);
//                                        System.out.println(TAG + " getValueanswer count " + cursor.getCount());
//                                        answercount=cursor.getCount();
//
//                                        if (cursor.getCount() > 0) {
//                                            if (cursor.moveToFirst()) {
//
//                                                Qusno = "";
//                                                answer = "";
//                                                wrong="";
////
//
//                                                do {
//                                                    Qusno = cursor.getString(cursor.getColumnIndex("Questionno"));
//                                                    answer = cursor.getString(cursor.getColumnIndex("correctanser"));
//                                                    wrong = cursor.getString(cursor.getColumnIndex("selectedanser"));
////                        str_itemIndex = cursor.getString(cursor.getColumnIndex("itemIndex"));
////                        str_itemQty = cursor.getString(cursor.getColumnIndex("itemQty"));
////                        str_itemVat = cursor.getString(cursor.getColumnIndex("itemVat"));
////                        str_itemPrice = cursor.getString(cursor.getColumnIndex("itemPrice"));
////                        str_itemVatAmt = cursor.getString(cursor.getColumnIndex("itemVatAmt"));
//                                                    System.out.println(TAG + " From Db " + answer+""+Qusno+"");
//
//
//
//
//                                                    map = new HashMap<String, String>();
//                                                    map.put(Constants.HM_ITEMCODE, Qusno);
//                                                    map.put(Constants.HM_ITEMNAME, answer);
////                        map.put(Constants.HM_ITEMINDEX, str_itemIndex);
////                        map.put(Constants.HM_ITEMPRICE, str_itemPrice);
////                        map.put(Constants.HM_VATPERCENT, str_itemVat);
////                        map.put(Constants.HM_QTY, str_itemQty);
////                        map.put(Constants.HM_VATAMT, str_itemVatAmt);
//                                                    cartArrayList.add(map);
//                                                    System.out.println("correct"+map);
//
//                                                } while (cursor.moveToNext());
//
//
//
//
//
//
//
//
//
//
//                                            }
//
//
//
//
//                                        }
//                                    } catch (Exception e) {
//                                        System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
//                                    }
//
//
//
//
//
//
//
//                                    String Anwercounts="0";
//
//
//
//                                    try {
//
//                                        dbHelper.CreateTable(6);
//                                        cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.selctedlist + " where Acount='"+Anwercounts+"'", null);
//                                        System.out.println(TAG + " getValueFromwrong count " + cursor.getCount());
//                                        wrongcount=cursor.getCount();
//
//                                        if (cursor.getCount() > 0) {
//                                            if (cursor.moveToFirst()) {
//
//                                                Qusno = "";
//                                                answer = "";
//                                                wrongs="";
////
//
//                                                do {
//                                                    Qusno = cursor.getString(cursor.getColumnIndex("Questionno"));
//                                                    //answer = cursor.getString(cursor.getColumnIndex("correctanser"));
//                                                    wrongs = cursor.getString(cursor.getColumnIndex("wronganser"));
////                        str_itemIndex = cursor.getString(cursor.getColumnIndex("itemIndex"));
////                        str_itemQty = cursor.getString(cursor.getColumnIndex("itemQty"));
////                        str_itemVat = cursor.getString(cursor.getColumnIndex("itemVat"));
////                        str_itemPrice = cursor.getString(cursor.getColumnIndex("itemPrice"));
////                        str_itemVatAmt = cursor.getString(cursor.getColumnIndex("itemVatAmt"));
//                                                    System.out.println(TAG + " From Db " + ""+wrongs+"");
//
//
//
//
//
//
//
//                                                } while (cursor.moveToNext());
//
//
//
//
//
//
//
//
//
//
//                                            }
//
//
//
//
//                                        }
//                                    } catch (Exception e) {
//                                        System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
//                                    }
//
//
//
//
//
//
//
//                                    System.out.println("corectandwrong--"+answercount+"----"+wrongcount);
//
//
//                                    int left=answercount+wrongcount;
//
//                                    int totals=
//                                            Integer.parseInt(ID);
//
//                                    lefts=totals-left;
//                                    Answer= String.valueOf(answercount);
//                                    Wrong= String.valueOf(wrongcount);
//
//                                    LEFTANSWER= String.valueOf(lefts);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//                                    Intent intent = new Intent(Singlequestion.this, Resultpage.class);
//
//
//                                    intent.putExtra("count",ID);
//
//                                    intent.putExtra("ID",IDs);
//
//                                    intent.putExtra("timer",timer);
//                                    intent.putExtra("Answer",Answer);
//                                    intent.putExtra("Wrong",Wrong);
//                                    intent.putExtra("LEFTANSWER",LEFTANSWER);
//
//                                    startActivity(intent);
//                                    finish();
//
//                                }
//                            });
//                    builder.show();
//
//
//
//                }
//
//
//
//
//            }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        }
//        });



        butprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                butttontext="prev";
                int result = Integer.parseInt(ID);
                radio1.setChecked(false);

                radio2.setChecked(false);
                radio3.setChecked(false);
                radio4.setChecked(false);
//
//                    System.out.println("result"+result);
//                    System.out.println("qustiinid"+quid);
                if(quid<=result){


                    if(quid==1){

                        // butprev.setVisibility(View.GONE);




                    }else{


                        minteger = minteger - 1;
                        System.out.println("minmines"+minteger);



                        quid--;



                        String questionid=String.valueOf(quid);
                        System.out.println("decrent"+questionid);

                        setQuestionView(questionid);
                        setlist();
                        views();
                        System.out.println("decrement"+quid);


                        layoutlist();
                    }


                }else{

                    SCore=String.valueOf(score);








                }




//                int selectedId = radioGroup.getCheckedRadioButtonId();
//                if (selectedId == -1) {
//                    Toast.makeText(Singlequestion.this,
//                            "No answer has been selected",
//                            Toast.LENGTH_SHORT)
//                            .show();
//                }
//                else {
//
//                    RadioButton radioButton
//                            = (RadioButton)radioGroup
//                            .findViewById(selectedId);
//
//                    // Now display the value of selected item
//                    // by the Toast message
//                    Toast.makeText(Singlequestion.this,
//                            radioButton.getText(),
//                            Toast.LENGTH_SHORT)
//                            .show();
//
//
//
//
//
//                    String Qno=Qusno;
//                    String answers=answer;
//                    String selected=radioButton.getText().toString();
//
//                    System.out.println("Check4"+Qno+"====anwer=="+answers+"===selected==="+selected);
//                    if(selected.equals(answer)){
//
//
//                        String co="correct";
//                        String Acoun="1";
//                        dbHelper.CreateTable(6);
//                        Cursor c = dbHelper.vatbook.rawQuery("select * from " + dbHelper.selctedlist + " where Questionno ='" + Qno + "' ", null);
//                        System.out.println("Add card table count" + c.getCount());
//                        if (c.getCount() == 0) {
//                            dbHelper.CreateTable(6);
//                            dbHelper.vatbook.execSQL("INSERT INTO "
//                                    + dbHelper.selctedlist
//                                    + "(Questionno, correctanser,selectedanser,Acount)"
//                                    + " VALUES ('" + Qno + "', '" + selected + "',  '" + selected + "','" + Acoun + "')");
//                            System.out.println("insert4"+dbHelper.selctedlist
//                                    + "(Questionno, correctanser,selectedanser,Acount)"
//                                    + " VALUES ('" + Qno + "', '" + selected + "',  '" + selected + "','" + Acoun + "')");
//                        } else {
//                            dbHelper.CreateTable(6);
//                            dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set correctanser = '" + selected + "' where Questionno = '" + Qno + "'  ");
//                            System.out.println("updatecorrct4"+dbHelper.selctedlist + " set correctanser = '" + selected + "' where Questionno = '" + Qno + "'  ");
//
//                        }
//
//
//
//
//
//                        System.out.println("correctanswer");
//                    }
//                    else{
//
//                        String was="wronganswer";
//
//                        String Acoun="0";
//
//                        dbHelper.CreateTable(6);
//                        Cursor c = dbHelper.vatbook.rawQuery("select * from " + dbHelper.selctedlist + " where Questionno ='" + Qno + "' ", null);
//                        System.out.println("Add card table count" + c.getCount());
//                        if (c.getCount() == 0) {
//                            dbHelper.CreateTable(6);
//                            dbHelper.vatbook.execSQL("INSERT INTO "
//                                    + dbHelper.selctedlist
//                                    + "(Questionno, wronganser,selectedanser, Acount)"
//                                    + " VALUES ('" + Qno + "', '" + selected + "',  '" + selected + "','" + Acoun + "')");
//                            System.out.println("wronginsert4"+ dbHelper.selctedlist
//                                    + "(Questionno, wronganser,selectedanser, Acount)"
//                                    + " VALUES ('" + Qno + "', '" + selected + "',  '" + selected + "','" + Acoun + "')");
//                        } else {
//                            dbHelper.CreateTable(6);
//                            dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set wronganser = '" + selected + "' where Questionno = '" + Qno + "'  ");
//                            System.out.println("update4"+dbHelper.selctedlist + " set wronganser = '" + selected + "' where Questionno = '" + Qno + "'  ");
//
//                        }
//
//
//
//
//                        System.out.println("notcorrectanswer");
//                    }
//
//                    int result = Integer.parseInt(ID);
//
//                    System.out.println("result"+result);
//                    System.out.println("qustiinid"+quid);
//
//
//
//
//
//
//                    if(quid<=result){
//
//
//                        if(quid==1){
//
//                            // butprev.setVisibility(View.GONE);
//
//                        }else{
//
//
//
//
//
//                            quid--;
//
//
//
//                            String questionid=String.valueOf(quid);
//                            System.out.println("decrent"+questionid);
//
//                            setQuestionView(questionid);
//                            setlist();
//                            views();
//                            System.out.println("decrement"+quid);
//                        }
//
//
//                    }else{
//
//                        SCore=String.valueOf(score);
//
//
//
//
//
//
//
//
//                    }
//
//                }
//
//
//
//
//
//
//





            }
        });


        btsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                crom.stop();
                timern=crom.getText().toString();

                stopCountdown();

                resetTimer();
                System.out.println("timers"+timern);


                String Anwercount="1";



                try {

                    cartArrayList = new ArrayList<HashMap<String, String>>();
                    dbHelper.CreateTable(6);
                    cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.selctedlist + " where Acount='"+Anwercount+"'", null);
                    System.out.println(TAG + " getValueanswer count " + cursor.getCount());
                    answercount=cursor.getCount();

                    if (cursor.getCount() > 0) {
                        if (cursor.moveToFirst()) {

                            Qusno = "";
                            answer = "";
                            wrong="";
//

                            do {
                                Qusno = cursor.getString(cursor.getColumnIndex("Questionno"));
                                answer = cursor.getString(cursor.getColumnIndex("correctanser"));
                                wrong = cursor.getString(cursor.getColumnIndex("selectedanser"));
//                        str_itemIndex = cursor.getString(cursor.getColumnIndex("itemIndex"));
//                        str_itemQty = cursor.getString(cursor.getColumnIndex("itemQty"));
//                        str_itemVat = cursor.getString(cursor.getColumnIndex("itemVat"));
//                        str_itemPrice = cursor.getString(cursor.getColumnIndex("itemPrice"));
//                        str_itemVatAmt = cursor.getString(cursor.getColumnIndex("itemVatAmt"));
                                System.out.println(TAG + " From Db " + answer+""+Qusno+"");




                                map = new HashMap<String, String>();
                                map.put(Constants.HM_ITEMCODE, Qusno);
                                map.put(Constants.HM_ITEMNAME, answer);
//                        map.put(Constants.HM_ITEMINDEX, str_itemIndex);
//                        map.put(Constants.HM_ITEMPRICE, str_itemPrice);
//                        map.put(Constants.HM_VATPERCENT, str_itemVat);
//                        map.put(Constants.HM_QTY, str_itemQty);
//                        map.put(Constants.HM_VATAMT, str_itemVatAmt);
                                cartArrayList.add(map);
                                System.out.println("correct"+map);

                            } while (cursor.moveToNext());










                        }




                    }
                } catch (Exception e) {
                    System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
                }







                String Anwercounts="0";



                try {

                    dbHelper.CreateTable(6);
                    cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.selctedlist + " where Acount='"+Anwercounts+"'", null);
                    System.out.println(TAG + " getValueFromwrong count " + cursor.getCount());
                    wrongcount=cursor.getCount();

                    if (cursor.getCount() > 0) {
                        if (cursor.moveToFirst()) {

                            Qusno = "";
                            answer = "";
                            wrongs="";
//

                            do {
                                Qusno = cursor.getString(cursor.getColumnIndex("Questionno"));
                                //answer = cursor.getString(cursor.getColumnIndex("correctanser"));
                                wrongs = cursor.getString(cursor.getColumnIndex("wronganser"));
//                        str_itemIndex = cursor.getString(cursor.getColumnIndex("itemIndex"));
//                        str_itemQty = cursor.getString(cursor.getColumnIndex("itemQty"));
//                        str_itemVat = cursor.getString(cursor.getColumnIndex("itemVat"));
//                        str_itemPrice = cursor.getString(cursor.getColumnIndex("itemPrice"));
//                        str_itemVatAmt = cursor.getString(cursor.getColumnIndex("itemVatAmt"));
                                System.out.println(TAG + " From Db " +""+wrongs+"");







                            } while (cursor.moveToNext());










                        }




                    }
                } catch (Exception e) {
                    System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
                }







                System.out.println("corectandwrong--"+answercount+"----"+wrongcount);


                int left=answercount+wrongcount;

                int totals=
                        Integer.parseInt(ID);

                lefts=totals-left;
                Answer= String.valueOf(answercount);
                Wrong= String.valueOf(wrongcount);

                LEFTANSWER= String.valueOf(lefts);

                System.out.println("totals"+totals+"   "+"left5"+LEFTANSWER);
















                Intent intent = new Intent(singlequstionevent.this, saveanswerevent.class);


                intent.putExtra("count",ID);

                intent.putExtra("ID",IDs);

                intent.putExtra("timer",timern);
                intent.putExtra("Answer",Answer);
                intent.putExtra("Wrong",Wrong);
                intent.putExtra("LEFTANSWER",LEFTANSWER);
                intent.putExtra("isCorrect",isCorrect);
                intent.putExtra("isWrong",isWrong );
                intent.putExtra("isSkip",isSkip );
                intent.putExtra("isAll","YES" );
                System.out.println("isall"+"YES");
                System.out.println("wwrong"+wrong);

                startActivity(intent);

                finish();



            }
        });

    }

    private void onFinishProcess() {
        String Anwercount="1";



        try {
            Correctqus = new ArrayList<String>();

            dbHelper.CreateTable(6);
            cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.selctedlist + " where Acount='"+Anwercount+"'", null);
            System.out.println( " correctgetValueanswer count " + cursor.getCount());
            answercount=cursor.getCount();

            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {

                    CorrectQusno = "";

//

                    do {
                        CorrectQusno = cursor.getString(cursor.getColumnIndex("questionIndexId"));


                        System.out.println(" Fromcorrect Db " + answer+""+CorrectQusno+"");



                        Correctqus.add(CorrectQusno);


                    } while (cursor.moveToNext());








                }

                callfunc1();


            }
        } catch (Exception e) {
            System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
        }







        String Anwercounts="0";




        try {

            Wrongqus = new ArrayList<String>();
            dbHelper.CreateTable(6);
            cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.selctedlist + " where Acount='"+Anwercounts+"'", null);
            System.out.println(" wronggetValueFromwrong count " + cursor.getCount());
            wrongcount=cursor.getCount();

            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {

                    Wrongquestiono = "";

//

                    do {
                        Wrongquestiono = cursor.getString(cursor.getColumnIndex("questionIndexId"));


                        System.out.println( "wrong From Db " + ""+Wrongquestiono+"");



                        Wrongqus.add(Wrongquestiono);



                    } while (cursor.moveToNext());










                }

                callfunc2();


            }
        } catch (Exception e) {
            System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
        }






        String Anwercountss="3";



        try {

            Skipequs = new ArrayList<String>();
            dbHelper.CreateTable(6);
            cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.selctedlist + " where Acount='"+Anwercountss+"'", null);
            System.out.println(" Skip count " + cursor.getCount());
            wrongcount=cursor.getCount();

            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {

                    Skipqno = "";

//

                    do {
                        Skipqno = cursor.getString(cursor.getColumnIndex("questionIndexId"));


                        System.out.println( "Skipqno From Db " + ""+Skipqno+"");



                        Skipequs.add(Skipqno);



                    } while (cursor.moveToNext());










                }

                callfunc3();


            }
        } catch (Exception e) {
            System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
        }





        try {

            Allqus = new ArrayList<String>();
            dbHelper.CreateTable(4);
            cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.questionresponse , null);
            System.out.println(" Allqusno count " + cursor.getCount());
            wrongcount=cursor.getCount();

            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {

                    Allqusno = "";

//

                    do {
                        Allqusno = cursor.getString(cursor.getColumnIndex("questionIndexId"));


                        System.out.println( "Allqusno From Db " + ""+Allqusno+"");



                        Allqus.add(Allqusno);



                    } while (cursor.moveToNext());










                }

                callfunc4();


            }
        } catch (Exception e) {
            System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
        }
        try {

            iscorrect = new ArrayList<String>();
            dbHelper.CreateTable(4);
            cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.selctedlist , null);
            System.out.println(" iscorrect count " + cursor.getCount());
            wrongcount=cursor.getCount();

            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {

                    Iscorrect = "";

//

                    do {
                        Iscorrect = cursor.getString(cursor.getColumnIndex("isCorrect"));


                        System.out.println( "Allqusno From Db " + ""+Iscorrect+"");



                        iscorrect.add(Iscorrect);



                    } while (cursor.moveToNext());










                }

                callfunc5();


            }
        } catch (Exception e) {
            System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
        }






        if(isCorrect.equals("null")){

        }else{
            LASTID = IDs;
            submittrsult(LASTID,Answer,Wrong,timer);
        }
    }

    private void callfunc1() {



        Object[] objNames = Correctqus.toArray();

        //Second Step: convert Object array to String array

        String[] strNames = Arrays.copyOf(objNames, objNames.length, String[].class);

        System.out.println("ArrayList converted to String array3");

        //print elements of String array
        for(int i=0; i < strNames.length; i++){
            System.out.println(strNames[i]);
        }





        for (int i = 0; i < Correctqus.size(); i++) {
            System.out.println("arraylist listName"+Correctqus.get(i));





            //Second Step: convert Object array to String array






            objArray = Correctqus.toArray();



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

            right_questions=newString;

            System.out.println("Correctquestionumber " + newString);




        }


    }



    private void callfunc2() {



        Object[] objNames = Wrongqus.toArray();

        //Second Step: convert Object array to String array

        String[] strNames = Arrays.copyOf(objNames, objNames.length, String[].class);

        System.out.println("ArrayList converted to String array3");

        //print elements of String array
        for(int i=0; i < strNames.length; i++){
            System.out.println(strNames[i]);
        }





        for (int i = 0; i < Wrongqus.size(); i++) {
            System.out.println("arraylist listName"+Wrongqus.get(i));





            //Second Step: convert Object array to String array






            objArray2 = Wrongqus.toArray();



            String[] stringArray = new String[objArray2.length];

            // copy elements from object array to string array
            for (int ik = 0; ik < objArray2.length; ik++)
                stringArray[ik] = String.valueOf(objArray2[ik]);

            System.out.println(Arrays.toString(stringArray));

            System.out.println("string listName"+convertObjectArrayToString2(stringArray, ","));

//            for(int k=0; k < objArray.length ; k++)
//
//                System.out.println("Elements in Array"+objArray[k]);


            newString2 = Arrays.toString(stringArray);

            newString2 = newString2.substring(1, newString2.length()-1);

            wrongQuestions=newString2;

            System.out.println("wrongquestionumber " + newString2);




        }


    }


    private void callfunc3() {



        Object[] objNames = Skipequs.toArray();


        String[] strNames = Arrays.copyOf(objNames, objNames.length, String[].class);

        System.out.println("ArrayList converted to String array3");

        for(int i=0; i < strNames.length; i++){
            System.out.println(strNames[i]);
        }





        for (int i = 0; i < Skipequs.size(); i++) {
            System.out.println("arraylist listName"+Skipequs.get(i));











            objArray3 = Skipequs.toArray();



            String[] stringArray = new String[objArray3.length];

            // copy elements from object array to string array
            for (int ik = 0; ik < objArray3.length; ik++)
                stringArray[ik] = String.valueOf(objArray3[ik]);

            System.out.println(Arrays.toString(stringArray));

            System.out.println("string listName"+convertObjectArrayToString3(stringArray, ","));

//            for(int k=0; k < objArray.length ; k++)
//
//                System.out.println("Elements in Array"+objArray[k]);


            newString3 = Arrays.toString(stringArray);

            newString3 = newString3.substring(1, newString3.length()-1);

            skippedQuestions=newString3;

            System.out.println("Skipequestionumber " + newString3);




        }


    }


    private void callfunc4() {



        Object[] objNames = Allqus.toArray();


        String[] strNames = Arrays.copyOf(objNames, objNames.length, String[].class);

        System.out.println("ArrayList converted to String array3");

        for(int i=0; i < strNames.length; i++){
            System.out.println(strNames[i]);
        }





        for (int i = 0; i < Allqus.size(); i++) {
            System.out.println("arraylist listName"+Allqus.get(i));











            objArray4 = Allqus.toArray();



            String[] stringArray = new String[objArray4.length];

            // copy elements from object array to string array
            for (int ik = 0; ik < objArray4.length; ik++)
                stringArray[ik] = String.valueOf(objArray4[ik]);

            System.out.println(Arrays.toString(stringArray));

            System.out.println("string listName"+convertObjectArrayToString4(stringArray, ","));

//            for(int k=0; k < objArray.length ; k++)
//
//                System.out.println("Elements in Array"+objArray[k]);


            newString4 = Arrays.toString(stringArray);

            newString4 = newString4.substring(1, newString4.length()-1);

            allquestiono=newString4;

            System.out.println("allquestionumber " + newString4);




        }


    }

    private void callfunc5() {


        Object[] objNames = iscorrect.toArray();


        String[] strNames = Arrays.copyOf(objNames, objNames.length, String[].class);

        System.out.println("ArrayList converted to String array3");

        for(int i=0; i < strNames.length; i++){
            System.out.println(strNames[i]);
        }





        for (int i = 0; i < iscorrect.size(); i++) {
            System.out.println("arraylist listName"+iscorrect.get(i));











            objArray5 = iscorrect.toArray();



            String[] stringArray = new String[objArray5.length];

            // copy elements from object array to string array
            for (int ik = 0; ik < objArray5.length; ik++)
                stringArray[ik] = String.valueOf(objArray5[ik]);

            System.out.println(Arrays.toString(stringArray));

            System.out.println("string listName"+convertObjectArrayToString5(stringArray, ","));

//            for(int k=0; k < objArray.length ; k++)
//
//                System.out.println("Elements in Array"+objArray[k]);


            newString5 = Arrays.toString(stringArray);

            newString5 = newString5.substring(1, newString5.length()-1);



            System.out.println("allquestionumber " + newString5);




        }

    }

    private String convertObjectArrayToString(String[] stringArray, String s) {


        StringBuilder sb = new StringBuilder();
        for (Object obj : stringArray)
            sb.append(obj.toString()).append(s);
        return sb.substring(0, sb.length() - 1);

    }

    private String convertObjectArrayToString2(String[] stringArray, String s) {


        StringBuilder sb = new StringBuilder();
        for (Object obj : stringArray)
            sb.append(obj.toString()).append(s);
        return sb.substring(0, sb.length() - 1);

    }
    private String convertObjectArrayToString3(String[] stringArray, String s) {


        StringBuilder sb = new StringBuilder();
        for (Object obj : stringArray)
            sb.append(obj.toString()).append(s);
        return sb.substring(0, sb.length() - 1);

    }

    private String convertObjectArrayToString4(String[] stringArray, String s) {


        StringBuilder sb = new StringBuilder();
        for (Object obj : stringArray)
            sb.append(obj.toString()).append(s);
        return sb.substring(0, sb.length() - 1);

    }


    private String convertObjectArrayToString5(String[] stringArray, String s) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : stringArray)
            sb.append(obj.toString()).append(s);
        return sb.substring(0, sb.length() - 1);
    }


    private void submittrsult(final String lastid, final String answer, final String wrong, final String timer) {




        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(singlequstionevent.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.eventsaveanswer, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);

                        System.out.println(" allSubjectAPI response - " + response);

                        utilis.dismissProgress();

                        str_result = obj.getString("errorCode");
                        System.out.print( " allSubjectAPI result " + str_result);

                        if (Integer.parseInt(str_result) == 0) {

                            str_message = obj.getString("Message");

                            Intent intent = new Intent(singlequstionevent.this, saveanswerevent.class);


                            intent.putExtra("count",ID);

                            intent.putExtra("ID",IDs);

                            intent.putExtra("timer",timern);
                            intent.putExtra("Answer",Answer);
                            intent.putExtra("Wrong",Wrong);
                            intent.putExtra("LEFTANSWER",LEFTANSWER);
                            intent.putExtra("isCorrect",isCorrect);
                            intent.putExtra("isWrong",isWrong );
                            intent.putExtra("isSkip",isSkip );
                            intent.putExtra("isAll",isAll );
                            System.out.println("isall"+isAll);
                            System.out.println("wrong"+wrong);

                            startActivity(intent);
                            finish();





                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(singlequstionevent.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(singlequstionevent.this, singlequstionevent.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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

                    params.put("lastIndexId",lastid );
                    params.put("correctAnswer",answer );
                    params.put("wrongAnswer",wrong );
                    params.put("duration",timer );

                    params.put("wrongQuestions",wrongQuestions );
                    params.put("rightQuestions", right_questions );
                    params.put("skippedQuestions",skippedQuestions );
                    params.put("allQuestions",allquestiono );
                    params.put("isCorrect",isCorrect);
                    params.put("isWrong",isWrong);
                    params.put("isSkip",isSkip);
                    params.put("isAll",isAll );
                    params.put("studentIndexId",utilis.strStudentID);

                    //   params.put("allQuestions",allquestiono );

                    System.out.println( "  inputsalll " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, singlequstionevent.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }







    }

    @SuppressLint("RestrictedApi")
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

    private void layoutlist() {

        System.out.println("buttonselected"+butttontext);

        if(butttontext.equals("Next")){
            if(sectionid.equals("1")){

                radio1.setChecked(true);
                radio2.setChecked(false);
                radio3.setChecked(false);
                radio4.setChecked(false);


                op1.setBackground(getResources().getDrawable(R.drawable.yourbuttonbackgrounds,null));
                op2.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op3.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op4.setBackground(getResources().getDrawable(R.drawable.ash,null));
            }else if(sectionid.equals("2")){


                radio1.setChecked(false);

                radio2.setChecked(true);
                radio3.setChecked(false);
                radio4.setChecked(false);
                op1.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op2.setBackground(getResources().getDrawable(R.drawable.yourbuttonbackgrounds,null));
                op3.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op4.setBackground(getResources().getDrawable(R.drawable.ash,null));

            }else  if(sectionid.equals("3")){

                radio1.setChecked(false);

                radio2.setChecked(false);
                radio3.setChecked(true);
                radio4.setChecked(false);


                op1.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op2.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op3.setBackground(getResources().getDrawable(R.drawable.yourbuttonbackgrounds,null));
                op4.setBackground(getResources().getDrawable(R.drawable.ash,null));

            }else  if(sectionid.equals("4")){

                radio1.setChecked(false);

                radio2.setChecked(false);
                radio3.setChecked(false);
                radio4.setChecked(true);
                op1.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op2.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op3.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op4.setBackground(getResources().getDrawable(R.drawable.yourbuttonbackgrounds,null));

            }else  {

                radio1.setChecked(false);

                radio2.setChecked(false);
                radio3.setChecked(false);
                radio4.setChecked(false);


                op1.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op2.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op3.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op4.setBackground(getResources().getDrawable(R.drawable.ash,null));

            }


        }else if(butttontext.equals("prev")){

            System.out.println("sectionid options"+sectionid);


            if(sectionid.equals("1")){


                radio1.setChecked(true);

                radio2.setChecked(false);
                radio3.setChecked(false);
                radio4.setChecked(false);


                op1.setBackground(getResources().getDrawable(R.drawable.yourbuttonbackgrounds,null));
                op2.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op3.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op4.setBackground(getResources().getDrawable(R.drawable.ash,null));
            }else if(sectionid.equals("2")){

                radio1.setChecked(false);

                radio2.setChecked(true);
                radio3.setChecked(false);
                radio4.setChecked(false);


                op1.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op2.setBackground(getResources().getDrawable(R.drawable.yourbuttonbackgrounds,null));
                op3.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op4.setBackground(getResources().getDrawable(R.drawable.ash,null));

            }else  if(sectionid.equals("3")){

                radio1.setChecked(false);

                radio2.setChecked(false);
                radio3.setChecked(true);
                radio4.setChecked(false);

                op1.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op2.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op3.setBackground(getResources().getDrawable(R.drawable.yourbuttonbackgrounds,null));
                op4.setBackground(getResources().getDrawable(R.drawable.ash,null));

            }else  if(sectionid.equals("4")){


                radio1.setChecked(false);

                radio2.setChecked(false);
                radio3.setChecked(false);
                radio4.setChecked(true);


                op1.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op2.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op3.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op4.setBackground(getResources().getDrawable(R.drawable.yourbuttonbackgrounds,null));

            }else  {

                radio1.setChecked(false);

                radio2.setChecked(false);
                radio3.setChecked(false);
                radio4.setChecked(false);
                op1.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op2.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op3.setBackground(getResources().getDrawable(R.drawable.ash,null));
                op4.setBackground(getResources().getDrawable(R.drawable.ash,null));

            }









        }


    }




    private void views() {



        radio1=findViewById(R.id.radia_id1);

        radio2=findViewById(R.id.radia_id2);
        radio3=findViewById(R.id.radia_id3);
        radio4=findViewById(R.id.radia_id4);
        radioGroup = (RadioGroup)findViewById(R.id.groupradio);

        radia_id1f1=findViewById(R.id.radia_id1f1);

        radia_id1f2=findViewById(R.id.radia_id1f2);
        radia_id1f3=findViewById(R.id.radia_id1f3);
        radia_id1f4=findViewById(R.id.radia_id1f4);

        radio1image=findViewById(R.id.radio1image);
        radio2image=findViewById(R.id.radio2image);
        radio3image=findViewById(R.id.radio3image);
        radio4image=findViewById(R.id.radio4image);

    }

    private void setlist() {








        if(opt1.length() >10){

            System.out.println("opt1"+opt1);
            System.out.println("optinonlenth"+opt1.length());
            String   lastFourDigits = opt1.substring(opt1.length() - 4);
            System.out.println("opt1"+lastFourDigits);
            if (lastFourDigits.equals(".png")){
                radio1image.setVisibility(View.VISIBLE);
                radia_id1f1.setVisibility(View.GONE);

                Glide.with(singlequstionevent.this).load(opt1).apply(new RequestOptions()
                        .override(Target.SIZE_ORIGINAL)
                        .format(DecodeFormat.PREFER_ARGB_8888))
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE).into(radio1image);


            }else
            {
                radia_id1f1.setVisibility(View.VISIBLE);
                radio1image.setVisibility(View.GONE);
                radia_id1f1.setDisplayText(opt1);

            }




        }else{
            radia_id1f1.setVisibility(View.VISIBLE);
            radio1image.setVisibility(View.GONE);
            radia_id1f1.setDisplayText(opt1);


        }



        if (opt2.length() >10){
            String   lastFourDigits2 = opt2.substring(opt2.length() - 4);
            System.out.println("opt2"+lastFourDigits2);
            if (lastFourDigits2.equals(".png")){
                radio2image.setVisibility(View.VISIBLE);
                radia_id1f2.setVisibility(View.GONE);

                Glide.with(singlequstionevent.this).load(opt2).apply(new RequestOptions()
                        .override(Target.SIZE_ORIGINAL)
                        .format(DecodeFormat.PREFER_ARGB_8888))
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE).into(radio2image);


            }else
            {
                radia_id1f2.setVisibility(View.VISIBLE);
                radio2image.setVisibility(View.GONE);
                radia_id1f2.setDisplayText(opt2);

            }
        }else{
            radia_id1f2.setVisibility(View.VISIBLE);
            radio2image.setVisibility(View.GONE);
            radia_id1f2.setDisplayText(opt2);
        }


        if (opt3.length() > 10){
            String   lastFourDigits3 = opt3.substring(opt3.length() - 4);
            System.out.println("opt3"+lastFourDigits3);
            if (lastFourDigits3.equals(".png")){
                radio3image.setVisibility(View.VISIBLE);
                radia_id1f3.setVisibility(View.GONE);

                Glide.with(singlequstionevent.this).load(opt3).apply(new RequestOptions()
                        .override(Target.SIZE_ORIGINAL)
                        .format(DecodeFormat.PREFER_ARGB_8888))
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE).into(radio3image);


            }else
            {
                radia_id1f3.setVisibility(View.VISIBLE);
                radio3image.setVisibility(View.GONE);
                radia_id1f3.setDisplayText(opt3);

            }


        }
        else {
            radia_id1f3.setVisibility(View.VISIBLE);
            radio3image.setVisibility(View.GONE);
            radia_id1f3.setDisplayText(opt3);
        }

        if (opt4.length() >10){
            String   lastFourDigits4 = opt4.substring(opt4.length() - 4);
            System.out.println("opt4"+lastFourDigits4);
            if (lastFourDigits4.equals(".png")){
                radio4image.setVisibility(View.VISIBLE);
                radia_id1f4.setVisibility(View.GONE);

                //   Glide.with(Singlequestion.this).load(opt4).into(radio4image);
                Glide.with(singlequstionevent.this).load(opt4)
                        .apply(new RequestOptions()
                                .override(Target.SIZE_ORIGINAL)
                                .format(DecodeFormat.PREFER_ARGB_8888))
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                        .into(radio4image);
            }else
            {
                radia_id1f4.setVisibility(View.VISIBLE);
                radio4image.setVisibility(View.GONE);
                radia_id1f4.setDisplayText(opt4);

            }
        }
        else{
            radia_id1f4.setVisibility(View.VISIBLE);
            radio4image.setVisibility(View.GONE);
            radia_id1f4.setDisplayText(opt4);
        }








        //  questionm.setDisplayText(Qus);

        System.out.println("QUESTIONWITHOUT"+Qus);
        Qust = Qus.trim();


        String  firstFourCharscheck = Qust.substring(0, 7);

        if (firstFourCharscheck.equals("https://")){


            String[] splits0 = Qust.split(".png");
            System.out.println("splits0.size: " + splits0.length);


            if (splits0.length==1){
                String imageonly = splits0[0];
                String setimage=imageonly+".png";
                System.out.println("splits0"+imageonly);

                String      input1 = setimage.trim();

                qimage.setVisibility(View.VISIBLE);
                Glide.with(singlequstionevent.this).load(input1)
                        .apply(new RequestOptions()
                                .override(Target.SIZE_ORIGINAL)
                                .format(DecodeFormat.PREFER_ARGB_8888))
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                        .into(qimage);


                qimage2.setVisibility(View.GONE);
                qimage3.setVisibility(View.GONE);
                qimage4.setVisibility(View.GONE);
                questionm.setVisibility(View.GONE);
                questionm2.setVisibility(View.GONE);
                questionm5.setVisibility(View.GONE);
                questionm3.setVisibility(View.GONE);
                questionm4.setVisibility(View.GONE);


            }
            if (splits0.length==2){

                String firstsub = splits0[0];

                String secondsub = splits0[1];



                System.out.println("splits020"+firstsub);
                System.out.println("splits021"+secondsub);
                String setimage=firstsub+".png";
                String      input01 = setimage.trim();
                String[] splits22 = secondsub.split("https://");

                if(splits22.length==1){
                    String firstsub221 = splits22[0];


                    System.out.println("firstsub221"+firstsub221);

                    System.out.println("input01"+input01);



                    qimage.setVisibility(View.VISIBLE);
                    questionm2.setVisibility(View.VISIBLE);
                    questionm2.setDisplayText(firstsub221);
                    Glide.with(singlequstionevent.this).load(input01)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);


                    qimage2.setVisibility(View.GONE);
                    qimage3.setVisibility(View.GONE);
                    qimage4.setVisibility(View.GONE);
                    questionm.setVisibility(View.GONE);

                    questionm5.setVisibility(View.GONE);
                    questionm3.setVisibility(View.GONE);
                    questionm4.setVisibility(View.GONE);



                } if(splits22.length==2){
                    String firstsub22 = splits22[0];
                    String secondsub22 = splits22[1];


                    System.out.println("firstsub222"+firstsub22);
                    System.out.println("firstsub222"+secondsub22);

                    String IMAGEset="https://"+secondsub22+".png";
                    String      input2 = IMAGEset.trim();

                    qimage.setVisibility(View.VISIBLE);
                    questionm2.setVisibility(View.VISIBLE);
                    questionm2.setDisplayText(firstsub22);
                    Glide.with(singlequstionevent.this).load(input01)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);

                    Glide.with(singlequstionevent.this).load(input2)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage2);
                    qimage2.setVisibility(View.VISIBLE);
                    qimage3.setVisibility(View.GONE);
                    qimage4.setVisibility(View.GONE);
                    questionm.setVisibility(View.GONE);
                    questionm5.setVisibility(View.GONE);

                    questionm3.setVisibility(View.GONE);
                    questionm4.setVisibility(View.GONE);






                }




            }
            if (splits0.length==3) {

                String splits03 = splits0[0];

                String splits032 = splits0[1];
                String splits033 = splits0[2];

                System.out.println("splits3"+splits03);
                System.out.println("splits32"+splits032);
                System.out.println("splits33"+splits033);

                String[] splits31 = splits032.split("https://");
                System.out.println("splits.size: " + splits31.length);
                System.out.println("IMAGEITII1"+splits03);
                System.out.println("IMAGEITII3"+splits033);


                String fimage=splits03+".png";


                String      input1 = fimage.trim();


                if (splits31.length==1){
                    String textonly = splits31[0];

                    System.out.println("splits311"+textonly);
                } if (splits31.length==2){

                    System.out.println("check2");
                    String textonly = splits31[0];
                    String imageonlys = splits31[1];

                    System.out.println("splits3121"+textonly);

                    System.out.println("splits3122"+imageonlys);



                    System.out.println("String str starts with quick: "+splits033.startsWith("https"));


                    if (splits033.startsWith("https")==true){
                        System.out.println("check3");

                        String limage=splits033+".png";
                        String      input3 = limage.trim();


                        String imageset="https://"+imageonlys+".png";
                        String      input2 = imageset.trim();


                        qimage2.setVisibility(View.VISIBLE);
                        qimage3.setVisibility(View.VISIBLE);
                        qimage4.setVisibility(View.GONE);
                        questionm.setVisibility(View.GONE);
                        questionm2.setVisibility(View.VISIBLE);

                        questionm3.setVisibility(View.GONE);
                        questionm4.setVisibility(View.GONE);
                        qimage.setVisibility(View.VISIBLE);

                        questionm5.setVisibility(View.GONE);



                        Glide.with(singlequstionevent.this).load(input1)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage);
                        questionm2.setDisplayText(textonly);
                        Glide.with(singlequstionevent.this).load(input2)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage2);

                        Glide.with(singlequstionevent.this).load(input3)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage3);


                    }else{

                        System.out.println("check2");
                        String imageset="https://"+imageonlys+".png";
                        String      input21 = imageset.trim();
                        System.out.println("image1"+input1);
                        Glide.with(singlequstionevent.this).load(input1)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage);

                        questionm2.setVisibility(View.VISIBLE);
                        questionm2.setDisplayText(textonly);

                        System.out.println("image2"+input21);
                        Glide.with(singlequstionevent.this).load(input21)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage2);


                        questionm3.setVisibility(View.GONE);
                        questionm3.setDisplayText(splits033);


                        String imagesetss=splits033+".png";


                        String      input212s = imagesetss.trim();
                        System.out.println("IMAGE3"+input212s);
                        Glide.with(singlequstionevent.this).load(input212s)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage3);
                        qimage2.setVisibility(View.VISIBLE);
                        qimage3.setVisibility(View.VISIBLE);
                        qimage4.setVisibility(View.GONE);
                        questionm.setVisibility(View.GONE);
                        questionm2.setVisibility(View.VISIBLE);

                        questionm3.setVisibility(View.GONE);
                        questionm4.setVisibility(View.GONE);
                        qimage.setVisibility(View.VISIBLE);

                        questionm5.setVisibility(View.GONE);


                    }





                }



            }

            if(splits0.length==4){


                String imageonly = splits0[0];

                String imageonly2 = splits0[1];
                String imageonly3 = splits0[2];
                String imageonly4 = splits0[3];
                System.out.println("imageonly"+imageonly);

                System.out.println("imageonly2"+imageonly2);


                System.out.println("imageonly3"+imageonly3);

                System.out.println("imageonly4"+imageonly4);

                String[] splits41 = imageonly4.split("https://");
                System.out.println("splits.size: " + splits41.length);

                if (splits41.length==1){
                    String textonly = splits41[0];
                    System.out.println("splits041"+textonly);


                    String fimage=imageonly+".png";
                    String fimage2=imageonly2+".png";
                    String fimage3=imageonly3+".png";

                    String      input1 = fimage.trim();
                    String      input2 = fimage2.trim();
                    String      input3 = fimage3.trim();



                    Glide.with(singlequstionevent.this).load(input1)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);

                    Glide.with(singlequstionevent.this).load(input2)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage2);

                    Glide.with(singlequstionevent.this).load(input3)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage3);


                    questionm4.setVisibility(View.VISIBLE);
                    questionm4.setDisplayText(textonly);
                    qimage3.setVisibility(View.VISIBLE);
                    qimage2.setVisibility(View.VISIBLE);
                    qimage.setVisibility(View.VISIBLE);

                    qimage4.setVisibility(View.GONE);



                    questionm.setVisibility(View.GONE);
                    questionm2.setVisibility(View.GONE);

                    questionm3.setVisibility(View.GONE);

                    qimage.setVisibility(View.VISIBLE);

                    questionm5.setVisibility(View.GONE);




                } if (splits41.length==2){



                    String textonly = splits41[0];
                    String imageonlys = splits41[1];

                    System.out.println("splits041"+textonly);
                    System.out.println("splits042"+imageonlys);




                    if (textonly.equals("")){



                        String fimage=imageonly+".png";
                        String fimage2=imageonly2+".png";
                        String fimage3=imageonly3+".png";

                        String      input1 = fimage.trim();
                        String      input2 = fimage2.trim();
                        String      input3 = fimage3.trim();

                        String imageset="https://"+imageonlys+".png";

                        String      input4 = imageset.trim();


                        Glide.with(singlequstionevent.this).load(input1)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage);

                        Glide.with(singlequstionevent.this).load(input2)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage2);

                        Glide.with(singlequstionevent.this).load(input3)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage3);
                        Glide.with(singlequstionevent.this).load(input4)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage4);

                        questionm4.setVisibility(View.GONE);
                        questionm4.setDisplayText(textonly);

                        questionm2.setVisibility(View.GONE);

                        questionm3.setVisibility(View.GONE);
                        questionm5.setVisibility(View.GONE);
                        questionm.setVisibility(View.GONE);






                        qimage4.setVisibility(View.VISIBLE);

                        qimage3.setVisibility(View.VISIBLE);
                        qimage2.setVisibility(View.VISIBLE);
                        qimage.setVisibility(View.VISIBLE);


                    }else{




                        String fimage=imageonly+".png";
                        String fimage2=imageonly2+".png";
                        String fimage3=imageonly3+".png";

                        String      input1 = fimage.trim();
                        String      input2 = fimage2.trim();
                        String      input3 = fimage3.trim();

                        String imageset="https://"+imageonlys+".png";

                        String      input4 = imageset.trim();


                        Glide.with(singlequstionevent.this).load(input1)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage);

                        Glide.with(singlequstionevent.this).load(input2)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage2);

                        Glide.with(singlequstionevent.this).load(input3)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage3);
                        Glide.with(singlequstionevent.this).load(input4)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage4);

                        questionm4.setVisibility(View.VISIBLE);
                        questionm4.setDisplayText(textonly);

                        questionm2.setVisibility(View.GONE);

                        questionm3.setVisibility(View.GONE);
                        questionm5.setVisibility(View.GONE);
                        questionm.setVisibility(View.GONE);






                        qimage4.setVisibility(View.VISIBLE);

                        qimage3.setVisibility(View.VISIBLE);
                        qimage2.setVisibility(View.VISIBLE);
                        qimage.setVisibility(View.VISIBLE);



                    }











                }










            }




        }else {
            String[] splits1 = Qust.split("https://");

            System.out.println("splits1.size: " + splits1.length);

            if(splits1.length==1){

                String TEXTONLY1 = splits1[0];


                System.out.println(TAG+"TEXTONLY"+TEXTONLY1);



                qimage2.setVisibility(View.GONE);
                qimage3.setVisibility(View.GONE);
                qimage4.setVisibility(View.GONE);
                questionm.setVisibility(View.VISIBLE);
                questionm2.setVisibility(View.GONE);

                questionm3.setVisibility(View.GONE);
                questionm4.setVisibility(View.GONE);
                qimage.setVisibility(View.GONE);

                questionm5.setVisibility(View.GONE);
                questionm.setDisplayText(TEXTONLY1);
            }
            if(splits1.length==2){

                String firstSubStrings = splits1[0];
                String secondSubStrings = splits1[1];

                System.out.println("textandimagecehck1"+firstSubStrings+"-----------"+secondSubStrings);
                String[] splits12 = secondSubStrings.split(".png");
                System.out.println("splits.size: " + splits12.length);

                if(splits12.length==1){
                    String firstSubStringss = splits12[0];
                    System.out.println("TEXTIMAGECEHCK2"+firstSubStringss);

                    String setimage="https://"+firstSubStringss+".png";

                    String      input2 = setimage.trim();
                    qimage2.setVisibility(View.GONE);
                    qimage3.setVisibility(View.GONE);
                    qimage4.setVisibility(View.GONE);
                    questionm.setVisibility(View.VISIBLE);
                    questionm2.setVisibility(View.GONE);
                    questionm5.setVisibility(View.GONE);
                    questionm3.setVisibility(View.GONE);
                    questionm4.setVisibility(View.GONE);
                    qimage.setVisibility(View.VISIBLE);



                    questionm.setDisplayText(firstSubStrings);

                    Glide.with(singlequstionevent.this).load(input2)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);

                }

                if(splits12.length==2){

                    String firstSubStringss = splits12[0];
                    String secondSubStringss = splits12[1];
                    String IMAGEset="https://"+firstSubStringss+".png";

                    System.out.println("IMAGEset12"+IMAGEset);

                    System.out.println("TEXTSET12"+secondSubStringss);


                    qimage2.setVisibility(View.GONE);
                    qimage3.setVisibility(View.GONE);
                    qimage4.setVisibility(View.GONE);
                    questionm.setVisibility(View.VISIBLE);
                    questionm2.setVisibility(View.VISIBLE);
                    questionm5.setVisibility(View.GONE);
                    questionm3.setVisibility(View.GONE);
                    questionm4.setVisibility(View.GONE);
                    qimage.setVisibility(View.VISIBLE);


                    String      input1 = IMAGEset.trim();
                    questionm.setDisplayText(firstSubStrings);

                    Glide.with(singlequstionevent.this).load(input1)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);
                    questionm2.setDisplayText(secondSubStringss);


                }

            }
            if(splits1.length==3){


                String firstSubStrings = splits1[0];
                String secondSubStrings = splits1[1];
                String tiredSubStrings = splits1[2];
                System.out.println("31"+firstSubStrings);
                System.out.println("32"+secondSubStrings);
                System.out.println("33"+tiredSubStrings);
                String[] splits31 = tiredSubStrings.split(".png");
                System.out.println("splits31.size: " + splits31.length);


                String imagespace = secondSubStrings.trim();

                String[] splitstwo1 = imagespace.split(".png");

                System.out.println("split2s.size: " + splitstwo1.length);

                if (splitstwo1.length==1){
                    String one = splitstwo1[0];
                    System.out.println("+++"+one);

                    questionm.setVisibility(View.VISIBLE);
                    qimage2.setVisibility(View.VISIBLE);
                    qimage3.setVisibility(View.GONE);
                    qimage4.setVisibility(View.GONE);

                    questionm2.setVisibility(View.GONE);
                    questionm5.setVisibility(View.GONE);
                    questionm3.setVisibility(View.GONE);
                    questionm4.setVisibility(View.GONE);
                    qimage.setVisibility(View.VISIBLE);


                    String imageset1="https://"+one+".png";
                    String  input312 = imageset1.trim();


                    Glide.with(singlequstionevent.this).load(input312)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);





                }
                if (splitstwo1.length==2){
                    String one = splitstwo1[0];
                    String onetwo = splitstwo1[1];
                    System.out.println("+++"+one+"--------"+onetwo);

                    String imageset1="https://"+one+".png";
                    input31 = imageset1.trim();




                    qimage2.setVisibility(View.VISIBLE);
                    qimage3.setVisibility(View.GONE);
                    qimage4.setVisibility(View.GONE);
                    questionm.setVisibility(View.VISIBLE);
                    questionm2.setVisibility(View.VISIBLE);
                    questionm5.setVisibility(View.GONE);
                    questionm3.setVisibility(View.GONE);
                    questionm4.setVisibility(View.GONE);
                    qimage.setVisibility(View.VISIBLE);





                    Glide.with(singlequstionevent.this).load(input31)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);



                    questionm2.setDisplayText(onetwo);

//                    if (onetwo.equals("")){
//
//                        questionm2.setDisplayText(onetwo);
//                    }else {
//
//                    }



                }



                if (splits31.length==1){
                    String firstSubStringss = splits31[0];
                    String SETIMAGE="https://"+firstSubStringss+".png";
                    String SETIMAGE2="https://"+secondSubStrings;

                    System.out.println("splits311"+SETIMAGE);
                    String      input = SETIMAGE.trim();
                    String      input2 = SETIMAGE2.trim();
                    qimage2.setVisibility(View.VISIBLE);
                    qimage3.setVisibility(View.GONE);
                    qimage4.setVisibility(View.GONE);
                    questionm.setVisibility(View.VISIBLE);
                    questionm2.setVisibility(View.GONE);
                    questionm5.setVisibility(View.GONE);
                    questionm3.setVisibility(View.GONE);
                    questionm4.setVisibility(View.GONE);
                    qimage.setVisibility(View.VISIBLE);



                    questionm.setDisplayText(firstSubStrings);

//                    Glide.with(Singlequestion.this).load(input2)
//                            .apply(new RequestOptions()
//                                    .override(Target.SIZE_ORIGINAL)
//                                    .format(DecodeFormat.PREFER_ARGB_8888))
//                            .skipMemoryCache(true) //2
//                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
//                            .into(qimage);
                    Glide.with(singlequstionevent.this).load(input2)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage2);



                }if (splits31.length==2){
                    String firstSubStringss = splits31[0];
                    String secondSubStringss = splits31[1];
                    String SETIMAGE="https://"+firstSubStringss+".png";

                    String settext=secondSubStringss;

                    System.out.println("splits312"+SETIMAGE+"TEXT"+settext);
                    String      input = SETIMAGE.trim();

                    qimage2.setVisibility(View.VISIBLE);
                    qimage3.setVisibility(View.GONE);
                    qimage4.setVisibility(View.GONE);
                    questionm.setVisibility(View.VISIBLE);
                    questionm2.setVisibility(View.VISIBLE);
                    questionm5.setVisibility(View.GONE);
                    questionm3.setVisibility(View.VISIBLE);
                    questionm4.setVisibility(View.GONE);
                    qimage.setVisibility(View.VISIBLE);




                    questionm.setDisplayText(firstSubStrings);
                    questionm2.setDisplayText(firstSubStrings);

                    Glide.with(singlequstionevent.this).load(input31)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);
                    Glide.with(singlequstionevent.this).load(input)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage2);



                    questionm3.setDisplayText(secondSubStringss);






                }



            }
            if(splits1.length==4){


                String firstSubStrings = splits1[0];
                String secondSubStrings = splits1[1];
                String tiredSubStrings = splits1[2];
                String forSubStrings = splits1[3];

                System.out.println("splits14"+firstSubStrings);
                System.out.println("splits14"+secondSubStrings);
                System.out.println("splits14"+tiredSubStrings);
                System.out.println("splits14"+forSubStrings);


                String imageset1="https://"+secondSubStrings;
                String      input1 = imageset1.trim();
//
                String imageset2="https://"+tiredSubStrings;
                String      input2 = imageset2.trim();

                String[] splits41 = forSubStrings.split(".png");
                System.out.println("splits41.size: " + splits41.length);


                if (splits41.length==1){
                    String firstSubStringss = splits41[0];
                    String SETIMAGE="https://"+firstSubStringss+".png";

                    String      input = SETIMAGE.trim();
                    qimage2.setVisibility(View.VISIBLE);
                    qimage3.setVisibility(View.VISIBLE);
                    qimage4.setVisibility(View.GONE);
                    questionm.setVisibility(View.VISIBLE);
                    questionm2.setVisibility(View.GONE);
                    questionm5.setVisibility(View.GONE);
                    questionm3.setVisibility(View.GONE);
                    questionm4.setVisibility(View.GONE);
                    qimage.setVisibility(View.VISIBLE);




                    questionm.setDisplayText(firstSubStrings);

                    Glide.with(singlequstionevent.this).load(input1)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);
                    Glide.with(singlequstionevent.this).load(input2)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage2);

                    Glide.with(singlequstionevent.this).load(input)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage3);



                    System.out.println("splits41"+firstSubStringss);

                }if (splits41.length==2){
                    String firstSubStringss = splits41[0];
                    String secondSubStringss = splits41[1];

                    System.out.println("splits42"+firstSubStringss);
                    System.out.println("splits42"+secondSubStringss);



                    String settext=secondSubStringss;
                    String SETIMAGE="https://"+firstSubStringss+".png";

                    String      input = SETIMAGE.trim();

                    qimage2.setVisibility(View.VISIBLE);
                    qimage3.setVisibility(View.VISIBLE);
                    qimage4.setVisibility(View.GONE);
                    questionm.setVisibility(View.VISIBLE);
                    questionm2.setVisibility(View.GONE);
                    questionm5.setVisibility(View.GONE);
                    questionm3.setVisibility(View.GONE);
                    questionm4.setVisibility(View.VISIBLE);
                    qimage.setVisibility(View.VISIBLE);




                    questionm.setDisplayText(firstSubStrings);
                    questionm4.setDisplayText(settext);

                    Glide.with(singlequstionevent.this).load(input1)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);
                    Glide.with(singlequstionevent.this).load(input2)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage2);

                    Glide.with(singlequstionevent.this).load(input)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage3);






                }



            }
            if(splits1.length==5){

                String firstSubStrings = splits1[0];
                String secondSubStrings = splits1[1];
                String tiredSubStrings = splits1[2];
                String forSubStrings = splits1[3];
                String fiveSubStrings = splits1[4];

//TEXT
                System.out.println("50"+firstSubStrings);



                //IMAGE
                System.out.println("50"+secondSubStrings);
                String      input1 ="https://"+secondSubStrings.trim();

                //IMAGE
                System.out.println("50"+tiredSubStrings);
                String      input2 ="https://"+tiredSubStrings.trim();
                //IMAGE
                System.out.println("50"+forSubStrings);
                String      input3 = "https://"+forSubStrings.trim();
                //IMASGEAND TEXT
                System.out.println("50"+fiveSubStrings);

                String[] splits51 = fiveSubStrings.split(".png");
                System.out.println("splits.size: " + splits51.length);

                if (splits51.length==1){
                    String firstSubStringss = splits51[0];
                    System.out.println("splits511"+firstSubStringss);
                    String SETIMAGE="https://"+firstSubStringss+".png";
                    String      input4 = SETIMAGE.trim();

                    questionm.setVisibility(View.VISIBLE);
                    qimage.setVisibility(View.VISIBLE);
                    qimage2.setVisibility(View.VISIBLE);
                    qimage3.setVisibility(View.VISIBLE);
                    qimage4.setVisibility(View.VISIBLE);
                    questionm2.setVisibility(View.GONE);

                    questionm3.setVisibility(View.GONE);
                    questionm4.setVisibility(View.GONE);
                    questionm5.setVisibility(View.GONE);


                    questionm.setDisplayText(firstSubStrings);

                    Glide.with(singlequstionevent.this).load(input1)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);

                    Glide.with(singlequstionevent.this).load(input2)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage2);

                    Glide.with(singlequstionevent.this).load(input3)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage3);


                    Glide.with(singlequstionevent.this).load(input4)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage4);





                }if(splits51.length==2){
                    String firstSubStringss = splits51[0];
                    String secondSubStringss = splits51[1];



                    questionm.setVisibility(View.VISIBLE);
                    qimage.setVisibility(View.VISIBLE);
                    qimage2.setVisibility(View.VISIBLE);
                    qimage3.setVisibility(View.VISIBLE);
                    qimage4.setVisibility(View.VISIBLE);
                    questionm2.setVisibility(View.GONE);

                    questionm3.setVisibility(View.GONE);
                    questionm4.setVisibility(View.GONE);
                    questionm5.setVisibility(View.VISIBLE);
                    questionm.setDisplayText(firstSubStrings);


                    String SETIMAGE="https://"+firstSubStringss+".png";
                    String      input4 = SETIMAGE.trim();



                    Glide.with(singlequstionevent.this).load(input1)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);

                    Glide.with(singlequstionevent.this).load(input2)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage2);

                    Glide.with(singlequstionevent.this).load(input3)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage3);


                    Glide.with(singlequstionevent.this).load(input4)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage4);




                    questionm5.setDisplayText(secondSubStringss);



//IMAGE
                    System.out.println("splits512"+firstSubStringss);

                    //TEXT

                    System.out.println("splits512"+secondSubStringss);





                }


            }



        }





















        Qno.setText(Qusno+"/"+ID);

        txtQuestion.setText(Qus);

        sub.setText("SUBJECT - "+subjectName);
        chap.setText("CHAPTER - "+chapterName);
        Single.setText(" Single Choice Question");












        Answer=answer;
        Explain=explain;
        System.out.println("set"+Qusno+"."+Qus+""+explain);
        radioGroup.clearCheck();


        if(quid==1){

            butprev.setVisibility(View.GONE);

        }else{
            butprev.setVisibility(View.VISIBLE);
        }



    }

    private static String convertStringArrayToString(String[] strArr, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (String str : strArr)
            sb.append(str).append(delimiter);
        return sb.substring(0, sb.length() - 1);
    }

    private void setQuestionView(String questionid) {



        try {


            System.out.println("setview"+quid);
            dbHelper.CreateTable(4);
            cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.questionresponse + " where Questionno='"+questionid+"'", null);
            System.out.println(TAG + " getValueFromquestion count " + cursor.getCount());
            answercount=cursor.getCount();

            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {

                    Qusno = "";
                    answer = "";
                    wrong="";
                    Qus="";
                    opt1="";
                    opt2="";
                    opt3="";
                    opt4="";
                    explain="";
                    subjectName="";
                    chapterName="";
                    sectionid="";

//

                    do {
                        Qusno = cursor.getString(cursor.getColumnIndex("Questionno"));
                        Qus = cursor.getString(cursor.getColumnIndex("Question"));
                        opt1 = cursor.getString(cursor.getColumnIndex("option1"));
                        opt2 = cursor.getString(cursor.getColumnIndex("option2"));
                        opt3 = cursor.getString(cursor.getColumnIndex("option3"));
                        opt4 = cursor.getString(cursor.getColumnIndex("option4"));
                        answer = cursor.getString(cursor.getColumnIndex("Answer"));
                        explain = cursor.getString(cursor.getColumnIndex("Explanation"));
                        subjectName = cursor.getString(cursor.getColumnIndex("subjectName"));
                        chapterName = cursor.getString(cursor.getColumnIndex("chapterName"));

                        sectionid = cursor.getString(cursor.getColumnIndex("sectionid"));
                        System.out.println(TAG + " From Db " + answer+""+Qusno+""+wrong+sectionid);








                    } while (cursor.moveToNext());










                }




            }
        } catch (Exception e) {
            System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
        }









    }

    private void getValues() {
        Bundle b = getIntent().getExtras();
        if (b != null) {


            ID = b.getString("Qcount");
            IDs = b.getString("Id");

            time = b.getString("time");
            isCorrect= b.getString("isCorrect");
            isWrong= b.getString("isWrong");
            isSkip= b.getString("isSkip");

            isAll=b.getString("isAll");
            testName = b.getString("testName");


            System.out.println(TAG + " getValues " + b);





        }
    }



    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    //Start Countodwn method
//    private void startTimer(int noOfMinutes) {
//        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
//            public void onTick(long millisUntilFinished) {
//                long millis = millisUntilFinished;
//                //Convert milliseconds into hour,minute and seconds
//                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
//               // System.out.println("timers2"+hms);
//                String time=hms;
//                System.out.println("hma"+time+hms);
//                countdownTimerText.setText(time);//set text
//            }
//
//            public void onFinish() {
//
//                countdownTimerText.setText("TIME'S UP!!"); //On finish change timer text
//                countDownTimer = null;//set CountDownTimer to null
//
//
//
//                if(countdownTimerText.getText().equals("TIME'S UP!!")){
//                   // alertshow();
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(
//                            Singlequestion.this);
//                    builder.setTitle(" Alert");
//                    builder.setMessage("TIME'S UP!!");
//                    builder.setPositiveButton("OK",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog,
//                                                    int which) {
//
//
//
//                                    stopCountdown();
//                                    Intent intent = new Intent(Singlequestion.this, Resultpage.class);
//
//                                    intent.putExtra("count",ID);
//
//                                    intent.putExtra("ID",IDs);
//nt
//                                    intent.putExtra("timer",timern);
//                                    intent.putExtra("Answer",Answer);
//                                    intent.putExtra("Wrong",Wrong);
//                                    intent.putExtra("LEFTANSWER",LEFTANSWER);
//
//                                    startActivity(intent);
//                                    finish();
//
//                                    dbHelper.vatbook.execSQL("delete from " + dbHelper.questionresponse);
//                                }
//                            });
//                    builder.show();
//                }else {
//
//                }
//
//
//
//
//               // alertshow();
//              //  startTimer.setText(getString(R.string.start_timer));//Change button text
//            }
//        }.start();
//
//    }

    private void alertshow() {

        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        // custom dialog
        dialog = new Dialog(singlequstionevent.this);
        dialog.setContentView(R.layout.my_dialog);
        Button dialogButton = (Button) dialog.findViewById(R.id.buttonOk);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                dialog.dismiss();

            }
        });
        dialog.show();
    }


    private boolean tableExists(SQLiteDatabase vatbook, String selctedlist) {


        if (selctedlist == null || vatbook == null || !vatbook.isOpen())
        {

            System.out.println("nulldatabase alll");
            return false;
        }
        Cursor cursor = vatbook.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", selctedlist});
        if (!cursor.moveToFirst())
        {
            System.out.println("nulldatabase table selected");
            cursor.close();
            return false;
        }
        System.out.println("nulldatabase alll not ");
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    class Radio_check implements  CompoundButton.OnCheckedChangeListener
    {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(radio1.isChecked())
            {
                String Qno=Qusno;
                String selected=radio1.getText().toString();
                if(selected.equals(answer)) {

                    System.out.println("Radiocheckcorrect1");


                    String co = "correct";
                    String Acoun = "1";
                    String IsCorrect="YES";
                    dbHelper.CreateTable(6);
                    dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set correctanser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "' , isCorrect = '" + IsCorrect + "'where Questionno='" + Qno + "'");
                    System.out.println("correct"+selected);
                    System.out.println("update " + dbHelper.selctedlist + " set correctanser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "' where Questionno='" + Qno + "'");
                } else{
                    System.out.println("Radiocheckwrong1");
                    String was="wronganswer";

                    String Acoun="0";
                    String IsCorrect="NO";

                    dbHelper.CreateTable(6);
                    dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set wronganser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "' , isCorrect = '" + IsCorrect + "' where Questionno='" + Qno + "'");
                    System.out.println("wronganswer"+selected);
                    System.out.println("update " + dbHelper.selctedlist + " set wronganser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "' where Questionno='" + Qno + "'");
                }



            }
            else if(radio2.isChecked())
            {
                String Qno=Qusno;
                String selected=radio2.getText().toString();
                if(selected.equals(answer)) {

                    System.out.println("Radiocheckcorrect2");


                    String co = "correct";
                    String Acoun = "1";
                    String IsCorrect="YES";
                    dbHelper.CreateTable(6);
                    dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set correctanser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "', isCorrect = '" + IsCorrect + "' where Questionno='" + Qno + "'");
                    System.out.println("correct"+selected);
                    System.out.println("update " + dbHelper.selctedlist + " set correctanser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "', isCorrect = '" + IsCorrect +"' where Questionno='" + Qno + "'");

                } else{
                    System.out.println("Radiocheckwrong2");
                    String was="wronganswer";

                    String Acoun="0";
                    String IsCorrect="NO";
                    dbHelper.CreateTable(6);
                    dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set wronganser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "' , isCorrect = '" + IsCorrect + "'where Questionno='" + Qno + "'");
                    System.out.println("wronganswer"+selected);
                    System.out.println("update " + dbHelper.selctedlist + " set wronganser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "' where Questionno='" + Qno + "'");

                }
            }
            else if(radio3.isChecked())
            {
                String Qno=Qusno;
                String selected=radio3.getText().toString();
                if(selected.equals(answer)) {

                    System.out.println("Radiocheckcorrect3");


                    String co = "correct";
                    String Acoun = "1";
                    String IsCorrect="YES";
                    dbHelper.CreateTable(6);
                    dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set correctanser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "', isCorrect = '" + IsCorrect + "' where Questionno='" + Qno + "'");
                    System.out.println("correct"+selected);
                    System.out.println("update " + dbHelper.selctedlist + " set correctanser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "', isCorrect = '" + IsCorrect + "' where Questionno='" + Qno + "'");

                } else{
                    System.out.println("Radiocheckwrong3");
                    String was="wronganswer";

                    String Acoun="0";
                    String IsCorrect="NO";
                    dbHelper.CreateTable(6);
                    dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set wronganser ='" + selected + "', selectedanser = '" +selected+ "', Acount = '" + Acoun + "' , isCorrect = '" + IsCorrect + "'where Questionno='" + Qno + "'");
                    System.out.println("wronganswer"+selected);
                    System.out.println("update " + dbHelper.selctedlist + " set wronganser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "' where Questionno='" + Qno + "'");

                }
            }
            else if(radio4.isChecked())
            {
                String Qno=Qusno;
                String selected=radio4.getText().toString();
                if(selected.equals(answer)) {

                    System.out.println("Radiocheckcorrect4");


                    String co = "correct";
                    String Acoun = "1";
                    String IsCorrect="YES";
                    dbHelper.CreateTable(6);
                    dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set correctanser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "', isCorrect = '" + IsCorrect + "' where Questionno='" + Qno + "'");
                    System.out.println("correct"+selected);
                    System.out.println("update " + dbHelper.selctedlist + " set correctanser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "' where Questionno='" + Qno + "'");

                } else{
                    System.out.println("Radiocheckwrong4");
                    String was="wronganswer";

                    String Acoun="0";
                    String IsCorrect="NO";
                    dbHelper.CreateTable(6);
                    dbHelper.vatbook.execSQL("update " + dbHelper.selctedlist + " set wronganser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "', isCorrect = '" + IsCorrect + "' where Questionno='" + Qno + "'");
                    System.out.println("wronganswer"+selected);
                    System.out.println("update " + dbHelper.selctedlist + " set wronganser ='" + selected + "', selectedanser = '" + selected + "', Acount = '" + Acoun + "' where Questionno='" + Qno + "'");

                }
            }
        }
    }





    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateWatchInterface();
            }
        }.start();

        mTimerRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateWatchInterface();
    }

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        countdownTimerText.setText(timeLeftFormatted);
        System.out.println("timeLeftFormatted"+timeLeftFormatted);




        if (countdownTimerText.getText().equals("0:00:01")){
            countdownTimerText.setText("TIME'S UP!!");
        }else if (countdownTimerText.getText().equals("00:01")){
            countdownTimerText.setText("TIME'S UP!!");


            AlertDialog.Builder builder = new AlertDialog.Builder(
                    singlequstionevent.this);
            builder.setTitle(" Alert");
            builder.setMessage("TIME'S UP!!");
            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {



                            //stopCountdown();




                            crom.stop();
                            timern=crom.getText().toString();

                            stopCountdown();

                            resetTimer();
                            System.out.println("timers"+timern);


                            String Anwercount="1";



                            try {

                                cartArrayList = new ArrayList<HashMap<String, String>>();
                                dbHelper.CreateTable(6);
                                cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.selctedlist + " where Acount='"+Anwercount+"'", null);
                                System.out.println(TAG + " getValueanswer count " + cursor.getCount());
                                answercount=cursor.getCount();

                                if (cursor.getCount() > 0) {
                                    if (cursor.moveToFirst()) {

                                        Qusno = "";
                                        answer = "";
                                        wrong="";
//

                                        do {
                                            Qusno = cursor.getString(cursor.getColumnIndex("Questionno"));
                                            answer = cursor.getString(cursor.getColumnIndex("correctanser"));
                                            wrong = cursor.getString(cursor.getColumnIndex("selectedanser"));
//                        str_itemIndex = cursor.getString(cursor.getColumnIndex("itemIndex"));
//                        str_itemQty = cursor.getString(cursor.getColumnIndex("itemQty"));
//                        str_itemVat = cursor.getString(cursor.getColumnIndex("itemVat"));
//                        str_itemPrice = cursor.getString(cursor.getColumnIndex("itemPrice"));
//                        str_itemVatAmt = cursor.getString(cursor.getColumnIndex("itemVatAmt"));
                                            System.out.println(TAG + " From Db " + answer+""+Qusno+"");




                                            map = new HashMap<String, String>();
                                            map.put(Constants.HM_ITEMCODE, Qusno);
                                            map.put(Constants.HM_ITEMNAME, answer);
//                        map.put(Constants.HM_ITEMINDEX, str_itemIndex);
//                        map.put(Constants.HM_ITEMPRICE, str_itemPrice);
//                        map.put(Constants.HM_VATPERCENT, str_itemVat);
//                        map.put(Constants.HM_QTY, str_itemQty);
//                        map.put(Constants.HM_VATAMT, str_itemVatAmt);
                                            cartArrayList.add(map);
                                            System.out.println("correct"+map);

                                        } while (cursor.moveToNext());










                                    }




                                }
                            } catch (Exception e) {
                                System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
                            }







                            String Anwercounts="0";



                            try {

                                dbHelper.CreateTable(6);
                                cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.selctedlist + " where Acount='"+Anwercounts+"'", null);
                                System.out.println(TAG + " getValueFromwrong count " + cursor.getCount());
                                wrongcount=cursor.getCount();

                                if (cursor.getCount() > 0) {
                                    if (cursor.moveToFirst()) {

                                        Qusno = "";
                                        answer = "";
                                        wrongs="";
//

                                        do {
                                            Qusno = cursor.getString(cursor.getColumnIndex("Questionno"));
                                            //answer = cursor.getString(cursor.getColumnIndex("correctanser"));
                                            wrongs = cursor.getString(cursor.getColumnIndex("wronganser"));
//                        str_itemIndex = cursor.getString(cursor.getColumnIndex("itemIndex"));
//                        str_itemQty = cursor.getString(cursor.getColumnIndex("itemQty"));
//                        str_itemVat = cursor.getString(cursor.getColumnIndex("itemVat"));
//                        str_itemPrice = cursor.getString(cursor.getColumnIndex("itemPrice"));
//                        str_itemVatAmt = cursor.getString(cursor.getColumnIndex("itemVatAmt"));
                                            System.out.println(TAG + " From Db " +""+wrongs+"");







                                        } while (cursor.moveToNext());










                                    }




                                }
                            } catch (Exception e) {
                                System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
                            }






                            System.out.println("corectandwrong--"+answercount+"----"+wrongcount);


                            int left=answercount+wrongcount;

                            int totals=
                                    Integer.parseInt(ID);

                            lefts=totals-left;
                            Answer= String.valueOf(answercount);
                            Wrong= String.valueOf(wrongcount);

                            LEFTANSWER= String.valueOf(lefts);











                            Intent intent = new Intent(singlequstionevent.this, saveanswerevent.class);

                            intent.putExtra("count",ID);

                            intent.putExtra("ID",IDs);

                            intent.putExtra("timer",timern);
                            intent.putExtra("Answer",Answer);
                            intent.putExtra("Wrong",Wrong);
                            intent.putExtra("LEFTANSWER",LEFTANSWER);

                            intent.putExtra("isCorrect",isCorrect);
                            intent.putExtra("isWrong",isWrong );
                            intent.putExtra("isSkip",isSkip );
                            intent.putExtra("isAll","YES" );
                            System.out.println("isall"+isAll);

                            System.out.println("wrong"+wrong);
                            startActivity(intent);
                            finish();

                            dbHelper.vatbook.execSQL("delete from " + dbHelper.questionresponse);
                        }
                    });
            builder.show();
        }else {

        }




    }

    private void updateWatchInterface() {


//
//            if (mTimeLeftInMillis < mStartTimeInMillis) {
//                // mButtonReset.setVisibility(View.VISIBLE);
//
//                countdownTimerText.setText("TIME'S UP!!");
//
//                if (countdownTimerText.getText().equals("TIME'S UP!!")){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(
//                            Singlequestion.this);
//                    builder.setTitle(" Alert");
//                    builder.setMessage("TIME'S UP!!");
//                    builder.setPositiveButton("OK",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog,
//                                                    int which) {
//
//
//
//                                    stopCountdown();
//                                    resetTimer();
//                                    Intent intent = new Intent(Singlequestion.this, Resultpage.class);
//
//                                    intent.putExtra("count",ID);
//
//                                    intent.putExtra("ID",IDs);
//
//                                    intent.putExtra("timer",timern);
//                                    intent.putExtra("Answer",Answer);
//                                    intent.putExtra("Wrong",Wrong);
//                                    intent.putExtra("LEFTANSWER",LEFTANSWER);
//
//                                    startActivity(intent);
//                                    finish();
//
//                                    dbHelper.vatbook.execSQL("delete from " + dbHelper.questionresponse);
//                                }
//                            });
//                    builder.show();
//
//                }
//
//            } else {
//                /// mButtonReset.setVisibility(View.INVISIBLE);
//            }

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateWatchInterface();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateWatchInterface();
            } else {
                startTimer();
            }
        }
    }



    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
deleteTable();
        Intent intent = new Intent(singlequstionevent.this, MainActivity.class); //OrderDetailsActivity



        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    private void deleteTable() {
        dbHelper.CreateTable(6);
        dbHelper.vatbook.execSQL("delete from " + dbHelper.selctedlist);
    }
}

