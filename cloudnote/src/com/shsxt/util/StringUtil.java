package com.shsxt.util;



public class StringUtil {
	/*
	 * 判断是否为空，空为true，非空false
	 */
	public static  boolean isEmpty(String string){
		if(string==null || "".equals(string.trim())){
			return true;
		}
		else{
			return false;
		}
	}
	
	/*
	 * 判断是否不为空，空为false，非空为true
	 */
	public  static boolean isNotEmpty(String string){
		if(string==null || "".equals(string.trim())){
			return false;
		}
		else{
			return true;
		}
	}
}
