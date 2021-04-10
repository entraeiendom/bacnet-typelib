package no.entra.bacnet.internal.properties;

import no.entra.bacnet.objects.ObjectId;

import java.util.ArrayList;
import java.util.List;

/*
{
  "objectId": "analog-value, 0",
  "results": [
    {
      "property-identifier": "object-name",
      "read-result": {
        "Object Name": "UI1_ZoneTemperature"
      }
    },
    {
      "property-identifier": "description",
      "read-result": {
        "description": "Analog Value 0"
      }
    },
    {
      "property-identifier": "units",
      "read-result": {
        "units": "No Units"
      }
    },
    {
      "property-identifier": "present-value",
      "read-result": {
        "Present Value": 22.3999862670898
      }
    }
  ]
}
With Error
{
  "objectId": "device, 8",
  "results": [
    {
      "property-identifier": "object-name",
      "read-result": {
        "Object Name": "FWFCU"
      }
    },
    {
      "property-identifier": "description",
      "read-result": {
        "description": "FW Series Bacnet Device"
      }
    },
    {
      "property-identifier": "present-value",
      "read-result": {
        "propertyAccessError": {
          "errorClass": "property",
          "errorCode": "unknown-property"
        }
      }
    }
  ]
}

With Array
{
  "objectId": "device, 8",
  "results": [
    {
      "property-identifier": "object-list",
      "property-array-index": "1",
      "read-result": {
        "objectId": "device, 8"
      }
    },
    {
      "property-identifier": "object-list",
      "property-array-index": "2",
      "read-result": {
        "objectId": "analog-value, 0"
      }
    }
  ]
}
 */
public class ReadObjectPropertiesResult {
    private final ObjectId objectId;
    private final List<ReadPropertyResult> results;


    public ReadObjectPropertiesResult(ObjectId objectId) {
        this.objectId = objectId;
        results = new ArrayList<>();
    }

    public ReadObjectPropertiesResult(ObjectId objectId, List<ReadPropertyResult> results) {
        this.objectId = objectId;
        this.results = results;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public List<ReadPropertyResult> getResults() {
        return results;
    }

    public void addReadPropertyResult(ReadPropertyResult readPropertyResult) {
        results.add(readPropertyResult);
    }

    @Override
    public String toString() {
        return "ReadObjectPropertiesResult{" +
                "objectId=" + objectId +
                ", results=" + results +
                '}';
    }
}
