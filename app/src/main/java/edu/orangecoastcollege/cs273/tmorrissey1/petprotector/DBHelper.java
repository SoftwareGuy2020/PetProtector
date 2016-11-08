package edu.orangecoastcollege.cs273.tmorrissey1.petprotector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;

/** Database helper class to handle the database interactions
 * Created by Travis on 11/8/2016.
 */

public class DBHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "PetProtector";
    private static final String DATABASE_TABLE = "Pets";
    private static final int DATABASE_VERSION = 1;

    private static final String KEY_FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_DETAILS = "details";
    private static final String FIELD_PHONE_NUMBER = "phone_number";
    private static final String FIELD_IMAGE_URI = "image_uri";

    /**
     * Constructor
     * @param context   The context
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table = "CREATE TABLE " + DATABASE_TABLE + "(" + KEY_FIELD_ID + " INTEGER PRIMARY KEY "
                + "AUTOINCREMENT, " + FIELD_NAME + " TEXT, " + FIELD_DETAILS + " TEXT, "
                + FIELD_PHONE_NUMBER + " TEXT, " + FIELD_IMAGE_URI + " TEXT" + ")";
        db.execSQL(table);
    }

    /**
     * Called when database needs to be updated.
     * @param db the database
     * @param oldVersion The old version
     * @param newVersion The new version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    /**
     * Called to add a pet to the database
     * @param pet The pet to be added
     */
    public void addPet(Pet pet) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_NAME, pet.getName());
        values.put(FIELD_DETAILS, pet.getDetails());
        values.put(FIELD_PHONE_NUMBER, pet.getPhone());
        values.put(FIELD_IMAGE_URI, pet.getImageURI().toString());

        db.insert(DATABASE_TABLE, null, values);
        db.close();
    }

    /**
     * Gets all pets in database
     * @return ArrayList of Pets.
     */
    public ArrayList<Pet> getAllPets() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Pet> list = new ArrayList<>();
        Cursor results = db.query(DATABASE_TABLE, null, null, null, null, null, null);

        if (results.moveToFirst()) {
            do {
                list.add(new Pet(results.getInt(0), results.getString(1), results.getString(2),
                        results.getString(3), Uri.parse(results.getString(4))));
            } while (results.moveToNext());
        }
        results.close();
        db.close();
        return list;
    }

    /**
     * Deletes all pets in database
     */
    public void deleteAllPets() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DATABASE_TABLE, null, null);
        db.close();
    }

    /**
     * Updates pet in database.
     * @param pet The pet to be updated.
     */
    public void updatePet(Pet pet){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_NAME, pet.getName());
        values.put(FIELD_DETAILS, pet.getDetails());
        values.put(FIELD_PHONE_NUMBER, pet.getPhone());
        values.put(FIELD_IMAGE_URI, pet.getImageURI().toString());

        db.update(DATABASE_TABLE, values, KEY_FIELD_ID + "=?", new String[] {String.valueOf(pet.getId())});
        db.close();
    }

    /**
     * gets a particular pet from the database
     * @param id id of the pet
     * @return the pet
     */
    public Pet getPet(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor results = db.query(DATABASE_TABLE, null, KEY_FIELD_ID + "=?", new String[] {String.valueOf(id)},
                null, null, null, null);

        if (results != null) {
            results.moveToFirst();
            Pet pet = new Pet(results.getInt(0), results.getString(1), results.getString(2),
                    results.getString(3), Uri.parse(results.getString(4)));
            results.close();
            db.close();
            return pet;
        }
        db.close();
        return null;
    }
}
