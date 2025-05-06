package com.example.DataBase;

import static android.provider.BaseColumns._ID;
import static com.example.DataBase.Table.*;

import android.content.Context;
import android.database.Cursor;

import com.example.alert_app.Alerta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Controller {
    DataBase DB;

    public Controller(Context context) {
        DB = new DataBase(context);
    }

    public long insertUser(String name, String lastName, long id_address, String number, String photo) {
        User user = new User(DB);
        return user.addUser(name, lastName, id_address, number, photo);
    }

    public long insertAddress(String city, String colony, String street, int number, int cp) {
        Address ad = new Address(DB);
        return ad.addAddress(city, colony, street, number, cp);
    }

    public long insertContact(String name, String lastName, String number, String relation, long id_user) {
        Contact contact = new Contact(DB);
        return contact.addContact(name, lastName, number, relation, id_user);
    }

    public long insertAlert(long id_user, double lat, double lon, String photoPath) {
        Alert a = new Alert(DB);
        return a.addAlert(id_user, lat, lon, photoPath);
    }

    public int updateUser(long id_user, String name, String lastName, String number, String photoPath) {
        User user = new User(DB);
        return user.updateUser(id_user, name, lastName, number, photoPath);
    }

    public int updateAddress(long id_address, String city, String colony, String street, int number, int cp) {
        Address address = new Address(DB);
        return address.updateAddress(id_address, city, colony, street, number, cp);
    }

    public int updateContact(long contactId, String name, String lastName, String number, String relation) {
        Contact contact = new Contact(DB);
        return contact.updateContact(contactId, name, lastName, number, relation);
    }

    public int updateAlert(long alertId, double lat, double lon, String photoPath) {
        Alert alert = new Alert(DB);
        return alert.updateAlert(alertId, lat, lon, photoPath);
    }

    public String[] readUser() {
        User user = new User(DB);
        Cursor cursor = user.readUser();
        String data[] = new String[10];
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            data[0] = String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            data[1] = cursor.getString(cursor.getColumnIndexOrThrow(USER_COL_NAME));
            data[2] = cursor.getString(cursor.getColumnIndexOrThrow(USER_COL_LASTNAME));
            data[3] = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(USER_COL_NUMBER)));
            data[4] = cursor.getString(cursor.getColumnIndexOrThrow(ADDRESS_COL_CITY));
            data[5] = cursor.getString(cursor.getColumnIndexOrThrow(ADDRESS_COL_COLONY));
            data[6] = cursor.getString(cursor.getColumnIndexOrThrow(ADDRESS_COL_STREET));
            data[7] = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(ADDRESS_COL_NUMBER)));
            data[8] = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(ADDRESS_COL_CP)));
            data[9] = cursor.getString(cursor.getColumnIndexOrThrow(USER_COL_PHOTO));
        }
        cursor.close();
        return data;
    }

    public String[] readContact(long id_user) {
        Contact contact = new Contact(DB);
        Cursor cursorContacts = contact.readUserContacts(id_user);
        String data[] = new String[8];
        if (cursorContacts.getCount() > 0) {
            cursorContacts.moveToFirst();
            data[0] = cursorContacts.getString(cursorContacts.getColumnIndexOrThrow(CONTACT_COL_NAME));
            data[1] = cursorContacts.getString(cursorContacts.getColumnIndexOrThrow(CONTACT_COL_LASTNAME));
            data[2] = cursorContacts.getString(cursorContacts.getColumnIndexOrThrow(CONTACT_COL_NUMBER));
            data[3] = cursorContacts.getString(cursorContacts.getColumnIndexOrThrow(CONTACT_COL_RELATION));
            if (cursorContacts.moveToNext()) {
                data[4] = cursorContacts.getString(cursorContacts.getColumnIndexOrThrow(CONTACT_COL_NAME));
                data[5] = cursorContacts.getString(cursorContacts.getColumnIndexOrThrow(CONTACT_COL_LASTNAME));
                data[6] = cursorContacts.getString(cursorContacts.getColumnIndexOrThrow(CONTACT_COL_NUMBER));
                data[7] = cursorContacts.getString(cursorContacts.getColumnIndexOrThrow(CONTACT_COL_RELATION));
            }
        }
        cursorContacts.close();
        return data;
    }

    public String[] readAlert(long id_user) {
        Alert alert = new Alert(DB);
        Cursor cursorAlert = alert.readAlert(id_user);
        String data[] = new String[3];
        if (cursorAlert.getCount() != 0) {
            cursorAlert.moveToFirst();
            data[0] = String.valueOf(cursorAlert.getDouble(cursorAlert.getColumnIndexOrThrow(ALERT_COL_LON)));
            data[1] = String.valueOf(cursorAlert.getDouble(cursorAlert.getColumnIndexOrThrow(ALERT_COL_LAT)));
            String createdAtStr = cursorAlert.getString(cursorAlert.getColumnIndexOrThrow(ALERT_COL_CREATED));
            long millis = Long.parseLong(createdAtStr);
            Date date = new Date(millis);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            data[2] = sdf.format(date);
        }
        cursorAlert.close();
        return data;
    }

    public void resetDatabase(Context context) {
        context.deleteDatabase("Mujer_Segura_DB"); // Usa el nombre real de tu base
    }

    public long[] getContactIds(long id_user) {
        Contact contact = new Contact(DB);
        Cursor cursor = contact.readUserContacts(id_user);
        long[] ids = {-1, -1};

        if (cursor.moveToFirst()) {
            ids[0] = cursor.getLong(cursor.getColumnIndexOrThrow(_ID));
            if (cursor.moveToNext()) {
                ids[1] = cursor.getLong(cursor.getColumnIndexOrThrow(_ID));
            }
        }

        cursor.close();
        return ids;
    }

    public long getUserAddressId(long id_user) {
        User user = new User(DB);
        Cursor cursor = user.readUser();
        long id_address = -1;

        if (cursor.moveToFirst()) {
            id_address = cursor.getLong(cursor.getColumnIndexOrThrow(USER_COL_ADRRESS));
        }

        cursor.close();
        return id_address;
    }

    public ArrayList<Alerta> readAllAlerts(long id_user) {
        Alert alert = new Alert(DB);
        Cursor cursor = alert.readAllAlerts(id_user);
        ArrayList<Alerta> lista = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                double lat = cursor.getDouble(cursor.getColumnIndexOrThrow(ALERT_COL_LAT));
                double lon = cursor.getDouble(cursor.getColumnIndexOrThrow(ALERT_COL_LON));
                String createdAtStr = cursor.getString(cursor.getColumnIndexOrThrow(ALERT_COL_CREATED));
                String photoPath = cursor.getString(cursor.getColumnIndexOrThrow(ALERT_COL_PHOTO));

                long millis = Long.parseLong(createdAtStr);
                Date date = new Date(millis);

                String ubicacion = "https://maps.google.com/?q=" + lat + "," + lon;
                String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date);
                Alerta a = new Alerta(fecha, ubicacion, photoPath);
                lista.add(a);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }
}
