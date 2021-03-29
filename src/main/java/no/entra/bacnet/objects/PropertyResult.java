package no.entra.bacnet.objects;

public class PropertyResult {
    private final String unprocessedHexString;
    private no.entra.bacnet.objects.Property property = null;

    public PropertyResult(String unprocessedHexString) {
        this.unprocessedHexString = unprocessedHexString;
    }

    public PropertyResult(String unprocessedHexString, no.entra.bacnet.objects.Property property) {
        this.unprocessedHexString = unprocessedHexString;
        this.property = property;
    }

    public String getUnprocessedHexString() {
        return unprocessedHexString;
    }

    public no.entra.bacnet.objects.Property getProperty() {
        return property;
    }

    public void setProperty(no.entra.bacnet.objects.Property property) {
        this.property = property;
    }
}
