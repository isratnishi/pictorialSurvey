package com.opus_bd.pictorialsurvey.Activity.User;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opus_bd.pictorialsurvey.Activity.Admin.AddNewSurveyActivity;
import com.opus_bd.pictorialsurvey.Activity.Admin.MainActivity;
import com.opus_bd.pictorialsurvey.Activity.Admin.SurveyActivity;
import com.opus_bd.pictorialsurvey.Activity.LoginActivity;
import com.opus_bd.pictorialsurvey.Adapter.ViewItemsAdapter;
import com.opus_bd.pictorialsurvey.Adapter.ViewItemsUserAdapter;
import com.opus_bd.pictorialsurvey.Model.Constant;
import com.opus_bd.pictorialsurvey.Model.Survey;
import com.opus_bd.pictorialsurvey.R;

import java.util.ArrayList;

public class UserMainActivity extends AppCompatActivity {
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference = mDatabase.getReference();


    private RecyclerView recyclerView;
    ViewItemsUserAdapter viewItemsAdapter;
    ArrayList<Survey> models = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        recyclerView = findViewById(R.id.Recyclerview);
        recyclerView.setHasFixedSize(true);
        initializeVariables();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference().child(Constant.SURVEY).child(Constant.SURVEY_LIST);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                models.clear();

                for (DataSnapshot tempDataSnapShot : dataSnapshot.getChildren()) {
                    Survey foodItem = tempDataSnapShot.getValue(Survey.class);
                    if (foodItem != null) {
                        if(foodItem.getSurveyCondition().equals("Open"))
                        {
                            models.add(foodItem);
                        }


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
        viewItemsAdapter = new ViewItemsUserAdapter(models, UserMainActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) mLayoutManager).setStackFromEnd(true);
        ((LinearLayoutManager) mLayoutManager).setReverseLayout(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(viewItemsAdapter);
    }

    public void showDialog() {
        // Create custom dialog object
        final Dialog dialog = new Dialog(UserMainActivity.this);
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
                    Intent intent = new Intent(UserMainActivity.this, AddNewSurveyActivity.class);
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

  /*  public void FabOnclick(View view) {
        showDialog();
    }*/

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        super.onBackPressed();
    }
}