package com.example.myapp_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
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
<<<<<<< HEAD
=======
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
>>>>>>> 8ec7d7f449d7f30876fd045e7d34e0f9b7b8a1cf

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button Register, Login, Forgot_Password;
    private EditText email, password;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Register = (Button)findViewById(R.id.register);
        Register.setOnClickListener(this);

        Forgot_Password = (Button)findViewById(R.id.forgotpassword);
        Forgot_Password.setOnClickListener(this);

        Login = (Button)findViewById(R.id.login);
        Login.setOnClickListener(this);

        email = (EditText)findViewById(R.id.editEmailAddress);
        password = (EditText)findViewById(R.id.editPassword);

        progressbar = (ProgressBar)findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.login:
                validation();
                break;
            case R.id.forgotpassword:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }
    }

    public void validation(){
        String str_email = email.getText().toString().trim();
        String str_password = password.getText().toString().trim();

        progressbar.setVisibility(View.VISIBLE);

        //-------------- CHECK INPUT---------------------//
        if(str_email.isEmpty()){
            email.setError("Invalid Email!");
            email.requestFocus();
            progressbar.setVisibility(View.GONE);
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()){
            email.setError("Invalid Email!");
            email.requestFocus();
            progressbar.setVisibility(View.GONE);
            return;
        }

        if(str_password.isEmpty()){
            password.setError("Invalid Password!");
            password.requestFocus();
            progressbar.setVisibility(View.GONE);
            return;
        }

        //--------------CONNECT TO THE SERVER------------------
        mAuth.signInWithEmailAndPassword(str_email, str_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressbar.setVisibility(View.GONE);
                    if(mAuth.getCurrentUser().isEmailVerified()) {
                        Intent i = new Intent(MainActivity.this, UserProfile.class);
<<<<<<< HEAD
                        i.putExtra("user", new User());     /* to-do : pass user data */
                        startActivity(i);
=======
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
>>>>>>> 8ec7d7f449d7f30876fd045e7d34e0f9b7b8a1cf
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