package io.swagger.repository;

import io.swagger.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer> {

    @Query(value = "SELECT MAX(passenger_id) FROM Passenger", nativeQuery = true)
    Integer findMaxPassengerId();
}
