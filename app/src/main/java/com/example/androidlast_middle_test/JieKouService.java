package com.example.androidlast_middle_test;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by 张玗 on 2018/3/14.
 */

public interface JieKouService {
    @GET("index?type=top&key=097060266650f67b2cebd2a06aded587")
    Call<DataClassBean> getDataMess();
}
