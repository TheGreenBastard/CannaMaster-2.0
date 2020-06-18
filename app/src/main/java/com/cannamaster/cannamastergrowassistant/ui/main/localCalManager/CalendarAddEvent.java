package com.cannamaster.cannamastergrowassistant.ui.main.localcalmanager;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.provider.CalendarContract;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.cannamaster.cannamastergrowassistant.R;
import com.google.android.material.snackbar.Snackbar;
//import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
//import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
//import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

public class CalendarAddEvent extends AppCompatActivity {
    Context context;
    Date startDate,endDate;
    TextView tv_start, tv_end;
    EditText title;
    Button cancel_btn, add_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Calendar c = new GregorianCalendar();
        c.setTime(Calendar.getInstance().getTime());
        c.set(Calendar.MINUTE, 0);
        context = this;

    }


    public boolean addEvent(){
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, 0);
            }
            ContentResolver cr = getContentResolver();
            //add event details
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.DTSTART, startDate.getTime());
            values.put(CalendarContract.Events.DTEND, endDate.getTime());
            values.put(CalendarContract.Events.TITLE, title.getText().toString());
            values.put(CalendarContract.Events.DESCRIPTION, "");
            values.put(CalendarContract.Events.CALENDAR_ID, 16);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, "Europe/London");
            values.put(CalendarContract.Events.GUESTS_CAN_INVITE_OTHERS, "1");
            values.put(CalendarContract.Events.GUESTS_CAN_SEE_GUESTS, "1");

            cr.insert(CalendarContract.Events.CONTENT_URI, values);
        }
        catch(SecurityException e){
            Log.e("Security Exception", e.getMessage());
            return false;
        }
        catch(Exception e) {
            Log.e("General Exception", e.getMessage());
            return false;
        }
        return true;
    }
}
