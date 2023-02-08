package bdd.cucumber.Options;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/bdd/features",
        glue = {"bdd/stepDefinitions"})
public class TestRunner {

}
