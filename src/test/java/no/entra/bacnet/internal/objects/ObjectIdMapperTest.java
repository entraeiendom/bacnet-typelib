package no.entra.bacnet.internal.objects;

import no.entra.bacnet.internal.octet.OctetReader;
import no.entra.bacnet.internal.parseandmap.ParserResult;
import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.objects.ObjectType;
import org.junit.jupiter.api.Test;

import static no.entra.bacnet.objects.ObjectType.*;
import static org.junit.jupiter.api.Assertions.*;

class ObjectIdMapperTest {

    @Test
    void parseDevice517() {
        String device517 = "02000205";
        ParserResult<ObjectId> result = ObjectIdMapper.parse(device517);
        assertNotNull(result);
        assertNotNull(result.getParsedObject());
        assertTrue(result.getParsedObject() instanceof ObjectId);
        assertEquals(4, result.getNumberOfOctetsRead());
        assertEquals(ObjectType.Device, result.getParsedObject().getObjectType());
        assertEquals(517, result.getParsedObject().getInstanceNumber());

    }

    @Test
    void parseDevice131109() {
        String device131109 = "02020025";
        ParserResult<ObjectId> result = ObjectIdMapper.parse(device131109);
        assertNotNull(result);
        assertNotNull(result.getParsedObject());
        assertTrue(result.getParsedObject() instanceof ObjectId);
        assertEquals(4, result.getNumberOfOctetsRead());
        assertEquals(ObjectType.Device, result.getParsedObject().getObjectType());
        assertEquals(131109, result.getParsedObject().getInstanceNumber());
    }

    @Test
    void parseDevice1001() {
        String device1001 = "020003e9";
        ParserResult<ObjectId> result = ObjectIdMapper.parse(device1001);
        assertNotNull(result);
        assertNotNull(result.getParsedObject());
        assertTrue(result.getParsedObject() instanceof ObjectId);
        assertEquals(4, result.getNumberOfOctetsRead());
        assertEquals(ObjectType.Device, result.getParsedObject().getObjectType());
        assertEquals(1001, result.getParsedObject().getInstanceNumber());
    }

    @Test
    void parseAnalogInput0() {
        String device131109 = "00000000";
        ParserResult<ObjectId> result = ObjectIdMapper.parse(device131109);
        assertNotNull(result);
        assertNotNull(result.getParsedObject());
        assertTrue(result.getParsedObject() instanceof ObjectId);
        assertEquals(4, result.getNumberOfOctetsRead());
        assertEquals(ObjectType.AnalogInput, result.getParsedObject().getObjectType());
        assertEquals(0, result.getParsedObject().getInstanceNumber());
    }

    @Test
    void findObjectTypeIntTest() {
        String objectTypeAsBitString = "00000010000000000000001000000101";
        int objectTypeInt = ObjectIdMapper.findObjectTypeInt(objectTypeAsBitString);
        assertEquals(8, objectTypeInt);
    }

    @Test
    void findInstanceNumberTest() {
        String objectTypeAsBitString = "00000010000000000000001000000101";
        int instanceNumber = ObjectIdMapper.findInstanceNumber(objectTypeAsBitString);
        assertEquals(517, instanceNumber);
    }

    @Test
    void validObjectIdTest() {
        String objectIdHexString = "02000204";
        ParserResult<ObjectId> objectIdResult = ObjectIdMapper.parse(objectIdHexString);
        assertNotNull(objectIdResult);
        assertEquals("Device", objectIdResult.getParsedObject().getObjectType().toString());
        assertEquals(516, objectIdResult.getParsedObject().getInstanceNumber());
    }

    @Test
    void decode4OctetsTest() {
        String objectIdentifierTypeAndInstance = "0200000c";
        OctetReader objectIdReader = new OctetReader(objectIdentifierTypeAndInstance);
        ObjectId objectId = ObjectIdMapper.decode4Octets(objectIdReader.nextOctets(4));
        assertNotNull(objectId);
        assertEquals(ObjectType.Device, objectId.getObjectType());
        assertEquals(12, objectId.getInstanceNumber());
    }

    @Test
    void fromHexString() {
        String idHexString = "00000000";
        ObjectId objectId = ObjectIdMapper.fromHexString(idHexString);
        assertNotNull(objectId);
        assertEquals(AnalogInput, objectId.getObjectType());
        assertEquals(0, objectId.getInstanceNumber());
        assertEquals(AnalogInput + "_0", objectId.toString());
        idHexString = "05000001";
        objectId = ObjectIdMapper.fromHexString(idHexString);
        assertNotNull(objectId);
        assertEquals(TrendLog + "_1", objectId.toString());
    }

    @Test
    void toHexString() {
        ObjectId objectId = new ObjectId(TrendLog,1);
        String hexString = ObjectIdMapper.toHexString(objectId);
        //TrendLog = int 20 = bits? = hex?
        assertEquals("05000001", hexString);
    }

    @Test
    void testAnalogValue0() {
        String hexString = "00800000";
        ParserResult<ObjectId> result = ObjectIdMapper.parse(hexString);
        ObjectId objectId = result.getParsedObject();
        assertNotNull(objectId);
        ObjectId expectedId = new ObjectId(AnalogValue,0);
        assertEquals(expectedId, objectId);
        String expectedAsHexString = ObjectIdMapper.toHexString(expectedId);
        assertEquals(hexString, expectedAsHexString);
        expectedId = new ObjectId(Device,8);
        expectedAsHexString = ObjectIdMapper.toHexString(expectedId);
        assertEquals("02000008", expectedAsHexString);
    }

    @Test
    void paddedBitStringTest() {
        String expected = "0000000000001000000101";
        String padded = ObjectIdMapper.paddedInstanceNumberBits(517);
        assertEquals(expected, padded);
    }

    @Test
    void paddedObjectTypeBitsTest() {
        String expected = "0000001000";
        String padded = ObjectIdMapper.paddedObjectTypeBits(Device);
        assertEquals(expected,padded);
        expected = "0000010100";
        padded = ObjectIdMapper.paddedObjectTypeBits(TrendLog);
        assertEquals(expected,padded);
        expected = "0000000000";
        padded = ObjectIdMapper.paddedObjectTypeBits(AnalogInput);
        assertEquals(expected,padded);
    }
}