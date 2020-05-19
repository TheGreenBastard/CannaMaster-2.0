package com.cannamaster.cannamastergrowassistant;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class GrowAssistantActivity extends AppCompatActivity {

    TextView mDateText;
    TextView mTimeText;

    EditText dateText;
    EditText hourNotifyText;
    EditText titleText;
    EditText descText;
    EditText reminderText;

    String mTitle;
    String mRepeat = "false";
    String mRepeatType = "Select Repeat Interval";
    String mActive = "true";

    // Layout components for time and date picker
    ImageView mDateDialog;
    ImageView mTimeDialog;
    TextView mSetTime;
    TextView mSetDate;

    // Values for Button
    Button buttonSetNotifications;

    // Initialize the RadioButtons
    RadioGroup radioWateringSchedule;
    RadioGroup radioFertilizerChoice;
    RadioGroup radioGrowMediumChoice;
    RadioGroup radioIndicaSativaOptions;




    // Flowering Days Variable
    int mFloweringDays;
    int mFlush2WeeksBefore;

    // Values for orientation change
    private static final String KEY_TITLE = "title_key";
    private static final String KEY_TIME = "time_key";
    private static final String KEY_DATE = "date_key";
    private static final String KEY_REPEAT = "repeat_key";
    private static final String KEY_REPEAT_NO = "repeat_no_key";
    private static final String KEY_REPEAT_TYPE = "repeat_type_key";
    private static final String KEY_ACTIVE = "active_key";

    // Constant values in milliseconds
    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDay = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonth = 2592000000L;
    private static final long mil2weeks = 1209600000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grow_assistant_activity_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // set page title in appbar
        Objects.requireNonNull(getSupportActionBar()).setTitle("CannaMaster Grow Assistant");
        // this places the back press arrow on the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dateText = (EditText) findViewById(R.id.date_edit_text);
        hourNotifyText = (EditText) findViewById(R.id.time_edit_text);
        titleText = (EditText) findViewById(R.id.titleText);
        descText = (EditText) findViewById(R.id.descText);
        reminderText = (EditText) findViewById(R.id.reminderText);



        /** This is how we request the user for Calendar write and read permissions **/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // Ask the user for permission and then re-run
            Log.i("No Permission", "No Permission");
            int requestCode = 1;
            // Re-request to ask for permission
            requestPermissions(new String[]{"android.permission.WRITE_CALENDAR", "android.permission.READ_CALENDAR"}, requestCode);
        }
    }



    // This sets the back arrow on the toolbar to work
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    /** Method to add the grow to the users on device calendar **/
    private void addToDeviceCalendar(String startDate,String endDate, String title,String description, int reminder) {



        // Make the strings for the formatted times
        String startDateFormatted = "";
        String endDateFormatted = "";

        // Get a calendar instance
        GregorianCalendar calDate = new GregorianCalendar();

        // Formats used to format the date into the correct format
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy,MM,dd,HH,mm");

        // Convert the dates given to the correct format
        try {
            // Convert date to the first format
            Date date = originalFormat.parse(startDate);
            Date eDate = originalFormat.parse(endDate);

            // Convert it into the second format
            startDateFormatted = targetFormat.format(date);
            endDateFormatted = targetFormat.format(eDate);

        } catch (ParseException ex) {} catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        // Makes an ArrayList of the date split into different sections
        ArrayList<String> startDateList = new ArrayList<String>(Arrays.asList(startDateFormatted.split(",")));
        ArrayList<String> endDateList = new ArrayList<String>(Arrays.asList(endDateFormatted.split(",")));

        // Converts the strings of the start and end list into ints
        int startYear = Integer.parseInt(startDateList.get(0));
        int startMonth = Integer.parseInt(startDateList.get(1));
        int startDay = Integer.parseInt(startDateList.get(2));
        int startHour = Integer.parseInt(startDateList.get(3));
        int startMinute = Integer.parseInt(startDateList.get(4));

        int endYear = startYear;
        int endMonth = startMonth;
        int endDay = startDay;
        int endHour = startHour + 1;
        int endMinute = startMinute;

        // Sets the date and gets the time in milliseconds
        calDate.set(startYear, startMonth-1, startDay, startHour, startMinute);
        long startMillis = calDate.getTimeInMillis();

        // Sets the date and gets the time in milliseconds
        calDate.set(endYear, endMonth-1, endDay, endHour, endMinute);
        long endMillis = calDate.getTimeInMillis();

        try {
            // Puts the values into a event for the calendar
            ContentResolver cr = this.getContentResolver();
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.DTSTART, startMillis);
            values.put(CalendarContract.Events.DTEND, endMillis);
            values.put(CalendarContract.Events.TITLE, title);
            values.put(CalendarContract.Events.DESCRIPTION, description);
            values.put(CalendarContract.Events.HAS_ALARM,1);
            values.put(CalendarContract.Events.CALENDAR_ID, 1);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance()
                    .getTimeZone().getID());
            System.out.println(Calendar.getInstance().getTimeZone().getID());

            // If the user still hasn't given permission, then re-ask
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                // Ask the user for permission and then re-run
                Log.i("No Permission", "No Permission");
                Toast.makeText(this, "Failed to give permission, try again", Toast.LENGTH_SHORT).show();
                int requestCode = 1;
                requestPermissions(new String[]{"android.permission.WRITE_CALENDAR", "android.permission.READ_CALENDAR"}, requestCode);
                return;
            }
            // Add the event to the calendar
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

            long eventId = Long.parseLong(uri.getLastPathSegment());
            Log.i("Event_Id", String.valueOf(eventId));

            // We add the reminder time to the new event
            try {
                values.clear();
                values.put(CalendarContract.Reminders.EVENT_ID, eventId);
                values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
                values.put(CalendarContract.Reminders.MINUTES, reminder);
                getContentResolver().insert(CalendarContract.Reminders.CONTENT_URI, values);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Toast.makeText(this, "Now check Google Calendar, you may need to refresh it, it takes time", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**  This takes the data from the textviews and turns it into an event to pass to calendar **/
    public void onMakeEvent (View view) {
        // Get the EditText views by ID
        // set some values into variables from user input

        /**  This checks the edit text fields to ensure there is some kind of data entered **/
        int yourDesiredLength = 2;
        if (titleText.getText().length() < yourDesiredLength) {
            titleText.setError("You must provide a title for the event");
        }else if (descText.getText().length() < yourDesiredLength) {
            descText.setError("You must provide a short description");
        }
        else if (dateText.getText().length() < 10) {
            dateText.setError("please provide a start date 'YYYY-MM-DD'");
        }
        else if (dateText.getText().length() > 10) {
            dateText.setError("please provide a start date 'YYYY-MM-DD'");
        }
        else if (hourNotifyText.getText().length() < 5) {
            hourNotifyText.setError("please provide a time for events 'hh:mm'");
        }
        else if (hourNotifyText.getText().length() > 5) {
            hourNotifyText.setError("please provide a time for events 'hh:mm'");
        }
        else if (reminderText.getText().length() < 2) {
            descText.setError("Enter a time to alert before the event");
        }
        else
        {



            // Converts the EditTexts to Strings
            String mDate = dateText.getText().toString();
            String startTime = hourNotifyText.getText().toString();



            // takes the 2 EditText fields and combines them to set the date and time of notification
            String startDate = mDate + " " + startTime;
            // sets the end date the same as the start date so the rest of the hard code still works
            String endDate = startDate;
            // this is the event title
            String titleString = titleText.getText().toString();
            // this is the event description
            String descString = descText.getText().toString();
            // this is the time before the event that the reminder will fire
            String reminderString = reminderText.getText().toString();
            int reminderInt = Integer.parseInt(reminderString);
            // Calls the function to add the event to the calender
            addToDeviceCalendar(startDate, endDate, titleString, descString, reminderInt);
        }

    }
}
