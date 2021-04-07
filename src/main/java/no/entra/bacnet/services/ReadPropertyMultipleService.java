package no.entra.bacnet.services;

import no.entra.bacnet.apdu.Apdu;
import no.entra.bacnet.apdu.ApduType;
import no.entra.bacnet.apdu.SDContextTag;
import no.entra.bacnet.bvlc.Bvlc;
import no.entra.bacnet.bvlc.BvlcBuilder;
import no.entra.bacnet.bvlc.BvlcFunction;
import no.entra.bacnet.npdu.Npdu;
import no.entra.bacnet.npdu.NpduBuilder;
import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.objects.ObjectProperties;
import no.entra.bacnet.properties.PropertyReference;

import java.util.Set;

import static no.entra.bacnet.apdu.ArrayTag.ARRAY1_END;
import static no.entra.bacnet.apdu.ArrayTag.ARRAY1_START;
import static no.entra.bacnet.utils.HexUtils.intToHexString;

public class ReadPropertyMultipleService implements Service, BacnetRequest {

    private Integer invokeId = null;
    private Set<ObjectProperties> objectProperties = null;
    private ObjectId objectId = null;
    private Set<PropertyReference> propertyReferences = null;

    public ReadPropertyMultipleService() {
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

    // List of:
    // ObjectId
    // List of PropertyIdentifier or PropertyReference

//    public ArrayList<Class> requiredSequence() {
//        ArrayList<Class> requirement = new ArrayList<>();
//        ArrayList<Class> sensorProperties = new ArrayList<>();
//        sensorProperties.add(ObjectId.class);
//        sensorProperties.add(List< PropertyIdentifier.class >.class);
//
//        requirement.add(ObjectId.class);
//        requirement.add(List< PropertyIdentifier.class >.class);
//        requirement.add(List<SensorProperties.class>)
//        return requirement;
//    }





    @Override
    public String buildHexString() {
        Apdu apdu = Apdu.ApduBuilder.builder()
                .withApduType(ApduType.ConfirmedRequest)
                .isSegmented(false)
                .hasMoreSegments(false)
                .isSegmentedReplyAllowed(true)
                .withMaxSegmentsAcceptedAbove64()
                .withMaxApduLength1476()
                .build();
        String apduHexString = apdu.toHexString()
                + intToHexString(getInvokeId(), 2)
                + ConfirmedServiceChoice.ReadPropertyMultiple.getServiceChoiceHex();
//        if (propertiesToRead != null && propertiesToRead.size() > 0) {
//            for (ReadAccessSpecification readAccessSpecification : propertiesToRead) {
//                apduHexString += readAccessSpecification.buildHexString();
//            }
//        }
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
        return hexString;
    }
}
