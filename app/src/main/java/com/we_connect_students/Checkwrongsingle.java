package com.we_connect_students;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.we_connect_students.DataBase.DBHelper;

import javax.xml.transform.Result;

import katex.hourglass.in.mathlib.MathView;

public class Checkwrongsingle extends AppCompatActivity {
    String TAG = "Checkwrongsingle";
    Toolbar toolbar;
    ActionBar actionBar = null;
    DBHelper dbHelper;
    Cursor cursor;


    Utilis utilis;
    private RadioGroup radioGroup;
    RadioButton radio1,radio2,radio3,radio4;
    TextView

            _tv,Qno,Single,countdownText;

    TextView txtQuestion,explains,opt,sub,chap;
    Button butNext,butprev,btsubmit;
    int quid = 1,wrongcount;
    public static String  Qusno = "",QusnoS="",
            answer = "",wrong="",result="",wresult="",NEWW="",Qust= "",input31="",
            Qus="",
            opt1="",
            opt2="",
            opt3="",
            opt4="",explain="",wronganswer="",WRONGANANWER="",subjectName="",chapterName="";
    String Answer="",Wrong="",LEFTANSWER="",Explain="",ID="",LASTID="",Toatal="",timer="",Answers="",Wrongs="",isCorrect= "",isSkip="",isWrong="",isAll="";
    LinearLayout op4,op3,op2,op1;

    MathView    radia_id1f1,radia_id1f2,radia_id1f3,radia_id1f4,questionm,questionm2,questionm3,questionm4,questionm5,explainationView;
    public static ImageView radio1image,radio2image,radio3image,radio4image,qimage4,qimage3,qimage2,qimage;
boolean isFlag;
LinearLayout explainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checksingle);


        Window window = Checkwrongsingle.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(Checkwrongsingle.this,R.color.my_statusbar_color1));



        dbHelper = new DBHelper(Checkwrongsingle.this);
        utilis = new Utilis(Checkwrongsingle.this);
        txtQuestion = (TextView)findViewById(R.id.question);
        questionm=findViewById(R.id.questionm);;
        Qno = (TextView)findViewById(R.id.qno);
        Single = (TextView)findViewById(R.id.single);

        sub = (TextView)findViewById(R.id.sub);
        chap = (TextView)findViewById(R.id.chap);
        countdownText=findViewById(R.id.countdownText);

        explainLayout = findViewById(R.id.explainLayout);

        initToolbar();

        questionm2=findViewById(R.id.questionm2);
        questionm3=findViewById(R.id.questionm3);
        questionm4=findViewById(R.id.question4);
        questionm5=findViewById(R.id.question5);
        //questionm=findViewById(R.id.questionm);

        qimage=findViewById(R.id.qimage);
        qimage2=findViewById(R.id.qimage2);
        qimage3=findViewById(R.id.qimage3);
        qimage4=findViewById(R.id.qimage4);

        butNext = (Button)findViewById(R.id.button1);
        butprev= (Button)findViewById(R.id.button2);
        btsubmit= (Button)findViewById(R.id.button3);
        radio1image=findViewById(R.id.radio1image);
        radio2image=findViewById(R.id.radio2image);
        radio3image=findViewById(R.id.radio3image);
        radio4image=findViewById(R.id.radio4image);










        views();
        String questionid=String.valueOf(quid);
        setQuestionView(questionid);

        getValues();
        countdownText.setText(timer);
        setlist();
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
        btsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });





        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioGroup.clearCheck();
                //radio1.setChecked(false);

                int result = Integer.parseInt(Toatal);

                if(quid<result){
                    quid++;
                    String questionid=String.valueOf(quid);
                    System.out.println("check"+questionid);
                    views();
                    setQuestionView(questionid);

                    setlist();

                    System.out.println("icremtrnt"+quid);

                }else{

                    Toast.makeText(Checkwrongsingle.this, "End of question!", Toast.LENGTH_SHORT).show();

                }
            }
        });


        butprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                radioGroup.clearCheck();
                int result = Integer.parseInt(Toatal);
