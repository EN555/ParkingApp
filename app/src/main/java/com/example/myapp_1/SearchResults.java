package com.example.myapp_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import utils.Post;

public class SearchResults extends AppCompatActivity implements View.OnClickListener {

    TextView city, street, houseNum, from, to, price, phoneNum;
    ImageView photo;
    Button next, prev;

    ArrayList<Post> posts;
    int currentPostIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        GetElements();
        SetButtonsListeners();

        // get the posts list
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
           this.posts = (ArrayList<Post>)(extras.getSerializable("postsList"));
        }

        // set view to the first post
        if(posts.size() != 0) {
            this.currentPostIndex = 0;
            nextPost();
        }
        else{
            startActivity(new Intent(SearchResults.this, NoResults.class));
        }

    }


    /**
     * get all elements on the screen (Buttons, texts, ...) as objects of the class
     */
    private void GetElements(){
        this.city = findViewById(R.id.locationCity);
        this.street = findViewById(R.id.locationStreet);
        this.houseNum = findViewById(R.id.locationHouseNum);
        this.from = findViewById(R.id.From);
        this.to = findViewById(R.id.To);
        this.price = findViewById(R.id.Price);
        this.phoneNum = findViewById(R.id.phoneNum);

        this.photo = findViewById(R.id.Photo);

        this.next = findViewById(R.id.Next);
        this.prev = findViewById(R.id.Prev);
    }


    /**
     * set all button listeners
     */
    private void SetButtonsListeners(){
        next.setOnClickListener(this);
        prev.setOnClickListener(this);
    }

    /**
     *  set the screen to the next post
     */
    private void nextPost(){
        if(currentPostIndex == posts.size() + 1){return;}

        Post currentPost = posts.get(currentPostIndex);
        setViewToPost(currentPost);
        currentPostIndex++;
    }
    /**
     *  set the screen to the previous post
     */
    private void prevPost(){
        if(currentPostIndex <= 1){return;}

        currentPostIndex -= 2;

        Post currentPost = posts.get(currentPostIndex);
        setViewToPost(currentPost);
    }

    /**
     * set the text on the screen to the given Post
     * @param currentPost
     */
    private void setViewToPost(Post currentPost){
        city.setText(currentPost.getCity());
        street.setText(currentPost.getStreet());
        houseNum.setText(currentPost.getHouseNum());
        from.setText(currentPost.getDataFrom() + "  :  " +currentPost.getTimeFrom());
        to.setText(currentPost.getTimeTo() + "  :  " +currentPost.getTimeTo());
        price.setText("" + currentPost.getPrice());
        phoneNum.setText("" + currentPost.getPrice());

        photo.setImageBitmap(Bitmap.createBitmap(currentPost.getPhoto(), currentPost.getPhotoW(),
                currentPost.getPhotoH(), Bitmap.Config.ARGB_8888));
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.Next:
                break;
            case  R.id.Prev:
                break;
        }

    }
}