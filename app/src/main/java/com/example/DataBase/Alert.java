package com.example.DataBase;

import static android.provider.BaseColumns._ID;
import static com.example.DataBase.Table.*;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Alert {
    DataBase DB;
    public Alert(DataBase DB){
        this.DB=DB;
    }

    public long addAlert(long id_user, double lat, double lon, String photoPath) {
        SQLiteDatabase sqlite = DB.getWritableDatabase();

        ContentValues container = new ContentValues();
        container.put(ALERT_COL_ID_USER, id_user);
        container.put(ALERT_COL_LAT, lat);
        container.put(ALERT_COL_LON, lon);
        container.put(ALERT_COL_PHOTO, photoPath);
        container.put(ALERT_COL_CREATED, System.currentTimeMillis());

        long idReturn = sqlite.insert(ALERT_TABLE, null, container);
        sqlite.close();
        return idReturn;
    }
    public Cursor readAlert(long userId){
        SQLiteDatabase sqlite = DB.getReadableDatabase();

        String query = "SELECT " +
                _ID + ", " +
                ALERT_COL_CREATED + ", " +
                ALERT_COL_LAT + ", " +
                ALERT_COL_LON + ", " +
                ALERT_COL_CREATED +
                " FROM " + ALERT_TABLE +
                " WHERE " + ALERT_COL_ID_USER + " = ?";

        String[] args = {String.valueOf(userId)};
        return sqlite.rawQuery(query, args);
    }

    public int updateAlert(long alertId, double lat, double lon, String photoPath) {
        SQLiteDatabase sqlite = DB.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ALERT_COL_LAT, lat);
        values.put(ALERT_COL_LON, lon);
        values.put(ALERT_COL_PHOTO, photoPath);

        return sqlite.update(ALERT_TABLE, values, _ID + " = ?", new String[]{String.valueOf(alertId)});
    }

}
