package com.cannamaster.cannamastergrowassistant.ui.main.favorites;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.provider.MediaStore.Images.Thumbnails.IMAGE_ID;

/************************
 * Database Helper Class
 ************************/
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favorites.db";
    private static final int DATABASE_VERSION = 1;
    private static final String ARTICLES_TABLE = "TABLE_ARTICLES";
    private static final String MAKE_TABLE = "CREATE TABLE" +
            " IF NOT EXISTS TABLE_ARTICLES" +
            "(_id TEXT PRIMARY KEY, " +
            "title TEXT, " +
            "description TEXT, " +
            "article TEXT, " +
            "image_id TEXT, " +
            "image INTEGER)";

    private static final String _id = "_id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String ARTICLE = "article";
    private static final String IMAGE_ID = "image_id";
    private static final String IMAGE_BITMAP = "image";

    private final Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    private String imageId;
    private byte[] imageByteArray;

    // public String getImageId() {
    //    return imageId;}
    // public void setImageId(String imageId) {
    //    this.imageId = imageId;
    //}
    public byte[] getImageByteArray() {
        return imageByteArray;
    }

    public void setImageByteArray(byte[] imageByteArray) {
        this.imageByteArray = imageByteArray;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the table
        db.execSQL(MAKE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS TABLE_ARTICLES");
        // Create tables again
        onCreate(db);
    }

    /* Insert into database*/
    public void insertIntoDB(String name, String title, String article, String description, String image_id, int image) {
        Log.d("insert", "before insert");

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("_id", name);
        values.put("title", title);
        values.put("article", article);
        values.put("description", description);
        values.put("image_id", image_id);
        values.put("image", image);

        // 3. insert
        db.insert(ARTICLES_TABLE, null, values);
        // 4. close
        db.close();

    }

    /* Retrive  data from database */
    public List<DatabaseModel> getDataFromDB() {
        List<DatabaseModel> modelList = new ArrayList<DatabaseModel>();
        String query = "SELECT * FROM TABLE_ARTICLES";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                DatabaseModel model = new DatabaseModel();
                model.set_id(cursor.getString(0));
                model.setTitle(cursor.getString(1));
                model.setDescription(cursor.getString(2));
                model.setArticle(cursor.getString(3));
                model.setImageId(cursor.getString(4));
                model.setImage(cursor.getInt(5));

                modelList.add(model);
            } while (cursor.moveToNext());
        }

        Log.d("favorites data", modelList.toString());

        close();
        return modelList;
    }

    public ImageHelper getImage(String imageId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor2 = db.query(ARTICLES_TABLE,
                new String[]{_id, TITLE, DESCRIPTION, ARTICLE, IMAGE_ID, IMAGE_BITMAP}, IMAGE_ID
                        + " LIKE '" + imageId + "%'", null, null, null, null);
        ImageHelper imageHelper = new ImageHelper();
        if (cursor2.moveToFirst()) {
            do {
                imageHelper.setImageId(cursor2.getString(4));
                imageHelper.setImageByteArray(cursor2.getBlob(5));
            } while (cursor2.moveToNext());
        }
        cursor2.close();
        db.close();
        return imageHelper;
    }

    /*delete a row from database*/
    public void deleteARow(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ARTICLES_TABLE, "title" + " = ?", new String[]{title});
        db.close();
    }

}