//
//                    System.out.println("result"+result);
//                    System.out.println("qustiinid"+quid);
                if(quid<=result){


                    if(quid==1){
                        Toast.makeText(Checkwrongsingle.this, "No Recorded found", Toast.LENGTH_SHORT).show();

                        // butprev.setVisibility(View.GONE);

                    }else{





                        quid--;



                        String questionid=String.valueOf(quid);
                        System.out.println("decrent"+questionid);
                        views();
                        setQuestionView(questionid);

                        setlist();

                        System.out.println("decrement"+quid);

                    }


                }else{



                    Toast.makeText(Checkwrongsingle.this, "No Recorded found", Toast.LENGTH_SHORT).show();






                }

            }
        });
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

    private void views() {

        opt=findViewById(R.id.opt);
        explains=findViewById(R.id.explain);
        explainationView=findViewById(R.id.explainationView);
        op1=findViewById(R.id.op1);
        op2=findViewById(R.id.op2);
        op3=findViewById(R.id.op3);
        op4=findViewById(R.id.op4);
        radioGroup = (RadioGroup)findViewById(R.id.groupradio);

        radioGroup.clearCheck();

        radio1=findViewById(R.id.radia_id1);

        radio2=findViewById(R.id.radia_id2);
        radio3=findViewById(R.id.radia_id3);
        radio4=findViewById(R.id.radia_id4);



        radia_id1f1=findViewById(R.id.radia_id1f1);

        radia_id1f2=findViewById(R.id.radia_id1f2);
        radia_id1f3=findViewById(R.id.radia_id1f3);
        radia_id1f4=findViewById(R.id.radia_id1f4);



        radio1.setChecked(false);
       radio2.setChecked(false);
       radio3.setChecked(false);
        radio4.setChecked(false);


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

                Glide.with(Checkwrongsingle.this).load(opt1).apply(new RequestOptions()
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

                Glide.with(Checkwrongsingle.this).load(opt2).apply(new RequestOptions()
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

                Glide.with(Checkwrongsingle.this).load(opt3).apply(new RequestOptions()
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
                Glide.with(Checkwrongsingle.this).load(opt4)
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







        Qno.setText(QusnoS+"/"+Toatal);

        txtQuestion.setText(Qus);







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
                Glide.with(Checkwrongsingle.this).load(input1)
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
                    Glide.with(Checkwrongsingle.this).load(input01)
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
                    Glide.with(Checkwrongsingle.this).load(input01)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);

                    Glide.with(Checkwrongsingle.this).load(input2)
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



                        Glide.with(Checkwrongsingle.this).load(input1)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage);
                        questionm2.setDisplayText(textonly);
                        Glide.with(Checkwrongsingle.this).load(input2)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage2);

                        Glide.with(Checkwrongsingle.this).load(input3)
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
                        Glide.with(Checkwrongsingle.this).load(input1)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage);

                        questionm2.setVisibility(View.VISIBLE);
                        questionm2.setDisplayText(textonly);

                        System.out.println("image2"+input21);
                        Glide.with(Checkwrongsingle.this).load(input21)
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
                        Glide.with(Checkwrongsingle.this).load(input212s)
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



                    Glide.with(Checkwrongsingle.this).load(input1)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);

                    Glide.with(Checkwrongsingle.this).load(input2)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage2);

                    Glide.with(Checkwrongsingle.this).load(input3)
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


                        Glide.with(Checkwrongsingle.this).load(input1)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage);

                        Glide.with(Checkwrongsingle.this).load(input2)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage2);

                        Glide.with(Checkwrongsingle.this).load(input3)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage3);
                        Glide.with(Checkwrongsingle.this).load(input4)
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


                        Glide.with(Checkwrongsingle.this).load(input1)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage);

                        Glide.with(Checkwrongsingle.this).load(input2)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage2);

                        Glide.with(Checkwrongsingle.this).load(input3)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage3);
                        Glide.with(Checkwrongsingle.this).load(input4)
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

                    Glide.with(Checkwrongsingle.this).load(input2)
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

                    Glide.with(Checkwrongsingle.this).load(input1)
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


                    Glide.with(Checkwrongsingle.this).load(input312)
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





                    Glide.with(Checkwrongsingle.this).load(input31)
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

