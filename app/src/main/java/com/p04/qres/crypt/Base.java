package com.p04.qres.crypt;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

public class Base {
    public static String encrypt(String text) throws UnsupportedEncodingException {
        return Base64.encodeToString(text.getBytes("UTF-8"), Base64.DEFAULT);
    }

    public static String decrypt(String base64) throws UnsupportedEncodingException {
        return new String(Base64.decode(base64, Base64.DEFAULT), "UTF-8");
    }
}
