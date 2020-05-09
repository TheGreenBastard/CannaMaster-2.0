package com.cannamaster.cannamastergrowassistant.ui.main;

import android.app.Activity;
import android.view.View;

import com.cannamaster.cannamastergrowassistant.R;


/***********************************************************************
 * This just creates some variables that will be needed for the recycler
 ***********************************************************************/
public class SectionsCardviewItems {
    // gather views for array population on cardview
    public String _id;
    public String title;
    public String description;
    public String article;
    public String image_id;
    public int image;


    // this sets the data into the view
    public SectionsCardviewItems(String _id, String title, String description, String article, String image_id, int image) {
        this._id = _id;
        this.title = title;
        this.article = article;
        this.description = description;
        this.image_id = image_id;
        this.image = image;
    }

    public int getViewResId() {
        return R.layout.sections_cardview_row;
    }

    public SectionsCardviewHolder createViewHolder(View v, Activity activity, SectionsCardviewAdapter adapter) {
        return new SectionsCardviewHolder(v, activity, adapter) {
            public void bind(SectionsCardviewItems sectionsCardviewItems, Activity activity, int position) {
                super.bind(sectionsCardviewItems, activity, position);
            }
        };
    }


}
