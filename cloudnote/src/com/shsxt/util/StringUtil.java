package com.shsxt.util;



public class StringUtil {
	/*
	 * �ж��Ƿ�Ϊ�գ���Ϊtrue���ǿ�false
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
	 * �ж��Ƿ�Ϊ�գ���Ϊfalse���ǿ�Ϊtrue
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
