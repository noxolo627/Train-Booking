package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.SeatType;
import io.swagger.service.SeatTypeService;
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
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2023-07-20T12:56:46.917+02:00")

@Controller
public class SeatTypeApiController implements SeatTypeApi {

    private static final Logger log = LoggerFactory.getLogger(SeatTypeApiController.class);

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final SeatTypeService seatTypeService;

    @Autowired
    public SeatTypeApiController(ObjectMapper objectMapper, HttpServletRequest request,
             SeatTypeService seatTypeService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.seatTypeService = seatTypeService;
    }

    public ResponseEntity<ApiResponseMessage> addSeatType(
            @ApiParam(value = "Seat type object" ,required=true )  @Valid @RequestBody SeatType seatType) {
        log.debug("Received request to /seatType/seatType POST (addSeatType) with seatType=" + seatType);
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            seatType.setCreatedBy(userEmail);
            SeatType addedSeatType = seatTypeService.createOrUpdateSeatType(seatType);
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(), "Seat type created successfully");
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to add seat type.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponseMessage> deleteSeatType(
            @ApiParam(value = "ID of the seat type to delete",required=true) @PathVariable("seatTypeId") Integer seatTypeId) {
        log.debug("Received request to /seatType/seatType/{seatTypeId} DELETE (deleteSeatType) with seatTypeId=" + seatTypeId);
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            SeatType foundSeatType = seatTypeService.getSeatTypeById(seatTypeId).orElse(null);

            if (foundSeatType == null) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.NOT_FOUND.value(), "Seat type was not found.");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
            }

            boolean deleted = seatTypeService.deleteSeatType(seatTypeId);

            if (!deleted) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.CONFLICT.value(), "The Seat type is still in use and cannot be deleted.");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.CONFLICT);
            }

            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(),
                    "Seat type deleted successfully");
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to delete seat type.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponseMessage> getSeatTypes() {
        log.debug("Received request to /seatType/seatTypes GET (getSeatTypes)");
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            List<SeatType> seatTypes = seatTypeService.getAllSeatTypes();

            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(), "Seat types retrieved successfully.", seatTypes);
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to retrieve seat types.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponseMessage> updateSeatType(
            @ApiParam(value = "ID of the seat type to update",required=true) @PathVariable("seatTypeId") Integer seatTypeId,
            @ApiParam(value = "Updated seat type object" ,required=true )  @Valid @RequestBody SeatType seatType) {
        log.debug("Received request to /seatType/seatType/{seatTypeId} PUT (updateSeatType) with seatTypeId=" + seatTypeId + " and seatType=" + seatType);
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            SeatType foundSeatType = seatTypeService.getSeatTypeById(seatTypeId).orElse(null);

            if (foundSeatType == null) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.NOT_FOUND.value(), "Seat type was not found.");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
            }

            foundSeatType.setCreatedBy(userEmail);
            foundSeatType.setSeatTypeName(seatType.getSeatTypeName());
            SeatType updatedSeatType = seatTypeService.createOrUpdateSeatType(foundSeatType);
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(),
                    "Seat type updated successfully");
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to update seat type.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
