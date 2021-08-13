package com.we_connect_students;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.we_connect_students.DataBase.DBHelper;

import java.util.ArrayList;
import java.util.List;

public  class Checkwrong  extends AppCompatActivity {
   public static ListView List;
     Toolbar toolbar;
     ActionBar actionBar = null;
    DBHelper dbHelper;
    Cursor cursor;
    String TAG = "Checkwrong";
    int answercount,wrongcount,  lefts;
  String  Qusno = "",
    Question = "",
    Wrong="",
    Answer="",
    Explain="", allQusno="",allQuestion="",allWrong="",allExplain="",allAnswer="",Option1="",alloption1="",alloption2="",alloption3="",alloption4="",
    Option2="",
    Option3="",
    Option4="";
    public static String[] arr_Qusno, arr_Question, arr_Wrong, arr_Explain,arr_Answer,arr_Option1,arr_Option2,arr_Option3,arr_Option4;
    LocalAdapter adapter;
    ArrayList<historyxpensemodel> store_arraylist = new ArrayList<historyxpensemodel>();

    public String  Answers="",Wronganswer="",LASTID="",Wrongs="",LEFTANSWER="",ID="",timer="",Toatal="";


     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        dbHelper = new DBHelper(Checkwrong.this);
         getValues();

        initToolbar();

         List=findViewById(R.id.list);
getallwronganswers();




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
         Intent i = new Intent(Checkwrong.this, Resultpage.class);

         i.putExtra("count",Toatal);

         i.putExtra("ID",LASTID);

