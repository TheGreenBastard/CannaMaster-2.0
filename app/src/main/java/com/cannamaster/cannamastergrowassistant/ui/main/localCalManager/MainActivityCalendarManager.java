package com.cannamaster.cannamastergrowassistant.ui.main.localcalmanager;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.cannamaster.cannamastergrowassistant.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

/**********************************************************
 * Main Activity for Calendar Manager Functions
 **********************************************************/

public class MainActivityCalendarManager extends CalendarManagerAppActivity {

    Context context;
    View parentView;
    //ArrayList<String> calendars;
    ArrayList<Date> dates;
    ArrayList<CalendarManagerEvent> calendarManagerEvents;
    TreeMap<Date,ArrayList<CalendarManagerEvent>> dataSet;
    ExpandableEventAdapter eveAdpt;
    ExpandableListView listView;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentView = findViewById(android.R.id.content);
        dates = new ArrayList<Date>();
        CoordinatorLayout layout = (CoordinatorLayout) findViewById(R.id.cal_mgr_activity_main);
        getLayoutInflater().inflate(R.layout.cal_mgr_content_main, layout);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.content_main);
        context = this;
        //calendars = getCalendars();
        getDataFromCalendarTable();
        listView = (ExpandableListView) findViewById(R.id.list);
        calendarManagerEvents = new ArrayList<>();
        dataSet = new TreeMap<Date,ArrayList<CalendarManagerEvent>>();
        //events.add(new Event(16,"Work","",1571048836,1571048847));
        dataSet = getDataFromEventTable();
        eveAdpt = new ExpandableEventAdapter(context,dates, dataSet);
        listView.setAdapter(eveAdpt);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent = new Intent(context, CalendarAddEvent.class);
                startActivityForResult(intent,1);
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

    /*public ArrayList<String> getCalendars(){
        ArrayList<String> content = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR},0);
        }
        String[] projection =
                new String[]{
                        CalendarContract.Calendars._ID,
                        CalendarContract.Calendars.NAME,
                        CalendarContract.Calendars.ACCOUNT_NAME,
                        CalendarContract.Calendars.ACCOUNT_TYPE,
                        CalendarContract.Calendars.OWNER_ACCOUNT};
        Cursor calCursor =
                getContentResolver().
                        query(CalendarContract.Calendars.CONTENT_URI,
                                projection,
                                CalendarContract.Calendars.VISIBLE + " = 1",
                                null,
                                CalendarContract.Calendars._ID + " ASC");
        if (calCursor.moveToFirst()) {
            do {
                long id = calCursor.getLong(0);
                String displayName = calCursor.getString(1);
                String AccountName = calCursor.getString(2);
                String AccountType = calCursor.getString(3);
                String OwnerAccount = calCursor.getString(4);
                content.add(displayName);
                // ...
            } while (calCursor.moveToNext());
        }
        return content;
    }*/
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
        // ask for permissions
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
                //DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                //String dateString = dateFormat.format(new Date(dtstart));
                Date testDate = new Date(dtstart);
                Calendar cal = Calendar.getInstance();
                cal.setTime(testDate);
                cal.set(Calendar.HOUR_OF_DAY,0);
                cal.set(Calendar.MINUTE,0);
                cal.set(Calendar.SECOND,0);
                Date inputDate = cal.getTime();
                CalendarManagerEvent calendarManagerEvent = new CalendarManagerEvent(id, title, dtstart, dtend);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(context, Settings.class);
                startActivityForResult(intent,0);
                return true;
            case R.id.menu_refresh:
                updateListView();
                return true;
        }
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

}
