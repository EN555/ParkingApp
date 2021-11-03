package com.example.myapp_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button register_user;
    private EditText email_adress, phone, password;
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

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(str_email, str_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    User user = new User(str_phone, str_password, str_email, "name"); /* to-do : add name */
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "The proccess pass successfuly!!",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                Intent i = new Intent(RegisterActivity.this, UserProfile.class);
                                i.putExtra("user", user);     /* to-do : pass user data */
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