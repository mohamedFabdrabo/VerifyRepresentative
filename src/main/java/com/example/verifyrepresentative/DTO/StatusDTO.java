package com.example.verifyrepresentative.DTO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class StatusDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private String code;
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "StatusDTO [code=" + code + ", message=" + message + "]";
	}

}
