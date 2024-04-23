package io.swagger.service;

import io.swagger.model.PeakTime;
import io.swagger.model.Train;
import io.swagger.model.TrainPeakTime;
import io.swagger.repository.PeakTimeRepository;
import io.swagger.repository.TrainPeakTimeRepository;
import io.swagger.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PeakTimeService {

    private final PeakTimeRepository peakTimeRepository;
    private final TrainPeakTimeRepository trainPeakTimeRepository;
    private final TrainRepository trainRepository;

    @Autowired
    public PeakTimeService(PeakTimeRepository peakTimeRepository, TrainPeakTimeRepository trainPeakTimeRepository,
                           TrainRepository trainRepository) {
        this.peakTimeRepository = peakTimeRepository;
        this.trainPeakTimeRepository = trainPeakTimeRepository;
        this.trainRepository = trainRepository;
    }

    public List<PeakTime> getAllPeakTimes() {
        return peakTimeRepository.findAll();
    }

    public Optional<PeakTime> getPeakTimeById(Integer peakTimeId) {
        return peakTimeRepository.findById(peakTimeId);
    }

    public PeakTime createOrUpdatePeakTime(PeakTime peakTime) {
        // check if it overlaps with an existing
        PeakTime existingPeakTime1 = findPeakTimeByTime(peakTime.getStartTime());
        PeakTime existingPeakTime2 = findPeakTimeByTime(peakTime.getEndTime());

        if (existingPeakTime1 != null || existingPeakTime2 != null) {
            return null;
        }

        peakTime = peakTimeRepository.save(peakTime);

        // check if peak time can be linked to any train
        List<Train> trains = trainRepository.findAll();
        for (Train train : trains) {
            TrainPeakTime trainPeakTime = train.getPeakTimes().get(0);

            // if train not linked to any peak time
            if (trainPeakTime.getPeakTime() == null) {
                trainPeakTime.setPeakTime(findPeakTimeByTime(train.getDepartureTime()));
                trainPeakTimeRepository.save(trainPeakTime);
            }
        }

        return peakTime;
    }

    public boolean deletePeakTime(Integer peakTimeId) {
        boolean isPeakTimeUsedInTrain = checkIfPeakTimeInTrain(peakTimeId);

        if (isPeakTimeUsedInTrain) {
            return false;
        }
        else {
            peakTimeRepository.deleteById(peakTimeId);
            return true;
        }
    }

    public PeakTime findPeakTimeByTime(LocalTime givenTime){
        List<PeakTime> allPeakTimes = this.getAllPeakTimes();

        return allPeakTimes.stream()
                .filter(peakTime -> peakTime.getStartTime().compareTo(givenTime) <= 0
                        && peakTime.getEndTime().compareTo(givenTime) >= 0)
                .findFirst().orElse(null);
    }

    private boolean checkIfPeakTimeInTrain(Integer peakTimeId) {
        return trainPeakTimeRepository.existsByPeakTimePeakTimeId(peakTimeId);
    }
}
