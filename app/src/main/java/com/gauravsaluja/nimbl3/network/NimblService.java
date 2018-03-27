package com.gauravsaluja.nimbl3.network;

import com.gauravsaluja.nimbl3.network.request.TokenBody;
import com.gauravsaluja.nimbl3.network.response.Survey;
import com.gauravsaluja.nimbl3.network.response.Token;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public interface NimblService {

    @POST("oauth/token")
    Observable<Token> getToken(@Body TokenBody body);

    @GET("surveys.json")
    Observable<List<Survey>> getSurveys(@Query("page") int page, @Query("per_page") int perPage,
                                        @Query("access_token") String accessToken);
}