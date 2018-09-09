package com.shsxt.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

public class JSONutil {
	public static String  toJson(Object object,HttpServletResponse response) throws IOException{
		PrintWriter out=response.getWriter();
		String json=JSON.toJSONString(object);
		out.write(json);
		out.close();
		return json;
	}
}
