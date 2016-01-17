package com.vega.data.datasources.realm.services;

import android.content.Context;

import com.vega.data.datasources.realm.RealmDataSource;
import com.vega.repository.datasources.IDataBaseDataSource;

/**
 * Component created on 11/01/2016.
 *
 * @author dmartin
 */
public class RealmServicesDataSource extends RealmDataSource implements IDataBaseDataSource {

    public RealmServicesDataSource(Context context) {
        super(context);
    }
}
