package com.example.myapp_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import utils.User;

public class UserProfile extends AppCompatActivity implements View.OnClickListener{

    private User user;
    private Button search, publish, delete, LogOut, activity;
    private EditText helloText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // get the user data
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.user = (User)extras.getSerializable("user");
        }

        GetElements();
        SetButtonsListeners();

        // set text to say hello to the user
        helloText.setText("hello " + user.getName());

    }

    /**
     * get all elements on the screen (Buttons, texts, ...) as objects of the class
     */
    private void GetElements(){
        this.search = (Button) findViewById(R.id.searchButton);
        this.publish = (Button) findViewById(R.id.publish);
        this.delete = (Button) findViewById(R.id.delete);
        this.LogOut = (Button) findViewById(R.id.logout);
        this.helloText = (EditText) findViewById(R.id.hello_text);
        this.activity = (Button) findViewById(R.id.activity1);
    }

    /**
     * set all button listeners
     */
    private void SetButtonsListeners(){
        this.search.setOnClickListener(this);
        this.publish.setOnClickListener(this);
        this.delete.setOnClickListener(this);
        this.LogOut.setOnClickListener(this);
        this.activity.setOnClickListener(this);
    }


    /**
     * click listener for buttons
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.searchButton:
                SearchButtonFunctionality();
                break;
            case R.id.publish:
                publishButtonFunctionality();
                break;
            case R.id.delete:
                deleteButtonFunctionality();
                break;
            case R.id.logout:
                log_out();
                break;
            case R.id.activity1:
               // PostsDataBaseConnection.getAllPostsByCity("city");
                startActivity(new Intent(this, feed_activity.class));
                break;
        }
    }


    private void SearchButtonFunctionality(){
        /* TODO : add functionality */
    }
    private void publishButtonFunctionality(){
        Intent i = new Intent(UserProfile.this, Publish.class);
        i.putExtra("user", user);
        startActivity(i);
    }
    private void deleteButtonFunctionality(){
        /* TODO: add functionality */
    }

    private void log_out(){
        Intent intent = new Intent(UserProfile.this, MainActivity.class);
        startActivity(intent);
        finish();  // This call is missing.
    }

}