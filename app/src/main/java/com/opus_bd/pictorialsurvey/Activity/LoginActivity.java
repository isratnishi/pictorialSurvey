package com.opus_bd.pictorialsurvey.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.opus_bd.pictorialsurvey.Activity.Admin.AdminHomeActivity;
import com.opus_bd.pictorialsurvey.Activity.Admin.MainActivity;
import com.opus_bd.pictorialsurvey.Activity.User.UserMainActivity;
import com.opus_bd.pictorialsurvey.R;

public class LoginActivity extends AppCompatActivity {
Button buttonUser,buttonAdmin;
ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonAdmin=findViewById(R.id.buttonAdmin);
        buttonUser=findViewById(R.id.buttonUser);
        image=findViewById(R.id.image);

        Glide.with(this).load(R.drawable.common_full_open_on_phone).into(image);


        buttonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        buttonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, UserMainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void showDialog() {
        // Create custom dialog object
        final Dialog dialog = new Dialog(LoginActivity.this);
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
                    Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
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
}
