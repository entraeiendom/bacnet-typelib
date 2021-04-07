package no.entra.bacnet.device;

import no.entra.bacnet.services.ReadPropertyMultipleService;
import no.entra.bacnet.services.ReadPropertyMultipleServiceBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void findCountOfObjects() {

        int invokeId = 69;
        DeviceId deviceId = new DeviceId(8);
        ReadPropertyMultipleService countOfObjectsAvailable = new ReadPropertyMultipleServiceBuilder(invokeId).findCountOfAvailableObjectsOnDevice(deviceId).build();

        String expectedHexString = "810a001501040275450e0c020000081e094c19001f";
        assertEquals(expectedHexString, countOfObjectsAvailable.buildHexString());
    }
}
