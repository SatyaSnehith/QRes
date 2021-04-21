package com.p04.qres.utils;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;

public class CryptUtil {
    public static String decryptBase64(String base64) throws UnsupportedEncodingException {
        return new String(Base64.decode(base64, Base64.DEFAULT), "UTF-8");
    }

    public static String encryptBase64(String base64) throws UnsupportedEncodingException {
        return new String(Base64.encode(base64.getBytes(), Base64.DEFAULT), "UTF-8");
    }


    private static int getAlphabetIndex(char code) {
        if (code >= 97 && code <= 122) {
            return code - 97;
        } else if (code >= 65 && code <= 90) {
            return code - 65;
        }
        return -1;
    }

    private static char subToChar(char code, int num) {
        if (code >= 97 && code <= 122) {
            code -= num;
            if (code < 97) {
                code += 26;
            }
            return code;
        } else if (code >= 65 && code <= 90) {
            code -= num;
            if (code < 65) {
                code += 26;
            }
            return code;
        } else {
            return code;
        }
    }

    private static String cleanKey(String key) {
        int keyLen = key.length();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < keyLen; ++i) {
            char c = key.charAt(i);
            if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String withPassword(String text, String key) throws UnsupportedEncodingException  {
        key = cleanKey(encryptBase64(key));
        Log.i("TAG", "text: " + text);
        Log.i("TAG", "key: " + key);
        StringBuilder result = new StringBuilder();
        int textLen = text.length();
        int keyLen = key.length();
        for (int i = 0; i < textLen; ++i) {
            result.append(subToChar(text.charAt(i), getAlphabetIndex(key.charAt(i % keyLen))));
        }
        Log.i("TAG", "withPassword: " + result.toString());
        return decryptBase64(result.toString());
    }
}
