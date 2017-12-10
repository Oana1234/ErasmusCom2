package com.oanabalaita.oana_maria.erasmuscom2.models;

/**
 * Created by Oana-Maria on 16/07/2017.
 */

public class Tip {


    public int pidTip;
    public String tipOwnerEmail;
    public String tipOwnerUid;
    public String tipDescription;
    public String numberTip;
    public String tipOwner;
    public String tipUid;
    private String email;

    private String countryTip;
    private String cityTip;
    private String facultyTip;
    private String universityTip;


    public Tip() {


    }

    public Tip(int pidTip, String tipOwnerEmail, String tipOwnerUid, String tipDescription, String numberTip, String tipOwner, String tipUid, String email, String countryTip, String cityTip, String facultyTip, String universityTip) {
        this.pidTip = pidTip;
        this.tipOwnerEmail = tipOwnerEmail;
        this.tipOwnerUid = tipOwnerUid;
        this.tipDescription = tipDescription;
        this.numberTip= numberTip;
        this.tipOwner = tipOwner;
        this.tipUid = tipUid;
        this.email = email;
        this.countryTip = countryTip;
        this.cityTip = cityTip;
        this.facultyTip = facultyTip;
        this.universityTip = universityTip;
    }

    public Tip( String tipUid, String tipDescription)
    {

        this.tipUid = tipUid;
        this.tipDescription = tipDescription;

    }

    public Tip( String tipUid, String tipDescription, String countryTip)
    {

        this.tipUid = tipUid;
        this.tipDescription = tipDescription;
        this.countryTip = countryTip;

    }


    public int getPidTip() {
        return pidTip;
    }

    public void setPidTip(int pidTip) {
        this.pidTip = pidTip;
    }

    public String getTipOwnerEmail() {
        return tipOwnerEmail;
    }

    public void setTipOwnerEmail(String tipOwnerEmail) {
        this.tipOwnerEmail = tipOwnerEmail;
    }

    public String getTipOwnerUid() {
        return tipOwnerUid;
    }

    public void setTipOwnerUid(String tipOwnerUid) {
        this.tipOwnerUid = tipOwnerUid;
    }

    public String getTipDescription() {
        return tipDescription;
    }

    public void setTipDescription(String tipDescription) {
        this.tipDescription = tipDescription;
    }

    public String getNumberTip() {
        return numberTip;
    }

    public void setOrd(String numberTip) {
        this.numberTip = numberTip;
    }

    public String getTipOwner() {
        return tipOwner;
    }

    public void setTipOwner(String tipOwner) {
        this.tipOwner = tipOwner;
    }

    public String getTipUid() {
        return tipUid;
    }

    public void setTipUid(String tipUid) {
        this.tipUid = tipUid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountryTip() {
        return countryTip;
    }

    public void setCountryTip(String countryTip) {
        this.countryTip = countryTip;
    }

    public String getCityTip() {
        return cityTip;
    }

    public void setCityTip(String cityTip) {
        this.cityTip = cityTip;
    }

    public String getFacultyTip() {
        return facultyTip;
    }

    public void setFacultyTip(String facultyTip) {
        this.facultyTip = facultyTip;
    }

    public String getUniversityTip() {
        return universityTip;
    }

    public void setUniversityTip(String universityTip) {
        this.universityTip = universityTip;
    }
}
