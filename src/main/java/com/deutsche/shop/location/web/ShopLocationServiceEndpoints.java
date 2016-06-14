package com.deutsche.shop.location.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deutsche.shop.location.domains.Response;
import com.deutsche.shop.location.domains.Shop;
import com.deutsche.shop.location.domains.Status;
import com.deutsche.shop.location.domains.Status.StatusCode;
import com.deutsche.shop.location.services.ShopLocationService;

/**
 * Shop Location Service endpoints.
 * 
 * @author ywang
 *
 */
@RestController
public class ShopLocationServiceEndpoints {

	@Autowired
	private ShopLocationService shopLocationService;
	
	@RequestMapping(value="/storeShopCoordinate", method=RequestMethod.GET)
	public Response storeShopCoordinate(
			@RequestParam("name") String name,
			@RequestParam("number") String number,
			@RequestParam("postcode") String postcode) {
		
		// Assume everything is ok unless otherwise.
		Status status = new Status();
		status.setStatusCode(StatusCode.OK);
		
		Response response = new Response();
		response.setStatus(status);
		response.setShops(shopLocationService.storeShopCoordinate(name, number, postcode));
		
		return response;
	}
	
	@RequestMapping(value="/getNearestShop", method=RequestMethod.GET)
	public Response getNearestShop(
			@RequestParam("latitude") Double latitude,
			@RequestParam("longitude") Double longitude) {
		
		// Assume everything is ok unless otherwise.
		Status status = new Status();
		status.setStatusCode(StatusCode.OK);
		
		List<Shop> shop = new ArrayList<Shop>();
		shop.add(shopLocationService.getNearestShop(longitude, latitude));
		
		Response response = new Response();
		response.setStatus(status);
		response.setShops(shop);
		
		return response;
	}
}
