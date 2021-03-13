package com.p04.qres.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

public class DecryptUtil {
    public static String base64(String base64) throws UnsupportedEncodingException {
        return new String(Base64.decode(base64, Base64.DEFAULT), "UTF-8");
    }
}
