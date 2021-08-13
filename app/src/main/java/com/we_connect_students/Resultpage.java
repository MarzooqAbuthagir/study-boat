package com.we_connect_students;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.github.mikephil.charting.data.PieDataSet;
import com.we_connect_students.DataBase.DBHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import at.grabner.circleprogress.CircleProgressView;

public class Resultpage extends AppCompatActivity {
  public String  Answer="",Wronganswer="",LASTID="",Wrong="",LEFTANSWER="",Wrongquestiono="",CorrectQusno="",Skipqno="",wrongQuestions="",right_questions="",skippedQuestions="",allquestiono="",isCorrectnew="",isAll="";
  TextView Mark,Name,correctanswer,total,time,charttext1,charttext2,charttext3, timetaken;
  Button   close,check;
    int intVariableName,intVariableNames;
    Utilis utilis;
    String wrong="",str_result="",str_message="";
    DBHelper dbHelper;
    Cursor cursor;
    ArrayList<String> listName;
    String TAG = "result",ID="";
    public static ArrayList<HashMap<String, String>> cartArrayList = null;
    int answercount,wrongcount,  lefts;
    HashMap<String, String> map;
    public static String  Allqusno = "",
            answer = "",result="",wresult="",Toatal="",timer="",Iscorrect="",isWrong="",isSkip="";

    PieDataSet dataSet;
    int w=10;
    int pStatus = 75;
    TextView tv,persentage,score,ptv1,ptv2,ptv3,tvs,tvsk;
    CircleProgressView mCircleView,mCircleView2,mCircleView3;
    String te="Correct Answers";



    ArrayList<String> Correctqus;
    ArrayList<String> Wrongqus;
    ArrayList<String> Skipequs;
    ArrayList<String> Allqus;
    ArrayList<String> iscorrect;
    Object[] objArray,objArray2,objArray3,objArray4,objArray5;
    String newString,newString2,Subject,newString3,newString4,newString5;

