package io.swagger.strategy;

import io.swagger.model.PeakTime;
import io.swagger.model.TrainClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TicketPriceCalculator {

    private TicketPriceStrategy offPeakTimePriceStrategy = new OffPeakTimePriceStrategy();
    private TicketPriceStrategy peakTimePriceStrategy = new PeakTimePriceStrategy();

    public BigDecimal calculateTicketPrice(TrainClass trainClass, PeakTime peakTime) {
        if (peakTime == null) {
            return offPeakTimePriceStrategy.calculateTicketPrice(trainClass, peakTime);
        }
        return peakTimePriceStrategy.calculateTicketPrice(trainClass, peakTime);
    }
}
