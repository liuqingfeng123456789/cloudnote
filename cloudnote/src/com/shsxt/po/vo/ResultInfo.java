package com.shsxt.po.vo;


/**
 * �Żظ��ͻ��˵������Ϣ����������
 * @author liuqingfeng
 *
 * @param <T>
 */
public class ResultInfo<T> {
	private Integer code; //�����Ƿ�ɹ�ת̬��
	private String msg;   //���ص�ת̬�����Ϣ˵��
	private T result;		//���ؿͻ��˵��������
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
