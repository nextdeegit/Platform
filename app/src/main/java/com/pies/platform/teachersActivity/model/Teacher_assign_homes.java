package com.pies.platform.teachersActivity.model;

import com.pies.platform.viewHolder.Teacher_Assigned_Home;

/**
 * Created by Nsikak  Thompson on 8/8/2016.
 */
public class Teacher_assign_homes {
    public String header,home_name,home_address,home_kilo;


    public Teacher_assign_homes(){

    }
    public Teacher_assign_homes(String header, String home_name, String home_address, String home_kilo) {
        this.header = header;
        this.home_name = home_name;
        this.home_address = home_address;
        this.home_kilo = home_kilo;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getHome_name() {
        return home_name;
    }

    public void setHome_name(String home_name) {
        this.home_name = home_name;
    }

    public String getHome_address() {
        return home_address;
    }

    public void setHome_address(String home_address) {
        this.home_address = home_address;
    }

    public String getHome_kilo() {
        return home_kilo;
    }

    public void setHome_kilo(String home_kilo) {
        this.home_kilo = home_kilo;
    }
}
