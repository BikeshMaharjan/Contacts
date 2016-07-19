package com.example.bbk.contacts.com.example.bbk.custom;

/**
 * Created by BBK on 02/25/16.
 */
public class RankingDataModel {
    public String getPhonetype() {
        return phonetype;
    }

    public void setPhonetype(String phonetype) {
        this.phonetype = phonetype;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getPhoneimage() {
        return phoneimage;
    }

    public void setPhoneimage(int phoneimage) {
        this.phoneimage = phoneimage;
    }

    String phonetype, phonenumber;
    int phoneimage;
}
