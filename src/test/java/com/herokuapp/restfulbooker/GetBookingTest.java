package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetBookingTest extends BaseTest {
    @Test(enabled = false)
    public void GetBookingTest() {
        // Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        // Set path parameter
        spec.pathParam("bookingId", responseCreate.jsonPath().getInt("bookingid"));

        // Get response with booking
        Response response = RestAssured.given(spec).get("/booking/{bookingId}");
        response.print();

        // Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

        // Verify All fields
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = response.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "Mark", "Firstname in response is not expected");

        String actualLastName = response.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "Brown", "Lastname in response is not expected");

        int price = response.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price, 648, "Totalprice in response is not expected");

        boolean depositpaid = response.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(depositpaid, "Depositpaid should be trye, but it's not");

        String actualCheckin = response.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2023-01-29", "Checkin in response is not expected");

        String actualCheckout = response.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2023-03-08", "Checkout in response is not expected");

        softAssert.assertAll();

        /*
{"firstname":"Mark","lastname":"Brown","totalprice":648,"depositpaid":false,"bookingdates":{"checkin":"2023-01-29","checkout":"2023-03-08"}}
         */
    }

    @Test
    public void GetBookingXMLTest() {
        // Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        // Set path parameter
        spec.pathParam("bookingId", responseCreate.jsonPath().getInt("bookingid"));

        // Get response with booking
//        Header xml = new Header("Accept", "application/xml");
//        spec.header(xml);
        Response response = RestAssured.given(spec).get("/booking/{bookingId}");
        response.print();

        // Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

        // Verify All fields
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = response.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "Mark", "Firstname in response is not expected");

        String actualLastName = response.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "Brown", "Lastname in response is not expected");

        int price = response.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price, 648, "Totalprice in response is not expected");

        boolean depositpaid = response.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(depositpaid, "Depositpaid should be trye, but it's not");

        String actualCheckin = response.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2023-01-29", "Checkin in response is not expected");

        String actualCheckout = response.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2023-03-08", "Checkout in response is not expected");

        softAssert.assertAll();

    }
}
