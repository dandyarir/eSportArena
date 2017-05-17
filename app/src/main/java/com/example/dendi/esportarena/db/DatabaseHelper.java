package com.example.dendi.esportarena.db;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by Dendi on 5/16/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public void queryData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public void insertData(String title, String date, String desc, String link, String location, byte[] image){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO EVENT VALUES (NULL,?, ?, ?,?,?,?)";

        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, title);
        statement.bindString(2, date);
        statement.bindString(3, desc);
        statement.bindString(4, link);
        statement.bindString(5, location);
        statement.bindBlob(6, image);

        statement.executeInsert();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE or Replace TABLE event " +
//                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
//                "title TEXT," +
//                "date TEXT, " +
//                "desc TEXT, " +
//                "link TEXT, " +
//                "location TEXT," +
//                "img BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXIST event");
//        db.execSQL("CREATE TABLE or Replace event " +
//                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
//                "title TEXT ," +
//                "date TEXT , " +
//                "desc TEXT, " +
//                "link TEXT, " +
//                "location TEXT," +
//                "img BLOB)");
    }
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
}