//                    Glide.with(Checkwrongsingle.this).load(input2)
//                            .apply(new RequestOptions()
//                                    .override(Target.SIZE_ORIGINAL)
//                                    .format(DecodeFormat.PREFER_ARGB_8888))
//                            .skipMemoryCache(true) //2
//                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
//                            .into(qimage);
                    Glide.with(Checkwrongsingle.this).load(input2)
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

                    Glide.with(Checkwrongsingle.this).load(input31)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);
                    Glide.with(Checkwrongsingle.this).load(input)
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

                    Glide.with(Checkwrongsingle.this).load(input1)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);
                    Glide.with(Checkwrongsingle.this).load(input2)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage2);

                    Glide.with(Checkwrongsingle.this).load(input)
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

                    Glide.with(Checkwrongsingle.this).load(input1)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);
                    Glide.with(Checkwrongsingle.this).load(input2)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage2);

                    Glide.with(Checkwrongsingle.this).load(input)
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

                    Glide.with(Checkwrongsingle.this).load(input1)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);

                    Glide.with(Checkwrongsingle.this).load(input2)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage2);

                    Glide.with(Checkwrongsingle.this).load(input3)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage3);


                    Glide.with(Checkwrongsingle.this).load(input4)
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



                    Glide.with(Checkwrongsingle.this).load(input1)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);

                    Glide.with(Checkwrongsingle.this).load(input2)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage2);

                    Glide.with(Checkwrongsingle.this).load(input3)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage3);


                    Glide.with(Checkwrongsingle.this).load(input4)
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





















        sub.setText("SUBJECT - "+subjectName);
        chap.setText("CHAPTER - "+chapterName);
        System.out.println("Explain string "+explain);
        System.out.println("Explain string "+ explain.equalsIgnoreCase("not given"));
        if (explain.equalsIgnoreCase(" not given")) {
            System.out.println("Explain string if cond");
         explainLayout.setVisibility(View.GONE);
        } else {
            System.out.println("Explain string else cond");
            explainLayout.setVisibility(View.VISIBLE);
            explains.setText(explain);
            explainationView.setDisplayText(explain);
        }
        Single.setText(" Single Choice Question");



        opt.setText(NEWW);

        System.out.println("checkanswerquestionoption"+opt1+"----"+opt2+"----"+opt3+"-----"+opt4+"wronganswer"+wronganswer+"answer"+answer);














    //    radio1.setText(opt1);






        String getradio1=radio1.getText().toString();
        System.out.println(getradio1);
        System.out.println(getradio1.trim());
        String Radio1=getradio1.trim();


        String opts=opt.getText().toString();


if(getradio1.equals(getradio1)){
    System.out.println("wrongselcted"+getradio1);
    System.out.println("opts"+opts);
    System.out.println("Radio1"+Radio1);

}







//
//        if(getradio1.equals(wronganswer)){
//
//            System.out.println("checkopt1---wrong"+opt1+"==");
//            int textColor = Color.parseColor("#FF0000");
//            radio1.setButtonTintList(ColorStateList.valueOf(textColor));
//            radio1.setChecked(true);
//            op1.setBackground(getResources().getDrawable(R.drawable.red,null));
//            radio1.setBackground(getResources().getDrawable(R.drawable.red,null));
//
//
//
//        }else if(getradio1.equals(answer)){
//
//
//            int textColor = Color.parseColor("#4CC417");
//            radio1.setButtonTintList(ColorStateList.valueOf(textColor));
//            radio1.setChecked(true);
//            op1.setBackground(getResources().getDrawable(R.drawable.rectangle,null));
//            radio1.setBackground(getResources().getDrawable(R.drawable.rectangle,null));
//            System.out.println("checkopt4---answer"+opt1+"=="+answer);
//        }
//        else {
//
//            System.out.println("checkopt4---"+opt1+"=="+answer+"---");
//            int textColor = Color.parseColor("#BFBFBF");
//            radio1.setButtonTintList(ColorStateList.valueOf(textColor));
//           radio1.setChecked(true);
//            op1.setBackground(getResources().getDrawable(R.drawable.ash,null));
//           radio1.setBackground(getResources().getDrawable(R.drawable.ash,null));
//
//
//
//        }








