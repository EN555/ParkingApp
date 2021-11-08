package com.example.myapp_1;

import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class UsersDataBaseConnection {

    private static final FirebaseAuth DataBaseAuth = FirebaseAuth.getInstance();
    private static final FirebaseDatabase DataBase = FirebaseDatabase.getInstance();

    private UsersDataBaseConnection(){ }    // disable the default constructor

    /**
     * register a new user to the app
     * @param user - new user to register
     * @return true is the process was successful, and false is not
     */
    public static boolean AddUser(User user){

        final boolean[] b = {true};

        // add user to the data base
        DataBaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){    // if the upload was successful

                    // upload all the data of the user
                    DataBase.getReference("Users").child(DataBaseAuth.getCurrentUser().getUid()).
                            setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){    // if the upload was successful, send verification email
                                DataBaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(!task.isSuccessful()){ b[0] = false;}    // if the email did nao send
                                    }
                                });
                            }
                            else{
                                b[0] = false;   // couldn't upload the data
                            }
                        }
                    });
                }
                else{
                    b[0] = false;   // if couldn't add user
                }
            }
        });

        return b[0];
    }






}
