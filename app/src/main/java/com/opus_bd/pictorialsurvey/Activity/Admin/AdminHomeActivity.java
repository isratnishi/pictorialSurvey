package com.opus_bd.pictorialsurvey.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.opus_bd.pictorialsurvey.R;

public class AdminHomeActivity extends AppCompatActivity {
    CardView cvCreateSurvey, cvExixtingSurvey, cvClosedSurvey;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        cvCreateSurvey = findViewById(R.id.cvCreateSurvey);
        cvExixtingSurvey = findViewById(R.id.cvExixtingSurvey);
        cvClosedSurvey = findViewById(R.id.cvClosedSurvey);
        image = findViewById(R.id.image);
        Glide.with(this).load(R.drawable.survey_admin).into(image);

        cvCreateSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AddNewSurveyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK & Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        cvExixtingSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK & Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }); cvClosedSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, ClosedSurveyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK & Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
