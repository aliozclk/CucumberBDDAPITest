package bdd.resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

public class Utils {

    public  static RequestSpecification req;
    public RequestSpecification requestSpecification() throws IOException {

        if(req == null) {
            PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));

            /**
             * addFilter() method filters the log and creates a log file
             *log file has request and response logs
             */

            req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl")).addQueryParam("key", "qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();

            return req;
        }
        return req;
    }

    public static String getGlobalValue(String key) throws IOException {
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream("C:\\Users\\alioz\\Desktop\\workspace\\InarAcademy\\CucumberBDDAPITest\\src\\test\\java\\bdd\\resources\\global.properties");
        properties.load(fis);
        return properties.getProperty(key);

    }

    public String getJsonPath(Response response, String key){
        String resp = response.asString();
        JsonPath json = new JsonPath(resp);
        return json.get(key).toString() ;
    }

}
