package com.cannamaster.cannamastergrowassistant.ui.main.localcalmanager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/****************************************************************************************
 * holder for some values relating to CalendarEventDate - not sure this is even needed
 ****************************************************************************************/

public class CalendarEventDate {
    private Date date;
    private String uid;




    public CalendarEventDate(Date date,String uid)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.date = date;
        this.uid = uid;
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
