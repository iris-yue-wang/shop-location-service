Shop Location Service Acceptance Tests

Narrative:
In order to provide a shop location service
I want customer to be able to search a shop's latitude and longitude by name, number and postcode
and
search the nearest shop to a geographic coordinate

Scenario: search for the nearest shop using latitude and longitude when no shops is stored
When I search nearest shop using latitude 51.4940716 and longitide -0.2798202
Then I should see no nearest shop found

Scenario: search a shop's geographic coordinate by its name, street number and postcode
When I store a shop's latitude and longitude by posting name MikesDivingStore, number 11 and postcode W4 5PY
Then I should see shop latitude 51.4940716 and longitude -0.2798202

Scenario: search for the nearest shop using latitude and longitude
Given I have shop MikesDivingStore, 11, W4 5PY stored
When I search nearest shop using latitude 51.4940716 and longitide -0.2798202
Then I should see shop MikesDivingStore, 11, W4 5PY