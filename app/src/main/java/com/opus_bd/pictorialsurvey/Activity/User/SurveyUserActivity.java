package com.opus_bd.pictorialsurvey.Activity.User;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opus_bd.pictorialsurvey.Activity.Admin.MainActivity;
import com.opus_bd.pictorialsurvey.Activity.Admin.QuestionActivity;
import com.opus_bd.pictorialsurvey.Activity.Admin.SurveyActivity;
import com.opus_bd.pictorialsurvey.Adapter.ViewItemsQuestionAdapter;
import com.opus_bd.pictorialsurvey.Model.Constant;
import com.opus_bd.pictorialsurvey.Model.Question;
import com.opus_bd.pictorialsurvey.Model.ServayQuestionModel;
import com.opus_bd.pictorialsurvey.Model.Survey;
import com.opus_bd.pictorialsurvey.Model.VoteModel;
import com.opus_bd.pictorialsurvey.Model.VotingModel;
import com.opus_bd.pictorialsurvey.R;

import java.util.ArrayList;

import static com.opus_bd.pictorialsurvey.Data.shared_data.CURRENTLY_SHOWING_SURVEY;
import static com.opus_bd.pictorialsurvey.Data.shared_data.CURRENTLY_SHOWING_SURVEY_ID;

public class SurveyUserActivity extends AppCompatActivity {
    TextView tvName, tvDescription;
    Button btnAddQuestion;
    Survey survey;
    FirebaseDatabase firebaseDatabase ;

    private RecyclerView recyclerView;
    ViewItemsQuestionAdapter viewItemsAdapter;
    ArrayList<ServayQuestionModel> models = new ArrayList<>();
    public static ArrayList<VotingModel> votingModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        tvName = findViewById(R.id.tvName);
        tvDescription = findViewById(R.id.tvDescription);
        btnAddQuestion = findViewById(R.id.btnAddQuestion);
        btnAddQuestion.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.Recyclerview);
    /*    Bundle bundle = getIntent().getExtras();
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
        */
try{
    database();
}catch (Exception e){
    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
}

        initializeVariables();
        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              showDialog();

            }
        });

    }
    public void voteSubmit(View view){
       if (votingModels.size()>0) {
           for (int i=0;i<votingModels.size();i++){
               final DatabaseReference reference = firebaseDatabase.getReference().child("voteHistory").child(votingModels.get(i).getQuestuionID());
               String key=reference.push().getKey();
               VoteModel voteModel=new VoteModel();
               voteModel.setVoteResult(votingModels.get(i).getSelectedOptionID());
               voteModel.setVoterID("USER_ID");
               firebaseDatabase.getReference().child("voteHistory").child(votingModels.get(i).getQuestuionID()).child(key).setValue(voteModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       Toast.makeText(SurveyUserActivity.this, "Vote done successfully", Toast.LENGTH_SHORT).show();
                   }
               });

           }
       }

    }

    public void showDialog() {
        // Create custom dialog object
        final Dialog dialog = new Dialog(SurveyUserActivity.this);
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
                    Intent intent = new Intent(SurveyUserActivity.this, QuestionActivity.class);
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
        firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference reference = firebaseDatabase.getReference().child(Constant.SURVEY).child(Constant.SURVEY_LIST).child(CURRENTLY_SHOWING_SURVEY_ID).child("options");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                models.clear();
                Toast.makeText(SurveyUserActivity.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();

                for (DataSnapshot tempDataSnapShot : dataSnapshot.getChildren()) {
                    ServayQuestionModel foodItem = tempDataSnapShot.getValue(ServayQuestionModel.class);

                    if (foodItem != null) {
                        models.add(foodItem);


                    }
                }
                if (models.size()>0){
                    for (int i=0;i<models.size();i++){
                        votingModels.add(new VotingModel("Empty",models.get(i).getOption1().getValue(),
                                models.get(i).getOption1().getKey(), models.get(i).getOption1().getValue(),
                                models.get(i).getOption2().getValue(),"ques_id",models.get(i).getQuestion(),models.get(i).getOptionType()));
                    }

                    viewItemsAdapter.notifyDataSetChanged();
                    Toast.makeText(SurveyUserActivity.this, ""+votingModels.size() +" questions found for user", Toast.LENGTH_LONG).show();

                }else {
                 //   Toast.makeText(SurveyUserActivity.this, "No data", Toast.LENGTH_SHORT).show();
                }
  }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });


    }

    private void initializeVariables() {
        viewItemsAdapter = new ViewItemsQuestionAdapter( SurveyUserActivity.this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setStackFromEnd(true);
        mLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(viewItemsAdapter);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SurveyUserActivity.this, UserMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        super.onBackPressed();
    }
   }