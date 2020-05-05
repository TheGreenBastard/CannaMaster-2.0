package com.cannamaster.cannamastergrowassistant.ui.main.favorites;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.cannamaster.cannamastergrowassistant.R;

import java.util.ArrayList;
import java.util.List;

public class FavoritesListActivity extends AppCompatActivity {

    DatabaseHelper helper;
    List<DatabaseModel> dbList;
    RecyclerView mRecyclerView;

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

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

}
