package com.vega.domain.event;

/**
 * Component created on 19/11/2015.
 *
 * @author dmartin
 */
public class ErrorEvent {

    private Throwable error = null;

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public boolean hasError() {
        return error != null;
    }
}
