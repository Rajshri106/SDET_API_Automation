package APIAssignment;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetAllProductList {

    @Test
    public void GetBooksDetails() {
        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "https://automationexercise.com/api/productsList";
        // Get the RequestSpecification of the request to be sent to the server.
        RequestSpecification httpRequest = RestAssured.given();
        // specify the method type (GET) and the parameters if any.
        //In this case the request does not take any parameters
        Response response = httpRequest.request(Method.GET, "");
        // Get the status code from the Response in a variable called statusCode
        var statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/
                , "Correct status code returned");
        // Retrieve the body of the Response
        Response body = (Response) response.getBody();
        // To check for sub string presence get the Response body as a String.
        // Do a String.contains
        String bodyAsString = body.asString();
        // convert the body into lower case and then do a comparison to ignore casing.
        Assert.assertTrue(bodyAsString.contains("Blue Top"), "Response body contains Blue top");
        // First get the JsonPath object instance from the Response interface
        JsonPath jsonPathEvaluator = response.jsonPath();
        // Then simply query the JsonPath object to get a String value of the node
        var productBrand = jsonPathEvaluator.get("products[0].brand");
        // Let us print the city variable to see what we got
        System.out.println("Product brand is:  " + productBrand);
        Assert.assertEquals(productBrand, "Polo", "Correct brand name received in the Response");
        //convert JSON to string
        JsonPath jsonResp = new JsonPath(response.asString());
        //length of JSON array
        int resSize = jsonResp.getInt("products.id.size()");
        System.out.println("ArraySize for IDs from Response : " + resSize);
    }
}