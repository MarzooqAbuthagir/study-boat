package com.we_connect_students;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utilis {

    String TAG = "Utilis";

    static Context mcon;

    // Custom Progress Dialog
    public static ProgressDialog mProgressDialog;

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    public static String NAME = "empname", MOB_NUM = "mobnum", EMAIL = "email", STUDENT_ID = "indexid",FNAME="fname";
    public static String strName = "", strMobNum = "", strEmail = "", strStudentID = "",BUYID="0",strFname="";

    // Base Url
    public static String Api = "https://studyboat.app/portal/api/";

    // Api's

    public static String checkupdate = "checkversion";
    public static String logout = "logout";

    public static String checkdevice="checkdevice";

    public static String updatetoken="updatetoken";


    public static String register = "studentregistration";
    public static String login = "studentlogin";
    public static String chapter = "getunitschapterdetails";
    public static String progress = "progress";
    public static String allSubjectList = "viewsubject";
    public static String  savetestpackage="savetestpackage";
    public static String  checkchapter="insertlocalstorage";


    public static String  clearapi="cleardata";
    public static String  question="getquestions";
    public static String  reattampt="reattemptcount";
//    public static String  submit="saveanswer";
    public static String  submit="newsaveanswer";
    public static String  report="getreports";
    public static String packagess="notcompletedlist";
    public static String    profilecount="profilecount";
    public static String packagessnew="viewpackagelist";
    public static String    subcription="analysis";
    public static String    reportnew="detailedreport";
    public static String    overall="overallreport";
    public static String   learnsubjectlist="learnsubjectlist";
    public static String   learnsubjectlistunit="learnunitlist";
    public static String  learnsubjectlistchapte="learnchapterlist";
    public static String  learngetquestionlist="learngetquestionlist";
    public static String submitpack="buynow";

    public static String  questioncount="staticnumbers";
    public static String  questiontime="staticnumberstime";

    public static String  packdetails="getpackagedetails";
    public static String alert="alert";

    public static String check="checkpromocode";




    public static String getnews ="getnews";

//    public static String  requestion="retest";
    public static String  requestion="newretest";


    public static String videopackages = "videopackages";
    public static String videopackageslist ="getvideopackagedetails";
    public static String videosbuy="buynowvideo_web";
    public static String videosubjectlist="videosubjectlist";
    public static String videounitlist = "videounitlist";
    public static String videochapterlist = "videochapterlist";
    public  static String videotopiclist = "videotopiclist";
    public static String videolecture = "videolecture";

    public static String getvideochat ="getvideochats?";
    public static  String videocmds ="videocomments";



    public static String getsataes="getstate";
    public static String getcity="getcity";
    public static String checkcount="checkcount";

    public static String viewsubscriptionlist="viewsubscriptionlist";
    public static String viewvideosubscriptionlist="viewvideosubscriptionlist";

    public static String sendcredentials = "sendcredentials";
    public static String dynamicimage = "dynamicimage";
    public static String getexamdetails = "getexamdetails";
    public static String eventdetails="eventdetails";
    public static String eventgetquestions= "eventgetquestions";
    public static String eventsaveanswer="eventsaveanswer";
    public static String resetPassword="resetpassword";






    public Utilis(Context con) {
        // TODO Auto-generated constructor stub
        mcon = con;
    }

    public static boolean isAvailInternet() {
        // TODO Auto-generated method stub
        boolean chk = false;
        ConnectivityManager conMgr = (ConnectivityManager) mcon.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conMgr.getActiveNetworkInfo();
        chk = info != null && info.isConnected();
        return chk;
    }

    public static void showProgress(Context context) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(context.getResources().getString(R.string.progresstitle));
        mProgressDialog.show();
    }

    public static void dismissProgress() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSharedPreference() {
        sharedPreferences = mcon.getSharedPreferences("Details", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void getSharedPreference() {
        onSharedPreference();
        strName = sharedPreferences.getString(NAME, "");
        strFname = sharedPreferences.getString(FNAME, "");
        strMobNum = sharedPreferences.getString(MOB_NUM, "");
        strEmail = sharedPreferences.getString(EMAIL, "");
        strStudentID = sharedPreferences.getString(STUDENT_ID, "");
    }

    public static void onDeletePreferences() {
        sharedPreferences = mcon.getSharedPreferences("Details", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
        sharedPreferences.edit().remove(NAME).commit();
        sharedPreferences.edit().remove(FNAME).commit();
        sharedPreferences.edit().remove(MOB_NUM).commit();
        sharedPreferences.edit().remove(EMAIL).commit();
        sharedPreferences.edit().remove(STUDENT_ID).commit();
        sharedPreferences.edit().commit();
    }


}
