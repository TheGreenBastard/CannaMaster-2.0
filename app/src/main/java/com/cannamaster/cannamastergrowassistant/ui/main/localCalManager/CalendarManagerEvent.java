package com.cannamaster.cannamastergrowassistant.ui.main.localcalmanager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*************************************************
 * This is the values for Calendar Manager Event
 *************************************************/

public class CalendarManagerEvent {
    private int id;
    private int uid;
    private String title;
    private String desc;
    private long dtstart;
    private long dtend;

    public CalendarManagerEvent()
    {

    }
    public CalendarManagerEvent(int id, String title, String desc, long dtstart, long dtend)
    {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.dtstart = dtstart;
        this.dtend = dtend;
        this.uid = (int) (dtstart+dtend);
    }

    public Date getStartDate(){
        return new Date(dtstart);
    }

    public Date getEndDate(){
        return new Date(dtend);
    }

    public String getTitle(){
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getStartTime(){
        DateFormat dateFormat = new SimpleDateFormat("kk:mm");
        return dateFormat.format(new Date(dtstart));
    }

    public String getEndTime(){
        DateFormat dateFormat = new SimpleDateFormat("kk:mm");
        return dateFormat.format(new Date(dtend));
    }


    public int getUid()
    {
        return uid;
    }

}
