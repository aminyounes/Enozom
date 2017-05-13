package com.example.owner.enozom;

/**
 * Created by owner on 5/12/2017.
 */

public class ListItem {

    private String name;
    private String desc;
    private String imageUrl;

    public ListItem(String name, String desc , String imageUrl) {
        this.name = name;
        this.desc = desc;
        this.imageUrl = imageUrl.replace("http","https");
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }



    public String getDesc() {
        return desc;
    }
}
