package com.oanabalaita.oana_maria.erasmuscom2.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oana-Maria on 27/06/2017.
 */

public class Post {


    public int pid;
    public String postOwnerEmail;
    public String postOwnerUid;
    public String description;
    public String location;
    public String date;
    public String postOwner;
    public int likesNumber;
    public Map<String, Boolean> likes = new HashMap<>();
    public String postUid;
    private String email;
    private String imgUrl;
    public Post() {

    }
    public Post(int pid, String postOwner, String description, String location, String date) {

        this.pid = pid;
        this.description = description;
        this.location = location;
        this.date = date;
        this.postOwner = postOwner;
        this.date = date;


    }
    public Post(String postUid, String description, String location) {

        this.postUid = postUid;
        this.description = description;
        this.location = location;


    }

    public String getPostOwnerEmail() {
        return postOwnerEmail;
    }

    public void setPostOwnerEmail(String postOwnerEmail) {
        this.postOwnerEmail = postOwnerEmail;
    }

    public String getPostUid() {
        return postUid;
    }

    public void setPostUid(String postUid) {
        this.postUid = postUid;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("pid", pid);
        result.put("postOwner", postOwner);
        result.put("description", description);
        result.put("location", location);
        result.put("likesNumeber", likesNumber);
        result.put("likes", likes);
        result.put("date", date);

        return result;
    }


    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPostOwner() {
        return postOwner;
    }

    public void setPostOwner(String postOwner) {
        this.postOwner = postOwner;
    }

    public int getLikesNumber() {
        return likesNumber;
    }

    public void setLikesNumber(int likesNumber) {
        this.likesNumber = likesNumber;
    }

    public Map<String, Boolean> getLikes() {
        return likes;
    }

    public void setLikes(Map<String, Boolean> likes) {
        this.likes = likes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPostOwnerUid() {
        return postOwnerUid;
    }

    public void setPostOwnerUid(String postOwnerUid) {
        this.postOwnerUid = postOwnerUid;
    }
}
