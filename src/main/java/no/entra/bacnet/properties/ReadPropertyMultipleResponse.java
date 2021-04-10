package no.entra.bacnet.properties;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/*
{
  "objectId": "BACnetObjectIdentifier",
  "results": [
    {
      "property-identifier": "BacnetPropertyIdentifier",
      "property-array-index": "-when property-identifier is an array eg object-list",
      "read-result": {
        "property-value": "-abstract-syntax??-",
        "property-access-error": "-when property-value failed.-"
      }
    }
  ]
}

*** Examples ***
Count number of objects available on a device.
Igjen er Json god til å dokumentere domenemodeller

Count number of available Sensors, Actuators and Devices available on a specific Device. This is the response.
{
  "objectId": "device, 8",
  "results": [
    {
      "property-identifier": "object-list",
      "property-array-index": "0",
      "read-result": {
        "object-list": "361"
      }
    }
  ]
}
Find ObjectIdentifier for every input/output on the Device. This is the response.
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
]

Find 4 properties on Device and Sensors. With Error. This is the response.
[
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
    },
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
  ]
 */
public class ReadPropertyMultipleResponse {
    private static final Logger log = getLogger(ReadPropertyMultipleResponse.class);

    private final List<ReadObjectPropertiesResult> results;

    public ReadPropertyMultipleResponse() {
        results = new ArrayList<>();
    }

    public ReadPropertyMultipleResponse(List<ReadObjectPropertiesResult> results) {
        this.results = results;
    }

    public List<ReadObjectPropertiesResult> getResults() {
        return results;
    }

    public void addReadObjectPropertiesResult(ReadObjectPropertiesResult readObjectPropertiesResult) {
        results.add(readObjectPropertiesResult);
    }

    @Override
    public String toString() {
        return "ReadPropertyMultipleResponse{" +
                "results=" + results +
                '}';
    }
}
