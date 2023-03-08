package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteBookingTests extends BaseTest {
    @Test
    public void DeleteBookingTest() {
        // Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        // Get bookingId of new booking
        int bookingid = responseCreate.jsonPath().getInt("bookingid");

        // Delete booking
        Response responseDelete = RestAssured.given(spec).auth().preemptive().basic("admin", "password123")
                .delete("/booking/" + bookingid);
        responseDelete.print();

        // Verifications
        // Verify response 201
        Assert.assertEquals(responseDelete.getStatusCode(), 201, "Status code should be 201, but it's not");

        Response responseGet = RestAssured.given(spec).get("/booking" + bookingid);
        responseGet.print();

        Assert.assertEquals(responseGet.getBody().asString(), "Not Found", "Body should be 'Not Found', but it's not ");
    }

}
