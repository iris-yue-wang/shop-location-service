package org.shop.location.services;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.shop.location.application.UnitTestConfiguration;
import org.shop.location.domains.Address;
import org.shop.location.domains.Coordinate;
import org.shop.location.domains.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit tests for Shop Location Service implementation class.
 * 
 * @author ywang
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(UnitTestConfiguration.class)
public class ShopLocationServiceTest {

	@Autowired
	private ShopLocationService shopLocationService;
	
	@Autowired
	private CacheManager cacheManager;
	
	private List<Shop> location1;
	private String cacheKey1;
	private static final String ADDRESS_NUMBER_1 = "1";
	private static final String POSTCODE_1 = "postcode1";
	private static final String SHOP_NAME_1 = "shop1";
	
	private List<Shop> location2;
	private String cacheKey2;
	private static final String ADDRESS_NUMBER_2 = "2";
	private static final String POSTCODE_2 = "postcode2";
	private static final String SHOP_NAME_2 = "shop2";
	
	private static final String CACHE_NAME = "shops";
	
	@Before
	public void setUp() {
		location1 = new ArrayList<Shop>();
		
		Shop shop1 = new Shop();
		Address address1 = new Address();
		Coordinate coordinate1 = new Coordinate();
		
		coordinate1.setLatitude(37.898556);
		coordinate1.setLongitude(-78.037852);
		address1.setNumber(ADDRESS_NUMBER_1);
		address1.setPostcode(POSTCODE_1);
		shop1.setName(SHOP_NAME_1);
		shop1.setAddress(address1);
		shop1.setCoordinate(coordinate1);
		
		Shop shop2 = new Shop();
		Address address2 = new Address();
		Coordinate coordinate2 = new Coordinate();
		
		coordinate2.setLatitude(30.898556);
		coordinate2.setLongitude(-70.037852);
		address2.setNumber(ADDRESS_NUMBER_1);
		address2.setPostcode(POSTCODE_1);
		shop2.setName(SHOP_NAME_1);
		shop2.setAddress(address2);
		shop2.setCoordinate(coordinate2);
		
		location1.add(shop1);
		location1.add(shop2);

		cacheKey1 = new StringBuilder()
						.append(SHOP_NAME_1)
						.append(ADDRESS_NUMBER_1)
						.append(POSTCODE_1)
						.toString();
		
		cacheManager.getCache(CACHE_NAME).put(cacheKey1, location1);
		
		location2 = new ArrayList<Shop>();
		
		Shop shop3 = new Shop();
		Address address3 = new Address();
		Coordinate coordinate3 = new Coordinate();
		
		coordinate3.setLatitude(42.898556);
		coordinate3.setLongitude(-80.037852);
		address3.setNumber(ADDRESS_NUMBER_2);
		address3.setPostcode(POSTCODE_2);
		shop3.setName(SHOP_NAME_2);
		shop3.setAddress(address3);
		shop3.setCoordinate(coordinate3);
		
		location2.add(shop3);

		cacheKey2 = new StringBuilder()
						.append(SHOP_NAME_2)
						.append(ADDRESS_NUMBER_2)
						.append(POSTCODE_2)
						.toString();
		
		cacheManager.getCache(CACHE_NAME).put(cacheKey2, location2);
	}
	
	@Test
	public void testGetNearestMap() {
		// Test that getNearestShop indeed returns the nearest shop.
		Shop nearestShop = shopLocationService.getNearestShop(-77.037852, 38.89855);
		
		assertEquals(location1.get(0), nearestShop);
	}
	
	@Test
	public void testStoreShopCoordinateCache() {
		// Test that if caching works when storing shop coordinates
		shopLocationService.storeShopCoordinate(SHOP_NAME_1, ADDRESS_NUMBER_1, POSTCODE_1);
		
		assertEquals(location1, cacheManager.getCache(CACHE_NAME).get(cacheKey1).get());
		
		//TODO: test cache eviction policy.
	}
}
