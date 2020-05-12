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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;


public class GrowAssistantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grow_assistant_activity_layout;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Set the back arrow
        Objects.requireNonNull(getSupportActionBar()).setTitle("Grow Assistant");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // find some layout features that will become important once the user starts interacting with them
        ImageView ivCal = findViewById(R.id.date_icon);
        ivCal.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {

                                     //    todo : code to open the date picker

                                     }
                                 });

        // This is how we request the user for Calendar write and read permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // Ask the user for permission and then re-run
            Log.i("No Permission", "No Permission");
            int requestCode = 1;
            // Re-request to ask for permission
            requestPermissions(new String[]{"android.permission.WRITE_CALENDAR", "android.permission.READ_CALENDAR"}, requestCode);
        }
    }

    private void addToDeviceCalendar(String startDate,String endDate, String title,String description, String location, int reminder) {

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
        }
        catch (ParseException ex) {} catch (java.text.ParseException e) {
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
        calDate.set(startYear, startMonth-1, startDay, startHour, startMinute);
        long startMillis = calDate.getTimeInMillis();

        // Sets the date and gets the time in milliseconds
        calDate.set(endYear, endMonth-1, endDay, endHour, endMinute);
        long endMillis = calDate.getTimeInMillis();

        try {
            /** Puts the values into an event for the calendar  **/
            ContentResolver cr = this.getContentResolver();
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.DTSTART, startMillis);
            values.put(CalendarContract.Events.DTEND, endMillis);
            values.put(CalendarContract.Events.TITLE, title);
            values.put(CalendarContract.Events.DESCRIPTION, description);
            values.put(CalendarContract.Events.EVENT_LOCATION,location);
            values.put(CalendarContract.Events.HAS_ALARM,1);
            values.put(CalendarContract.Events.CALENDAR_ID, 1);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, android.icu.util.Calendar.getInstance()
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
    public void onMakeEvent (View view) {
        // Get the EditText views by ID
        EditText startText = (EditText) findViewById(R.id.startText);
        EditText endText = (EditText) findViewById(R.id.endText);
        EditText titleText = (EditText) findViewById(R.id.titleText);
        EditText descText = (EditText) findViewById(R.id.descText);
        EditText locText = (EditText) findViewById(R.id.locText);
        EditText reminderText = (EditText) findViewById(R.id.reminderText);
        // Converts the EditTexts to Strings
        String startDate = startText.getText().toString();
        String endDate = endText.getText().toString();
        String titleString = titleText.getText().toString();
        String descString = descText.getText().toString();
        String locString = locText.getText().toString();
        String reminderString = reminderText.getText().toString();
        int reminderInt = Integer.parseInt(reminderString);
        // Calls the function to add the event to the calender
        addToDeviceCalendar(startDate, endDate, titleString, descString, locString, reminderInt);
    }





    /****************************************
     * OnCheckChanged Listener for Indica/Sativa Ratio - it sets mFloweringDays
     ****************************************/

    /** This changes the value of mFloweringDays every time a button in the group is selected.
     public void indicaSativaRatio(View view) {

     rgIndicaSativaOptions = (RadioGroup) findViewById(R.id.rgIndicaSativa);
     rgIndicaSativaOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
    @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
    // Get Index Of Radio Button
    int pos = rgIndicaSativaOptions.indexOfChild(findViewById(rgIndicaSativaOptions.getCheckedRadioButtonId()));

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
    // 50/50 Indica Sativa
    mFloweringDays = 70;
    break;
    case 3:
    // 60/40 Indica Sativa
    mFloweringDays = 63;
    break;
    case 4:
    // 40/60 Indica Sativa
    mFloweringDays = 77;
    break;
    case 5:
    // 70/30 Indica Sativa
    mFloweringDays = 60;
    break;
    case 6:
    // 30/70 Indica Sativa
    mFloweringDays = 77;
    break;
    case 7:
    // Mostly Sativa
    mFloweringDays = 80;
    break;
    case 8:
    // Mostly Indica
    mFloweringDays = 60;
    break;
    case 9:
    // Unknown Genetics
    mFloweringDays = 70;
    break;
    }

    Toast.makeText(GrowAssistantActivity.this, "Flowering Days = " + mFloweringDays,
    Toast.LENGTH_SHORT).show();
    }
    });
     }

     /****************************************
     * Flush Reminder Function
     ****************************************/
    /**
     // FLUSH - On clicking the set notifications button
     public void SetNotificationsButtonFlush(View v){
     ReminderDatabase rb = new ReminderDatabase(this);
     // Check if Indoor Or Outdoor
     mFlushCalendar = (Calendar)mCalendar.clone();
     if (mIsOutdoor == "true") {
     mFlushCalendar.set(Calendar.DAY_OF_YEAR, 275);
     }
     else {
     mFlush2WeeksBefore = mFloweringDays - 14;
     mFlushCalendar.add(Calendar.DAY_OF_YEAR, (mFlush2WeeksBefore));
     }
     mTitle = "No more nutes! Start to flush out excess fertilizer by using only " +
     "water to finish out your girls.";
     mYear = mFlushCalendar.get(Calendar.YEAR);
     mMonth = mFlushCalendar.get(Calendar.MONTH) + 1;
     mDay = mFlushCalendar.get(Calendar.DATE);
     mDate = mMonth + "/" + mDay + "/" + mYear;
     mRepeatTime = (3 * milDay);
     mRepeat = "true";
     mRepeatType = "Last 2 Weeks";
     // Creating reminder
     int _id = rb.addReminder(new Reminder(mTitle, mDate, mTime, mRepeat, "1", mRepeatType, mActive));
     new AlarmReceiver().setRepeatAlarm(getApplicationContext(), mFlushCalendar, _id,
     mRepeatTime);
     Toast.makeText(GrowAssistantActivity.this, "Flush Reminder " + mDate,
     Toast.LENGTH_SHORT).show();
     }

     /****************************************
     * Watering Reminder Function
     ****************************************/
// WATERING REMINDERS - set's its own repeat
    /**  public void SetNotificationsButtonWater(View v){
     ReminderDatabase rb = new ReminderDatabase(this);
     mYear = mWaterCalendar.get(Calendar.YEAR);
     mMonth = mWaterCalendar.get(Calendar.MONTH) + 1;
     mDay = mWaterCalendar.get(Calendar.DATE);
     mDate = mMonth + "/" + mDay + "/" + mYear;
     mRepeat = "true";
     // **** mRepeatTime must be declared from the radio button callout ****
     // Creating Reminder
     int _id = rb.addReminder(new Reminder(mTitle, mDate, mTime, mRepeat, "1", mRepeatType, mActive));
     new AlarmReceiver().setRepeatAlarm(getApplicationContext(), mWaterCalendar, _id, mRepeatTime);
     Toast.makeText(GrowAssistantActivity.this, "Watering Reminder Set " + mDate,
     Toast.LENGTH_SHORT).show();
     }

     /****************************************
     * Fertilizer Reminder Function
     ****************************************/
// FERTILIZE - On clicking the set notifications button
    /** public void SetNotificationsButtonFert(View v){
     ReminderDatabase rb = new ReminderDatabase(this);
     mFertilizeCalendar = (Calendar)mCalendar.clone();
     mFertilizeCalendar.add(Calendar.DAY_OF_YEAR, 1);
     mYear = mFertilizeCalendar.get(Calendar.YEAR);
     mMonth = mFertilizeCalendar.get(Calendar.MONTH) + 1;
     mDay = mFertilizeCalendar.get(Calendar.DATE);
     mDate = mMonth + "/" + mDay + "/" + mYear;
     mRepeat = "true";
     mRepeatType = "Repeats Bi-Weekly";
     mRepeatTime = mil2weeks;
     // Creating Reminder
     int _id = rb.addReminder(new Reminder(mTitle, mDate, mTime, mRepeat, "1", mRepeatType, mActive));
     // Create a new notification
     new AlarmReceiver().setRepeatAlarm(getApplicationContext(), mFertilizeCalendar, _id, mRepeatTime);
     Toast.makeText(GrowAssistantActivity.this, "Fert Date Set " + mDate, Toast.LENGTH_SHORT).show();
     }

     /****************************************
     * Outdoor Harvest Reminder Function
     ****************************************/
    /**  public void SetNotificationsButtonOutdoorHarvestDay(View v){
     ReminderDatabase rb = new ReminderDatabase(this);
     // Reset Time for this notification
     mTitle = "Outdoor Harvest Day!  Time to take down the girls. - outdoor";
     mOutdoorHarvestCalendar = (Calendar)mCalendar.clone();
     mOutdoorHarvestCalendar.set(Calendar.DAY_OF_YEAR, 290);
     mHour = mOutdoorHarvestCalendar.get(Calendar.HOUR_OF_DAY);
     mMinute = mOutdoorHarvestCalendar.get(Calendar.MINUTE);
     mYear = mOutdoorHarvestCalendar.get(Calendar.YEAR);
     mMonth = mOutdoorHarvestCalendar.get(Calendar.MONTH) + 1;
     mDay = mOutdoorHarvestCalendar.get(Calendar.DATE);
     mDate = mMonth + "/" + mDay + "/" + mYear;
     mRepeat = "false";
     mRepeatType = "Does Not Repeat";
     // Change State to Outdoor
     mIsOutdoor = "true";
     // Creating Reminder
     int _id = rb.addReminder(new Reminder(mTitle, mDate, mTime, mRepeat, "1", mRepeatType, mActive));
     // Create a new notification
     new AlarmReceiver().setAlarm(getApplicationContext(), mOutdoorHarvestCalendar, _id);
     Toast.makeText(GrowAssistantActivity.this, "Outdoor Harvest Date Set " + mDate,
     Toast.LENGTH_SHORT).show();
     }

     /****************************************
     * Indoor Harvest Reminder Function
     ****************************************/
    /** public void SetNotificationsButtonIndoorHarvestDay(View v){
     ReminderDatabase rb = new ReminderDatabase(this);
     mIndoorHarvestCalendar = (Calendar)mCalendar.clone();
     mIndoorHarvestCalendar.add(Calendar.DAY_OF_YEAR, mFloweringDays);
     mTitle = "Harvest Day!  Time to take down the girls. - indoor";
     mHour = mIndoorHarvestCalendar.get(Calendar.HOUR_OF_DAY);
     mMinute = mIndoorHarvestCalendar.get(Calendar.MINUTE);
     mYear = mIndoorHarvestCalendar.get(Calendar.YEAR);
     mMonth = mIndoorHarvestCalendar.get(Calendar.MONTH) + 1;
     mDay = mIndoorHarvestCalendar.get(Calendar.DATE);
     mDate = mMonth + "/" + mDay + "/" + mYear;
     mRepeat = "false";
     mRepeatType = "Does Not Repeat";
     // Creating Reminder
     int _id = rb.addReminder(new Reminder(mTitle, mDate, mTime, mRepeat, "1", mRepeatType, mActive));
     new AlarmReceiver().setAlarm(getApplicationContext(), mIndoorHarvestCalendar, _id);
     Toast.makeText(GrowAssistantActivity.this, "Indoor Harvest Date Set " + mDate,
     Toast.LENGTH_SHORT).show();
     }

     /****************************************
     * Hydro Harvest Reminder Function
     ****************************************/
    /**public void SetNotificationsButtonHydroHarvestDay(View v){
     ReminderDatabase rb = new ReminderDatabase(this);
     mTitle = "Hydro Harvest Day!  Time to take down those lovely ladies.";
     mHydroHarvestCalendar = (Calendar)mCalendar.clone();
     mHydroHarvestCalendar.add(Calendar.DAY_OF_YEAR, mFloweringDays);
     if (mFloweringDays >= 70) {
     mHydroHarvestCalendar.add(Calendar.DAY_OF_YEAR, -10);
     } else if (mFloweringDays >= 63) {
     mHydroHarvestCalendar.add(Calendar.DAY_OF_YEAR, -7);
     }
     mHour = mHydroHarvestCalendar.get(Calendar.HOUR_OF_DAY);
     mMinute = mHydroHarvestCalendar.get(Calendar.MINUTE);
     mYear = mHydroHarvestCalendar.get(Calendar.YEAR);
     mMonth = mHydroHarvestCalendar.get(Calendar.MONTH) + 1;
     mDay = mHydroHarvestCalendar.get(Calendar.DATE);
     mDate = mMonth + "/" + mDay + "/" + mYear;
     mRepeat = "false";
     mRepeatType = "Does Not Repeat";
     // Creating Reminder
     int _id = rb.addReminder(new Reminder(mTitle, mDate, mTime, mRepeat, "1", mRepeatType, mActive));
     new AlarmReceiver().setAlarm(getApplicationContext(), mHydroHarvestCalendar, _id);
     Toast.makeText(GrowAssistantActivity.this, "Hydro Harvest Selected " + mDate,
     Toast.LENGTH_SHORT).show();
     }


     /****************************************
     * Hydro Reservoir Change Reminder Function
     ****************************************/
    /**public void SetNotificationsButtonHydroResChange(View v){
     ReminderDatabase rb = new ReminderDatabase(this);
     mTitle = "Did you remember to change your hydro reservoir this week?";
     mHydroResCalendar = (Calendar)mCalendar.clone();
     mHydroResCalendar.add(Calendar.DAY_OF_YEAR, 7);
     mYear = mHydroResCalendar.get(Calendar.YEAR);
     mMonth = mHydroResCalendar.get(Calendar.MONTH) + 1;
     mDay = mHydroResCalendar.get(Calendar.DATE);
     mDate = mMonth + "/" + mDay + "/" + mYear;
     mRepeat = "true";
     mRepeatType = "Weekly Reminder";
     mRepeatTime = milWeek;
     // Creating Reminder
     int _id = rb.addReminder(new Reminder(mTitle, mDate, mTime, mRepeat, "1", mRepeatType, mActive));
     new AlarmReceiver().setAlarm(getApplicationContext(), mHydroResCalendar, _id);
     Toast.makeText(GrowAssistantActivity.this, "Hydro Res Change Selected " + mDate,
     Toast.LENGTH_SHORT).show();
     }

     /****************************************
     * Clone Reminder Function
     ****************************************/
    // TAKE CLONES REMINDER - On clicking the set notifications button
    /**public void SetNotificationsButtonTakeClones(View v){
     ReminderDatabase rb = new ReminderDatabase(this);
     // Reset Time for this notification
     mTitle = "If you want clones, you should take cuts now.";
     mTakeClonesCalendar = (Calendar)mCalendar.clone();
     mTakeClonesCalendar.add(Calendar.DAY_OF_YEAR, 14);
     // Check if grow is indoor or outdoor
     if (mIsOutdoor == "true") {
     // add code to select mid august for clones
     mTakeClonesCalendar.set(Calendar.DAY_OF_YEAR, 230);
     }
     mYear = mTakeClonesCalendar.get(Calendar.YEAR);
     mMonth = mTakeClonesCalendar.get(Calendar.MONTH) + 1;
     mDay = mTakeClonesCalendar.get(Calendar.DATE);
     mDate = mMonth + "/" + mDay + "/" + mYear;
     mRepeat = "false";
     mRepeatType = "One Time Reminder";
     // Creating Reminder
     int _id = rb.addReminder(new Reminder(mTitle, mDate, mTime, mRepeat, "1", mRepeatType, mActive));
     new AlarmReceiver().setAlarm(getApplicationContext(), mTakeClonesCalendar, _id);
     Toast.makeText(GrowAssistantActivity.this, "Take Clones " + mDate,
     Toast.LENGTH_SHORT).show();
     }


     /****************************************
     * Defoliate Reminder Function
     ****************************************/
// DEFOLIATE REMINDER - On clicking the set notifications button
    /** public void SetNotificationsButtonDefoliate(View v){
     ReminderDatabase rb = new ReminderDatabase(this);
     mDefoliateCalendar = (Calendar)mCalendar.clone();
     mDefoliateCalendar.add(Calendar.DAY_OF_YEAR, 7);
     if (mIsOutdoor == "true") {
     mDefoliateCalendar.set(Calendar.DAY_OF_YEAR, 235);
     }
     mTitle = "Have you defoliated your plants to increase light penetration?";
     mYear = mDefoliateCalendar.get(Calendar.YEAR);
     mMonth = mDefoliateCalendar.get(Calendar.MONTH) + 1;
     mDay = mDefoliateCalendar.get(Calendar.DATE);
     mDate = mMonth + "/" + mDay + "/" + mYear;
     mRepeat = "true";
     mRepeatType = "Monthly";
     mRepeatTime = milMonth;
     // Creating Reminder
     int _id = rb.addReminder(new Reminder(mTitle, mDate, mTime, mRepeat, "1", mRepeatType, mActive));
     new AlarmReceiver().setRepeatAlarm(getApplicationContext(), mDefoliateCalendar, _id, mRepeatTime);
     Toast.makeText(GrowAssistantActivity.this, "Defoliate " + mDate, Toast.LENGTH_SHORT).show();
     }

     /**************************************************************************************
     * OnClickListener for radio group selection results
     **************************************************************************************/
    /** public void addListenerOnButtonClick() {
     // Find Radio Groups By _id
     rgGrowMediumOptions = (RadioGroup) findViewById(R.id.rgGrowMedium);
     rgWaterOptions = (RadioGroup) findViewById(R.id.rgWaterOptions);
     rgFertilizerOptions = (RadioGroup) findViewById(R.id.rgFertilizerOptions);
     rgIndicaSativaOptions = (RadioGroup) findViewById(R.id.rgIndicaSativa);
     // Button that gets things going
     buttonSetNotifications = (Button) findViewById(R.id.button_set_notifications);
     buttonSetNotifications.setOnClickListener(new View.OnClickListener() {

    @Override public void onClick(View v) {

    // get selected radio buttons from each radioGroup
    int selectedWaterScheduleID = rgWaterOptions.getCheckedRadioButtonId();
    int selectedFertilzierScheduleID = rgFertilizerOptions.getCheckedRadioButtonId();
    int selectedGrowMediumID = rgGrowMediumOptions.getCheckedRadioButtonId();
    int selectedIndicaSativaRatioID = rgIndicaSativaOptions.getCheckedRadioButtonId();

    // find the RadioButton by returned id
    radioGrowMediumChoice = (RadioButton) findViewById(selectedGrowMediumID);
    radioFertilizerChoice = (RadioButton) findViewById(selectedFertilzierScheduleID);
    radioWateringSchedule = (RadioButton) findViewById(selectedWaterScheduleID);
    // radioIndicaSativaRatio = (RadioButton) findViewById(selectedIndicaSativaRatioID);

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

    // Grow Medium

    /****************************************************
     * Indoor Soil Grow Medium Radio Button Selection
     ****************************************************/
    /**   if (radioSoil == radioGrowMediumChoice) {
     // Indoor Harvest Reminder
     SetNotificationsButtonIndoorHarvestDay(v);
     // Flush Reminder - Soil 2 weeks before harvest
     SetNotificationsButtonFlush(v);
     // Clone Reminder - 2 Weeks After Flower
     SetNotificationsButtonTakeClones(v);
     Toast.makeText(GrowAssistantActivity.this, "Soil Harvest Selected" +
     mDate, Toast.LENGTH_SHORT).show();
     }

     /***************************************************
     * Outdoor Grow Medium Radio Button Selection
     ***************************************************/
    /**     else  if (radioOutdoor == radioGrowMediumChoice) {
     // Outdoor Harvest Reminder
     SetNotificationsButtonOutdoorHarvestDay(v);
     // Flush Reminder - 2 weeks before harvest;
     SetNotificationsButtonFlush(v);
     // Clone Reminder - mid August
     SetNotificationsButtonTakeClones(v);
     Toast.makeText(GrowAssistantActivity.this, "Outdoor Harvest Selected", Toast
     .LENGTH_SHORT).show();
     }

     /****************************************************
     * Hydro Grow Medium Radio Button Selection
     ****************************************************/
    /**    else if (radioHydroponic == radioGrowMediumChoice) {
     // Harvest Notification - Hydro
     SetNotificationsButtonHydroHarvestDay(v);
     // Reservoir change reminder
     SetNotificationsButtonHydroResChange(v);
     // Clone Reminder - 2 Weeks Into Flower
     SetNotificationsButtonTakeClones(v);
     Toast.makeText(GrowAssistantActivity.this, "Hydro Harvest Selected", Toast
     .LENGTH_SHORT).show();
     }

     // Type Of Nutes

     /****************************************************
     * Chemical Fertilizer Radio Button Selection
     ****************************************************/
    /**     if (radioChemical == radioFertilizerChoice) {
     // Chemical Fert Reminder
     mTitle = "It's time to feed the girls.  Be careful not to burn them with your" +
     " fertilizer.";
     SetNotificationsButtonFert(v);
     Toast.makeText(GrowAssistantActivity.this, "Chemical Fertilizer selected",
     Toast.LENGTH_SHORT).show();
     }

     /****************************************************
     * Organic Fertilizer Radio Button Selection
     ****************************************************/
    /**   else if (radioOrganic == radioFertilizerChoice) {
     mTitle = "This would be a good time to amend with some organic compost tea,                            top dressing or unsulfured molasses.";
     SetNotificationsButtonFert(v);
     Toast.makeText(GrowAssistantActivity.this, "Organic Fertilizer Selected",
     Toast.LENGTH_SHORT).show();
     }

     // Frequency Of Watering

     /****************************************************
     * Water Every Day Radio Button Selection
     ****************************************************/
    /**    if (radioWaterDaily == radioWateringSchedule) {
     mTitle = "Have you watered your plants today?";
     mWaterCalendar = (Calendar)mCalendar.clone();
     mWaterCalendar.add(Calendar.DAY_OF_YEAR, 1);
     mRepeatType = "Repeats Daily";
     mRepeatTime = milDay;
     SetNotificationsButtonWater(v);
     Toast.makeText(GrowAssistantActivity.this, "Watering Reminder Set",
     Toast.LENGTH_SHORT).show();
     }

     /****************************************************
     * Water Every Other Day Radio Button Selection
     ****************************************************/
    /** else if (radioWater2Days == radioWateringSchedule) {
     mTitle = "Don't forget to water the girls every other day.";
     mWaterCalendar = (Calendar)mCalendar.clone();
     mWaterCalendar.add(Calendar.DAY_OF_YEAR, 2);
     mRepeatType = "Repeats Every Other Day";
     mRepeatTime = (2 * milDay);
     SetNotificationsButtonWater(v);
     Toast.makeText(GrowAssistantActivity.this, "Watering Reminder Set",
     Toast.LENGTH_SHORT).show();
     }

     /****************************************************
     * Water Every 3 Days Radio Button Selection
     ****************************************************/
    /**    else if (radioWater3Days == radioWateringSchedule) {
     mTitle = "Don't forget to water the girls every 3 days.";
     mWaterCalendar = (Calendar)mCalendar.clone();
     mWaterCalendar.add(Calendar.DAY_OF_YEAR, 3);
     mRepeatType = "Repeats Every 3 Days";
     mRepeatTime = (3 * milDay);
     SetNotificationsButtonWater(v);
     Toast.makeText(GrowAssistantActivity.this, "Watering Reminder Set", Toast
     .LENGTH_SHORT).show();
     }

     /****************************************************
     * Weekly Watering Radio Button Selection
     ****************************************************/
    /**  else if (radioWaterWeekly == radioWateringSchedule) {
     mTitle = "Don't forget about your weekly watering schedule.";
     mWaterCalendar = (Calendar)mCalendar.clone();
     mWaterCalendar.add(Calendar.DAY_OF_YEAR, 7);
     mRepeatType = "Repeats Once Weekly";
     mRepeatTime = milWeek;
     SetNotificationsButtonWater(v);
     Toast.makeText(GrowAssistantActivity.this, "Watering Reminder Set", Toast
     .LENGTH_SHORT).show();
     }

     /****************************************************
     * Do Not Set Watering Reminder Radio Button Selection
     ****************************************************/
    /**
     * else if (radioWaterNoNotify == radioWateringSchedule) {
     * // No Reminder is to be set on watering options
     * Toast.makeText(GrowAssistantActivity.this, "You Have Selected 'Not' To Receive " +
     * "Watering Reminders", Toast.LENGTH_SHORT).show();
     * } //
     * finish();
     * }
     * <p>
     * <p>
     * });
     * <p>
     * }
     */

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

    // On pressing the back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}