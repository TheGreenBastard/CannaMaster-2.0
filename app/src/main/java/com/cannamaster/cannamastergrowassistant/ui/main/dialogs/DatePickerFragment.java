package com.cannamaster.cannamastergrowassistant.ui.main.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cannamaster.cannamastergrowassistant.GrowAssistantActivity;
import com.cannamaster.cannamastergrowassistant.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    GrowAssistantActivity growAssistantActivity;
    EditText dateEditText;

    public DatePickerFragment(GrowAssistantActivity growAssistantActivity) {
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

    public void onDateSet(DatePicker view, int year, int month, int day) {

    }






}