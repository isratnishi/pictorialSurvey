package com.opus_bd.pictorialsurvey.Activity.Admin;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.opus_bd.pictorialsurvey.Adapter.ViewItemsQuestionAdapter;
import com.opus_bd.pictorialsurvey.Adapter.ViewItemsQuestionAdapterAdmin;
import com.opus_bd.pictorialsurvey.Model.Constant;
import com.opus_bd.pictorialsurvey.Model.Question;
import com.opus_bd.pictorialsurvey.Model.ServayQuestionModel;
import com.opus_bd.pictorialsurvey.Model.Survey;
import com.opus_bd.pictorialsurvey.R;

import java.util.ArrayList;
import java.util.Map;

import static com.opus_bd.pictorialsurvey.Data.shared_data.CURRENTLY_SHOWING_SURVEY;
import static com.opus_bd.pictorialsurvey.Data.shared_data.CURRENTLY_SHOWING_SURVEY_ID;
import static com.opus_bd.pictorialsurvey.Data.shared_data.CURRENTLY_SHOWING_SURVEY_STATUS;

public class SurveyActivity extends AppCompatActivity {
    TextView tvName, tvDescription;
    Button btnAddQuestion,btnVote,btnClose;
    Survey survey;

    private RecyclerView recyclerView;
    ViewItemsQuestionAdapterAdmin viewItemsAdapter;
    ArrayList<ServayQuestionModel> models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        tvName = findViewById(R.id.tvName);
        tvDescription = findViewById(R.id.tvDescription);
        btnAddQuestion = findViewById(R.id.btnAddQuestion);
        btnVote = findViewById(R.id.btnVote);
        btnClose = findViewById(R.id.btnClose);
        btnVote.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.Recyclerview);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            survey = (Survey) getIntent().getSerializableExtra(Constant.EXTRA_ITEM);
        }
        try {
            if (true) {
                tvName.setText(CURRENTLY_SHOWING_SURVEY.getSurveyName());
            }
            if (true) {
                tvDescription.setText(CURRENTLY_SHOWING_SURVEY.getDescription());
            }
            if(CURRENTLY_SHOWING_SURVEY_STATUS!=null)
            {
                if(CURRENTLY_SHOWING_SURVEY_STATUS.equals("Open")){
                    btnClose.setText("Close");
                }

                else btnClose.setText("Reopen");
            }
        } catch (Exception e) {
        }

        try {
            //Toast.makeText(this, CURRENTLY_SHOWING_SURVEY_ID, Toast.LENGTH_LONG).show();
            database();

        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

        initializeVariables();
        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showDialog();
                Intent intent = new Intent(SurveyActivity.this, QuestionActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra(Constant.EXTRA_ITEM, survey);

                startActivity(intent);
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(CURRENTLY_SHOWING_SURVEY.getSurveyCondition()!=null)
                {
                    if(CURRENTLY_SHOWING_SURVEY.getSurveyCondition().equals("Open"))
                    {
                        showAlert("Close");

                    }

                    else showAlert("Open");
                }
               ;
            }
        });

    }
    public  void  openVoteResultActivity (View view){
        startActivity(new Intent(this,VoteResult.class));
    }
    public void showAlert(final String con) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you Sure to close this survey?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        editData(con);


                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
    private void editData(final String con){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference().child(Constant.SURVEY).child(Constant.SURVEY_LIST).child(CURRENTLY_SHOWING_SURVEY_ID);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("surveyCondition").setValue(con);
                Toast.makeText(SurveyActivity.this,"Data Edited "+con,Toast.LENGTH_LONG).show();
               // Toast.makeText(SurveyActivity.this,"Data Edited",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SurveyActivity.this, AdminHomeActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra(Constant.EXTRA_ITEM, survey);

                startActivity(intent);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SurveyActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public void database() {
        // Toast.makeText(this, "method worked", Toast.LENGTH_SHORT).show();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference().child(Constant.SURVEY).child(Constant.SURVEY_LIST).child(CURRENTLY_SHOWING_SURVEY_ID).child("options");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Toast.makeText(SurveyActivity.this, dataSnapshot.toString(), Toast.LENGTH_LONG).show();


                models.clear();
                //Toast.makeText(SurveyActivity.this, "data downloaded", Toast.LENGTH_SHORT).show();

                try {
                    for (DataSnapshot tempDataSnapShot : dataSnapshot.getChildren()) {
                        ServayQuestionModel foodItem = tempDataSnapShot.getValue(ServayQuestionModel.class);

                        if (foodItem != null) {

                            models.add(foodItem);


                        }
                    }

                    viewItemsAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                   // Toast.makeText(SurveyActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });


    }

    private void initializeVariables() {
        viewItemsAdapter = new ViewItemsQuestionAdapterAdmin(models, SurveyActivity.this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setStackFromEnd(true);
        mLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(viewItemsAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SurveyActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        super.onBackPressed();
    }


}
