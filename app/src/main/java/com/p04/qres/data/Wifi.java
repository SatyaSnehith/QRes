package com.p04.qres.data;

public class Wifi extends Text {
    public static final String WEP = "WEP";
    public static final String WPA = "WPA";
    private String ssid, password;
    private String type;

    public Wifi(String value) {
        super(value);
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
