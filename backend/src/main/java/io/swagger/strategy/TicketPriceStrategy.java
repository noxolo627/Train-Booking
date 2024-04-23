package io.swagger.strategy;

import java.math.BigDecimal;

import io.swagger.model.PeakTime;
import io.swagger.model.TrainClass;

public interface TicketPriceStrategy {
    BigDecimal calculateTicketPrice(TrainClass trainClass, PeakTime peakTime);
}
