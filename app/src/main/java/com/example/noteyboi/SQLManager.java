
package com.example.noteyboi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class SQLManager extends SQLiteOpenHelper {
//    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "simplenotes";
//    private static final String COL1 = "ID";//Actually column 0
    private static final String COL2 = "NAMES";
    private static final String COL3 = "NOTES";
    private static final String COL4 = "CREATEDDATE";
    private static final String COL5 = "MODIFIEDDATE";
    private static final int DATABASE_VERSION = 2;


    public SQLManager(Context context){
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TIMESTAMP, " + COL5 + " TIMESTAMP)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public int addData(String Name, String Note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =  new ContentValues();

        contentValues.put(COL2, Name);
        contentValues.put(COL3, Note);
        contentValues.put(COL4, (new Date(Calendar.getInstance().getTimeInMillis())).toString());
        contentValues.put(COL5, (new Date(Calendar.getInstance().getTimeInMillis())).toString());
        long result = (db.insert(TABLE_NAME, null, contentValues));
        db.close();

        //-1 if not inserted correctly
        if (result == -1){
//            return false;
            return -1;
        }
        else{
//            return true; Return ID
            String query = "SELECT ID FROM " + TABLE_NAME + "WHERE " + COL2 + " = " + Name + " AND " + COL3 + " = " + Note;
            return (db.rawQuery(query, null)).getInt(0);
        }
    }

    public void UpdateRow(String Name, String Note, int Id){//Change to bool
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put(COL2, Name);
        contentValues.put(COL3, Note);
        contentValues.put(COL5, (new Date(Calendar.getInstance().getTimeInMillis())).toString());

        String nID = Integer.toString(Id);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{nID});
    }

    public void Delete(int Id){//Change to bool
        SQLiteDatabase db = this.getWritableDatabase();
        String nID = Integer.toString(Id);
        db.delete(TABLE_NAME, "ID = ?", new String[]{nID});
    }

    public ArrayList<DatabaseObject> getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);

        ArrayList<DatabaseObject> objectList = new ArrayList<>();
        while(data.moveToNext()){
            // TODO: Add proper created and modified dates
            DatabaseObject tempData = new DatabaseObject(data.getInt(0), data.getString(1),data.getString(2), 0, 0);
//            mID.add();//get items from column zero
//            mNoteNames.add();//get items from column one
//            mNotes.add(data.getString(2));//get items from column two
            objectList.add(tempData);
        }
        return objectList;
    }
}
