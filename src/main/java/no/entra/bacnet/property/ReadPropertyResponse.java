package no.entra.bacnet.property;

import no.entra.bacnet.internal.property.ReadSinglePropertyResult;
import org.slf4j.Logger;

import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

/*
{
  "objectId": "device, 8",
  "property-identifier": "PropertiesServicesSupported",
  "property-array-index": 0,
  "value": "00000111000000000000101111000000000000001111100000000000"
}
 */
public class ReadPropertyResponse {
    private static final Logger log = getLogger(ReadPropertyResponse.class);

    private final ReadSinglePropertyResult result;

    public ReadPropertyResponse(ReadSinglePropertyResult result) {
        this.result = result;
    }

    public ReadSinglePropertyResult getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "ReadPropertyResponse{" +
                "result=" + result +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReadPropertyResponse that = (ReadPropertyResponse) o;
        return Objects.equals(getResult(), that.getResult());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getResult());
    }
}
