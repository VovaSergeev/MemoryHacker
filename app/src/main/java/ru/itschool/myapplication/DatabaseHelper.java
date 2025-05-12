package ru.itschool.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "results.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_RESULTS = "results";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_DIFFICULTY = "difficulty";
    private static final String COLUMN_RESULT = "result";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_RESULTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_DIFFICULTY + " TEXT, " +
                COLUMN_RESULT + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
        onCreate(db);
    }



    public float getResult(String time, String difficulty) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RESULTS, new String[]{COLUMN_RESULT},
                COLUMN_TIME + "=? AND " + COLUMN_DIFFICULTY + "=?", new String[]{time, difficulty},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int result = cursor.getInt(cursor.getColumnIndex(COLUMN_RESULT));
            cursor.close();
            return result;
        }
        return -1;
    }
    public void updateResult(String time, String difficulty, float newResult) {
        SQLiteDatabase db = this.getWritableDatabase();


        Cursor cursor = db.query(TABLE_RESULTS, new String[]{COLUMN_RESULT},
                COLUMN_TIME + "=? AND " + COLUMN_DIFFICULTY + "=?", new String[]{time, difficulty},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") float currentResult = cursor.getFloat(cursor.getColumnIndex(COLUMN_RESULT));


            if (newResult > currentResult) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_RESULT, newResult);
                db.update(TABLE_RESULTS, values, COLUMN_TIME + "=? AND " + COLUMN_DIFFICULTY + "=?", new String[]{time, difficulty});
            }
            cursor.close();
        }
    }
    public void addResult(String time, String difficulty, float result) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_DIFFICULTY, difficulty);
        values.put(COLUMN_RESULT, result);

        db.insert(TABLE_RESULTS, null, values);
        db.close();
    }
    @SuppressLint("Range")
    public float getRecord(String time, String difficulty) {
        float maxRecord = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RESULTS, new String[]{COLUMN_RESULT},
                COLUMN_TIME + "=? AND " + COLUMN_DIFFICULTY + "=?", new String[]{time, difficulty},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                maxRecord = cursor.getFloat(cursor.getColumnIndex(COLUMN_RESULT));
            }
            cursor.close();
        }
        return maxRecord;
    }

}