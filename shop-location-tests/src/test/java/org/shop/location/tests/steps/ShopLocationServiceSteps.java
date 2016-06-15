package org.shop.location.tests.steps;

import net.thucydides.core.annotations.Steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.shop.location.tests.steps.serenity.ShopLocationSteps;

public class ShopLocationServiceSteps {

	@Steps
	private ShopLocationSteps steps;
	
	@When("I store a shop's latitude and longitude by posting name $name, number $number and postcode $postcode")
	public void searchShopCoordinate(String name, String number, String postcode) {
		steps.store_shop_location(name, number, postcode);
	}
	
	@Then("I should see shop latitude $latitude and longitude $longitude")
	public void verifyShopCoordinate(double latitude, double longitude) {
		steps.verify_shop_latitude_longitude(latitude, longitude);
	}
	
	@Given("I have shop $name, $number, $postcode stored")
	public void storeShopCoordinate(String name, String number, String postcode) {
		steps.store_shop_location(name, number, postcode);
	}
	
	@When("I search nearest shop using latitude $latitude and longitide $longitude")
	public void searchNearestShop(double latitude, double longitude) {
		steps.search_nearest_shop_by_coordinate(latitude, longitude);
	}
	
	@Then("I should see shop $name, $number, $postcode")
	public void verifyNearestShop(String name, String number, String postcode) {
		steps.verify_shop_location(name, number, postcode);
	}
	
	@Then("I should see no nearest shop found")
	public void verifyNearestShopNotFound() {
		steps.verify_shop_location_null();
	}
}
