package com.gauravsaluja.nimbl3.network;

import com.gauravsaluja.nimbl3.network.request.TokenBody;
import com.gauravsaluja.nimbl3.network.response.Survey;
import com.gauravsaluja.nimbl3.network.response.Token;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public class CommunicationLink {

    // generate token
    public static Observable<Token> actionGetToken(TokenBody tokenBody) {
        NimblService service = CommunicationApi.getNimblService();
        return service.getToken(tokenBody);
    }

    // retrieve surveys
    public static Observable<List<Survey>> actionGetSurveys(int page, int perPage, String accessToken) {
        NimblService service = CommunicationApi.getNimblService();
        return service.getSurveys(page, perPage, accessToken);
    }
}