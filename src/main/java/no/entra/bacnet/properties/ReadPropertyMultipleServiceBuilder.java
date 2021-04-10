package no.entra.bacnet.properties;

import no.entra.bacnet.device.DeviceId;
import no.entra.bacnet.internal.objects.ObjectProperties;
import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.objects.ObjectType;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static no.entra.bacnet.objects.ObjectType.*;

public final class ReadPropertyMultipleServiceBuilder {
    private Integer invokeId = null;
    private Set<ObjectProperties> objectProperties = null;
    private Set<PropertyReference> propertyReferences = null;
    private DeviceId deviceId = null;

    public ReadPropertyMultipleServiceBuilder() {
    }

    public ReadPropertyMultipleServiceBuilder(Integer invokeId) {
        this.invokeId = invokeId;
    }

    public ReadPropertyMultipleServiceBuilder withInvokeId(Integer invokeId) {
        this.invokeId = invokeId;
        return this;
    }

    public ReadPropertyMultipleServiceBuilder withObjectProperties(Set<ObjectProperties> objectProperties) {
        this.objectProperties = objectProperties;
        return this;
    }

    public ReadPropertyMultipleServiceBuilder withPropertyReferences(Set<PropertyReference> propertyReferences) {
        this.propertyReferences = propertyReferences;
        return this;
    }

    public ReadPropertyMultipleServiceBuilder withDeviceId(DeviceId deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public ReadPropertyMultipleServiceBuilder findCountOfAvailableObjectsOnDevice(DeviceId deviceId) {
        PropertyReference findObjectListSize = new PropertyReference(PropertyIdentifier.ObjectList, 0);
        this.deviceId = deviceId;
        this.propertyReferences = Set.of(findObjectListSize);
        return this;
    }

    public ReadPropertyMultipleServiceBuilder findAvailableObjectsOnDevice(Set<PropertyReference> propertyReferences) {
        this.propertyReferences = propertyReferences;
        return this;
    }
    public ReadPropertyMultipleServiceBuilder findSensorProperties(ObjectId sensorId, Set<PropertyIdentifier> propertyIdentifiers) {
        if (sensorId != null && propertyIdentifiers != null) {
            List<ObjectType> validSensorTypes = Arrays.asList(AnalogValue, AnalogInput, BinaryValue, BinaryInput);
            ObjectType sensorObjectType = sensorId.getObjectType();
            if (validSensorTypes.contains(sensorObjectType)) {
               this.objectProperties = Set.of(new ObjectProperties(sensorId, propertyIdentifiers));
            } else {
                throw new IllegalArgumentException("SensorObjectType must be one of " + validSensorTypes.toString());
            }
        }
        return null;
    }
    public ReadPropertyMultipleServiceBuilder findPropertiesForSensorsAndDevices(Set<ObjectProperties> objectProperties) {
        this.objectProperties = objectProperties;
        return this;
    }


    public ReadPropertyMultipleService build() {
        ReadPropertyMultipleService readPropertyMultipleService = new ReadPropertyMultipleService();
        readPropertyMultipleService.setInvokeId(invokeId);
        readPropertyMultipleService.setObjectProperties(objectProperties);
        readPropertyMultipleService.setObjectId(deviceId);
        readPropertyMultipleService.setPropertyReferences(propertyReferences);
        //Validate
        String hexString = readPropertyMultipleService.buildHexString();
        return readPropertyMultipleService;
    }


}
