package com.example.TecFresh.Model;

public class modelProducts {
    private String pImg;
    private String pName;
    private String pPrice;

    public modelProducts(String pImg, String pName, String pPrice) {
        this.pImg = pImg;
        this.pName = pName;
        this.pPrice = pPrice;
    }

    public String getpImg() {
        return pImg;
    }

    public void setpImg(String pImg) {
        this.pImg = pImg;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }
}
