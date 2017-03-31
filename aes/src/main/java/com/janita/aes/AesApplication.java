package com.janita.aes;

import com.janita.aes.util.AES;

/**
 * Created by Janita on 2017-03-31 17:55
 */
public class AesApplication {

    static AES aes = new AES();

    public static void main(String[] args){
        String msg = "大家好";
        String enc = aes.encryptAES(msg);
        System.out.println("*******"+enc);
        System.out.println("*******"+aes.decryptAES(enc));

    }
}
