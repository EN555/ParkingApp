package com.example.myapp_1;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class PostsDataBaseConnection {

    private static FirebaseAuth DataBaseAuth = FirebaseAuth.getInstance();
    private static FirebaseDatabase DataBase = FirebaseDatabase.getInstance();

    /**
     * upload a new post to the data base
     * @param post
     */
    public static void uploadPost(Post post) {
        DataBase.getReference("Posts").child("" + post.hashCode()).setValue(post);
    }

}

