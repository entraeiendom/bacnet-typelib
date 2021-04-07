package no.entra.bacnet.sensor;

import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.objects.ObjectType;

import java.util.Arrays;
import java.util.List;

import static no.entra.bacnet.objects.ObjectType.*;

public class SensorId extends ObjectId {
    List<ObjectType> validSensorTypes = Arrays.asList(AnalogValue, AnalogInput, BinaryValue, BinaryInput);

    public SensorId(ObjectType sensorType, Integer instanceNumber) {
        if (!validSensorTypes.contains(sensorType)) {
            throw new RuntimeException("SensorId must be either of " + validSensorTypes.toString());
        }
        setObjectType(sensorType);
        setInstanceNumber(instanceNumber);
    }
}
