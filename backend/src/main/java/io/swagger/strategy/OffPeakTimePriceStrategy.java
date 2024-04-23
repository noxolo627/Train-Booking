package io.swagger.strategy;

import io.swagger.model.PeakTime;
import io.swagger.model.TrainClass;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OffPeakTimePriceStrategy implements TicketPriceStrategy {
    @Override
    public BigDecimal calculateTicketPrice(TrainClass trainClass, PeakTime peakTime) {
        return trainClass.getBasePrice();
    }
}

