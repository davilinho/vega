package com.vega.presentation.presenter;

import com.vega.domain.event.DataEvent;
import com.vega.domain.usecase.IUseCase;
import com.vega.presentation.view.IBaseView;

/**
 * Component created on 25/11/2015.
 *
 * @author dmartin
 */
public interface IPresenter<T1, T2, V extends IBaseView> {

    String SUBSCRIBER_METHOD_NAME = "subscriberPresenterResponse";

    void attachView(V view);

    void detachView();

    void onResume();

    void onPause();

    void executePresenterRequest(T1 data, IUseCase<T1> useCase);

    V getView();

    void choreographerCallback(DataEvent<T2> event);

    /** {@inheritDoc} It's required to call the super class method called "choreographerCallback" */
    void subscriberPresenterResponse(DataEvent<T2> event);

}
