package com.oanabalaita.oana_maria.erasmuscom2;

/**
 * Created by Oana-Maria on 15/07/2017.
 */

public class Category {

    private int pic;
    private  String name;


    public Category(int pic, String name) {
        this.pic = pic;
        this.name = name;
    }

    public Category (){}

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
