package org.shop.location.utils;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.shop.location.domains.Coordinate;
import org.shop.location.domains.Shop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Distance calculator util
 * 
 * @author ywang
 *
 */
public class DistanceCalculator {

	private final static Logger LOGGER = LoggerFactory.getLogger(DistanceCalculator.class);
	
	/**
	 * Get distance between two longitude and latitude points 
	 * make use of the Haversine formula.
	 * 
	 * @param start
	 * @param end
	 * @return distance between start point and end point, in radius
	 */
	public static double getDistance(Coordinate start, Coordinate end) {
		double dlon = degreeToRadius(end.getLongitude()) - degreeToRadius(start.getLongitude());
		double dlat = degreeToRadius(end.getLatitude()) - degreeToRadius(start.getLatitude());
		double dist = 
				Math.sin(dlat/2) * Math.sin(dlat/2)
				+
				Math.cos(degreeToRadius(start.getLatitude())) * Math.cos(degreeToRadius(end.getLatitude()))
				*
				Math.sin(dlon/2) * Math.sin(dlon/2);
		
		dist = 2 * Math.atan2(Math.sqrt(dist), Math.sqrt(1 - dist));
		
		return dist;
	}
	
	/**
	 * Retrieve nearest shop within the given map for the provided coordinate.
	 * 
	 * @param customerCoordinate
	 * @param shopMap
	 * @return nearest shop within the given map
	 */
	public static Shop retrieveNearestShop(Coordinate customerCoordinate, ConcurrentMap<String, List<Shop>> shopMap) {
		
		Shop nearestShop = null;
		double shortestDist = 0;
		
		Set<Entry<String, List<Shop>>> entries = shopMap.entrySet();
		
		for(Entry<String, List<Shop>> entry : entries) {
			List<Shop> shops = entry.getValue();
			for(Shop shop : shops) {
				
				LOGGER.debug("Comparing (lat {}, lon {}) to key {} = (lat {}, lon {}), distance is {}.", 
						customerCoordinate.getLatitude(), customerCoordinate.getLongitude(),
						entry.getKey(),
						shop.getCoordinate().getLatitude(), shop.getCoordinate().getLongitude(),
						DistanceCalculator.getDistance(customerCoordinate, shop.getCoordinate()));
				
				if (nearestShop == null) {
					nearestShop = shop;
					shortestDist = DistanceCalculator.getDistance(customerCoordinate, shop.getCoordinate());
				} else {
					double currentDist = DistanceCalculator.getDistance(customerCoordinate, shop.getCoordinate());
					
					// Store the shorter distance.
					if(currentDist < shortestDist) {
						nearestShop = shop;
						shortestDist = currentDist;
					}
				}
			}
		}
		
		return nearestShop;
	}
	
	private static double degreeToRadius(double degree) {
		return degree * Math.PI / 180;
	}
}
