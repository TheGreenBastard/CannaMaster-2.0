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
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.cannamaster.cannamastergrowassistant.R;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    ArrayList<CalendarManagerEvent> calendarManagerEvents;
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
        dates = new ArrayList<Date>();
        titles = new ArrayList<String>();
        CoordinatorLayout layout = (CoordinatorLayout) findViewById(R.id.cal_mgr_activity_main);
        getLayoutInflater().inflate(R.layout.cal_mgr_content_main, layout);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.cal_mgr_content_main);
        context = this;
        getDataFromCalendarTable();
        listView = (ExpandableListView) findViewById(R.id.elv_main);
        calendarManagerEvents = new ArrayList<>();
        dataSet = new TreeMap<Date,ArrayList<CalendarManagerEvent>>();
        dataSet = getDataFromEventTable();
        eveAdpt = new ExpandableListEventAdapter(context,dates, dataSet,titles);
        listView.setAdapter(eveAdpt);


/*
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                int pos = parent.getFlatListPosition(ExpandableListView
                        .getPackedPositionForGroup(groupPosition));
                if (!parent.isGroupExpanded(groupPosition) && !parent.isItemChecked(pos)) {
                    parent.expandGroup(groupPosition, true);
                    parent.setItemChecked(pos, true);
                } else if (parent.isGroupExpanded(groupPosition) && parent.isItemChecked(pos)) {
                    parent.collapseGroup(groupPosition);
                    parent.setItemChecked(pos, false);
                } else if (parent.isGroupExpanded(groupPosition) && !parent.isItemChecked(pos)) {
                    parent.setItemChecked(pos, true);
                }
                return true;
            }
        });*/

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                int pos = parent.getFlatListPosition(ExpandableListView
                        .getPackedPositionForGroup(groupPosition));
                if (!parent.isGroupExpanded(groupPosition) && !parent.isItemChecked(pos)) {
                    parent.expandGroup(groupPosition, true);
                    parent.setItemChecked(pos, true);
                } else if (parent.isGroupExpanded(groupPosition) && parent.isItemChecked(pos)) {
                    parent.collapseGroup(groupPosition);
                    parent.setItemChecked(pos, false);
                } else if (parent.isGroupExpanded(groupPosition) && !parent.isItemChecked(pos)) {
                    parent.setItemChecked(pos, true);
                }



                TextView uid = (TextView) v.findViewById(R.id.tv_uid);
                String mUid = uid.getText().toString();
                deleteEvent(Long.parseLong(mUid));
                updateListView();
                return true;
            }
        });
/*        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {

                    TextView tv = (TextView) view.findViewById(R.id.tv_uid);
                    String uid = tv.getText().toString();
                    long convertUid = Long.parseLong(uid);
                    deleteEvent(convertUid);
                    finish();
                    Intent intent = new Intent(context, MainActivityCalendarManager.class);
                    startActivity(intent);

                    // You now have everything that you would as if this was an OnChildClickListener()
                    // Add your logic here.
                    Toast.makeText(MainActivityCalendarManager.this, "Long click registered",
                            Toast.LENGTH_SHORT).show();
                    // Return true as we are handling the event.
                    return true;
                }

                return false;
            }
        });*/

