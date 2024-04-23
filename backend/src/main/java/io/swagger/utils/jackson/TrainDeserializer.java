package io.swagger.utils.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.model.*;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.math.BigDecimal;

public class TrainDeserializer extends JsonDeserializer<Train> {
    @Override
    public Train deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);

        Train train = new Train();

        if (node.hasNonNull("train_id")) {
            train.setTrainId(node.get("train_id").asInt());
        }
        train.setTrainName(node.get("train_name").asText());

        Station sourceStation = new Station();
        sourceStation.setStationId(node.get("source_station").get("station_id").asInt());
        sourceStation.setStationName(node.get("source_station").get("station_name").asText());
        train.setSourceStation(sourceStation);

        Station destinationStation = new Station();
        destinationStation.setStationId(node.get("destination_station").get("station_id").asInt());
        destinationStation.setStationName(node.get("destination_station").get("station_name").asText());
        train.setDestinationStation(destinationStation);

        train.setTravelDate(LocalDate.parse(node.get("travel_date").asText()));
        train.setDepartureTime(Time.valueOf(node.get("departure_time").asText()).toLocalTime());

        // Handle train_classes
        if (node.hasNonNull("train_classes")) {
            for (JsonNode trainClassNode : node.get("train_classes")) {
                TrainClass trainClass = new TrainClass();
                if (trainClassNode.hasNonNull("class_id")) {
                    trainClass.setClassId(trainClassNode.get("class_id").asInt());
                }
                TrainClassType trainClassType = new TrainClassType();
                trainClassType.setClassTypeId(trainClassNode.get("class_type").get("class_type_id").asInt());
                trainClassType.setClassTypeName(trainClassNode.get("class_type").get("class_type_name").asText());
                trainClass.setClassType(trainClassType);
                trainClass.setCapacity(trainClassNode.get("capacity").asInt());
                trainClass.setBasePrice(new BigDecimal(trainClassNode.get("base_price").asText()));

                train.getTrainClasses().add(trainClass);
            }
        }

        // Handle train_seats
        if (node.hasNonNull("train_seats")) {
            for (JsonNode trainSeatNode : node.get("train_seats")) {
                TrainSeat trainSeat = new TrainSeat();
                trainSeat.setSeatId(trainSeatNode.get("seat_id").asInt());
                trainSeat.setSeatNumber(trainSeatNode.get("seat_number").asText());
                trainSeat.setBooked(trainSeatNode.get("is_booked").asBoolean());

                SeatType seatType = new SeatType();
                seatType.setSeatTypeId(trainSeatNode.get("seat_type").get("seatTypeId").asInt());
                seatType.setSeatTypeName(trainSeatNode.get("seat_type").get("seatTypeName").asText());
                trainSeat.setSeatType(seatType);

                train.getTrainSeats().add(trainSeat);
            }
        }

        return train;
    }
}
