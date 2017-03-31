
package com.janita.aes.util;

import com.janita.base64.util.Base64Util;
import com.janita.md5.util.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES encrypt util
 * <p>
 * @author   hubin
 * @Date	 2014-5-8
 */
public class AES extends Encrypt {

	private static final Logger LOGGER = LoggerFactory.getLogger(AES.class);
	private static final String ALGORITHM = "AES";

	private String secret = "h2wmABdfM7i3K80mAS";
	private String encoding = "UTF-8";

	private SecretKeySpec secretKey;

	public AES() {
		setKey(secret,encoding);//secret key
	}

	public AES(String str) {
		setKey(str,encoding);//generate secret key
	}

	public SecretKey getSecretKey() {
		return secretKey;
	}

	/**
	 * generate KEY
	 */
	public void setKey(String strKey,String encoding) {
		try {
			byte[] bk = MD5.md5Raw(strKey.getBytes(encoding));
			this.secretKey = new SecretKeySpec(bk, ALGORITHM);
		} catch (Exception e) {
			LOGGER.error("Encrypt setKey is exception:", e.getMessage());
		}
	}

	/**
	 * @Description AES encrypt
	 * @param str
	 * @return
	 */
	public String encryptAES(String str) {
		byte[] encryptBytes = null;
		String encryptStr = null;
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
			encryptBytes = cipher.doFinal(str.getBytes());
			if (encryptBytes != null) {
				encryptStr = Base64Util.encryptBASE64(encryptBytes,encoding);
			}
		} catch (Exception e) {
			LOGGER.error("Encrypt encryptAES is exception:", e.getMessage());
		}
		return encryptStr;
	}

	/**
	 * @Description AES decrypt
	 * @param str
	 * @return
	 */
	public String decryptAES(String str) {
		byte[] decryptBytes = null;
		String decryptStr = null;
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
			byte[] scrBytes = Base64Util.decryptBASE64(str,encoding);
			decryptBytes = cipher.doFinal(scrBytes);
		} catch (Exception e) {
			LOGGER.error("Encrypt decryptAES is exception:", e.getMessage());
		}
		if (decryptBytes != null) {
			decryptStr = new String(decryptBytes);
		}
		return decryptStr;
	}

	/**
	 * AES encrypt
	 */
	@Override
	public String encrypt(String value, String key) throws Exception {
		return this.encryptAES(value);
	}

	/**
	 * AES decrypt
	 */
	@Override
	public String decrypt(String value, String key) throws Exception {
		return this.decryptAES(value);

	}

	/**
	 * test
	 */
	public static void main(String[] args) {
		String password = "100010\n1w#E#测试\nssAASASSC\n127.0.0.1\nlif123gsjkdsgvjxeh\n";
		AES en = new AES("lifgnfdfg216958134gsjkdsgvjxeh");
		String encryptPwd = en.encryptAES(password);
		System.out.println(encryptPwd);
		String decryptPwd = en.decryptAES(encryptPwd);
		System.out.println(decryptPwd);
	}
}