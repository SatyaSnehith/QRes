package com.p04.qres.data;

public class Email extends Text {
    private String email;

    public Email(String value) {
        super(value);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
