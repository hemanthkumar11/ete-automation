package com.encore.ete.steps;

import com.encore.ete.helpers.RestAssuredHelper;
import com.encore.ete.utils.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestAssuredSteps {
    RestContext restContext;
    RequestSpecification request = given();
    protected Logger log = LogManager.getLogger(this.getClass().getName());

    public RestAssuredSteps(RestContext restContext) {
        this.restContext = restContext;
    }

    JSONObject basedata = null;
    private String api = null;
    private String path = null;
    private String method = null;

    private static boolean apiTest;

    public static boolean isApiTest() {
        return apiTest;
    }

    public static void setApiTest(boolean api) {
        apiTest = api;
    }

    public void resetRest() {
        request = given();
        restContext.getRestData().setRequest(request);
    }

    @Given("^a rest api \"(.*)\"$")
    public void aRestApi(String api) {
        String envURI = Property.getProperty(Constants.ENVIRONMENTPATH + Property.getVariable("cukes.env") + ".properties", api);
        RestAssuredHelper.setBaseURI(restContext.getRestData().getRequest(), envURI);
        setApiTest(true);
    }

    @Given("^a header$")
    public void setHeader(Map<String, String> map) {
        map.forEach((key, val) -> {
            RestAssuredHelper.setHeader(restContext.getRestData().getRequest(), key, val);
            if (key.equalsIgnoreCase("Content-Type")) restContext.getRestData().setContextType(val);
        });
    }

    @Given("^(form parameters|query parameters|path parameters|parameters)$")
    public void withParams(String type, Map<String, String> map) {
        map.forEach((key, val) -> {
            RestAssuredHelper.setParam(restContext.getRestData().getRequest(), type, key, val);
        });
    }

    @When("^the system requests (GET|PUT|POST|PATCH|DELETE) \"(.*)\"$")
    public void apiGetRequest(String apiMethod, String path) {
        this.path = path;
        this.method = apiMethod;
        Response response = RestAssuredHelper.callAPI(restContext.getRestData().getRequest(), apiMethod, path);
        restContext.getRestData().setResponse(response);
        resetRest();        //enables multiple api calls within single scenario
    }

    @Then("^the response code is (\\d+)$")
    public void verify_status_code(int code) throws NumberFormatException {
        RestAssuredHelper.checkStatus(restContext.getRestData(), code);
        TestContext.getInstance().sa().assertAll();
    }

    @And("^the (json|response) body( strictly|) contains$")
    public void responseBodyValid(String type, String mode, DataTable table) throws IOException, JSONException {
        List<List<String>> temp = table.cells();

        String responseString;
        if (type.equalsIgnoreCase("response")) {
            responseString = restContext.getRestData().getRespString();
        } else {
            responseString = TestContext.getInstance().testdataGet("jsonBody").toString();
        }

        if (temp.get(0).size() == 1) {
            String[] filename = temp.get(1).get(0).replace("<<", "").replace(">>", "").split("\\.");
            RestAssuredHelper.responseBodyValid(mode, filename[0], filename[1], responseString);
        } else if (temp.get(0).size() == 2) {
            RestAssuredHelper.responseBodyValid(this.api, this.method, this.path, table.asMap(String.class, String.class), responseString);
        } else {
            RestAssuredHelper.responseContains(table.asList(ResponseValidator.class), responseString);
        }
    }

}
