package com.cannamaster.cannamastergrowassistant.ui.main.localcalmanager;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
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
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.cannamaster.cannamastergrowassistant.R;
import com.cannamaster.cannamastergrowassistant.ui.main.GrowAssistantActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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

    private static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = 1001;
    Context context;
    View parentView;
    //ArrayList<String> calendars;
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
        parentView = findViewById(android.R.id.content);
        dates = new ArrayList<Date>();
        CoordinatorLayout layout = (CoordinatorLayout) findViewById(R.id.cal_mgr_activity_main);
        getLayoutInflater().inflate(R.layout.cal_mgr_content_main, layout);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.cal_mgr_content_main);
        context = this;
        getDataFromCalendarTable();
        listView = (ExpandableListView) findViewById(R.id.elv_main);
        calendarManagerEvents = new ArrayList<>();
        dataSet = new TreeMap<Date,ArrayList<CalendarManagerEvent>>();
        dataSet = getDataFromEventTable();
        eveAdpt = new ExpandableListEventAdapter(context,dates, dataSet);
        listView.setAdapter(eveAdpt);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        readEvents(listView);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    final ExpandableListAdapter adapter = ((ExpandableListView) parent).getExpandableListAdapter();
                    long packedPos = ((ExpandableListView) parent).getExpandableListPosition(position);
                    int groupPosition = ExpandableListView.getPackedPositionGroup(packedPos);
                    int childPosition = ExpandableListView.getPackedPositionChild(packedPos);
                    removeEvent(parent);


                    // You now have everything that you would as if this was an OnChildClickListener()
                    // Add your logic here.
                    Toast.makeText(MainActivityCalendarManager.this, "Long click registered",
                            Toast.LENGTH_SHORT).show();
                    // Return true as we are handling the event.
                    return true;
                }

                return false;
            }
        });


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
        eveAdpt.update(dates,dataSet);
        eveAdpt.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }


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
                        CalendarContract.Events.EVENT_LOCATION,
                        CalendarContract.Events.DTSTART,
                        CalendarContract.Events.DTEND,
                        CalendarContract.Events.DESCRIPTION
                };

        Uri uri = CalendarContract.Events.CONTENT_URI;
        String selection = CalendarContract.Events.CALENDAR_ID + " = ? ";

        String[] selectionArgs = new String[]{"16"};

        cur = cr.query(uri, mProjection, selection, selectionArgs, null);

        while (cur.moveToNext()) {
            if (Integer.parseInt(cur.getString(cur.getColumnIndex(CalendarContract.Events.CALENDAR_ID))) == 16) {
                try {
                    int id = Integer.parseInt(cur.getString(cur.getColumnIndex(CalendarContract.Events.CALENDAR_ID)));
                    String title = cur.getString(cur.getColumnIndex(CalendarContract.Events.TITLE));
                    String location = cur.getString(cur.getColumnIndex(CalendarContract.Events.EVENT_LOCATION));
                    long dtstart = Long.parseLong(cur.getString(cur.getColumnIndex(CalendarContract.Events.DTSTART)));
                    long dtend = Long.parseLong(cur.getString(cur.getColumnIndex(CalendarContract.Events.DTEND)));
                    String desc = cur.getString(cur.getColumnIndex(CalendarContract.Events.DESCRIPTION));

                    Date testDate = new Date(dtstart);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(testDate);
                    cal.set(Calendar.HOUR_OF_DAY,0);
                    cal.set(Calendar.MINUTE,0);
                    cal.set(Calendar.SECOND,0);
                    Date inputDate = cal.getTime();
                    CalendarManagerEvent calendarManagerEvent = new CalendarManagerEvent(id, title, desc, dtstart, dtend);
                    //String date = dateFormat.format(event.getStartDate());
                    if(dataSet.get(inputDate)== null)
                    {
                        ArrayList<CalendarManagerEvent> calendarManagerEvents = new ArrayList<>();
                        calendarManagerEvents.add(calendarManagerEvent);
                        dataSet.put(inputDate, calendarManagerEvents);
                        dates.add(inputDate);
                    }
                    else
                    {
                        ArrayList<CalendarManagerEvent> calendarManagerEvents = dataSet.get(inputDate);
                        boolean unique = true;
                        for(CalendarManagerEvent e : calendarManagerEvents)
                        {
                            if(e.getUid() == calendarManagerEvent.getUid())
                            {
                                unique = false;
                            }
                        }
                        if(unique) {
                            calendarManagerEvents.add(calendarManagerEvent);
                            dataSet.remove(inputDate);
                            dataSet.put(inputDate, calendarManagerEvents);
                        }
                    }
                }
                catch(Exception e)
                {
                    Log.e("Error", e.getMessage());
                    Log.e("start time",cur.getString(cur.getColumnIndex(CalendarContract.Events.DTSTART)));
                    Log.e("end time",cur.getString(cur.getColumnIndex(CalendarContract.Events.DTEND)));
                }
            }
        }
        return dataSet;
    }

    public void removeEvent(View view) {
        String eventTitle = "Jazzercise";

        final String[] INSTANCE_PROJECTION = new String[] {
                CalendarContract.Instances.EVENT_ID,      // 0
                CalendarContract.Instances.BEGIN,         // 1
                CalendarContract.Instances.TITLE          // 2
        };
        // The indices for the projection array above.
        final int PROJECTION_ID_INDEX = 0;
        final int PROJECTION_BEGIN_INDEX = 1;
        final int PROJECTION_TITLE_INDEX = 2;

        // Specify the date range you want to search for recurring event instances
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2017, 9, 23, 8, 0);
        long startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2018, 1, 24, 8, 0);
        long endMillis = endTime.getTimeInMillis();


        // The ID of the recurring event whose instances you are searching for in the Instances table
        String selection = CalendarContract.Instances.TITLE + " = ?";
        String[] selectionArgs = new String[] {eventTitle};

        // Construct the query with the desired date range.
        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startMillis);
        ContentUris.appendId(builder, endMillis);

        // Submit the query
        Cursor cur =  getContentResolver().query(builder.build(), INSTANCE_PROJECTION, selection, selectionArgs, null);

        while(cur.moveToNext()) {
            // Get the field values
            long eventID = cur.getLong(PROJECTION_ID_INDEX);
            long beginVal = cur.getLong(PROJECTION_BEGIN_INDEX);
            String title = cur.getString(PROJECTION_TITLE_INDEX);

            Uri deleteUri = null;
            deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
            int rows = getContentResolver().delete(deleteUri, null, null);
            Log.i("Calendar", "Rows deleted: " + rows);
        }

        showEvents(eventTitle);
    }

    private void showEvents(String eventTitle) {
        final String[] INSTANCE_PROJECTION = new String[] {
                CalendarContract.Instances.EVENT_ID,       // 0
                CalendarContract.Instances.BEGIN,         // 1
                CalendarContract.Instances.TITLE,        // 2
                CalendarContract.Instances.ORGANIZER    //3
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

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, events);
        listView.setAdapter(eveAdpt);
    }

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
        beginTime.set(2017, 9, 23, 8, 0);
        long startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2018, 1, 24, 8, 0);
        long endMillis = endTime.getTimeInMillis();


        // The ID of the recurring event whose instances you are searching for in the Instances table
        String selection = CalendarContract.Instances.EVENT_ID + " = ?";
        String[] selectionArgs = new String[] {"207"};

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
            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Log.i("Calendar", "Date: " + formatter.format(calendar.getTime()));

            events.add(String.format("Event ID: %d\nEvent: %s\nOrganizer: %s\nDate: %s", eventID, title, organizer, formatter.format(calendar.getTime())));
        }

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, events);
        listView.setAdapter(eveAdpt);
    }


    private void updateEvent(long eventID) {
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.TITLE, "Kickboxing");
        Uri updateUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
        int rows = getContentResolver().update(updateUri, values, null, null);
        Log.i("Calendar", "Rows updated: " + rows);
    }

    private void deleteEvent(long eventID) {
        Uri deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
        int rows = getContentResolver().delete(deleteUri, null, null);
        Log.i("Calendar", "Rows deleted: " + rows);
    }


    }



