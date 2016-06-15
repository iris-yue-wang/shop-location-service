package org.shop.location.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.shop.location.domains.Address;
import org.shop.location.domains.Coordinate;
import org.shop.location.domains.GeocodingResponse;
import org.shop.location.domains.Location;
import org.shop.location.domains.Result;
import org.shop.location.domains.Shop;
import org.shop.location.utils.AddressBuilder;
import org.shop.location.utils.DistanceCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Implements ShopLocationService interface.
 * 
 * @author ywang
 *
 */
@Service
public class ShopLocationServiceImpl implements ShopLocationService {

	private final static Logger LOGGER = LoggerFactory.getLogger(ShopLocationServiceImpl.class);
	
	@Value("${shop.location.geocoding.endpoint:https://maps.googleapis.com/maps/api/geocode/json}")
	private String geocodingEndpoint;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	private CacheManager cacheManager;
	
	@Cacheable("shops")
	public List<Shop> storeShopCoordinate(
			String name, 
			String number,
			String postcode) {
		
		List<Shop> shops = new ArrayList<Shop>();
		
		// Build the request string
		StringBuilder builder = new StringBuilder(geocodingEndpoint);
		builder
		.append("?")
		.append("address=")
		.append(AddressBuilder.buildGeocodingAddress(name, number, postcode));
		
		LOGGER.info("Getting shop coordinate from {}", builder.toString());
		
		// Get resposne from geocoding endpoint
		GeocodingResponse geocodingResponse = restTemplate.getForObject(builder.toString(), GeocodingResponse.class);
		List<Result> results = geocodingResponse.getResults();
		
		// Transform geocoding response into Shop.
		// Because there might be more than one result returned in the response,
		// we want to save them all.
		for(Result result : results){
			Location location = result.getGeometry().getLocation();
			
			Address address = new Address();
			address.setNumber(number);
			address.setPostcode(postcode);
			
			Coordinate coordinate = new Coordinate();
			coordinate.setLatitude(location.getLat());
			coordinate.setLongitude(location.getLng());
			
			Shop shop = new Shop();
			shop.setName(name);
			shop.setAddress(address);
			shop.setCoordinate(coordinate);
			
			shops.add(shop);
			
			LOGGER.info("Shop coordinate lat {} lon {} stored for {} {} {}.", 
					shop.getCoordinate().getLatitude(), 
					shop.getCoordinate().getLongitude(), 
					name, 
					number, 
					postcode);
		}
		
		return shops;
	}

	public Shop getNearestShop(
			Double customerLongitude,
			Double customerLatitude) {
		
		LOGGER.info("Getting nearest shop for (lat {} lon {}).", customerLatitude, customerLongitude);
		
		Coordinate customerCoordinate = new Coordinate();
		customerCoordinate.setLatitude(customerLatitude);
		customerCoordinate.setLongitude(customerLongitude);
		
		// Get the concurrent map from cache.
		ConcurrentMap<String, List<Shop>> shopCacheMap = (ConcurrentHashMap<String, List<Shop>>) cacheManager.getCache("shops").getNativeCache();

		// Get the nearest shop.
		Shop nearestShop = DistanceCalculator.retrieveNearestShop(customerCoordinate, shopCacheMap);
		
		if (nearestShop != null) {
			LOGGER.info("Nearest shop for (lat {} lon {}) is {}, {}, {} (lat {} lon {}.)",
					customerLatitude,
					customerLongitude,
					nearestShop.getName(),
					nearestShop.getAddress().getNumber(),
					nearestShop.getAddress().getPostcode(),
					nearestShop.getCoordinate().getLatitude(),
					nearestShop.getCoordinate().getLongitude());
		} else {
			LOGGER.info("No nearest shop found for (lat {} lon {})",
					customerLatitude,
					customerLongitude);
		}
		
		return nearestShop;
	}
}
