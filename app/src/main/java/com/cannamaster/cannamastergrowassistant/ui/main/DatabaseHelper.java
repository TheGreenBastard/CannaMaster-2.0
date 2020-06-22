package com.cannamaster.cannamastergrowassistant.ui.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/************************
 * Database Helper Class
 ************************/
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favorites.db";
    private static final int DATABASE_VERSION = 1;
    public static final String ARTICLES_TABLE = "TABLE_ARTICLES";
    public static final String _id = "_id";
    private static final String MAKE_TABLE = "CREATE TABLE" +
            " IF NOT EXISTS TABLE_ARTICLES" +
            "(_id TEXT PRIMARY KEY, " +
            "title TEXT, " +
            "description TEXT, " +
            "article TEXT, " +
            "image_id TEXT, " +
            "image INTEGER)";

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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

    // Insert into database
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

    // Retrieve  data from database
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

    /** delete a row from database
     public void deleteRow(int _id){

     SQLiteDatabase db = helper.getWritableDatabase();
     String id = Integer.toString(_id);
     db.delete("TABLE_ARTICLES", "_id =? ",new String[] {id});
     db.close();
     }*/

}
