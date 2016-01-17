package com.vega.app.core.activity.di;

import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Component created on 03/12/2015.
 *
 * @author dmartin
 */
public class ActivityViewInjector {

    private AppCompatActivity activity;

    public ActivityViewInjector(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void inject(int layoutId) {
        if (layoutId != 0) {
            activity.setContentView(layoutId);
            ButterKnife.bind(activity);
        }
    }

}
