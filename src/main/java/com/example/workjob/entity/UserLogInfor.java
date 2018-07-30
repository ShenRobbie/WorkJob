package com.example.workjob.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.bytebuddy.asm.Advice.This;

public class UserLogInfor implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5787210607380977148L;
	
	
	private int id;
	
	private String username;
	
	private String message;
	
	private Date createDate;
	
	private String beginDate;
	
	private String endDate;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getCreateDate() {
		return createDate;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String total=dfs.format(this.createDate)+ "   "+this.username+ "   " +this.message;
		return  total;
	}

	
	
	

}
