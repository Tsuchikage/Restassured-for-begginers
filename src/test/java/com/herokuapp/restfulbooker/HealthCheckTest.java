package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class HealthCheckTest extends BaseTest {
    @Test
    public void healthCheckTest() {
        given().spec(spec).when().get("/ping").
                then().assertThat().statusCode(201);
    }

    @Test
    public void healthAndCookiesTest() {
        Header someHeader = new Header("Some_name", "Some_value");
        spec.header(someHeader);

        Cookie someCookie = new Cookie.Builder("Some cookie", "Some cookie value").build();
        spec.cookie(someCookie);

        Response response = RestAssured.given(spec).
                cookie("Test cookie name","Test cookie value").
                header("Test header name", "Test header Value").log().all().get("/ping");

        // Get headers
        Headers headers = response.getHeaders();
        System.out.println("Headers: " + headers);

        Header serverHeader1 = headers.get("Server");
        System.out.println(serverHeader1.getName() + ": " + serverHeader1.getValue());

        String serverHeader2 = response.getHeader("Server");
        System.out.println("Server: " + serverHeader2);

        // Get cookies
        Cookies cookies = response.getDetailedCookies();
        System.out.println("Cookies: " + cookies);
    }
}
