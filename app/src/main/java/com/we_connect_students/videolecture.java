package com.we_connect_students;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.squareup.picasso.Picasso;
import com.we_connect_students.DataBase.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class videolecture extends AppCompatActivity {

    String TAG = "videotopiclistActivity";
    ListView listView;

    RecyclerView recyclerView;
    Toolbar toolbar;
    ActionBar actionBar = null;
    WebView webView;
//    VideoView videoView;
    Button button;
//    VimeoPlayerView vimeoPlayerView;
    TextView sub_title,explaination,topic,comds,imageselect;
    EditText commends;
    DBHelper dbHelper;
    Utilis utilis;
    String str_result = "";
    String str_message = "";
    String text="";
    String Chapterid="";
    String Subject="";
    String unitid="";
    String Topicid="";
    String subjectid="";
    String videoname="";
    String videolink;
    String topicname="";
    int videoid;
    String studentname="",studentcmd="",studentAttch="",adminattach="",admincmd="";
    FragmentManager fm = null;

    ArrayList<videofive> listvalues1 = new ArrayList<videofive>();

    int REQUEST_CODE = 1234;

    //  DefaultNoNet defaultNoNet = null;

    private  final int IMG_REQUEST = 1;
    private static final int PICK_FILE_REQUEST = 1;
    private String selectedFilePath;
    Uri selectedFileUri;

    Bitmap bitmap;

    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST =1 ;
//    private Bitmap bitmap;
    private String filePath;
    ImageView imageView;
    TextView textView;
    private String SERVER_URL = "https://studyboat.app/portal/CommentsFileUpload.php";




    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videolecture);

        fm = getSupportFragmentManager();

        utilis = new Utilis(videolecture.this);
        dbHelper=new DBHelper(videolecture.this);
        Window window = videolecture.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(videolecture.this,R.color.video));

        final Intent intent = getIntent();
        Subject = intent.getStringExtra("Subject");
        Chapterid = intent.getStringExtra("chapterid");
        unitid = intent.getStringExtra("Unitid");
        Topicid = intent.getStringExtra("topicid");
        subjectid = intent.getStringExtra("Subjectid");
        topicname = intent.getStringExtra("topicname");


        sub_title=findViewById(R.id.topic);
        explaination=findViewById(R.id.explain);
        topic=findViewById(R.id.subtitle);
        recyclerView=findViewById(R.id.videocmd);
        comds = findViewById(R.id.comds);
        commends = findViewById(R.id.commends);

        sub_title.setText(Subject);
        topic.setText(topicname);


//        videoView = findViewById(R.id.videoview);

        webView =findViewById(R.id.videoview);
        imageselect = findViewById(R.id.imageselect);



        button=findViewById(R.id.sendcmd);

        listvalues1= new ArrayList<>();

        allSubjectAPI(Topicid);

