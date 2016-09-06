package com.pies.platform.model.managers.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nsikak  Thompson on 7/13/2016.
 */
public class managers_data {
   public String name;
    String email;
    String password;
    String photoUrl;
    String uid;
    String region;
    String userType;

    public managers_data(){

    }


    public managers_data(String uid, String name, String email, String password, String userType, String region){

    }


    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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


    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap2() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);
        result.put("email", email);
        result.put("photourl", photoUrl);
        result.put("userType", userType);
        result.put("region", region);



        return result;
    }
    // [END post_to_map]
}
