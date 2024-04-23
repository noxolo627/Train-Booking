package io.swagger.service;

import io.swagger.model.Train;
import io.swagger.observer.TrainSearchObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Service
public class TrainSearchService {

    private final TrainService trainService;

    private final List<TrainSearchObserver> observers = new ArrayList<>();

    @org.springframework.beans.factory.annotation.Autowired
    public TrainSearchService(TrainService trainService) {
        this.trainService = trainService;
    }

    public void addObserver(TrainSearchObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(TrainSearchObserver observer) {
        observers.remove(observer);
    }

    public List<Train> search(String source, String destination, Date travelDate) {
        List<Train> trains = trainService.getAllTrains();
        List<Train> searchResults = new ArrayList<>();

        for (Train train : trains) {
            if ((source == null || source.isEmpty() || train.getSourceStation().getStationName().toLowerCase().contains(source.toLowerCase()))
                    && (destination == null || destination.isEmpty() || train.getDestinationStation().getStationName().toLowerCase().contains(destination.toLowerCase()))
                    && (travelDate == null || train.getTravelDate().equals(travelDate))) {
                searchResults.add(train);
            }
        }

        // Notify observers with the search results
        notifyObservers(searchResults);

        return searchResults;
    }

    private void notifyObservers(List<Train> results) {
        for (TrainSearchObserver observer : observers) {
            observer.update(results);
        }
    }
}