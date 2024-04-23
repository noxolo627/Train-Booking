package io.swagger.api;

import io.swagger.annotations.*;
import io.swagger.model.Booking;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2023-07-20T12:56:46.917+02:00")
@Validated
@Api(value = "booking", description = "the booking API")
@RequestMapping(value = "")
public interface BookingApi {

    @ApiOperation(value = "Create a new booking", nickname = "bookTrain", notes = "", response = Integer.class, tags={ "booking", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Booking created successfully", response = Integer.class) })
    @RequestMapping(value = "/booking/booking",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<ApiResponseMessage> bookTrain(
            @ApiParam(value = "User Booking" ,required=true )  @Valid @RequestBody Booking booking);


    @ApiOperation(value = "Delete a booking", nickname = "deleteBooking", notes = "", tags={ "booking", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Booking deleted successfully"),
        @ApiResponse(code = 400, message = "Invalid request data"),
        @ApiResponse(code = 404, message = "Booking not found") })
    @RequestMapping(value = "/booking/booking/{bookingId}",
        method = RequestMethod.DELETE)
    ResponseEntity<ApiResponseMessage> deleteBooking(
            @ApiParam(value = "ID of the booking to delete",required=true) @PathVariable("bookingId") Integer bookingId);


    @ApiOperation(value = "Get a booking", nickname = "getBooking", notes = "", response = Booking.class, tags={ "booking", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Train Ticket", response = Booking.class) })
    @RequestMapping(value = "/booking/booking/{bookingId}",
        method = RequestMethod.GET)
    ResponseEntity<ApiResponseMessage> getBooking(
            @ApiParam(value = "ID of the booking to get",required=true) @PathVariable("bookingId") Integer bookingId);


    @ApiOperation(value = "Get bookings of a user", nickname = "getBookings", notes = "", response = Booking.class, responseContainer = "List", tags={ "booking", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Bookings", response = Booking.class, responseContainer = "List") })
    @RequestMapping(value = "/booking/getBooking/{userEmail}",
        method = RequestMethod.GET)
    ResponseEntity<ApiResponseMessage> getBookings(
            @ApiParam(value = "User email of bookings to get",required=true) @PathVariable("userEmail") String userEmail);


    @ApiOperation(value = "Update a booking", nickname = "updateBooking", notes = "", tags={ "booking", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Booking updated successfully"),
        @ApiResponse(code = 400, message = "Invalid request data"),
        @ApiResponse(code = 404, message = "Booking not found") })
    @RequestMapping(value = "/booking/booking/{bookingId}",
        method = RequestMethod.PUT)
    ResponseEntity<ApiResponseMessage> updateBooking(
            @ApiParam(value = "ID of the booking to update",required=true) @PathVariable("bookingId") Integer bookingId,
            @ApiParam(value = "Updated booking object" ,required=true )  @Valid @RequestBody Booking booking);

}
