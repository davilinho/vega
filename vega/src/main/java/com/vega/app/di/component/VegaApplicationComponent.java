package com.vega.app.di.component;

import com.vega.app.core.activity.AbstractActivity;
import com.vega.app.core.fragment.AbstractFragment;
import com.vega.app.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Component created on 17/11/2015.
 *
 * @author dmartin
 */
@Singleton
@Component(
        modules = ApplicationModule.class
)
public interface VegaApplicationComponent {

    void inject(AbstractActivity activity);

    void inject(AbstractFragment fragment);

}
