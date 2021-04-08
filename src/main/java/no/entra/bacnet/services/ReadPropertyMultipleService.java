package no.entra.bacnet.services;

import no.entra.bacnet.apdu.*;
import no.entra.bacnet.bvlc.Bvlc;
import no.entra.bacnet.bvlc.BvlcBuilder;
import no.entra.bacnet.bvlc.BvlcFunction;
import no.entra.bacnet.npdu.Npdu;
import no.entra.bacnet.npdu.NpduBuilder;
import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.objects.ObjectIdMapper;
import no.entra.bacnet.objects.ObjectIdMapperResult;
import no.entra.bacnet.objects.ObjectProperties;
import no.entra.bacnet.octet.Octet;
import no.entra.bacnet.octet.OctetReader;
import no.entra.bacnet.properties.PropertyIdentifier;
import no.entra.bacnet.properties.PropertyReference;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static no.entra.bacnet.apdu.ArrayTag.ARRAY1_END;
import static no.entra.bacnet.apdu.ArrayTag.ARRAY1_START;
import static no.entra.bacnet.apdu.SDContextTag.TAG4START;
import static no.entra.bacnet.utils.HexUtils.intToHexString;
import static no.entra.bacnet.utils.HexUtils.toInt;
import static no.entra.bacnet.utils.StringUtils.hasValue;
import static org.slf4j.LoggerFactory.getLogger;

public class ReadPropertyMultipleService implements Service, BacnetRequest, BacnetResponse {
    private static final Logger log = getLogger(ReadPropertyMultipleService.class);

    private Integer invokeId = null;
    private Set<ObjectProperties> objectProperties = null;
    private ObjectId objectId = null;
    private Set<PropertyReference> propertyReferences = null;
    private List<ObjectId> propertyListResponse = null;


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

    public static ReadPropertyMultipleService parse(String hexString) {
        //Expect
        //ObjectId
        //List of PropertyReferences
        ObjectIdMapperResult<ObjectId> idMapperResult = ObjectIdMapper.parse(hexString);
        ReadPropertyMultipleService service = new ReadPropertyMultipleService();
        ObjectId objectId = idMapperResult.getParsedObject();
        int numberOfOctetsRead = idMapperResult.getNumberOfOctetsRead();
        OctetReader listReader = new OctetReader(hexString);
        listReader.next(numberOfOctetsRead); //Discard
        Octet startList = listReader.next();
        if (startList.equals(ARRAY1_START)) {
            //PropertyReference/ObjectList
            //PresentValue
            String unprocessedHexString = listReader.unprocessedHexString();
            while (hasValue(unprocessedHexString)) {
                Octet contextTag = listReader.next();
                int arrayIndex = -1;
                if (contextTag.equals(SDContextTag.TAG2LENGTH1)) {
                    Octet propertyIdentifierOctet = listReader.next();
                    PropertyIdentifier propertyIdentifier = PropertyIdentifier.fromOctet(propertyIdentifierOctet);
                    if (propertyIdentifier.equals(PropertyIdentifier.ObjectList)) {
                        Octet arrayIndexTag = listReader.next();

                        if (arrayIndexTag.equals(SDContextTag.TAG3LENGTH1)) {
                            Octet indexNumber = listReader.next();
                            arrayIndex = toInt(indexNumber);
                        } else if (arrayIndexTag.equals(SDContextTag.TAG3LENGTH2)) {
                            arrayIndex = toInt(listReader.nextOctets(2));
                        }
                        if (arrayIndex > -1) {
                            PropertyReference propertyReference = new PropertyReference(propertyIdentifier, arrayIndex);
                            service.addPropertyReference(propertyReference);
                            service.setObjectId(objectId);
                            log.trace("Added: {}", propertyReference);
                        }
                    }
                    if (listReader.unprocessedHexString().startsWith(TAG4START.toString())) {
                        listReader.next();
                        ApplicationTag applicationTag = new ApplicationTag(listReader.next());
                        Object value = null;

                        ValueType valueType = applicationTag.findValueType();
                        switch (valueType) {
                            case Long:
                            case Integer:
                                int length = applicationTag.findLength();
                                value = toInt(listReader.nextOctets(length));
                                ObjectProperties objectProperties = new ObjectProperties(objectId, Set.of(propertyIdentifier));
                                objectProperties.addProperty(propertyIdentifier, value);
                                service.addObjectProperty(objectProperties);
                                break;
                            case ObjectIdentifier:
                                //TODO
                                ObjectIdMapperResult<ObjectId> idResult = ObjectIdMapper.parse(listReader.next(5));
                                ObjectId propertyObjectId = idResult.getParsedObject();
                                service.addPropertyResponse(objectId);
                                break;
                            default:
                                throw new IllegalArgumentException("Not implemented yet, " + valueType);
                        }

                    }
                } else if (listReader.unprocessedHexString().startsWith(TAG4START.toString())) {
                    //Handle Error
                }

                unprocessedHexString = listReader.unprocessedHexString();
            }
                /*
                PropertyResult propertyResult = parseProperty(unprocessedHexString);
                if (propertyResult != null ) {
                    if (propertyResult.getProperty() != null) {
                        Property property = propertyResult.getProperty();
                        String key = property.getKey();
                        Object value = property.getValue();
                        log.trace("{}:{}", key, value);
//                        accessResult.setResultByKey(key, value);
                    }
                    unprocessedHexString = propertyResult.getUnprocessedHexString();
                }

                 */

        }
        return service;
    }

    private void addPropertyResponse(ObjectId objectId) {
        if (propertyListResponse == null) {
            propertyListResponse = new ArrayList<>();
        }
        propertyListResponse.add(objectId);
    }

    public List<ObjectId> getPropertyListResponse() {
        return propertyListResponse;
    }

    private void addObjectProperty(ObjectProperties objectProperty) {
        if (objectProperties == null) {
            objectProperties = new HashSet<>();
        }
        objectProperties.add(objectProperty);
    }

    void addPropertyReference(PropertyReference propertyReference) {
        if (propertyReferences == null) {
            propertyReferences = new HashSet<>();
        }
        propertyReferences.add(propertyReference);
    }


}
