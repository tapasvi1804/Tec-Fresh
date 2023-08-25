package com.example.TecFresh.Model;

public class modelCategory {

    private int imageResource;
    private String text1;

    public modelCategory(int imageResource, String text1) {
        this.imageResource = imageResource;
        this.text1 = text1;
    }
    public int getImageResource() {
        return imageResource;
    }
    public String getText1() {
        return text1;
    }
}
