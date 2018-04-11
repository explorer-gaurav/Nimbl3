package com.gauravsaluja.nimbl3;

import com.gauravsaluja.nimbl3.mvp.contract.SurveyContract;
import com.gauravsaluja.nimbl3.mvp.presenter.SurveyPresenter;
import com.gauravsaluja.nimbl3.network.request.TokenBody;
import com.gauravsaluja.nimbl3.network.response.Survey;
import com.gauravsaluja.nimbl3.network.response.Token;
import com.gauravsaluja.nimbl3.network.service.SurveyService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Gaurav Saluja on 10-Apr-18.
 */

@RunWith(MockitoJUnitRunner.class)
public class PresenterTests {

    @Mock
    SurveyContract.View surveyView;
    @Mock
    SurveyService surveyService;

    @InjectMocks
    SurveyPresenter surveyPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadView_ViewHasZeroInteractions() {
        verifyZeroInteractions(surveyView);
    }

    @Test
    public void testSuccess_GenerateToken() {
        surveyPresenter.setView(surveyView);

        final Token token = new Token("Access Token", "Token Type", 0, 0);
        when(surveyService.resultToken(any(TokenBody.class)))
                .thenReturn(Observable.just(token));

        surveyPresenter.generateToken();

        verify(surveyView, times(1)).onSuccessGetToken(token);
        verify(surveyView, times(1)).onCompleteGetToken();
        verify(surveyView, times(0)).onErrorGetToken();
    }

    @Test
    public void testFailure_GenerateToken() {
        surveyPresenter.setView(surveyView);

        Exception exception = new Exception();

        when(surveyService.resultToken(any(TokenBody.class)))
                .thenReturn(Observable.<Token>error(exception));

        surveyPresenter.generateToken();

        verify(surveyView, times(0)).onSuccessGetToken(any(Token.class));
        verify(surveyView, times(0)).onCompleteGetToken();
        verify(surveyView, times(1)).onErrorGetToken();
    }

    @Test
    public void testSuccess_GetSurveys() {
        surveyPresenter.setView(surveyView);

        int page = 1;
        int perPage = 10;
        String accessToken = "";

        final List<Survey> surveyList = new ArrayList<>();
        surveyList.add(new Survey("id", "title", "description", "coverImageUrl"));

        when(surveyService.resultSurveys(anyInt(), anyInt(), anyString()))
                .thenReturn(Observable.just(surveyList));

        surveyPresenter.fetchSurveys(page, perPage, accessToken);

        verify(surveyView, times(1)).onSuccessGetSurveys(surveyList);
        verify(surveyView, times(1)).onCompleteGetSurveys();
        verify(surveyView, times(0)).onErrorGetSurveys();
    }

    @Test
    public void testFailure_GetSurveys() {
        surveyPresenter.setView(surveyView);

        Exception exception = new Exception();

        int page = 1;
        int perPage = 10;
        String accessToken = "";

        when(surveyService.resultSurveys(anyInt(), anyInt(), anyString()))
                .thenReturn(Observable.<List<Survey>>error(exception));

        surveyPresenter.fetchSurveys(page, perPage, accessToken);

        verify(surveyView, times(0)).onSuccessGetSurveys(any(List.class));
        verify(surveyView, times(0)).onCompleteGetSurveys();
        verify(surveyView, times(1)).onErrorGetSurveys();
    }
}