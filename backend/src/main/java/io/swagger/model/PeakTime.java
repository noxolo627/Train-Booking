package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "peaktimes")
public class PeakTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    @JsonProperty("peak_time_id")
    @ApiModelProperty(value = "")
    private Integer peakTimeId;

    @Column(name = "start_time", nullable = false)
    @ToString.Include
    @JsonProperty("start_time")
    @ApiModelProperty(value = "")
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    @ToString.Include
    @JsonProperty("end_time")
    @ApiModelProperty(value = "")
    private LocalTime endTime;

    @Column(name = "price_increase_percentage", nullable = false)
    @ToString.Include
    @JsonProperty("price_increase_percentage")
    private BigDecimal priceIncreasePercentage;

    @Column(name = "created_by", nullable = false)
    @JsonIgnore
    private String createdBy = "SYSTEM";

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
