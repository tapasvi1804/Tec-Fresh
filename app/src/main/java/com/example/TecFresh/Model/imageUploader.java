package com.example.TecFresh.Model;

public class imageUploader {
    private String imageUrl;

    public imageUploader(){}

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public imageUploader(String imageUrl){
        this.imageUrl = imageUrl;
    }
}
