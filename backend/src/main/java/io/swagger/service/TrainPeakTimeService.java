package io.swagger.service;

import io.swagger.model.TrainPeakTime;
import io.swagger.repository.TrainPeakTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainPeakTimeService {

    private final TrainPeakTimeRepository trainPeakTimeRepository;

    @Autowired
    public TrainPeakTimeService(TrainPeakTimeRepository trainPeakTimeRepository) {
        this.trainPeakTimeRepository = trainPeakTimeRepository;
    }

    public TrainPeakTime saveTrainPeakTime(TrainPeakTime trainPeakTime) {
        return trainPeakTimeRepository.save(trainPeakTime);
    }

    public TrainPeakTime findTrainPeakTimeById(Integer trainPeakTimeId) {
        return trainPeakTimeRepository.findById(trainPeakTimeId).orElse(null);
    }

    public void deleteTrainPeakTime(Integer trainPeakTimeId) {
        trainPeakTimeRepository.deleteById(trainPeakTimeId);
    }

}
