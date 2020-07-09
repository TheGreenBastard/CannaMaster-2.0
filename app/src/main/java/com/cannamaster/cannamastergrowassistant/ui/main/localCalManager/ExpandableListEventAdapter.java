package com.cannamaster.cannamastergrowassistant.ui.main.localcalmanager;

import android.app.Activity;
import android.content.Context;
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

public class ExpandableListEventAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private ArrayList<Date> dates;
    private ArrayList<String> titles;
    private TreeMap<Date,ArrayList<CalendarManagerEvent>> dataSet;


    public ExpandableListEventAdapter(Context context, ArrayList<Date> dates, TreeMap<Date,ArrayList<CalendarManagerEvent>> events, ArrayList<String> titles)
    {
        this.context = context;
        this.dates = dates;
        this.dataSet = events;
        this.titles = titles;
    }

    @Override
    public int getGroupCount() {

        return this.dates.size();
    }

    @Override
    public int getChildrenCount(int listPosition) {
        Date key = this.dates.get(listPosition);
        return this.dataSet.get(key).size();
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
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int listPosition, int expandedListPostion) {
        return expandedListPostion;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    // This is just for the first parent expandable list view item
    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Date date = dates.get(listPosition);
        String title = titles.get(listPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.cal_mgr_groupview_listitem,null);
        }
        TextView dateView = (TextView) convertView.findViewById(R.id.tv_groupView_date);
        TextView titleView = (TextView) convertView.findViewById(R.id.tv_main_title);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        dateView.setText(dateFormat.format(date.getTime()));
        titleView.setText(title);

        return convertView;
    }

    // this sets the child items on expandable list view item
    @Override
    public View getChildView(int listPosition, int expandedListPosition, boolean isLastChild, View itemView, ViewGroup parent) {
        TextView desc, uid;
        //create the list item

        if(itemView == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            itemView = inflater.inflate(R.layout.cal_mgr_childview_listitem, parent, false);

            final CalendarManagerEvent currentCalendarManagerEvent = (CalendarManagerEvent) getChild(listPosition,expandedListPosition);
            // declare the textviews
            desc = (TextView) itemView.findViewById(R.id.tv_groupView_desc);
            uid = (TextView) itemView.findViewById(R.id.tv_uid);
            // set the text in above views
            desc.setText(currentCalendarManagerEvent.getDesc());
            uid.setText(currentCalendarManagerEvent.getUid());
    }
         return itemView;
    }

    public void update(ArrayList<Date> dates,TreeMap<Date,ArrayList<CalendarManagerEvent>> events){
        this.dates = dates;
        this.dataSet = events;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView title;
        TextView date;
        TextView desc;
        TextView uid;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
