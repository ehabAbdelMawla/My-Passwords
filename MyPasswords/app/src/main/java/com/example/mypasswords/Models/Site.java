package com.example.mypasswords.Models;


public class Site {
    private int id;
    private String siteName;
    private String userName;
    private String pass;
    private byte[] img;

    public Site(int id, String siteName, String userName, String pass, byte[] img) {
        this.id = id;
        this.siteName = siteName;
        this.userName = userName;
        this.pass = pass;
        this.img = img;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
