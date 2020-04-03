package com.trungdaniel.congdongmeme.model;

public class UploadImage {
    private String name;
    private String uriImage;

    public UploadImage(String name, String uriImage) {
        this.name = name;
        this.uriImage = uriImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUriImage() {
        return uriImage;
    }

    public void setUriImage(String uriImage) {
        this.uriImage = uriImage;
    }
}
