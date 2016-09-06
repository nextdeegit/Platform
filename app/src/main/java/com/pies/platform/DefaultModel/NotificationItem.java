package com.pies.platform.DefaultModel;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nsikak  Thompson on 9/2/2016.
 */
public class NotificationItem {
    public String title;
    public String message;

public  NotificationItem(){

}
    public NotificationItem(String title, String message) {
        this.title = title;
        this.message = message;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toSendNoti() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("message", message);





        return result;
    }
}
