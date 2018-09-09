package com.shsxt.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.sun.org.apache.xml.internal.security.utils.Base64;


public class MD5util {
	public static String md5(String str){
		try {
			MessageDigest md=MessageDigest.getInstance("md5");
			byte[] bs=md.digest(str.getBytes());
			str=Base64.encode(bs);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
}
