package com.deutsche.shop.location.domains;

/**
 * Represents the response status from Shop Location Service
 * 
 * @author ywang
 *
 */
public class Status {

	private StatusCode statusCode;
	private String reason;
	
	public enum StatusCode {
		OK,
		BAD
	}

	/**
	 * @return the statusCode
	 */
	public StatusCode getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(StatusCode statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
}