//        radio2.setText(opt2);
//        radio3.setText(opt3);
//        radio4.setText(opt4);

        Answer=answer;
        Explain=explain;


        int textColor = Color.parseColor("#BFBFBF");
        radio4.setButtonTintList(ColorStateList.valueOf(textColor));
        radio4.setChecked(true);
        op4.setBackground(getResources().getDrawable(R.drawable.ash,null));
        //radio4.setBackground(getResources().getDrawable(R.drawable.ash,null));




        int textColors = Color.parseColor("#BFBFBF");
        radio3.setButtonTintList(ColorStateList.valueOf(textColors));
        radio3.setChecked(true);
        op3.setBackground(getResources().getDrawable(R.drawable.ash,null));
        //radio3.setBackground(getResources().getDrawable(R.drawable.ash,null));



        int textColor2 = Color.parseColor("#BFBFBF");
        radio2.setButtonTintList(ColorStateList.valueOf(textColor2));
        radio2.setChecked(true);
        op2.setBackground(getResources().getDrawable(R.drawable.ash,null));
       /// radio2.setBackground(getResources().getDrawable(R.drawable.ash,null));

        int textColor1 = Color.parseColor("#BFBFBF");
        radio1.setButtonTintList(ColorStateList.valueOf(textColor1));
        radio1.setChecked(true);
        op1.setBackground(getResources().getDrawable(R.drawable.ash,null));
        //radio1.setBackground(getResources().getDrawable(R.drawable.ash,null));




//        if (wronganswer.equals(opt1)) {
//            Toast.makeText(Checkwrongsingle.this, "Enter mobile number", Toast.LENGTH_SHORT).show();
//        } else if (answer.equals(opt1)) {
//            Toast.makeText(Checkwrongsingle.this, "Enter password", Toast.LENGTH_SHORT).show();
//        } else {
//
//        }

        System.out.println("set"+Qusno+"."+Qus+""+explain+""+wronganswer);



        if(quid==1){

            butprev.setVisibility(View.GONE);

        }else{
            butprev.setVisibility(View.VISIBLE);
        }


        //Toast.makeText(this, wronganswer, Toast.LENGTH_SHORT).show();

        //Toast.makeText(this, opt1, Toast.LENGTH_SHORT).show();
        System.out.println("option1and wrong"+opt1+""+wronganswer+"Qusno"+Qusno);


        String OPT=opt1;
        String Wro=wronganswer;

        System.out.println("ss"+OPT+Wro+""+NEWW+"Qusno"+Qusno);

        boolean correct = NEWW.equals(Qusno);

        System.out.println("BOLENAN"+correct);



        boolean correct1 = opt1.equals(Qusno);

        System.out.println("BOLENAN1"+correct1);


        boolean correct2 = Qusno.equals(opt1);

        System.out.println("BOLENAN2"+correct2);

        boolean correct3 = NEWW.equals(opt1);

        System.out.println("BOLENAN3"+correct3);
        String Radio11=opt1.trim();



        if(Radio11.equalsIgnoreCase(Qusno)) {





            System.out.println("checkopt1---wrong"+opt1+"=="+wronganswer);
            int textColorz = Color.parseColor("#FF0000");
            radio1.setButtonTintList(ColorStateList.valueOf(textColorz));
            radio1.setChecked(true);
            op1.setBackground(getResources().getDrawable(R.drawable.red,null));
            //radio1.setBackground(getResources().getDrawable(R.drawable.red,null));

        }

        if(opt1.equalsIgnoreCase(answer)){




            System.out.println("checkopt1---answer"+opt1+"=="+answer);
            int textColorx = Color.parseColor("#4CC417");
            radio1.setButtonTintList(ColorStateList.valueOf(textColorx));
            radio1.setChecked(true);
            op1.setBackground(getResources().getDrawable(R.drawable.rectangle,null));
            //radio1.setBackground(getResources().getDrawable(R.drawable.rectangle,null));


        }



        String Radio112=opt2.trim();

        if(Radio112.equals(wronganswer)){
            int textColorcc = Color.parseColor("#FF0000");
            radio2.setButtonTintList(ColorStateList.valueOf(textColorcc));
            radio2.setChecked(true);
            op2.setBackground(getResources().getDrawable(R.drawable.red,null));
           // radio2.setBackground(getResources().getDrawable(R.drawable.red,null));
            System.out.println("checkopt2---wrong"+opt2+"=="+wronganswer);
        }if(opt2.equals(answer)) {
            int textColorv = Color.parseColor("#4CC417");
            radio2.setButtonTintList(ColorStateList.valueOf(textColorv));
            radio2.setChecked(true);
            op2.setBackground(getResources().getDrawable(R.drawable.rectangle,null));
           // radio2.setBackground(getResources().getDrawable(R.drawable.rectangle,null));
            System.out.println("checkopt2---answer"+opt2+"=="+answer);

        }

        String Radio113=opt3.trim();

        if(Radio113.equals(wronganswer)){
            int textColorb = Color.parseColor("#FF0000");
            radio3.setButtonTintList(ColorStateList.valueOf(textColorb));
            radio3.setChecked(true);
            op3.setBackground(getResources().getDrawable(R.drawable.red,null));
            //radio3.setBackground(getResources().getDrawable(R.drawable.red,null));
            System.out.println("checkopt3---wrong"+opt3+"=="+wronganswer);
        } if(opt3.equals(answer)) {
            int textColorn = Color.parseColor("#4CC417");
            radio3.setButtonTintList(ColorStateList.valueOf(textColorn));
            radio3.setChecked(true);
            op3.setBackground(getResources().getDrawable(R.drawable.rectangle,null));
          //  radio3.setBackground(getResources().getDrawable(R.drawable.rectangle,null));
            System.out.println("checkopt3---wrong"+answer+"=="+answer);

        }

        String Radio114=opt4.trim();
        if(Radio114.equals(wronganswer)){
            int textColorm = Color.parseColor("#FF0000");
            radio4.setButtonTintList(ColorStateList.valueOf(textColorm));
            radio4.setChecked(true);
            op4.setBackground(getResources().getDrawable(R.drawable.red,null));
          //  radio4.setBackground(getResources().getDrawable(R.drawable.red,null));
            System.out.println("checkopt4---wrong"+opt4+"=="+wronganswer);
        } if(opt4.equals(answer)) {
            int textColora = Color.parseColor("#4CC417");
            radio4.setButtonTintList(ColorStateList.valueOf(textColora));
            radio4.setChecked(true);
            op4.setBackground(getResources().getDrawable(R.drawable.rectangle,null));
            //radio4.setBackground(getResources().getDrawable(R.drawable.rectangle,null));
            System.out.println("checkopt4---answer"+opt4+"=="+answer);

        }


    }

