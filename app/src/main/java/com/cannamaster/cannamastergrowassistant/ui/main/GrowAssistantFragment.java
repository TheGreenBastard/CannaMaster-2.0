package com.cannamaster.cannamastergrowassistant.ui.main;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.cannamaster.cannamastergrowassistant.R;
import com.cannamaster.cannamastergrowassistant.ui.main.localcalmanager.MainActivityCalendarManager;

/*********************************************************************
 * This is the Grow Assistant Fragment visible in the View Pager Tabs
 *********************************************************************/

public class GrowAssistantFragment extends Fragment {

    // for context in fragment
    Context thisContext;

    public void clearPreferences() {
        try {
            // clearing app data
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear com.cannamaster.cannamastergrowassistant");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.grow_assistant_fragment_layout, container, false);

        //   get the view
        View view = inflater.inflate(R.layout.grow_assistant_fragment_layout, container, false);

        Button btn = (Button) view.findViewById(R.id.button_start_growassistant);
        Button btn2 = (Button) view.findViewById(R.id.button_edit_grow_information);
        Button btn3 = (Button) view.findViewById(R.id.button_clear_data);

        // get the context
        thisContext = container.getContext();

        // Start Grow Assistant Button Click
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // just a toast to make sure the button click works
                // Toast.makeText(getActivity(), "Start Grow Assistant Button Clicked", Toast.LENGTH_LONG).show();

                // this starts the grow assistant
                Intent intent = new Intent(thisContext, GrowAssistantActivity.class);
                startActivity(intent);
            }
        });

        // Edit Grow Assistant Data Button Click
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getActivity(), "Edit Data Button Clicked", Toast.LENGTH_LONG)
                //        .show();

                // this starts the grow assistant
                 Intent intent = new Intent(thisContext, MainActivityCalendarManager.class);
                 startActivity(intent);
            }
        });

        // Clear Data Button Click
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // this is to erase all calendar events at once
                Uri eventsUri = Uri.parse("content://com.android.calendar/events");
                ContentResolver cr = new ContentResolver(thisContext) {
                    @Nullable
                    @Override
                    public String[] getStreamTypes(@NonNull Uri url, @NonNull String mimeTypeFilter) {
                        return super.getStreamTypes(url, mimeTypeFilter);
                    }
                };
                Cursor cursor;
                cursor = cr.query(eventsUri, new String[]{ "_id" },"calendar_id=" + 1, null, null);
                while(cursor.moveToNext()) {
                    long eventId = cursor.getLong(cursor.getColumnIndex("_id"));
                    cr.delete(ContentUris.withAppendedId(eventsUri, eventId), null, null);
                }
                cursor.close();
                // Show message
                Toast.makeText(getContext(), "Calendar Cleared!",Toast.LENGTH_LONG).show();


                // Alert Dialog to inform the user all info will be erased
                AlertDialog.Builder builder1 = new AlertDialog.Builder(thisContext);
                builder1.setMessage("This will clear all user data and reset the app to it's original state.  Do you want to erase your data?\n" + "\n" + "This will force close the application.");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Erase",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // The erase click code goes here, this is what happens when erase is clicked
                                Toast.makeText(getActivity(), "Application Data And Preferences Cleared", Toast.LENGTH_LONG).show();
                                // above is erase clicked code below cancels the dialog after the code has run
                                clearPreferences();
                                dialog.cancel();

                            }
                        });

                builder1.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }


        });
        return view;
    }
}