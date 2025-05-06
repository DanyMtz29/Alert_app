package com.example.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import static android.provider.BaseColumns._ID;
import static com.example.DataBase.Table.*;

public class DataBase extends SQLiteOpenHelper {

    String queryAddress;
    String queryUsers;
    String queryContacts;
    String queryAlerts;

    {
        queryAddress = "CREATE TABLE " + ADDRESS_TABLE + " ( " +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ADDRESS_COL_CITY + " VARCHAR(100), "+
                        ADDRESS_COL_COLONY + " VARCHAR(100), "+
                        ADDRESS_COL_STREET + " VARCHAR(100), "+
                        ADDRESS_COL_NUMBER + " INTEGER, "+
                        ADDRESS_COL_CP + " INTEGER);";
        queryUsers = "CREATE TABLE " + USER_TABLE + " ( "+
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        USER_COL_NAME + " VARCHAR(100), " +
                        USER_COL_LASTNAME + " VARCHAR(100), " +
                        USER_COL_ADRRESS + " INTEGER, " +
                        USER_COL_CREATED + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                        USER_COL_NUMBER + " VARCHAR(15), " +
                        USER_COL_PHOTO + " VARCHAR(200), " +
                        "FOREIGN KEY ("+USER_COL_ADRRESS+") REFERENCES "+ADDRESS_TABLE+"("+_ID+"));";
        queryContacts = "CREATE TABLE " + CONTACT_TABLE + " ( "+
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CONTACT_COL_NAME + " VARCHAR(100), " +
                CONTACT_COL_LASTNAME + " VARCHAR(100), " +
                CONTACT_COL_NUMBER + " VARCHAR(15), " +
                CONTACT_COL_RELATION + " VARCHAR(50), " +
                CONTACT_COL_ID_USER + " INTEGER, " +
                "FOREIGN KEY ("+CONTACT_COL_ID_USER+") REFERENCES "+USER_TABLE+"("+_ID+") ON DELETE CASCADE );";
        queryAlerts = "CREATE TABLE " + ALERT_TABLE + " ( "+
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ALERT_COL_ID_USER + " INTEGER, " +
                ALERT_COL_CREATED + " TIMESTAMP, " +
                ALERT_COL_LAT + " REAL, " +
                ALERT_COL_LON + " REAL, " +
                ALERT_COL_PHOTO + " VARCHAR(200), " +
                "FOREIGN KEY ("+ALERT_COL_ID_USER+") REFERENCES "+USER_TABLE+"("+_ID+") ON DELETE CASCADE);";
    }

    public DataBase(@Nullable Context context) {
        super(context, "Mujer_Segura_DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(queryAddress);
        db.execSQL(queryUsers);
        db.execSQL(queryContacts);
        db.execSQL(queryAlerts);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropAddress = "DROP TABLE IF EXISTS "+ADDRESS_TABLE+ ";";
        String dropUser = "DROP TABLE IF EXISTS "+USER_TABLE+ ";";
        String dropContact = "DROP TABLE IF EXISTS "+CONTACT_TABLE+ ";";
        String dropAlert = "DROP TABLE IF EXISTS "+ALERT_TABLE+ ";";
        db.execSQL(dropAddress);
        db.execSQL(dropUser);
        db.execSQL(dropContact);
        db.execSQL(dropAlert);
        onCreate(db);
    }
}