//    private void printColorUsingSwitch(String opt1) {
//
//        if (opt1.equals(answer)) {
//
//            System.out.println("answer");
//        } else if (opt1.equals(wronganswer)) {
//
//            System.out.println("wronganswer");
//        } else {
//
//            System.out.println("INVALID COLOR CODE");
//        }
//    }


    private void setQuestionView(String questionid) {



        String Anwercounts="0";



        try {

            dbHelper.CreateTable(6);
            cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.selctedlist+ " where Questionno='"+questionid+"'" , null);
            System.out.println(TAG + " getValueFromwrong count " + cursor.getCount());
            wrongcount=cursor.getCount();

            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    Qusno="";
                    QusnoS="";
                    Qus = "";
                    answer = "";
                    NEWW="";
                    wrong="";
                    opt1="";
                            opt2="";
                            opt3="";
                            opt4="";explain="";wronganswer="";WRONGANANWER="";subjectName="";chapterName="";
//

                    do {
                        QusnoS = cursor.getString(cursor.getColumnIndex("Questionno"));

                        Qus = cursor.getString(cursor.getColumnIndex("Questoin"));

                        answer = cursor.getString(cursor.getColumnIndex("Answer"));
                        explain = cursor.getString(cursor.getColumnIndex("Explains"));
                        wronganswer=cursor.getString(cursor.getColumnIndex("wronganser"));
                        Qusno = cursor.getString(cursor.getColumnIndex("selectedanser"));
                        opt1 = cursor.getString(cursor.getColumnIndex("Option1"));
                        opt2 = cursor.getString(cursor.getColumnIndex("Option2"));
                        opt3 = cursor.getString(cursor.getColumnIndex("Option3"));
                        opt4 = cursor.getString(cursor.getColumnIndex("Option4"));
                        NEWW=cursor.getString(cursor.getColumnIndex("wronganser"));
                        subjectName = cursor.getString(cursor.getColumnIndex("subjectName"));
                        chapterName = cursor.getString(cursor.getColumnIndex("chapterName"));



//                        System.out.println(TAG + " From Db " + Qus);
//
                      System.out.println("options"+opt1+"opt1"+opt2+"opt3"+opt3+"opt4"+opt4);

//
                        System.out.println("Answer"+answer);
                       System.out.println("Wronganseweryes"+wronganswer);
//
                      System.out.println("selectanswer"+Qusno);


                    } while (cursor.moveToNext());










                }




            }
        } catch (Exception e) {
            System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
        }




