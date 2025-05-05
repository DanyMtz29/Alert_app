package com.example.DataBase;

import static android.provider.BaseColumns._ID;
import static com.example.DataBase.Table.*;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class Address {
    DataBase DB;
    public Address(DataBase DB){
        this.DB=DB;
    }

    public long addAddress(String city, String colony, String street, int number, int cp){
        SQLiteDatabase sqlite = DB.getWritableDatabase();

        ContentValues container = new ContentValues();

        container.put( ADDRESS_COL_CITY, city );
        container.put( ADDRESS_COL_COLONY, colony );
        container.put( ADDRESS_COL_STREET, street);
        container.put( ADDRESS_COL_NUMBER, number );
        container.put( ADDRESS_COL_CP, cp );

        return sqlite.insert(ADDRESS_TABLE, null, container);
    }

    public int updateAddress(long id_address, String city, String colony, String street, int number, int cp) {
        SQLiteDatabase sqlite = DB.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ADDRESS_COL_CITY, city);
        values.put(ADDRESS_COL_COLONY, colony);
        values.put(ADDRESS_COL_STREET, street);
        values.put(ADDRESS_COL_NUMBER, number);
        values.put(ADDRESS_COL_CP, cp);

        return sqlite.update(ADDRESS_TABLE, values, _ID + " = ?", new String[]{String.valueOf(id_address)});
    }

}
