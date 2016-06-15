package org.shop.location.utils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.junit.Test;
import org.shop.location.domains.Address;
import org.shop.location.domains.Coordinate;
import org.shop.location.domains.Shop;

/**
 * Unit tests for DistanceCalculator util class
 * 
 * @author ywang
 *
 */
public class DistanceCalculatorTest {

	@Test
	public void testGetDistance() {
		Coordinate start = new Coordinate();
		start.setLatitude(38.898556);
		start.setLongitude(-77.037852);
		
		Coordinate end = new Coordinate();
		end.setLatitude(38.897147);
		end.setLongitude(-77.043934);
		
		double distance = DistanceCalculator.getDistance(start, end) * 6371;
		
		// In kilometers
		assertEquals(0.549, distance, 0.001);
	}
	
	@Test
	public void testGetNearestMap() {
		ConcurrentMap<String, List<Shop>> shopMap = new ConcurrentHashMap<String, List<Shop>>();
		
		List<Shop> location1 = new ArrayList<Shop>();
		
		Shop shop1 = new Shop();
		Address address1 = new Address();
		Coordinate coordinate1 = new Coordinate();
		
		coordinate1.setLatitude(37.898556);
		coordinate1.setLongitude(-78.037852);
		address1.setNumber("1");
		address1.setPostcode("postcode1");
		shop1.setName("shop1");
		shop1.setAddress(address1);
		shop1.setCoordinate(coordinate1);
		
		Shop shop2 = new Shop();
		Address address2 = new Address();
		Coordinate coordinate2 = new Coordinate();
		
		coordinate2.setLatitude(30.898556);
		coordinate2.setLongitude(-70.037852);
		address2.setNumber("2");
		address2.setPostcode("postcode2");
		shop2.setName("shop2");
		shop2.setAddress(address2);
		shop2.setCoordinate(coordinate2);
		
		location1.add(shop1);
		location1.add(shop2);
		
		shopMap.put("shop11postcode1", location1);
		
		Coordinate customerCoordinate = new Coordinate();
		customerCoordinate.setLatitude(38.898556);
		customerCoordinate.setLongitude(-77.037852);
		
		Shop nearestShop = DistanceCalculator.retrieveNearestShop(customerCoordinate, shopMap);
		
		assertEquals(shop1, nearestShop);
	}
}