//      String  Anwercounts="0";
//
//
//        try {
//
//            dbHelper.CreateTable(6);
//            cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.selctedlist + " where Acount='" + Anwercounts +"' and Wrongquestion = '"+questionid+"'", null);
//            System.out.println(TAG + " getValueFromwrong count " + cursor.getCount());
//            wrongcount = cursor.getCount();
//
//            if (cursor.getCount() > 0) {
//                if (cursor.moveToFirst()) {
//
//
//
//
//                    do {
//                        Qus = cursor.getString(cursor.getColumnIndex("Questoin"));
//                        opt1 = cursor.getString(cursor.getColumnIndex("option1"));
//                        opt2 = cursor.getString(cursor.getColumnIndex("Option2"));
//                        opt3 = cursor.getString(cursor.getColumnIndex("option3"));
//                        opt4 = cursor.getString(cursor.getColumnIndex("option4"));
//                        answer = cursor.getString(cursor.getColumnIndex("Answer"));
//                        explain = cursor.getString(cursor.getColumnIndex("Explains"));
//                        wronganswer=cursor.getString(cursor.getColumnIndex("wronganser"));
//                        Qusno = cursor.getString(cursor.getColumnIndex("Wrongquestion"));
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
//                    } while (cursor.moveToNext());
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
//                }
//
//
//
//
//            }
//
//        } catch (Exception e) {
//            System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
//        }










    }

    private void getValues() {
        Bundle b = getIntent().getExtras();
        if (b != null) {


            LASTID = b.getString("ID");
            Toatal=b.getString("count");
            timer=b.getString("timer");
            Answers = b.getString("Answer");
            Wrongs=b.getString("Wrong");
            LEFTANSWER=b.getString("LEFTANSWER");
            isCorrect=b.getString("isCorrect");
            isWrong=b.getString("isWrong");
            isSkip=b.getString("isSkip");
            isAll=b.getString("isAll");
            isFlag = b.getBoolean("isFlag");
            System.out.println(" getValues Check check wrong" + b);





        }
    }


    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {

        if (isFlag) {
            Intent i = new Intent(Checkwrongsingle.this, saveanswerevent.class);

            i.putExtra("count",Toatal);

            i.putExtra("ID",LASTID);

            i.putExtra("timer",timer);
            i.putExtra("Answer",Answers);
            i.putExtra("Wrong",Wrongs);
            i.putExtra("LEFTANSWER",LEFTANSWER);
            i.putExtra("isCorrect",isCorrect);
            i.putExtra("isWrong",isWrong);
            i.putExtra("isSkip",isSkip);
            i.putExtra("isAll",isAll );
            startActivity(i);
        } else {
            Intent i = new Intent(Checkwrongsingle.this, Resultpage.class);

            i.putExtra("count",Toatal);

            i.putExtra("ID",LASTID);

            i.putExtra("timer",timer);
            i.putExtra("Answer",Answers);
            i.putExtra("Wrong",Wrongs);
            i.putExtra("LEFTANSWER",LEFTANSWER);
            i.putExtra("isCorrect",isCorrect);
            i.putExtra("isWrong",isWrong);
            i.putExtra("isSkip",isSkip);
            i.putExtra("isAll",isAll );
            startActivity(i);
        }


    }
/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logouts) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

}








