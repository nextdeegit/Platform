package com.pies.platform.teachersActivity.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nsikak  Thompson on 7/13/2016.
 */
public class teacher_data {
  public  String name;
    String email;
    String password;
    String photoUrl ="";
  public   String uid;
    String region;
    String userType;
  public  String home1 ="";
    public String home2 = "";
    public String home3 ="";
    public String home4 = "";

    public teacher_data(){

    }


    public teacher_data(String uid, String name, String email, String password, String userType, String region){
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.region = region;

    }

    public String getHome4() {
        return home4;
    }

    public void setHome4(String home4) {
        this.home4 = home4;
    }

    public String getHome3() {
        return home3;
    }

    public void setHome3(String home3) {
        this.home3 = home3;
    }

    public String getHome2() {
        return home2;
    }

    public void setHome2(String home2) {
        this.home2 = home2;
    }

    public String getHome1() {
        return home1;
    }

    public void setHome1(String home1) {
        this.home1 = home1;
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




    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap1() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);
        result.put("email", email);
        result.put("photourl", photoUrl);
        result.put("userType", userType);
        result.put("region", region);
        result.put("Home1", home1);
        result.put("Home2", home2);
        result.put("Home3", home3);
        result.put("Home4", home4);


        return result;
    }
    // [END post_to_map]
}
