package com.we_connect_students;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.we_connect_students.DataBase.DBHelper;

import katex.hourglass.in.mathlib.MathView;

public  class PracticequestionActivity extends AppCompatActivity {
    String TAG = "Question",ID="",IDs="", timer="",time="";
    Toolbar toolbar;
    ActionBar actionBar = null;
   public static Button button3,Button1,button2;
   public static String Subject="",Unit="",chapter="",input31="";
    DBHelper dbHelper;
    Utilis utilis;
    int quid = 1;
    Cursor cursor;


  public static String  answer = "",QuestionNumber="",Qust="",

    Qus=""

    ,explain="";
    String SCore="";
    int score = 0;
    int minteger = 0;
   public static TextView qno,question,answers,explains,toolbar_title;


   public static MathView math,questionm,questionm2,questionm3,questionm4,questionm5,explainationView;

    public static ImageView radio1image,qimage4,qimage3,qimage2,qimage;;
    LinearLayout explainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Window window = PracticequestionActivity.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(PracticequestionActivity.this,R.color.voil));
        utilis = new Utilis(PracticequestionActivity.this);
        dbHelper=new DBHelper(PracticequestionActivity.this);
        Intent intent = getIntent();
        ID = intent.getStringExtra("Qcount");
        Subject = intent.getStringExtra("Subject");
        Unit = intent.getStringExtra("Unit");
        chapter = intent.getStringExtra("chapter");

        toolbar_title=findViewById(R.id.toolbar_title);

        toolbar_title.setText(Subject);

        initToolbar();
        qno = (TextView)findViewById(R.id.qno);
        question = (TextView)findViewById(R.id.question);
        answers = (TextView)findViewById(R.id.answer);
        math=findViewById(R.id.maths);
        questionm=findViewById(R.id.questionm);
        questionm2=findViewById(R.id.questionm2);
        questionm3=findViewById(R.id.questionm3);
        questionm4=findViewById(R.id.question4);
        questionm5=findViewById(R.id.question5);
        //questionm=findViewById(R.id.questionm);

        explainLayout = findViewById(R.id.explainLayout);

        qimage=findViewById(R.id.qimage);
        qimage2=findViewById(R.id.qimage2);
        qimage3=findViewById(R.id.qimage3);
        qimage4=findViewById(R.id.qimage4);
        explains = (TextView)findViewById(R.id.explain);
        explainationView = findViewById(R.id.explainationView);
        button3=findViewById(R.id.button3);
        radio1image=findViewById(R.id.radio1image);
        button2=findViewById(R.id.button2);
        Button1=findViewById(R.id.button1);
        views();
        String questionid=String.valueOf(quid);
        setQuestionView(questionid);

        setlist();


        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int result = Integer.parseInt(ID);

                if(quid<result){
                    quid++;
                    String questionid=String.valueOf(quid);
                    System.out.println("check"+questionid);

                    setQuestionView(questionid);
                    setlist();
                    views();
                    System.out.println("icremtrnt"+quid);

                }else{

                    SCore=String.valueOf(score);
                    System.out.println("check"+SCore);


                    //  btsubmit.setVisibility(View.VISIBLE);














                    final AlertDialog.Builder builder = new AlertDialog.Builder(
                            PracticequestionActivity.this);

                    builder.setMessage("End of Questions");

                    builder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {





                                }
                            });
                    builder.show();



                }





            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int result = Integer.parseInt(ID);
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
                    }


                }else{

                    SCore=String.valueOf(score);








                }




