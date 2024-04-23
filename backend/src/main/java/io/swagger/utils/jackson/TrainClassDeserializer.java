package io.swagger.utils.jackson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.model.TrainClass;
import io.swagger.model.TrainClassType;
import io.swagger.repository.TrainClassTypeRepository;

public class TrainClassDeserializer extends JsonDeserializer<List<TrainClass>> {

    private final TrainClassTypeRepository trainClassTypeRepository;

    public TrainClassDeserializer(TrainClassTypeRepository trainClassTypeRepository) {
        this.trainClassTypeRepository = trainClassTypeRepository;
    }

    @Override
    public List<TrainClass> deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        List<TrainClass> trainClasses = new ArrayList<>();

        if (node.isArray()) {
            for (JsonNode classNode : node) {
                TrainClass trainClass = new TrainClass();
                if (classNode.has("class_id")) {
                    trainClass.setClassId(classNode.get("class_id").asInt());
                }
                trainClass.setCapacity(classNode.get("capacity").asInt());
                trainClass.setBasePrice(classNode.get("base_price").decimalValue());

                JsonNode classTypeNode = classNode.get("class_type");
                if (classTypeNode != null) {
                    String classTypeName = classTypeNode.get("class_type_name").asText();
                    TrainClassType classType = trainClassTypeRepository.findByClassTypeName(classTypeName);
                    trainClass.setClassType(classType);
                }

                trainClasses.add(trainClass);
            }
        }

        return trainClasses;
    }
}
