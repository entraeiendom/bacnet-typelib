package no.entra.bacnet.objects;

import no.entra.bacnet.internal.octet.Octet;
import no.entra.bacnet.utils.HexUtils;

public class ObjectIdBuilder {
    private final no.entra.bacnet.objects.ObjectType objectType;
    private Integer instanceNumber;

    public ObjectIdBuilder(Octet objectTypeOctet) {
        this.objectType = no.entra.bacnet.objects.ObjectType.fromOctet(objectTypeOctet);
    }

    public ObjectIdBuilder withInstanceNumberOctet(Octet[] instanceNumberOctets) {
        String instanceNumberString = HexUtils.octetsToString(instanceNumberOctets);
        instanceNumber = HexUtils.toInt(instanceNumberString);
        return this;
    }

    public ObjectId build() {
        ObjectId objectId = new ObjectId(objectType, instanceNumber);
        return objectId;
    }
}
