package org.shop.location.domains;

import java.util.List;

/**
 * Represents a response from Shop Location Service.
 * 
 * @author ywang
 *
 */
public class Response {

	private List<Shop> shops;
	private Status status;

	/**
	 * @return the shops
	 */
	public List<Shop> getShops() {
		return shops;
	}

	/**
	 * @param shops the shops to set
	 */
	public void setShops(List<Shop> shops) {
		this.shops = shops;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
}
