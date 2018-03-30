package com.example.androidlast_middle_test;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 张玗 on 2018/3/14.
 */

public class MyData {
    private OkHttpClient okHttpClient;
    private static MyData myData;
    public static MyData getMyDataer(MyData myData){
        if (myData==null){
            myData=new MyData();
        }
        return myData;
    }
    private MyData(){
//        缓存
        Interceptor interceptor=new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                if (TextUtils.isEmpty(response.cacheControl().toString())){
                    int time=0;
                    if (isNetworkAvailable()){
                        time=60;

                    }else {
                        time=600;
                    }
                    Response response1 = response.newBuilder().addHeader("Cache-Control", "max-age=" + time).build();
                    return response1;

                }
                Cache cache=new Cache(AppContext.context.getCacheDir(),1024*1024*10);
                okHttpClient=new OkHttpClient.Builder().cache(cache).build();
                return response;
            }
        };
    }

   /* public void doGet(){
        OkHttpClient ok_http = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().build();
//        ok_http.newCall(request).

    };*/

//    判断是否有网
    public boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager) AppContext.context
                .getApplicationContext().getSystemService(AppContext.context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }

        return true;
    }
}
