/**
 * Copyright (c) 2011-2014, hubin (243194995@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.janita.md5.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.*;
import java.util.Map.Entry;

/**
 * MD5加密工具类
 * <p>
 * @author   hubin
 * @Date	 2014-5-9
 */
public class MD5 {
	private final static Logger logger = LoggerFactory.getLogger(MD5.class);

	/**
	 * @Description 字符串加密为MD5
	 * 				中文加密一致通用,必须转码处理：  
	 * 					plainText.getBytes("UTF-8")
	 * @param plainText
	 * 				需要加密的字符串
	 * @return
	 */
	public static String toMD5(String plainText,String encoding) {
		StringBuffer stringBuffer = new StringBuffer();
		try {
			stringBuffer.append(md5String(plainText.getBytes(encoding)));
		} catch (UnsupportedEncodingException e) {
			logger.error(" CipherHelper toMD5 exception:", e.toString());
		}
		return stringBuffer.toString();
	}

	/**
	 * MD5 参数签名生成算法
	 * @param params <String,String> params 请求参数集，所有参数必须已转换为字符串类型
	 * @param secret secret 签名密钥
	 * @return 签名
	 * @throws IOException
	 */
	public static String getSignature(HashMap<String, String> params, String secret,String encoding) throws IOException {
		Map<String, String> sortedParams = new TreeMap<String, String>(params);
		Set<Entry<String, String>> entries = sortedParams.entrySet();
		StringBuilder baseString = new StringBuilder();
		for (Entry<String, String> param : entries) {
			baseString.append(param.getKey()).append("=").append(param.getValue());
		}
		baseString.append(secret);
		byte[] bytes = md5Raw(baseString.toString().getBytes(encoding));
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex);
		}
		return sign.toString();
	}

	public static byte[] md5Raw(byte[] data) {
		byte[] md5buf = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("md5");
			md5buf = md5.digest(data);
		} catch (Exception e) {
			md5buf = null;
			e.printStackTrace(System.err);
		}
		return md5buf;
	}

	public static String md5String(byte[] data) {
		String md5Str = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("md5");
			md5Str = "";
			byte[] buf = md5.digest(data);
			for (int i = 0; i < buf.length; i++) {
				md5Str += byte2Hex(buf[i]);
			}
		} catch (Exception e) {
			md5Str = null;
			e.printStackTrace(System.err);
		}
		return md5Str;
	}

	public static String byte2Hex(byte b) {
		String hex = Integer.toHexString(b);
		if (hex.length() > 2) {
			hex = hex.substring(hex.length() - 2);
		}
		while (hex.length() < 2) {
			hex = "0" + hex;
		}
		return hex;
	}
	
	public static String byte2Hex(byte[] bytes) {
		Formatter formatter = new Formatter();
		for (byte b : bytes) {
			formatter.format("%02x", b);
		}
		String hash = formatter.toString();
		formatter.close();
		return hash;
	}
}
