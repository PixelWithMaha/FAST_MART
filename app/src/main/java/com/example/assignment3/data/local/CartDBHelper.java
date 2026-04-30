package com.example.assignment3.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.assignment3.data.model.CartItem;
import com.example.assignment3.data.model.Product;
import java.util.ArrayList;
import java.util.List;

public class CartDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FastMartCart.db";
    private static final String TABLE_CART = "cart";

    public CartDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CART + " (id TEXT PRIMARY KEY, name TEXT, price TEXT, description TEXT, image INTEGER, quantity INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int n) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

    // Requirement 2: Add or Update record
    public void addOrUpdate(Product p, int qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT quantity FROM " + TABLE_CART + " WHERE id=?", new String[]{p.id});

        if (cursor.moveToFirst()) {
            updateQuantity(p.id, cursor.getInt(0) + qty);
        } else {
            ContentValues v = new ContentValues();
            v.put("id", p.id); v.put("name", p.name); v.put("price", p.price);
            v.put("description", p.description); v.put("image", p.imageRes);
            v.put("quantity", qty);
            db.insert(TABLE_CART, null, v);
        }
        cursor.close();
    }

    // Requirement 3: Update query
    public void updateQuantity(String id, int qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("quantity", qty);
        db.update(TABLE_CART, v, "id=?", new String[]{id});
    }

    // Requirement 5: Delete query
    public void deleteItem(String id) {
        this.getWritableDatabase().delete(TABLE_CART, "id=?", new String[]{id});
    }

    public List<CartItem> getAllItems() {
        List<CartItem> list = new ArrayList<>();
        Cursor c = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_CART, null);
        while (c.moveToNext()) {
            Product p = new Product(c.getString(0), c.getString(1), "", c.getString(2), c.getString(3), "", c.getInt(4), false);
            list.add(new CartItem(p, c.getInt(5)));
        }
        c.close();
        return list;
    }

    public void clearCart() {
        this.getWritableDatabase().execSQL("DELETE FROM " + TABLE_CART);
    }
}