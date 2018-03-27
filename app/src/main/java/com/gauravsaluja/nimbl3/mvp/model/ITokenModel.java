package com.gauravsaluja.nimbl3.mvp.model;

import com.gauravsaluja.nimbl3.network.request.TokenBody;
import com.gauravsaluja.nimbl3.network.response.Token;

import io.reactivex.Observable;

/**
 * Created by Gaurav Saluja on 24-Mar-18.
 */

public interface ITokenModel {
    Observable<Token> resultToken(TokenBody tokenBody);
}