package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.TrainClassType;
import io.swagger.service.TrainClassTypeService;
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
public class TrainClassApiController implements TrainClassApi {

    private static final Logger log = LoggerFactory.getLogger(TrainClassApiController.class);

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final TrainClassTypeService trainClassTypeService;

    @Autowired
    public TrainClassApiController(ObjectMapper objectMapper, HttpServletRequest request,
            TrainClassTypeService trainClassTypeService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.trainClassTypeService = trainClassTypeService;
    }

    public ResponseEntity<ApiResponseMessage> addTrainClassType(
            @ApiParam(value = "Station object" ,required=true )  @Valid @RequestBody TrainClassType trainClassType) {
        log.debug("Received request to /trainClass/trainClass POST (addTrainClassType) with trainClassType=" + trainClassType);
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            trainClassType.setCreatedBy(userEmail);
            TrainClassType addedTrainClassType = trainClassTypeService.createOrUpdateTrainClassType(trainClassType);
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(), "Train class type created successfully");
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to add train class type.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponseMessage> deleteTrainClassType(
            @ApiParam(value = "ID of the train class type to delete",required=true) @PathVariable("classId") Integer classId) {
        log.debug("Received request to /trainClass/trainClass/{classId} DELETE (deleteTrainClassType) with classId=" + classId);
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            TrainClassType foundTrainClassType = trainClassTypeService.getTrainClassTypeById(classId).orElse(null);

            if (foundTrainClassType == null) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.NOT_FOUND.value(), "Train class type was not found.");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
            }

            boolean deleted = trainClassTypeService.deleteTrainClassType(classId);

            if (!deleted) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.CONFLICT.value(), "The Train class type is still in use and cannot be deleted.");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.CONFLICT);
            }

            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(),
                    "Train class type deleted successfully");
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to delete train class type.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponseMessage> getClasses() {
        log.debug("Received request to /trainClass/trainClasses GET (getClasses)");
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            List<TrainClassType> trainClassTypes = trainClassTypeService.getAllTrainClassTypes();

            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(), "Train class types retrieved successfully.", trainClassTypes);
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to retrieve train class types.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponseMessage> updateTrainClassType(
            @ApiParam(value = "ID of the train class type to update",required=true) @PathVariable("classId") Integer classId,
            @ApiParam(value = "Updated train class type object" ,required=true )  @Valid @RequestBody TrainClassType trainClassType) {
        log.debug("Received request to /trainClass/trainClass/{classId} PUT (updateTrainClassType) with classId=" + classId + " AND trainClassType=" + trainClassType);
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            TrainClassType foundTrainClassType = trainClassTypeService.getTrainClassTypeById(classId).orElse(null);

            if (foundTrainClassType == null) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.NOT_FOUND.value(), "Train class type was not found.");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
            }

            foundTrainClassType.setClassTypeName(trainClassType.getClassTypeName());
            foundTrainClassType.setCreatedBy(userEmail);
            TrainClassType updatedTrainClassType = trainClassTypeService.createOrUpdateTrainClassType(foundTrainClassType);
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(),
                    "Train class type updated successfully");
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to update train class type.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
