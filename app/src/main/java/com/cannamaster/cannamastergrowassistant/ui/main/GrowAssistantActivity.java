package com.cannamaster.cannamastergrowassistant.ui.main;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.icu.util.TimeZone;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.cannamaster.cannamastergrowassistant.R;
import com.cannamaster.cannamastergrowassistant.ui.main.dialogs.DatePickerFragment;
import com.cannamaster.cannamastergrowassistant.ui.main.dialogs.TimePickerFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class GrowAssistantActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText dateText;
    EditText timeText;
    EditText titleText;
    EditText reminderText;
    Button runButton;

    GregorianCalendar calDate = new GregorianCalendar();

    String calID;

    String mTitle;
    Boolean outdoorSelected = false;
    Boolean hydroSelected = false;
    Boolean doNotSetWateringEvents;
    Boolean soilSelected = true;

    // Layout components for time and date picker
    ImageView mDateDialog;
    ImageView mTimeDialog;

    // Initialize the RadioGroups
    RadioGroup rgWateringSchedule;
    RadioGroup rgFertilizerChoice;
    RadioGroup rgGrowMediumChoice;
    RadioGroup rgIndicaSativaOptions;

    // Flowering Days Variable
    int mFloweringDays = 70;
    // Watering Notification Variable
    int mWateringScheduleDays = 0;
    int checkedId;

    RadioButton radioButton, radioButtonWater2Days, radioButtonWater3Days, radioButtonWaterWeekly, radioButtonWaterNoNotify, radioButtonWaterDaily;


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

        mDateDialog = (ImageView) findViewById(R.id.date_icon);
        mTimeDialog = (ImageView) findViewById(R.id.time_icon);
        dateText = (EditText) findViewById(R.id.date_edit_text);
        timeText = (EditText) findViewById(R.id.time_edit_text);
        titleText = (EditText) findViewById(R.id.titleText);
        reminderText = (EditText) findViewById(R.id.reminderText);
        runButton = (Button) findViewById(R.id.button_set_notifications);


        rgIndicaSativaOptions = findViewById(R.id.rgIndicaSativa);
        rgWateringSchedule = findViewById(R.id.rgWaterOptions);
        rgFertilizerChoice = findViewById(R.id.rgFertilizerOptions);
        rgGrowMediumChoice = findViewById(R.id.rgGrowMediumChoice);

        // OnClick Listener For The Date Picker Dialog
        mDateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment(GrowAssistantActivity.this);
                datePickerFragment.show(getSupportFragmentManager(), "datepicker");
            }
        });

        // OnClick Listener For The Time Picker Dialog
        mTimeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment = new TimePickerFragment(GrowAssistantActivity.this);
                timePickerFragment.show(getSupportFragmentManager(), "timepicker");
            }
        });

        /** This is how we request the user for Calendar write and read permissions **/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // Ask the user for permission and then re-run
            Log.i("No Permission", "No Permission");
            int requestCode = 1;
            // Re-request to ask for permission
            requestPermissions(new String[]{"android.permission.WRITE_CALENDAR", "android.permission.READ_CALENDAR"}, requestCode);
        }
    }


    /** This sets the back arrow on the toolbar to work **/
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

    private void getCalendarId() {
        // get basic info from the users calendars
        String[] projection =
                new String[]{
                        CalendarContract.Calendars._ID,
                        CalendarContract.Calendars.NAME,
                        CalendarContract.Calendars.ACCOUNT_NAME,
                        CalendarContract.Calendars.ACCOUNT_TYPE};

        // check for permissions before reading the local calendar
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // Ask the user for permission and then re-run
            Log.i("No Permission", "No Permission");
            Toast.makeText(this, "Failed to give permission, try again", Toast.LENGTH_SHORT).show();
            int requestCode = 1;
            requestPermissions(new String[]{"android.permission.WRITE_CALENDAR", "android.permission.READ_CALENDAR"}, requestCode);
            return;
        }
        Cursor calCursor = getContentResolver().query(CalendarContract.Calendars.CONTENT_URI,
                projection, CalendarContract.Calendars.VISIBLE + " = 1", null,
                CalendarContract.Calendars._ID + " ASC");
        if (calCursor.moveToFirst()) {
            do {
                long id = calCursor.getLong(0);
                calID = Long.toString(id);
                String displayName = calCursor.getString(1);
                // ...
            } while (calCursor.moveToNext());
        }
        Toast.makeText(this, "Calendar ID = " + calID, Toast.LENGTH_SHORT).show();
    }

    private void CreateNewCalendar() {
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Calendars.ACCOUNT_NAME, "CannaMaster Grow Assistant");
        values.put(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
        values.put(CalendarContract.Calendars.NAME, "Grow Assistant");
        values.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, "Grow Assistant");
        values.put( CalendarContract.Calendars.CALENDAR_COLOR, 0xffff0000);
        values.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        values.put(CalendarContract.Calendars.OWNER_ACCOUNT, "owner");
        values.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, String.valueOf(TimeZone.UNKNOWN_ZONE_ID));
        values.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
        Uri.Builder builder = CalendarContract.Calendars.CONTENT_URI.buildUpon();
        builder.appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, "Grow Assistant");
        builder.appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
        builder.appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true");
        Uri uri = getContentResolver().insert(builder.build(), values);


    }

    /** Method to add the grow to the users on device calendar **/
    private void addToDeviceCalendar(String startDate,String endDate, String title,String description, int reminder) {

        // Make the strings for the formatted times
        String startDateFormatted = "";
        String endDateFormatted = "";

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
        // bad way of making ending numbers work
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


            // Puts the values into an array for the calendar
            ContentResolver cr = this.getContentResolver();
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Calendars.OWNER_ACCOUNT, "owner");
            values.put(CalendarContract.Calendars.ACCOUNT_NAME, "CannaMaster");
            values.put(CalendarContract.Calendars.ACCOUNT_TYPE, "local");
            values.put(CalendarContract.Events.DTSTART, startMillis);
            values.put(CalendarContract.Events.DTEND, endMillis);
            values.put(CalendarContract.Events.TITLE, title);
            values.put(CalendarContract.Events.DESCRIPTION, description);
            values.put(CalendarContract.Events.HAS_ALARM, 1);
            values.put(CalendarContract.Events.CALENDAR_ID, calID);
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
            assert uri != null;
            long eventId = Long.parseLong(Objects.requireNonNull(uri.getLastPathSegment()));
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

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**  This takes the data from the TextViews and turns it into events to pass to calendar **/
    public void onMakeEvent (View view) throws java.text.ParseException {
        // get the user inputed title from the layout and put it into a string
        CreateNewCalendar();
        getCalendarId();
        // This function sets all the events based on the users selctions and input
        // This checks the edit text fields to ensure there is some kind of data entered
        if (titleText.getText().length() < 1) {
            titleText.setError("You must provide a title for the event");
            runButton.setError("Error : Check your information above");}
        else if (dateText.getText().length() < 10) {
            dateText.setError("please provide a valid start date 'YYYY-MM-DD'");
            runButton.setError("Error : Check your information above");}
        else if (dateText.getText().length() > 10) {
            dateText.setError("please provide a valid start date 'YYYY-MM-DD'");
            runButton.setError("Error : Check your information above");}
        else if (timeText.getText().length() < 5) {
            timeText.setError("please provide a valid time for events 'hh:mm'");
            runButton.setError("Error : Check your information above");; }
        else if (timeText.getText().length() > 5) {
            timeText.setError("please provide a valid time for events 'hh:mm'");
            runButton.setError("Error : Check your information above");}
        else if (reminderText.getText().length() < 1) {
            reminderText.setError("please provide a valid buffer to receive notifications");
            runButton.setError("Error : Check your information above");}
        else if (outdoorSelected = true) {
            // runs if outdoor is selected as grow medium of choice
            SetOutdoorReminders();
            // Tell the user that the information has been set
            Toast.makeText(this, "Check Google Calendar for your new events, you may need to refresh it.", Toast.LENGTH_LONG).show();
        }
        else if (hydroSelected = true) {
            // runs if hydro is selected as grow medium of choice
            SetFloweringStartDate();
            SetFlushReminder();
            SetHarvestDateReminder();

            // Tell the user that the information has been set
            Toast.makeText(this, "Check Google Calendar for your new events, you may need to refresh it.", Toast.LENGTH_LONG).show();
        }
        else {
            // runs if indoor soil is selected as grow medium of choice


            // as long as the EditTexts are filled out run the following code
            SetFloweringStartDate();
            SetFlushReminder();
            SetHarvestDateReminder();
            SetWateringCalendarDates();
            SetFertilizerReminders();

            // Tell the user that the information has been set
            Toast.makeText(this, "Check Google Calendar for your new events, you may need to refresh it.", Toast.LENGTH_LONG).show();

            // This closes the grow assistant activity
            finish();
        }
    }

    /** **************************************************************************************************
     *                         ---- OUTDOOR GROW in one huge function ----
     *
     * This sets the outdoor reminders separate from other defined methods
     * It is quite long because if I were to call the other methods it would end up double setting events
     * ***************************************************************************************************/
    private void SetOutdoorReminders() throws java.text.ParseException {
        //
        // check to make sure outdoor is selected
        if (outdoorSelected = true) {
            String growTitle = titleText.getText().toString();
            // Turn the first 4 digits of date entry into the year then add a preselected flower date
            dateText = (EditText) findViewById(R.id.date_edit_text);
            CharSequence firstFour = dateText.getText();
            String yearDate = firstFour.toString();
            String fourDigitDate = yearDate.substring(0, 4);
            String mDate = fourDigitDate + "-07-30";
            // turn the strings into values the app can actually use
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateFormat.parse(mDate));
            String startTime = timeText.getText().toString();
            // takes the 2 EditText fields and combines them to set the date and time of notification
            String startDate = mDate + " " + startTime;
            /** clone the start date for some functions coming up **/
            Calendar calHarvestDate = cal;
            Calendar calFlushDate = cal;
            Calendar calWater = cal;
            Calendar calFertilizer = cal;

            // sets the end date the same as the start date so the rest of the hard code still works
            String endDate = startDate;
            // this is the event title
            String titleString = "First day of outdoor flowering";
            String descString = growTitle + "\n\nThis is the first day of the outdoor flowering cycle.\n\nThe days are getting shorter and your girls will start the flowering process any day now.  This is a good date to use as a reference point for your respective timelines.\n\n";
            // this is the time before the event that the reminder will fire
            String reminderString = reminderText.getText().toString();
            int reminderInt = Integer.parseInt(reminderString);
            // Calls the function to add the event to the calender
            addToDeviceCalendar(startDate, endDate, titleString, descString, reminderInt);
            // Notify the user that the first day of flower has been set
            Toast.makeText(GrowAssistantActivity.this, "First day of outdoor flowering set to " + mDate,
                    Toast.LENGTH_SHORT).show();

            /** This will set the flush notification 2 weeks before harvest **/
            calFlushDate.add(Calendar.DATE, mFloweringDays - 14);
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String convertedDate = dateFormat.format(calFlushDate.getTime());
            startDate = convertedDate + " " + startTime;
            endDate = startDate;
            titleString = "Time To Start Flushing";
            descString = growTitle + "\n\nFrom now until harvest use only water to quench your plants.  This will flush out any undissolved salts left over from the fertilizer you may have used.  This will noticeably improve the taste of your product once harvested and cured.";
            addToDeviceCalendar(startDate, endDate, titleString, descString, reminderInt);
            // Inform the user that the flush notification has been set
            Toast.makeText(GrowAssistantActivity.this, "Flush notification event has been set for " + convertedDate,
                    Toast.LENGTH_SHORT).show();

            /** set the fertilizer notification events **/
            int repeatNumber = (mFloweringDays / 14);
            while (repeatNumber > 2) {
                calFertilizer.add(Calendar.DATE, 14);
                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                convertedDate = dateFormat.format(calFertilizer.getTime());
                startDate = convertedDate + " " + startTime;
                endDate = startDate;
                titleString = "Feed Your Girls";
                descString = growTitle + "\n\nNow would be a good time to supplement your girls with some fertilizer.  You can use chemical based fertilizer or organic fertilizer.  There are pros and cons to each choice, the decision is ultimately up to you.\n\nBe careful not to over fertilize.\n\nYou can certainly have too much of a good thing.  Overuse of fertilizers can result in poor plant growth and production.  Be sure to follow all warnings labels and mix your fertilizers consistent with manufactures label and recommendations.";
                addToDeviceCalendar(startDate, endDate, titleString, descString, reminderInt);
                repeatNumber--;
                Toast.makeText(GrowAssistantActivity.this, "Feeding date set for " + convertedDate,
                        Toast.LENGTH_SHORT).show();
            }

            /** set the outdoor harvest date **/
            calHarvestDate.add(Calendar.DATE, mFloweringDays);
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            convertedDate = dateFormat.format(calHarvestDate.getTime());
            startDate = convertedDate + " " + startTime;
            endDate = startDate;
            titleString = "Harvest Day Is Here!";
            descString = growTitle + "\n\nIts taken a lot of work to get here but the time is finally upon you.\n\nIt's time to cut down your girls and start the drying/curing process.\n\nYour end product will taste and cure better if you hang the plants whole to dry.  Don't cut them into a bunch of pieces to hang and dry, instead hang the entire plant as one or cut it into as few pieces as possible";
            addToDeviceCalendar(startDate, endDate, titleString, descString, reminderInt);
            // Inform the user that the flush notification has been set
            Toast.makeText(GrowAssistantActivity.this, "Harvest date set for " + convertedDate,
                    Toast.LENGTH_SHORT).show();

            /** Outdoor Watering Notifications **/

            mDate = fourDigitDate + "-07-30";

            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            cal = Calendar.getInstance();
            cal.setTime(dateFormat.parse(mDate));

            repeatNumber = (mFloweringDays / mWateringScheduleDays);
            while (repeatNumber > 1) {
                // while statement to set fertilizer notifications based on user input
                cal.add(Calendar.DATE, mWateringScheduleDays);
                convertedDate = dateFormat.format(cal.getTime());
                System.out.println("Watering reminder set on " + convertedDate);
                startTime = timeText.getText().toString();
                // takes the 2 EditText fields and combines them to set the date and time of notification
                startDate = convertedDate + " " + startTime;
                // sets the end date the same as the start date so the rest of the hard code still works
                endDate = startDate;
                // this is the event title
                titleString = "Have you watered your girls lately?";
                // this is the event description
                descString = mTitle + "\n\nDon't forget to water your girls.\n\nThey can get quite thirsty while flowering.\n\nThe soil should be dry to the touch as well as dry 2'' below the surface before you water again";
                // this is the time before the event that the reminder will fire
                reminderString = reminderText.getText().toString();
                reminderInt = Integer.parseInt(reminderString);
                // Calls the function to add the event to the calender
                addToDeviceCalendar(startDate, endDate, titleString, descString, reminderInt);
                repeatNumber--;

                Toast.makeText(GrowAssistantActivity.this, "Setting Water notification for " + mDate,
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        }
    }


    /** This sets the indoor fertilization schedule **/
    private void SetFertilizerReminders() throws java.text.ParseException {
        // grab the date from the user input TextEdit
        String mDate = dateText.getText().toString();
        // Turn the date into something the app can actually use
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFormat.parse(mDate));
        // This figures out how many times you should add fertilizer
        int repeatNumber = (mFloweringDays / 14);
        // This repeats the fertilization event as many times as it is needed
        while (repeatNumber > 2) {
            // while statement to set fertilizer notifications based on user input
            cal.add(Calendar.DATE, 14);
            dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
            String convertedDate = dateFormat.format(cal.getTime());
            String startTime = timeText.getText().toString();
            // takes the 2 EditText fields and combines them to set the date and time of notification
            String startDate = convertedDate + " " + startTime;
            // sets the end date the same as the start date so the rest of the hard code still works
            String endDate = startDate;
            // this is the event title
            String titleString = "Have you fed your girls lately?";
            // this is the event description
            String descString = mTitle + "\n\nDon't forget to feed your girls.\n\nThey can get quite hungry while flowering.\n\nFlowering takes a lot of (P)hosphurous make sure whatever fertilizer you are using is high in P.";
            // this is the time before the event that the reminder will fire
            String reminderString = reminderText.getText().toString();
            int reminderInt = Integer.parseInt(reminderString);
            // Calls the function to add the event to the calender
            addToDeviceCalendar(startDate, endDate, titleString, descString, reminderInt);
            repeatNumber--;

            Toast.makeText(GrowAssistantActivity.this, "Setting Fertilizer notification for " + mDate,
                    Toast.LENGTH_SHORT).show();
        }
    }

    /** Sets the indoor watering notifications based on what is selected **/
    public void SetWateringCalendarDates() throws java.text.ParseException {
        // Converts the EditTexts to Strings

        String mDate = dateText.getText().toString();

        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFormat.parse(mDate));

        int repeatNumber = (mFloweringDays / mWateringScheduleDays);
        while (repeatNumber > 1) {
            // while statement to set fertilizer notifications based on user input
            cal.add(Calendar.DATE, mWateringScheduleDays);
            String convertedDate = dateFormat.format(cal.getTime());
            System.out.println("Watering reminder set on " + convertedDate);
            String startTime = timeText.getText().toString();
            // takes the 2 EditText fields and combines them to set the date and time of notification
            String startDate = convertedDate + " " + startTime;
            // sets the end date the same as the start date so the rest of the hard code still works
            String endDate = startDate;
            // this is the event title
            String titleString = "Have you watered your girls lately?";
            // this is the event description
            String descString = mTitle + "\n\nDon't forget to water your girls.\n\nThey can get quite thirsty while flowering.\n\nThe soil should be dry to the touch as well as dry 2'' below the surface before you water again";
            // this is the time before the event that the reminder will fire
            String reminderString = reminderText.getText().toString();
            int reminderInt = Integer.parseInt(reminderString);
            // Calls the function to add the event to the calender
            addToDeviceCalendar(startDate, endDate, titleString, descString, reminderInt);
            repeatNumber--;

            Toast.makeText(GrowAssistantActivity.this, "Setting Water notification for " + mDate,
                    Toast.LENGTH_SHORT).show();
        }
    }

    /** Sets the first day of indoor flowering based on user input **/
    public void SetFloweringStartDate() {
        // Converts the EditTexts to Strings
        String growTitle = titleText.getText().toString();
        String mDate = dateText.getText().toString();
        String startTime = timeText.getText().toString();
        // takes the 2 EditText fields and combines them to set the date and time of notification
        String startDate = mDate + " " + startTime;
        // sets the end date the same as the start date so the rest of the hard code still works
        String endDate = startDate;
        // this is the event title
        String titleString = "First day of flowering";
        // this is the event description
        String descString = growTitle + "\n\nThis is the first day of the flowering cycle.\n\nPlants should be placed into a 12/12 light cycle.  Be sure to check for any light leaks in your grow room as they could lead to hermaphrodite plants which in turn could produce seeds.\n\n";
        // this is the time before the event that the reminder will fire
        String reminderString = reminderText.getText().toString();
        int reminderInt = Integer.parseInt(reminderString);
        // Calls the function to add the event to the calender
        addToDeviceCalendar(startDate, endDate, titleString, descString, reminderInt);
        // Notify the user that the first day of flower has been set
        Toast.makeText(GrowAssistantActivity.this, "First day of flowering set to " + mDate,
                Toast.LENGTH_SHORT).show();
    }

    /** This is the  indoor flush reminder that springs 2 weeks before harvest **/
    public void SetFlushReminder() throws java.text.ParseException {
        // Converts the EditTexts to Strings
        String mDate = dateText.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        Calendar cal = Calendar.getInstance();
        cal.setTime( dateFormat.parse(mDate));
        cal.add( Calendar.DATE, mFloweringDays -14);
        String convertedDate = dateFormat.format(cal.getTime());

        String startTime = timeText.getText().toString();
        // takes the 2 EditText fields and combines them to set the date and time of notification
        String startDate = convertedDate + " " + startTime;
        // sets the end date the same as the start date so the rest of the hard code still works
        String endDate = startDate;
        // this is the event title
        String titleString = "Time To Start Flushing";
        // this is the event description
        String descString = "From now until harvest use only water to quench your plants.\n \n  No fertilizer or supplements.\n \n  This will wash out any undissolved fertilizer salts in your grow medium which in turn will make your final product taste better.";
        // this is the time before the event that the reminder will fire
        String reminderString = reminderText.getText().toString();
        int reminderInt = Integer.parseInt(reminderString);
        // Calls the function to add the event to the calender
        addToDeviceCalendar(startDate, endDate, titleString, descString, reminderInt);
        // Inform the user that the flush notification has been set
        Toast.makeText(GrowAssistantActivity.this, "Flush Notificiation Has Been Set",
                Toast.LENGTH_SHORT).show();
    }

    /** Set Harvest Date **/
    public void SetHarvestDateReminder() throws java.text.ParseException {

        String mDate = dateText.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFormat.parse(mDate));
        cal.add(Calendar.DATE, mFloweringDays);
        String convertedDate = dateFormat.format(cal.getTime());

        String startTime = timeText.getText().toString();
        // takes the 2 EditText fields and combines them to set the date and time of notification
        String startDate = convertedDate + " " + startTime;
        // sets the end date the same as the start date so the rest of the hard code still works
        String endDate = startDate;
        // this is the event title
        String titleString = "Time To Harvest!";
        // this is the event description
        String descString = "All your hard work has finally paid off.  \n \n " + titleText + " is finally done. \n \n  Today is the day you cut down your girls and start the journey towards enjoying all that hard earned work.";
        // this is the time before the event that the reminder will fire
        String reminderString = reminderText.getText().toString();
        int reminderInt = Integer.parseInt(reminderString);
        // Calls the function to add the event to the calender
        addToDeviceCalendar(startDate, endDate, titleString, descString, reminderInt);
        // inform the user that the harvest date has been set
        Toast.makeText(GrowAssistantActivity.this, "Harvest date set for " + convertedDate ,
                Toast.LENGTH_SHORT).show();
    }

    public void GrowMedium(View view) {
        checkedId = rgGrowMediumChoice.getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.radioSoil:
                soilSelected = true;
                outdoorSelected = false;
                hydroSelected = false;
                break;
            case R.id.radioOutdoor:
                soilSelected = false;
                outdoorSelected = true;
                hydroSelected = false;
                break;
            case R.id.radioHydro:
                soilSelected = false;
                hydroSelected = true;
                outdoorSelected = false;
                break;
        }
    }

    /*******************************************************************************
     * What Happens When You Click Something in Indica/Sativa Ratio - (it sets mFloweringDays)
     *******************************************************************************/
    // This changes the value of mFloweringDays every time a button in the group is selected.
    public void IndicaSativaRatio(View view) {
        // Grab the RadioGroup and check to make sure there haven't been any changes.  If there has act accordingly.
        checkedId = rgIndicaSativaOptions.getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.radioButtonIndicaSativa1000:
                // 100% Indica
                mFloweringDays = 56;
                break;
            case R.id.radioButtonIndicaSativa0100:
                // 100% Sativa
                mFloweringDays = 84;
                break;
            case R.id.radioButtonIndicaSativa5050:
                // 50/50 Indica Sativa
                mFloweringDays = 70;
                break;
            case R.id.radioButtonIndicaSativa6040:
                // 60/40 Indica Sativa
                mFloweringDays = 63;
                break;
            case R.id.radioButtonIndicaSativa4060:
                // 40/60 Indica Sativa
                mFloweringDays = 77;
                break;
            case R.id.radioButtonIndicaSativa7030:
                // 70/30 Indica Sativa
                mFloweringDays = 60;
                break;
            case R.id.radioButtonIndicaSativa3070:
                // 30/70 Indica Sativa
                mFloweringDays = 77;
                break;
            case R.id.radioButtonIndicaSativa9010:
                // Mostly Sativa
                mFloweringDays = 80;
                break;
            case R.id.radioButtonIndicaSativa1090:
                // Mostly Indica
                mFloweringDays = 60;
                break;
            case R.id.radioButtonIndicaSativaUnknown:
                // Unknown Genetics
                mFloweringDays = 70;
                break;
        }
        // informs the user how many flowering days their choice has set
        Toast.makeText(GrowAssistantActivity.this, "Flowering Days = " + mFloweringDays,
                Toast.LENGTH_SHORT).show();
    }


    /**************************************************************************************
     *  This is what happens when watering options are selected
     **************************************************************************************/
    public void WaterScheduleRatio(View view) {
        checkedId = rgWateringSchedule.getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.radioButtonWaterDaily:
                // Every Day
                mWateringScheduleDays = 1;
                break;
            case R.id.radioButtonWater2Days:
                // Every 2 Days
                mWateringScheduleDays = 2;
                break;
            case R.id.radioButtonWater3Days:
                // Every 3 Days
                mWateringScheduleDays = 3;
                break;
            case R.id.radioButtonWaterWeekly:
                // Once A week
                mWateringScheduleDays = 7;
                break;
            case R.id.radioButtonWaterNoNotify:
                // do not set notification
                mWateringScheduleDays = 0;
                break;
        }
    }




    /** Date Picker Dialog Rules **/
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // This ensures that the date is populated correctly
        if (month <10 && dayOfMonth <10) {
            dateText.setText(year + "-0" + month + "-0" + dayOfMonth);
        }
        else if (month <10) {
            dateText.setText(year + "-0" + month + "-" + dayOfMonth);
        }
        else if (dayOfMonth <10) {
            dateText.setText(year + "-" + month + "-0" + dayOfMonth);
        }
        else if (month >9 && dayOfMonth >9) {
            dateText.setText(year + "-" + month + "-" + dayOfMonth);
        }
    }

    /** Time Picker Dialog Rules  **/
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // This ensures that the time is populated correctly
        if (hourOfDay <10 && minute <10) {
            timeText.setText("0" + hourOfDay + ":0" + minute);
        }
        else if (hourOfDay <10) {
            timeText.setText("0" + hourOfDay + ":" + minute);
        }
        else if (minute <10) {
            timeText.setText(hourOfDay + ":0" + minute);
        }
        else if (hourOfDay >9 && minute >9)
            timeText.setText(hourOfDay + ":" + minute);
    }
}
