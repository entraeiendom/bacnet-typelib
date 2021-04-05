package no.entra.bacnet.properties;

import no.entra.bacnet.apdu.SDContextTag;

import static no.entra.bacnet.utils.HexUtils.intToHexString;

public class PropertyReference {
    private final PropertyIdentifier propertyIdentifier;
    private int propertyArrayIndex = -1;

    public PropertyReference(PropertyIdentifier propertyIdentifier) {
        this.propertyIdentifier = propertyIdentifier;
    }

    public PropertyReference(PropertyIdentifier propertyIdentifier, int propertyArrayIndex) {
        this.propertyIdentifier = propertyIdentifier;
        this.propertyArrayIndex = propertyArrayIndex;
    }

    public PropertyIdentifier getPropertyIdentifier() {
        return propertyIdentifier;
    }

    public int getPropertyArrayIndex() {
        return propertyArrayIndex;
    }

    public void setPropertyArrayIndex(int propertyArrayIndex) {
        this.propertyArrayIndex = propertyArrayIndex;
    }

    public String buildHexString() {
        String hexString = SDContextTag.TAG0LENGTH1 + propertyIdentifier.getPropertyIdentifierHex();
//        int arrayIndexValue = propertyReference.getPropertyArrayIndex();
        if (propertyArrayIndex > -1) {
            if (propertyArrayIndex < Integer.parseInt("ff", 16)) {
                hexString += SDContextTag.TAG1LENGTH1 + intToHexString(propertyArrayIndex, 2);
            } else {
                hexString += SDContextTag.TAG1LENGTH2 + intToHexString(propertyArrayIndex, 4);
            }
        }
        return hexString;
    }

    @Override
    public String toString() {
        return "PropertyReference{" +
                "propertyIdentifier=" + propertyIdentifier.name() +
                ", propertyArrayIndex=" + propertyArrayIndex +
                '}';
    }
}
