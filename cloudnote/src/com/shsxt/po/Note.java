package com.shsxt.po;

public class Note {
	private Integer noteid;
	private String title;
	private String content;
	private String pubtime;
	private Integer typeId;
	public Note(){
		
	}
	public Integer getNoteid() {
		return noteid;
	}
	public void setNoteid(Integer noteid) {
		this.noteid = noteid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPubtime() {
		return pubtime;
	}
	public void setPubtime(String pubtime) {
		this.pubtime = pubtime;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	

	
	
}
