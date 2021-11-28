package com.example.myapp_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Manager extends AppCompatActivity implements View.OnClickListener {
    private User user;
    private TextView hello;
    private Button post, users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        hello = (TextView) findViewById(R.id.hello_text1);
        post = (Button) findViewById(R.id.button);
        post.setOnClickListener(this);
        users = (Button) findViewById(R.id.button2);
        users.setOnClickListener(this);

        // get the user data
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.user = (User)extras.getSerializable("us");
        }

        // set text to say hello to the user
        hello.setText("Hello " + user.getName());
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.button:
            startActivity(new Intent(Manager.this, managerPost.class));
            break;
        case R.id.button2:
            startActivity(new Intent(Manager.this, userlist.class));
            break;
    }
    }
}