package com.example.myapp_1;

import java.io.Serializable;

public class User implements Serializable{
    public String phone, password, email, name;

    public User(){ }

    public User(String phone, String password,String email, String name){
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.name = name;

    }

}
