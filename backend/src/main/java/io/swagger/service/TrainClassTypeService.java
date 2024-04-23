package io.swagger.service;

import io.swagger.model.TrainClassType;
import io.swagger.repository.TrainClassTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainClassTypeService {

    private final TrainClassTypeRepository trainClassTypeRepository;

    @Autowired
    public TrainClassTypeService(TrainClassTypeRepository trainClassTypeRepository) {
        this.trainClassTypeRepository = trainClassTypeRepository;
    }

    public List<TrainClassType> getAllTrainClassTypes() {
        return trainClassTypeRepository.findAll();
    }

    public Optional<TrainClassType> getTrainClassTypeById(Integer classTypeId) {
        return trainClassTypeRepository.findById(classTypeId);
    }

    public TrainClassType getTrainClassTypeByName(String classTypeName) {
        return trainClassTypeRepository.findByClassTypeName(classTypeName);
    }

    public TrainClassType createOrUpdateTrainClassType(TrainClassType trainClassType) {
        Integer maxIdFromDatabase = trainClassTypeRepository.findMaxClassTypeId();
        int nextId = (maxIdFromDatabase != null) ? maxIdFromDatabase + 1 : 1;

        if (trainClassType.getClassTypeId() == null) {
            trainClassType.setClassTypeId(nextId);
        }
        return trainClassTypeRepository.save(trainClassType);
    }

    public boolean deleteTrainClassType(Integer classTypeId) {
        boolean isTrainClassUsedInTrain = checkIfTrainClassUsedInTrain(classTypeId);

        if (isTrainClassUsedInTrain) {
            return false;
        }
        else {
            trainClassTypeRepository.deleteById(classTypeId);
            return true;
        }
    }

    private boolean checkIfTrainClassUsedInTrain(Integer trainClassTypeId) {
        return trainClassTypeRepository.existsByClassTypeId(trainClassTypeId);
    }

    public TrainClassType saveTrainClassType(TrainClassType trainClassType) {
        return trainClassTypeRepository.save(trainClassType);
    }
}

