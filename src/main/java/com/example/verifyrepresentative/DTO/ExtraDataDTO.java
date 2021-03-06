package com.example.verifyrepresentative.DTO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ExtraDataDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@NotNull
	private String key;
	@NotNull
	private Object value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ExtraDataDTO [key=" + key + ", value=" + value + "]";
	}

}
