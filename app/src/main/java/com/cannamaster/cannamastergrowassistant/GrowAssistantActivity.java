package com.cannamaster.cannamastergrowassistant;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.cannamaster.cannamastergrowassistant.ui.main.dialogs.DatePickerFragment;
import com.cannamaster.cannamastergrowassistant.ui.main.dialogs.TimePickerFragment;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class GrowAssistantActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText dateText;
    EditText timeText;
    EditText titleText;
    EditText descText;
    EditText reminderText;
    Button runButton;

    String mRepeat = "false";
    String mRepeatType = "Select Repeat Interval";
    String mActive = "true";

    // Layout components for time and date picker
    ImageView mDateDialog;
    ImageView mTimeDialog;
    DatePicker datePicker;
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
    int mFloweringDays = 70;
    int mFlush2WeeksBefore;

    private int mYear, mMonth, mDay, mHour, mMinute;

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

        mDateDialog = (ImageView) findViewById(R.id.date_icon);
        mTimeDialog = (ImageView) findViewById(R.id.time_icon);
        dateText = (EditText) findViewById(R.id.date_edit_text);
        timeText = (EditText) findViewById(R.id.time_edit_text);
        titleText = (EditText) findViewById(R.id.titleText);
        descText = (EditText) findViewById(R.id.descText);
        reminderText = (EditText) findViewById(R.id.reminderText);
        runButton = (Button) findViewById(R.id.button_set_notifications);

        mDateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment(GrowAssistantActivity.this);
                datePickerFragment.show(getSupportFragmentManager(),"datepicker");
            }
        });

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
    public void onMakeEvent (View view) throws java.text.ParseException {
        // Get the EditText views by ID
        // set some values into variables from user input

        /**  This checks the edit text fields to ensure there is some kind of data entered **/
        if (titleText.getText().length() < 1) {
            titleText.setError("You must provide a title for the event");
            runButton.setError("Error : Check your information above");}
        else if (descText.getText().length() < 2) {
            descText.setError("You must provide a short description");
            runButton.setError("Error : Check your information above");}
        else if (dateText.getText().length() < 10) {
            dateText.setError("please provide a start date 'YYYY-MM-DD'");
            runButton.setError("Error : Check your information above");}
        else if (dateText.getText().length() > 10) {
            dateText.setError("please provide a start date 'YYYY-MM-DD'");
            runButton.setError("Error : Check your information above");}
        else if (timeText.getText().length() < 5) {
            timeText.setError("please provide a time for events 'hh:mm'");
            runButton.setError("Error : Check your information above");; }
        else if (timeText.getText().length() > 5) {
            timeText.setError("please provide a time for events 'hh:mm'");
            runButton.setError("Error : Check your information above");}
        else if (reminderText.getText().length() < 2) {
            descText.setError("Enter a time to alert before the event");
            runButton.setError("Error : Check your information above");}
        else {

            /**  as long as the EditTexts are filled out run the following code **/
            // Converts the EditTexts to Strings
            String mDate = dateText.getText().toString();
            String startTime = timeText.getText().toString();
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
            FlushReminder();
        }

    }

    /*******************************************************************************
     * OnCheckChanged Listener for Indica/Sativa Ratio - (it sets mFloweringDays)
     *******************************************************************************/

    // This changes the value of mFloweringDays every time a button in the group is selected.
    public void indicaSativaRatio(View view) {

        RadioGroup rgIndicaSativaOptions = findViewById(R.id.rgIndicaSativa);
        rgIndicaSativaOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Get Index Of Radio Button
                int pos = rgIndicaSativaOptions.indexOfChild(findViewById(rgIndicaSativaOptions.getCheckedRadioButtonId()));
                mFloweringDays = 70;
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

    public void FlushReminder() throws java.text.ParseException {

        // Converts the EditTexts to Strings
        String mDate = dateText.getText().toString();

        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        Calendar cal = Calendar.getInstance();
        cal.setTime( dateFormat.parse(mDate));
        cal.add( Calendar.DATE, mFloweringDays -14);
        String convertedDate=dateFormat.format(cal.getTime());
        System.out.println("Flush date set to "+convertedDate);

        String startTime = timeText.getText().toString();
        // takes the 2 EditText fields and combines them to set the date and time of notification
        String startDate = convertedDate + " " + startTime;
        // sets the end date the same as the start date so the rest of the hard code still works
        String endDate = startDate;
        // this is the event title
        String titleString = "Time To Start Flushing";
        // this is the event description
        String descString = "From now until harvest use only water to quench your plants.  No fertilizer or supplements.  This will wash out any undissolved salts in your grow medium which in turn will make your final product taste better.";
        // this is the time before the event that the reminder will fire
        String reminderString = reminderText.getText().toString();
        int reminderInt = Integer.parseInt(reminderString);
        // Calls the function to add the event to the calender
        addToDeviceCalendar(startDate, endDate, titleString, descString, reminderInt);

        Toast.makeText(GrowAssistantActivity.this, "Flush Notificiation Has Been Set",
                Toast.LENGTH_SHORT).show();
    }

    /** date picker dialog rules **/
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // Do something with the date chosen by the user
        // This sets the date picker dialog to work properly
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


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (hourOfDay <10 && minute <10) {  // conditions so date is filled out correctly
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
