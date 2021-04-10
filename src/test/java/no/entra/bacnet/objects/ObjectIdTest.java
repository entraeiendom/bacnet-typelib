package no.entra.bacnet.objects;

import no.entra.bacnet.internal.objects.ObjectIdMapper;
import no.entra.bacnet.parseandmap.ParserResult;
import org.junit.jupiter.api.Test;

import static no.entra.bacnet.objects.ObjectType.AnalogInput;
import static no.entra.bacnet.objects.ObjectType.TrendLog;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ObjectIdTest {

    @Test
    void parseTest() {
        String idHexString = "002dc6ef";
        ParserResult<ObjectId> result = ObjectIdMapper.parse(idHexString);
        ObjectId objectId = result.getParsedObject();
        assertNotNull(objectId);
        assertEquals(AnalogInput, objectId.getObjectType());
        assertEquals(3000047, objectId.getInstanceNumber());
        assertEquals(AnalogInput + "_3000047", objectId.toString());
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
        ObjectId objectId = new ObjectId(AnalogInput,0);
        String hexString = ObjectIdMapper.toHexString(objectId);
        assertEquals("00000000", hexString);
    }
}