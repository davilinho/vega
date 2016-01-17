package com.vega.domain.event;

/**
 * Component created on 19/11/2015.
 *
 * @author dmartin
 */
public class DataEvent<T> extends ErrorEvent {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
