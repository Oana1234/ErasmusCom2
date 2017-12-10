package com.oanabalaita.oana_maria.erasmuscom2.models;

/**
 * Created by Oana-Maria on 10/06/2017.
 */

public class UserChat {


    public String uid;
    public String email;
    public String firebaseToken;
    public String nameAndSurname=null;
  //  public String nameAndSurname;
    public String erasmusCity=null;
    public String erasmusCountry=null;
    public String erasmusUniversity=null;
   // public String status=null;

    public UserChat() {
    }

    public UserChat(String uid, String email, String firebaseToken) {
        this.uid = uid;
        this.email = email;
        this.firebaseToken = firebaseToken;



    }

    public UserChat(String uid, String email, String firebaseToken,String erasmusCity, String erasmusCountry, String erasmusUniversity) {
        this.uid = uid;
        this.email = email;
        this.firebaseToken = firebaseToken;
        this.erasmusCity= erasmusCity;
        this.erasmusCountry = erasmusCountry;
        this.erasmusUniversity= erasmusUniversity;


    }
}
