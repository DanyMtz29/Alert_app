package com.example.DataBase;

import static android.provider.BaseColumns._ID;
import static com.example.DataBase.Table.*;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Contact {
    DataBase DB;
    public Contact(DataBase DB){
        this.DB=DB;
    }

    public long addContact(String name, String lastName, String number, String relation, long id_user){
        SQLiteDatabase sqlite = DB.getWritableDatabase();

        ContentValues container = new ContentValues();

        container.put(CONTACT_COL_NAME, name);
        container.put(CONTACT_COL_LASTNAME, lastName);
        container.put(CONTACT_COL_NUMBER, number);
        container.put(CONTACT_COL_RELATION, relation);
        container.put(CONTACT_COL_ID_USER, id_user);
        long idReturn =  sqlite.insert(CONTACT_TABLE, null, container);
        sqlite.close();
        return idReturn;
    }

    public Cursor readUserContacts(long userId){
        SQLiteDatabase sqlite = DB.getReadableDatabase();

        String query = "SELECT " +
                _ID + ", " +
                CONTACT_COL_NAME + ", " +
                CONTACT_COL_LASTNAME + ", " +
                CONTACT_COL_NUMBER + ", " +
                CONTACT_COL_RELATION +
                " FROM " + CONTACT_TABLE +
                " WHERE " + CONTACT_COL_ID_USER + " = ?";

        String[] args = {String.valueOf(userId)};
        return sqlite.rawQuery(query, args);
    }
    public int updateContact(long contactId, String name, String lastName, String number, String relation) {
        SQLiteDatabase sqlite = DB.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CONTACT_COL_NAME, name);
        values.put(CONTACT_COL_LASTNAME, lastName);
        values.put(CONTACT_COL_NUMBER, number);
        values.put(CONTACT_COL_RELATION, relation);

        return sqlite.update(CONTACT_TABLE, values, _ID + " = ?", new String[]{String.valueOf(contactId)});
    }

}
