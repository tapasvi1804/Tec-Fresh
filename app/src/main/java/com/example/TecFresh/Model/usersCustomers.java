package com.example.TecFresh.Model;

public class usersCustomers {

    private String name, phone, password, email;

    public usersCustomers()
    {
    }

    public usersCustomers(String name, String phone, String password, String email) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
