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

import Exceptions.NotValidEmailException;
import Exceptions.NotValidNameException;
import Exceptions.NotValidPasswordException;
import Exceptions.NotValidPhoneNumberException;
import Intrfaces.UserAdder;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, UserAdder {

    private Button register_user;
    private EditText email_adress, phone, password, full_name;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        GetElements();
        SetButtonsListeners();


    }

    /**
     * get all elements on the screen (Buttons, texts, ...) as objects of the class
     */
    private void GetElements(){
        register_user = (Button)findViewById(R.id.register);

        email_adress = (EditText)findViewById(R.id.editTextTextEmail);
        phone = (EditText)findViewById(R.id.editTextPhone);
        password = (EditText)findViewById(R.id.editTextPassword);
        full_name = (EditText)findViewById(R.id.PersonName);

        progressBar = (ProgressBar)findViewById(R.id.progress);
    }

    /**
     * set all button listeners
     */

    private void SetButtonsListeners(){
        register_user.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.register){
            registerUser();
        }
    }

    /**
     * register a new user to the app
     */
    private void registerUser(){

        //get the new user's details
        String str_email = email_adress.getText().toString().trim();
        String str_password = password.getText().toString().trim();
        String str_phone = phone.getText().toString().trim();
        String str_full_name = full_name.getText().toString().trim();

        // create a user object, and send an error msg if a detail is wrong
        User user;
        try {
            user = new User(str_full_name, str_email, str_password,str_phone);
        }
        catch (NotValidNameException e){
            full_name.setError("Please Write Your Full Name!");
            full_name.requestFocus();
            return;
        }
        catch (NotValidEmailException e){
            email_adress.setError("Invalid Email!");
            email_adress.requestFocus();
            return;
        }
        catch (NotValidPasswordException e){
            password.setError("Password Must Be At Least 6 Characters!");
            password.requestFocus();
            return;
        }
        catch (NotValidPhoneNumberException e){
            phone.setError("Please Write Your Phone Number!");
            phone.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);

        // register the new user
        UsersDataBaseConnection.AddUser(this, user);

    }

    @Override
    public void userAdded(boolean isSuccessful) {
        progressBar.setVisibility(View.GONE);

        if(isSuccessful){
            Toast.makeText(RegisterActivity.this, "user register successfully", Toast.LENGTH_LONG).show();
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        }
        else {
            Toast.makeText(RegisterActivity.this, "failed to register", Toast.LENGTH_LONG).show();
        }
    }
}