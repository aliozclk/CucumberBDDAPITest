package bdd.stepDefinitions;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

    @Before("@deletePlace")
    public void beforeScenario() throws IOException {
        //write a code that will give you place id
        //execute this code only place id is null

        if(StepDefinition.place_id == null){
            StepDefinition m = new StepDefinition();
            m.add_place_payload("Shetty","French","Asia");
            m.the_user_calls_with_post_http_request("addPlaceAPI","POST");
            m.verify_place_id_created_maps_to_using("Shetty","getPlaceAPI");
        }
    }
}
