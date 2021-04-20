package no.entra.bacnet;

import no.entra.bacnet.device.DeviceId;
import no.entra.bacnet.error.ErrorClassType;
import no.entra.bacnet.error.ErrorCodeType;
import no.entra.bacnet.internal.properties.PropertyIdentifier;
import no.entra.bacnet.internal.properties.ReadObjectPropertiesResult;
import no.entra.bacnet.internal.properties.ReadPropertyResult;
import no.entra.bacnet.internal.property.ReadSinglePropertyResult;
import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.objects.ObjectType;
import no.entra.bacnet.properties.ReadPropertyMultipleService;
import no.entra.bacnet.property.ReadPropertyResponse;
import no.entra.bacnet.property.ReadPropertyService;
import no.entra.bacnet.services.AbortService;
import no.entra.bacnet.services.Service;
import no.entra.bacnet.services.WhoIsService;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static no.entra.bacnet.internal.properties.ReadPropertyResultParser.ERROR_CLASS;
import static no.entra.bacnet.internal.properties.ReadPropertyResultParser.ERROR_CODE;
import static org.junit.jupiter.api.Assertions.*;

class BacnetMessageParserTest {

    @Test
    void parsePropertiesFoundResponse() {
        String bacnetHexString = "810a054f010030470e0c020000081e294d4e75060046574643554f291c4e751800465720536572696573204261636e6574204465766963654f29755e910291205f29555e910291205f1f0c008000001e294d4e7514005549315f5a6f6e6554656d70657261747572654f291c4e750f00416e616c6f672056616c756520304f29754e915f4f29554e4441b3332c4f1f0c008000011e294d4e7519005549325f44697363686172676554656d70657261747572654f291c4e750f00416e616c6f672056616c756520314f29754e915f4f29554e4441acccf84f1f0c008000021e294d4e751300554f315f537570706c7946616e53706565644f291c4e750f00416e616c6f672056616c756520324f29754e915f4f29554e44000000004f1f0c008000031e294d4e751100554f325f436f6f6c696e6756616c76654f291c4e750f00416e616c6f672056616c756520334f29754e915f4f29554e44000000004f1f0c008000041e294d4e751100554f335f48656174696e6756616c76654f291c4e750f00416e616c6f672056616c756520344f29754e915f4f29554e4442b90d164f1f0c008000051e294d4e7512004f63637570616e6379536574706f696e744f291c4e750f00416e616c6f672056616c756520354f29754e915f4f29554e4441f000004f1f0c008000061e294d4e751400556e6f63637570616e6379536574706f696e744f291c4e750f00416e616c6f672056616c756520364f29754e915f4f29554e44415000004f1f0c008000071e294d4e751900456666656374697665436f6f6c696e67536574706f696e744f291c4e750f00416e616c6f672056616c756520374f29754e915f4f29554e4441f800004f1f0c008000081e294d4e75190045666665637469766548656174696e67536574706f696e744f291c4e750f00416e616c6f672056616c756520384f29754e915f4f29554e4441b000004f1f0c008000091e294d4e750f00416e616c6f672056616c756520394f291c4e750f00416e616c6f672056616c756520394f29754e915f4f29554e44000000004f1f0c0080000a1e294d4e751000416e616c6f672056616c75652031304f291c4e751000416e616c6f672056616c75652031304f29754e915f4f29554e44000000004f1f0c0080000b1e294d4e751000416e616c6f672056616c75652031314f291c4e751000416e616c6f672056616c75652031314f29754e915f4f29554e44000000004f1f0c0080000c1e294d4e751000416e616c6f672056616c75652031324f291c4e751000416e616c6f672056616c75652031324f29754e915f4f29554e44000000004f1f0c0080000d1e294d4e751000416e616c6f672056616c75652031334f291c4e751000416e616c6f672056616c75652031334f29754e915f4f29554e44000000004f1f0c0080000e1e294d4e751000416e616c6f672056616c75652031344f291c4e751000416e616c6f672056616c75652031344f29754e915f4f29554e44000000004f1f0c0080000f1e294d4e751000416e616c6f672056616c75652031354f291c4e751000416e616c6f672056616c75652031354f29754e915f4f29554e44000000004f1f0c008000101e294d4e751000416e616c6f672056616c75652031364f291c4e751000416e616c6f672056616c75652031364f29754e915f4f29554e44000000004f1f0c008000111e294d4e751000416e616c6f672056616c75652031374f291c4e751000416e616c6f672056616c75652031374f29754e915f4f29554e44000000004f1f0c008000121e294d4e751000416e616c6f672056616c75652031384f291c4e751000416e616c6f672056616c75652031384f29754e915f4f29554e44000000004f1f";
        BacnetResponse response = BacnetMessageParser.parse(bacnetHexString);
        assertNotNull(response.getService());
        assertEquals(71, response.getInvokeId());
        ReadPropertyMultipleService service = (ReadPropertyMultipleService) response.getService();
        List<ReadObjectPropertiesResult> propertiesResults = service.getReadPropertyMultipleResponse().getResults();
        ReadObjectPropertiesResult propertiesResult = propertiesResults.get(0);
        ObjectId objectId = new ObjectId(ObjectType.Device, 8);
        assertEquals(objectId, propertiesResult.getObjectId());

        List<ReadPropertyResult> resultList = propertiesResult.getResults();
        assertNotNull(resultList);
        assertEquals(4, resultList.size());
        assertEquals("FWFCU", resultList.get(0).getReadResult().get(PropertyIdentifier.ObjectName));
        assertEquals("FW Series Bacnet Device", resultList.get(1).getReadResult().get(PropertyIdentifier.Description));


        Map errorMap = new HashMap();
        errorMap.put(ERROR_CLASS, ErrorClassType.property);
        errorMap.put(ERROR_CODE, ErrorCodeType.UnknownProperty);
        assertEquals(PropertyIdentifier.Units, resultList.get(2).getPropertyIdentifier());
        assertEquals(null, resultList.get(2).getReadResult().get(PropertyIdentifier.Units));
        assertEquals(errorMap, resultList.get(2).getReadResult().get(PropertyIdentifier.XxError));
        assertEquals(null, resultList.get(3).getReadResult().get(PropertyIdentifier.PresentValue));
        assertEquals(null, resultList.get(3).getReadResult().get(PropertyIdentifier.PresentValue));
        assertEquals(errorMap, resultList.get(3).getReadResult().get(PropertyIdentifier.XxError));
    }

