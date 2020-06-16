package com.cannamaster.cannamastergrowassistant.ui.main.localcalmanager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by paulq on 15/10/2019.
 */

public class CalendarEventDate {
    private Date date;
    private int shiftNumber;
    private double totalShiftLength;
    private double totalEarnings;


    public CalendarEventDate(Date date, double totalShiftLength, double totalEarnings)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.date = date;
        this.totalShiftLength = totalShiftLength;
        this.totalEarnings = totalEarnings;
    }
    public CalendarEventDate(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.date = date;
    }

    public Date getDate()
    {
        return this.date;
    }

}
