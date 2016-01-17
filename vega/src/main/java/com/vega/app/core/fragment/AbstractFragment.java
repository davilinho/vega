package com.vega.app.core.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vega.app.core.annotation.AnnotationChecker;
import com.vega.app.core.application.VegaApplication;
import com.vega.app.core.fragment.di.FragmentViewInjector;
import com.vega.presentation.presenter.IPresenter;
import com.vega.presentation.presenter.Presenter;
import com.vega.presentation.view.IBaseView;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Component created on 03/12/2015.
 *
 * @author dmartin
 */
public abstract class AbstractFragment extends Fragment {

    @Presenter
    private List<IPresenter> presenterList;

    private FragmentViewInjector fragmentViewInjector;

    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragmentViewInjector();
        injectDependencies();
        injectComponentDependences();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injectViewDependencies(inflater, container);
        bindViewDepencencies();
        bindPresenter();
        attachView();
        return recoverView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewCreatedImplementation(view, savedInstanceState);
    }

    protected abstract void onViewCreatedImplementation(View view, Bundle savedInstanceState);

    protected abstract int getFragmentLayout();

    protected abstract void injectComponentDependences();

    protected abstract List<IPresenter> getPresenterList();

    @Override
    public void onResume() {
        super.onResume();

        if (checkHasPresenter()) {
            for (IPresenter presenter : presenterList) {
                presenter.onResume();
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();

        if (checkHasPresenter()) {
            for (IPresenter presenter : presenterList) {
                presenter.onPause();
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (checkHasPresenter()) {
            for (IPresenter presenter : presenterList) {
                presenter.detachView();
            }
        }

    }

    private void initFragmentViewInjector() {
        fragmentViewInjector = new FragmentViewInjector(this);
    }

    private void injectDependencies() {
        ((VegaApplication) getActivity().getApplication()).getComponent().inject(this);
    }

    private void injectViewDependencies(LayoutInflater inflater, ViewGroup container) {
        view = fragmentViewInjector.inject(inflater, container, getFragmentLayout());
    }

    private void bindViewDepencencies() {
        fragmentViewInjector.bind(view);
    }

    private void injectViews(final View view) {
        ButterKnife.bind(this, view);
    }

    private void attachView() {
        if (checkHasPresenter()) {
            for (IPresenter presenter : presenterList) {
                presenter.attachView((IBaseView) this);
            }
        }
    }

    private View recoverView() {
        return view;
    }

    private boolean checkHasPresenter() {
        return presenterList != null && !presenterList.isEmpty();
    }

    private void bindPresenter() {

        if (getPresenterList() != null) {
            AnnotationChecker.addPresentedAnnotated(getActivity());
            presenterList = getPresenterList();
        }

    }

}
