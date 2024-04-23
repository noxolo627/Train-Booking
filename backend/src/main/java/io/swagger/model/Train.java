package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.utils.jackson.StationDeserializer;
import io.swagger.utils.jackson.TrainClassDeserializer;
import io.swagger.utils.jackson.TrainPeakTimeDeserializer;
import io.swagger.utils.jackson.TrainSeatDeserializer;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "Train")
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    @JsonProperty("train_id")
    @Column(name = "train_id")
    @ApiModelProperty(value = "")
    private Integer trainId;

    @Column(name = "train_name",nullable = false)
    @ToString.Include
    @JsonProperty("train_name")
    @ApiModelProperty(value = "")
    private String trainName;

    @ToString.Include
    @ManyToOne
    @JoinColumn(name = "source_station", referencedColumnName = "station_id")
    @JsonProperty("source_station")
    @JsonDeserialize(using = StationDeserializer.class)
    private Station sourceStation;

    @ToString.Include
    @ManyToOne
    @JoinColumn(name = "destination_station", referencedColumnName = "station_id")
    @JsonProperty("destination_station")
    @JsonDeserialize(using = StationDeserializer.class)
    private Station destinationStation;

    @ToString.Include
    @Column(name = "travel_date")
    @JsonProperty("travel_date")
    private LocalDate travelDate;

    @ToString.Include
    @Column(name = "departure_time")
    @JsonProperty("departure_time")
    private LocalTime departureTime;

    @ToString.Include
    @OneToMany( orphanRemoval = true)
    @JoinColumn(name = "train_id", referencedColumnName = "train_id")
    @JsonProperty("peak_times")
    @JsonDeserialize(using = TrainPeakTimeDeserializer.class)
    private List<TrainPeakTime> peakTimes = null;

    @Column(name = "created_by")
    @JsonIgnore
    private String createdBy = "SYSTEM";

    @Column(name = "date_created")
    @JsonIgnore
    private Date dateCreated;

    @Column(name = "date_updated")
    @JsonIgnore
    private Date dateUpdated;

    @ToString.Include
    @OneToMany( mappedBy = "train", orphanRemoval = true)
    @JsonProperty("train_classes")
    @JsonDeserialize(using = TrainClassDeserializer.class)
    private List<TrainClass> trainClasses = new ArrayList<>();

    @ToString.Include
    @OneToMany( orphanRemoval = true)
    @JoinColumn(name = "train_id", referencedColumnName = "train_id")
    @JsonProperty("train_seats")
    @JsonDeserialize(using = TrainSeatDeserializer.class)
    private List<TrainSeat> trainSeats = new ArrayList<>();

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
