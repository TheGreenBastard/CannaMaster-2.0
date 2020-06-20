package com.cannamaster.cannamastergrowassistant.ui.main.favorites;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cannamaster.cannamastergrowassistant.R;
import com.cannamaster.cannamastergrowassistant.ui.main.DatabaseHelper;
import com.cannamaster.cannamastergrowassistant.ui.main.DatabaseModel;


import java.util.ArrayList;
import java.util.List;

/**********************************
 * Favorites Recycler View Adapter
 **********************************/
public class FavoritesRecyclerAdapter extends RecyclerView.Adapter<FavoritesRecyclerAdapter.ViewHolder> {

    static List<DatabaseModel> dbList;
    static Context context;
    private Activity activity;
    static DatabaseHelper helper;
    SQLiteDatabase db;

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(int position);
    }

    private FavoritesListActivity mFragment;

    FavoritesRecyclerAdapter(Context context, List<DatabaseModel> dbList) {
        this.dbList = new ArrayList<DatabaseModel>();
        this.context = context;
        this.dbList = dbList;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_list_row, parent, false);

        return new ViewHolder(itemLayoutView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        int sImage = dbList.get(position).getImage();
        holder.image.setImageResource(sImage);
        holder.title.setText(dbList.get(position).getTitle());
        holder.description.setText(dbList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return dbList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {


        public final TextView title, description;
        public final ImageView image;
        public final CardView cardview;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            cardview = (CardView) itemLayoutView.findViewById(R.id.fav_cardview);

            title = (TextView) itemLayoutView
                    .findViewById(R.id.favorites_row_title);
            description = (TextView) itemLayoutView
                    .findViewById(R.id.favorites_row_description);
            image = (ImageView) itemLayoutView
                    .findViewById(R.id.favorites_cardview_image);

            itemLayoutView.setOnClickListener(this);
            itemLayoutView.setOnLongClickListener(this);
        }

        /* public void updateName() {
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper._id, newName);
            String[] whereArgs = { oldName};
            db.update(DatabaseHelper.TABLE_ARTICLES,)
        } */

        // click on cardview to open favorites endpage
        @Override
        public void onClick(View v) {
            // This opens the endpage activity and populates the views

            Intent intent = new Intent(context, FavoritesEndpageActivity.class);

            Bundle extras = new Bundle();
            extras.putInt("position", getAdapterPosition());
            intent.putExtras(extras);

            context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View view) {
            // this opens the delete dialog
            // Alert Dialog to inform the user favorites article will be erased
            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            builder1.setMessage("Do you want to remove the selected article from your favorites?\n");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Remove",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int _id) {

                            dbList.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                        }
                    });

            builder1.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert12 = builder1.create();
            alert12.show();

            return true;
        }

        // delete a row from database
        public void deleteRow(int _id) {

            SQLiteDatabase db = helper.getWritableDatabase();
            String id = Integer.toString(_id);
            db.delete("TABLE_ARTICLES", "_id =? ", new String[]{id});
            db.close();
        }

    }

}
