package bdd.stepDefinitions;

import APIFramework.pojo.AddPlace;
import APIFramework.pojo.Location;
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

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class StepDefinition extends Utils{

    RequestSpecification res;
    ResponseSpecification resspec;
    TestDataBuild dataBuild = new TestDataBuild();

    Response response;
    @Given("Add Place Payload")
    public void add_place_payload() {
        // Write code here that turns the phrase above into concrete actions
        resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        res = given().spec(requestSpecification())
                .body(dataBuild.addPlacePayLoad());

    }
    @When("the user calls {string} with Post http request")
    public void the_user_calls_with_post_http_request(String string) {
        // Write code here that turns the phrase above into concrete actions
        response = res.when().post("/maps/api/place/add/json").
                then().spec(resspec).extract().response();
    }
    @Then("the API Call got success with status code {int}")
    public void the_api_call_got_success_with_status_code(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(response.getStatusCode(),200);
    }
    @Then("{string} in response body is {string}")
    public void in_response_body_is(String key, String ExpectedValue) {
        // Write code here that turns the phrase above into concrete actions
        String resp = response.asString();
        JsonPath jsonPath = new JsonPath(resp);
        Assert.assertEquals(jsonPath.get(key).toString(),ExpectedValue);
    }

}
