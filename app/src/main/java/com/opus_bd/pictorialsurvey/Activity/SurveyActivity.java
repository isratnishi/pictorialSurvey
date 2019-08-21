package com.opus_bd.pictorialsurvey.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opus_bd.pictorialsurvey.Adapter.ViewItemsAdapter;
import com.opus_bd.pictorialsurvey.Adapter.ViewItemsQuestionAdapter;
import com.opus_bd.pictorialsurvey.Model.Constant;
import com.opus_bd.pictorialsurvey.Model.Question;
import com.opus_bd.pictorialsurvey.Model.Survey;
import com.opus_bd.pictorialsurvey.R;

import java.util.ArrayList;

public class SurveyActivity extends AppCompatActivity {
    TextView tvName, tvDescription;
    Button btnAddQuestion;
    Survey survey;

    private RecyclerView recyclerView;
    ViewItemsQuestionAdapter viewItemsAdapter;
    ArrayList<Question> models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        tvName = findViewById(R.id.tvName);
        tvDescription = findViewById(R.id.tvDescription);
        btnAddQuestion = findViewById(R.id.btnAddQuestion);
        recyclerView = findViewById(R.id.Recyclerview);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            survey = (Survey) getIntent().getSerializableExtra(Constant.EXTRA_ITEM);
        }
        try {
            if (survey != null) {
                tvName.setText(survey.getSurveyName());
            }
            if (survey != null) {
                tvDescription.setText(survey.getDescription());
            }
        } catch (Exception e) {
        }

        database();
        initializeVariables();
        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              showDialog();

            }
        });

    }

    public void showDialog() {
        // Create custom dialog object
        final Dialog dialog = new Dialog(SurveyActivity.this);
        // Include dialog.xml file
        dialog.setContentView(R.layout.content_main);
        // Set dialog title
        dialog.setTitle("Are You Admin?");

        // set values for custom dialog components - text, image and button
        final EditText etUserId = dialog.findViewById(R.id.etUserId);
        final EditText etPassword = dialog.findViewById(R.id.etPassword);
        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etUserId.getText().toString().equals("ADMIN") && etPassword.getText().toString().equals("123456")) {
                    Intent intent = new Intent(SurveyActivity.this, QuestionActivity.class);
                    intent.putExtra(Constant.EXTRA_ITEM, survey);

                    startActivity(intent);
                }

            }
        });

        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
    public void database() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference().child(Constant.SURVEY).child(Constant.QUESTION).child(survey.getSurveyName());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                models.clear();

                for (DataSnapshot tempDataSnapShot : dataSnapshot.getChildren()) {
                    Question foodItem = tempDataSnapShot.getValue(Question.class);

                    if (foodItem != null) {
                        models.add(foodItem);


                    }
                }

                viewItemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });


    }

    private void initializeVariables() {
        viewItemsAdapter = new ViewItemsQuestionAdapter(models, SurveyActivity.this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setStackFromEnd(true);
        mLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(viewItemsAdapter);
    }

   }
