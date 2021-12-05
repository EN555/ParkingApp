package com.example.myapp_1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.primitives.Ints;

import java.util.ArrayList;

import utils.InputChecks;
import utils.Post;

public class SearchResults extends AppCompatActivity implements View.OnClickListener {

    TextView city, street, houseNum, from, to, price, phoneNum;
    ImageView photo;
    Button next, prev, spam;

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
        this.spam = findViewById(R.id.reportspam);
    }


    /**
     * set all button listeners
     */
    private void SetButtonsListeners(){
        next.setOnClickListener(this);
        prev.setOnClickListener(this);
        spam.setOnClickListener(this);
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
     *  send manger mail for post spam
     */
    private void spamRep(){

        final Dialog dialog = new Dialog(SearchResults.this);
        dialog.setContentView(R.layout.activity_email_pass_dialog);

        // adapt dialog window to screen size
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.70);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.40);

        dialog.getWindow().setLayout(width, height);
        final EditText email_sender = dialog.findViewById(R.id.email);
        final EditText password_sender = dialog.findViewById(R.id.password);
        Button submitButton = dialog.findViewById(R.id.report);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em_send = email_sender.getText().toString();
                // email validation
                if(!InputChecks.CheckValidEMail(em_send)){
                    email_sender.getText().clear();
                    email_sender.setHint("Invalid email!");
                    return;
                }
                // password validation
                String pass_send = password_sender.getText().toString();
                if(!InputChecks.CheckValidPassword(pass_send)){
                    password_sender.getText().clear();
                    password_sender.setHint("Invalid password!");
                    return;
                }
                // send the message
                        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
        GMailSender sender = new GMailSender(em_send, pass_send);
            sender.sendMail("EmailSender App",
                    "This post suspect as spam,\nHis Details:" +"\nCity: " + city.getText().toString() + "\nStreet: " + street.getText().toString() + "\nHouse Number: " + houseNum.getText().toString() + "\nPrice: " + price.getText().toString() + "\nPhone Number: " + phoneNum.getText().toString(),
                    em_send,
                    "eviatarn@gmail.com");
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();}
            }
        });
        sender.start();
                dialog.dismiss();
            }
        });

        dialog.show();
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


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.Next:
                nextPost();
                break;
            case  R.id.Prev:
                prevPost();
                break;
            case  R.id.reportspam:
                spamRep();
                break;
        }

    }
}