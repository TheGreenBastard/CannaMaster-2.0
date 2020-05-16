package com.cannamaster.cannamastergrowassistant;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class GrowAssistantPage2Activity extends GrowAssistantActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grow_assistant_page_2_layout;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Set the back arrow
        Objects.requireNonNull(getSupportActionBar()).setTitle("Grow Assistant : Variables");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


}