//                int selectedId = radioGroup.getCheckedRadioButtonId();
//                if (selectedId == -1) {
//                    Toast.makeText(PracticequestionActivity.this,
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
//                    Toast.makeText(PracticequestionActivity.this,
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







    }

    private void   views(){



    }
    private void setlist() {
        math=findViewById(R.id.maths);

        qno = (TextView)findViewById(R.id.qno);
        question = (TextView)findViewById(R.id.question);
        answers = (TextView)findViewById(R.id.answer);

        explains = (TextView)findViewById(R.id.explain);
        explainationView = findViewById(R.id.explainationView);
//        qno.setText(QuestionNumber+"/"+ID);
        qno.setText(chapter+(" ("+QuestionNumber+"/"+ID+")"));


//        String   lastFourDigits = answer.substring(answer.length() - 4);
//        if (lastFourDigits.equals(".png")){
//            radio1image.setVisibility(View.VISIBLE);
//            math.setVisibility(View.GONE);
//
//            Glide.with(PracticequestionActivity.this).load(answer).apply(new RequestOptions()
//                    .override(Target.SIZE_ORIGINAL)
//                    .format(DecodeFormat.PREFER_ARGB_8888))
//                    .skipMemoryCache(true) //2
//                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(radio1image);
//
//
//        }else
//        {
//            math.setVisibility(View.VISIBLE);
//            radio1image.setVisibility(View.GONE);
//            math.setDisplayText(answer);
//
//        }



        if(answer.length() >10){

            System.out.println("opt1"+answer);
            System.out.println("optinonlenth"+answer.length());
            String   lastFourDigits = answer.substring(answer.length() - 4);
            System.out.println("opt1"+lastFourDigits);
            if (lastFourDigits.equals(".png")){
                radio1image.setVisibility(View.VISIBLE);
                math.setVisibility(View.GONE);

                Glide.with(PracticequestionActivity.this).load(answer).apply(new RequestOptions()
                        .override(Target.SIZE_ORIGINAL)
                        .format(DecodeFormat.PREFER_ARGB_8888))
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE).into(radio1image);



            }else
            {
                math.setVisibility(View.VISIBLE);
                radio1image.setVisibility(View.GONE);
                math.setDisplayText(answer);

            }




        }else{
            math.setVisibility(View.VISIBLE);
            radio1image.setVisibility(View.GONE);
            math.setDisplayText(answer);


        }










        question.setText(Qus);

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
                Glide.with(PracticequestionActivity.this).load(input1)
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
                    Glide.with(PracticequestionActivity.this).load(input01)
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
                    Glide.with(PracticequestionActivity.this).load(input01)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);

                    Glide.with(PracticequestionActivity.this).load(input2)
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



                        Glide.with(PracticequestionActivity.this).load(input1)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage);
                        questionm2.setDisplayText(textonly);
                        Glide.with(PracticequestionActivity.this).load(input2)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage2);

                        Glide.with(PracticequestionActivity.this).load(input3)
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
                        Glide.with(PracticequestionActivity.this).load(input1)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage);

                        questionm2.setVisibility(View.VISIBLE);
                        questionm2.setDisplayText(textonly);

                        System.out.println("image2"+input21);
                        Glide.with(PracticequestionActivity.this).load(input21)
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
                        Glide.with(PracticequestionActivity.this).load(input212s)
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



                    Glide.with(PracticequestionActivity.this).load(input1)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);

                    Glide.with(PracticequestionActivity.this).load(input2)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage2);

                    Glide.with(PracticequestionActivity.this).load(input3)
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


                        Glide.with(PracticequestionActivity.this).load(input1)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage);

                        Glide.with(PracticequestionActivity.this).load(input2)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage2);

                        Glide.with(PracticequestionActivity.this).load(input3)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage3);
                        Glide.with(PracticequestionActivity.this).load(input4)
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


                        Glide.with(PracticequestionActivity.this).load(input1)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage);

                        Glide.with(PracticequestionActivity.this).load(input2)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage2);

                        Glide.with(PracticequestionActivity.this).load(input3)
                                .apply(new RequestOptions()
                                        .override(Target.SIZE_ORIGINAL)
                                        .format(DecodeFormat.PREFER_ARGB_8888))
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .into(qimage3);
                        Glide.with(PracticequestionActivity.this).load(input4)
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

                    Glide.with(PracticequestionActivity.this).load(input2)
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

                    Glide.with(PracticequestionActivity.this).load(input1)
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


                    Glide.with(PracticequestionActivity.this).load(input312)
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





                    Glide.with(PracticequestionActivity.this).load(input31)
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

//                    Glide.with(PracticequestionActivity.this).load(input2)
//                            .apply(new RequestOptions()
//                                    .override(Target.SIZE_ORIGINAL)
//                                    .format(DecodeFormat.PREFER_ARGB_8888))
//                            .skipMemoryCache(true) //2
//                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
//                            .into(qimage);
                    Glide.with(PracticequestionActivity.this).load(input2)
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

                    Glide.with(PracticequestionActivity.this).load(input31)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);
                    Glide.with(PracticequestionActivity.this).load(input)
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

                    Glide.with(PracticequestionActivity.this).load(input1)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);
                    Glide.with(PracticequestionActivity.this).load(input2)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage2);

                    Glide.with(PracticequestionActivity.this).load(input)
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

                    Glide.with(PracticequestionActivity.this).load(input1)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);
                    Glide.with(PracticequestionActivity.this).load(input2)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage2);

                    Glide.with(PracticequestionActivity.this).load(input)
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

                    Glide.with(PracticequestionActivity.this).load(input1)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);

                    Glide.with(PracticequestionActivity.this).load(input2)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage2);

                    Glide.with(PracticequestionActivity.this).load(input3)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage3);


                    Glide.with(PracticequestionActivity.this).load(input4)
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



                    Glide.with(PracticequestionActivity.this).load(input1)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage);

                    Glide.with(PracticequestionActivity.this).load(input2)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage2);

                    Glide.with(PracticequestionActivity.this).load(input3)
                            .apply(new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL)
                                    .format(DecodeFormat.PREFER_ARGB_8888))
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            .into(qimage3);


                    Glide.with(PracticequestionActivity.this).load(input4)
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


        answers.setText(answer);


        System.out.println("Explain string "+explain);
        if (explain.equalsIgnoreCase("not upated")) {
         explainLayout.setVisibility(View.GONE);
        } else {
            explainLayout.setVisibility(View.VISIBLE);
            explains.setText(explain);
            explainationView.setDisplayText(explain);
        }


        if(quid==1){

            button2.setVisibility(View.GONE);

        }else{
            button2.setVisibility(View.VISIBLE);
        }



    }
    private void setQuestionView(String questionid) {



        try {


            System.out.println("setview"+quid);
            dbHelper.CreateTable(7);
            cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.questionanswer + " where questionNumber='"+questionid+"'", null);
            System.out.println(TAG + " getValueFromquestion count " + cursor.getCount());


            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {


                    answer = "";

                    Qus="";

                    explain="";
                    QuestionNumber="";


                    do {
                        Qus = cursor.getString(cursor.getColumnIndex("Questoin"));

                        QuestionNumber = cursor.getString(cursor.getColumnIndex("questionNumber"));
                        answer = cursor.getString(cursor.getColumnIndex("Answer"));
                        explain = cursor.getString(cursor.getColumnIndex("Explains"));

                        System.out.println(TAG + " From Db " + answer+""+Qus+""+explain);








                    } while (cursor.moveToNext());










                }




            }
        } catch (Exception e) {
            System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
        }









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
        dbHelper.vatbook.execSQL("delete from " + dbHelper.questionanswer);


        Intent intent = new Intent(PracticequestionActivity.this, PracticechapterActivity.class); //OrderDetailsActivity



        intent.putExtra("Subject",Subject );
        intent.putExtra("Unit",Unit );
        intent.putExtra("chapter",chapter );


        startActivity(intent);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.logouts) {
//
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}