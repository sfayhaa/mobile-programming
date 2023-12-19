package com.example.crudapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "CrudMhs.db";
    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("CREATE TABLE mhs(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT , nrp TEXT UNIQUE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("DROP TABLE IF EXISTS mhs");
    }

    public Boolean insertData(String name, String nrp){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("name", name);
        contentValues.put("nrp", nrp);
        long result = MyDB.insert("mhs", null, contentValues);
        if(result==-1) return false;
        else return true;
    }

    @SuppressLint("Range")
    public String[] checkName(String name) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String[] result = new String[2];
        Cursor cursor = MyDB.rawQuery("SELECT * FROM mhs WHERE name = ?", new String[]{name});

        if(cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                result[0] = cursor.getString(cursor.getColumnIndex("name"));
                result[1] = cursor.getString(cursor.getColumnIndex("nrp"));
            } else {
                result[0] = null;
                result[1] = null;
            }
        }

        cursor.close();
        return result;
    }

    @SuppressLint("Range")
    public String[] checkNrp(String nrp) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String[] result = new String[2];
        Cursor cursor = MyDB.rawQuery("SELECT * FROM mhs WHERE nrp = ?", new String[]{nrp});

        if(cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                result[0] = cursor.getString(cursor.getColumnIndex("name"));
                result[1] = cursor.getString(cursor.getColumnIndex("nrp"));
            } else {
                result[0] = null;
                result[1] = null;
            }
        }

        cursor.close();
        return result;
    }

    public boolean updateName(String newName, String nrp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", newName);
        String whereClause = "nrp = ?";
        String[] whereArgs = {nrp};
        int rowsUpdated = db.update("mhs", values, whereClause, whereArgs);
        return rowsUpdated > 0;
    }

    public boolean deleteNrpByName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "name = ?";
        String[] whereArgs = {name};
        int rowsDeleted = db.delete("mhs", whereClause, whereArgs);
        return rowsDeleted > 0;
    }

    @SuppressLint("Range")
    public String[] showAll(){
        ArrayList<String> data = new ArrayList<>(); 
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT name,nrp FROM mhs",null);

        if(cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String nrp = cursor.getString(cursor.getColumnIndex("nrp"));
                data.add(name + "\t\t\t\t\t\t\t"+nrp);
            }
        }
        cursor.close();

        String[] dataArray = data.toArray(new String[data.size()]);
        return dataArray;
    }
}
