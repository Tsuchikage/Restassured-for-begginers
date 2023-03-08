package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateBookingTests extends BaseTest {
    @Test(enabled = false)
    public void CreateBookingTest() {
        Response response = createBooking();
        response.print();

        // Verifications

        // Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

        // Verify All fields
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = response.jsonPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstName, "John", "Firstname in response is not expected");

        String actualLastName = response.jsonPath().getString("booking.lastname");
        softAssert.assertEquals(actualLastName, "Brown", "Lastname in response is not expected");

        int price = response.jsonPath().getInt("booking.totalprice");
        softAssert.assertEquals(price, 150, "Totalprice in response is not expected");

        boolean depositpaid = response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertFalse(depositpaid, "Depositpaid should be trye, but it's not");

        String actualCheckin = response.jsonPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2020-03-25", "Checkin in response is not expected");

        String actualCheckout = response.jsonPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2020-03-27", "Checkout in response is not expected");

        String actualAdditionalNeeds = response.jsonPath().getString("booking.additionalneeds");
        softAssert.assertEquals(actualAdditionalNeeds, "Apple", "Additionalneeds in response is not expected");

        softAssert.assertAll();
    }

    @Test
    public void CreateBookingWithPOJOTest() {
        // Create body using POJOs
        Bookingdates bookingdates = new Bookingdates("2020-03-25", "2020-03-27");
        Booking booking = new Booking("Mary", "Smith", 150, false, bookingdates, "Apple");

        // Get response
        Response response = RestAssured.given(spec).contentType(ContentType.JSON).
                body(booking).post("/booking");
        response.print();
        Bookingid bookingid = response.as(Bookingid.class);

        // Verifications
        // Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

        System.out.println("Request booking: " + booking.toString());
        System.out.println("Response booking: " + bookingid.getBooking().toString());

        // Verify All fields
        Assert.assertEquals(bookingid.getBooking().toString(), booking.toString());
    }
}
