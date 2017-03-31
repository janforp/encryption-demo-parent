package com.janita.md5;

import com.janita.md5.util.MD5;

/**
 * Created by Janita on 2017-03-31 17:45
 */
public class Md5Application {

    public static void main(String[] args){

        String plainText = "大家好";
        String text = "大家好";

        System.out.println("*******"+ MD5.toMD5(plainText,"UTF-8"));
        System.out.println("*******"+ MD5.toMD5(text,"UTF-8"));
    }
}
