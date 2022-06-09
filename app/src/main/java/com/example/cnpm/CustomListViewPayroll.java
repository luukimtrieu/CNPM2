package com.example.cnpm;

public class CustomListViewPayroll {
    private String name;
    private String status;
    private String id;
    private String urlImage;

    public CustomListViewPayroll(String name, String id, String status, String url) {
        this.name = name;
        this.id = id;
        this.status = status;
        this.urlImage = url;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
