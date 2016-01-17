package com.vega.data.datasources.sqlite.manager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.vega.data.datasources.sqlite.helper.AbstractSQLiteOpenHelper;

import java.io.File;
import java.util.List;

/**
 * Component created on 02/12/2015.
 *
 * @author dmartin
 */
public abstract class SQLiteAppManager implements ISQLiteAppManager {

    protected boolean isOpenSQLiteDB(SQLiteDatabase sqliteDataBase) {
        return sqliteDataBase != null && sqliteDataBase.isOpen();
    }

    protected synchronized void closeSQLiteDB(SQLiteDatabase sqliteDataBase) {
        if (sqliteDataBase != null) {
            sqliteDataBase.close();
        }
    }

    @Override
    public SQLiteDatabase getSQLiteDatabaseInstance(AbstractSQLiteOpenHelper openHelper) {
        return openHelper.getWritableDatabase();
    }

    @Override
    public void deleteSQLiteDB(String path) {
        SQLiteDatabase.deleteDatabase(new File(path));
    }

    @Override
    public Cursor executeQuery(SQLiteDatabase sqliteDataBase, String sqlQuery, List<String> sqlQueryParams)
        throws SQLiteException {

        int count = 0;

        String[] sqlQueryParamsArray = new String[sqlQueryParams.size()];

        for (String param : sqlQueryParams) {
            sqlQueryParamsArray[count++] = param;
        }

        if (sqlQuery != null) {
            return sqliteDataBase.rawQuery(sqlQuery, sqlQueryParamsArray);
        } else {
            throw new SQLiteException("SQLite error occurs when executeQuery method is executing.");
        }

    }

    @Override
    public boolean executeInsert(SQLiteDatabase sqliteDataBase, String tableName, List<String> fieldParams,
        List<String> valueParams) throws SQLiteException {

        ContentValues values = new ContentValues();

        if (fieldParams != null && valueParams != null && fieldParams.size() == valueParams.size()) {

            for (int i = 0; i < fieldParams.size(); i++) {
                values.put(fieldParams.get(i), valueParams.get(i));
            }

        } else {
            throw new SQLiteException("SQLite error occurs when executeInsert method is executing.");
        }

        return (sqliteDataBase.insert(tableName, null, values) != -1);

    }

    @Override
    public boolean executeUpdate(SQLiteDatabase sqliteDataBase, String tableName, List<String> fieldParams,
        List<String> valueParams, String whereClause) throws SQLiteException {

        ContentValues values = new ContentValues();

        if (fieldParams != null && valueParams != null && fieldParams.size() == valueParams.size()) {

            for (int i = 0; i < fieldParams.size(); i++) {
                values.put(fieldParams.get(i), valueParams.get(i));
            }

        } else {
            throw new SQLiteException("SQLite error occurs when executeUpdate method is executing.");
        }

        return (sqliteDataBase.update(tableName, values, whereClause, null) != -1);

    }

    @Override
    public boolean executeUpdate(SQLiteDatabase sqliteDataBase, String tableName, List<String> fieldParams,
        List<String> valueParams, String[] whereParams) throws SQLiteException {

        ContentValues values = new ContentValues();

        if (fieldParams != null && valueParams != null && fieldParams.size() == valueParams.size()) {

            for (int i = 0; i < fieldParams.size(); i++) {
                values.put(fieldParams.get(i), valueParams.get(i));
            }

        } else {
            throw new SQLiteException("SQLite error occurs when executeUpdate method is executing.");
        }

        return (sqliteDataBase.update(tableName, values, null, whereParams) != -1);

    }

    @Override
    public boolean executeDelete(SQLiteDatabase sqliteDataBase, String tableName, String whereClause) {
        return (sqliteDataBase.delete(tableName, whereClause, null) != -1);
    }

    @Override
    public boolean executeDelete(SQLiteDatabase sqliteDataBase, String tableName, String[] whereParams) {
        return (sqliteDataBase.delete(tableName, null, whereParams) != -1);
    }

}
