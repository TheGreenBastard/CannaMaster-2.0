package com.cannamaster.cannamastergrowassistant.ui.main;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/***********************************************************
 * Sections Cardview Adapter - brings the holder and adapter together
 *
 * This takes the various data and puts it into the cardview
 * as one item
 *
 ***********************************************************/
public class SectionsCardviewAdapter extends RecyclerView.Adapter<SectionsCardviewHolder> {

    public List<SectionsCardviewItems> articles;
    private Activity activity;

    public SectionsCardviewAdapter(Activity activity) {
        articles = new ArrayList<>();
        this.activity = activity;
    }

    public void add(SectionsCardviewItems dataModel) {
        articles.add(dataModel);
    }

    public void add(SectionsCardviewItems dataModel, int position) {
        articles.add(position, dataModel);
    }

    public void addAll(List<SectionsCardviewItems> SectionsCardviewItems) {
        articles.addAll(SectionsCardviewItems);
    }

    @Override
    public SectionsCardviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return createViewHolder(v, viewType);
    }

    @Override
    public void onBindViewHolder(SectionsCardviewHolder holder, int position) {
        holder.bind(articles.get(position), activity, position);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public int getItemViewType(int position) {
        return articles.get(position).getViewResId();
    }

    public int searchViewTypePosition(int viewType) {
        int i = 0;
        boolean found = false;
        while (i < articles.size() && !found) {
            if (articles.get(i).getViewResId() == viewType) {
                found = true;
                i--;
            }
            i++;
        }
        return i;
    }

    public SectionsCardviewHolder createViewHolder(View v, int viewType) {
        return articles.get(searchViewTypePosition(viewType)).createViewHolder(v, activity, this);
    }
}
