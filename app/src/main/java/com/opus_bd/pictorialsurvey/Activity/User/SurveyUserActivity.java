package com.opus_bd.pictorialsurvey.Activity.User;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opus_bd.pictorialsurvey.Activity.Admin.MainActivity;
import com.opus_bd.pictorialsurvey.Activity.Admin.QuestionActivity;
import com.opus_bd.pictorialsurvey.Activity.Admin.SurveyActivity;
import com.opus_bd.pictorialsurvey.Activity.Admin.VoteResult;
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
    Button btnAddQuestion, btnVote,btnClose;
    Survey survey;
    FirebaseDatabase firebaseDatabase;

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
        btnVote = findViewById(R.id.btnVote);
        btnAddQuestion.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.Recyclerview);
        btnClose = findViewById(R.id.btnClose);
        btnClose.setVisibility(View.GONE);
        tvName.setText(CURRENTLY_SHOWING_SURVEY.getSurveyName());
        tvDescription.setText(CURRENTLY_SHOWING_SURVEY.getDescription());
        try {
            database();
        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

        initializeVariables();
        btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();

            }
        });

    }

    public void openVoteResultActivity(View view) {
        startActivity(new Intent(this, VoteResult.class));
    }

    public void voteSubmit(String uid) {
        if (votingModels.size() > 0) {
            for (int i = 0; i < votingModels.size(); i++) {
                final DatabaseReference reference = firebaseDatabase.getReference().child("voteHistory").child(votingModels.get(i).getQuestuionID());
                String key = reference.push().getKey();
                final VoteModel voteModel = new VoteModel();
                voteModel.setVoteResult(votingModels.get(i).getSelectedOptionID());
                voteModel.setVoterID(uid);
                final int finalI = i;
                firebaseDatabase.getReference().child("voteHistory").child(votingModels.get(i).getQuestuionID()).child(key).setValue(voteModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SurveyUserActivity.this, "Vote done successfully", Toast.LENGTH_SHORT).show();
                        firebaseDatabase.getReference().child("vote_count").child(votingModels.get(finalI).getQuestuionID()).child(votingModels.get(finalI).getSelectedOptionID()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot != null && dataSnapshot.exists()) {
                                    // Toast.makeText(SurveyUserActivity.this, dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                                    int oldCount = Integer.parseInt(dataSnapshot.getValue().toString());
                                    oldCount++;
                                    firebaseDatabase.getReference().child("vote_count").child(votingModels.get(finalI).getQuestuionID()).child(votingModels.get(finalI).getSelectedOptionID()).setValue(oldCount).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                        }
                                    });
                                    Intent intent = new Intent(SurveyUserActivity.this, UserMainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);


                                } else {
                                    Toast.makeText(SurveyUserActivity.this, "Null", Toast.LENGTH_SHORT).show();
                                    firebaseDatabase.getReference().child("vote_count").child(votingModels.get(finalI).getQuestuionID()).child(votingModels.get(finalI).getSelectedOptionID()).setValue(1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    });

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                });

                firebaseDatabase.getReference().child("vote_count").child(votingModels.get(finalI)
                        .getQuestuionID()).child(votingModels.get(finalI).getNonSelectedID1())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot != null && dataSnapshot.exists()) {
                                    Toast.makeText(SurveyUserActivity.this, dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                                    int oldCount = Integer.parseInt(dataSnapshot.getValue().toString());

                                    Toast.makeText(SurveyUserActivity.this, "Survey Submited ", Toast.LENGTH_SHORT).show();
                                    onBackPressed();

                                } else {
                                    Toast.makeText(SurveyUserActivity.this, "Null", Toast.LENGTH_SHORT).show();
                                    firebaseDatabase.getReference().child("vote_count").child(votingModels.get(finalI).getQuestuionID()).child(votingModels.get(finalI).getNonSelectedID1()).setValue(0).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(SurveyUserActivity.this, "Survey Submited ", Toast.LENGTH_SHORT).show();
                                            onBackPressed();
                                        }
                                    });

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                firebaseDatabase.getReference().child("vote_count").child(votingModels.get(finalI)
                        .getQuestuionID()).child(votingModels.get(finalI).getNonSelectedID2())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot != null && dataSnapshot.exists()) {
                                    Toast.makeText(SurveyUserActivity.this, dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                                    int oldCount = Integer.parseInt(dataSnapshot.getValue().toString());

                                    Toast.makeText(SurveyUserActivity.this, "Survey Submited ", Toast.LENGTH_SHORT).show();
                                    onBackPressed();

                                } else {
                                    Toast.makeText(SurveyUserActivity.this, "Null", Toast.LENGTH_SHORT).show();
                                    firebaseDatabase.getReference().child("vote_count").child(votingModels.get(finalI).getQuestuionID()).child(votingModels.get(finalI).getNonSelectedID1()).setValue(0).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(SurveyUserActivity.this, "Survey Submited ", Toast.LENGTH_SHORT).show();
                                            onBackPressed();
                                        }
                                    });

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                firebaseDatabase.getReference().child("vote_count").child(votingModels.get(finalI)
                        .getQuestuionID()).child(votingModels.get(finalI).getNonSelectedID3())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot != null && dataSnapshot.exists()) {
                                    Toast.makeText(SurveyUserActivity.this, dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                                    int oldCount = Integer.parseInt(dataSnapshot.getValue().toString());

                                    Toast.makeText(SurveyUserActivity.this, "Survey Submited ", Toast.LENGTH_SHORT).show();
                                    onBackPressed();

                                } else {
                                    Toast.makeText(SurveyUserActivity.this, "Null", Toast.LENGTH_SHORT).show();
                                    firebaseDatabase.getReference().child("vote_count").child(votingModels.get(finalI).getQuestuionID()).child(votingModels.get(finalI).getNonSelectedID3()).setValue(0).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(SurveyUserActivity.this, "Survey Submited ", Toast.LENGTH_SHORT).show();
                                            onBackPressed();
                                        }
                                    });

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                //  firebaseDatabase.getReference().child("vote_count").child(votingModels.get(finalI).getSelectedOptionID()).setValue("1");


            }
        }

    }

    public void showDialog() {
        // Create custom dialog object
        final Dialog dialog = new Dialog(SurveyUserActivity.this);
        // Include dialog.xml file
        dialog.setContentView(R.layout.user_content_main);
        // Set dialog title
        dialog.setTitle("Submit Your Answers");

        // set values for custom dialog components - text, image and button
        final EditText etUserId = dialog.findViewById(R.id.etUserId);
        final EditText etPassword = dialog.findViewById(R.id.etPassword);
        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(etUserId.getText().toString())) {
                    etUserId.setError(getResources().getString(R.string.field_null_error));

                } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
                    etPassword.setError(getResources().getString(R.string.field_null_error));
                } else {
                    showAlert(etPassword.getText().toString());
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

    public void showAlert(final String id) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you Sure?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        voteSubmit(id);


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

    public void database() {
        votingModels.clear();
        firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference reference = firebaseDatabase.getReference().child(Constant.SURVEY).child(Constant.SURVEY_LIST).child(CURRENTLY_SHOWING_SURVEY_ID).child("options");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                models.clear();
                //Toast.makeText(SurveyUserActivity.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();

                for (DataSnapshot tempDataSnapShot : dataSnapshot.getChildren()) {
                    ServayQuestionModel foodItem = tempDataSnapShot.getValue(ServayQuestionModel.class);

                    if (foodItem != null) {
                        models.add(foodItem);


                    }
                }
                if (models.size() > 0) {
                    for (int i = 0; i < models.size(); i++) {
                        votingModels.add(new VotingModel("Empty",
                                models.get(i).getOption1().getValue(),
                                models.get(i).getOption1().getKey(),
                                models.get(i).getOption1().getValue(),
                                models.get(i).getOption2().getValue(), models.get(i).getOption3().getValue(),
                                models.get(i).getOption4().getValue(),
                                models.get(i).getQuestionKey(),
                                models.get(i).getQuestion(),
                                models.get(i).getOptionType(),
                                models.get(i).getOption1().getKey(),
                                models.get(i).getOption2().getKey(),
                                models.get(i).getOption3().getKey(),
                                models.get(i).getOption4().getKey(),models.get(i).getOption2().getKey(),
                                models.get(i).getOption3().getKey(),
                                models.get(i).getOption4().getKey()));
                    }

                    viewItemsAdapter.notifyDataSetChanged();
                    // Toast.makeText(SurveyUserActivity.this, "" + votingModels.size() + " questions found for user", Toast.LENGTH_LONG).show();

                } else {
                    //   Toast.makeText(SurveyUserActivity.this, "No data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });


    }

    private void initializeVariables() {
        viewItemsAdapter = new ViewItemsQuestionAdapter(SurveyUserActivity.this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setStackFromEnd(true);
        mLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(viewItemsAdapter);
    }


}
