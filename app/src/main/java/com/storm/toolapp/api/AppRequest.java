package com.storm.toolapp.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by jiang_ruicheng on 16/10/29.
 */
public class AppRequest {
    private AppRequest() {
    }


    public static String ACCOUNTURL = "";

    public static Api getAPI() {
        return new Retrofit.Builder().
                baseUrl(ACCOUNTURL).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                addConverterFactory(MyGsonConverterFactory.create()).
                build().
                create(Api.class);
    }
}
