package com.example.noteyboi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "simplenotes";
    private static final String COL1 = "ID";//Actually column 0
    private static final String COL2 = "NAMES";
    private static final String COL3 = "NOTES";
    private static final int DATABASE_VERSION = 1;


    public DatabaseHelper(Context context){
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT, " + COL3 + " TEXT)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addData(String Name, String Note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put(COL2, Name);
        contentValues.put(COL3, Note);
        long result = (db.insert(TABLE_NAME, null, contentValues));
        db.close();
        //negative one if not inserted correctly
        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void UpdateRow(String Name, String Note, int Id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put(COL2, Name);
        contentValues.put(COL3, Note);
        String nID = Integer.toString(Id);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{nID});
    }

    public void Delete(int Id){
        SQLiteDatabase db = this.getWritableDatabase();
        String nID = Integer.toString(Id);
        db.delete(TABLE_NAME, "ID = ?", new String[]{nID});
    }
}
