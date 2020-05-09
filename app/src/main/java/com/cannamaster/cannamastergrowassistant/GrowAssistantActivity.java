package com.cannamaster.cannamastergrowassistant;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;


public class GrowAssistantActivity extends AppCompatActivity {

    TextView mDateText, mTimeText;
    Calendar mCalendar;
    Calendar mIndoorHarvestCalendar;
    Calendar mOutdoorHarvestCalendar;
    Calendar mWaterCalendar;
    Calendar mFertilizeCalendar;
    Calendar mFlushCalendar;
    Calendar mTakeClonesCalendar;
    Calendar mHydroHarvestCalendar;
    Calendar mHydroResCalendar;
    Calendar mDefoliateCalendar;
    int mYear, mMonth, mHour, mMinute, mDay;
    long mRepeatTime;
    String mTitleText;
    String mTitle;
    String mTime;
    String mDate;
    String mRepeat;
    String mIsOutdoor;
    String mRepeatType;
    String mActive;

    // private RelativeLayout mHowOftenRepeat;

    // Radio Groups
    RadioGroup rgGrowMediumOptions;
    RadioGroup rgFertilizerOptions;
    RadioGroup rgWaterOptions;
    RadioGroup rgIndicaSativaOptions;

    // Values for Button
    Button buttonSetNotifications;

    // Initialize the RadioButtons
    RadioButton radioWateringSchedule;
    RadioButton radioFertilizerChoice;
    RadioButton radioGrowMediumChoice;

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

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grow_assistant_activity_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Grow Assistant");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Initialize Views
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //mDateText = (TextView) findViewById(R.id.set_date);
        //mTimeText = (TextView) findViewById(R.id.set_time);

        // Initialize default values
        mActive = "true";
        mRepeat = "false";
        mIsOutdoor = "false";
        mRepeatType = "Select Repeat Interval";
        mTitle = "Default Title";
        mTitleText = mTitle;
        mFloweringDays = 70;

        // sets default values if the date/time picker isnt clicked
        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DATE);
        mDate = mMonth + "/" + mDay + "/" + mYear;
        mTime = mHour + ":" + mMinute;

        // Setup TextViews using reminder values
        mDateText.setText(mDate);
        mTimeText.setText(mTime);

        // To save state on device rotation
        if (savedInstanceState != null) {
            String savedTitle = savedInstanceState.getString(KEY_TITLE);
            mTitle = savedTitle;

            String savedTime = savedInstanceState.getString(KEY_TIME);
            mTimeText.setText(savedTime);
            mTime = savedTime;

            String savedDate = savedInstanceState.getString(KEY_DATE);
            mDateText.setText(savedDate);
            mDate = savedDate;

            String saveRepeat = savedInstanceState.getString(KEY_REPEAT);
            mRepeat = saveRepeat;

            String savedRepeatType = savedInstanceState.getString(KEY_REPEAT_TYPE);
            mRepeatType = savedRepeatType;

            mActive = savedInstanceState.getString(KEY_ACTIVE);
        }
        // addListenerOnButtonClick();
    }

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

    /** On clicking Time picker
     public void setTime(View v){
     Calendar now = Calendar.getInstance();
     TimePickerDialog tpd = TimePickerDialog.newInstance(
     this,
     now.get(Calendar.HOUR_OF_DAY),
     now.get(Calendar.MINUTE),
     false
     );
     tpd.setThemeDark(false);
     tpd.show(getFragmentManager(), "Timepickerdialog");
     }

     // On clicking Date picker
     public void setDate(View v){
     Calendar now = Calendar.getInstance();
     DatePickerDialog dpd = DatePickerDialog.newInstance(
     this,
     now.get(Calendar.YEAR),
     now.get(Calendar.MONTH),
     now.get(Calendar.DAY_OF_MONTH)
     );
     dpd.show(getFragmentManager(), "Datepickerdialog");
     }

     // Obtain time from time picker
     @Override public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
     mHour = hourOfDay;
     mMinute = minute;
     if (minute < 10) {
     mTime = hourOfDay + ":0" + minute;
     } else {
     mTime = hourOfDay + ":" + minute;
     }
     mTimeText.setText(mTime);
     }

     // Obtain date from date picker
     @Override public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

     mYear = year;
     mMonth = monthOfYear;
     mDay = dayOfMonth;
     mDate = mMonth + "/" + mDay + "/" + mYear;
     mDateText.setText(mDate);

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