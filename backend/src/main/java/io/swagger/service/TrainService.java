package io.swagger.service;

import io.swagger.model.Train;
import io.swagger.model.TrainClass;
import io.swagger.model.TrainPeakTime;
import io.swagger.model.TrainSeat;
import io.swagger.repository.BookingRepository;
import io.swagger.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TrainService {

    private final BookingRepository bookingRepository;
    private final TrainRepository trainRepository;
    private final TrainClassService trainClassService;
    private final TrainSeatService trainSeatService;
    private final JdbcTemplate jdbcTemplate;
    private final PeakTimeService peakTimeService;
    private final TrainPeakTimeService trainPeakTimeService;


    @Autowired
    public TrainService(BookingRepository bookingRepository, TrainRepository trainRepository,
                        TrainClassService trainClassService, TrainSeatService trainSeatService,
                        JdbcTemplate jdbcTemplate, PeakTimeService peakTimeService,
                        TrainPeakTimeService trainPeakTimeService) {
        this.bookingRepository = bookingRepository;
        this.trainRepository = trainRepository;
        this.trainClassService = trainClassService;
        this.trainSeatService = trainSeatService;
        this.jdbcTemplate = jdbcTemplate;
        this.peakTimeService = peakTimeService;
        this.trainPeakTimeService = trainPeakTimeService;
    }

    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    public Train getTrainById(Integer trainId) {
        return trainRepository.findById(trainId).orElse(null);
    }
    
    public Train createOrUpdateTrain(Train train) {
    	train = trainRepository.save(train);

        // adding train class
        for (TrainClass trainClass: train.getTrainClasses()) {
            trainClass.setTrain(train);
            trainClass = trainClassService.createOrUpdateTrainClass(trainClass);
        }

        // adding train seats
        callAddSeatsStoredProcedure(train.getTrainId());

        // adding peak time
        TrainPeakTime trainPeakTime = new TrainPeakTime();
        trainPeakTime.setTrain(train);
        trainPeakTime.setPeakTime(peakTimeService.findPeakTimeByTime(train.getDepartureTime()));
        trainPeakTimeService.saveTrainPeakTime(trainPeakTime);

        List<TrainPeakTime> trainPeakTimes = new ArrayList<>();
        trainPeakTimes.add(trainPeakTime);
        train.setPeakTimes(trainPeakTimes);

        return train;
    }

    public boolean deleteTrain(Train train, Integer trainId) {
        boolean isTrainUsedInBooking = checkIfTrainUsedInBooking(trainId);

        if (isTrainUsedInBooking) {
            return false;
        }
        else {
            for (TrainPeakTime trainPeakTime : train.getPeakTimes()) {
                trainPeakTimeService.deleteTrainPeakTime(trainPeakTime.getTrainPeakTimeId());
            }
            for (TrainClass trainClass : train.getTrainClasses()) {
                trainClassService.deleteTrainClass(trainClass.getClassId());
            }
            for (TrainSeat trainSeat : train.getTrainSeats()) {
                trainSeatService.deleteTrainSeat(trainSeat.getSeatId());
            }
            train.setTrainClasses(null);
            train.setTrainSeats(null);
            train.setPeakTimes(null);
            trainRepository.deleteById(trainId);
            return true;
        }
    }

    private boolean checkIfTrainUsedInBooking(Integer trainId) {
        return bookingRepository.existsByTrainId(trainId);
    }

    private void callAddSeatsStoredProcedure(int trainId) {
        String storedProcedureCall = "{call AddSeats(?)}";

        jdbcTemplate.update(storedProcedureCall, trainId);
    }
}
