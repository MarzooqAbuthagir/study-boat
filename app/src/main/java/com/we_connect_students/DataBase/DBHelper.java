package com.we_connect_students.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper {
    String TAG = "DBHelper";
    public String MY_DATABASE_NAME = "student.db";
    public static final String Login = "Login_Credentials";
    public static final String Subject = "AddCart_Credentials";
   public static final String question = "question_Credentials";
    public static final String questionresponse = "questionresponse_Credentials";
    public static final String questionslist = "questionslist_Credentials";
    public static final String selctedlist = "selctedlist_Credentials";
    public static final String questionanswer = "questionanswer_Credentials";


//    public static final String Language = "Lang_Credentials";

    public static SQLiteDatabase vatbook;
    Context con;

    public DBHelper(Context con) {
        vatbook = con.openOrCreateDatabase(MY_DATABASE_NAME, 0, null);
        this.con = con;
    }

    public void CreateTable(int i) {
        // TODO Auto-generated method stub
        switch (i) {

            case 1:
                /* Login credentials */
                try {
                    vatbook.execSQL("create table if not exists "
                            + Login
                            + " (lid integer primary key autoincrement, status TEXT(50), storeID TEXT(50), indexID TEXT(50), storeName TEXT(50), storeAddress TEXT(50), storeMobNum TEXT(50), trn TEXT(50), ownerName TEXT(50), ownerMob TEXT(50), ownerEmail TEXT(50), encryptID TEXT(100), role TEXT(50), counterIndexID TEXT(50), counterName TEXT(50), counterEncryptID TEXT(100), policy TEXT(500))");
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                break;

            case 2:
                /* AddCart credentials */
                try {
                    vatbook.execSQL("create table if not exists "
                            + Subject
                            + " (id integer primary key autoincrement, status TEXT(50),classCode TEXT(50), subjectName TEXT(50), subjectid TEXT(50), noquestion TEXT(50), testname TEXT(50), repetwrong TEXT(50), negativemark TEXT(50), unitname TEXT(50), chaptername TEXT(50))");
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                break;

            case 3:
                /* InVoice credentials */
                try {
                    vatbook.execSQL("create table if not exists "
                            + question
                            + " (id integer primary key autoincrement, questionno TEXT(50), correctanswer TEXT(50), wronganswer TEXT(50),Acount TEXT(50))");
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                break;


            case 4:
                /* InVoice credentials */
                try {
                    vatbook.execSQL("create table if not exists "
                            + questionresponse
                            + " (id integer primary key autoincrement, Questionno integer(50), Question TEXT(50), option1 TEXT(50),option2 TEXT(50),option3 TEXT(50),option4 TEXT(50),Answer TEXT(50),Explanation TEXT (1000),subjectName TEXT (50),chapterName TEXT (50),questionIndexId TEXT(30),sectionid TEXT(10))");
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                break;

            case 5:
                /* InVoice credentials */
                try {
                    vatbook.execSQL("create table if not exists "
                            + questionslist
                            + " (id integer primary key autoincrement, testname integer(50), subject TEXT(50), unit TEXT(50),chapter TEXT(50),userid TEXT(50))");
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                break;



            case 6:
                /* InVoice credentials */
                try {
                    vatbook.execSQL("create table if not exists "
                            + selctedlist
                            + " (id integer primary key autoincrement, Questionno integer(50), correctanser TEXT(100), wronganser TEXT(100),selectedanser TEXT(100),Acount TEXT(50),Answer TEXT(200),Questoin TEXT(300),Explains TEXT(1000),Option1 TEXT(200),Option2 TEXT(200),Option3 TEXT(200),Option4 TEXT(200), Wrongquestion TEXT(100),subjectName TEXT (50),chapterName TEXT (50),questionIndexId TEXT(30),isCorrect TEXT(10))");
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                break;

            case 7:
                /* InVoice credentials */
                try {
                    vatbook.execSQL("create table if not exists "
                            + questionanswer
                            + " (id integer primary key autoincrement, Answer TEXT(200),Questoin TEXT(300),Explains TEXT(1000),questionNumber TEXT(20))");
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                break;


//            case 4:
//                /* Lang_Credentials */
//                try {
//                    vatbook.execSQL("create table if not exists "
//                            + Language
//                            + "(Lid integer primary key autoincrement, language TEXT)");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
        }

    }

    public String getTableAsString(String tableName) {
        Log.d(TAG, "getTableAsString called");
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows = vatbook.rawQuery("SELECT itemName, itemQty, itemVatAmt, itemPrice FROM " + tableName, null);
        if (allRows.moveToFirst()) {
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name : columnNames) {
                    tableString += String.format("%s: %s", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }

        return tableString;
    }

    public String tableToString(String tableName) {
        Log.d("", "tableToString called");
        String tableString = "";//String.format("Table %s:\n", tableName);
        Cursor allRows = vatbook.rawQuery("SELECT itemName, itemQty, itemVatAmt, itemPrice FROM " + tableName, null);
        tableString += cursorToString(allRows);
        return tableString;
    }

    public String cursorToString(Cursor cursor) {
        String cursorColumnString = "", cursorString = "";
        if (cursor.moveToFirst()) {
            String[] columnNames = cursor.getColumnNames();
            for (String name : columnNames)
                cursorColumnString += String.format("%s ][ ", name);
            cursorColumnString += "\n";
            do {
                for (String name : columnNames) {
                    cursorString += String.format("%s \t \t \t \t \t ",
                            cursor.getString(cursor.getColumnIndex(name)));
                }
                cursorString += "\n";
            } while (cursor.moveToNext());
        }
        return cursorString;
    }
}
