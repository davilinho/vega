package com.vega.app.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Clase encargada de detectar el estado de la conexión a internet
 */
public class ConnectionStatusChecker {

    private ConnectivityManager connectivityManager;

    public ConnectionStatusChecker(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public boolean isConnected() {

        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return (activeNetworkInfo != null) && (activeNetworkInfo.isConnectedOrConnecting());
    }
}
