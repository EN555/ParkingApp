package com.example.myapp_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class feed_activity extends AppCompatActivity {
    RecyclerView recyclerView;
    String s1[], s2[];
    int image[]={R.drawable.gif};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        recyclerView = findViewById(R.id.recycler);

        s1 = getResources().getStringArray(R.array.activty_post);
        s2 = getResources().getStringArray(R.array.description);

        MyAdaptor myAdaptor = new MyAdaptor(this, s1,s2,image);
        recyclerView.setAdapter(myAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}