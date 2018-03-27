package com.gauravsaluja.nimbl3.mvp.model;

import com.gauravsaluja.nimbl3.network.CommunicationLink;
import com.gauravsaluja.nimbl3.network.request.TokenBody;
import com.gauravsaluja.nimbl3.network.response.Token;

import io.reactivex.Observable;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public class TokenModel implements ITokenModel {

    @Override
    public Observable<Token> resultToken(TokenBody tokenBody) {
        return CommunicationLink.actionGetToken(tokenBody);
    }
}