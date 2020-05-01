package com.cannamaster.cannamastergrowassistant.ui.main.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.cannamaster.cannamastergrowassistant.R;
import com.cannamaster.cannamastergrowassistant.ui.main.DatabaseHelper;
import com.cannamaster.cannamastergrowassistant.ui.main.DatabaseModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

public class FavoritesEndpageActivity extends AppCompatActivity {
    DatabaseHelper helper;
    List<DatabaseModel> dbList;
    int position;
    RecyclerView recyclerView;
    FavoritesRecyclerAdapter mAdapter;
    TextView tvarticle;
    CollapsingToolbarLayout tvTitle;
    ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.favorites_endpage_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.fav_endpage_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        // 5. get status value from bundle
        position = bundle.getInt("position");

        ivImage = (ImageView)findViewById(R.id.fav_endpage_header_image);
        tvarticle = (TextView)findViewById(R.id.fav_endpage_article);
        tvTitle = (CollapsingToolbarLayout)findViewById(R.id.fav_endpage_header);

        helper = new DatabaseHelper(this);
        dbList= new ArrayList<DatabaseModel>();
        dbList = helper.getDataFromDB();

        String _id = dbList.get(position).get_id();
        String title = dbList.get(position).getTitle();
        String article = dbList.get(position).getArticle();
        String description = dbList.get(position).getDescription();
        String imageId = dbList.get(position).getImageId();
        int image = dbList.get(position).getImage();

        // set the collapsing toolbar image
        ivImage.setImageDrawable(getResources().getDrawable(image));
        tvTitle.setTitle(title);
        tvarticle.setText(article);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
