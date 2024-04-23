package io.swagger.api;

import lombok.ToString;
import org.springframework.http.HttpStatus;

import javax.xml.bind.annotation.XmlTransient;
import java.time.Instant;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2023-07-20T12:56:46.917+02:00")

@javax.xml.bind.annotation.XmlRootElement
@ToString
public class ApiResponseMessage {
    Instant timestamp;
    int status;
    String type;
    String message;
    Object data = null;

    public ApiResponseMessage(){}

    public ApiResponseMessage(int status, String message){
        this.timestamp = Instant.now();
        this.status = status;
        this.type = getMessageFromHttpStatus(status);
        this.message = message;
    }

    public ApiResponseMessage(int status, String message, Object data){
        this.timestamp = Instant.now();
        this.status = status;
        this.type = getMessageFromHttpStatus(status);
        this.message = message;
        this.data = data;
    }

    private String getMessageFromHttpStatus(int status) {
        HttpStatus httpStatus = HttpStatus.valueOf(status);
        return httpStatus.getReasonPhrase();
    }

    @XmlTransient
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
