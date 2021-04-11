package no.entra.bacnet;

public abstract class BacnetRequest {
    //SEE no.entra.bacnet.json.services.ServiceBuilder
    protected BacnetRequest() {
    }

    public abstract String buildHexString();

    public abstract boolean expectReply();
}
