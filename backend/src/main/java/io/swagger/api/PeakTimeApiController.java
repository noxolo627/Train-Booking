package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.PeakTime;
import io.swagger.service.PeakTimeService;
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
public class PeakTimeApiController implements PeakTimeApi {

    private static final Logger log = LoggerFactory.getLogger(PeakTimeApiController.class);

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final PeakTimeService peakTimeService;

    @Autowired
    public PeakTimeApiController(ObjectMapper objectMapper, HttpServletRequest request,
             PeakTimeService peakTimeService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.peakTimeService = peakTimeService;
    }

    public ResponseEntity<ApiResponseMessage> addPeakTime(
            @ApiParam(value = "Peak time object" ,required=true )  @Valid @RequestBody PeakTime peakTime) {
        String accept = request.getHeader("Accept");
        log.debug("Received request to /peakTime/peakTime POST (addPeakTime) with peakTime=" + peakTime);
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            // check if start_time < end_time
            if (peakTime.getStartTime().compareTo(peakTime.getEndTime()) >= 0) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.BAD_REQUEST.value(), "Start time should be less than end time.");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
            }

            peakTime.setCreatedBy(userEmail);
            PeakTime addedPeakTime = peakTimeService.createOrUpdatePeakTime(peakTime);

            if (addedPeakTime == null) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.CONFLICT.value(), "Peak time overlaps with an existing peak time.");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.CONFLICT);
            }

            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(), "Peak time created successfully");
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to add peak time.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponseMessage> deletePeakTime(
            @ApiParam(value = "ID of the peak time to delete",required=true) @PathVariable("peakTimeId") Integer peakTimeId) {
        String accept = request.getHeader("Accept");
        log.debug("Received request to /peakTime/peakTime/{peakTimeId} DELETE (deletePeakTime) with peakTimeId=" + peakTimeId);
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            PeakTime foundPeakTime = peakTimeService.getPeakTimeById(peakTimeId).orElse(null);

            if (foundPeakTime == null) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.NOT_FOUND.value(), "Peak time was not found.");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
            }

            boolean deleted = peakTimeService.deletePeakTime(peakTimeId);

            if (!deleted) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.CONFLICT.value(), "The Peak time is still in use and cannot be deleted.");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.CONFLICT);
            }

            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(),
                    "Peak time deleted successfully");
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to delete peak time.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponseMessage> getpeakTimes() {
        String accept = request.getHeader("Accept");
        log.debug("Received request to /peakTime/peakTimes GET (getpeakTimes)");
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            List<PeakTime> peakTimes = peakTimeService.getAllPeakTimes();

            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(), "Peak times retrieved successfully.", peakTimes);
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to retrieve peak times.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponseMessage> updatePeakTime(
            @ApiParam(value = "ID of the peak time to update",required=true) @PathVariable("peakTimeId") Integer peakTimeId,
            @ApiParam(value = "Updated peak time object" ,required=true )  @Valid @RequestBody PeakTime peakTime) {
        String accept = request.getHeader("Accept");
        log.debug("Received request to /peakTime/peakTime/{peakTimeId} PUT (updatePeakTime) with peakTimeId=" + peakTimeId + " and peakTime=" + peakTime);
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        peakTime.setCreatedBy(userEmail);

        // TODO: the code needs to be edited to (1) check no overlap with the peak times, (2) check if this peak time mathes with any train and update that
        /*
        try {
            PeakTime foundPeakTime = peakTimeService.getPeakTimeById(peakTimeId).orElse(null);

            if (foundPeakTime == null) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.NOT_FOUND.value(), "Peak time was not found.");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
            }

            foundPeakTime.setStartTime(peakTime.getStartTime());
            foundPeakTime.setEndTime(peakTime.getEndTime());
            foundPeakTime.setPriceIncreasePercentage(peakTime.getPriceIncreasePercentage());
            PeakTime updatedPeakTime = peakTimeService.createOrUpdatePeakTime(foundPeakTime);
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(),
                    "Peak time updated successfully");
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to update peak time.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
         */

        ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.NOT_IMPLEMENTED.value(), "Not implemented.");
        log.debug("Response: " + responseMessage);
        return new ResponseEntity<>(responseMessage, HttpStatus.NOT_IMPLEMENTED);
    }

}
