package com.vega.app.core.eventbus;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * Clase que extiende Bus para dar soporte a comunicación entre distintos Threads.
 *
 * Solución propuesta por JakeWharton en https://github.com/square/otto/issues/38
 */
public class AndroidBus extends Bus {

	/** Thread principal */
    private final Handler mainThread = new Handler(Looper.getMainLooper());

    @Override
    public void post(final Object event) {

        if (Looper.myLooper() == Looper.getMainLooper()) {

            super.post(event);

        } else {

            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    post(event);
                }
            });

        }
    }
}
