package com.example.myapp_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserProfile extends AppCompatActivity implements View.OnClickListener{

    private User user;
    Button search, publish, delete;
    EditText helloText;

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
        helloText.setText("hello " + user.name);

    }

    /**
     * get all elements on the screen (Buttons, texts, ...) as objects of the class
     */
    private void GetElements(){
        this.search = (Button) findViewById(R.id.search);
        this.publish = (Button) findViewById(R.id.publish);
        this.delete = (Button) findViewById(R.id.delete);

        this.helloText = (EditText) findViewById(R.id.hello_text);
    }

    /**
     * set all button listeners
     */
    private void SetButtonsListeners(){
        this.search.setOnClickListener(this);
        this.publish.setOnClickListener(this);
        this.delete.setOnClickListener(this);
    }


    /**
     * click listener for buttons
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search:
                SearchButtonFunctionality();
                break;
            case R.id.publish:
                publishButtonFunctionality();
                break;
            case R.id.delete:
                deleteButtonFunctionality();
                break;
        }
    }


    private void SearchButtonFunctionality(){
        /* to-do : add functionality */
    }
    private void publishButtonFunctionality(){
        /* to-do : add functionality */
    }
    private void deleteButtonFunctionality(){
        /* to-do : add functionality */
    }


}