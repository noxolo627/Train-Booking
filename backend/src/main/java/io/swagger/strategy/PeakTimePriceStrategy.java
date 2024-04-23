package io.swagger.strategy;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import io.swagger.model.PeakTime;
import io.swagger.model.TrainClass;

@Service
public class PeakTimePriceStrategy implements TicketPriceStrategy {
    @Override
    public BigDecimal calculateTicketPrice(TrainClass trainClass, PeakTime peakTime) {
        if (peakTime == null) {
            throw new IllegalArgumentException("PeakTime should not be null");
        }
        return trainClass.getBasePrice().multiply(peakTime.getPriceIncreasePercentage());
    }
}
