package com.cannamaster.cannamastergrowassistant.ui.main.localcalmanager;

/*************************************************
 * This is the values for Calendar Manager Event
 *************************************************/

public class CalendarManagerEvent {
    private String eventID;
    private String title;
    private String desc;

    public CalendarManagerEvent(int id, String title, String desc, long dtstart, long dtend, String eventID)
    {  // This is the model for the array that is made for listview items
        this.title = title;
        this.desc = desc;
        this.eventID = eventID;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }


    public String getUid()
    {
        return eventID;
    }

}
