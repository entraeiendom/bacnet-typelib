package no.entra.bacnet.device;

import no.entra.bacnet.objects.ObjectProperties;
import no.entra.bacnet.properties.PropertyIdentifier;
import no.entra.bacnet.properties.PropertyReference;
import no.entra.bacnet.services.ReadPropertyMultipleService;
import no.entra.bacnet.services.ReadPropertyMultipleServiceBuilder;
import org.junit.jupiter.api.Test;

import java.util.*;

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
        //1. Find count of Objects available on a Device
        int invokeId = 69;
        DeviceId deviceId = new DeviceId(8);
        ReadPropertyMultipleService countOfObjectsAvailable = new ReadPropertyMultipleServiceBuilder(invokeId).findCountOfAvailableObjectsOnDevice(deviceId).build();

        String expectedHexString = "810a001501040275450e0c020000081e094c19001f";
        assertEquals(expectedHexString, countOfObjectsAvailable.buildHexString());
    }

    @Test
    void findCountOfObjectsResponse() {
        //2. Receive count
        String fullBacnetHexString = "810a0019010030450e0c020000081e294c39004e2201694f1f";
        String apduHexString = "30450e0c020000081e294c39004e2201694f1f";
        String serviceHexString = "0c020000081e294c39004e2201694f1f";
        ReadPropertyMultipleService countOfObjectsResponse = ReadPropertyMultipleService.parse(serviceHexString);
        assertNotNull(countOfObjectsResponse);
        DeviceId deviceId = new DeviceId(8);
        assertEquals(deviceId, countOfObjectsResponse.getObjectId());
        Set<ObjectProperties> objectProperties = countOfObjectsResponse.getObjectProperties();
        assertNotNull(objectProperties);
        assertEquals(1, objectProperties.size());
        ObjectProperties objectProperty = objectProperties.iterator().next();
        Set<PropertyIdentifier> propertyIdentifiers = objectProperty.getPropertyIdentifiers();
        assertEquals(PropertyIdentifier.ObjectList, propertyIdentifiers.iterator().next());
        Map<PropertyIdentifier, Object> properties = objectProperty.getProperties();
        assertEquals(Integer.valueOf(361), properties.get(PropertyIdentifier.ObjectList));
    }

    @Test
    void findPropertyIdentifiersOnDeviceRequest() {
        //3. Find PropertyIdentifiers for every Device, Sensor or Actuator in ObjectList
        int invokeId = 70;
        DeviceId deviceId = new DeviceId(8);
        List<Integer> objectListElements = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            objectListElements.add(i + 1);
        }
        Set<PropertyReference> propertyReferences = new LinkedHashSet<>();
        for (Integer objectListElement : objectListElements) {
            propertyReferences.add(new PropertyReference(PropertyIdentifier.ObjectList, objectListElement));
        }
        ReadPropertyMultipleService propertyIdentifiersRequest = new ReadPropertyMultipleServiceBuilder(invokeId)
                .withDeviceId(deviceId)
                .withPropertyReferences(propertyReferences)
                .build();

        String expectedHexString = "810a00d901040275460e0c020000081e094c1901094c1902094c1903094c1904094c1905094c1906094c" +
                "1907094c1908094c1909094c190a094c190b094c190c094c190d094c190e094c190f094c1910094c1911094c1912094c1913094" +
                "c1914094c1915094c1916094c1917094c1918094c1919094c191a094c191b094c191c094c191d094c191e094c191f094c192009" +
                "4c1921094c1922094c1923094c1924094c1925094c1926094c1927094c1928094c1929094c192a094c192b094c192c094c192d0" +
                "94c192e094c192f094c1930094c1931094c19321f";
        String hexString = propertyIdentifiersRequest.buildHexString();
        assertEquals(expectedHexString, hexString);
    }

    @Test
    void findPropertyIdentifiersOnDevice() {
        //4. Receive PropertyIdentifiers from ObjectList - and potentially loop
        String singleDevicePropertyHexString = "0c020000081e294c39014ec4020000084f1f";
        ReadPropertyMultipleService countOfObjectsResponse = ReadPropertyMultipleService.parse(singleDevicePropertyHexString);
        assertNotNull(countOfObjectsResponse);
        DeviceId deviceId = new DeviceId(8);
        assertEquals(deviceId, countOfObjectsResponse.getObjectId());
//        fail(); //FIXME flowtest
//       List<ObjectId> deviceProperties = countOfObjectsResponse.getPropertyListResponse();
//       ai
//        assertNotNull(deviceProperties);
//        assertEquals(1, deviceProperties.size());
//        ObjectProperties objectProperty = objectProperties.iterator().next();
//        Set<PropertyIdentifier> propertyIdentifiers = objectProperty.getPropertyIdentifiers();
//        assertEquals(PropertyIdentifier.ObjectList, propertyIdentifiers.iterator().next());
//        String fullBacnetHexString = "810a0236010030460e0c020000081e294c39014ec4020000084f294c39024ec4008000004f294c39034ec4008000014f294c39044ec4008000024f294c39054ec4008000034f294c39064ec4008000044f294c39074ec4008000054f294c39084ec4008000064f294c39094ec4008000074f294c390a4ec4008000084f294c390b4ec4008000094f294c390c4ec40080000a4f294c390d4ec40080000b4f294c390e4ec40080000c4f294c390f4ec40080000d4f294c39104ec40080000e4f294c39114ec40080000f4f294c39124ec4008000104f294c39134ec4008000114f294c39144ec4008000124f294c39154ec4008000134f294c39164ec4008000144f294c39174ec4008000154f294c39184ec4008000164f294c39194ec4008000174f294c391a4ec4008000184f294c391b4ec4008000194f294c391c4ec40080001a4f294c391d4ec40080001b4f294c391e4ec40080001c4f294c391f4ec40080001d4f294c39204ec40080001e4f294c39214ec40080001f4f294c39224ec4008000204f294c39234ec4008000214f294c39244ec4008000224f294c39254ec4008000234f294c39264ec4008000244f294c39274ec4008000254f294c39284ec4008000264f294c39294ec4008000274f294c392a4ec4008000284f294c392b4ec4008000294f294c392c4ec40080002a4f294c392d4ec40080002b4f294c392e4ec40080002c4f294c392f4ec40080002d4f294c39304ec40080002e4f294c39314ec40080002f4f294c39324ec4008000304f1f";
//        String apduHexString = "...";
//        String serviceHexString = "0c020000081e094c1901094c1902094c1903094c1904094c1905094c1906094c1907094c1908094c1909094c190a094c190b094c190c094c190d094c190e094c190f094c1910094c1911094c1912094c1913094c1914094c1915094c1916094c1917094c1918094c1919094c191a094c191b094c191c094c191d094c191e094c191f094c1920094c1921094c1922094c1923094c1924094c1925094c1926094c1927094c1928094c1929094c192a094c192b094c192c094c192d094c192e094c192f094c1930094c1931094c19321f";
//        ReadPropertyMultipleService countOfObjectsResponse = ReadPropertyMultipleService.parse(serviceHexString);
//        assertNotNull(countOfObjectsResponse);
//        DeviceId deviceId = new DeviceId(8);
//        assertEquals(deviceId, countOfObjectsResponse.getObjectId());
    }
}
