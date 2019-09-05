package com.opus_bd.pictorialsurvey.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.opus_bd.pictorialsurvey.Activity.Admin.MainActivity;
import com.opus_bd.pictorialsurvey.Model.Constant;
import com.opus_bd.pictorialsurvey.Model.Survey;
import com.opus_bd.pictorialsurvey.R;

import java.util.HashMap;
import java.util.Map;

public class AddNewSurveyActivity extends AppCompatActivity {
    EditText etSurveyName,etSurveyDescription,etSurveyPassword;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_survey);
        etSurveyName=findViewById(R.id.etSurveyName);
        etSurveyDescription=findViewById(R.id.etSurveyDescription);
        etSurveyPassword=findViewById(R.id.etSurveyPassword);
        btnSave=findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit();
            }
        });
    }

    public void Submit( ) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child(Constant.SURVEY).child(Constant.SURVEY_LIST);
        //String key = databaseReference.push().getKey();
        String key = "survey_key"+System.currentTimeMillis();

        Map<String, String> firebaseDataMap = new HashMap<>();
        firebaseDataMap.put(Constant.SETSURVEY, etSurveyName.getText().toString());
        firebaseDataMap.put(Constant.SETSURVEYDESCRIPTION, etSurveyDescription.getText().toString());
        firebaseDataMap.put(Constant.SETSURVEYPASSWORD, etSurveyPassword.getText().toString());
        Survey survey = new Survey();
        survey.setSurveyName(etSurveyName.getText().toString());
        survey.setDescription(etSurveyDescription.getText().toString());
        survey.setPassword(etSurveyPassword.getText().toString());
        survey.setSurveyCondition("Open");
        survey.setKey(key);
        databaseReference.child(key).setValue(survey);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Survey_ID",key);
        startActivity(intent);
    }
}
