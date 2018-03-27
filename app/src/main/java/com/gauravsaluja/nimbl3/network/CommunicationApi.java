package com.gauravsaluja.nimbl3.network;

import com.gauravsaluja.nimbl3.utils.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public class CommunicationApi {

    public static NimblService service;

    public static NimblService getNimblService() {
        if (service == null) {
            service = restAdapterGen(Constants.API_BASE_URL);
        }

        return service;
    }

    public static NimblService restAdapterGen(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getDefaultOkHttpClient())
                .build();

        NimblService service = retrofit.create(NimblService.class);
        return service;
    }

    private static OkHttpClient getDefaultOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .build();

                return chain.proceed(request);
            }
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(httpLoggingInterceptor);
        builder.interceptors().add(headerInterceptor);
        OkHttpClient client = builder.build();

        return client;
    }
}