package io.swagger.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.swagger.api.AdminApiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
@Service
public class TokenValidationService implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AdminApiController.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("Validating jwt token");
        String token = extractTokenFromRequest(request);
        boolean isTokenValid = validateToken(token);

        if (isTokenValid) {
            log.debug("jwt token is valid");

            String userEmail = getEmail(token);
            request.setAttribute("user_email", userEmail);

            return true;
        }
        else {
            log.debug("jwt token is invalid");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("User is unauthorized.");

            return false;
        }
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Extract the token from "Bearer <token>"
        }

        return null; // Return null if no token is found
    }

    public boolean validateToken(String token){
        try{
            DecodedJWT jwt = JWT.decode(token);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            if(formatter.format(jwt.getExpiresAt()).compareTo(formatter.format(new Date())) > 0){
                String issuer = jwt.getIssuer(); //Have to make this configurable
                String validIssuer = System.getenv("VALID_ISSUER");
                return validateIssuer(issuer, validIssuer);
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            return false;
        }
    }
    private boolean validateIssuer(String issuer, String validIssuer){
        return issuer.equals(validIssuer);
    }

    public String getEmail(String token){
        DecodedJWT jwt = JWT.decode(token);
        Claim claim = jwt.getClaim("preferred_username");
        return claim.toString().replaceAll("\"", "");
    }
}