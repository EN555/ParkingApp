package com.example.myapp_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button Register, Login, Forgot_Password;
    private EditText email, password;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.GetElements();
        this.SetButtonsListeners();

        mAuth = FirebaseAuth.getInstance();

    }

    /**
     * get all elements on the screen (Buttons, texts, ...) as objects of the class
     */
    private void GetElements(){
        Register = (Button)findViewById(R.id.register);
        Forgot_Password = (Button)findViewById(R.id.forgotpassword);
        Login = (Button)findViewById(R.id.login);
        email = (EditText)findViewById(R.id.editEmailAddress);
        password = (EditText)findViewById(R.id.editPassword);
        progressbar = (ProgressBar)findViewById(R.id.progressbar);
    }

    /**
     * set all button listeners
     */
    private void SetButtonsListeners() {
        Register.setOnClickListener(this);
        Forgot_Password.setOnClickListener(this);
        Login.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.login:
                loginButtonFunc();
                break;
            case R.id.forgotpassword:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }
    }

    /**
     * get email and password from screen, and try to log in
     */
    private void loginButtonFunc(){

        progressbar.setVisibility(View.VISIBLE);

        // get email and password from screen
        String str_email = email.getText().toString().trim();
        String str_password = password.getText().toString().trim();


        // check input
        if(!InputChecks.CheckValidEMail(str_email)){
            email.setError("Invalid Email!");
            email.requestFocus();
            return;
        }
        if(!InputChecks.CheckValidPassword(str_password)){
            password.setError("Invalid Password!");
            password.requestFocus();
            return;
        }

        // try to log in
        validation(str_email, str_password);
    }


    /**
     * try to log in to the app, with the provided email and password
     */
    private void validation(String email, String password){


        //--------------CONNECT TO THE SERVER------------------
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressbar.setVisibility(View.GONE);
                    if(mAuth.getCurrentUser().isEmailVerified()) {
                        Intent i = new Intent(MainActivity.this, UserProfile.class);
                        FirebaseDatabase data_base = FirebaseDatabase.getInstance();
                        DatabaseReference data_ref= data_base.getReference("Users/"+ mAuth.getInstance().getCurrentUser().getUid());
                        data_ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User us = snapshot.getValue(User.class);
                                i.putExtra("user", us);     /* to-do : pass user data */
                                startActivity(i);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    else{
                        Toast.makeText(MainActivity.this, "verify your email Again!", Toast.LENGTH_LONG).show();
                        progressbar.setVisibility(View.GONE);
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Please try Again!", Toast.LENGTH_LONG).show();
                    progressbar.setVisibility(View.GONE);
                }
            }
        });

    }

}