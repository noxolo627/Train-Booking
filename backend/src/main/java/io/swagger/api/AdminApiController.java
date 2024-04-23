package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Admin;
import io.swagger.service.AdminService;
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
import java.util.Optional;

import static java.util.Optional.*;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2023-07-20T12:56:46.917+02:00")

@Controller
public class AdminApiController implements AdminApi {

    private static final Logger log = LoggerFactory.getLogger(AdminApiController.class);

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final AdminService adminService;

    @Autowired
    public AdminApiController(ObjectMapper objectMapper, HttpServletRequest request,
            AdminService adminService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.adminService = adminService;
    }

    public ResponseEntity<ApiResponseMessage> addAdmin(
            @ApiParam(value = "Admin object" ,required=true )  @Valid @RequestBody Admin admin) {
        String accept = request.getHeader("Accept");
        log.debug("Received request to /admin/admin POST (addAdmin) with admin=" + admin);
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            Admin addedAdmin = adminService.createOrUpdateAdmin(admin);
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(), "Admin created successfully");
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to add admin.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponseMessage> deleteAdmin(
            @ApiParam(value = "ID of the admin to delete",required=true) @PathVariable("adminId") Integer adminId) {
        String accept = request.getHeader("Accept");
        log.debug("Received request to /admin/admin/{adminId} DELETE (deleteAdmin) with adminId=" + adminId);
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            Admin foundAdmin = adminService.getAdminById(adminId).orElse(null);

            if (foundAdmin == null) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.NOT_FOUND.value(), "Admin was not found.");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
            }

            adminService.deleteAdmin(adminId);
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(),
                    "Admin deleted successfully");
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to delete admin.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponseMessage> getAdmins() {
        String accept = request.getHeader("Accept");
        log.debug("Received request to /admin/admins GET (getAdmins)");
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            List<Admin> admins = adminService.getAllAdmins();

            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(), "Admins retrieved successfully.", admins);
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to retrieve admins.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponseMessage> updateAdmin(
            @ApiParam(value = "ID of the admin to update",required=true) @PathVariable("adminId") Integer adminId,
            @ApiParam(value = "Updated admin object" ,required=true )  @Valid @RequestBody Admin admin) {
        String accept = request.getHeader("Accept");
        log.debug("Received request to /admin/admin/{adminId} PUT (updateAdmin) with adminId=" + adminId + " AND admin=" + admin);
        String userEmail = (String) request.getAttribute("user_email");
        log.debug("Request made by " + userEmail);

        try {
            Admin foundAdmin = adminService.getAdminById(adminId).orElse(null);

            if (foundAdmin == null) {
                ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.NOT_FOUND.value(), "Admin was not found.");
                log.debug("Response: " + responseMessage);
                return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
            }

            foundAdmin.setAdminEmail(admin.getAdminEmail());
            Admin updatedAdmin = adminService.createOrUpdateAdmin(foundAdmin);
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.OK.value(),
                    "Admin updated successfully");
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseMessage responseMessage = new ApiResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to update admin.", e.getMessage());
            log.debug("Response: " + responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
