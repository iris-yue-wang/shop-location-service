package org.shop.location.web;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.shop.location.App;
import org.shop.location.domains.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 * Integration tests for endpoints.
 * 
 * @author ywang
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
@WebIntegrationTest("server.port=0")
public class ShopLocationServiceEndpointsIT {

	@Value("${local.server.port}")
	private int port;
	
	private RestTemplate template;
	
	private String storeShopCoordinateEndpoint;
	private String getNearestShopEndPoint;
	
	private static final String URL_BASE = "http://localhost";
	private static final String STORE_SHOP_COORDINATE_ENDPOINT = "storeShopCoordinate";
	private static final String GET_NEAREST_SHOP_ENDPOINT = "getNearestShop";
	
	private static final String SHOP_NAME = "MikesDivingStore";
	private static final String SHOP_NUMBER = "11";
	private static final String SHOP_POSTCODE = "W4 5PY";
	private static final double LATITUDE = 51.4940716;
	private static final double LONGITUDE = -0.2798202;
	
	
	@Before
	public void setUp() {
		template = new TestRestTemplate();
		
		storeShopCoordinateEndpoint = new StringBuilder(URL_BASE)
										.append(":")
										.append(port)
										.append("/")
										.append(STORE_SHOP_COORDINATE_ENDPOINT)
										.append("?name=")
										.append(SHOP_NAME)
										.append("&number=")
										.append(SHOP_NUMBER)
										.append("&postcode=")
										.append(SHOP_POSTCODE)
										.toString();
		
		getNearestShopEndPoint = new StringBuilder(URL_BASE)
									.append(":")
									.append(port)
									.append("/")
									.append(GET_NEAREST_SHOP_ENDPOINT)
									.append("?latitude=")
									.append(LATITUDE)
									.append("&longitude=")
									.append(LONGITUDE)
									.toString();
									
	}
	
	@Test
	public void testShopLocationServiceEndpoints() {
		// Store this shop's details
		Response response = template.getForObject(storeShopCoordinateEndpoint, Response.class);
		
		assertEquals(SHOP_NAME, response.getShops().get(0).getName());
		assertEquals(SHOP_NUMBER, response.getShops().get(0).getAddress().getNumber());
		assertEquals(SHOP_POSTCODE, response.getShops().get(0).getAddress().getPostcode());
		assertEquals(LATITUDE, response.getShops().get(0).getCoordinate().getLatitude(), 0);
		assertEquals(LONGITUDE, response.getShops().get(0).getCoordinate().getLongitude(), 0);
		
		// Get the nearest shop using the same coordinate.
		response = template.getForObject(getNearestShopEndPoint, Response.class);
		
		assertEquals(SHOP_NAME, response.getShops().get(0).getName());
		assertEquals(SHOP_NUMBER, response.getShops().get(0).getAddress().getNumber());
		assertEquals(SHOP_POSTCODE, response.getShops().get(0).getAddress().getPostcode());
		assertEquals(LATITUDE, response.getShops().get(0).getCoordinate().getLatitude(), 0);
		assertEquals(LONGITUDE, response.getShops().get(0).getCoordinate().getLongitude(), 0);
	}
}