/*        listView.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.i("TAG","I got clicked childPosition:["+childPosition+"] groupPosition:["+groupPosition+"] id:["+id+"]");
                TextView tv = (TextView) v.findViewById(R.id.tv_uid);
                String uid = tv.getText().toString();
                long convertUid = Long.parseLong(uid);
                deleteEvent(convertUid);
                return true;
            }
        });*/


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateListView();
                Log.i("refresh", "Layout Refreshed");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                // Get String data from Intent
                String returnString = data.getStringExtra("string");

                // Set text view with string
                getSnackbar(parentView, returnString);
            }

        }
    }

    public void getSnackbar(View view, String text)
    {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    public void updateListView()
    {
        dataSet = getDataFromEventTable();
        // eveAdpt.update(dates,dataSet);
        eveAdpt.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(true);
    }

    // this reads the data from the calendar table
    public void getDataFromCalendarTable() {
        Cursor cur = null;
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
    }

    // this is the main array for the information table contained in dataset
    public TreeMap<Date,ArrayList<CalendarManagerEvent>> getDataFromEventTable() {
        ArrayList<CalendarManagerEvent> content = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, 0);
        }
        Cursor cur = null;
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
                            if(e.getUid() == calendarManagerEvent.getUid())
                            {
                                unique = false;
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
        // bundle everything up into the dataset
        return dataSet;
    }

    // This function removes an event from the calendar and your list of events
    public void removeEvent(View view) {
        String eventTitle = "CannaMaster Grow Assistant";

        // projection array (this is faster)
        final String[] INSTANCE_PROJECTION = new String[] {
                CalendarContract.Instances.EVENT_ID,      // 0
                CalendarContract.Instances.BEGIN,         // 1
                CalendarContract.Instances.TITLE     };   // 2
        // The indices for the projection array above.
        final int PROJECTION_ID_INDEX = 0;
        final int PROJECTION_BEGIN_INDEX = 1;
        final int PROJECTION_TITLE_INDEX = 2;

        // Specify the date range you want to search for recurring event instances
        // default set for 5 years
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2020, 1, 23, 8, 0);
        long startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2025, 1, 24, 8, 0);
        long endMillis = endTime.getTimeInMillis();

        // Select the matching Name attribute from the db.
        // The "Name/Title" of the recurring event whose instances you are searching for in the Instances table
        String selection = CalendarContract.Instances.TITLE + " = ?";
        String[] selectionArgs = new String[] {eventTitle};

        // Construct the query with the desired date range.
        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startMillis);
        ContentUris.appendId(builder, endMillis);

        // Submit the query
        Cursor cur =  getContentResolver().query(builder.build(), INSTANCE_PROJECTION, selection, selectionArgs, null);

        // loop through all the records
        while(cur.moveToNext()) {
            // Get the field values by cycling through all the records
            long eventID = cur.getLong(PROJECTION_ID_INDEX);
            long beginVal = cur.getLong(PROJECTION_BEGIN_INDEX);
            String title = cur.getString(PROJECTION_TITLE_INDEX);

            // Delete the event as it relates to the unique URI
            Uri deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
            int rows = getContentResolver().delete(deleteUri, null, null);
            Log.i("Calendar", "Rows deleted: " + rows);
        }
        showEvents(eventTitle);
    }

    private void showEvents(String eventTitle) {
        // Projection Array (This is faster)
        final String[] INSTANCE_PROJECTION = new String[] {
                CalendarContract.Instances.EVENT_ID,       // 0
                CalendarContract.Instances.BEGIN,         // 1
                CalendarContract.Instances.TITLE,        // 2
                // I think i can get rid of ORGANIZER
                CalendarContract.Instances.ORGANIZER    }; //3

        // The indices for the projection array above.
        final int PROJECTION_ID_INDEX = 0;
        final int PROJECTION_BEGIN_INDEX = 1;
        final int PROJECTION_TITLE_INDEX = 2;
        final int PROJECTION_ORGANIZER_INDEX = 3;

        // Specify the date range you want to search for recurring event instances
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2020, 1, 23, 8, 0);
        long startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2025, 1, 24, 8, 0);
        long endMillis = endTime.getTimeInMillis();


        // The Name/Title of the recurring event whose instances you are searching for in the Instances table
        String selection = CalendarContract.Instances.TITLE + " = ?";
        String[] selectionArgs = new String[] {eventTitle};

        // Construct the query with the desired date range.
        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startMillis);
        ContentUris.appendId(builder, endMillis);

        // Submit the query
        Cursor cur =  getContentResolver().query(builder.build(), INSTANCE_PROJECTION, selection, selectionArgs, null);

        ArrayList<String> events = new ArrayList<>();
        while (cur.moveToNext()) {
            // Get the field values
            long eventID = cur.getLong(PROJECTION_ID_INDEX);
            long beginVal = cur.getLong(PROJECTION_BEGIN_INDEX);
            String title = cur.getString(PROJECTION_TITLE_INDEX);
            String organizer = cur.getString(PROJECTION_ORGANIZER_INDEX);

            // Do something with the values.
            Log.i("Calendar", "Event:  " + title);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(beginVal);
            DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            Log.i("Calendar", "Date: " + formatter.format(calendar.getTime()));

            events.add(String.format("Event ID: %d\nEvent: %s\nOrganizer: %s\nDate: %s", eventID, title, organizer, formatter.format(calendar.getTime())));
        }

        //ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, R.layout.cal_mgr_groupview_date, R.id.tv_groupView_date, events);
        //listView.setAdapter(eveAdpt);
    }

