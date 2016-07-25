package com.pies.platform.admin.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nsikak  Thompson on 7/20/2016.
 */
public class imageData {
    String uid;
    String full_image_url;
    public  imageData(){

    }

    public imageData(String uid, String full_image_url, String thumb_url) {
        this.uid = uid;
        this.full_image_url = full_image_url;
        this.thumb_url = thumb_url;
    }

    String thumb_url;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFull_image_url() {
        return full_image_url;
    }

    public void setFull_image_url(String full_image_url) {
        this.full_image_url = full_image_url;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toAdminImage() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("full-image-url", full_image_url);
        result.put("thumb-url", thumb_url);




        return result;
    }
}
