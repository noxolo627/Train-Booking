package io.swagger.repository;

import io.swagger.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainRepository extends JpaRepository<Train, Integer> {

	boolean existsBySourceStationStationIdOrDestinationStationStationId(Integer stationId, Integer stationId2);

}
