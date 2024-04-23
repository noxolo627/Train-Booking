package io.swagger.repository;

import io.swagger.model.PeakTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PeakTimeRepository extends JpaRepository<PeakTime, Integer> {

    @Query(value = "SELECT MAX(peak_time_id) FROM PeakTimes", nativeQuery = true)
    Integer findMaxPeakTimeId();
}
