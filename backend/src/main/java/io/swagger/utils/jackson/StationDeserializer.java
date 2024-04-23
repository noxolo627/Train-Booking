package io.swagger.utils.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.model.Station;
import io.swagger.repository.StationRepository;

import java.io.IOException;

public class StationDeserializer extends JsonDeserializer<Station> {

    private final StationRepository stationRepository;

    public StationDeserializer(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Override
    public Station deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Integer stationId = (node.get("station_id") != null) ? node.get("station_id").asInt() : null;
        String stationName = (node.get("station_name") != null) ? node.get("station_name").asText() : null;

        if (stationId == null) {
            return stationRepository.findByStationName(stationName);
        }
        
        Station station = new Station();
        station.setStationId(stationId);
        station.setStationName(stationName);
        return station;
    }
}