    @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_result);



        Window window = Resultpage.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(Resultpage.this,R.color.my_statusbar_color1));
        utilis = new Utilis(Resultpage.this);
        dbHelper = new DBHelper(Resultpage.this);
        getValues();


        close=findViewById(R.id.close);
       check=findViewById(R.id.check);

        persentage=findViewById(R.id.persentage);
        score=findViewById(R.id.score);
       time=findViewById(R.id.time);
       timetaken = findViewById(R.id.timetaken);


        ptv1=findViewById(R.id.ptv1);
        ptv2=findViewById(R.id.ptv2);
        ptv3=findViewById(R.id.ptv3);


        ptv1.setText("Correct"+" "+":"+" "+Answer);
        ptv2.setText("Wrong"+" "+":"+" "+Wrong);
        ptv3.setText("Skipped"+" "+":"+" "+LEFTANSWER);

        mCircleView = (CircleProgressView) findViewById(R.id.circleView);
        mCircleView2 = (CircleProgressView) findViewById(R.id.circleViews);
        mCircleView3 = (CircleProgressView) findViewById(R.id.circleViewss);

        mCircleView.setMaxValue(100);
        mCircleView.setValue(50);
        mCircleView.setValueAnimated(20);

        //show unit
        mCircleView.setUnit("%");
        mCircleView.setUnitVisible(true);

        //text sizes
        mCircleView.setTextSize(35); // text size set, auto text size off
        mCircleView.setUnitSize(35); // if i set the text size i also have to set the unit size
        //mCircleView.setAutoTextSize(true); // enable auto text size, previous values are overwritten
        //if you want the calculated text sizes to be bigger/smaller you can do so via
        //mCircleView.setUnitScale(0.8f);
       // mCircleView.setTextScale(0.5f);


        mCircleView.setBarColor(getResources().getColor(R.color.corect1), getResources().getColor(R.color.corect1));

        //colors of text and unit can be set via
        mCircleView.setTextColor(getResources().getColor(R.color.corect1));
        mCircleView.setRimColor(getResources().getColor(R.color.corect));
        mCircleView.setInnerContourColor(getResources().getColor(R.color.corect1));
        mCircleView.setOuterContourColor(getResources().getColor(R.color.corect1));

        //or to use the same color as in the gradient
       // mCircleView.setTextColorAuto(true); //previous set values are ignored

        //text mode
       // mCircleView.setText("Text"); //shows the given text in the circle view
      //  mCircleView.setTextMode(TextMode.TEXT); // Set text mode to text to show text

        //in the following text modes, the text is ignored
       // mCircleView.setTextMode(TextMode.VALUE); // Shows the current value
       // mCircleView.setTextMode(TextMode.PERCENT); // Shows current percent of the current value from the max value



        mCircleView2.setMaxValue(100);
        mCircleView2.setValue(90);
        mCircleView2.setValueAnimated(90);






    //show unit
        mCircleView2.setUnit("%");
        mCircleView2.setUnitVisible(true);

        //text sizes
        mCircleView2.setTextSize(35); // text size set, auto text size off
        mCircleView2.setUnitSize(35); // if i set the text size i also have to set the unit size
       // mCircleView2.setAutoTextSize(true); // enable auto text size, previous values are overwritten
        //if you want the calculated text sizes to be bigger/smaller you can do so via
       // mCircleView2.setUnitScale(0.8f);
        //mCircleView2.setTextScale(0.2f);


        mCircleView2.setBarColor(getResources().getColor(R.color.incorect1), getResources().getColor(R.color.incorect1));

        //colors of text and unit can be set via
        mCircleView2.setTextColor(getResources().getColor(R.color.incorect1));
        mCircleView2.setRimColor(getResources().getColor(R.color.incorect));
        mCircleView2.setInnerContourColor(getResources().getColor(R.color.incorect1));
        mCircleView2.setOuterContourColor(getResources().getColor(R.color.incorect1));


        //or to use the same color as in the gradient
        // mCircleView.setTextColorAuto(true); //previous set values are ignored

        //text mode
        // mCircleView.setText("Text"); //shows the given text in the circle view
      //  mCircleView2.setTextMode(TextMode.TEXT); // Set text mode to text to show text

        //in the following text modes, the text is ignored
        //mCircleView2.setTextMode(TextMode.VALUE); // Shows the current value
      //  mCircleView2.setTextMode(TextMode.PERCENT); // Shows current percent of the current value from the max value




        mCircleView3.setMaxValue(100);
        mCircleView3.setValue(0);
        mCircleView3.setValueAnimated(0);


        //show unit
        mCircleView3.setUnit("%");
        mCircleView3.setUnitVisible(true);

        //text sizes
        mCircleView3.setTextSize(35); // text size set, auto text size off
        mCircleView3.setUnitSize(35); // if i set the text size i also have to set the unit size
        //mCircleView3.setAutoTextSize(true); // enable auto text size, previous values are overwritten
        //if you want the calculated text sizes to be bigger/smaller you can do so via
       // mCircleView3.setUnitScale(0.8f);
       // mCircleView3.setTextScale(0.3f);


        mCircleView3.setBarColor(getResources().getColor(R.color.skip1), getResources().getColor(R.color.skip1));

        //colors of text and unit can be set via
        mCircleView3.setTextColor(getResources().getColor(R.color.skip1));
mCircleView3.setRimColor(getResources().getColor(R.color.skip));

        mCircleView2.setInnerContourColor(getResources().getColor(R.color.skip1));
        mCircleView2.setOuterContourColor(getResources().getColor(R.color.skip1));

        //or to use the same color as in the gradient
        // mCircleView.setTextColorAuto(true); //previous set values are ignored

        //text mode
        // mCircleView.setText("Text"); //shows the given text in the circle view
        //mCircleView3.setTextMode(TextMode.TEXT); // Set text mode to text to show text

        //in the following text modes, the text is ignored
        //mCircleView3.setTextMode(TextMode.VALUE); // Shows the current value
       // mCircleView3.setTextMode(TextMode.PERCENT); // Shows current percent of the current value from the max value





        score.setText(Answer+"/"+Toatal);
        time.setText(timer);
        String timeString ="<b>" + "TIME TAKEN" + "</b> "+"(MM:SS)";
        timetaken.setText(Html.fromHtml(timeString));











        answercount= Integer.parseInt(Answer);

        int Toatals=Integer.parseInt(Toatal);

        int cper=answercount*100;

        int total=cper/Toatals;



        persentage.setText(total+"%");

        String numberAsString = new Integer(total).toString();


        Float f= Float.parseFloat(numberAsString);
         // returns double primitive
        System.out.println(f);

        mCircleView.setValue(f);
        mCircleView.setValueAnimated(f);



       wrongcount= Integer.parseInt(Wrong);
        int cpers=wrongcount*100;
        int totals=cpers/Toatals;
        String numberAsStrings = new Integer(totals).toString();


        Float ff= Float.parseFloat(numberAsStrings);
        mCircleView2.setValue(ff);
        mCircleView2.setValueAnimated(ff);


        lefts= Integer.parseInt(LEFTANSWER);
        int cperss=lefts*100;
        int totalss=cperss/Toatals;
        String numberAsStringss = new Integer(totalss).toString();


        Float fff= Float.parseFloat(numberAsStringss);
        mCircleView3.setValue(fff);
        mCircleView3.setValueAnimated(fff);

