package org.shop.location.web;

import java.util.ArrayList;
import java.util.List;

import org.shop.location.domains.Response;
import org.shop.location.domains.Shop;
import org.shop.location.domains.Status;
import org.shop.location.domains.Status.StatusCode;
import org.shop.location.services.ShopLocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Shop Location Service endpoints.
 * 
 * @author ywang
 *
 */
@RestController
public class ShopLocationServiceEndpoints {

	private final static Logger LOGGER = LoggerFactory.getLogger(ShopLocationServiceEndpoints.class);
	
	@Autowired
	private ShopLocationService shopLocationService;
	
	@RequestMapping(value="/storeShopCoordinate", method=RequestMethod.GET)
	public Response storeShopCoordinate(
			@RequestParam("name") String name,
			@RequestParam("number") String number,
			@RequestParam("postcode") String postcode) {
		
		LOGGER.info("storeShopCoordinate request name={} number={} postcode={}.",
				name,
				number,
				postcode);
		
		// Assume everything is ok unless otherwise.
		Status status = new Status();
		status.setStatusCode(StatusCode.OK);
		
		Response response = new Response();
		response.setStatus(status);
		response.setShops(shopLocationService.storeShopCoordinate(name, number, postcode));
		
		//TODO exception handling
		
		return response;
	}
	
	@RequestMapping(value="/getNearestShop", method=RequestMethod.GET)
	public Response getNearestShop(
			@RequestParam("latitude") Double latitude,
			@RequestParam("longitude") Double longitude) {
		
		LOGGER.info("getNearestShop request latitude={} longitude={}.",
				latitude,
				longitude);
		
		// Assume everything is ok unless otherwise.
		Status status = new Status();
		status.setStatusCode(StatusCode.OK);
		
		List<Shop> shop = new ArrayList<Shop>();
		shop.add(shopLocationService.getNearestShop(longitude, latitude));
		
		Response response = new Response();
		response.setStatus(status);
		response.setShops(shop);
		
		//TODO exception handling
		
		return response;
	}
	
	@RequestMapping(value="/helloWorld", method=RequestMethod.GET)
	public String helloWorld() {
		return "hello!";
	}
}
