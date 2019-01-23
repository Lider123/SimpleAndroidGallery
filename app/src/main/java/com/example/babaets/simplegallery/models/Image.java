package com.example.babaets.simplegallery.models;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class Image {
    private UUID id;
    private URL url;
    private String title;

    public Image(String url, String title) {
        this.id = UUID.randomUUID();
        try {
            this.url = new URL(url);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.title = title;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }

    public URL getUrl() {
        return this.url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void setUrl(String url) {
        try {
            this.url = new URL(url);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
