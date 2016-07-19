package com.example.bbk.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by BBK on 02/23/16.
 */
public class Database {

    Context context;
    MyDatabase dbHelper;
    SQLiteDatabase db;

    public static String DATABASENAME= "My contacts";
    public static int DATABASEVERSION= 1;

    public static final String TABLENAME="contacts";
    public static final String ID = "id";
    public static final String FIRSTNAME = "firstname";
    public static final String LASTNAME = "lastname";
    public static final String MOBILE = "mobile";
    public static final String WORK = "work";
    public static final String HOME = "home";

    public Database(Context context){
        this.context=context;
        dbHelper= new MyDatabase(context);
    }

    public static class MyDatabase extends SQLiteOpenHelper {

        public MyDatabase(Context context) {
            super(context,DATABASENAME,null,DATABASEVERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL("create table " + TABLENAME + " (" + ID + " integer primary key autoincrement, "
                        + FIRSTNAME + " text not null, " + LASTNAME + " text not null, " + MOBILE + " text not null, " + WORK + " text not null, "
                + HOME + " text not null );");
            }
            catch (SQLException ex){
                Log.d("SqlException",ex.getMessage());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
            onCreate(db);
        }
    }

    public Database open(){
        try{
            db=dbHelper.getWritableDatabase();
        }catch(SQLException ex){
            Log.d("SQLException", ex.getMessage());
        }
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long insertContacts(String firstname, String lastname, String mobile, String work, String home){
        ContentValues addvalues = new ContentValues();

        addvalues.put(FIRSTNAME,firstname);
        addvalues.put(LASTNAME,lastname);
        addvalues.put(MOBILE,mobile);
        addvalues.put(WORK,work);
        addvalues.put(HOME, home);

        return db.insert(TABLENAME,null,addvalues);
    }

    public Cursor fetchAllContacts(){
        Cursor query=db.rawQuery("select * from " + TABLENAME, null);
        if (query!=null) {
            return query;
        }
        else{
            return null;
        }
    }

    public Cursor fetchSpecificContactFromDatabase(int id)
    {
        Cursor query=db.rawQuery("select * from " + TABLENAME + " where " + ID + " = " + id+"",null);
        return query;
    }

    public boolean deleteContact(int id){
        //Log.d("deleteidindatabase",id+"");
        return db.delete(TABLENAME, ID + " = " + id ,null)>0;

    }

    public boolean updateContact(int id,String firstname,String lastname,String mobile, String work, String home){

        ContentValues updateValues=new ContentValues();
        updateValues.put(FIRSTNAME, firstname);
        updateValues.put(LASTNAME, lastname);
        updateValues.put(MOBILE, mobile);
        updateValues.put(WORK, work);
        updateValues.put(HOME, home);

        return db.update(TABLENAME, updateValues, ID + " = " + id, null)>0;
    }
}
