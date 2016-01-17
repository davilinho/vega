package com.vega.data.datasources.sqlite;


import com.vega.data.datasources.sqlite.manager.ISQLiteAppManager;

/**
 * Component created on 30/11/2015.
 *
 * @author dmartin
 */
public class SQLiteDataSource {

    private ISQLiteAppManager sqLiteAppManager;

    public SQLiteDataSource(ISQLiteAppManager sqLiteAppManager) {
        this.sqLiteAppManager = sqLiteAppManager;
    }

    protected ISQLiteAppManager getSqLiteAppManager() {
        return this.sqLiteAppManager;
    }

}
