package no.entra.bacnet.internal.property;

import no.entra.bacnet.internal.properties.PropertyIdentifier;
import no.entra.bacnet.objects.ObjectId;

import java.util.Objects;

public class ReadSinglePropertyResult {
    private ObjectId objectId;
    private PropertyIdentifier propertyIdentifier;
    private Object value;
    private Integer arrayIndexNumber = null;

    public ReadSinglePropertyResult() {
    }

    public ReadSinglePropertyResult(ObjectId objectId, PropertyIdentifier propertyIdentifier, Object value) {
        this.objectId = objectId;
        this.propertyIdentifier = propertyIdentifier;
        this.value = value;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public PropertyIdentifier getPropertyIdentifier() {
        return propertyIdentifier;
    }

    public void setPropertyIdentifier(PropertyIdentifier propertyIdentifier) {
        this.propertyIdentifier = propertyIdentifier;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Integer getArrayIndexNumber() {
        return arrayIndexNumber;
    }

    public void setArrayIndexNumber(Integer arrayIndexNumber) {
        this.arrayIndexNumber = arrayIndexNumber;
    }

    public boolean isValid() {
        return objectId != null && propertyIdentifier != null && value != null;
    }

    @Override
    public String toString() {
        return "ReadSinglePropertyResult{" +
                "objectId=" + objectId +
                ", propertyIdentifier=" + propertyIdentifier +
                ", value=" + value +
                ", arrayIndexNumber=" + arrayIndexNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReadSinglePropertyResult that = (ReadSinglePropertyResult) o;
        return Objects.equals(getObjectId(), that.getObjectId()) &&
                getPropertyIdentifier() == that.getPropertyIdentifier() &&
                Objects.equals(getValue(), that.getValue()) &&
                Objects.equals(getArrayIndexNumber(), that.getArrayIndexNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getObjectId(), getPropertyIdentifier(), getValue(), getArrayIndexNumber());
    }
}
