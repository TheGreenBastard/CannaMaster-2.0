package com.cannamaster.cannamastergrowassistant.ui.main.localcalmanager;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.cannamaster.cannamastergrowassistant.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

/***********************************************
 * Expandable List Adapter for Calendar Manager
 ***********************************************/

public class ExpandableEventAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private ArrayList<Date> dates;
    private TreeMap<Date,ArrayList<CalendarManagerEvent>> dataSet;
    private SparseBooleanArray mSelectedItemsIds;
    LayoutInflater inflater;

    public ExpandableEventAdapter(Context context, ArrayList<Date> dates, TreeMap<Date,ArrayList<CalendarManagerEvent>> events)
    {
        this.context = context;
        this.dates = dates;
        this.dataSet = events;
    }

    @Override
    public int getGroupCount() {

        return this.dates.size();
    }

    @Override
    public int getChildrenCount(int listPosition) {
        Date key = this.dates.get(listPosition);
        return this.dataSet.get(key).size()+1;
    }

    @Override
    public Object getGroup(int listPosition) {

        return this.dates.get(listPosition);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition)
    {
        return this.dataSet.get(this.dates.get(listPosition)).get(expandedListPosition);
    }

    @Override
    public long getGroupId(int listPosition) {

        return listPosition;
    }

    @Override
    public long getChildId(int listPosition, int expandedListPostion) {
        return expandedListPostion;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Date date = dates.get(listPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.cal_mgr_groupview_date,null);
        }
        TextView dateView = (TextView) convertView.findViewById(R.id.tv_groupView_date);


        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateView.setText(dateFormat.format(date.getTime()));
        //long shLen = date.getEndDate().getTime() - date.getStartDate().getTime();
        //shiftNumber.setText((getChildrenCount(listPosition)-1)+" shift(s)");
        return convertView;
    }

    @Override
    public View getChildView(int listPosition, int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView title, desc, uid;
        //create the list item view
        if(expandedListPosition<getChildrenCount(listPosition)-1) {
            final CalendarManagerEvent currentCalendarManagerEvent = (CalendarManagerEvent) getChild(listPosition,expandedListPosition);
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.cal_mgr_event_listitem, parent, false);
            //set the item as longClickable
            convertView.setLongClickable(true);
            // declare the textviews
            title = (TextView) convertView.findViewById(R.id.tv_groupView_title);
            desc = (TextView) convertView.findViewById(R.id.tv_groupView_desc);
            uid = (TextView) convertView.findViewById(R.id.tv_uid);
            // set the text in above views
            title.setText(currentCalendarManagerEvent.getTitle());
            desc.setText(currentCalendarManagerEvent.getDesc());
           // uid.setText(currentCalendarManagerEvent.getUid());

        }
        if(expandedListPosition == getChildrenCount(listPosition)-1)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.cal_mgr_childview_footer,parent,false);

        }
        return convertView;
    }



    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
}

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public void update(ArrayList<Date> dates,TreeMap<Date,ArrayList<CalendarManagerEvent>> events){
        this.dates = dates;
        this.dataSet = events;
        notifyDataSetChanged();
    }

private class ViewHolder {
        TextView title;
        TextView date;
        TextView desc;
        TextView uid;
}

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
