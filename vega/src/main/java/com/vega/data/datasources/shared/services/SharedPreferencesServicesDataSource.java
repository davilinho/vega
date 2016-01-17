package com.vega.data.datasources.shared.services;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.vega.data.datasources.shared.SharedPreferencesDataSource;
import com.vega.repository.datasources.ISharedPreferencesDataSource;

/**
 * Component created on 24/11/2015.
 *
 * @author dmartin
 */
public class SharedPreferencesServicesDataSource extends SharedPreferencesDataSource
        implements ISharedPreferencesDataSource {

    public SharedPreferencesServicesDataSource(SharedPreferences preferences, Gson gson) {
        super(preferences, gson);
    }

}
