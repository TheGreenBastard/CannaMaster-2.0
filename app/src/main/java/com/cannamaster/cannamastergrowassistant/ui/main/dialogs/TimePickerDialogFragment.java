package com.cannamaster.cannamastergrowassistant.ui.main.dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cannamaster.cannamastergrowassistant.GrowAssistantActivity;

import java.util.Calendar;

public class TimePickerDialogFragment extends DialogFragment {

    GrowAssistantActivity growAssistantActivity;

    public TimePickerDialogFragment(GrowAssistantActivity growAssistantActivity) {
        this.growAssistantActivity = growAssistantActivity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),growAssistantActivity,hour,minute, false);


    }
}