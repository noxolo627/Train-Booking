package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.utils.jackson.TrainClassDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "trainclass")
@JsonDeserialize(using = TrainClassDeserializer.class)
public class TrainClass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@ToString.Include
	@Column(name = "class_id")
	@JsonProperty("class_id")
	@ApiModelProperty(value = "")
	private Integer classId;

	@ManyToOne
	@JoinColumn(name = "train_id", nullable = false)
	@JsonIgnore
	private Train train;

	@ManyToOne
	@JoinColumn(name = "class_type_id", nullable = false)
	@ApiModelProperty(value = "")
	@JsonProperty("class_type")
	@ToString.Include
	private TrainClassType classType;

	@Column(name = "capacity", nullable = false)
	@ApiModelProperty(value = "")
	@JsonProperty("capacity")
	@ToString.Include
	private Integer capacity;

	@Column(name = "base_price", nullable = false)
	@ApiModelProperty(value = "")
	@JsonProperty("base_price")
	@ToString.Include
	private BigDecimal basePrice;

	@Column(name = "date_created")
	@JsonIgnore
	private Date dateCreated;

	@Column(name = "date_updated")
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

	public void setClassType(TrainClassType classType) {
		this.classType = classType;
	}
}
