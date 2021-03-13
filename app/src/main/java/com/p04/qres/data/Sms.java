package com.p04.qres.data;

public class Sms extends Text {
    private String number, message;

    public Sms(String value) {
        super(value);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
