package no.entra.bacnet.parseandmap;

public class ParserResult<T> {

    private final T parsedObject;
    private final int numberOfOctetsRead;

    public ParserResult(T parsedObject, int numberOfOctetsRead) {
        this.parsedObject = parsedObject;
        this.numberOfOctetsRead = numberOfOctetsRead;
    }

    public T getParsedObject() {
        return parsedObject;
    }

    public int getNumberOfOctetsRead() {
        return numberOfOctetsRead;
    }
}
