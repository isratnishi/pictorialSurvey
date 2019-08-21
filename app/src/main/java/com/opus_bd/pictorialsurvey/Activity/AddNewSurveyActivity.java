package com.opus_bd.pictorialsurvey.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.opus_bd.pictorialsurvey.Model.Constant;
import com.opus_bd.pictorialsurvey.Model.Question;
import com.opus_bd.pictorialsurvey.Model.Survey;
import com.opus_bd.pictorialsurvey.R;

import java.util.HashMap;
import java.util.Map;

public class AddNewSurveyActivity extends AppCompatActivity {
    EditText etSurveyName,etSurveyDescription;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_survey);
        etSurveyName=findViewById(R.id.etSurveyName);
        etSurveyDescription=findViewById(R.id.etSurveyDescription);
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

        Map<String, String> firebaseDataMap = new HashMap<>();
        firebaseDataMap.put(Constant.SETSURVEY, etSurveyName.getText().toString());
        firebaseDataMap.put(Constant.SETSURVEYDESCRIPTION, etSurveyDescription.getText().toString());
        Survey survey = new Survey();
        survey.setSurveyName(etSurveyName.getText().toString());
        survey.setDescription(etSurveyDescription.getText().toString());
        databaseReference.push().setValue(survey);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
