package com.we_connect_students;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Mysingletonevideo {

    private static Mysingletonevideo mInstance;
    private RequestQueue requestQueue;
    private static Context mctx;

    private Mysingletonevideo(Context context){
        mctx = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue(){
        if (requestQueue==null)
            requestQueue = Volley.newRequestQueue(mctx.getApplicationContext());
            return requestQueue;

    }

    public static synchronized Mysingletonevideo getInstance(Context context){
        if (mInstance == null){
            mInstance = new Mysingletonevideo(context);
        }
        return mInstance;
    }

    public <T> void  addToRequestQue(Request<T> request){
        getRequestQueue().add(request);
    }

}
