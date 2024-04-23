package io.swagger.utils.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.model.TrainPeakTime;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TrainPeakTimeDeserializer extends JsonDeserializer<List<TrainPeakTime>> {

    @Override
    public List<TrainPeakTime> deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        List<TrainPeakTime> trainPeakTimes = new ArrayList<>();

        if (node.isArray()) {
            for (JsonNode peakTimeNode : node) {
                TrainPeakTime trainPeakTime = new TrainPeakTime();

           
                if (peakTimeNode.has("train_id")) {
                    trainPeakTime.setTrainPeakTimeId(peakTimeNode.get("train_id").asInt());;
                }

                if (peakTimeNode.has("peak_time_id")) {
                    trainPeakTime.setTrainPeakTimeId(peakTimeNode.get("peak_time_id").asInt());
                }

                trainPeakTime.setDateCreated(LocalDateTime.now());
                trainPeakTime.setDateUpdated(LocalDateTime.now());

                trainPeakTimes.add(trainPeakTime);
            }
        }

        return trainPeakTimes;
    }
}
