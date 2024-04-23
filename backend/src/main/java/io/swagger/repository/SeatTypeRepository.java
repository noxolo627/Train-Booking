package io.swagger.repository;

import io.swagger.model.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatTypeRepository extends JpaRepository<SeatType, Integer> {

    @Query(value = "SELECT MAX(seat_type_id) FROM SeatType", nativeQuery = true)
    Integer findMaxSeatTypeId();

    boolean existsBySeatTypeId(Integer seatTypeId);
}
