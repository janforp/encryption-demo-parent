package com.janita.encryption.rsa;

import com.janita.encryption.rsa.encryption.RSA;
import com.janita.encryption.rsa.util.Base64Util;

import java.util.Map;

/**
 * Created by Janita on 2017-03-31 17:15
 */
public class EncryptionRSAApplication {


    private static String publicKey;
    private static String privateKey;

    static {
        try {
            Map<String, Object> keyMap = RSA.genKeyPair();
            publicKey = RSA.getPublicKey(keyMap);
            privateKey = RSA.getPrivateKey(keyMap);
            System.err.println("公钥: \n\r" + publicKey);
            System.err.println("私钥： \n\r" + privateKey);

//			Base64Util.decodeToFile(Base64Util.filePath("/home", "d://rsa", "publicKey.rsa"), publicKey);
//			Base64Util.decodeToFile(Base64Util.filePath("/home", "d://rsa", "privateKey.rsa"), privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        EncryptedByPublicKeyAndDecryptByPrivate();
        EncryptedByPrivateKeyAndDecryptByPublic();
    }

    /**
     * 公钥加密——私钥解密
     * @throws Exception
     */
    private static void EncryptedByPublicKeyAndDecryptByPrivate() throws Exception {
        String source = "静夜思-床前看月光，疑是地上霜。抬头望山月，低头思故乡。";
        byte[] data = source.getBytes();
        byte[] encodedData = RSA.encryptByPublicKey(data, publicKey);
        byte[] decodedData = RSA.decryptByPrivateKey(encodedData, privateKey);
        String target = new String(decodedData);
        System.out.println("解密后文字: \r\n" + target);
    }

    /**
     * 私钥加密——公钥解密
     * @throws Exception
     */
    private static void EncryptedByPrivateKeyAndDecryptByPublic() throws Exception {
        String source = "这是一行测试RSA数字签名的无意义文字";
        byte[] data = source.getBytes();
        byte[] encodedData = RSA.encryptByPrivateKey(data, privateKey);
        byte[] decodedData = RSA.decryptByPublicKey(encodedData, publicKey);
        String target = new String(decodedData);
        System.out.println("解密后: \r\n" + target);
        System.err.println("私钥签名——公钥验证签名");
        String sign = RSA.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);
        boolean status = RSA.verify(encodedData, publicKey, sign);
        System.err.println("验证结果:\r" + status);
    }
}
