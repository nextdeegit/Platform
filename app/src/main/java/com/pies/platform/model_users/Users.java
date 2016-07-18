package com.pies.platform.model_users;


import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nsikak  Thompson on 7/13/2016.
 */
public class Users {
    String name;
    String email;
    String password;
    String photoUrl ="";
    String uid;
    String userType;



    public Users(String uid, String name, String email, String password, String userType){
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.email = password;
        this.userType = userType;
    }


    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String home4;
    public Users(){

    }


    // [START post_to_map]
    @Exclude
    public Map<String, Object> toAdmin() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);
        result.put("email", email);
        result.put("photourl", photoUrl);
        result.put("userType", userType);



        return result;
    }
    // [END post_to_map]
}

