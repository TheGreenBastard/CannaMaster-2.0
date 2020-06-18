package com.cannamaster.cannamastergrowassistant.ui.main.localcalmanager;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.cannamaster.cannamastergrowassistant.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/******************************************
 * Adapter for Calendar Manager List View
 ******************************************/

public class CalendarManagerEventAdapter extends ArrayAdapter<CalendarManagerEvent> {
    private Context context;
    int layoutResourceId;
    private ArrayList<CalendarManagerEvent> calendarManagerEvents;
    public CalendarManagerEventAdapter(Context context, int layoutResourceId, ArrayList<CalendarManagerEvent> calendarManagerEvents)
    {
        super(context, layoutResourceId, calendarManagerEvents);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.calendarManagerEvents = calendarManagerEvents;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null)
        {
            //create the list item view
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.cal_mgr_event_listitem, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }
        //get item at position
        final CalendarManagerEvent currentCalendarManagerEvent = calendarManagerEvents.get(position);
        convertView.setLongClickable(true);
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });

        /*convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "ProductID: " + currentItem.getItemID() , Toast.LENGTH_SHORT).show();
            }
        });*/

        // set on layout
        mViewHolder.title.setText(currentCalendarManagerEvent.getTitle());
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        mViewHolder.date.setText(dateFormat.format(currentCalendarManagerEvent.getStartDate())+" "+ currentCalendarManagerEvent.getStartTime()+" - "+ currentCalendarManagerEvent.getEndTime());
        mViewHolder.desc.setText(currentCalendarManagerEvent.getDesc());
        mViewHolder.uid.setText(currentCalendarManagerEvent.getUid());
        long shLen = currentCalendarManagerEvent.getEndDate().getTime() - currentCalendarManagerEvent.getStartDate().getTime();
        long seconds = shLen/1000;
        long minutes = seconds/60;
        long hours = minutes/60;
        minutes = minutes-(hours*60);
        dateFormat = new SimpleDateFormat("hh:mm:ss");

        return convertView;

    }
    private class MyViewHolder {
        TextView title, date, desc, uid;

        // refer on layout
        public MyViewHolder(View item) {
            //sets the View Holder Text Views
            title = (TextView) item.findViewById(R.id.tv_groupView_title);
            desc = (TextView) item.findViewById(R.id.tv_groupView_desc);
            date = (TextView) item.findViewById(R.id.tv_groupView_date);
            uid = (TextView) item.findViewById(R.id.tv_uid);


        }
    }
}
