package com.example.DataBase;

import static android.provider.BaseColumns._ID;
import static com.example.DataBase.Table.*;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class User {
    DataBase DB;
    public User(DataBase DB){
        this.DB=DB;
    }

    public long addUser(String name, String lastName, long id_address, String number, String photo){
        SQLiteDatabase sqlite = DB.getWritableDatabase();

        ContentValues container = new ContentValues();

        container.put(USER_COL_NAME, name);
        container.put(USER_COL_LASTNAME, lastName);
        container.put(USER_COL_ADRRESS, id_address);
        container.put(USER_COL_NUMBER, number);
        container.put(USER_COL_PHOTO, photo);
        long idReturn =  sqlite.insert(USER_TABLE, null, container);
        sqlite.close();
        return idReturn;
    }

    public Cursor readUser(){
        SQLiteDatabase sqlite = DB.getReadableDatabase();
        String query = "SELECT " +
                "u." + _ID + ", " +
                "u." + USER_COL_NAME + ", " +
                "u." + USER_COL_LASTNAME + ", " +
                "u." + USER_COL_NUMBER + ", " +
                "u." + USER_COL_PHOTO + ", " +
                "a." + ADDRESS_COL_CITY + ", " +
                "a." + ADDRESS_COL_COLONY + ", " +
                "a." + ADDRESS_COL_STREET + ", " +
                "a." + ADDRESS_COL_NUMBER + ", " +
                "a." + ADDRESS_COL_CP +
                " FROM " + USER_TABLE + " u" +
                " JOIN " + ADDRESS_TABLE + " a ON u." + USER_COL_ADRRESS + " = a." + _ID +
                " WHERE u." + _ID + " = ?";

        String[] args = {"1"};
        return sqlite.rawQuery(query, args);
    }

    public int updateUser(long id_user, String name, String lastName, String number, String photoPath) {
        SQLiteDatabase sqlite = DB.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_COL_NAME, name);
        values.put(USER_COL_LASTNAME, lastName);
        values.put(USER_COL_NUMBER, number);
        values.put(USER_COL_PHOTO, photoPath);

        return sqlite.update(USER_TABLE, values, _ID + " = ?", new String[]{String.valueOf(id_user)});
    }
}
