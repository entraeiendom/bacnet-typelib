package no.entra.bacnet.services;

public class BacnetParserResult {

    private boolean parsedOk = false;
    private String initialHexString = "";
    private String unparsedHexString = "";
    private String errorMessage = null;

    public BacnetParserResult() {
    }

    public BacnetParserResult(String initialHexString, String unparsedHexString, String errorMessage) {
        this.initialHexString = initialHexString;
        this.unparsedHexString = unparsedHexString;
        this.errorMessage = errorMessage;
        parsedOk = false;
    }

    public boolean isParsedOk() {
        return parsedOk;
    }

    public void setParsedOk(boolean parsedOk) {
        this.parsedOk = parsedOk;
    }

    public String getInitialHexString() {
        return initialHexString;
    }

    public void setInitialHexString(String initialHexString) {
        this.initialHexString = initialHexString;
    }

    public String getUnparsedHexString() {
        return unparsedHexString;
    }

    public void setUnparsedHexString(String unparsedHexString) {
        this.unparsedHexString = unparsedHexString;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