         i.putExtra("timer",timer);
         i.putExtra("Answer",Answers);
         i.putExtra("Wrong",Wrongs);
         i.putExtra("LEFTANSWER",LEFTANSWER);
         startActivity(i);
     }




     private void getallwronganswers() {
         String Anwercounts="0";



         try {

             dbHelper.CreateTable(6);
             cursor = dbHelper.vatbook.rawQuery("select * from " + dbHelper.selctedlist + " where Acount='"+Anwercounts+"'", null);
             System.out.println(TAG + " getValueFromwrong count " + cursor.getCount());
             wrongcount=cursor.getCount();
             checksingle();




             if (cursor.getCount() > 0) {
                 if (cursor.moveToLast()) {

                     Qusno = "";
                     Question = "";
                     Wrong="";
                     Answer="";
                     Explain="";
                     Option1="";
                     Option2="";
                     Option3="";
                     Option4="";

                     do {
                         Qusno += cursor.getString(cursor.getColumnIndex("Questionno"))+ "~";;
                         Question += cursor.getString(cursor.getColumnIndex("Questoin"))+ "~";;
                         Wrong += cursor.getString(cursor.getColumnIndex("wronganser"))+ "~";;
                          Answer += cursor.getString(cursor.getColumnIndex("Answer"))+ "~";;
                         Explain += cursor.getString(cursor.getColumnIndex("Explains"))+ "~";;
                         Option1 += cursor.getString(cursor.getColumnIndex("Option1"))+ "~";;
                         Option2 += cursor.getString(cursor.getColumnIndex("Option2"))+ "~";;
                         Option3 += cursor.getString(cursor.getColumnIndex("Option3"))+ "~";;
                         Option4 += cursor.getString(cursor.getColumnIndex("Option4"))+ "~";;



                         allQusno=Qusno;
                         allQuestion=Question;
                         allWrong=Wrong;
                         allAnswer=Answer;
                         allExplain=Explain;
                         alloption1=Option1;
                         alloption2=Option2;
                         alloption3=Option3;
                         alloption4=Option4;


                         System.out.println("check"+Explain+Qusno);


                     } while (cursor.moveToPrevious());










                 }
                 arr_Qusno=Qusno.split("~");
                 arr_Question = Question.split("~");
                 arr_Wrong = Wrong.split("~");
                 arr_Answer= Answer.split("~");
                 arr_Explain = Explain.split("~");

                 arr_Option1 = Option1.split("~");
                 arr_Option2 = Option2.split("~");
                 arr_Option3= Option3.split("~");
                 arr_Option4 = Option4.split("~");

                 for(int i=0;i< arr_Qusno.length;i++){
                     historyxpensemodel lv =new historyxpensemodel(arr_Qusno[i],arr_Question[i],arr_Wrong[i],arr_Explain[i],arr_Answer[i],arr_Option1[i],arr_Option2[i],arr_Option3[i],arr_Option4[i]);
                     store_arraylist.add(lv);

                     adapter = new LocalAdapter(Checkwrong.this, store_arraylist);
                     List.setAdapter(adapter);




                 }

                // List.setAdapter(new LocalAdapter(Checkwrong.this, arr_Qusno.length));

             }
         } catch (Exception e) {
             System.out.println(TAG + " getValueFromCart Exception " + e.getMessage());
         }


     }

    private void checksingle() {
        if (!cursor.isLast()){

            cursor.moveToNext();
        }

        GetSQLiteDatabaseRecords();
    }

    private void GetSQLiteDatabaseRecords() {











        System.out.println("Questionno"+cursor.getString(1));


        System.out.println("Questoin"+cursor.getString(7));
        System.out.println("wronganser"+cursor.getString(3));
        System.out.println("Answer"+cursor.getString(6));
        System.out.println("Explains"+cursor.getString(8));
        System.out.println("Option1"+cursor.getString(9));

        System.out.println("Option2"+cursor.getString(10));

        System.out.println("Option3"+cursor.getString(11));

        System.out.println("Option4"+cursor.getString(12));

    }


    private class LocalAdapter  extends BaseAdapter {

        private LayoutInflater minflater;
        ViewHolder holder;
        private List<historyxpensemodel> storeValues = null;
        private ArrayList<historyxpensemodel> store_arraylist=null;

        int len = 0, arrlen = 0;

        public LocalAdapter(Context c, ArrayList<historyxpensemodel> store_arraylist) {
            minflater = LayoutInflater.from(c);
            this.storeValues = store_arraylist;
            this.store_arraylist = new ArrayList<historyxpensemodel>();
            this.store_arraylist.addAll(storeValues);
        }



        @Override
        public int getCount() {
            return storeValues.size();
        }

        @Override
        public Object getItem(int position) {
            return storeValues.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return getCount();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }


        public int getItemCount() {
            return storeValues.size();
        }











        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = minflater.inflate(R.layout.checkview, null);
                holder.Text1 = (TextView) convertView.findViewById(R.id.textView1);

                holder.Text2 = (TextView) convertView.findViewById(R.id.textView2);
                holder.textView3 = (RadioButton) convertView.findViewById(R.id.textView3);

                holder.Text4 = (RadioButton) convertView.findViewById(R.id.textView4);
                holder.Text5 = (RadioButton) convertView.findViewById(R.id.textView5);
                holder.Text6 = (RadioButton) convertView.findViewById(R.id.textView6);
holder.op1=(LinearLayout)convertView.findViewById(R.id.op1);
                holder.op2=(LinearLayout)convertView.findViewById(R.id.op2);
                holder.op3=(LinearLayout)convertView.findViewById(R.id.op3);
                holder.op4=(LinearLayout)convertView.findViewById(R.id.op4);
                holder.explain=(TextView)convertView.findViewById(R.id.explain);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();

            }











            if (arr_Qusno[position].contains("null") || arr_Question[position].contains("null") || arr_Wrong[position].contains("null") || arr_Explain[position].contains("null") || arr_Answer[position].contains("null") || arr_Option1[position].contains("null") || arr_Option2[position].contains("null") || arr_Option3[position].contains("null") || arr_Option4[position].contains("null")) {


                arr_Qusno[position] = arr_Qusno[position].replace("null", "");
                arr_Question[position] = arr_Question[position].replace("null", "");
                arr_Wrong[position] = arr_Wrong[position].replace("null", "");
                arr_Explain[position] = arr_Explain[position].replace("null", "");
                arr_Answer[position] = arr_Answer[position].replace("null", "");

                arr_Option1[position] = arr_Option1[position].replace("null", "");
                arr_Option2[position] = arr_Option2[position].replace("null", "");
                arr_Option3[position] = arr_Option3[position].replace("null", "");
                arr_Option4[position] = arr_Option4[position].replace("null", "");



                try {
                    System.out.println("check "+storeValues.get(position).getExplain());

                  // holder.Text3.setText("Your Answer:"+" "+storeValues.get(position).getwrong());
                    holder.Text1.setText(storeValues.get(position).getQno()+".");
                    holder.Text2.setText(" " +storeValues.get(position).getQuestion());

                    holder.textView3.setText(storeValues.get(position).getoption1());
                    holder.Text4.setText(storeValues.get(position).getoption2());
                    holder.Text5.setText(storeValues.get(position).getoption3());
                    holder.Text6.setText(storeValues.get(position).getoption4());


                    if(storeValues.get(position).getExplain().equals("null"))
                    {
                        holder.explain.setVisibility(View.GONE);
                    }else{
                        holder.explain.setText(storeValues.get(position).getExplain());
                    }


                    if (storeValues.get(position).getoption1() .equals(storeValues.get(position).getAnswer())){

                       // holder.op1.setBackgroundColor(Color.GREEN);
                        holder.op1.setBackground(getResources().getDrawable(R.drawable.rectangle,null));

                        int textColor = Color.parseColor("#4CC417");
                        holder. textView3.setButtonTintList(ColorStateList.valueOf(textColor));
                        holder.textView3.setChecked(true);
                    }else if (storeValues.get(position).getoption1() .equals(storeValues.get(position).getwrong())){
                       // holder.op1.setBackgroundColor(Color.RED);

                        int textColor = Color.parseColor("#FF0000");
                        holder. textView3.setButtonTintList(ColorStateList.valueOf(textColor));
                        holder.textView3.setChecked(true);
                        holder.op1.setBackground(getResources().getDrawable(R.drawable.red,null));
                    }else{
                        holder.op1.setBackground(getResources().getDrawable(R.drawable.ash,null));
                    }


                    if (storeValues.get(position).getoption2() .equals(storeValues.get(position).getAnswer())){

                        //holder.op2.setBackgroundColor(Color.GREEN);
                        holder.op2.setBackground(getResources().getDrawable(R.drawable.rectangle,null));
                        int textColor = Color.parseColor("#4CC417");
                        holder. Text4.setButtonTintList(ColorStateList.valueOf(textColor));
                        holder.Text4.setChecked(true);
                    }else if (storeValues.get(position).getoption2() .equals(storeValues.get(position).getwrong())){
                       // holder.op2.setBackgroundColor(Color.RED);
                        holder.op2.setBackground(getResources().getDrawable(R.drawable.red,null));
                        int textColor = Color.parseColor("#FF0000");
                        holder. Text4.setButtonTintList(ColorStateList.valueOf(textColor));
                        holder.Text4.setChecked(true);
                    }else{
                        holder.op2.setBackground(getResources().getDrawable(R.drawable.ash,null));
                    }
                    if (storeValues.get(position).getoption3() .equals(storeValues.get(position).getAnswer())){

                      //  holder.op3.setBackgroundColor(Color.GREEN);
                        int textColor = Color.parseColor("#4CC417");
                        holder. Text5.setButtonTintList(ColorStateList.valueOf(textColor));
                        holder.op3.setBackground(getResources().getDrawable(R.drawable.rectangle,null));
                        holder.Text5.setChecked(true);
                    }else if (storeValues.get(position).getoption3() .equals(storeValues.get(position).getwrong())){
                       // holder.op3.setBackgroundColor(Color.RED);
                        holder.op3.setBackground(getResources().getDrawable(R.drawable.red,null));
                        int textColor = Color.parseColor("#FF0000");
                        holder. Text5.setButtonTintList(ColorStateList.valueOf(textColor));
                        holder.Text5.setChecked(true);
                    }else{
                        holder.op3.setBackground(getResources().getDrawable(R.drawable.ash,null));
                    }

                    if (storeValues.get(position).getoption4() .equals(storeValues.get(position).getAnswer())){
                        int textColor = Color.parseColor("#4CC417");
                        holder. Text6.setButtonTintList(ColorStateList.valueOf(textColor));
                     //  holder.op4.setBackgroundColor(Color.GREEN);
                        holder.Text6.setChecked(true);
                        holder.op4.setBackground(getResources().getDrawable(R.drawable.rectangle,null));
                    }else if (storeValues.get(position).getoption4() .equals(storeValues.get(position).getwrong())){
                        //holder.op4.setBackgroundColor(Color.RED);
                        holder.op4.setBackground(getResources().getDrawable(R.drawable.red,null));
                        int textColor = Color.parseColor("#FF0000");
                        holder. Text6.setButtonTintList(ColorStateList.valueOf(textColor));
                        holder.Text6.setChecked(true);
                    }else{
                        holder.op4.setBackground(getResources().getDrawable(R.drawable.ash,null));
                    }

//                    holder.Text4.setText("Explanation:"+"  "+storeValues.get(position).getExplain());
//                    holder.Text5.setText("Correct Answer:" +" "+storeValues.get(position).getAnswer());






                } catch (Exception e) {

                }


            } else {
                try {

                    //holder.Text3.setText("Your Answer:"+" "+storeValues.get(position).getwrong());
                    holder.Text1.setText(storeValues.get(position).getQno()+".");
                    holder.Text2.setText(" "+storeValues.get(position).getQuestion());

                    holder.textView3.setText(storeValues.get(position).getoption1());
                    holder.Text4.setText(storeValues.get(position).getoption2());
                    holder.Text5.setText(storeValues.get(position).getoption3());
                    holder.Text6.setText(storeValues.get(position).getoption4());
                    holder.explain.setText(storeValues.get(position).getExplain());


                    if(storeValues.get(position).getExplain().equals("null"))
                    {
                        holder.explain.setVisibility(View.GONE);
                    }else{
                        holder.explain.setText(storeValues.get(position).getExplain());
                    }


                    if (storeValues.get(position).getoption1() .equals(storeValues.get(position).getAnswer())){

                        // holder.op1.setBackgroundColor(Color.GREEN);
                        holder.op1.setBackground(getResources().getDrawable(R.drawable.rectangle,null));

                        int textColor = Color.parseColor("#4CC417");
                        holder. textView3.setButtonTintList(ColorStateList.valueOf(textColor));

holder.textView3.setChecked(true);

                    }else if (storeValues.get(position).getoption1() .equals(storeValues.get(position).getwrong())){
                        // holder.op1.setBackgroundColor(Color.RED);
                        holder.op1.setBackground(getResources().getDrawable(R.drawable.red,null));
                        int textColor = Color.parseColor("#FF0000");
                        holder. textView3.setButtonTintList(ColorStateList.valueOf(textColor));
                        holder.textView3.setChecked(true);


                    }else{
                        holder.op1.setBackground(getResources().getDrawable(R.drawable.ash,null));
                    }


                    if (storeValues.get(position).getoption2() .equals(storeValues.get(position).getAnswer())){

                        //holder.op2.setBackgroundColor(Color.GREEN);
                        holder.op2.setBackground(getResources().getDrawable(R.drawable.rectangle,null));
                        int textColor = Color.parseColor("#4CC417");
                        holder. Text4.setButtonTintList(ColorStateList.valueOf(textColor));
                        holder.Text4.setChecked(true);
                    }else if (storeValues.get(position).getoption2() .equals(storeValues.get(position).getwrong())){
                        // holder.op2.setBackgroundColor(Color.RED);
                        holder.op2.setBackground(getResources().getDrawable(R.drawable.red,null));



                        int textColor = Color.parseColor("#FF0000");
                        holder. Text4.setButtonTintList(ColorStateList.valueOf(textColor));
                        holder.Text4.setChecked(true);


                    }else{
                        holder.op2.setBackground(getResources().getDrawable(R.drawable.ash,null));
                    }
                    if (storeValues.get(position).getoption3() .equals(storeValues.get(position).getAnswer())){

                        //  holder.op3.setBackgroundColor(Color.GREEN);
                        int textColor = Color.parseColor("#4CC417");
                        holder.Text5.setChecked(true);
                        holder. Text5.setButtonTintList(ColorStateList.valueOf(textColor));
                        holder.op3.setBackground(getResources().getDrawable(R.drawable.rectangle,null));
                    }else if (storeValues.get(position).getoption3() .equals(storeValues.get(position).getwrong())){
                        // holder.op3.setBackgroundColor(Color.RED);
                        holder.op3.setBackground(getResources().getDrawable(R.drawable.red,null));

                        int textColor = Color.parseColor("#FF0000");
                        holder. Text5.setButtonTintList(ColorStateList.valueOf(textColor));
                        holder.Text5.setChecked(true);
                    }else{
                        holder.op3.setBackground(getResources().getDrawable(R.drawable.ash,null));
                    }

                    if (storeValues.get(position).getoption4() .equals(storeValues.get(position).getAnswer())){
                        int textColor = Color.parseColor("#4CC417");
                        holder. Text6.setButtonTintList(ColorStateList.valueOf(textColor));
                        holder.Text6.setChecked(true);
                        //  holder.op4.setBackgroundColor(Color.GREEN);
                        holder.op4.setBackground(getResources().getDrawable(R.drawable.rectangle,null));
                    }else if (storeValues.get(position).getoption4() .equals(storeValues.get(position).getwrong())){
                        //holder.op4.setBackgroundColor(Color.RED);
                        holder.op4.setBackground(getResources().getDrawable(R.drawable.red,null));

                        int textColor = Color.parseColor("#FF0000");
                        holder. Text6.setButtonTintList(ColorStateList.valueOf(textColor));
                        holder.Text6.setChecked(true);
                    }else{
                        holder.op4.setBackground(getResources().getDrawable(R.drawable.ash,null));
                    }








//                    holder.Text4.setText("Explanation:"+"  "+storeValues.get(position).getExplain());
//                    holder.Text5.setText("Correct Answer:" +" "+storeValues.get(position).getAnswer());

                } catch (Exception e) {

                }

            }






            return convertView;
        }
        class ViewHolder {
            TextView Text1, Text2,explain;
            RadioButton textView3,Text4,Text5,Text6;
            Button deleted,buttons;

            LinearLayout op1,op2,op3,op4;

        }



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


            System.out.println(" getValues Check" + b);





        }
    }

