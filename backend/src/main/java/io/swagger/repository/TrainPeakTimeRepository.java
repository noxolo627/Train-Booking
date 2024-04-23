package io.swagger.repository;

import io.swagger.model.TrainPeakTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainPeakTimeRepository extends JpaRepository<TrainPeakTime, Integer> {
    boolean existsByPeakTimePeakTimeId(Integer peakTimeId);

}
