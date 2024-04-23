package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.utils.jackson.TrainSeatDeserializer;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "Passenger")
public class Passenger {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@ToString.Include
	@JsonProperty("passenger_id")
	private Integer passengerId;

	@JsonProperty("seat_id")
	@Column(name = "seat_id")
	@ToString.Include
	private Integer seatId;

	@JsonProperty("passenger_name")
	@ToString.Include
	@Column(name = "passenger_name", nullable = false)
	private String passengerName;

	@JsonProperty("passenger_age")
	@ToString.Include
	@Column(name = "age")
	private Integer age;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "booking_id")
	@JsonIgnore
	private Booking booking;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "seat_id")
	@JsonProperty("train_seat")
	@JsonDeserialize(using = TrainSeatDeserializer.class)
	@ToString.Include
	private List<TrainSeat> seats;

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
