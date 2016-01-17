package com.vega.data.datasources.rest;

import android.util.Log;

import com.vega.data.service.RestClientService;

import java.io.IOException;

import retrofit.Call;

/**
 * Component created on 30/11/2015.
 *
 * @author dmartin
 */
public class RestDataSource<T> {

    private RestClientService restClientService;

    public RestDataSource(RestClientService restClientService) {
        this.restClientService = restClientService;
    }

    protected T executeRestService(Call<T> call) {

        try {
            return call.execute().body();
        } catch (IOException e) {
            Log.e("executeRestService", e.getMessage());
        }

        return null;
    }

    protected RestClientService getRestClientService() {
        return this.restClientService;
    }

}
