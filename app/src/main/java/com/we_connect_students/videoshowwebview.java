package com.we_connect_students;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;



public class videoshowwebview extends AppCompatActivity {

    WebView webView;
    ProgressDialog pDialog;
    String id = "",price="",validity="",name="";
    String examid = "", str_messageresult = "";
    Utilis utilis;
    String   SUCCESS_URL=utilis.Api + utilis.alert;

    Button close;

    FragmentManager fm = null;
    //  DefaultNoNet defaultNoNet = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoshowwebview);


        fm = getSupportFragmentManager();


        webView = findViewById(R.id.webView);
        close=findViewById(R.id.close);
        Intent intent = getIntent();


        id = intent.getStringExtra("Qcount");
        examid = intent.getStringExtra("examid");
        str_messageresult = intent.getStringExtra("str_messageresult");
        name = intent.getStringExtra("name");
        validity = intent.getStringExtra("validity");
        price = intent.getStringExtra("price");


        pDialog = new ProgressDialog(videoshowwebview.this);
        pDialog.setMessage(videoshowwebview.this.getResources().getString(R.string.progresstitle));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        utilis = new Utilis(videoshowwebview.this);
        webView.setWebViewClient(new MyWebViewClient(this));
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebChromeClient(new MyWebChromeClient());
        webView.loadUrl(str_messageresult);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(videoshowwebview.this, videobuynow.class); //OrderDetailsActivity

                intent.putExtra("Qcount",id );
                intent.putExtra("examid",examid);
                intent.putExtra("name", name);
                intent.putExtra("validity",validity);
                intent.putExtra("price",price);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();




            }
        });
    }

    private class MyWebViewClient extends WebViewClient {
        private Context context;

        public MyWebViewClient(Context context) {
            this.context = context;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {


//            Toast.makeText(ShowWebView.this, "Page Started! " + url,
//                    Toast.LENGTH_SHORT).show();
//            if (url.equals(url)) {
//                Toast.makeText(ShowWebView.this, "Success! " + url,
//                        Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(ShowWebView.this, "Failure! " + url,
//                        Toast.LENGTH_SHORT).show();
//            }



            System.out.println("url"+url);
            if (url.contains("https://studyboat.app/portal/api/paysuccess")) {

                System.out.println("sucess");
                reDirectToApp(context);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            pDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pDialog.dismiss();
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.d("LogTag", message);
            result.confirm();
            return true;
        }
    }
    private void reDirectToApp(final Context context) {





        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setMessage(videoshowwebview.this.getResources().getString(R.string.reg_msg))
                    .setNeutralButton(videoshowwebview.this.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog

                            dialog.dismiss();

                            Intent intent = new Intent(videoshowwebview.this, PackageActivity.class); //OrderDetailsActivity

                            intent.putExtra("Qcount",id );
                            intent.putExtra("examid",examid);

                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    });


            AlertDialog alert = builder.create();
            alert.show();

            TextView tv_msg = (TextView) alert.findViewById(android.R.id.message);
            Button btn_ok = alert.getButton(DialogInterface.BUTTON_NEUTRAL);

//            tv_msg.setTypeface(app.tv_tf);
//            btn_ok.setTypeface(app.tv_tf);

            btn_ok.setTextColor(Color.parseColor("#000000"));

        } catch (Resources.NotFoundException e) {
            System.out.println( " NotFoundException " + e.getMessage());
        }



//
//        Intent intent = new Intent(ShowWebView.this, PackageActivity.class); //OrderDetailsActivity
//
//        intent.putExtra("Qcount",id );
//        intent.putExtra("examid",examid);
//
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        finish();


    }

    @Override
    public void onBackPressed() {


        Intent intent = new Intent(videoshowwebview.this, videobuynow.class); //OrderDetailsActivity

        intent.putExtra("Qcount",id );
        intent.putExtra("examid",examid);
        intent.putExtra("name", name);
        intent.putExtra("validity",validity);
        intent.putExtra("price",price);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


}
