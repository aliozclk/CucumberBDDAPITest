Feature: Validating Place API's

  Scenario Outline: Verify if Place is being successfully added using AddPlaceAPI


      Given Add Place Payload with "<name>" "<language>" "<address>"
      When the user calls "<resources>" with "<httpMethod>" http request
      Then the API Call got success with status code 200
      And "status" in response body is "OK"
      And "scope" in response body is "APP"
      And verify place_id created maps to "<name>" using "getPlaceAPI"

      Examples:
        | name    | language | address            | resources   | httpMethod |
        | AAhouse | English  | World cross center | addPlaceAPI | Post       |
        #| BBhouse | Spanish  | Sea cross center   | addPlaceAPI | Post       |