//        imageselect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if ((ContextCompat.checkSelfPermission(getApplicationContext(),
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
//                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
//                    if ((ActivityCompat.shouldShowRequestPermissionRationale(videolecture.this,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(videolecture.this,
//                            Manifest.permission.READ_EXTERNAL_STORAGE))) {
//
//                    } else {
//                        ActivityCompat.requestPermissions(videolecture.this,
//                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
//                                REQUEST_PERMISSIONS);
//                    }
//                } else {
//                    Log.e("Else", "Else");
//
//
//                    Intent intent = new Intent();
//                    intent.setType("*/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(intent.createChooser(intent, "Choose file to upload"), PICK_FILE_REQUEST);
//
//                }
//            }
//        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//               uploadBitmap(bitmap);
//                uploadFile(selectedFilePath);


            }
        });





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST) {
                if (data == null) {
                    //no data present
                    return;
                }


                Uri selectedFileUri = data.getData();
                selectedFilePath = FilePath.getPath(this, selectedFileUri);
                Log.i(TAG, "Selected File Path:" + selectedFilePath);

                if (selectedFilePath != null && !selectedFilePath.equals("")) {
//                    tvFileName.setText(selectedFilePath);
                } else {
                    Toast.makeText(this, "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //android upload file to server
//    public int uploadFile(final String selectedFilePath) {
//
//        int serverResponseCode = 0;
//
//        HttpURLConnection connection;
//        DataOutputStream dataOutputStream;
//        String lineEnd = "\r\n";
//        String twoHyphens = "--";
//        String boundary = "*****";
//
//
//        int bytesRead, bytesAvailable, bufferSize;
//        byte[] buffer;
//        int maxBufferSize = 1 * 1024 * 1024;
//        File selectedFile = new File(selectedFilePath);
//
//
//        String[] parts = selectedFilePath.split("/");
//        final String fileName = parts[parts.length - 1];
//
//        if (!selectedFile.isFile()) {
////            dialog.dismiss();
//
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
////                    tvFileName.setText("Source File Doesn't Exist: " + selectedFilePath);
//                }
//            });
//            return 0;
//        } else {
//            try {
//                FileInputStream fileInputStream = new FileInputStream(selectedFile);
//                URL url = new URL(SERVER_URL);
//                connection = (HttpURLConnection) url.openConnection();
//                connection.setDoInput(true);//Allow Inputs
//                connection.setDoOutput(true);//Allow Outputs
//                connection.setUseCaches(false);//Don't use a cached Copy
//                connection.setRequestMethod("POST");
//                connection.setRequestProperty("Connection", "Keep-Alive");
//                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
//                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
//                connection.setRequestProperty("uploaded_file", selectedFilePath);
//
//                //creating new dataoutputstream
//                dataOutputStream = new DataOutputStream(connection.getOutputStream());
//
//                //writing bytes to data outputstream
//                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
//                        + selectedFilePath + "\"" + lineEnd);
//
//                dataOutputStream.writeBytes(lineEnd);
//
//                //returns no. of bytes present in fileInputStream
//                bytesAvailable = fileInputStream.available();
//                //selecting the buffer size as minimum of available bytes or 1 MB
//                bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                //setting the buffer as byte array of size of bufferSize
//                buffer = new byte[bufferSize];
//
//                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
//                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//
//                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
//                while (bytesRead > 0) {
//                    //write the bytes read from inputstream
//                    dataOutputStream.write(buffer, 0, bufferSize);
//                    bytesAvailable = fileInputStream.available();
//                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//                }
//
//                dataOutputStream.writeBytes(lineEnd);
//                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
//
//                serverResponseCode = connection.getResponseCode();
//                String serverResponseMessage = connection.getResponseMessage();
//
//                Log.i(TAG, "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);
//
//                //response code of 200 indicates the server status OK
//                if (serverResponseCode == 200) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
////                            tvFileName.setText("File Upload completed.\n\n You can see the uploaded file here: \n\n" + "http://coderefer.com/extras/uploads/" + fileName);
//                        }
//                    });
//                }
//
//                //closing the input and output streams
//                fileInputStream.close();
//                dataOutputStream.flush();
//                dataOutputStream.close();
//
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(videolecture.this, "File Not Found", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                Toast.makeText(videolecture.this, "URL error!", Toast.LENGTH_SHORT).show();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                Toast.makeText(videolecture.this, "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
//            }
////            dialog.dismiss();
//            return serverResponseCode;
//        }
//
//    }


    private void allSubjectAPI( final String Topicid) {



        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(videolecture.this);

            StringRequest stringRequest
                    = new StringRequest(Request.Method.POST, utilis.Api + utilis.videolecture, new Response.Listener<String>() {
                @SuppressLint("SetJavaScriptEnabled")
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

                            JSONArray json = obj.getJSONArray("result");
                            System.out.println(TAG + " allSubjectAPI Length " + json.length());
                            for (int i = 0; i < json.length(); i++) {

                                JSONObject jsonObject = json.getJSONObject(i);

                                        videoname=jsonObject.getString("videoName");
                                        videolink= jsonObject.getString("videoLink");

//                                     practicelistthree.add(practicelistthrees);
//                                       explaination.setText(videoname);

                                 webView.setWebViewClient(new Browers_home());
                            webView.setWebChromeClient(new ChromeClient());

                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.getSettings().setAllowFileAccess(true);
                            webView.getSettings().setAppCacheEnabled(true);

                            webView.getSettings().setPluginState(WebSettings.PluginState.ON);
                            webView.getSettings().setBuiltInZoomControls(true);
                            webView.getSettings().setDisplayZoomControls(false);

                                   webView.loadUrl( videolink );
                            explaination.setText(videoname);
                            studentcmd(Topicid);

                                System.out.println("videolink"+"\""+videolink+"\"");




                            }


                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(videolecture.this, str_message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(videolecture.this, videolecture.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
                    System.out.println("chapterid"+Chapterid);
                    System.out.println("unitid"+unitid);
                    System.out.println("topicid"+Topicid);
                    System.out.println("subject"+Subject);

                    params.put("videoId",Topicid );

                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        }

    }




    private void studentcmd( final String Topicid) {



        if (utilis.isAvailInternet() == true) {

            utilis.showProgress(videolecture.this);

            StringRequest stringRequest
                    = new StringRequest(Request.Method.GET, utilis.Api +utilis.getvideochat+"videoId="+Topicid+"&studentIndexId="+utilis.strStudentID, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);

                        System.out.println(TAG + " allSubjectAPI response11 - " + response);

                        utilis.dismissProgress();

                        str_result = obj.getString("errorCode");
                        System.out.print(TAG + " allSubjectAPI result " + str_result);

                        if (Integer.parseInt(str_result) == 0) {

                            str_message = obj.getString("Message");

                            JSONArray json = obj.getJSONArray("result");
                            System.out.println(TAG + " allSubjectAPI Length " + json.length());
                            for (int i = 0; i < json.length(); i++) {

                                JSONObject jsonObject = json.getJSONObject(i);

                                videofive video =new videofive(
                              jsonObject.getString("student"),
                              jsonObject.getString("studentComments"),
                             jsonObject.getString("AdminComments"),
                              jsonObject.getString("AdminAttach"),
                              jsonObject.getString("studentAttach"));

                                listvalues1.add(video);



                            }

                            Recyclerviewadaptervideo adapter = new Recyclerviewadaptervideo(videolecture.this, listvalues1);
                            LinearLayoutManager llm = new LinearLayoutManager(videolecture.this);
                            recyclerView.setLayoutManager(llm);
                            recyclerView.setAdapter(adapter);



                        } else if (Integer.parseInt(str_result) == 2) {
                            str_message = obj.getString("Message");

                            Toast.makeText(videolecture.this, "Add No Comments", Toast.LENGTH_SHORT).show();
//                            comds.setText("Add No Comments");
//                            comds.setVisibility(View.VISIBLE);
                            utilis.dismissProgress();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    utilis.dismissProgress();
                    Toast.makeText(videolecture.this, videolecture.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();

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
            }) ;
            {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    utilis.getSharedPreference();
//                    System.out.println("id"+utilis.strStudentID);
//                    System.out.println("chapterid"+Chapterid);
//                    System.out.println("unitid"+unitid);
//                    System.out.println("topicid"+Topicid);
//                    System.out.println("subject"+Subject);
//
//                    params.put("videoId",Topicid );
//
//                    return params;
//                }
            }

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        }

    }


    class Recyclerviewadaptervideo extends RecyclerView.Adapter<Recyclerviewadaptervideo.MyViewHolder> {


        Context mcontext;
        ArrayList<videofive>listvalues1;

        public Recyclerviewadaptervideo(Context mcontext, ArrayList<videofive>listvalues1) {
            this.mcontext = mcontext;
            this.listvalues1 = listvalues1;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v;
            v = LayoutInflater.from(mcontext).inflate(R.layout.studentcmd,parent,false);
            Recyclerviewadaptervideo.MyViewHolder myViewHolder =new Recyclerviewadaptervideo.MyViewHolder(v);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            holder.studentname.setText(listvalues1.get(position).getStudent());
            holder.studentmsg.setText(listvalues1.get(position).getStudentComments());
            holder.staffmsg.setText(listvalues1.get(position).getAdminComments());

            String image = listvalues1.get(position).getStudentAttach();
            String image1 = listvalues1.get(position).getAdminAttach();

            if(image.equals("")){

//                holder.imagesend.setVisibility(View.INVISIBLE);


            }
            else {
//                holder.imagesend.setVisibility(View.VISIBLE);
//                holder.imagesend.setImageURI(Uri.parse(image));
//                Picasso.with(getApplication()).load(image).into(holder.imagesend);


            }

            if (image1.equals("")){
//                holder.imagerec.setVisibility(View.INVISIBLE);


            }
//            else
//                holder.imagerec.setVisibility(View.INVISIBLE);
//                Picasso.with(getApplication()).load(image1).into(holder.imagerec);


        }

        @Override
        public int getItemCount() {
            return listvalues1.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{



//            LinearLayout create,buy,viewdet,button;
            TextView studentname,studentmsg,staffmsg;
            ImageView imagesend,imagerec;


            public MyViewHolder(@NonNull View view) {
                super(view);

               studentname= view.findViewById(R.id.namestudent);
               studentmsg = view.findViewById(R.id.studentmsg);
                staffmsg = view.findViewById(R.id.msgstaff);
                imagesend = view.findViewById(R.id.imagesend);
                imagerec = view.findViewById(R.id.imagerec);


            }
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
//                back();
            }
        });
    }






    private class Browers_home extends WebViewClient {

        Browers_home(){}


        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }


        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }


    private class ChromeClient extends WebChromeClient {
        private View mCustomView;
        private CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        ChromeClient() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), Integer.parseInt(videolink));
        }

        public void onHideCustomView()
        {
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

//    private void cmdsend(){
//
//        if (utilis.isAvailInternet() == true) {
//
//            utilis.showProgress(videolecture.this);
//
//            StringRequest stringRequest
//                    = new StringRequest(Request.Method.POST, utilis.Api + utilis.videocmds, new Response.Listener<String>() {
//                @SuppressLint("SetJavaScriptEnabled")
//                @Override
//                public void onResponse(String response) {
//
//                    try {
//                        //converting response to json object
//                        JSONObject obj = new JSONObject(response);
//
//                        System.out.println(TAG + " allSubjectAPI response - " + response);
//
//                        utilis.dismissProgress();
//
//                        str_result = obj.getString("errorCode");
//                        System.out.print(TAG + " allSubjectAPI result " + str_result);
//
//                        if (Integer.parseInt(str_result) == 0) {
//
//                            str_message = obj.getString("Message");
//
//                            JSONArray json = obj.getJSONArray("result");
//                            System.out.println(TAG + " allSubjectAPI Length " + json.length());
////                            for (int i = 0; i < json.length(); i++) {
////
////                                JSONObject jsonObject = json.getJSONObject(i);
////
////
////
////                            }
//
//
//                        } else if (Integer.parseInt(str_result) == 2) {
//                            str_message = obj.getString("Message");
//
//                            Toast.makeText(videolecture.this, str_message, Toast.LENGTH_SHORT).show();
//
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                    utilis.dismissProgress();
//                    Toast.makeText(videolecture.this, videolecture.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();
//
//                    if (error instanceof NoConnectionError) {
//                        System.out.println("NoConnectionError");
//                    } else if (error instanceof TimeoutError) {
//                        System.out.println("TimeoutError");
//
//                    } else if (error instanceof ServerError) {
//                        System.out.println("ServerError");
//
//                    } else if (error instanceof AuthFailureError) {
//                        System.out.println("AuthFailureError");
//
//                    } else if (error instanceof NetworkError) {
//                        System.out.println("NetworkError");
//                    }
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    utilis.getSharedPreference();
//                    System.out.println("id"+utilis.strStudentID);
//
//                    System.out.println("topicid"+Topicid);
//
////                    commends.getText().toString();
//
//                    params.put("studentId",utilis.strStudentID);
//                    params.put("videoId",Topicid );
//                    params.put("comments",commends.getText().toString());
//                    params.put("image", String.valueOf(selectedFileUri));
//
//                    System.out.println(params);
//
//                    return params;
//                }
//            };
//
////            Mysingletonevideo.getInstance(  )
//            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
//
//        }
//
//    }



//    private void uploadBitmap(final Bitmap bitmap) {
//
//        FilePath volleyMultipartRequest = new FilePath(Request.Method.POST,utilis.Api + utilis.videocmds,
//                new Response.Listener<NetworkResponse>() {
//                    @Override
//                    public void onResponse(NetworkResponse response) {
//                                            try {
//                        //converting response to json object
//                        JSONObject obj = new JSONObject(String.valueOf(response));
//
//                        System.out.println(TAG + " allSubjectAPI response - " + response);
//
//                        utilis.dismissProgress();
//
//                        str_result = obj.getString("errorCode");
//                        System.out.print(TAG + " allSubjectAPI result " + str_result);
//
//                        if (Integer.parseInt(str_result) == 0) {
//
//                            str_message = obj.getString("Message");
//
//                            JSONArray json = obj.getJSONArray("result");
//                            System.out.println(TAG + " allSubjectAPI Length " + json.length());
////                            for (int i = 0; i < json.length(); i++) {
////
////                                JSONObject jsonObject = json.getJSONObject(i);
////
////
////
////                            }
//
//
//                        } else if (Integer.parseInt(str_result) == 2) {
//                            str_message = obj.getString("Message");
//
//                            Toast.makeText(videolecture.this, str_message, Toast.LENGTH_SHORT).show();
//
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        utilis.dismissProgress();
//                    Toast.makeText(videolecture.this, videolecture.this.getResources().getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();
//
//                    if (error instanceof NoConnectionError) {
//                        System.out.println("NoConnectionError");
//                    } else if (error instanceof TimeoutError) {
//                        System.out.println("TimeoutError");
//
//                    } else if (error instanceof ServerError) {
//                        System.out.println("ServerError");
//
//                    } else if (error instanceof AuthFailureError) {
//                        System.out.println("AuthFailureError");
//
//                    } else if (error instanceof NetworkError) {
//                        System.out.println("NetworkError");
//                    }
//                    }
//                }) {
//
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    utilis.getSharedPreference();
//                    System.out.println("id"+utilis.strStudentID);
//
//                    System.out.println("topicid"+Topicid);
//
////                    commends.getText().toString();
//
//                    params.put("studentId",utilis.strStudentID);
//                    params.put("videoId",Topicid );
//                    params.put("comments",commends.getText().toString());
//                    params.put("image","uploads"+selectedFilePath);
//
//                    System.out.println(params);
//
//                    return params;
//                }
//        };
//
//        //adding the request to volley
////        Volley.newRequestQueue(this).add(volleyMultipartRequest);
//    }


}




