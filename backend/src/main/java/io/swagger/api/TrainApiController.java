package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Train;
import io.swagger.service.TrainSearchService;
import io.swagger.service.TrainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2023-07-21T17:12:22.488+02:00")

@Controller
public class TrainApiController implements TrainApi {

    private static final Logger log = LoggerFactory.getLogger(TrainApiController.class);

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final TrainService trainService;
    private final TrainSearchService trainSearchService;

    @Autowired
    public TrainApiController(ObjectMapper objectMapper, HttpServletRequest request,
            TrainService trainService, TrainSearchService trainSearchService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.trainService = trainService;
        this.trainSearchService = trainSearchService;
    }

    public ResponseEntity<ApiResponseMessage> addTrain(
            @ApiParam(value = "Train object" ,required=true )  @Valid @RequestBody Train train) {
        log.debug("Received request to /train/train POST (addTrain) with train=" + train);
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            train.setCreatedBy(userEmail);
            Train addedTrain = trainService.createOrUpdateTrain(train);

            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(), "Train created successfully");
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to add train.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponseMessage> deleteTrain(
            @ApiParam(value = "ID of the train to delete",required=true) @PathVariable("trainId") Integer trainId) {
        log.debug("Received request to /train/train/{trainId} DELETE (deleteTrain) with trainId=" + trainId);
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            Train foundTrain = trainService.getTrainById(trainId);

            if (foundTrain == null) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.NOT_FOUND.value(), "Train was not found.");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
            }

            boolean deleted = trainService.deleteTrain(foundTrain, trainId);

            if (!deleted) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.CONFLICT.value(), "The Train is still in use and cannot be deleted.");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.CONFLICT);
            }

            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(), "Train deleted successfully");
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to delete train.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponseMessage> getTrains() {
        String accept = request.getHeader("Accept");
        log.debug("Received request to /train/trains GET (getTrains)");
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            List<Train> trains = trainService.getAllTrains();

            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(), "Trains retrieved successfully", trains);
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to retrieve trains", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponseMessage> getTrainsBasedOnStation(
            @ApiParam(value = "The source station") @Valid @RequestParam(value = "from", required = false) String from,
            @ApiParam(value = "The destination station") @Valid @RequestParam(value = "to", required = false) String to,
            @ApiParam(value = "The travel date") @Valid @RequestParam(value = "date", required = false) Date date) {
        log.debug("Received request to /train/getTrainsBasedOnStation GET (getTrainsBasedOnStation) with from=" + from + " AND to=" + to + " AND date=" + date);
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            List<Train> searchResults = trainSearchService.search(from, to, date);

            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(), "Results retrieved successfully.", searchResults);
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error in searching for trains.");
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponseMessage> updateTrain(
            @ApiParam(value = "ID of the train to update",required=true) @PathVariable("trainId") Integer trainId,
            @ApiParam(value = "Updated train" ,required=true )  @Valid @RequestBody Train train) {
        log.debug("Received request to /train/train/{trainId} PUT (updateTrain) with =" + trainId + " AND train=" + train);
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);
        train.setCreatedBy(userEmail);

        ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.NOT_IMPLEMENTED.value(), "Not implemented");
        log.debug("Response: " + responseMessage);
        return new ResponseEntity<>(responseMessage, HttpStatus.NOT_IMPLEMENTED);
    }

}
