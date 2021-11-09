package com.example.myapp_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//import com.example.myapp_1.databinding.ActivityManagerBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ManagerActivity extends AppCompatActivity implements  View.OnClickListener{
    private EditText email, password;
    private FirebaseAuth mAuth;
    private Button login, previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager3);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        previous = (Button) findViewById(R.id.previous);
        previous.setOnClickListener(this);
    }



    @SuppressLint("WrongConstant")
    public void validation() {
        String str_email = email.getText().toString().trim();
        String str_password = password.getText().toString().trim();

        Notification.Builder progressbar = null;
        progressbar.setVisibility(View.VISIBLE);

        //-------------- CHECK INPUT---------------------//
        if (str_email.isEmpty()) {
            email.setError("Invalid Email!");
            email.requestFocus();
            progressbar.setVisibility(View.GONE);
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
            email.setError("Invalid Email!");
            email.requestFocus();
            progressbar.setVisibility(View.GONE);
            return;
        }

        if (str_password.isEmpty()) {
            password.setError("Invalid Password!");
            password.requestFocus();
            progressbar.setVisibility(View.GONE);
            return;
        }
        if (str_email.toString() != "lironi06@gmail.com") {
            email.setError("Try again");
            email.requestFocus();
            progressbar.setVisibility(View.GONE);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.previous:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.login:
                validation();
                break;

        }
    }
}