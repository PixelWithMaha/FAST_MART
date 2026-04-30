package com.example.assignment3.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavDbHelper extends SQLiteOpenHelper {

    public FavDbHelper(Context context) {
        // This line actually creates the database file on the phone
        super(context, "Favourites.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // This code creates the table inside the database file
        db.execSQL("CREATE TABLE fav_table (id TEXT PRIMARY KEY, name TEXT, price TEXT, description TEXT, image INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS fav_table");
        onCreate(db);
    }

    public void addFavorite(String id, String name, String price, String desc, int img) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("id", id);
        v.put("name", name);
        v.put("price", price);
        v.put("description", desc);
        v.put("image", img);

        db.insert("fav_table", null, v);
        db.close();
    }
}