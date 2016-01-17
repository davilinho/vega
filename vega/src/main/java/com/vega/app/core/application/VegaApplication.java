package com.vega.app.core.application;

import android.support.multidex.MultiDexApplication;

import com.karumi.dexter.Dexter;
import com.vega.app.di.component.VegaApplicationComponent;
import com.vega.app.di.module.ApplicationModule;

/**
 * Component created on 17/11/2015.
 *
 * @author dmartin
 */
public class VegaApplication extends MultiDexApplication {

    private VegaApplicationComponent component;

    /* Contiene true si el dispositivo esta conectado a internet */
    private boolean connected;

    @Override
    public void onCreate() {
        super.onCreate();
        initDependencyInjector();
        initDexter();
    }

    private void initDependencyInjector() {
        //component = DaggerVegaApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    private void initDexter() {
        Dexter.initialize(this);
    }

    public VegaApplicationComponent getComponent() {
        return component;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
