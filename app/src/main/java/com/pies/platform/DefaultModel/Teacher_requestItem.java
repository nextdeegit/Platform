package com.pies.platform.DefaultModel;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nsikak  Thompson on 9/9/2016.
 */
public class Teacher_requestItem {
    public String pName,pEmail,pNumber,pCity,pSubjects,pComment;

    public Teacher_requestItem(String pName, String pEmail, String pNumber, String pCity, String pSubjects, String pComment) {
        this.pName = pName;
        this.pEmail = pEmail;
        this.pNumber = pNumber;
        this.pCity = pCity;
        this.pSubjects = pSubjects;
        this.pComment = pComment;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpEmail() {
        return pEmail;
    }

    public void setpEmail(String pEmail) {
        this.pEmail = pEmail;
    }

    public String getpNumber() {
        return pNumber;
    }

    public void setpNumber(String pNumber) {
        this.pNumber = pNumber;
    }

    public String getpCity() {
        return pCity;
    }

    public void setpCity(String pCity) {
        this.pCity = pCity;
    }

    public String getpSubjects() {
        return pSubjects;
    }

    public void setpSubjects(String pSubjects) {
        this.pSubjects = pSubjects;
    }

    public String getpComment() {
        return pComment;
    }

    public void setpComment(String pComment) {
        this.pComment = pComment;
    }
    // [START post_to_map]
    @Exclude
    public Map<String, Object> toRequest() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("parent Name", pName);
        result.put("parent Email", pEmail);
        result.put("parent Phone", pNumber);
        result.put("parent City", pCity);
        result.put("parent Subject", pSubjects);
        result.put("parent Comment", pComment);






        return result;
    }
}
