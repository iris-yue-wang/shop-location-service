package com.deutsche.shop.location.services;

import java.util.List;

import com.deutsche.shop.location.domains.Shop;

/**
 * Interface for Shop Location Service.
 * 
 * @author ywang
 *
 */
public interface ShopLocationService {
	/**
	 * Store shop's longtitude and latitude by 
	 * providing shop name, shop address number and postcode.
	 * 
	 * @param name
	 * @param number
	 * @param postcode
	 * @return 
	 */
	public List<Shop> storeShopCoordinate(String name, String number, String postcode); 
	
	/**
	 * Retrieve the nearest shop.
	 * 
	 * @param customerLongitude
	 * @param customerLatitude
	 * @return
	 */
	public Shop getNearestShop(Double customerLongitude, Double customerLatitude);
}
