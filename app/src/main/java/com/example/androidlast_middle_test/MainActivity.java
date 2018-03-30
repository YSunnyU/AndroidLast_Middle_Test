package com.example.androidlast_middle_test;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
//    我是物联网专业H1708C班张玗  正在考B场
    private TextView text_title;
    private RecyclerView recy_view;
    private MainAdapter mainAdapter;
    private List<DataClassBean.ResultBean.DataBean> data=new ArrayList<>();
    private OkHttpClient okHttpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        AppContext.context=this;
        mainAdapter=new MainAdapter(data,MainActivity.this);
        recy_view.setAdapter(mainAdapter);
        initData();
        //          缓存
        Interceptor interceptor=new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                okhttp3.Response response = chain.proceed(request);
                if (TextUtils.isEmpty(response.cacheControl().toString())){
                    int time;
                    if (isNetworkAvailable()){
                        time=60;

                    }else {
                        time=600;
                    }
                    okhttp3.Response response1 = response.newBuilder().addHeader("Cache-Control", "max-age=" + time).build();
                    return response1;

                }
                return response;
            }
        };
        Cache cache=new Cache(AppContext.context.getCacheDir(),1024*1024*10);
        okHttpClient=new OkHttpClient.Builder().cache(cache).addNetworkInterceptor(interceptor).build();

        initListener();
    }





   private void initListener() {
        mainAdapter.OnshortListener(new MainAdapter.setShortListener() {
            @Override
            public void setonClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, Two.class);
                intent.putExtra("url",data.get(position).getUrl());
                startActivity(intent);
//                Toast.makeText(MainActivity.this, "123243535435", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://v.juhe.cn/toutiao/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JieKouService jieKouService = retrofit.create(JieKouService.class);
        Call<DataClassBean> dataMess =jieKouService.getDataMess();
        dataMess.enqueue(new Callback<DataClassBean>() {
            @Override
            public void onResponse(Call<DataClassBean> call, Response<DataClassBean> response) {
                DataClassBean.ResultBean result = response.body().getResult();
                data.addAll(result.getData());
//                Toast.makeText(MainActivity.this, data.get(3).getTitle(), Toast.LENGTH_SHORT).show();

                mainAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<DataClassBean> call, Throwable t) {

            }
        });

    }

    private void initView() {
        text_title = (TextView) findViewById(R.id.text_title);
        recy_view = (RecyclerView) findViewById(R.id.recy_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recy_view.setLayoutManager(linearLayoutManager);
    }
    //    判断是否有网
    public boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager) AppContext.context.getApplicationContext().getSystemService(AppContext.context.CONNECTIVITY_SERVICE);

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
