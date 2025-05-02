package com.example.DataBase;

import android.provider.BaseColumns;

public class Table {

    //USER
    public static final String USER_TABLE = "user";
    public static final String USER_COL_NAME= "name";
    public static final String USER_COL_LASTNAME = "last_name";
    public static final String USER_COL_NUMBER = "number";
    public static final String USER_COL_CREATED = "created_at";
    public static final String USER_COL_ADRRESS = "id_address";
    public static final String USER_COL_PHOTO = "photo";


    //ADRESS
    public static final String ADDRESS_TABLE = "address";
    public static final String ADDRESS_COL_CITY= "city";
    public static final String ADDRESS_COL_COLONY = "colony";
    public static final String ADDRESS_COL_NUMBER = "number";
    public static final String ADDRESS_COL_STREET = "street";
    public static final String ADDRESS_COL_CP = "cp";

    //Contacts
    public static final String CONTACT_TABLE = "contact";
    public static final String CONTACT_COL_NAME= "name";
    public static final String CONTACT_COL_LASTNAME = "last_name";
    public static final String CONTACT_COL_NUMBER = "number";
    public static final String CONTACT_COL_RELATION = "relation";
    public static final String CONTACT_COL_ID_USER = "id_user";

    //Alert
    public static final String ALERT_TABLE = "alert";
    public static final String ALERT_COL_ID_USER= "id_user";
    public static final String ALERT_COL_CREATED = "created_at";
    public static final String ALERT_COL_LAT = "ubication_lat";
    public static final String ALERT_COL_LON = "ubication_lon";
    public static final String ALERT_COL_PHOTO = "photo";

    public Table(){}
}


