package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "Admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    @JsonProperty("admin_id")
    @ApiModelProperty(value = "")
    private Integer adminId;

    @Column(name = "email", nullable = false)
    @ToString.Include
    @JsonProperty("admin_email")
    @ApiModelProperty(value = "")
    private String adminEmail;

    @Column(name = "created_by", nullable = false)
    @JsonIgnore
    private final String createdBy = "SYSTEM";

    @Column(name = "date_created", nullable = false)
    @JsonIgnore
    private Date dateCreated;

    @Column(name = "date_updated", nullable = false)
    @JsonIgnore
    private Date dateUpdated;
    
    @PrePersist
    protected void onCreate() {
      dateCreated = new Date();
      dateUpdated = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
      dateUpdated = new Date();
    }
}
