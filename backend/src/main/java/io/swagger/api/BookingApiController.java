package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Booking;
import io.swagger.model.Train;
import io.swagger.service.BookingService;
import io.swagger.service.TrainService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2023-07-20T12:56:46.917+02:00")

@Controller
public class BookingApiController implements BookingApi {

    private static final Logger log = LoggerFactory.getLogger(BookingApiController.class);

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final BookingService bookingService;

    @Autowired
    public BookingApiController(ObjectMapper objectMapper, HttpServletRequest request, BookingService bookingService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.bookingService = bookingService;
    }

    public ResponseEntity<ApiResponseMessage> bookTrain(
            @ApiParam(value = "User Booking" ,required=true )  @Valid @RequestBody Booking booking) {
        log.debug("Received request to /booking/booking POST (bookTrain) with booking=" + booking);
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            if (booking.getUserEmail() != userEmail) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.BAD_REQUEST.value(), "The logged in user is not doing the booking.");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
            }

            Booking addedBooking = bookingService.createOrUpdateTrain(booking);

            if (addedBooking == null) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.NOT_FOUND.value(), "No train seats available for booking or invalid train.");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
            }

            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(), "Booking created successfully.");
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to add booking.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponseMessage> deleteBooking(
            @ApiParam(value = "ID of the booking to delete",required=true) @PathVariable("bookingId") Integer bookingId) {
        log.debug("Received request to /booking/booking/{bookingId} DELETE (deleteBooking) with bookingId=" + bookingId);
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            Booking foundBooking = bookingService.getBookingById(bookingId);

            if (foundBooking == null) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.NOT_FOUND.value(), "Booking was not found.");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
            }

            bookingService.deleteBooking(bookingId, foundBooking);

            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(),
                    "Booking deleted successfully");
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to delete booking.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponseMessage> getBooking(
            @ApiParam(value = "ID of the booking to get",required=true) @PathVariable("bookingId") Integer bookingId) {
        log.debug("Received request to /booking/booking/{bookingId} GET (getBooking) with bookingId=" + bookingId);
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            Booking foundBooking = bookingService.getBookingById(bookingId);

            if (foundBooking == null) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.NOT_FOUND.value(), "Booking was not found.");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
            }

            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(), "Booking found successfully", foundBooking);
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to get booking.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponseMessage> getBookings(
            @ApiParam(value = "User email of bookings to get",required=true) @PathVariable("userEmail") String userEmail) {
        log.debug("Received request to /booking/getBooking/{userEmail} GET (getBookings) with userEmail=" + userEmail);
        String userEmailAttribute = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmailAttribute);

        try {
            if (userEmail != userEmailAttribute) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.BAD_REQUEST.value(), "You are not fetching your bookings!");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
            }

            List<Booking> foundBooking = bookingService.getBookingByUserEmail(userEmail);

            if (foundBooking == null || foundBooking.size() == 0) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.NOT_FOUND.value(), "Bookings was not found.");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
            }

            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(), "Bookings found successfully", foundBooking);
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to get bookings.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponseMessage> updateBooking(
            @ApiParam(value = "ID of the booking to update",required=true) @PathVariable("bookingId") Integer bookingId,
            @ApiParam(value = "Updated booking object" ,required=true )  @Valid @RequestBody Booking booking) {
        log.debug("Received request to /booking/booking/{bookingId} PUT (updateBooking) with bookingId=" + bookingId + " and booking=" + booking);
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.NOT_IMPLEMENTED.value(), "Not implemented");
        log.debug("Response: " + responseMessage);
        return new ResponseEntity<>(responseMessage, HttpStatus.NOT_IMPLEMENTED);
    }

}
