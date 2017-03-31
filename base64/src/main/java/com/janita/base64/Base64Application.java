package com.janita.base64;

import com.janita.base64.util.Base64Util;

/**
 * Created by Janita on 2017-03-31 17:38
 */
public class Base64Application {

    public static void main(String[] args) throws Exception {
        String msg = "大家好";
        String encodeStr = base64Encode(msg);
        System.out.println("*******"+encodeStr);
        System.out.println("*******"+base64Decode(encodeStr));
    }

    private static String base64Encode(String msg) throws Exception {
       return Base64Util.encode(msg.getBytes());
    }

    private static String base64Decode(String encodeStr) throws Exception {
        byte[] b = Base64Util.decode(encodeStr);
        return new String(b);
    }
}
