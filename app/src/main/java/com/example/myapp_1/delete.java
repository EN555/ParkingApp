package com.example.myapp_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.primitives.Ints;

import java.util.ArrayList;

import DateBaseConnection.PostsDataBaseConnection;
import Intrfaces.PostRemover;
import utils.Post;

public class delete extends AppCompatActivity implements View.OnClickListener, PostRemover {

    TextView city, street, houseNum, from, to, price, phoneNum;
    ImageView photo;
    Button next, prev, delete;

    ArrayList<Post> posts;
    int currentPostIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

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
            startActivity(new Intent(com.example.myapp_1.delete.this, NoResults.class));
        }

    }


    /**
     * get all elements on the screen (Buttons, texts, ...) as objects of the class
     */
    private void GetElements(){
        this.city = findViewById(R.id.locationCityD);
        this.street = findViewById(R.id.locationStreetD);
        this.houseNum = findViewById(R.id.locationHouseNumD);
        this.from = findViewById(R.id.FromD);
        this.to = findViewById(R.id.ToD);
        this.price = findViewById(R.id.PriceD);
        this.phoneNum = findViewById(R.id.phoneNumD);

        this.photo = findViewById(R.id.PhotoD);

        this.next = (Button) findViewById(R.id.NextD);
        this.prev = (Button) findViewById(R.id.PrevD);
        this.delete = (Button) findViewById(R.id.DeleteD);
    }


    /**
     * set all button listeners
     */
    private void SetButtonsListeners(){

//        //DEBUG
//        System.out.println("next = " + next);
//        System.out.println("prev = " + prev);
//        System.out.println("delete = " + delete);
//
//        System.out.println("city = " + city);
//        System.out.println("photo = " + photo);
//        //END DEBUG

        this.next.setOnClickListener(this);
        this.prev.setOnClickListener(this);
        this.delete.setOnClickListener(this);
    }

    /**
     *  set the screen to the next post
     */
    private void nextPost(){
        if(currentPostIndex == posts.size()){return;}

        Post currentPost = posts.get(currentPostIndex);
        setViewToPost(currentPost);
        currentPostIndex++;
    }
    /**
     *  set the screen to the previous post
     */
    private void prevPost(){
        if(currentPostIndex <= 1){return;}

        currentPostIndex--;

        Post currentPost = posts.get(currentPostIndex - 1);
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
        to.setText(currentPost.getDateTo() + "  :  " +currentPost.getTimeTo());
        price.setText("" + currentPost.getPrice());
        phoneNum.setText(currentPost.getUser().getPhone());

        photo.setImageBitmap(Bitmap.createBitmap(Ints.toArray(currentPost.getPhoto()), currentPost.getPhotoW(),
                currentPost.getPhotoH(), Bitmap.Config.ARGB_8888));
    }

    /**
     * delete the current displayed post
     */
    private void DeletePost(){
        Post postToDelete = this.posts.get(currentPostIndex - 1);

        // if removing the first post
        if(currentPostIndex == 1){
            // if removed last post
            if(this.posts.size() == 1){
                startActivity(new Intent(delete.this , NoResults.class));
            }
            else {
                nextPost();
                this.posts.remove(postToDelete);
                currentPostIndex--;
            }
        }
        // if removing any other post
        else{
            currentPostIndex = 0;
            nextPost();
            this.posts.remove(postToDelete);
        }


        PostsDataBaseConnection.deletePost(this, postToDelete);

    }

    @Override
    public void postRemoved(boolean isSuccessful) {
        if(!isSuccessful){
            Toast.makeText(com.example.myapp_1.delete.this, "an error occurred!", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(com.example.myapp_1.delete.this, "post successfully removed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.NextD:
                nextPost();
                break;
            case  R.id.PrevD:
                prevPost();
                break;
            case R.id.DeleteD:
                DeletePost();
                break;
        }
    }

}