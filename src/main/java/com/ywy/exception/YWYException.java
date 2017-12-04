package com.ywy.exception;

public class YWYException extends RuntimeException {
	private static final long serialVersionUID = 8788718115929212870L;

	private int code;
	private String message;

	public YWYException(){
		super();
	}

	public YWYException(Throwable t){
		super(t);
	}

	public YWYException(String message){
		super(message);
	}

	public YWYException(String message, Throwable cause) {
		super(message, cause);
	}

	public YWYException(int code, String message) {
		this.code = code;
		this.message = message;
	}


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
