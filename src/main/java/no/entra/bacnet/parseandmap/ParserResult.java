package no.entra.bacnet.parseandmap;

public class ParserResult<T> {

    private T parsedObject;
    private int numberOfOctetsRead = -1;
    private boolean parsedOk = true;
    private String initialHexString = "";
    private String unparsedHexString = "";
    private String errorMessage = null;

    public ParserResult() {
    }

    public ParserResult(T parsedObject, int numberOfOctetsRead) {
        this.parsedObject = parsedObject;
        this.numberOfOctetsRead = numberOfOctetsRead;
    }

    public ParserResult(T parsedObject, String initialHexString, String unparsedHexString, boolean parsedOk) {
        this.parsedObject = parsedObject;
        this.initialHexString = initialHexString;
        this.unparsedHexString = unparsedHexString;
        this.parsedOk = parsedOk;
    }

    public void setParsedObject(T parsedObject) {
        this.parsedObject = parsedObject;
    }

    public T getParsedObject() {
        return parsedObject;
    }

    public void setNumberOfOctetsRead(int numberOfOctetsRead) {
        this.numberOfOctetsRead = numberOfOctetsRead;
    }

    public int getNumberOfOctetsRead() {
        if (numberOfOctetsRead < 0) {
            if (initialHexString != null && unparsedHexString != null) {
                return (initialHexString.length() - unparsedHexString.length())/2;
            }
        }
        return numberOfOctetsRead;
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

    @Override
    public String toString() {
        return "ParserResult{" +
                "parsedObject=" + parsedObject +
                ", numberOfOctetsRead=" + numberOfOctetsRead +
                ", parsedOk=" + parsedOk +
                ", initialHexString='" + initialHexString + '\'' +
                ", unparsedHexString='" + unparsedHexString + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
