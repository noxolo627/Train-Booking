package io.swagger.service;

import io.swagger.model.Station;
import io.swagger.repository.StationRepository;
import io.swagger.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StationService {

	private final StationRepository stationRepository;
	private final TrainRepository trainRepository;

	@Autowired
	public StationService(StationRepository stationRepository, TrainRepository trainRepository) {
		this.stationRepository = stationRepository;
		this.trainRepository = trainRepository;
	}

	public List<Station> getAllStations() {
		return stationRepository.findAll();
	}

	public Optional<Station> getStationById(Integer stationId) {
		return stationRepository.findById(stationId);
	}

	public Station createOrUpdateStation(Station station) {
		Integer maxIdFromDatabase = stationRepository.findMaxStationId();
		int nextId = (maxIdFromDatabase != null) ? maxIdFromDatabase + 1 : 1;

		if (station.getStationId() == null) {
			station.setStationId(nextId);
		}
		return stationRepository.save(station);
	}

	public boolean deleteStation(Integer stationId) {
		boolean isStationUsedInTrain = checkIfStationUsedInTrain(stationId);

		if (isStationUsedInTrain) {
			return false;
		}
		else {
			stationRepository.deleteById(stationId);
			return true;
		}
	}

	private boolean checkIfStationUsedInTrain(Integer stationId) {
		return trainRepository.existsBySourceStationStationIdOrDestinationStationStationId(stationId, stationId);
	}

	public Station getStationByName(String stationName) {
		return stationRepository.findByStationName(stationName);
	}
}