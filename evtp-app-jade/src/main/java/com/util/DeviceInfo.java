package com.util;

import java.io.Serializable;

public class DeviceInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String type;
	private String status;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "DeviceInfo [type=" + type + ", status=" + status + "]";
	}
	
	
}
