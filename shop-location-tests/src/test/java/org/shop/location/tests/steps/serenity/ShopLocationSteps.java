package org.shop.location.tests.steps.serenity;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.shop.location.domains.Shop;
import org.shop.location.services.ShopLocationService;
import org.shop.location.tests.TestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;

@SpringApplicationConfiguration(classes = TestConfiguration.class)
@ActiveProfiles("tests")
public class ShopLocationSteps {

	@Autowired
	private ShopLocationService shopLocationService;
	
	private List<Shop> shops;
	private Shop nearestShop;
	
	public void store_shop_location(String name, String number, String postcode) {
		shops = shopLocationService.storeShopCoordinate(name, number, postcode);
	}
	
	public void verify_shop_latitude_longitude(double latitude, double longitude) {
		assertEquals(latitude, shops.get(0).getCoordinate().getLatitude(), 0);
		assertEquals(longitude, shops.get(0).getCoordinate().getLongitude(), 0);
	}
	
	public void search_nearest_shop_by_coordinate(double latitude, double longitude) {
		nearestShop = shopLocationService.getNearestShop(longitude, latitude);
	}
	
	public void verify_shop_location(String name, String number, String postcode) {
		assertEquals(name, nearestShop.getName());
		assertEquals(number, nearestShop.getAddress().getNumber());
		assertEquals(postcode, nearestShop.getAddress().getPostcode());
	}
}
