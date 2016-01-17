package com.vega.app.core.fragment.di;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Component created on 03/12/2015.
 *
 * @author dmartin
 */
public class FragmentViewInjector {

    private Fragment fragment;

    public FragmentViewInjector(Fragment fragment) {
        this.fragment = fragment;
    }

    public View inject(LayoutInflater inflater, ViewGroup container, int fragmentLayout) {
        return inflater.inflate(fragmentLayout, container, false);
    }

    public void bind(View view) {
        ButterKnife.bind(fragment, view);
    }

}
