Feature: Validating Place API's

  Scenario: Verify if Place is being successfully added using AddPlaceAPI

    Given Add Place Payload
    When the user calls "AddPlaceAPI" with Post http request
    Then the API Call got success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"

