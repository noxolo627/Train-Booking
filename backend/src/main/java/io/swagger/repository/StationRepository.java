package io.swagger.repository;

import io.swagger.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<Station, Integer> {

	@Query(value = "SELECT MAX(station_id) FROM Station", nativeQuery = true)
	Integer findMaxStationId();

	Station findByStationName(String stationName);
}