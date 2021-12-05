package com.example.myapp_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import DateBaseConnection.PostsDataBaseConnection;
import Intrfaces.SearchCaller;
import utils.Post;
import utils.SearchFields;
import utils.User;

public class UserProfile extends AppCompatActivity implements View.OnClickListener, SearchCaller {

    private User user;
    private Button search, publish, delete, LogOut;
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
        this.search = (Button) findViewById(R.id.searchButtonF);
        this.publish = (Button) findViewById(R.id.publish);
        this.delete = (Button) findViewById(R.id.delete);
        this.LogOut = (Button) findViewById(R.id.logout);
        this.helloText = (EditText) findViewById(R.id.hello_text);
    }

    /**
     * set all button listeners
     */
    private void SetButtonsListeners(){
        this.search.setOnClickListener(this);
        this.publish.setOnClickListener(this);
        this.delete.setOnClickListener(this);
        this.LogOut.setOnClickListener(this);
    }


    /**
     * click listener for buttons
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.searchButtonF:
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
        }
    }


    private void SearchButtonFunctionality(){
        startActivity(new Intent(UserProfile.this, Search.class));
    }
    private void publishButtonFunctionality(){
        Intent i = new Intent(UserProfile.this, Publish.class);
        i.putExtra("user", user);
        startActivity(i);
    }
    private void deleteButtonFunctionality(){
        Map<SearchFields, String> forSearch = Collections.singletonMap(SearchFields.EMAIL, user.getEmail());
        PostsDataBaseConnection.search(this, forSearch);
    }

    private void log_out(){
        Intent intent = new Intent(UserProfile.this, MainActivity.class);
        startActivity(intent);
        finish();  // This call is missing.
    }

    @Override
    public void gotSearchResults(ArrayList<Post> posts) {
        Intent i = new Intent(UserProfile.this, com.example.myapp_1.delete.class);
        i.putExtra("postsList", posts);
        startActivity(i);
    }
}