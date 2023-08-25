package com.example.TecFresh.Model;

public class usersShopkeepers {

    private String Username, phone, password, shopID, Shopname;

    public usersShopkeepers(){

    }

    public usersShopkeepers(String username, String phone, String password, String shopID, String shopname) {
        Username = username;
        this.phone = phone;
        this.password = password;
        this.shopID = shopID;
        Shopname = shopname;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getShopname() {
        return Shopname;
    }

    public void setShopname(String shopname) {
        Shopname = shopname;
    }
}
