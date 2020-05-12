package com.cannamaster.cannamastergrowassistant.ui.main.ui;

/******************************
 *  Database Model for events set with Grow Assistant
 *  for interaction with the calendar 
 ******************************/

// this is what is in every row of the DB
public class GrowAssistantDatabaseModel {
    private String _id;
    private int event_id;
    private String event_time;
    private String event_description;
    private String event_color;

    // functions to interact with the DB
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
    public String getEvent_time() {
        return event_time;
    }
    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }
    public String getEvent_description() {
        return event_description;
    }
    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }
    public String getEvent_color() {
        return event_color;
    }
    public void setEvent_color(String event_color) {
        this.event_color = event_color;
    }



}