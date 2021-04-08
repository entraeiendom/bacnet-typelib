package no.entra.bacnet.objects;

import no.entra.bacnet.octet.Octet;
import no.entra.bacnet.octet.OctetReader;
import no.entra.bacnet.utils.HexUtils;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class ObjectIdMapper {
    private static final Logger log = getLogger(ObjectIdMapper.class);

    //4 or 5 octets
    //10 bits for ObjectType
    //22 bits for Instance Number

    /**
     * @param hexString 4 octets
     * @return ObjectId and count octets read
     */
    public static ObjectIdMapperResult<no.entra.bacnet.objects.ObjectId> parse(String hexString) {
        no.entra.bacnet.objects.ObjectId objectId = null;
        OctetReader idReader = new OctetReader(hexString);
        Octet[] typeAndInstanceOctets = idReader.nextOctets(4);
        objectId = decode4Octets(typeAndInstanceOctets);
        ObjectIdMapperResult result = new ObjectIdMapperResult(objectId, 4);

        return result;
    }

    public static no.entra.bacnet.objects.ObjectId fromHexString(String hexString) {
        no.entra.bacnet.objects.ObjectId objectId = null;
        OctetReader idReader = new OctetReader(hexString);
        if (idReader.countOctets() == 4) {
            Octet[] typeAndInstanceOctets = idReader.nextOctets(4);
            objectId = decode4Octets(typeAndInstanceOctets);
        }

        return objectId;
    }

    public static String toHexString(no.entra.bacnet.objects.ObjectId objectId) {
        String hexString = null;
        if (objectId != null) {
            String objectTypeBits = paddedObjectTypeBits(objectId.getObjectType());
            int instanceNumber = objectId.getInstanceNumber();
            String instanceNumberBits = paddedInstanceNumberBits(instanceNumber);
            hexString = HexUtils.binaryToHex(objectTypeBits + instanceNumberBits);
        }
        return hexString;
    }

    static String fillInstanceNumber(no.entra.bacnet.objects.ObjectId objectId) {
        Integer hexNumber = objectId.getInstanceNumber();
        int lenght = 6;
        return String.format("%1$" + lenght + "s", hexNumber).replace(' ', '0');
    }

    public static no.entra.bacnet.objects.ObjectId decode4Octets(Octet[] typeAndInstanceOctets) {
        no.entra.bacnet.objects.ObjectId objectId;
        String typeAndInstance = HexUtils.octetsToString(typeAndInstanceOctets);
        String typeAndInstanceBits = HexUtils.toBitString(typeAndInstance);
        int objectTypeInt = findObjectTypeInt(typeAndInstanceBits);
        Integer instanceNumber = findInstanceNumber(typeAndInstanceBits);
        no.entra.bacnet.objects.ObjectType objectType = no.entra.bacnet.objects.ObjectType.fromObjectTypeInt(objectTypeInt);
        objectId = new no.entra.bacnet.objects.ObjectId(objectType,instanceNumber);
        return objectId;
    }

    protected static int findInstanceNumber(String typeAndInstanceBits) {
        String instanceNumberBits = typeAndInstanceBits.substring(10,32);
        return Integer.parseInt(instanceNumberBits,2);
    }

    protected static int findObjectTypeInt(String typeAndInstanceBits) {
        String objectTypeBits = typeAndInstanceBits.substring(0,10);
        return Integer.parseInt(objectTypeBits, 2);
    }

    protected static String paddedInstanceNumberBits(int instanceNumber) {
        String bitString = Integer.toBinaryString(instanceNumber);
        int lenght = 22;
        return String.format("%1$" + lenght + "s", bitString).replace(' ', '0');
    }
    protected static String paddedObjectTypeBits(no.entra.bacnet.objects.ObjectType objectType) {
        String bitString = Integer.toBinaryString(objectType.getObjectTypeInt());
        int lenght = 10;
        return String.format("%1$" + lenght + "s", bitString).replace(' ', '0');
    }
}
