package com.vega.data.datasources.sqlite.manager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.vega.data.datasources.sqlite.helper.AbstractSQLiteOpenHelper;

import java.util.List;

/**
 * Component created on 02/12/2015.
 *
 * @author dmartin
 */
public interface ISQLiteAppManager {

    SQLiteDatabase getSQLiteDatabaseInstance(AbstractSQLiteOpenHelper openHelper);

    void closeSQLiteDB();

    void deleteSQLiteDB(String path);

    Cursor executeQuery(SQLiteDatabase sqliteDataBase, String sqlQuery, List<String> sqlQueryParams)
        throws SQLiteException;

    boolean executeInsert(SQLiteDatabase sqliteDataBase, String tableName, List<String> fieldParams,
                          List<String> valueParams) throws SQLiteException;

    boolean executeUpdate(SQLiteDatabase sqliteDataBase, String tableName, List<String> fieldParams,
                          List<String> valueParams, String whereClause) throws SQLiteException;

    boolean executeUpdate(SQLiteDatabase sqliteDataBase, String tableName, List<String> fieldParams,
                          List<String> valueParams, String[] whereParams) throws SQLiteException;

    boolean executeDelete(SQLiteDatabase sqliteDataBase, String tableName, String whereClause);

    boolean executeDelete(SQLiteDatabase sqliteDataBase, String tableName, String[] whereParams);

}
