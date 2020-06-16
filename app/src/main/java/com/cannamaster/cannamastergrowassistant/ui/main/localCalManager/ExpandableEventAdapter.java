package com.quigglesproductions.paulq.calendartest;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

/**
 * Created by paulq on 13/10/2019.
 */

public class ExpandableEventAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Date> dates;
    private TreeMap<Date,ArrayList<CalendarManagerEvent>> dataSet;

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
        int size = this.dataSet.get(key).size()+1;
        return size;
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
        TextView shiftNumber = (TextView) convertView.findViewById(R.id.tv_groupView_shift_number);

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateView.setText(dateFormat.format(date.getTime()));
        //long shLen = date.getEndDate().getTime() - date.getStartDate().getTime();
        shiftNumber.setText((getChildrenCount(listPosition)-1)+" shift(s)");
        return convertView;
    }

    @Override
    public View getChildView(int listPosition, int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView title, date, shiftLength, earning;
        //create the list item view
        if(expandedListPosition<getChildrenCount(listPosition)-1) {
            final CalendarManagerEvent currentCalendarManagerEvent = (CalendarManagerEvent) getChild(listPosition,expandedListPosition);
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.cal_mgr_event_listitem, parent, false);
            //get item at position
            convertView.setLongClickable(true);
            title = (TextView) convertView.findViewById(R.id.tv_groupView_title);
            date = (TextView) convertView.findViewById(R.id.tv_groupView_date);


            // set on layout
            title.setText(currentCalendarManagerEvent.getTitle());
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            date.setText(dateFormat.format(currentCalendarManagerEvent.getStartDate()) + " " + currentCalendarManagerEvent.getStartTime() + " - " + currentCalendarManagerEvent.getEndTime());
            long shLen = currentCalendarManagerEvent.getEndDate().getTime() - currentCalendarManagerEvent.getStartDate().getTime();
            long seconds = shLen / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            minutes = minutes - (hours * 60);
            dateFormat = new SimpleDateFormat("hh:mm:ss");


        }
        if(expandedListPosition == getChildrenCount(listPosition)-1)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.cal_mgr_childview_footer,parent,false);

        }
        return convertView;
    }




    public void update(ArrayList<Date> dates,TreeMap<Date,ArrayList<CalendarManagerEvent>> events){
        this.dates = dates;
        this.dataSet = events;
        notifyDataSetChanged();
    }



    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