//         Mark=findViewById(R.id.mark);
//         Name=findViewById(R.id.name);
//         close=findViewById(R.id.close);
//        check=findViewById(R.id.check);
//
//        correctanswer=findViewById(R.id.correctanswer);
//        total=findViewById(R.id.total);
//        time=findViewById(R.id.time);
//
//
//        charttext1=findViewById(R.id.charttext1);
//        charttext2=findViewById(R.id.charttext2);
//        charttext3=findViewById(R.id.charttext3);
        utilis.getSharedPreference();

//time.setText(timer);
//        System.out.println("timer"+timer);



check.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        System.out.println("timer"+timer);
        Intent i=new Intent(Resultpage.this,Checkwrongsingle.class);


        i.putExtra("count",Toatal);

        i.putExtra("ID",LASTID);

        i.putExtra("timer",timer);
        i.putExtra("Answer",Answer);
        i.putExtra("Wrong",Wrong);
        i.putExtra("LEFTANSWER",LEFTANSWER);
        i.putExtra("isCorrect",isCorrectnew);
        i.putExtra("isWrong",isWrong);
        i.putExtra("isSkip",isSkip);
        i.putExtra("isAll",isAll );
        i.putExtra("isFlag", false);


        startActivity(i);



    }
});





//        String Anwercount="1";valitity
//
//
//
//        try {
//
//            cartArrayList = new ArrayList<HashMap<String, String>>();
//            dbHelper.CreateTable(6);
//            cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.selctedlist + " where Acount='"+Anwercount+"'", null);
//            System.out.println(TAG + " getValueanswer count " + cursor.getCount());
//            answercount=cursor.getCount();
//
//            if (cursor.getCount() > 0) {
//                if (cursor.moveToFirst()) {
//
//                    Qusno = "";
//                    answer = "";
//                    wrong="";
////
//
//                    do {
//                        Qusno = cursor.getString(cursor.getColumnIndex("Questionno"));
//                        answer = cursor.getString(cursor.getColumnIndex("correctanser"));
//                        wrong = cursor.getString(cursor.getColumnIndex("selectedanser"));
////                        str_itemIndex = cursor.getString(cursor.getColumnIndex("itemIndex"));
////                        str_itemQty = cursor.getString(cursor.getColumnIndex("itemQty"));
////                        str_itemVat = cursor.getString(cursor.getColumnIndex("itemVat"));
////                        str_itemPrice = cursor.getString(cursor.getColumnIndex("itemPrice"));
////                        str_itemVatAmt = cursor.getString(cursor.getColumnIndex("itemVatAmt"));
//                        System.out.println(TAG + " From Db " + answer+""+Qusno+"");
//
//
//
//
//                        map = new HashMap<String, String>();
//                        map.put(Constants.HM_ITEMCODE, Qusno);
//                        map.put(Constants.HM_ITEMNAME, answer);
////                        map.put(Constants.HM_ITEMINDEX, str_itemIndex);
////                        map.put(Constants.HM_ITEMPRICE, str_itemPrice);
////                        map.put(Constants.HM_VATPERCENT, str_itemVat);
////                        map.put(Constants.HM_QTY, str_itemQty);
////                        map.put(Constants.HM_VATAMT, str_itemVatAmt);
//                        cartArrayList.add(map);
//                        System.out.println("correct"+map);
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
//        } catch (Exception e) {
//            System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
//        }
//
//
//
//
//
//
//
//        String Anwercounts="0";
//
//
//
//        try {
//
//            dbHelper.CreateTable(6);
//            cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.selctedlist + " where Acount='"+Anwercounts+"'", null);
//            System.out.println(TAG + " getValueFromwrong count " + cursor.getCount());
//            wrongcount=cursor.getCount();
//
//            if (cursor.getCount() > 0) {
//                if (cursor.moveToFirst()) {
//
//                    Qusno = "";
//                    answer = "";
//                    wrong="";
////
//
//                    do {
//                        Qusno = cursor.getString(cursor.getColumnIndex("Questionno"));
//                        //answer = cursor.getString(cursor.getColumnIndex("correctanser"));
//                        wrong = cursor.getString(cursor.getColumnIndex("wronganser"));
////                        str_itemIndex = cursor.getString(cursor.getColumnIndex("itemIndex"));
////                        str_itemQty = cursor.getString(cursor.getColumnIndex("itemQty"));
////                        str_itemVat = cursor.getString(cursor.getColumnIndex("itemVat"));
////                        str_itemPrice = cursor.getString(cursor.getColumnIndex("itemPrice"));
////                        str_itemVatAmt = cursor.getString(cursor.getColumnIndex("itemVatAmt"));
//                        System.out.println(TAG + " From Db " + answer+""+Qusno+"");
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
//        } catch (Exception e) {
//            System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
//        }
//
//
//
//
//
//
//
//        System.out.println("corectandwrong--"+answercount+"----"+wrongcount);
//
//
//        int left=answercount+wrongcount;
//
//        int totals=
//        Integer.parseInt(Toatal);
//         lefts=totals-left;
//        Answer= String.valueOf(answercount);
//        Wrong= String.valueOf(wrongcount);
//
//        LEFTANSWER= String.valueOf(lefts);

        System.out.println("id"+utilis.strName);

       // dbHelper.vatbook.execSQL("delete from " + dbHelper.selctedlist);


