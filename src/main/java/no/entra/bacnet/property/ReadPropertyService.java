package no.entra.bacnet.property;

import no.entra.bacnet.BacnetRequest;
import no.entra.bacnet.apdu.Apdu;
import no.entra.bacnet.bvlc.Bvlc;
import no.entra.bacnet.internal.apdu.MessageType;
import no.entra.bacnet.internal.apdu.SDContextTag;
import no.entra.bacnet.internal.bvlc.BvlcBuilder;
import no.entra.bacnet.internal.bvlc.BvlcFunction;
import no.entra.bacnet.internal.npdu.NpduBuilder;
import no.entra.bacnet.internal.objects.ObjectProperties;
import no.entra.bacnet.internal.properties.PropertyReference;
import no.entra.bacnet.npdu.Npdu;
import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.services.ConfirmedServiceChoice;
import no.entra.bacnet.services.Service;

import java.util.Set;

import static no.entra.bacnet.internal.apdu.ArrayTag.ARRAY1_END;
import static no.entra.bacnet.internal.apdu.ArrayTag.ARRAY1_START;
import static no.entra.bacnet.utils.HexUtils.intToHexString;

public class ReadPropertyService extends BacnetRequest implements Service {
    private Integer invokeId = null;
    private Set<ObjectProperties> objectProperties = null;
    private ObjectId objectId = null;
    private Set<PropertyReference> propertyReferences = null;
    private ReadPropertyResponse readPropertyResponse = null;

    public ReadPropertyService() {
    }

    public Integer getInvokeId() {
        return invokeId;
    }

    public void setInvokeId(Integer invokeId) {
        this.invokeId = invokeId;
    }

    public Set<ObjectProperties> getObjectProperties() {
        return objectProperties;
    }

    public void setObjectProperties(Set<ObjectProperties> objectProperties) {
        this.objectProperties = objectProperties;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public Set<PropertyReference> getPropertyReferences() {
        return propertyReferences;
    }

    public void setPropertyReferences(Set<PropertyReference> propertyReferences) {
        this.propertyReferences = propertyReferences;
    }

    public ReadPropertyResponse getReadPropertyResponse() {
        return readPropertyResponse;
    }

    public void setReadPropertyResponse(ReadPropertyResponse readPropertyResponse) {
        this.readPropertyResponse = readPropertyResponse;
    }

    @Override
    public String buildHexString() {
        Apdu apdu = Apdu.ApduBuilder.builder()
                .withApduType(MessageType.ConfirmedRequest)
                .isSegmented(false)
                .hasMoreSegments(false)
                .isSegmentedReplyAllowed(true)
                .withMaxSegmentsAcceptedAbove64()
                .withMaxApduLength1476()
                .build();
        String apduHexString = apdu.toHexString()
                + intToHexString(getInvokeId(), 2)
                + ConfirmedServiceChoice.ReadPropertyMultiple.getServiceChoiceHex();
        if (objectId != null && propertyReferences != null) {
            apduHexString += SDContextTag.TAG0LENGTH4 + objectId.toHexString();
            apduHexString += ARRAY1_START.toString();
            for (PropertyReference propertyReference : propertyReferences) {
                apduHexString += propertyReference.buildHexString();
            }
            apduHexString += ARRAY1_END;
        }
        int numberOfOctets = (apduHexString.length() / 2) + 6;
        Bvlc bvlc = new BvlcBuilder(BvlcFunction.OriginalUnicastNpdu).withTotalNumberOfOctets(numberOfOctets).build();
        Npdu npdu = new NpduBuilder().withExpectingReply().build();
        String hexString = bvlc.toHexString() + npdu.toHexString() + apduHexString;
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public boolean expectReply() {
        return true;
    }

    @Override
    public String toString() {
        return "ReadPropertyService{" +
                "invokeId=" + invokeId +
                ", objectProperties=" + objectProperties +
                ", objectId=" + objectId +
                ", propertyReferences=" + propertyReferences +
                ", readPropertyResponse=" + readPropertyResponse +
                "} " + super.toString();
    }
}
