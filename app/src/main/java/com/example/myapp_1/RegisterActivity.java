package com.example.myapp_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button register_user;
    private EditText email_adress, phone, password, full_name;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        register_user = (Button)findViewById(R.id.register);
        register_user.setOnClickListener(this);

        email_adress = (EditText)findViewById(R.id.editTextTextEmail);
        phone = (EditText)findViewById(R.id.editTextPhone);
        password = (EditText)findViewById(R.id.editTextPassword);
        full_name = (EditText)findViewById(R.id.PersonName);

        progressBar = (ProgressBar)findViewById(R.id.progress);

    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.register){
            registerUser();
        }
    }


    private void registerUser(){
        String str_email = email_adress.getText().toString().trim();
        String str_password = password.getText().toString().trim();
        String str_phone = phone.getText().toString().trim();
        String str_full_name = full_name.getText().toString().trim();

        //---------------CHECK INPUT-----------------
        if(str_email.isEmpty()){
            email_adress.setError("Invalid Email!");
            email_adress.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()){
            email_adress.setError("Invalid Email!");
            email_adress.requestFocus();
            return;
        }

        if(str_password.length() < 6){
            password.setError("Need at Least 6 Characters!");
            password.requestFocus();
            return;
        }

        if(str_full_name.isEmpty()){
            password.setError("Please Write Your Full Name!");
            password.requestFocus();
            return;
        }

        if(str_phone.isEmpty()){
            email_adress.setError("Please Write Your Phone Number!");
            email_adress.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(str_email, str_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    User user = new User(str_phone, str_password, str_email, str_full_name); /* to-do : add name */
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(RegisterActivity.this, "pass successfully, verify in your email!", Toast.LENGTH_LONG).show();
                                        }
                                        else{
                                            Toast.makeText(RegisterActivity.this, "not pass!", Toast.LENGTH_LONG).show();

                                        }
                                    }
                                });
                                FirebaseUser user_check = FirebaseAuth.getInstance().getCurrentUser();
                                    progressBar.setVisibility(View.GONE);
                                    Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                               //     i.putExtra("user", user);
                                    startActivity(i);
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, "The proccess1 not pass successfuly!!",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);

                            }
                        }
                    });

                }
                else{
                    Toast.makeText(RegisterActivity.this, "Failed to Register, please check your credentials!!",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }
}