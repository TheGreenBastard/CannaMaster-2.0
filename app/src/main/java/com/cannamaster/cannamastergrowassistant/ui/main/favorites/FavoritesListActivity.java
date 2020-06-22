package com.cannamaster.cannamastergrowassistant.ui.main.favorites;


import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cannamaster.cannamastergrowassistant.R;
import com.cannamaster.cannamastergrowassistant.ui.main.DatabaseHelper;
import com.cannamaster.cannamastergrowassistant.ui.main.DatabaseModel;

import java.util.ArrayList;
import java.util.List;


public class FavoritesListActivity extends AppCompatActivity {

    DatabaseHelper helper;
    List<DatabaseModel> dbList;
    RecyclerView mRecyclerView;

    FavoritesRecyclerAdapter adapter;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_list_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.fav_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        helper = new DatabaseHelper(this);
        dbList = new ArrayList<DatabaseModel>();
        dbList = helper.getDataFromDB();

        mRecyclerView = (RecyclerView) findViewById(R.id.fav_recycleview);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // specify an adapter (see also next example)
        mAdapter = new FavoritesRecyclerAdapter(this, dbList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), mRecyclerView, new RecyclerViewClickListener() {

            @Override
            public void onClick(View view, int position) {
                // Method must be overridden for onClick
                // do not put code here, put it in Recycler Adapter
            }

            @Override
            public void onLongClick(View view, int position) {
                // Method must be overridden for onLongClick
                // do not put code here, put it in RecyclerAdapter
            }
        }));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.endpage_zoom_menu, menu);
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
