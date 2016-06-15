# Shop Location Service
Store geographic coordinates of a location and retrieve nearest location for a given coordinate

## Run the application
### To run the Spring Boot app, download the source code, and build using Maven 3:
```
mvn package
```
Create a .yml file, or download it from this repository:
```
spring:
    cache:
        # Cache name should always be shops.
        cache-names: shops
        guava:
            spec: maximumSize=10000,expireAfterAccess=6000s

# Geocoding endpoint address, should always use json format
shop:
    location:
        geocoding:
            endpoint: https://maps.googleapis.com/maps/api/geocode/json

logging:
    file: shoplocationservice.log
```
Run the built executable jar file in the same directory as the .yml file:
```
java -jar target/shop-location-service-0.0.1-SNAPSHOT.jar
```

## API usage
### Store a shop location
The following example stores a shop longitude and latitude for "Mikes Diving Store, 11, W4 5PY":
```
http://localhost:8080/storeShopCoordinate?name=MikesDivingStore&number=11&postcode=W4%205PY
```
Below is a sample response in JSON:
```
{"shops":[{"name":"MikesDivingStore","address":{"postcode":"W4 5PY","number":"11"},"coordinate":{"longitude":-0.2798202,"latitude":51.4940716}}],"status":{"statusCode":"OK","reason":null}}
```

### Get the nearest shop location
The following example gets a nearest shop for longitude -0.2798202 and latitude 51.4940716:
```
http://localhost:8080/getNearestShop?latitude=51.4940716&longitude=-0.2798202
```
Below is a sample response in JSON:
```
{"shops":[{"name":"MikesDivingStore","address":{"postcode":"W4 5PY","number":"11"},"coordinate":{"longitude":-0.2798202,"latitude":51.4940716}}],"status":{"statusCode":"OK","reason":null}}
```

## Run tests
### Integration tests and unit tests can be run by running the following in shop-location-service module:
```
mvn verify
```

### BDD acceptance tests can be run by running the following in shop-location-tests module:
```
mvn verify
```
