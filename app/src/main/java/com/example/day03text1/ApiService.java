package com.example.day03text1;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by 雪碧 on 2019/10/28.
 */

public interface ApiService {
    String url="http://gank.io/api/data/%E7%A6%8F%E5%88%A9/";
    @GET("20/1")
    Call<Bean> mBean();
}
