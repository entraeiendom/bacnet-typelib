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
        //Find PropertyIdentifiers for every Device, Sensor or Actuator in ObjectList
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
        String fullBacnetHexString = "810a00d901040275460e0c020000081e094c1901094c1902094c1903094c1904094c1905094c1906094c1907094c1908094c1909094c190a094c190b094c190c094c190d094c190e094c190f094c1910094c1911094c1912094c1913094c1914094c1915094c1916094c1917094c1918094c1919094c191a094c191b094c191c094c191d094c191e094c191f094c1920094c1921094c1922094c1923094c1924094c1925094c1926094c1927094c1928094c1929094c192a094c192b094c192c094c192d094c192e094c192f094c1930094c1931094c19321f";
        String apduHexString = "...";
        String serviceHexString = "0c020000081e094c1901094c1902094c1903094c1904094c1905094c1906094c1907094c1908094c1909094c190a094c190b094c190c094c190d094c190e094c190f094c1910094c1911094c1912094c1913094c1914094c1915094c1916094c1917094c1918094c1919094c191a094c191b094c191c094c191d094c191e094c191f094c1920094c1921094c1922094c1923094c1924094c1925094c1926094c1927094c1928094c1929094c192a094c192b094c192c094c192d094c192e094c192f094c1930094c1931094c19321f";
        ReadPropertyMultipleService countOfObjectsResponse = ReadPropertyMultipleService.parse(serviceHexString);
        assertNotNull(countOfObjectsResponse);
        DeviceId deviceId = new DeviceId(8);
        assertEquals(deviceId, countOfObjectsResponse.getObjectId());
    }
}
