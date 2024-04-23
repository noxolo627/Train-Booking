package io.swagger.repository;

import io.swagger.model.TrainSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainSeatRepository extends JpaRepository<TrainSeat, Integer> {

    @Query(value = "SELECT MAX(seat_id) FROM Seat", nativeQuery = true)
    Integer findMaxSeatId();
}
