package io.swagger.observer;

import io.swagger.model.Train;

import java.util.List;

public interface TrainSearchObserver {
    void update(List<Train> results);
}