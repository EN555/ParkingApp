package com.example.myapp_1;

import android.util.Patterns;

import java.io.Serializable;

import Exceptions.NotValidEmailException;
import Exceptions.NotValidNameException;
import Exceptions.NotValidPasswordException;
import Exceptions.NotValidPhoneNumberException;

public class User implements Serializable{
    private String phone, password, email, name;

    public User(){ }

    public User(String name, String email, String password, String phone){

        // check all details are valid
        if(!CheckValidEMail(email)){throw new NotValidEmailException();}
        if(!CheckValidPassword(password)){throw new NotValidPasswordException();}
        if(!CheckValidPhoneNumber(phone)){throw new NotValidPhoneNumberException();}
        if(!CheckValidName(name)){throw new NotValidNameException();}

        // set variables
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.name = name;
    }

    // getters
    public String getPhone(){return this.phone;}
    public String getPassword(){return this.password;}
    public String getEmail(){return this.email;}
    public String getName(){return this.name;}

    /**
     * check if the given Email address is valid
     * @param email
     * @return
     */
    private static boolean CheckValidEMail(String email){
        if(email == null || email.isEmpty()){return false;}

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * check if the given password is valid - a.k.a at list 6 characters long
     * @param password
     * @return
     */
    private static boolean CheckValidPassword(String password){
     if(password == null || password.isEmpty()){return false;}

     return password.length() >=6;
    }

    /**
     * check if the given phone number is valid
     * @param phoneNumber
     * @return
     */
    private static boolean CheckValidPhoneNumber(String phoneNumber){
        if(phoneNumber == null || phoneNumber.isEmpty()){return false;}

        return Patterns.PHONE.matcher(phoneNumber).matches();
    }

    /**
     * check if the given name is valid - a.k.a at list 2 characters long and doesn't contain a digit
     * @param name
     * @return
     */
    private static boolean CheckValidName(String name){
        if(name == null || name.isEmpty()){return false;}

        return name.length() >=2 && !name.matches(".*\\d.*");
    }


}



