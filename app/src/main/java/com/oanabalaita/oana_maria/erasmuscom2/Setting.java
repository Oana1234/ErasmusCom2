package com.oanabalaita.oana_maria.erasmuscom2;

/**
 * Created by Oana-Maria on 14/07/2017.
 */

public class Setting {

    private String title, description;

    public Setting(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Setting(){};

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
