package io.swagger.api;

import io.swagger.annotations.*;
import io.swagger.model.Station;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2023-07-20T12:56:46.917+02:00")
@Validated
@Api(value = "station", description = "the station API")
@RequestMapping(value = "")
public interface StationApi {

    @ApiOperation(value = "Add a new station", nickname = "addStation", notes = "", tags = { "station", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Station created successfully") })
    @RequestMapping(value = "/station/station", produces = { "application/json" }, consumes = { "application/json" }, method = RequestMethod.POST)
    ResponseEntity<ApiResponseMessage> addStation(
            @ApiParam(value = "Station object", required = true) @Valid @RequestBody Station station);

    @ApiOperation(value = "Delete a station", nickname = "deleteStation", notes = "", tags = { "station", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Station deleted successfully"),
            @ApiResponse(code = 400, message = "Invalid request data"),
            @ApiResponse(code = 404, message = "Station not found") })
    @RequestMapping(value = "/station/station/{stationId}", method = RequestMethod.DELETE)
    ResponseEntity<ApiResponseMessage> deleteStation(
            @ApiParam(value = "ID of the station to delete", required = true) @PathVariable("stationId") Integer stationId);

    @ApiOperation(value = "Get all stations", nickname = "getStations", notes = "", response = Station.class, responseContainer = "List", tags = {
            "station", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successful operation", response = Station.class, responseContainer = "List") })
    @RequestMapping(value = "/station/stations", produces = { "application/json" }, method = RequestMethod.GET)
    ResponseEntity<ApiResponseMessage> getStations();

    @ApiOperation(value = "Update a station", nickname = "updateStation", notes = "", tags = { "station", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Station updated successfully"),
            @ApiResponse(code = 400, message = "Invalid request data"),
            @ApiResponse(code = 404, message = "Station not found") })
    @RequestMapping(value = "/station/station/{stationId}", method = RequestMethod.PUT)
    ResponseEntity<ApiResponseMessage> updateStation(
            @ApiParam(value = "ID of the station to update", required = true) @PathVariable("stationId") Integer stationId,
            @ApiParam(value = "Updated station object", required = true) @Valid @RequestBody Station station);
}
