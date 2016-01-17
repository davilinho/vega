package com.vega.data.datasources.realm;

import android.content.Context;

import io.realm.Realm;

/**
 * Component created on 11/01/2016.
 *
 * @author dmartin
 */
public class RealmDataSource {

    private Context context;
    private Realm realm;

    public RealmDataSource(Context context) {
        this.context = context;
    }

    protected void initRealm() {
        realm = Realm.getInstance(context);
    }

    protected Realm getRealmInstance() {
        if (realm == null) {
            initRealm();
        }
        return  realm;
    }

    protected void beginTransaction() {
        if (realm == null) {
            initRealm();
        }
        realm.beginTransaction();
    }

    protected void commitTransaction() {
        if (realm != null) {
            realm.commitTransaction();
        }
    }

    protected void closeConnection() {
        if (realm != null) {
            realm.close();
        }
    }

}
