package com.p04.qres.data;

public class Url extends Text {
    private String url;

    public Url(String value) {
        super(value);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
