package com.example.myapp_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import Intrfaces.SearchCaller;
import utils.Post;

public class Search extends AppCompatActivity implements SearchCaller {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    public void gotSearchResults(ArrayList<Post> posts) {
        Intent i = new Intent(Search.this, SearchResults.class);
        i.putExtra("postsList", posts);
        startActivity(i);
    }
}