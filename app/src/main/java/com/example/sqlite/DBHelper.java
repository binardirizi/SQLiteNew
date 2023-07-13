package com.example.sqlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "project1.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE users (username TEXT PRIMARY KEY, password TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE biodata(nim TEXT PRIMARY KEY, nama TEXT, jeniskelamin TEXT, alamat TEXT, email TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE biodatabuku(kode TEXT PRIMARY KEY, judul TEXT, pengarang TEXT, penerbit TEXT, isbn TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS biodata");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS biodatabuku");
    }

    public boolean insertData(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);

        long result = db.insert("users", null, values);
        db.close(); // Tambahkan ini untuk menutup koneksi database

        return result != -1; // Ubah logika pengembalian nilai
    }

    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "users",
                null,
                "username = ?",
                new String[]{username},
                null,
                null,
                null
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return exists;
    }

    public boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "users",
                null,
                "username = ? AND password = ?",
                new String[]{username, password},
                null,
                null,
                null
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return exists;
    }
    public boolean checkkode(String kode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM biodatabuku WHERE kode=?", new String[]{kode});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public boolean insertDataBuku(String kode, String judul, String pengarang, String penerbit, String isbn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("kode", kode);
        values.put("judul", judul);
        values.put("pengarang", pengarang);
        values.put("penerbit", penerbit);
        values.put("isbn", isbn);
        long result = db.insert("biodatabuku", null, values);
        db.close();
        return result != -1;
    }
    public Cursor tampildatabuku() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM biodatabuku", null);
    }
    public boolean deleteDataBuku(String kode) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("biodatabuku", "kode=?", new String[]{kode});
        db.close();
        return result > 0;
    }

    public boolean updateDataBuku(String kode, String judul, String pengarang, String penerbit, String isbn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("judul", judul);
        values.put("pengarang", pengarang);
        values.put("penerbit", penerbit);
        values.put("isbn", isbn);

        int result = db.update("biodatabuku", values, "kode=?", new String[]{kode});
        db.close();
        return result > 0;
    }

    public boolean checknim(String nim) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM biodata WHERE nim=?", new String[]{nim});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public boolean insertData(String nim, String nama, String jeniskelamin, String alamat, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nim", nim);
        values.put("nama", nama);
        values.put("jeniskelamin", jeniskelamin);
        values.put("alamat", alamat);
        values.put("email", email);
        long result = db.insert("biodata", null, values);
        db.close();
        return result != -1;
    }
    public Cursor tampildata() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM biodata", null);
    }
    public boolean deleteData(String nim) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("biodata", "nim=?", new String[]{nim});
        db.close();
        return result > 0;
    }

    public boolean updateData(String nim, String nama, String jeniskelamin, String alamat, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nama", nama);
        values.put("jeniskelamin", jeniskelamin);
        values.put("alamat", alamat);
        values.put("email", email);

        int result = db.update("biodata", values, "nim=?", new String[]{nim});
        db.close();
        return result > 0;
    }
}
