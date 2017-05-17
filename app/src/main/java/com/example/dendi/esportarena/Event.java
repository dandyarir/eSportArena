package com.example.dendi.esportarena;

/**
 * Created by Dendi on 5/17/2017.
 */
public class Event {
    private int id;
    private String title;
    private String date;
    private String desc;
    private String link;
    private String location;
    private byte[] image;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Event(int id, String title, String date, String desc, String link, String location, byte[] image) {

        this.id = id;
        this.title = title;
        this.date = date;
        this.desc = desc;
        this.link = link;
        this.location = location;
        this.image = image;
    }

    public Event() {
    }
}

