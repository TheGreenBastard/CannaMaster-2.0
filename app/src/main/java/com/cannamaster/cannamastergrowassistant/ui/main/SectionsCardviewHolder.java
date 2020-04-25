package com.cannamaster.cannamastergrowassistant.ui.main;

import android.app.Activity;

import android.content.Intent;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.cannamaster.cannamastergrowassistant.R;



/****************************************
 * Sections Cardview Holder - For Viewpager
 *
 * This holds the views for each individual cardview
 *
 ****************************************/
public abstract class SectionsCardviewHolder extends RecyclerView.ViewHolder{

    protected final Activity activity;
    protected SectionsCardviewAdapter adapter;
    private TextView txtTitle, txtDescription;
    private ImageView imgImage;
    public View view;

    public SectionsCardviewHolder(View v, Activity activity, SectionsCardviewAdapter adapter) {
        super(v);

        this.activity = activity;
        this.adapter = adapter;

        // declare the views
        imgImage = (ImageView) v.findViewById(R.id.cardview_image);
        txtTitle = (TextView) v.findViewById(R.id.cardview_title);
        txtDescription = (TextView) v.findViewById(R.id.cardview_description);
        view = v.findViewById(R.id.sections_card_view);
    }

    public void bind(SectionsCardviewItems dataModel, Activity activity, final int position) {

        final SectionsCardviewItems sectionsCardviewItems = dataModel;

        view.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v){

                // once clicked pass the stored cardview data to the endpage for final display

                Intent intent = new Intent(view.getContext(), EndpageActivity.class);

                // values to pass to endpage activity
                intent.putExtra("_id", sectionsCardviewItems._id);
                intent.putExtra("title", sectionsCardviewItems.title);
                intent.putExtra("description", sectionsCardviewItems.description);
                intent.putExtra("article", sectionsCardviewItems.article);
                intent.putExtra("image_id", sectionsCardviewItems.image_id);
                intent.putExtra("image", sectionsCardviewItems.image);

                // start Endpage Activity with values passed
                view.getContext().startActivity(intent);
            }
        });

        // set the pulled content to the cardviews
        imgImage.setImageResource(sectionsCardviewItems.image);
        txtTitle.setText(sectionsCardviewItems.title);
        txtDescription.setText(sectionsCardviewItems.description);


    }
}
