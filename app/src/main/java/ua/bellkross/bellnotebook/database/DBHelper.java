package ua.bellkross.bellnotebook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import ua.bellkross.bellnotebook.model.Contact;

import static ua.bellkross.bellnotebook.model.Constants.*;

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper instance;

    private DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        instance = this;
    }

    public int addDB(Contact contact){
        SQLiteDatabase db = getWritableDatabase();
        db.query(TABLE_NAME, null, null, null, null, null, NAME_TAG);
        ContentValues contentValues = new ContentValues();
        contentValues.put(POSITION_IN_LIST_TAG, contact.getPositionInList());
        contentValues.put(PHOTO_URI_TAG, contact.getPhotoUri());
        contentValues.put(NAME_TAG, contact.getName());
        contentValues.put(SURNAME_TAG, contact.getSurname());
        contentValues.put(NUMBER_TAG, contact.getNumber());
        contentValues.put(BIRTHDAY_TAG, contact.getBirthday());
        contentValues.put(ADDITIONAL_INFORMATION_TAG, contact.getAdditionalInformation());
        int pos = (int) db.insert(TABLE_NAME, null, contentValues);
        db.close();
        contentValues.clear();
        return pos;
    }

    public void updateDB(String id, Contact contact){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(POSITION_IN_LIST_TAG, contact.getPositionInList());
        contentValues.put(PHOTO_URI_TAG, contact.getPhotoUri());
        contentValues.put(NAME_TAG, contact.getName());
        contentValues.put(SURNAME_TAG, contact.getSurname());
        contentValues.put(NUMBER_TAG, contact.getNumber());
        contentValues.put(BIRTHDAY_TAG, contact.getBirthday());
        contentValues.put(ADDITIONAL_INFORMATION_TAG, contact.getAdditionalInformation());
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        db.close();
        contentValues.clear();
    }

    public void removeDB(String id){
        if(!id.equalsIgnoreCase("")) {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(TABLE_NAME, ID_TAG + " = " + id, null);
            db.close();
        }
    }

    public ArrayList<Contact> sortContacts(){
        int positionInList = -1;
        ArrayList<Contact> contacts = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, NAME_TAG);
        if(cursor.moveToFirst()) {
            int contactId = cursor.getColumnIndex(ID_TAG);
            int photoUriIndex = cursor.getColumnIndex(PHOTO_URI_TAG);
            int nameIndex = cursor.getColumnIndex(NAME_TAG);
            int surnameIndex = cursor.getColumnIndex(SURNAME_TAG);
            int numberIndex = cursor.getColumnIndex(NUMBER_TAG);
            int birthdayIndex = cursor.getColumnIndex(BIRTHDAY_TAG);
            int additionalInformationIndex = cursor.getColumnIndex(ADDITIONAL_INFORMATION_TAG);
            do {
                contacts.add(new Contact(++positionInList,cursor.getInt(contactId), cursor.getString(photoUriIndex),cursor.getString(nameIndex),
                        cursor.getString(surnameIndex), cursor.getString(numberIndex),
                        cursor.getString(birthdayIndex), cursor.getString(additionalInformationIndex)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contacts;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                ID_TAG + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                POSITION_IN_LIST_TAG + " INTEGER NOT NULL," +
                PHOTO_URI_TAG + " TEXT NOT NULL," +
                NAME_TAG + " TEXT NOT NULL," +
                SURNAME_TAG + " TEXT NOT NULL," +
                NUMBER_TAG + " TEXT NOT NULL," +
                BIRTHDAY_TAG + " TEXT NOT NULL," +
                ADDITIONAL_INFORMATION_TAG + " TEXT NOT NULL" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public synchronized static DBHelper getDBHelper(Context context) {
        if(instance==null)
            return new DBHelper(context.getApplicationContext());
        else
            return instance;
    }

    public synchronized static DBHelper getDBHelper() {
        return instance;
    }
}
