package com.example.intent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.style.AlignmentSpan;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "search";

    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "mycourses";

    public MainActivity m1;

    private static final String ID_COL = "id";

    private static final String NAME_COL = "name";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT)";

        db.execSQL(query);
    }

    public void addNewCourse(String courseName) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NAME_COL, courseName);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }



    public ArrayList<String> readData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor courcs = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        m1 = MainActivity.getmInstanceActivity();
        //m1.getmInstanceActivity
        ArrayList<String> list = new ArrayList<>();
        if(courcs.moveToFirst()){
            do {
                //list.add(new ReadData(courcs.getString(1)));
                list.add(courcs.getString(1));
                //MainActivity.aa.add(courcs.getString(1));
                //MainActivity.setdata(courcs.getString(1));
            }while(courcs.moveToNext());
        }
        courcs.close();
        return list;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
