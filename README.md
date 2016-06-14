# Shop Location Service
Store geographic coordinates of a location and retrieve nearest location for a given coordinate

## Store a shop location
The following example stores a shop longitude and latitude for "Mikes Diving Store, 11, W4 5PY":
```
http://localhost:8080/storeShopCoordinate?name=MikesDivingStore&number=11&postcode=W4%205PY
```
Below is a sample response in JSON:
```
{"shops":[{"name":"MikesDivingStore","address":{"postcode":"W4 5PY","number":"11"},"coordinate":{"longitude":-0.2798202,"latitude":51.4940716}}],"status":{"statusCode":"OK","reason":null}}
```

## Get the nearest shop location
The following example gets a nearest shop for longitude -0.2798202 and latitude 51.4940716:
```
http://localhost:8080/getNearestShop?latitude=51.4940716&longitude=-0.2798202
```
Below is a sample response in JSON:
```
{"shops":[{"name":"MikesDivingStore","address":{"postcode":"W4 5PY","number":"11"},"coordinate":{"longitude":-0.2798202,"latitude":51.4940716}}],"status":{"statusCode":"OK","reason":null}}
```
