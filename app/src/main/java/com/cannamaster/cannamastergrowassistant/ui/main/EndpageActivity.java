package com.cannamaster.cannamastergrowassistant.ui.main;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;


import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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
    final String createDB = "CREATE TABLE IF NOT EXISTS TABLE_ARTICLES(_id TEXT PRIMARY KEY, title TEXT, description TEXT, article TEXT, image_id TEXT, image INTEGER)";

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
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        // this adds the back button arrow to the header
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_arrow_left));

        //what to do when clicked
        // set view contents on Endpage
        image = findViewById(R.id.endpage_header_image);
        image.setImageDrawable(getDrawable(getIntent().getIntExtra("image", 0)));
        // set the article text on Endpage
        article = findViewById(R.id.tv_endpage_article);
        article.setText(sArticle);


            /***************************************
             * FAB Button click to add to favorites
             **************************************/

            final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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




    /*************************
     * Top Right Endpage Menu
     *************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.endpage_zoom_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (AppCompatDelegate.getDefaultNightMode()) {
            case R.id.menu_font_size_small:
                menu.findItem(R.id.menu_font_size_small)
                        .setChecked(true);
                break;
            case R.id.menu_font_size_medium:
                menu.findItem(R.id.menu_font_size_medium)
                        .setChecked(true);
                break;
            case R.id.menu_font_size_large:
                menu.findItem(R.id.menu_font_size_large)
                        .setChecked(true);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_font_size_small:
                article.setTextSize(14);
                break;
            case R.id.menu_font_size_medium:
                article.setTextSize(18);
                break;
            case R.id.menu_font_size_large:
                article.setTextSize(22);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
