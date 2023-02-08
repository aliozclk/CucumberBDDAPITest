package bdd.stepDefinitions;

import APIFramework.pojo.AddPlace;
import APIFramework.pojo.Location;
import bdd.resources.APIResources;
import bdd.resources.TestDataBuild;
import bdd.resources.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class StepDefinition extends Utils {

    RequestSpecification res;
    ResponseSpecification resspec;
    TestDataBuild dataBuild = new TestDataBuild();
    static String place_id;


    Response response;

    @Given("Add Place Payload with {string} {string} {string}")
    public void add_place_payload(String name, String language, String address) throws IOException {
        // Write code here that turns the phrase above into concrete actions


        res = given().spec(requestSpecification())
                .body(dataBuild.addPlacePayLoad(name, language, address));

    }

    @When("the user calls {string} with {string} http request")
    public void the_user_calls_with_post_http_request(String resource, String httpMethod) {
        // Write code here that turns the phrase above into concrete actions
        //this line creates an enum object for special API URL
        APIResources apiResources = APIResources.valueOf(resource);


        resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        if (httpMethod.equalsIgnoreCase("post")) {
            response = res.when().post(apiResources.getResource()).
                    then().spec(resspec).extract().response();
        }
        else if(httpMethod.equalsIgnoreCase("delete")) {
            response = res.when().delete(apiResources.getResource()).
                    then().spec(resspec).extract().response();
        }
        else if(httpMethod.equalsIgnoreCase("get")) {
            response = res.when().get(apiResources.getResource()).
                    then().spec(resspec).extract().response();
        }
    }

    @Then("the API Call got success with status code {int}")
    public void the_api_call_got_success_with_status_code(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Then("{string} in response body is {string}")
    public void in_response_body_is(String key, String ExpectedValue) {
        // Write code here that turns the phrase above into concrete actions

        Assert.assertEquals(getJsonPath(response,key), ExpectedValue);
    }

    @Then("verify place_id created maps to {string} using {string}")
    public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
        // requestSpec

        place_id = getJsonPath(response,"place_id");
        res = given().spec(requestSpecification())
                .queryParam("place_id",place_id);
        the_user_calls_with_post_http_request(resource,"GET");
        String actualName = getJsonPath(response, "name");
        Assert.assertEquals(actualName,expectedName);
    }



    @Given("Delete Place Payload")
    public void delete_place_payload() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        res = given().spec(requestSpecification().body(dataBuild.deletePlacePayload(place_id)));
    }

}
