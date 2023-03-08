package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UpdateBookingTests extends BaseTest {
    @Test
    public void UpdateBookingTest() {
        // Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        // Get bookingId of new booking
        int bookingid = responseCreate.jsonPath().getInt("bookingid");

        // Create JSON body
        JSONObject body = new JSONObject();
        body.put("firstname", "Mary");
        body.put("lastname", "Brown");
        body.put("totalprice", 550);
        body.put("depositpaid", true);

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2020-03-25");
        bookingdates.put("checkout", "2020-03-27");
        body.put("bookingdates", bookingdates);
        body.put("additionalneeds", "Apple");

        // Update booking
        Response responseUpdate = RestAssured.given().auth().preemptive().basic("admin","password123").contentType(ContentType.JSON).
                body(body.toString()).put("https://restful-booker.herokuapp.com/booking/" + bookingid);
        responseUpdate.print();

        //    "username" : "admin",
        //    "password" : "password123"

        // Verifications
        // Verify response 200
        Assert.assertEquals(responseUpdate.getStatusCode(), 200, "Status code should be 200, but it's not");

        // Verify All fields
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = responseUpdate.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "Mary", "Firstname in response is not expected");

        String actualLastName = responseUpdate.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "Brown", "Lastname in response is not expected");

        int price = responseUpdate.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price, 550, "Totalprice in response is not expected");

        boolean depositpaid = responseUpdate.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(depositpaid, "Depositpaid should be trye, but it's not");

        String actualCheckin = responseUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2020-03-25", "Checkin in response is not expected");

        String actualCheckout = responseUpdate.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2020-03-27", "Checkout in response is not expected");

        String actualAdditionalNeeds = responseUpdate.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(actualAdditionalNeeds, "Apple", "Additionalneeds in response is not expected");

        softAssert.assertAll();

        /*
{"bookingid":11235,
"booking":{"firstname":"John",
"lastname":"Brown",
"totalprice":150,
"depositpaid":false,
bookingdates":
{"checkin":"2020-03-25",
"checkout":"2020-03-27"},
"additionalneeds":"Apple"}}

         */
    }
}
