package com.pies.platform.model.managers;

import android.graphics.drawable.Drawable;

/**
 * Created by Nsikak  Thompson on 7/5/2016.
 */
public class ManagersItem {
    String titleName;
    String number;


    String description;
    Drawable thumbNail;

    public ManagersItem(String titleName, String number,String description, Drawable thumbNail) {
        this.titleName = titleName;
        this.number = number;
        this.thumbNail = thumbNail;
        this.description = description;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitle(String titleName) {
        this.titleName = titleName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Drawable getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(Drawable thumbNail) {
        this.thumbNail = thumbNail;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
