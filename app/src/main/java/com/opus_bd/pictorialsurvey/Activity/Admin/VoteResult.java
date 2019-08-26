package com.opus_bd.pictorialsurvey.Activity.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opus_bd.pictorialsurvey.Adapter.QuestionsLitsAdapter;
import com.opus_bd.pictorialsurvey.Model.Constant;
import com.opus_bd.pictorialsurvey.Model.QuestionAndVoteCount;
import com.opus_bd.pictorialsurvey.Model.ServayQuestionModel;
import com.opus_bd.pictorialsurvey.R;

import java.util.ArrayList;


import static com.opus_bd.pictorialsurvey.Data.shared_data.CURRENTLY_SHOWING_SURVEY;
import static com.opus_bd.pictorialsurvey.Data.shared_data.CURRENTLY_SHOWING_SURVEY_ID;

public class VoteResult extends AppCompatActivity {
    TextView tvName, tvDescription;
    RecyclerView Recyclerview;
    ArrayList<QuestionAndVoteCount> models = new ArrayList<>();
    QuestionsLitsAdapter myAdapter;
    String Option_1_vote_count = "0";
    String Option_2_vote_count = "0";
    TextView totalParticipate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_result);
        Recyclerview=findViewById(R.id.Recyclerview);
        totalParticipate=findViewById(R.id.totalParticipate);
        tvName = findViewById(R.id.tvName);
        tvDescription = findViewById(R.id.tvDescription);
        initRecycler();
        database();

        try {
            if (true) {
                tvName.setText(CURRENTLY_SHOWING_SURVEY.getSurveyName());
            }
            if (true) {
                tvDescription.setText(CURRENTLY_SHOWING_SURVEY.getDescription());
            }
        } catch (Exception e) {
        }

    }

    private void initRecycler() {
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        Recyclerview.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        myAdapter = new QuestionsLitsAdapter(models);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Recyclerview.getContext(),
                layoutManager.getOrientation());
      //  Recyclerview.addItemDecoration(dividerItemDecoration);
        Recyclerview.setAdapter(myAdapter);
    }

    public void database() {
        // Toast.makeText(this, "method worked", Toast.LENGTH_SHORT).show();

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference().child(Constant.SURVEY).child(Constant.SURVEY_LIST).child(CURRENTLY_SHOWING_SURVEY_ID).child("options");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              //  Toast.makeText(VoteResult.this, dataSnapshot.toString(), Toast.LENGTH_LONG).show();


                models.clear();
                //  Toast.makeText(VoteResult.this, "data downloaded", Toast.LENGTH_SHORT).show();

                try {
                    for (DataSnapshot tempDataSnapShot : dataSnapshot.getChildren()) {
                        final ServayQuestionModel foodItem = tempDataSnapShot.getValue(ServayQuestionModel.class);
                        Option_2_vote_count = "0";
                        Option_1_vote_count = "0";

                        if (foodItem != null) {
                            firebaseDatabase.getReference().child("vote_count").child(foodItem.getQuestionKey()).child(foodItem.getOption1().getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot_one) {
                                    if (dataSnapshot_one != null && dataSnapshot_one.exists()) {
                                        Option_1_vote_count = dataSnapshot_one.getValue().toString();

                                    } else {
                                        Option_1_vote_count = "0";
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            firebaseDatabase.getReference().child("vote_count").child(foodItem.getQuestionKey()).child(foodItem.getOption2().getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot_two) {
                                    if (dataSnapshot_two != null && dataSnapshot_two.exists()) {
                                        Option_2_vote_count = dataSnapshot_two.getValue().toString();
                                        int total_count = Integer.parseInt(Option_1_vote_count) + Integer.parseInt(Option_2_vote_count);
                                        int first = ((Integer.parseInt(Option_1_vote_count))* 100) / total_count;
                                        int second = ((Integer.parseInt(Option_2_vote_count))* 100) / total_count;

                                       try {
                                           totalParticipate.setText("Total Participate "+total_count);
                                       }
                                       catch (Exception e){}




                                        models.add(new QuestionAndVoteCount(foodItem.getQuestion(), foodItem.getQuestionKey(), foodItem.getOption1().getValue(), String.valueOf(Option_1_vote_count), foodItem.getOption2().getValue(), String.valueOf(Option_2_vote_count), first, second,foodItem.getOptionType()));
                                        myAdapter.notifyItemInserted(models.size() - 1);
                                    } else {
                                        Option_2_vote_count = "0";
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }

                    }

                    myAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Toast.makeText(VoteResult.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });


    }
}
