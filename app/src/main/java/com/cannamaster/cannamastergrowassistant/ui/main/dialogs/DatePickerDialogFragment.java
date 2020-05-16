package com.cannamaster.cannamastergrowassistant.ui.main.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.cannamaster.cannamastergrowassistant.GrowAssistantActivity;

import java.util.Calendar;
public class DatePickerDialogFragment extends DialogFragment {

    GrowAssistantActivity growAssistantActivity;

    public DatePickerDialogFragment(GrowAssistantActivity growAssistantActivity) {
        this.growAssistantActivity = growAssistantActivity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        return new DatePickerDialog(getActivity(),growAssistantActivity,year,month,day);
    }
}