/*
    private boolean isEventAlreadyExist(String eventTitle) {
        final String[] INSTANCE_PROJECTION = new String[] {
                CalendarContract.Instances.EVENT_ID,      // 0
                CalendarContract.Instances.BEGIN,         // 1
                CalendarContract.Instances.TITLE          // 2
        };

        long calID = 3;
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2017, 11, 18, 6, 00);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2017, 11, 18, 8, 00);
        endMillis = endTime.getTimeInMillis();

        // The ID of the recurring event whose instances you are searching for in the Instances table
        String selection = CalendarContract.Instances.TITLE + " = ?";
        String[] selectionArgs = new String[] {eventTitle};

        // Construct the query with the desired date range.
        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startMillis);
        ContentUris.appendId(builder, endMillis);

        // Submit the query
        Cursor cur =  getContentResolver().query(builder.build(), INSTANCE_PROJECTION, selection, selectionArgs, null);

        return cur.getCount() > 0;
    }
*/

    public void readEvents(View view) {
        final String[] INSTANCE_PROJECTION = new String[] {
                CalendarContract.Instances.EVENT_ID,      // 0
                CalendarContract.Instances.BEGIN,         // 1
                CalendarContract.Instances.TITLE,          // 2
                CalendarContract.Instances.ORGANIZER
        };

        // The indices for the projection array above.
        final int PROJECTION_ID_INDEX = 0;
        final int PROJECTION_BEGIN_INDEX = 1;
        final int PROJECTION_TITLE_INDEX = 2;
        final int PROJECTION_ORGANIZER_INDEX = 3;

        // Specify the date range you want to search for recurring event instances
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2020, 1, 23, 8, 0);
        long startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2025, 1, 24, 8, 0);
        long endMillis = endTime.getTimeInMillis();


        // The ID of the recurring event whose instances you are searching for in the Instances table
        String selection = CalendarContract.Instances.EVENT_ID + " = ?";
        String[] selectionArgs = new String[] {"16"};

        // Construct the query with the desired date range.
        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startMillis);
        ContentUris.appendId(builder, endMillis);

        // Submit the query
        Cursor cur =  getContentResolver().query(builder.build(), INSTANCE_PROJECTION, null, null, null);

        ArrayList<String> events = new ArrayList<>();
        while (cur.moveToNext()) {

            // Get the field values
            long eventID = cur.getLong(PROJECTION_ID_INDEX);
            long beginVal = cur.getLong(PROJECTION_BEGIN_INDEX);
            String title = cur.getString(PROJECTION_TITLE_INDEX);
            String organizer = cur.getString(PROJECTION_ORGANIZER_INDEX);

            // Do something with the values.
            Log.i("Calendar", "Event:  " + title);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(beginVal);
            DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            Log.i("Calendar", "Date: " + formatter.format(calendar.getTime()));

            events.add(String.format("Event ID: %d\nEvent: %s\nOrganizer: %s\nDate: %s", eventID, title, organizer, formatter.format(calendar.getTime())));
        }
    }



    private void deleteEvent(long eventID) {
        Uri deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
        int rows = getContentResolver().delete(deleteUri, null, null);
        Log.i("Calendar", "Rows deleted: " + rows);
        eveAdpt.notifyDataSetChanged();

    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}



