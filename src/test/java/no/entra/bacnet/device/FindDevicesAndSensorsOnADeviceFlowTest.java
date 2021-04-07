package no.entra.bacnet.device;

import no.entra.bacnet.services.ReadPropertyMultipleService;
import no.entra.bacnet.services.ReadPropertyMultipleServiceBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/*
1. Find count of Objects available on a Device
2. Receive count
3. Find PropertyIdentifiers for every Device, Sensor or Actuator in ObjectList
4. Receive PropertyIdentifiers from ObjectList - and potentially loop
5. Find Property Value for a set of Device, Sensor or Actuator
6. Receive Properties for every Device, Sensor and Actuator
 */
public class FindDevicesAndSensorsOnADeviceFlowTest {

    @Test
    void findCountOfObjectsRequest() {

        int invokeId = 69;
        DeviceId deviceId = new DeviceId(8);
        ReadPropertyMultipleService countOfObjectsAvailable = new ReadPropertyMultipleServiceBuilder(invokeId).findCountOfAvailableObjectsOnDevice(deviceId).build();

        String expectedHexString = "810a001501040275450e0c020000081e094c19001f";
        assertEquals(expectedHexString, countOfObjectsAvailable.buildHexString());
    }

    @Test
    void findCountOfObjectsResponse() {
        String fullBacnetHexString = "810a0019010030450e0c020000081e294c39004e2201694f1f";
        String apduHexString = "30450e0c020000081e294c39004e2201694f1f";
        String serviceHexString = "0c020000081e294c39004e2201694f1f";
        ReadPropertyMultipleService countOfObjectsResponse =  ReadPropertyMultipleService.parse(serviceHexString);
        assertNotNull(countOfObjectsResponse);
        DeviceId deviceId = new DeviceId(8);
        assertEquals(deviceId, countOfObjectsResponse.getObjectId());
    }
}
