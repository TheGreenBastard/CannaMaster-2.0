package com.cannamaster.cannamastergrowassistant.ui.main.localcalmanager;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.cannamaster.cannamastergrowassistant.R;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

/**********************************************************
 * Main Activity for Calendar Manager Functions
 **********************************************************/

public class MainActivityCalendarManager extends AppCompatActivity {


    Context context;
    View parentView;
    ArrayList<String> titles;
    ArrayList<Date> dates;
    TreeMap<Date,ArrayList<CalendarManagerEvent>> dataSet;
    ExpandableListEventAdapter eveAdpt;
    ExpandableListView listView;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cal_mgr_activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Set the drawer icon
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        actionBar.setDisplayHomeAsUpEnabled(true);
        parentView = findViewById(android.R.id.content);
        dates = new ArrayList<>();
        titles = new ArrayList<>();
        CoordinatorLayout layout = (CoordinatorLayout) findViewById(R.id.cal_mgr_activity_main);
        getLayoutInflater().inflate(R.layout.cal_mgr_content_main, layout);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.cal_mgr_swipe_refresh);
        context = this;
        getDataFromCalendarTable();
        listView = (ExpandableListView) findViewById(R.id.elv_main);
        dataSet = new TreeMap<>();
        dataSet = getDataFromEventTable();
        eveAdpt = new ExpandableListEventAdapter(context,dates, dataSet,titles);
        listView.setAdapter(eveAdpt);


        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                return false;
            }
        });

        listView.setOnChildClickListener((ExpandableListView.OnChildClickListener) (parent, v, groupPosition, childPosition, id) -> {

            TextView uid = (TextView) v.findViewById(R.id.tv_uid);
            String mUid = uid.getText().toString();
            deleteEvent(Long.parseLong(mUid));
           // updateListView();
            return true;
        });


        swipeRefreshLayout.setOnRefreshListener(() -> {
            updateListView();
            Log.i("refresh", "Layout Refreshed");
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    public void updateListView()
    {
         dataSet = getDataFromEventTable();
         eveAdpt.update(dates,dataSet);
        eveAdpt.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(true);
    }

    // this reads the data from the calendar table
    public void getDataFromCalendarTable() {
        Cursor cur;
        ContentResolver cr = getContentResolver();

        String[] mProjection =
                {
                        CalendarContract.Calendars.ALLOWED_ATTENDEE_TYPES,
                        CalendarContract.Calendars.ACCOUNT_NAME,
                        CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                        CalendarContract.Calendars.CALENDAR_LOCATION,
                        CalendarContract.Calendars.CALENDAR_TIME_ZONE,
                        CalendarContract.Calendars._ID
                };
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(context);
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
        String[] selectionArgs = new String[]{mSharedPreference.getString("account_name",""), mSharedPreference.getString("account_type",""),
                mSharedPreference.getString("owner_account","")};

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR},0);
        }
        cur = cr.query(uri, mProjection, selection, selectionArgs, null);

        while (cur.moveToNext()) {
            String displayName = cur.getString(cur.getColumnIndex(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME));
            String accountName = cur.getString(cur.getColumnIndex(CalendarContract.Calendars.ACCOUNT_NAME));
            String ID = cur.getString(cur.getColumnIndex(CalendarContract.Calendars._ID));
        }
        cur.close();
    }

    // this is the main array for the information table contained in dataset
    public TreeMap<Date,ArrayList<CalendarManagerEvent>> getDataFromEventTable() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, 0);
        }
        Cursor cur;
        ContentResolver cr = getContentResolver();

        String[] mProjection =
                {
                        "_id",
                        CalendarContract.Events.CALENDAR_ID,
                        CalendarContract.Events.TITLE,
                        CalendarContract.Events.DTSTART,
                        CalendarContract.Events.DTEND,
                        CalendarContract.Events.DESCRIPTION,
                        CalendarContract.Events._ID
                };

        Uri uri = CalendarContract.Events.CONTENT_URI;
        String selection = CalendarContract.Events.CALENDAR_ID + " = ? ";
        // this sets every calendar to the same ID so I dont end up with 300
        // individual calendars (Calendar In Use Is Local#16)
        String[] selectionArgs = new String[]{"16"};

        cur = cr.query(uri, mProjection, selection, selectionArgs, null);

        while (cur.moveToNext()) {
            if (Integer.parseInt(cur.getString(cur.getColumnIndex(CalendarContract.Events.CALENDAR_ID))) == 16) {
                try {
                    int id = Integer.parseInt(cur.getString(cur.getColumnIndex(CalendarContract.Events.CALENDAR_ID)));
                    String title = cur.getString(cur.getColumnIndex(CalendarContract.Events.TITLE));
                    long dtstart = Long.parseLong(cur.getString(cur.getColumnIndex(CalendarContract.Events.DTSTART)));
                    long dtend = Long.parseLong(cur.getString(cur.getColumnIndex(CalendarContract.Events.DTEND)));
                    String desc = cur.getString(cur.getColumnIndex(CalendarContract.Events.DESCRIPTION));
                    String eventID = cur.getString(cur.getColumnIndex(CalendarContract.Events._ID));

                    // functions related to getting the date formatted correctly
                    Date testDate = new Date(dtstart);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(testDate);
                    cal.set(Calendar.HOUR_OF_DAY,0);
                    cal.set(Calendar.MINUTE,0);
                    cal.set(Calendar.SECOND,0);
                    Date inputDate = cal.getTime();
                    // end date related code
                    CalendarManagerEvent calendarManagerEvent = new CalendarManagerEvent(id, title, desc, dtstart, dtend, eventID);
                    if(dataSet.get(inputDate)== null)
                    {
                        ArrayList<CalendarManagerEvent> calendarManagerEvents = new ArrayList<>();
                        calendarManagerEvents.add(calendarManagerEvent);
                        dataSet.put(inputDate, calendarManagerEvents);
                        dates.add(inputDate);
                        titles.add(title);
                    }
                    else
                    {
                        ArrayList<CalendarManagerEvent> datesArrayList = dataSet.get(inputDate);
                        boolean unique = true;
                        for(CalendarManagerEvent e : datesArrayList)
                        {
                            if (e.getUid().equals(calendarManagerEvent.getUid())) {
                                unique = false;
                                break;
                            }
                        }
                        if(unique) {
                            datesArrayList.add(calendarManagerEvent);
                            dataSet.remove(inputDate);
                            titles.remove(title);
                            dataSet.put(inputDate, datesArrayList);
                            titles.add(title);
                        }
                    }
                }
                // just error messages
                catch(Exception e)
                {
                    Log.e("Error", e.getMessage());
                    Log.e("start time",cur.getString(cur.getColumnIndex(CalendarContract.Events.DTSTART)));
                    Log.e("end time",cur.getString(cur.getColumnIndex(CalendarContract.Events.DTEND)));
                }
            }
        }
        cur.close();
        // bundle everything up into the dataset
        return dataSet;
    }


    private void deleteEvent(long eventID) {
        Uri deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
        int rows = getContentResolver().delete(deleteUri, null, null);
        Log.i("Calendar", "Rows deleted: " + rows);
        eveAdpt.notifyDataSetChanged();
        finish();
        Intent intent = new Intent(context, MainActivityCalendarManager.class);
        startActivity(intent);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}



