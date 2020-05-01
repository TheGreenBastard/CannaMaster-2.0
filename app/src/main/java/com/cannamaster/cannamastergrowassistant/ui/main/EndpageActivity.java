package com.cannamaster.cannamastergrowassistant.ui.main;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.cannamaster.cannamastergrowassistant.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;


public class EndpageActivity extends AppCompatActivity {

    TextView article;
    ImageView image;
    DatabaseHelper helper;


    // int sID;
    String sID, sTitle, sDescription, sArticle, sImageId;
    int sImage;

    //CREATE TABLE
    String createDB = "CREATE TABLE IF NOT EXISTS TABLE_ARTICLES(_id TEXT PRIMARY KEY, title TEXT, description TEXT, article TEXT, image_id TEXT, image INTEGER)";

    public void onCreate(SQLiteDatabase db) {
                    db.execSQL(createDB);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get the layout
        setContentView(R.layout.endpage_layout);

        // get the values passed from favorites
        Intent intent = getIntent();

        // Get values passed to activity from menu pages fragment
        sID = intent.getStringExtra("_id");
        sTitle = intent.getStringExtra("title");
        sDescription = intent.getStringExtra("description");
        sArticle = intent.getStringExtra("article");
        sImageId = intent.getStringExtra("image_id");
        sImage = intent.getIntExtra("image", 0);

        // sets toolbar and page title

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.endpage_header);
        collapsingToolbar.setTitle(sTitle);
        Toolbar toolbar = findViewById(R.id.endpage_toolbar);
        setSupportActionBar(toolbar);

        // this adds the back button arrow to the header
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_left));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     //what to do when clicked
                                                     onBackPressed();
                                                 }
                                             });


        // set view contents on Endpage
        image = findViewById(R.id.endpage_header_image);
        image.setImageDrawable(getResources().getDrawable(getIntent().getIntExtra("image", 0)));
        // set the article text on Endpage
        article = findViewById(R.id.tv_endpage_article);
        article.setText(sArticle);


        /***************************************
         * FAB Button click to add to favorites
         **************************************/

        final FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v){

                // Insert Record to SQLite Database for Favorites List

                Intent intent = getIntent();

                String name = intent.getStringExtra("_id");
                String title = intent.getStringExtra("title");
                String article = intent.getStringExtra("article");
                String description = intent.getStringExtra("description");
                String image_id = intent.getStringExtra("image_id");
                int image = intent.getIntExtra("image", 0);

                helper = new DatabaseHelper(EndpageActivity.this);
                helper.insertIntoDB(name, title, article, description, image_id, image);

                Toast.makeText(EndpageActivity.this, "'"
                        + sTitle + "' Added To Favorite Articles",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }



}
