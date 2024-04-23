package io.swagger.service;

import io.swagger.model.SeatType;
import io.swagger.repository.SeatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatTypeService {

    private final SeatTypeRepository seatTypeRepository;

    @Autowired
    public SeatTypeService(SeatTypeRepository seatTypeRepository) {
        this.seatTypeRepository = seatTypeRepository;
    }

    public List<SeatType> getAllSeatTypes() {
        return seatTypeRepository.findAll();
    }

    public Optional<SeatType> getSeatTypeById(Integer seatTypeId) {
        return seatTypeRepository.findById(seatTypeId);
    }

    public SeatType createOrUpdateSeatType(SeatType seatType) {
        Integer maxIdFromDatabase = seatTypeRepository.findMaxSeatTypeId();
        int nextId = (maxIdFromDatabase != null) ? maxIdFromDatabase + 1 : 1;

        if (seatType.getSeatTypeId() == null) {
            seatType.setSeatTypeId(nextId);
        }
        return seatTypeRepository.save(seatType);
    }

    public boolean deleteSeatType(Integer seatTypeId) {
        boolean isSeatTypeUsedInTrain = checkIfSeatTypeInTrain(seatTypeId);

        if (isSeatTypeUsedInTrain) {
            return false;
        }
        else {
            seatTypeRepository.deleteById(seatTypeId);
            return true;
        }
    }

    private boolean checkIfSeatTypeInTrain(Integer seatTypeId) {
        return seatTypeRepository.existsBySeatTypeId(seatTypeId);
    }
}