//     private class LocalAdapter  extends BaseAdapter {
//
//        private LayoutInflater minflater;
//        ViewHolder holder;
//        int len = 0, arrlen = 0;
//
//        public LocalAdapter(Context name, int length) {
//            minflater = LayoutInflater.from(name);
//            len = length;
//        }
//
//        @Override
//        public int getCount() {
//
//            arrlen = len;
//            return arrlen;
//        }
//
//
//        @Override
//        public int getViewTypeCount() {
//            return getCount();
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//            return position;
//        }
//
//
//        @Override
//        public Object getItem(int position) {
//            return position;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            if (convertView == null) {
//                holder = new ViewHolder();
//                convertView = minflater.inflate(R.layout.checkview, null);
//                holder.text1 = (TextView) convertView.findViewById(R.id.textView1);
//
//                holder.text2 = (TextView) convertView.findViewById(R.id.textView2);
//              holder.text3 = (TextView) convertView.findViewById(R.id.textView3);
//
//                holder.text4 = (TextView) convertView.findViewById(R.id.textView4);
////               /* holder.buttons=(Button)convertView.findViewById(R.id.delete);
////                holder.edbuttons=(Button)convertView.findViewById(R.id.bt_edit);*/
////                /*holder.text5 = (TextView) convertView.findViewById(R.id.tv_city);*/
////                /*holder.buttons=(Button)convertView.findViewById(R.id.delete);
////                holder.button=(Button)convertView.findViewById(R.id.bt_edit);*/
//
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//
//            }
//
//
//
//
//            arr_Qusno=Qusno.split("~");
//            arr_Question = Question.split("~");
//            arr_Wrong = Wrong.split("~");
//            arr_Explain = Explain.split("~");
//
//
//
//
//            if (arr_Qusno[position].contains("null") ||arr_Question[position].contains("null") || arr_Wrong[position].contains("null") || arr_Explain[position].contains("null") ) {
//                arr_Qusno[position]=arr_Qusno[position].replace("null", "");
//
//                arr_Question[position] = arr_Question[position].replace("null", "");
//                arr_Wrong[position] = arr_Wrong[position].replace("null", "");
//                arr_Explain[position] = arr_Explain[position].replace("null", "");
//
//
//
//                try {
//
//                    holder.text1.setText(arr_Qusno[position]);
//                    holder.text2.setText(arr_Question[position]);
//                    holder.text3.setText(arr_Wrong[position]);
//                    holder.text4.setText(arr_Explain[position]);
//
//                    System.out.println("locadate"+arr_Explain[position]);
//
//
//                } catch (Exception e) {
//
//                }
//
//
//            } else {
//                try {
//                    holder.text1.setText(arr_Qusno[position]);
//                    holder.text2.setText(arr_Question[position]);
//                    holder.text3.setText(arr_Wrong[position]);
//                    holder.text4.setText(arr_Explain[position]);
//
//                } catch (Exception e) {
//
//                }
//
//            }
//
//
//
//
//            return convertView;
//        }
//        class ViewHolder {
//            TextView text1, text2,text3, text4,text5;
//            Button button,buttons,edbuttons;
//
//        }
//
//
//
//    }





}
