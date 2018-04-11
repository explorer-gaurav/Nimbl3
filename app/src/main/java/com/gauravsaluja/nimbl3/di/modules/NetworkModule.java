package com.gauravsaluja.nimbl3.di.modules;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gaurav Saluja on 27-Mar-18.
 */

@Module
public class NetworkModule {

    public static final String NIMBL3 = "nimbl3";
    private String baseUrl;

    public NetworkModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    @Singleton
    public String provideBaseUrl() {
        return baseUrl;
    }

    @Provides
    @Singleton
    @Named(NIMBL3)
    public Retrofit provideRetrofitRestAdapter(String baseUrl) {
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}