package com.we_connect_students;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShowWebView2 extends AppCompatActivity {
    WebView webView;
    ProgressDialog pDialog;
    String id = "",price="",validity="",name="";
    String examid = "", str_messageresult = "https://studyboat.app/terms_conditions.html";
    Utilis utilis;
    String   SUCCESS_URL=utilis.Api + utilis.alert;

    Button close;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.show_web_view2);
        webView = findViewById(R.id.webView);
        close=findViewById(R.id.close);

        startWebView("https://studyboat.app/terms_conditions.html");


//        pDialog = new ProgressDialog(ShowWebView2.this);
//        pDialog.setMessage(ShowWebView2.this.getResources().getString(R.string.progresstitle));
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
        utilis = new Utilis(ShowWebView2.this);
//        webView.loadUrl(str_messageresult);
//        webView.setWebViewClient(new ShowWebView2.MyWebViewClient(this));
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebChromeClient(new ShowWebView2.MyWebChromeClient());

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShowWebView2.this, RegisterActivity.class); //OrderDetailsActivity


                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();




            }
        });
    }
    private void startWebView(String url) {

        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        webView.setWebViewClient(new WebViewClient() {
            //ProgressDialog progressDialog;

            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //Show loader on url load
//            public void onLoadResource (WebView view, String url) {
//                if (progressDialog == null) {
//                    // in standard case YourActivity.this
//                    progressDialog = new ProgressDialog(ShowWebView2.this);
//                    progressDialog.setMessage("Loading...");
//                    progressDialog.show();
//                }
//            }
//            public void onPageFinished(WebView view, String url) {
//                try{
//                    if (progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                        progressDialog = null;
//                    }
//                }catch(Exception exception){
//                    exception.printStackTrace();
//                }
//            }

        });

        // Javascript inabled on webview
      //  webView.getSettings().setJavaScriptEnabled(true);

        // Other webview options
        /*
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);
        */

        /*
         String summary = "<html><body>You scored <b>192</b> points.</body></html>";
         webview.loadData(summary, "text/html", null);
         */

        //Load url in webview
        webView.loadUrl(url);


    }

    // Open previous opened link from history on webview when back button pressed

//    @Override
//    // Detect when the back button is pressed
//    public void onBackPressed() {
//        if(webView.canGoBack()) {
//            webView.goBack();
//        } else {
//            // Let the system handle the back button
//            super.onBackPressed();
//        }
//    }
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


    @Override
    public void onBackPressed() {


        Intent intent = new Intent(ShowWebView2.this, RegisterActivity.class); //OrderDetailsActivity



        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}