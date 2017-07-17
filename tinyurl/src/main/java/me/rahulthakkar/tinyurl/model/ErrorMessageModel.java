package me.rahulthakkar.tinyurl.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessageModel {
	private String errorMessage;
	private int errorCode;
	
	public ErrorMessageModel(){}
	
	public ErrorMessageModel(String errorMessage, int errorCode) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
