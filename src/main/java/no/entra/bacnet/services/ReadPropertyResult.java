package no.entra.bacnet.services;

import no.entra.bacnet.properties.PropertyIdentifier;

import java.util.HashMap;
import java.util.Map;

/*
{
  "property-identifier": "object-list",
  "property-array-index": "2",
  "read-result": {
    "objectId": "analog-value, 0"
  }
}
 */
public class ReadPropertyResult extends BacnetParserResult {
    private final PropertyIdentifier propertyIdentifier;
    private int arrayIndexNumber;
    private final Map<String, Object> readResult;

    public ReadPropertyResult(PropertyIdentifier propertyIdentifier) {
        this.propertyIdentifier = propertyIdentifier;
        readResult = new HashMap<>();
    }

    public ReadPropertyResult(PropertyIdentifier propertyIdentifier, Map<String, Object> readResult) {
        this.propertyIdentifier = propertyIdentifier;
        this.readResult = readResult;
    }

    public PropertyIdentifier getPropertyIdentifier() {
        return propertyIdentifier;
    }

    public int getArrayIndexNumber() {
        return arrayIndexNumber;
    }

    public void setArrayIndexNumber(int arrayIndexNumber) {
        this.arrayIndexNumber = arrayIndexNumber;
    }

    public Map<String, Object> getReadResult() {
        return readResult;
    }

    public void addReadResult(String key, Object value) {
        readResult.put(key,value);
    }

    @Override
    public String toString() {
        return "ReadPropertyResult{" +
                "propertyIdentifier=" + propertyIdentifier +
                ", arrayIndexNumber=" + arrayIndexNumber +
                ", readResult=" + readResult +
                '}';
    }
}