//
//        getallwronganswers();
//        getValues();
//
//        initToolbar();
//        setlist();
//
//        txtQuestion = (TextView)findViewById(R.id.question);
//        Qno = (TextView)findViewById(R.id.qno);
//
//        explain = (TextView)findViewById(R.id.explain);
//
//
//        radio1=findViewById(R.id.radia_id1);
//
//        radio2=findViewById(R.id.radia_id2);
//        radio3=findViewById(R.id.radia_id3);
//        radio4=findViewById(R.id.radia_id4);
//        radioGroup = (RadioGroup)findViewById(R.id.groupradio);
//
//
//
//        butNext = (Button)findViewById(R.id.button1);
//        butprev= (Button)findViewById(R.id.button2);
//        btsubmit= (Button)findViewById(R.id.button3);
//
//       op1=(LinearLayout)findViewById(R.id.op1);
//      op2=(LinearLayout)findViewById(R.id.op2);
//       op3=(LinearLayout)findViewById(R.id.op3);
//        op4=(LinearLayout)findViewById(R.id.op4);
//
//
//
//        String Anwercounts = "0";
//
//
//        try {
//
//            dbHelper.CreateTable(6);
//            cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.selctedlist + " where Acount='" + Anwercounts + "'", null);
//            System.out.println(TAG + " getValueFromwrong count " + cursor.getCount());
//            wrongcount = cursor.getCount();
//            cursor.moveToFirst();
//            radioGroup.clearCheck();
//            GetSQLiteDatabaseRecords();
//            // checksingle();
//
//        } catch (Exception e) {
//            System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
//        }
//
//
//        radioGroup.setOnCheckedChangeListener(
//                new RadioGroup
//                        .OnCheckedChangeListener() {
//                    @Override
//
//                    // The flow will come here when
//                    // any of the radio buttons in the radioGroup
//                    // has been clicked
//
//                    // Check which radio button has been clicked
//                    public void onCheckedChanged(RadioGroup group,
//                                                 int checkedId)
//                    {
//
//                        // Get the selected Radio Button
//                        RadioButton
//                                radioButton
//                                = (RadioButton)group
//                                .findViewById(checkedId);
//                    }
//                });
//
//
//
//        butNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                checksingle();
//                radioGroup.clearCheck();
//            }
//        });
//
//
//        butprev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                checksinglepre();
//                radioGroup.clearCheck();
//            }
//        });
//
//
//
//
//    }
//
//    private void setlist() {
//
//        txtQuestion.setText(Qus);
//
//        radio1.setText(opt1);
//        radio2.setText(opt2);
//        radio3.setText(opt3);
//        radio4.setText(opt4);
//        Answer=answer;
//
//        radioGroup.clearCheck();
//
//
//
//
//    }
//
//    private void checksinglepre() {
//
//        if (!cursor.isFirst()){
//
//            cursor.moveToPrevious();
//        }else {
//            Toast.makeText(Checkwrongsingle.this, "No Previous Review Wrong Answer", Toast.LENGTH_SHORT).show();
//        }
//        GetSQLiteDatabaseRecords();
//    }
//
//
//    private void initToolbar() {
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        actionBar = getSupportActionBar();
//
//        if (actionBar != null) {
//            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                back();
//            }
//        });
//    }
//
//    @Override
//    public void onBackPressed() {
//        back();
//    }
//
//    private void back() {
//        Intent i = new Intent(Checkwrongsingle.this, Resultpage.class);
//
//        i.putExtra("count",Toatal);
//
//        i.putExtra("ID",LASTID);
//
//        i.putExtra("timer",timer);
//        i.putExtra("Answer",Answers);
//        i.putExtra("Wrong",Wrongs);
//        i.putExtra("LEFTANSWER",LEFTANSWER);
//        startActivity(i);
//    }
//
//
//
//
//    private void getallwronganswers() {
//
//
//    }
//
//    private void checksingle() {
//        if (!cursor.isLast()){
//
//            cursor.moveToNext();
//        }else {
//            Toast.makeText(Checkwrongsingle.this, "No Next  Review Wrong Answer", Toast.LENGTH_SHORT).show();
//        }
//
//        GetSQLiteDatabaseRecords();
//    }
//
//    private void GetSQLiteDatabaseRecords() {
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
//        System.out.println("Questionno"+cursor.getString(1).toString());
//
//
//        System.out.println("Questoin"+cursor.getString(7).toString());
//        System.out.println("wronganser"+cursor.getString(3).toString());
//        System.out.println("Answer"+cursor.getString(6).toString());
//        System.out.println("Explains"+cursor.getString(8).toString());
//        System.out.println("Option1"+cursor.getString(9).toString());
//
//        System.out.println("Option2"+cursor.getString(10).toString());
//
//        System.out.println("Option3"+cursor.getString(11).toString());
//
//        System.out.println("Option4"+cursor.getString(12).toString());
//
//         String count = new Integer(wrongcount).toString();
//
//        Qno.setText(cursor.getString(1).toString()+"/"+count);
//        txtQuestion.setText(cursor.getString(7).toString());
//       // Single.setText(" Single Choice Question");
//
//
//
//
//        radio1.setText(cursor.getString(9).toString());
//        radio2.setText(cursor.getString(10).toString());
//        radio3.setText(cursor.getString(11).toString());
//        radio4.setText(cursor.getString(12).toString());
//
//        String correctss="",first="",wrong="",second="",thired="",four="";
//
// first=cursor.getString(9).toString();
//        second=cursor.getString(10).toString();
//        thired=cursor.getString(11).toString();
//        four=cursor.getString(12).toString();
//
//        correctss=cursor.getString(6).toString();
//        wrong=cursor.getString(3).toString();
//        if (first .equals(correctss)){
//
//           //  op1.setBackgroundColor(Color.GREEN);
//            op1.setBackground(getResources().getDrawable(R.drawable.rectangle,null));
//          int textColor = Color.parseColor("#4CC417");
//           radio1.setButtonTintList(ColorStateList.valueOf(textColor));
//
//            radio1.setChecked(true);
//
//        }else if (first .equals(wrong)){
//            //op1.setBackgroundColor(Color.RED);
//            op1.setBackground(getResources().getDrawable(R.drawable.red,null));
//            int textColor = Color.parseColor("#FF0000");
//            radio1.setButtonTintList(ColorStateList.valueOf(textColor));
//
//            radio1.setChecked(true);
//
//        }else{
//            op1.setBackground(getResources().getDrawable(R.drawable.ash,null));
//        }
//
//
//        if (second .equals(correctss)){
//
//          //  op2.setBackgroundColor(Color.GREEN);
//            op2.setBackground(getResources().getDrawable(R.drawable.rectangle,null));
//            int textColor = Color.parseColor("#4CC417");
//            radio2.setButtonTintList(ColorStateList.valueOf(textColor));
//
//
//
//
//            radio2.setChecked(true);
//
//        }else if (second.equals(wrong)){
//             // op2.setBackgroundColor(Color.RED);
//            op2.setBackground(getResources().getDrawable(R.drawable.red,null));
//            int textColor = Color.parseColor("#FF0000");
//            radio2.setButtonTintList(ColorStateList.valueOf(textColor));
//
//
//            radio2.setChecked(true);
//
//        }else{
//            op2.setBackground(getResources().getDrawable(R.drawable.ash,null));
//        }
//
//        if (thired.equals(correctss)){
//
//           // op3.setBackgroundColor(Color.GREEN);
//            op3.setBackground(getResources().getDrawable(R.drawable.rectangle,null));
//            int textColor = Color.parseColor("#4CC417");
//            radio3.setButtonTintList(ColorStateList.valueOf(textColor));
//
//
//
//
//            radio3.setChecked(true);
//
//        }else if (thired .equals(wrong)){
//         //   op3.setBackgroundColor(Color.RED);
//            op3.setBackground(getResources().getDrawable(R.drawable.red,null));
//            int textColor = Color.parseColor("#FF0000");
//            radio3.setButtonTintList(ColorStateList.valueOf(textColor));
//
//
//
//            radio3.setChecked(true);
//
//        }else{
//            op3.setBackground(getResources().getDrawable(R.drawable.ash,null));
//        }
//
//        if (four .equals(correctss)){
//
//            //op4.setBackgroundColor(Color.GREEN);
//            op4.setBackground(getResources().getDrawable(R.drawable.rectangle,null));
//            int textColor = Color.parseColor("#4CC417");
//            radio4.setButtonTintList(ColorStateList.valueOf(textColor));
//
//
//
//
//            radio4.setChecked(true);
//
//        }else if (four .equals(wrong)){
//           // op4.setBackgroundColor(Color.RED);
//            op4.setBackground(getResources().getDrawable(R.drawable.red,null));
//            int textColor = Color.parseColor("#FF0000");
//            radio4.setButtonTintList(ColorStateList.valueOf(textColor));
//
//
//
//            radio4.setChecked(true);
//
//        }else{
//            op4.setBackground(getResources().getDrawable(R.drawable.ash,null));
//        }
//
//
//
//
//
//
//
//
//
//        Answer=cursor.getString(6).toString();
//        Explain=cursor.getString(8).toString();
//
//
//
//       // System.out.println("set"+Qusno+"."+Qus+""+explain);
//
//        if(Explain.equals("null")){
//            explain.setVisibility(View.GONE);
//        }else{
//            explain.setVisibility(View.VISIBLE);
//            explain.setText(Explain);
//        }
//
//
//    }
//
//    private void getValues() {
//        Bundle b = getIntent().getExtras();
//        if (b != null) {
//
//
//
//
//
//
//
//
//            LASTID = b.getString("ID");
//            Toatal=b.getString("count");
//            timer=b.getString("timer");
//            Answers = b.getString("Answer");
//            Wrongs=b.getString("Wrong");
//            LEFTANSWER=b.getString("LEFTANSWER");
//
//
//            System.out.println(" getValues Check" + b);
//
//
//
//
//
//        }
//    }
//
//}