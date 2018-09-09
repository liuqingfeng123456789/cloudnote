package com.shsxt.po.vo;


/**
 * 放回给客户端的相关信息存在这里面
 * @author liuqingfeng
 *
 * @param <T>
 */
public class ResultInfo<T> {
	private Integer code; //返回是否成功转态码
	private String msg;   //返回的转态码的信息说明
	private T result;		//返回客户端的相干数据
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	
	
	
}
