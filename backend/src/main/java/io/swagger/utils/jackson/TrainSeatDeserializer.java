package io.swagger.utils.jackson;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.model.SeatType;
import io.swagger.model.TrainClass;
import io.swagger.model.TrainClassType;
import io.swagger.model.TrainSeat;
import io.swagger.repository.TrainClassTypeRepository;
import io.swagger.repository.SeatTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrainSeatDeserializer extends JsonDeserializer<List<TrainSeat>> {

    private final TrainClassTypeRepository trainClassTypeRepository;
    private final SeatTypeRepository seatTypeRepository;

    public TrainSeatDeserializer(TrainClassTypeRepository trainClassTypeRepository, SeatTypeRepository seatTypeRepository) {
        this.trainClassTypeRepository = trainClassTypeRepository;
        this.seatTypeRepository = seatTypeRepository;
    }

    @Override
    public List<TrainSeat> deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        List<TrainSeat> trainSeats = new ArrayList<>();

        if (node.isArray()) {
            for (JsonNode seatNode : node) {
                TrainSeat trainSeat = new TrainSeat();

                if (seatNode.has("seat_id")) {
                    trainSeat.setSeatId(seatNode.get("seat_id").asInt());
                }

                trainSeat.setSeatNumber(seatNode.get("seat_number").asText());
                trainSeat.setBooked(seatNode.get("is_booked").asBoolean());

                JsonNode classTypeNode = seatNode.get("class_type");
                if (classTypeNode != null) {
                    int classId = classTypeNode.get("class_id").asInt();
                    Optional<TrainClassType> classTypeOptional = trainClassTypeRepository.findById(classId);

                    if (classTypeOptional.isPresent()) {
                        TrainClassType classType = classTypeOptional.get();

                        TrainClass trainClass = new TrainClass();
                        trainClass.setClassId(classId);
                        trainClass.setCapacity(classTypeNode.get("capacity").asInt());
                        trainClass.setBasePrice(classTypeNode.get("base_price").decimalValue());
                        trainClass.setClassType(classType);

                        trainSeat.setClassType(trainClass);
                    } else {
                        // Handle the case when the TrainClassType is not found for the given classId
                        // You can throw an exception, log a warning, or handle it in any other appropriate way.
                    }
                }

                JsonNode seatTypeNode = seatNode.get("seat_type");
                if (seatTypeNode != null) {
                    int seatTypeId = seatTypeNode.get("seat_type_id").asInt();
                    Optional<SeatType> seatTypeOptional = seatTypeRepository.findById(seatTypeId);

                    if (seatTypeOptional.isPresent()) {
                        SeatType seatType = seatTypeOptional.get();
                        trainSeat.setSeatType(seatType);
                    } else {
                        // Handle the case when the SeatType is not found for the given seatTypeId
                        // You can throw an exception, log a warning, or handle it in any other appropriate way.
                    }
                }

                trainSeats.add(trainSeat);
            }
        }

        return trainSeats;
    }
}
