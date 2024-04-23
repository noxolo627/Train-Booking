package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.util.Date;

@Entity
@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name="Station")
public class Station {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  @ToString.Include
  @JsonProperty("station_id")
  @Column(name="station_id")
  @ApiModelProperty(value = "")
  private Integer stationId;

  @Column(name="station_name",nullable = false)
  @ToString.Include
  @JsonProperty("station_name")
  @ApiModelProperty(value = "")
  private String stationName;

  @Column(name="created_by",nullable = false)
  @JsonIgnore
  private String createdBy = "SYSTEM";

  @Column(name="date_created",nullable = false)
  @JsonIgnore
  private Date dateCreated;

  @Column(name="date_updated",nullable = false)
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
