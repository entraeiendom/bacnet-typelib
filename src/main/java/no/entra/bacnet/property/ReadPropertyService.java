package no.entra.bacnet.property;

import no.entra.bacnet.BacnetRequest;
import no.entra.bacnet.services.Service;

public class ReadPropertyService extends BacnetRequest implements Service {
    @Override
    public String buildHexString() {
        return null;
    }

    @Override
    public boolean expectReply() {
        return false;
    }
}