    @Test
    void parseAbort() {
        String hexString = "810a00090100710004";
        BacnetResponse response = BacnetMessageParser.parse(hexString);
        assertNotNull(response);
        assertNotNull(response.getService());
        assertEquals(0, response.getInvokeId());
        AbortService service = (AbortService) response.getService();
    }

    @Test
    void whoIsService() {
        String whoIsHexString = "810b000c0120ffff00ff1008";
        BacnetResponse bacnetResponse = BacnetMessageParser.parse(whoIsHexString);
        assertNotNull(bacnetResponse);
        Service service = bacnetResponse.getService();
        assertNotNull(service);
        assertTrue(service instanceof WhoIsService);
    }

    @Test
    void whoIsWithLowHighRange() {
        String whoIsHexString = "810400180a3f510cbac00120ffff00ff10080a07ae1a07ae";
        BacnetResponse bacnetResponse = BacnetMessageParser.parse(whoIsHexString);
        assertNotNull(bacnetResponse);
        Service service = bacnetResponse.getService();
        assertNotNull(service);
        assertTrue(service instanceof WhoIsService);
        WhoIsService whoIsService = (WhoIsService) service;
        assertEquals(1966, whoIsService.getLowRangeLimit());
        assertEquals(1966, whoIsService.getHighRangeLimit());
    }

    @Test
    void bugfix() {
        String hexString = "810b00190120ffff00ff1000c4020000082205c491032201f6";
        BacnetResponse bacnetResponse = BacnetMessageParser.parse(hexString);
        assertNotNull(bacnetResponse);
    }

    @Test
    void readPropertyServicesSupported() {
        String hexString = "810a001b010030050c0c0200000819613e850707000bc000f8003f";
        BacnetResponse bacnetResponse = BacnetMessageParser.parse(hexString);
        Service service = bacnetResponse.getService();
        assertNotNull(service);
        ReadPropertyService propertyService = (ReadPropertyService) service;
        ReadPropertyResponse readPropertyResponse = propertyService.getReadPropertyResponse();
        ReadSinglePropertyResult result = readPropertyResponse.getResult();
        assertEquals(PropertyIdentifier.ProtocolServicesSupported, result.getPropertyIdentifier());
        assertEquals(new DeviceId(8), result.getObjectId() );
        assertEquals("00000111000000000000101111000000000000001111100000000000", result.getValue());

    }
}