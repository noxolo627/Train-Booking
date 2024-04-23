package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Station;
import io.swagger.service.StationService;
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
public class StationApiController implements StationApi {

    private static final Logger log = LoggerFactory.getLogger(StationApiController.class);

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final StationService stationService;

    @Autowired
    public StationApiController(ObjectMapper objectMapper, HttpServletRequest request,
            StationService stationService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.stationService = stationService;
    }

    public ResponseEntity<ApiResponseMessage> addStation(
            @ApiParam(value = "Station object", required = true) @Valid @RequestBody Station station) {
        log.debug("Received request to /station/station POST (addStation) with station=" + station);
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            station.setCreatedBy(userEmail);
            Station addedStation = stationService.createOrUpdateStation(station);
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(), "Station created successfully");
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to add station.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponseMessage> deleteStation(
            @ApiParam(value = "ID of the station to delete", required = true) @PathVariable("stationId") Integer stationId) {
        log.debug("Received request to /station/station/{stationId} DELETE (deleteStation) with stationId="
                + stationId);
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            Station foundStation = stationService.getStationById(stationId).orElse(null);

            if (foundStation == null) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.NOT_FOUND.value(), "Station was not found.");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
            }

            boolean deleted = stationService.deleteStation(stationId);

            if (!deleted) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.CONFLICT.value(), "The Station is still in use and cannot be deleted.");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.CONFLICT);
            }

            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(),
                    "Station deleted successfully");
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to delete station.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponseMessage> getStations() {
        log.debug("Received request to /station/stations GET (getStations)");
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            List<Station> stations = stationService.getAllStations();

            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(), "Stations retrieved successfully.", stations);
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to retrieve stations.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponseMessage> updateStation(
            @ApiParam(value = "ID of the station to update", required = true) @PathVariable("stationId") Integer stationId,
            @ApiParam(value = "Updated station object", required = true) @Valid @RequestBody Station station) {
        log.debug("Received request to /station/station/{stationId} PUT (updateStation) with stationId=" + stationId
                + " AND station=" + station);
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            Station foundStation = stationService.getStationById(stationId).orElse(null);

            if (foundStation == null) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.NOT_FOUND.value(), "Station was not found.");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
            }

            foundStation.setStationName(station.getStationName());
            foundStation.setCreatedBy(userEmail);
            Station updatedStation = stationService.createOrUpdateStation(foundStation);
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(), "Station updated successfully");
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to update station.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

