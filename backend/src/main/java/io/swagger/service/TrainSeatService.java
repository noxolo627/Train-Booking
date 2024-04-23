package io.swagger.service;

import io.swagger.model.TrainSeat;
import io.swagger.repository.TrainSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainSeatService {

    private final TrainSeatRepository trainSeatRepository;

    @Autowired
    public TrainSeatService(TrainSeatRepository trainSeatRepository) {
        this.trainSeatRepository = trainSeatRepository;
    }

    public List<TrainSeat> getAllTrainSeats() {
        return trainSeatRepository.findAll();
    }

    public TrainSeat getTrainSeatById(Integer seatId) {
        return trainSeatRepository.findById(seatId).orElse(null);
    }

    public TrainSeat createOrUpdateTrainSeat(TrainSeat trainSeat) {
        Integer maxIdFromDatabase = trainSeatRepository.findMaxSeatId();
        int nextId = (maxIdFromDatabase != null) ? maxIdFromDatabase + 1 : 1;

        if (trainSeat.getSeatId() == null) {
            trainSeat.setSeatId(nextId);
        }
        return trainSeatRepository.save(trainSeat);
    }

    public void deleteTrainSeat(Integer seatId) {
        trainSeatRepository.deleteById(seatId);
    }
}
