package com.vega.presentation.presenter;

import com.squareup.otto.Bus;
import com.vega.domain.event.DataEvent;
import com.vega.domain.event.ErrorEvent;
import com.vega.domain.usecase.IUseCase;
import com.vega.domain.usecase.invoker.Invoker;
import com.vega.presentation.annotation.AnnotationChecker;
import com.vega.presentation.view.IBaseView;

import java.lang.ref.WeakReference;

/**
 * Presenter base con la funcionalidad base de todas los demás Presenters.
 * @param <V> View representation
 */
public abstract class AbstractPresenter<T1, T2, V extends IBaseView> implements IPresenter<T1, T2, V> {

    private Bus bus;
    private Invoker<T1> invoker;
    private WeakReference<V> view;

    public AbstractPresenter(Bus bus, Invoker<T1> invoker) {
        this.bus = bus;
        this.invoker = invoker;

        AnnotationChecker.subscribeAnnotationChecker(getClass());

    }

    @Override
    public void attachView(V view) {
        this.view = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void onResume() {
        registerEventBus();
    }

    @Override
    public void onPause() {
        unRegisterEventBus();
    }

    @Override
    public void executePresenterRequest(T1 data, IUseCase<T1> useCase) {
        invoker.execute(data, useCase);
    }

    @Override
    public V getView() {

        if (view == null) {
            throw new NullPointerException("View is NULL.");
        }

        return view.get();

    }

    @Override
    public void choreographerCallback(DataEvent<T2> event) {

        if (event.hasError()) {
            manageCommonError(event);
            manageSpecificError(event);
        } else {
            manageView(event);
        }

    }

    protected abstract void manageView(DataEvent<T2> event);

    protected abstract void manageSpecificError(DataEvent<T2> event);

    private void registerEventBus() {
        bus.register(this);
    }

    private void unRegisterEventBus() {
        bus.unregister(this);
    }

    private void manageCommonError(ErrorEvent event) {

        if (getView() != null) {
            getView().manageCommonErrors();
        }

    }

}
