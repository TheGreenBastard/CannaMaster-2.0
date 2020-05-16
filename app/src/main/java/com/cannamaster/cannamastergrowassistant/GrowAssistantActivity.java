package com.cannamaster.cannamastergrowassistant;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import com.cannamaster.cannamastergrowassistant.ui.main.dialogs.DatePickerDialogFragment;
import com.cannamaster.cannamastergrowassistant.ui.main.dialogs.TimePickerDialogFragment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class GrowAssistantActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    TextView mDateText, mTimeText;

    String mTitle;
    String mRepeat = "false";
    String mRepeatType = "Select Repeat Interval";
    String mActive = "true";

    // Layout components for time and date picker
    ImageView mDateDialog = findViewById(R.id.date_icon);
    ImageView mTimeDialog = findViewById(R.id.time_icon);
    TextView mSetTime = findViewById(R.id.set_time);
    TextView mSetDate = findViewById(R.id.set_date);

    // Values for Button
    Button buttonSetNotifications = findViewById(R.id.button_set_notifications);

    // Initialize the RadioButtons
    RadioGroup radioWateringSchedule = findViewById(R.id.rgWaterOptions);
    RadioGroup radioFertilizerChoice = findViewById(R.id.rgFertilizerOptions);
    RadioGroup radioGrowMediumChoice = findViewById(R.id.rgGrowMedium);
    RadioGroup radioIndicaSativaOptions = findViewById(R.id.rgIndicaSativa);

    // Declare Radio Buttons
    final RadioButton radioOutdoor = (RadioButton) findViewById(R.id.radioOutdoor);
    final RadioButton radioSoil = (RadioButton) findViewById(R.id.radioSoil);
    final RadioButton radioHydroponic = (RadioButton) findViewById(R.id.radioHydro);
    final RadioButton radioChemical = (RadioButton) findViewById(R.id.radioChemical);
    final RadioButton radioOrganic = (RadioButton) findViewById(R.id.radioOrganic);
    final RadioButton radioWaterDaily = (RadioButton) findViewById(R.id.radioButtonWaterDaily);
    final RadioButton radioWater2Days = (RadioButton) findViewById(R.id.radioButtonWater2Days);
    final RadioButton radioWater3Days = (RadioButton) findViewById(R.id.radioButtonWater3Days);
    final RadioButton radioWaterWeekly = (RadioButton) findViewById(R.id.radioButtonWaterWeekly);
    final RadioButton radioWaterNoNotify = (RadioButton) findViewById(R.id.radioButtonWaterNoNotify);


    // Flowering Days Variable
    private int mFloweringDays;
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


    // To save state on device rotation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putCharSequence(KEY_TITLE, mTitle);
        outState.putCharSequence(KEY_TIME, mTimeText.getText());
        outState.putCharSequence(KEY_DATE, mDateText.getText());
        outState.putCharSequence(KEY_REPEAT, mRepeat);
        outState.putCharSequence(KEY_REPEAT_TYPE, mRepeatType);
        outState.putCharSequence(KEY_ACTIVE, mActive);
    }

    // OnCreate (main setup when activity starts)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grow_assistant_activity_layout;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Set the page title
        Objects.requireNonNull(getSupportActionBar()).setTitle("Grow Assistant");
        // set the back navigation arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set the Onclick listeners for the icons for time picker
        mTimeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialogFragment timePickerDialogFragment = new TimePickerDialogFragment(GrowAssistantActivity.this);
                timePickerDialogFragment.show(getSupportFragmentManager(),"timepicker");
            }
        });
        // Set the Onclick listeners for the icons for date picker
        mDateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment(GrowAssistantActivity.this);
                datePickerDialogFragment.show(getSupportFragmentManager(),"datepicker");
            }
        });

    }

    // This is the working add to device calendar method - try not to fuck with it
    private void addToDeviceCalendar(String startDate, String endDate, String title, String description, String location, int reminder,
                                     String recurrenceRule, String recurrenceDate) {

        // Make the strings for the formatted times
        String startDateFormatted = "";
        String endDateFormatted = "";

        // Get a calendar instance
        GregorianCalendar calDate = new GregorianCalendar();

        // Formats used to format the date into the correct format
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy,MM,dd,HH,mm");

        /** Convert the dates given to the correct format **/
        try {
            // Convert date to the first format
            Date date = originalFormat.parse(startDate);
            Date eDate = originalFormat.parse(endDate);
            // Convert it into the second format
            startDateFormatted = targetFormat.format(date);
            endDateFormatted = targetFormat.format(eDate);
        } catch (ParseException ex) {
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        // Makes an ArrayList of the date split into different sections
        ArrayList<String> startDateList = new ArrayList<String>(Arrays.asList(startDateFormatted.split(",")));
        ArrayList<String> endDateList = new ArrayList<String>(Arrays.asList(endDateFormatted.split(",")));

        // Converts the strings of the start list into ints
        int startYear = Integer.parseInt(startDateList.get(0));
        int startMonth = Integer.parseInt(startDateList.get(1));
        int startDay = Integer.parseInt(startDateList.get(2));
        int startHour = Integer.parseInt(startDateList.get(3));
        int startMinute = Integer.parseInt(startDateList.get(4));
        // Converts the strings of the end list into ints
        int endYear = Integer.parseInt(endDateList.get(0));
        int endMonth = Integer.parseInt(endDateList.get(1));
        int endDay = Integer.parseInt(endDateList.get(2));
        int endHour = Integer.parseInt(endDateList.get(3));
        int endMinute = Integer.parseInt(endDateList.get(4));

        // Sets the date and gets the time in milliseconds into startMillis
        calDate.set(startYear, startMonth - 1, startDay, startHour, startMinute);
        long startMillis = calDate.getTimeInMillis();

        // Sets the date and gets the time in milliseconds
        calDate.set(endYear, endMonth - 1, endDay, endHour, endMinute);
        long endMillis = calDate.getTimeInMillis();

        try {
            /** Puts the values into an event for the calendar  **/
            ContentResolver cr = this.getContentResolver();
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.DTSTART, startMillis);
            values.put(CalendarContract.Events.DTEND, endMillis);
            values.put(CalendarContract.Events.TITLE, title);
            values.put(CalendarContract.Events.DESCRIPTION, description);
            values.put(CalendarContract.Events.EVENT_LOCATION, location);
            values.put(CalendarContract.Events.HAS_ALARM, 1);
            values.put(CalendarContract.Events.CALENDAR_ID, 1);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, android.icu.util.Calendar.getInstance().getTimeZone().getID());
            // i added the following values to the method
            values.put(CalendarContract.Events.RRULE, recurrenceRule);
            values.put(CalendarContract.Events.RDATE, recurrenceDate);

            System.out.println(Calendar.getInstance().getTimeZone().getID());

            /** If the user still hasn't given permission, then re-ask **/
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                // Ask the user for permission and then re-run
                Log.i("No Permission", "No Permission");
                Toast.makeText(this, "Failed to give permission, try again", Toast.LENGTH_SHORT).show();
                int requestCode = 1;
                requestPermissions(new String[]{"android.permission.WRITE_CALENDAR", "android.permission.READ_CALENDAR"}, requestCode);
                return;
            }

            /** Add the event to the calendar **/
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
            // The Event ID
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

    /**  This takes the values from the text inputs and puts them into a calendar array for insertion
     *   into a calendar.  todo : edit this to set events in app based on radio and date picker choices.
     * */
    public void onMakeEvent(View view) {
        // Get the EditText views by ID
        EditText startText = (EditText) findViewById(R.id.startText);
        EditText endText = (EditText) findViewById(R.id.endText);
        EditText titleText = (EditText) findViewById(R.id.titleText);
        EditText descText = (EditText) findViewById(R.id.descText);
        EditText locText = (EditText) findViewById(R.id.locText);
        EditText reminderText = (EditText) findViewById(R.id.reminderText);
// todo : make Edit Text for recurrence rules and dates or hardcode it into something else
        EditText recurrenceRule = (EditText) findViewById(R.id.recurrenceRule);
        EditText recurrenceDate = (EditText) findViewById(R.id.recurrenceDate);
        // Converts the EditTexts to Strings
        String startDate = startText.getText().toString();
        String endDate = endText.getText().toString();
        String titleString = titleText.getText().toString();
        String descString = descText.getText().toString();
        String locString = locText.getText().toString();
        String reminderString = reminderText.getText().toString();
        String recurrenceRule = recurrenceRule.getText().toString();
        String recurrenceDate = recurrenceDate.getText().toString();

        int reminderInt = Integer.parseInt(reminderString);
        // Calls the function to add the event to the calender
        addToDeviceCalendar(startDate, endDate, titleString, descString, locString, reminderInt, recurrenceRule, recurrenceDate);
    }

    /**   Set Reminder Method   **/
    private void setReminder(int minutes, String eventID) {
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Reminders.MINUTES, 15);
        values.put(CalendarContract.Reminders.EVENT_ID, eventID);
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        // check to make sure permissions have been granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // Ask the user for permission and then re-run
            Log.i("No Permission", "No Permission");
            Toast.makeText(this, "Failed to give permission, try again", Toast.LENGTH_SHORT).show();
            int requestCode = 1;
            requestPermissions(new String[]{"android.permission.WRITE_CALENDAR", "android.permission.READ_CALENDAR"}, requestCode);
            return;
        }
        Uri uri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
    }


    // I dont think this is actually being used right now
    private void getValuesFromUserCalendar ((String startDate,String endDate, String title,String description, String location, int reminder) {
        Cursor cursor;  /**  this may all move to a switch statement (button) **/
        // Before reading the users calendar get their permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // Ask the user for permission and then re-run
            Log.i("No Permission", "No Permission");
            Toast.makeText(this, "Failed to give permission, try again", Toast.LENGTH_SHORT).show();
            int requestCode = 1;
            requestPermissions(new String[]{"android.permission.WRITE_CALENDAR", "android.permission.READ_CALENDAR"}, requestCode);
            return;
        }

// get the data from the calendar
// I dont think this is actually being used right now
        cursor = getContentResolver().query(CalendarContract.Events.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor != null) {
                int id_1 = cursor.getColumnIndex(CalendarContract.Events._ID);
                int id_2 = cursor.getColumnIndex(CalendarContract.Events.TITLE);
                int id_3 = cursor.getColumnIndex(CalendarContract.Events.DESCRIPTION);
                int id_4 = cursor.getColumnIndex(CalendarContract.Events.EVENT_LOCATION);

                String idValue = cursor.getColumnName(id_1);
                String titleValue = cursor.getString(id_2);
                String descriptionValue = cursor.getString(id_3);
                String eventValue = cursor.getString(id_4);
            }

// this is a method for selecting a calendar and eventually modifying its display name
// Projection array. Creating indices for this array instead of doing
// dynamic lookups improves performance.
            final String[] EVENT_PROJECTION = new String[]{
                    CalendarContract.Calendars._ID,                           // 0
                    CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
                    CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
                    CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
            };

// The indices for the projection array above.
            final int PROJECTION_ID_INDEX = 0;
            final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
            final int PROJECTION_DISPLAY_NAME_INDEX = 2;
            final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
// Run query
            Cursor cur = null;
            ContentResolver cr = getContentResolver();
            Uri uri = CalendarContract.Calendars.CONTENT_URI;
            String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                    + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                    + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
            String[] selectionArgs = new String[]{"hera@example.com", "com.example",
                    "hera@example.com"};
// Submit the query and get a Cursor object back.
            cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
// Use the cursor to step through the returned records
            while (cur.moveToNext()) {
                long calID = 0;
                String displayName = null;
                String accountName = null;
                String ownerName = null;

                // Get the field values
                calID = cur.getLong(PROJECTION_ID_INDEX);
                displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
                accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
                ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);
                // Do something with the values...
                private static final String DEBUG_TAG = "MyActivity";
                long calID = 2;
                ContentValues values = new ContentValues();
// The new display name for the calendar
                values.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, "Trevor's Calendar");
                Uri updateUri = ContentUris.withAppendedId(CalendarContract.Calendars.CONTENT_URI, calID);
                int rows = getContentResolver().update(updateUri, values, null, null);
                Log.i(DEBUG_TAG, "Rows updated: " + rows);


            }

            /****************************************
             * OnCheckChanged Listener for Indica/Sativa Ratio - this sets mFloweringDays
             ****************************************/

            // This changes the value of mFloweringDays every time a button in the group is selected.
            public void indicaSativaRatio (String mFloweringDays){


                radioIndicaSativaOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        // Get Index Of Radio Button
                        int pos = radioIndicaSativaOptions.indexOfChild(findViewById(radioIndicaSativaOptions.getCheckedRadioButtonId()));

                        switch (pos) {
                            case 0:
                                // 100% Indica
                                mFloweringDays = 56;
                                break;
                            case 1:
                                // 100% Sativa
                                mFloweringDays = 84;
                                break;
                            case 2:
                            case 9:
                                // Unknown Genetics
                                // 50/50 Indica Sativa
                                mFloweringDays = 70;
                                break;
                            case 3:
                                // 60/40 Indica Sativa
                                mFloweringDays = 63;
                                break;
                            case 4:
                            case 6:
                                // 30/70 Indica Sativa
                                // 40/60 Indica Sativa
                                mFloweringDays = 77;
                                break;
                            case 5:
                            case 8:
                                // Mostly Indica
                                // 70/30 Indica Sativa
                                mFloweringDays = 60;
                                break;
                            case 7:
                                // Mostly Sativa
                                mFloweringDays = 80;
                                break;
                        }

                        Toast.makeText(GrowAssistantActivity.this, "Flowering Days = " + mFloweringDays,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
    }
    // On pressing the back button
    @Override
    public void onBackPressed () {
        super.onBackPressed();
    }



    @Override
    public void onDateSet (DatePicker view,int year, int month, int dayOfMonth){

    }
}
