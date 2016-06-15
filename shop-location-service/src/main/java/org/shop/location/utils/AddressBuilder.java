package org.shop.location.utils;

public class AddressBuilder {

	public static String buildGeocodingAddress(String name, String number, String postcode) {
		StringBuilder builder = new StringBuilder();
		
		builder.append(name)
		.append(",")
		.append(number)
		.append(",")
		.append(postcode);
		
		return builder.toString();
	}
}
