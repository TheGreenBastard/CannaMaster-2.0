package com.cannamaster.cannamastergrowassistant.ui.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cannamaster.cannamastergrowassistant.R;

import java.util.List;

/************************
 * Holder for Menu Pages
 ************************/
public abstract class SectionsCardviewFragment extends Fragment {

    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    protected SectionsCardviewAdapter adapter;
    protected List<SectionsCardviewItems> articles;
    protected Activity activity;

    public SectionsCardviewFragment() {
    }

    public abstract List<SectionsCardviewItems> getArticles();

    public void onCreateView(View v, ViewGroup parent, Bundle savedInstanceState) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.viewpager_recyclerview, parent, false);
        prepareArticles();
        prepareViews(v);
        onCreateView(v, parent, savedInstanceState);
        return v;
    }

    private void prepareArticles() {
        articles = getArticles();
    }

    protected void bindViews(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.fragment_container);
    }

    protected void prepareViews(View v) {
        bindViews(v);
        setupLayoutManager();
        setupAdapter();
    }

    private void setupLayoutManager() {
        if (getColumnNumber() == 1) {
            layoutManager = new LinearLayoutManager(activity);
        } else {
            layoutManager = new GridLayoutManager(activity, getColumnNumber());
        }
        recyclerView.setLayoutManager(layoutManager);
    }
    
    private void setupAdapter() {
        adapter = new SectionsCardviewAdapter(activity);
        adapter.addAll(articles);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        articles.clear();
    }

    /******************************************
     *  Override to create grid recyclerview
     *  @return number of column to be generated
     ******************************************/
    public int getColumnNumber() {
        return 1;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

}
