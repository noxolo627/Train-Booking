package io.swagger.service;

import io.swagger.model.TrainClass;
import io.swagger.model.TrainClassType;
import io.swagger.models.auth.In;
import io.swagger.repository.TrainClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainClassService {

    private final TrainClassRepository trainClassRepository;

    @Autowired
    public TrainClassService(TrainClassRepository trainClassRepository) {
        this.trainClassRepository = trainClassRepository;
    }

    public List<TrainClass> getAllTrainClasses() {
        return trainClassRepository.findAll();
    }

    public Optional<TrainClass> getTrainClassById(Integer classId) {
        return trainClassRepository.findById(classId);
    }

    public TrainClass getTrainClassByTrainClassType(String trainClassTypeName, Integer trainId) {
        return trainClassRepository.findByClassTypeClassTypeNameAndTrainTrainId(trainClassTypeName, trainId).orElse(null);
    }

    public TrainClass createOrUpdateTrainClass(TrainClass trainClass) {
        Integer maxIdFromDatabase = trainClassRepository.findMaxClassId();
        int nextId = (maxIdFromDatabase != null) ? maxIdFromDatabase + 1 : 1;

        if (trainClass.getClassId() == null) {
            trainClass.setClassId(nextId);
        }
        return trainClassRepository.save(trainClass);
    }

    public void deleteTrainClass(Integer classId) {
        trainClassRepository.deleteById(classId);
    }
}
