package com.example.workjob.entity;

import java.io.Serializable;

import org.springframework.stereotype.Component;


@Component
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6123923861047263723L;
	
	private int result;
	
	private String msg;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
