package com.vega.app.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.vega.app.core.application.VegaApplication;

/**
 * Broadcast que gestiona los cambios de estado de la conexión a internet
 */
public class ConnectionStatusReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        VegaApplication application = (VegaApplication) context.getApplicationContext();

        application.setConnected(isConnected(context));
    }

    private boolean isConnected(Context context) {
        return new ConnectionStatusChecker(context).isConnected();
    }
}
