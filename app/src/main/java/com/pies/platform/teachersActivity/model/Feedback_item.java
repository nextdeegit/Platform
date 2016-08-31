package com.pies.platform.teachersActivity.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nsikak  Thompson on 8/15/2016.
 */
public class Feedback_item {

    String fb1,rm1,fb2,rm2,fb3,rm3,fb4,rm4,fb5,rm5,fb6,rm6,fb7,rm7,fb8,rm8;
    String author ="";
    String time;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    String day;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String date;

    public Feedback_item(){

    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Feedback_item(String day,String time,String date,String author,String fb1, String rm1, String fb2, String rm2, String fb3, String rm3, String fb4, String rm4, String fb5, String rm5, String fb6, String rm6, String fb7, String rm7, String fb8, String rm8) {
        this.fb1 = fb1;
        this.rm1 = rm1;
        this.fb2 = fb2;
        this.rm2 = rm2;
        this.fb3 = fb3;
        this.rm3 = rm3;
        this.fb4 = fb4;
        this.rm4 = rm4;
        this.fb5 = fb5;
        this.rm5 = rm5;
        this.fb6 = fb6;
        this.rm6 = rm6;
        this.fb7 = fb7;
        this.rm7 = rm7;
        this.fb8 = fb8;
        this.rm8 = rm8;
        this.author = author;
        this.time = time;
        this.date = date;
        this.day = day;
    }

    public String getFb1() {
        return fb1;
    }

    public void setFb1(String fb1) {
        this.fb1 = fb1;
    }

    public String getRm1() {
        return rm1;
    }

    public void setRm1(String rm1) {
        this.rm1 = rm1;
    }

    public String getFb2() {
        return fb2;
    }

    public void setFb2(String fb2) {
        this.fb2 = fb2;
    }

    public String getRm2() {
        return rm2;
    }

    public void setRm2(String rm2) {
        this.rm2 = rm2;
    }

    public String getFb3() {
        return fb3;
    }

    public void setFb3(String fb3) {
        this.fb3 = fb3;
    }

    public String getRm3() {
        return rm3;
    }

    public void setRm3(String rm3) {
        this.rm3 = rm3;
    }

    public String getFb4() {
        return fb4;
    }

    public void setFb4(String fb4) {
        this.fb4 = fb4;
    }

    public String getRm4() {
        return rm4;
    }

    public void setRm4(String rm4) {
        this.rm4 = rm4;
    }

    public String getFb5() {
        return fb5;
    }

    public void setFb5(String fb5) {
        this.fb5 = fb5;
    }

    public String getRm5() {
        return rm5;
    }

    public void setRm5(String rm5) {
        this.rm5 = rm5;
    }

    public String getFb6() {
        return fb6;
    }

    public void setFb6(String fb6) {
        this.fb6 = fb6;
    }

    public String getRm6() {
        return rm6;
    }

    public void setRm6(String rm6) {
        this.rm6 = rm6;
    }

    public String getFb7() {
        return fb7;
    }

    public void setFb7(String fb7) {
        this.fb7 = fb7;
    }

    public String getRm7() {
        return rm7;
    }

    public void setRm7(String rm7) {
        this.rm7 = rm7;
    }

    public String getFb8() {
        return fb8;
    }

    public void setFb8(String fb8) {
        this.fb8 = fb8;
    }

    public String getRm8() {
        return rm8;
    }

    public void setRm8(String rm8) {
        this.rm8 = rm8;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toFeedback() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("fb1", fb1);
        result.put("rm1", rm1);
        result.put("fb2", fb2);
        result.put("rm2", rm2);
        result.put("fb3", fb3);
        result.put("rm3", rm3);
        result.put("fb4", fb4);
        result.put("rm4", rm4);
        result.put("fb5", fb5);
        result.put("rm5", rm5);
        result.put("fb6", fb6);
        result.put("rm6", rm6);
        result.put("fb7", fb7);
        result.put("rm7", rm7);
        result.put("fb8", fb8);
        result.put("rm8", rm8);
        result.put("author",author);
        result.put("time", time);
        result.put("date", date);
        result.put("day",day);



        return result;
    }
}
