package com.pies.platform.admin.model;

import com.google.firebase.database.Exclude;
import com.pies.platform.admin.Add_Home;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nsikak  Thompson on 7/29/2016.
 */
public class Add_Home_item {

 public   double latitude;
  public double longitude;
   public String name;
    String address;

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    String keyId;
    public Add_Home_item(){

    }

    public Add_Home_item(double latitude, double longitude, String name, String address, String phone,String keyId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.keyId = keyId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    String phone;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toAddHome() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("address", address);
        result.put("phone", phone);
        result.put("latitude", latitude);
        result.put("longitude", longitude);




        return result;
    }
    // [END post_to_map]
}