//        Name.setText("Your Test Mark");
//
//
//
//
//         Mark.setText( Answer+" "+"/"+" "+Toatal);
//
//         correctanswer.setText(Answer);
//         total.setText("/"+Toatal);
//
//
//
//
//        charttext1.setText(Answer+" "+"CORRECT");
//        charttext2.setText(Wrong+" "+"INCORRECT");
//        charttext3.setText(LEFTANSWER+" "+"LEFT");
       // Mark.setText("Your Test Mark"+" "+ result+" "+"/"+" "+Wronganswer);

//        Spannable span = new SpannableString(Answers.getText());
//        span.setSpan(new RelativeSizeSpan(1.5f), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        Answers.setText(span);


      //  drawChart();
         close.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

 /*                String Anwercount="1";



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






if(isCorrectnew.equals("null")){

}else{
    submittrsult(LASTID,Answer,Wrong,timer);
}*/


                 startActivity(new Intent(Resultpage.this, subcription.class));
                 finish();


             }
         });




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

            utilis.showProgress(Resultpage.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, utilis.Api + utilis.submit, new Response.Listener<String>() {
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



                            startActivity(new Intent(Resultpage.this, subcription.class));
                            finish();





                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(Resultpage.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(Resultpage.this, Resultpage.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
                    params.put("isCorrect",isCorrectnew);
                    params.put("isWrong",isWrong);
                    params.put("isSkip",isSkip);
                    params.put("isAll",isAll );

                 //   params.put("allQuestions",allquestiono );

                    System.out.println( "  inputsalll " + params);
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(this, Resultpage.this.getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }







    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        startActivity(new Intent(Resultpage.this, MainActivity.class));
        finish();
    }
     private void getValues() {
         Bundle b = getIntent().getExtras();
         if (b != null) {








             LASTID = b.getString("ID");
             Toatal=b.getString("count");
             timer=b.getString("timer");
             Answer = b.getString("Answer");
             Wrong=b.getString("Wrong");
             LEFTANSWER=b.getString("LEFTANSWER");
             isCorrectnew=b.getString("isCorrect");
             isWrong=b.getString("isWrong");
             isSkip=b.getString("isSkip");
             isAll=b.getString("isAll");

             System.out.println(" getValues " + b);





         }
     }
}
