package com.cannamaster.cannamastergrowassistant.ui.main.favorites;

import android.view.View;

/*****************************************
 * Click Listener for Listview Card Click
 *****************************************/
public interface RecyclerViewClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
