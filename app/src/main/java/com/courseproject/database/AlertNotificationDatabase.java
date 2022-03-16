package com.courseproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.courseproject.model.AlertNotification;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class AlertNotificationDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "alertdatabase";
    private static final String TABLE_NAME = "alertdb";
    private static final String ID = "nid";
    private static final String TYPE = "ntype";
    private static final String OCCURENCE = "noccurence";
    private static final String DATE = "ndate";
    private SQLiteDatabase sqLiteDatabase;

    public AlertNotificationDatabase(@Nullable Context context) {

        super(context, DATABASE_NAME, null, 3);
        sqLiteDatabase = getReadableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TYPE + " VARCHAR(125),"
                + OCCURENCE + " VARCHAR(255),"
                + DATE + " LONG"
                + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void add(AlertNotification data) {

        ContentValues cv = new ContentValues();
        cv.put(TYPE, data.getType());
        cv.put(OCCURENCE, data.getOccurence());
        cv.put(DATE, data.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        sqLiteDatabase.insert(TABLE_NAME, null, cv);

    }

    public Map<Integer, AlertNotification> get() {

        Map<Integer, AlertNotification> list = new HashMap<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                AlertNotification each = new AlertNotification(cursor);
                list.put(each.getId(), each);
            } while (cursor.moveToNext());
        }
        return list;

    }


    public void remove(int id) {

        sqLiteDatabase.delete(TABLE_NAME, ID + "=" + id, null);

    }

    public void remove(AlertNotification alerts) {

        if (alerts != null) {
            remove(alerts.getId());
        }

    }

}