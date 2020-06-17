package com.cannamaster.cannamastergrowassistant;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class HelpPage extends AppCompatActivity {

    String emailTitle;
    String emailBody;
    final String emailRecipient = "prisonerofconsciousness@gmail.com";
    RadioButton rbQuestion;
    RadioButton rbError;
    RadioButton rbSuggestion;
    EditText editTextForIssue;
    Button mSendEmailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get the views
        setContentView(R.layout.help_page_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // set page title in appbar
        Objects.requireNonNull(getSupportActionBar()).setTitle("CannaMaster Help");
        // set up back press arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get the views from layout

        // todo : subject is not working when sending mail
        // get the radio button views
        rbQuestion = findViewById(R.id.radio_question);
        rbError = findViewById(R.id.radio_error);
        rbSuggestion = findViewById(R.id.radio_suggestion);
        // get the edit text view
        editTextForIssue = findViewById(R.id.edit_text_for_issue);

        // set up send button
        mSendEmailButton = findViewById(R.id.help_page_send_button);
        mSendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get the contents of the edit textbox
                emailBody = editTextForIssue.getText().toString();
                // sends the email via intent
                Intent email = new Intent(Intent.ACTION_SEND);
                // add the email recipient
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{emailRecipient});
                // add the subject
                email.putExtra(Intent.EXTRA_SUBJECT, emailTitle);
                // add the contents of textbox
                email.putExtra(Intent.EXTRA_TEXT, emailBody);

                //need this too prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
                // set page title
            }
        });
    }


    // This is for the back button on the toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onRadioButtonClicked(View view) {
        // is the button checked now?
        boolean checked = ((RadioButton) view).isChecked();
        // now check which radio button is checked
        switch (view.getId()) {
            case R.id.radio_error:
                if (checked)
                    // this will change the variable for the email title
                    emailTitle = "User Error Report : CannaMaster App";
                break;
            case R.id.radio_question:
                if (checked)
                    // this will change the variable for the email title
                    emailTitle = "User Question Regarding CannaMaster App";
                break;
            case R.id.radio_suggestion:
                if (checked)
                    // this will change the variable for the email title
                    emailTitle = "User Suggestion For CannaMaster App";
                break;
        }
    }
}


