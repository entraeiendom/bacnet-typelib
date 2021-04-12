package no.entra.bacnet.internal.services;

import no.entra.bacnet.device.DeviceId;
import no.entra.bacnet.internal.apdu.MeasurementUnit;
import no.entra.bacnet.internal.objects.Segmentation;
import no.entra.bacnet.internal.parseandmap.ParserResult;
import no.entra.bacnet.internal.properties.PropertyIdentifier;
import no.entra.bacnet.internal.properties.ReadObjectPropertiesResult;
import no.entra.bacnet.internal.properties.ReadPropertyResult;
import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.objects.ObjectType;
import no.entra.bacnet.properties.ReadPropertyMultipleService;
import no.entra.bacnet.services.*;
import no.entra.bacnet.vendor.Vendor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceParserTest {

    @Test
    void iamService() {
        String hexString = "c40200000822040091002105";
        ParserResult<Service> parserResult = ServiceParser.parse(UnconfirmedServiceChoice.IAm, hexString);
        assertNotNull(parserResult);
        assertTrue(parserResult.isParsedOk());
        Service parsedObject = parserResult.getParsedObject();
        assertTrue(parsedObject instanceof IAmService);
        DeviceId deviceId = new DeviceId(8);
        IAmService iAmService = (IAmService) parserResult.getParsedObject();
        assertEquals(deviceId, iAmService.getObjectId());
        assertEquals(1024, iAmService.getMaxADPULengthAccepted());
        assertEquals(Segmentation.SegmentedBoth, iAmService.getSegmentation());
        Vendor vendor = new Vendor("05", "Johnson Controls, Inc");
        assertEquals(vendor, iAmService.getVendor());
    }

    @Test
    void whoIsService() {
        String hexString = "0a07ae1a07ae";
        ParserResult<Service> parserResult = ServiceParser.parse(UnconfirmedServiceChoice.WhoIs, hexString);
        assertNotNull(parserResult);
        assertTrue(parserResult.isParsedOk());
        Service parsedObject = parserResult.getParsedObject();
        assertTrue(parsedObject instanceof WhoIsService);
    }

    //FIXME is too complex for end user. Need more explicit typing in Service Interface.
    @Test
    void parseSingleObjectMultipleProperties() throws BacnetParserException {
        String hexString = "0c008000001e294d4e7514005549315f5a6f6e6554656d70657261747572654f291c4e750f00416e616c6f672056616c756520304f29754e915f4f29554e4441b3332c4f1f";
        ParserResult<Service> parserResult = ServiceParser.parse(ConfirmedServiceChoice.ReadPropertyMultiple, hexString);
        assertNotNull(parserResult);
        assertTrue(parserResult.isParsedOk());
        Service parsedObject = parserResult.getParsedObject();
        assertTrue(parsedObject instanceof ReadPropertyMultipleService);
        ReadPropertyMultipleService service = (ReadPropertyMultipleService) parserResult.getParsedObject();
        List<ReadObjectPropertiesResult> propertiesResults = service.getReadPropertyMultipleResponse().getResults();
        assertNotNull(propertiesResults);
        ReadObjectPropertiesResult propertiesResult = propertiesResults.get(0);
        ObjectId objectId = new ObjectId(ObjectType.AnalogValue, 0);
        assertEquals(objectId, propertiesResult.getObjectId());
        List<ReadPropertyResult> resultList = propertiesResult.getResults();
        assertNotNull(resultList);
        assertEquals(4, resultList.size());
        assertEquals("UI1_ZoneTemperature", resultList.get(0).getReadResult().get(PropertyIdentifier.ObjectName));
        assertEquals("Analog Value 0", resultList.get(1).getReadResult().get(PropertyIdentifier.Description));
        assertEquals(MeasurementUnit.NoUnits, resultList.get(2).getReadResult().get(PropertyIdentifier.Units));
        assertEquals(Float.parseFloat("22.3999862670898"), resultList.get(3).getReadResult().get(PropertyIdentifier.PresentValue));
    }
}